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

import com.liferay.sync.engine.lan.discovery.DiscoveryBroadcaster;
import com.liferay.sync.engine.lan.discovery.DiscoveryListener;
import com.liferay.sync.engine.lan.util.LanClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Dennis Ju
 */
public class LanEngine {

	private static DiscoveryListener _discoveryListener;
	private static DiscoveryBroadcaster _discoveryBroadcaster;
	private static LanFileServer _lanFileServer;

	public static synchronized void stop() {
		if (_discoveryListener != null) {
			_discoveryListener.shutdown();
		}

		if (_discoveryBroadcasterScheduledFuture != null) {
			_discoveryBroadcasterScheduledFuture.cancel(true);
		}

		if (_lanFileServer != null) {
			_lanFileServer.stop();
		}
	}

	private static ScheduledFuture _discoveryBroadcasterScheduledFuture;

	public static synchronized void start() {
		if (_lanFileServer == null) {
			_lanFileServer = new LanFileServer();
		}

		try {
			_lanFileServer.start();
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			return;
		}

		if (_discoveryBroadcaster == null) {
			_discoveryBroadcaster = new DiscoveryBroadcaster();
		}

		Runnable broadcastRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					int port = _lanFileServer.getPort();

					_discoveryBroadcaster.broadcast(
						LanClientUtil.createSyncLanClient(port));
				}
				catch (Exception e) {
					_logger.error(e.getMessage(), e);
				}
			}

		};

		_discoveryBroadcasterScheduledFuture = _scheduledExecutorService.scheduleWithFixedDelay(
			broadcastRunnable, 0, 5000,
//		broadcastRunnable, 0, PropsValues.SYNC_LAN_BROADCAST_INTERVAL,
			TimeUnit.MILLISECONDS);

		if (_discoveryListener == null) {
			_discoveryListener = new DiscoveryListener();
		}

		Runnable _discoveryListenerRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					_discoveryListener.listen();
				}
				catch (Exception e) {
					_logger.error(e.getMessage(), e);
				}
			}
		};

		_scheduledExecutorService.submit(_discoveryListenerRunnable);
	}


	private static final Logger _logger = LoggerFactory.getLogger(
		LanEngine.class);


	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(2);

}