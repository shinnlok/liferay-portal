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

package com.liferay.sync.engine.document.library.handler;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author Jonathan McCann
 */
public class ThrottledInputStream extends InputStream {

	public ThrottledInputStream(InputStream inputStream, long syncAccountId) {
		_inputStream = inputStream;

		_rateLimiter = RateLimiter.create(2 * FileUtils.ONE_MB);

		_syncAccountId = syncAccountId;

		RateLimiterUtil.registerDownloadConnection(
			_syncAccountId, _rateLimiter);
	}

	@Override
	public void close() throws IOException {
		RateLimiterUtil.unregisterDownloadConnection(
			_syncAccountId, _rateLimiter);

		super.close();
	}

	@Override
	public int read() throws IOException {
		_rateLimiter.acquire(1);

		return _inputStream.read();
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		_rateLimiter.acquire(bytes.length);

		return _inputStream.read(bytes);
	}

	@Override
	public int read(byte[] bytes, int off, int len) throws IOException {
		_rateLimiter.acquire(len);

		return _inputStream.read(bytes, off, len);
	}

	private static long _syncAccountId;

	private static RateLimiter _rateLimiter;

	private final InputStream _inputStream;

}