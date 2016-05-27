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

package com.liferay.sync.engine.lan.discovery;

import com.liferay.sync.engine.util.JSONUtil;
import com.liferay.sync.engine.util.PropsValues;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author Dennis Ju
 */
public class DiscoveryBroadcaster {

	public static void main(String[] args) throws Exception {
		DiscoveryBroadcaster discoveryBroadcaster = new DiscoveryBroadcaster();

		discoveryBroadcaster.broadcast("testing");
	}

	private void _initialize() throws Exception {
		EventLoopGroup eventLoopGroup = null;

		try {
			eventLoopGroup = new NioEventLoopGroup();

			Bootstrap bootstrap = new Bootstrap();

			bootstrap.channel(NioDatagramChannel.class);
			bootstrap.group(eventLoopGroup);
			bootstrap.handler(new DiscoveryBroadcasterHandler());
			bootstrap.option(ChannelOption.SO_BROADCAST, true);

			ChannelFuture channelFuture = bootstrap.bind(
				0);

			channelFuture = channelFuture.sync();

			_channel = channelFuture.channel();
		}
		catch (Exception e) {
			eventLoopGroup.shutdownGracefully();

			throw e;
		}
	}

	public void broadcast(Object object) throws Exception {
		if (_channel == null) {
			_initialize();
		}

		byte[] jsonBytes = JSONUtil.writeValueAsBytes(object);

		InetSocketAddress inetSocketAddress = new InetSocketAddress(
			"255.255.255.255", PropsValues.SYNC_LAN_UDP_PORT);

		DatagramPacket datagramPacket = new DatagramPacket(
			Unpooled.copiedBuffer(jsonBytes), inetSocketAddress);

		ChannelFuture channelFuture = _channel.writeAndFlush(datagramPacket);

		channelFuture.sync();
	}

	private static Channel _channel;

	private static final Logger _logger = LoggerFactory.getLogger(
		DiscoveryBroadcaster.class);

}