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

package com.liferay.sync.engine.session.rate.limiter;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author Jonathan McCann
 */
public class RateLimitedOutputStream extends OutputStream {

	public RateLimitedOutputStream(
		OutputStream outputStream, String syncAccountUuid) {

		_rateLimiter = RateLimiter.create(2 * FileUtils.ONE_MB);

		_outputStream = outputStream;
		_syncAccountUuid = syncAccountUuid;

		RateLimiterUtil.registerUploadConnection(_syncAccountUuid, _rateLimiter);
	}

	@Override
	public void close() throws IOException {
		RateLimiterUtil.unregisterUploadConnection(
			_syncAccountUuid, _rateLimiter);

		super.close();
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		_rateLimiter.acquire(bytes.length);

		_outputStream.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int off, int len) throws IOException {
		_rateLimiter.acquire(len);

		_outputStream.write(bytes, off, len);
	}

	@Override
	public void write(int b) throws IOException {
		_rateLimiter.acquire(1);

		_outputStream.write(b);
	}

	private static RateLimiter _rateLimiter;

	private final OutputStream _outputStream;

	private static String _syncAccountUuid;

}