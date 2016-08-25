/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sync.engine.lan.fileserver;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.HEAD;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.liferay.sync.engine.lan.util.LanTokenUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.util.GetterUtil;
import com.liferay.sync.engine.util.OSDetector;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;

import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanFileServerHandler
	extends SimpleChannelInboundHandler<FullHttpRequest> {

	@Override
	public void channelRead0(
			ChannelHandlerContext channelHandlerContext,
			FullHttpRequest fullHttpRequest)
		throws Exception {

		ChannelPipeline channelPipeline = channelHandlerContext.pipeline();

		if (channelPipeline.get(SslHandler.class) == null) {
			_sendError(channelHandlerContext, BAD_REQUEST);

			return;
		}

		DecoderResult decoderResult = fullHttpRequest.decoderResult();

		if (!decoderResult.isSuccess()) {
			_sendError(channelHandlerContext, BAD_REQUEST);

			return;
		}

		if (fullHttpRequest.method() == GET) {
			processGetRequest(channelHandlerContext, fullHttpRequest);
		}
		else if (fullHttpRequest.method() == HEAD) {
			processHeadRequest(channelHandlerContext, fullHttpRequest);
		}
		else {
			_sendError(channelHandlerContext, BAD_REQUEST);
		}
	}

	@Override
	public void exceptionCaught(
		ChannelHandlerContext channelHandlerContext, Throwable cause) {

		_logger.error(cause.getMessage(), cause);

		Channel channel = channelHandlerContext.channel();

		if (channel.isActive()) {
			_sendError(channelHandlerContext, INTERNAL_SERVER_ERROR);
		}
	}

	protected void processGetRequest(
			ChannelHandlerContext channelHandlerContext,
			FullHttpRequest fullHttpRequest)
		throws Exception {

		HttpHeaders httpHeaders = fullHttpRequest.headers();

		String lanToken = httpHeaders.get("lanToken");

		if ((lanToken == null) || lanToken.isEmpty()) {
			Channel channel = channelHandlerContext.channel();

			_logger.error(
				"Lan client {} did not send token", channel.remoteAddress());

			_sendError(channelHandlerContext, NOT_FOUND);

			return;
		}

		if (!LanTokenUtil.containsLanToken(lanToken)) {
			Channel channel = channelHandlerContext.channel();

			_logger.error(
				"Lan client {} token not found or expired",
				channel.remoteAddress());

			_sendError(channelHandlerContext, NOT_FOUND);

			return;
		}

		SyncFile syncFile = _getSyncFile(fullHttpRequest);

		if (syncFile == null) {
			_sendError(channelHandlerContext, NOT_FOUND);

			return;
		}

		sendFile(channelHandlerContext, fullHttpRequest, syncFile);

		LanTokenUtil.removeLanToken(lanToken);
	}

	protected void processHeadRequest(
		ChannelHandlerContext channelHandlerContext,
		FullHttpRequest fullHttpRequest) {

		SyncFile syncFile = _getSyncFile(fullHttpRequest);

		if (syncFile == null) {
			_sendError(channelHandlerContext, NOT_FOUND);

			return;
		}

		String lanTokenKey = syncFile.getLanTokenKey();

		if ((lanTokenKey == null) || lanTokenKey.isEmpty()) {
			_sendError(channelHandlerContext, NOT_FOUND);

			return;
		}

		String encryptedToken = null;

		try {
			encryptedToken = LanTokenUtil.createEncryptedToken(lanTokenKey);
		}
		catch (Exception e) {
			_sendError(channelHandlerContext, NOT_FOUND);

			return;
		}

		HttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK);

		HttpHeaders httpHeaders = httpResponse.headers();

		httpHeaders.set("encryptedToken", encryptedToken);

		channelHandlerContext.writeAndFlush(httpResponse);
	}

	protected void sendFile(
			ChannelHandlerContext channelHandlerContext,
			FullHttpRequest fullHttpRequest, SyncFile syncFile)
		throws Exception {

		Path path = Paths.get(syncFile.getFilePathName());

		if (Files.notExists(path)) {
			_sendError(channelHandlerContext, NOT_FOUND);

			return;
		}

		HttpResponse httpResponse = new DefaultHttpResponse(HTTP_1_1, OK);

		HttpUtil.setContentLength(httpResponse, Files.size(path));

		HttpHeaders httpHeaders = httpResponse.headers();

		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

		httpHeaders.set(
			HttpHeaderNames.CONTENT_TYPE,
			mimetypesFileTypeMap.getContentType(syncFile.getName()));

		if (HttpUtil.isKeepAlive(fullHttpRequest)) {
			httpHeaders.set(
				HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}

		channelHandlerContext.write(httpResponse);

		SeekableByteChannel seekableByteChannel = Files.newByteChannel(
			path, StandardOpenOption.READ);

		ByteBuffer byteBuffer = ByteBuffer.allocate(65536);

		long modifiedTime = syncFile.getModifiedTime();
		long previousModifiedTime = syncFile.getPreviousModifiedTime();

		if (OSDetector.isApple()) {
			modifiedTime = modifiedTime / 1000 * 1000;
			previousModifiedTime = previousModifiedTime / 1000 * 1000;
		}

		while (seekableByteChannel.read(byteBuffer) > 0) {
			byteBuffer.flip();

			ByteBufInputStream byteBufInputStream = new ByteBufInputStream(
				Unpooled.wrappedBuffer(byteBuffer));

			channelHandlerContext.write(new ChunkedStream(byteBufInputStream));

			byteBuffer.clear();

			FileTime currentFileTime = Files.getLastModifiedTime(
				path, LinkOption.NOFOLLOW_LINKS);

			long currentTime = currentFileTime.toMillis();

			if ((currentTime != modifiedTime) &&
				(currentTime != previousModifiedTime)) {

				seekableByteChannel.close();

				channelHandlerContext.close();

				return;
			}
		}

		seekableByteChannel.close();

		ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(
			LastHttpContent.EMPTY_LAST_CONTENT,
			channelHandlerContext.newProgressivePromise());

		if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
			channelFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private SyncFile _getSyncFile(FullHttpRequest fullHttpRequest) {
		String[] pathArray = StringUtils.split(fullHttpRequest.uri(), "/");

		if (pathArray.length != 4) {
			return null;
		}

		String lanServerUuid = pathArray[0];
		long repositoryId = GetterUtil.getLong(pathArray[1]);
		long typePK = GetterUtil.getLong(pathArray[2]);
		long versionId = GetterUtil.getLong(pathArray[3]);

		if (lanServerUuid.isEmpty() || (repositoryId == 0) || (typePK == 0) ||
			(versionId == 0)) {

			return null;
		}

		List<SyncAccount> syncAccounts = SyncAccountService.findSyncAccounts(
			lanServerUuid);

		for (SyncAccount syncAccount : syncAccounts) {
			SyncFile syncFile = SyncFileService.fetchSyncFile(
				repositoryId, syncAccount.getSyncAccountId(), typePK,
				versionId);

			if ((syncFile != null) &&
				(syncFile.getState() == SyncFile.STATE_SYNCED)) {

				return syncFile;
			}
		}

		return null;
	}

	private void _sendError(
		ChannelHandlerContext channelHandlerContext,
		HttpResponseStatus httpResponseStatus) {

		ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(
			new DefaultFullHttpResponse(HTTP_1_1, httpResponseStatus));

		channelFuture.addListener(ChannelFutureListener.CLOSE);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanFileServerHandler.class);

}