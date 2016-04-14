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

package com.liferay.sync.engine.documentlibrary.handler;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author Jonathan McCann
 */
public class ThrottledInputStream extends InputStream {

	public ThrottledInputStream(InputStream inputStream) {
		_inputStream = inputStream;
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

	private static final RateLimiter _rateLimiter = RateLimiter.create(
		100 * FileUtils.ONE_KB);

	private final InputStream _inputStream;

}