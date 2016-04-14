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
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author Jonathan McCann
 */
public class ThrottledOutputStream extends OutputStream {

	public ThrottledOutputStream(OutputStream outputStream) {
		_outputStream = outputStream;
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

	private static final RateLimiter _rateLimiter = RateLimiter.create(
		100 * FileUtils.ONE_KB);

	private final OutputStream _outputStream;

}