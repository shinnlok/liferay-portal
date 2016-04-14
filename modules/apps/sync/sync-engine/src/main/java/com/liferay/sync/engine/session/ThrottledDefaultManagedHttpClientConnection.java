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

package com.liferay.sync.engine.session;

import com.liferay.sync.engine.document.library.handler.ThrottledOutputStream;

import java.io.IOException;
import java.io.OutputStream;

import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.conn.DefaultManagedHttpClientConnection;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.util.Args;

/**
 * @author Jonathan McCann
 */
public class ThrottledDefaultManagedHttpClientConnection
	extends DefaultManagedHttpClientConnection {

	public ThrottledDefaultManagedHttpClientConnection(
		String id, int buffersize, int fragmentSizeHint,
		CharsetDecoder chardecoder, CharsetEncoder charencoder,
		MessageConstraints constraints,
		ContentLengthStrategy incomingContentStrategy,
		ContentLengthStrategy outgoingContentStrategy,
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory,
		HttpMessageParserFactory<HttpResponse> responseParserFactory) {

		super(
			id, buffersize, fragmentSizeHint, chardecoder, charencoder,
			constraints, incomingContentStrategy, outgoingContentStrategy,
			requestWriterFactory, responseParserFactory);
	}

	@Override
	public void sendRequestEntity(final HttpEntityEnclosingRequest request)
		throws HttpException, IOException {

		Args.notNull(request, "HTTP request");

		ensureOpen();

		final HttpEntity entity = request.getEntity();

		if (entity == null) {
			return;
		}

		final OutputStream outputStream = prepareOutput(request);

		String syncAccountUuid = request.getHeaders("Sync-UUID")[0].getValue();

		ThrottledOutputStream throttledOutputStream = new ThrottledOutputStream(
			outputStream, syncAccountUuid);

		entity.writeTo(throttledOutputStream);

		outputStream.close();
	}

}