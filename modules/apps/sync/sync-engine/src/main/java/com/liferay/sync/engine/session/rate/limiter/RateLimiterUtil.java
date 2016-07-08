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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * @author Jonathan McCann
 */
public class RateLimiterUtil {

	public static void registerDownloadConnection(
		long syncAccountId, RateLimiter rateLimiter) {

		List<RateLimiter> rateLimiters = _downloadConnections.get(syncAccountId);

		if (rateLimiters == null) {
			rateLimiters = new ArrayList<>();

			rateLimiters.add(rateLimiter);

			_downloadConnections.put(syncAccountId, rateLimiters);
		}
		else {
			rateLimiters.add(rateLimiter);
		}

		updateDownloadRates(syncAccountId);
	}

	public static void registerUploadConnection(
		String syncAccountUuid, RateLimiter rateLimiter) {

		List<RateLimiter> rateLimiters = _uploadConnections.get(syncAccountUuid);

		if (rateLimiters == null) {
			rateLimiters = new ArrayList<>();

			rateLimiters.add(rateLimiter);

			_uploadConnections.put(syncAccountUuid, rateLimiters);
		}
		else {
			rateLimiters.add(rateLimiter);
		}

		updateUploadRates(syncAccountUuid);
	}

	public static void unregisterDownloadConnection(
		long syncAccountId, RateLimiter rateLimiter) {

		List<RateLimiter> rateLimiters = _downloadConnections.get(syncAccountId);

		rateLimiters.remove(rateLimiter);

		updateDownloadRates(syncAccountId);
	}

	public static void unregisterUploadConnection(
		String syncAccountUuid, RateLimiter rateLimiter) {

		List<RateLimiter> rateLimiters = _uploadConnections.get(syncAccountUuid);

		rateLimiters.remove(rateLimiter);

		updateUploadRates(syncAccountUuid);
	}

	protected static void updateDownloadRates(long syncAccountId) {
		List<RateLimiter> rateLimiters = _downloadConnections.get(syncAccountId);

		double rate = (2 * FileUtils.ONE_MB) / rateLimiters.size();

		for (RateLimiter rateLimiter : rateLimiters) {
			rateLimiter.setRate(rate);
		}
	}

	protected static void updateUploadRates(String syncAccountUuid) {
		List<RateLimiter> rateLimiters = _uploadConnections.get(syncAccountUuid);

		double rate = (2 * FileUtils.ONE_MB) / rateLimiters.size();

		for (RateLimiter rateLimiter : rateLimiters) {
			rateLimiter.setRate(rate);
		}
	}

	private static Map<Long, List<RateLimiter>> _downloadConnections =
		new HashMap<>();
	private static Map<String, List<RateLimiter>> _uploadConnections =
		new HashMap<>();

}