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

package com.liferay.sync.engine.lan;

import com.liferay.sync.engine.lan.util.LanClientUtil;
import com.liferay.sync.engine.lan.util.LanPEMUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SniHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.DomainNameMapping;
import io.netty.util.DomainNameMappingBuilder;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanFileServerInitializer
	extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel socketChannel) {
		ChannelPipeline channelPipeline = socketChannel.pipeline();

		try {
			DomainNameMapping domainNameMapping = _getDomainNameMapping();

			if (domainNameMapping != null) {
				channelPipeline.addLast(new SniHandler(domainNameMapping));
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}

		channelPipeline.addLast(new HttpServerCodec());
		channelPipeline.addLast(new HttpObjectAggregator(65536));
		channelPipeline.addLast(new ChunkedWriteHandler());

		LanFileServerHandler lanSyncServerHandler = new LanFileServerHandler();

		channelPipeline.addLast(lanSyncServerHandler);
	}

	public void reload() {
		_domainNameMapping = null;
	}

	private DomainNameMapping<SslContext> _getDomainNameMapping()
		throws Exception {

		if (_domainNameMapping != null) {
			return _domainNameMapping;
		}

		DomainNameMappingBuilder<SslContext> domainNameMappingBuilder = null;

		for (SyncAccount syncAccount : SyncAccountService.findAll()) {
			if (!syncAccount.isActive()) {
				continue;
			}

			PrivateKey privateKey = LanPEMUtil.getPrivateKey(
				syncAccount.getLanKey());

			X509Certificate x509Certificate = LanPEMUtil.getX509Certificate(
				syncAccount.getLanCertificate());

			SslContextBuilder sslContextBuilder = SslContextBuilder.forServer(
				privateKey, x509Certificate);

			sslContextBuilder.clientAuth(ClientAuth.REQUIRE);
			sslContextBuilder.sslProvider(SslProvider.JDK);
			sslContextBuilder.trustManager(x509Certificate);

			SslContext sslContext = sslContextBuilder.build();

			if (domainNameMappingBuilder == null) {
				domainNameMappingBuilder = new DomainNameMappingBuilder<>(
					sslContext);
			}

			String sniCompliantLanServerId =
				LanClientUtil.getSNICompliantLanServerId(
					syncAccount.getLanServerId());

			domainNameMappingBuilder.add(sniCompliantLanServerId, sslContext);
		}

		if (domainNameMappingBuilder == null) {
			return null;
		}

		_domainNameMapping = domainNameMappingBuilder.build();

		return _domainNameMapping;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanFileServerInitializer.class);

	private DomainNameMapping<SslContext> _domainNameMapping;

}