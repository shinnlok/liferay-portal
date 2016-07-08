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

import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan McCann
 */
public class RateLimiterManager {

	public static synchronized RateLimiter getDownloadRateLimiter(
		long syncAccountId) {

		RateLimiter rateLimiter = getRateLimiter(
			syncAccountId, _downloadRateLimiters);

		updateDownloadRateLimits(syncAccountId);

		return rateLimiter;
	}

	public static synchronized RateLimiter getUploadRateLimiter(
		long syncAccountId) {

		RateLimiter rateLimiter = getRateLimiter(
			syncAccountId, _uploadRateLimiters);

		updateUploadRateLimits(syncAccountId);

		return rateLimiter;
	}

	public static synchronized void removeDownloadRateLimiter(
		long syncAccountId, RateLimiter rateLimiter) {

		List<RateLimiter> rateLimiters = _downloadRateLimiters.get(
			syncAccountId);

		rateLimiters.remove(rateLimiter);

		updateDownloadRateLimits(syncAccountId);
	}

	public static synchronized void removeUploadRateLimiter(
		long syncAccountId, RateLimiter rateLimiter) {

		List<RateLimiter> rateLimiters = _uploadRateLimiters.get(syncAccountId);

		rateLimiters.remove(rateLimiter);

		updateUploadRateLimits(syncAccountId);
	}

	protected static RateLimiter getRateLimiter(
		long syncAccountId, Map<Long, List<RateLimiter>> rateLimiterMap) {

		RateLimiter rateLimiter = RateLimiter.create(1);

		List<RateLimiter> rateLimiters = rateLimiterMap.get(syncAccountId);

		if (rateLimiters == null) {
			rateLimiters = new ArrayList<>();

			rateLimiters.add(rateLimiter);

			rateLimiterMap.put(syncAccountId, rateLimiters);
		}
		else {
			rateLimiters.add(rateLimiter);
		}

		return rateLimiter;
	}

	protected static void updateDownloadRateLimits(long syncAccountId) {
		List<RateLimiter> rateLimiters = _downloadRateLimiters.get(
			syncAccountId);

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		updateRateLimits(rateLimiters, syncAccount.getMaxDownloadRate());
	}

	protected static void updateRateLimits(
		List<RateLimiter> rateLimiters, int maxRate) {

		if (rateLimiters.isEmpty()) {
			return;
		}

		int rate = 0;

		if (maxRate <= 0) {
			rate = Integer.MAX_VALUE;
		}
		else {
			rate = maxRate / rateLimiters.size();
		}

		for (RateLimiter rateLimiter : rateLimiters) {
			rateLimiter.setRate(rate);
		}
	}

	protected static void updateUploadRateLimits(long syncAccountId) {
		List<RateLimiter> rateLimiters = _uploadRateLimiters.get(syncAccountId);

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		updateRateLimits(rateLimiters, syncAccount.getMaxUploadRate());
	}

	private static final Map<Long, List<RateLimiter>> _downloadRateLimiters =
		new HashMap<>();
	private static final Map<Long, List<RateLimiter>> _uploadRateLimiters =
		new HashMap<>();

}