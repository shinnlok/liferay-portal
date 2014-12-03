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

package com.liferay.sync.engine.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class BatchEventUtil {

	public static synchronized BatchEvent getBatchEvent(long syncAccountId) {
		try {
			BatchEvent batchEvent = _batchEvents.get(syncAccountId);

			if ((batchEvent != null) && !batchEvent.isClosed()) {
				return batchEvent;
			}

			batchEvent = new BatchEvent(syncAccountId);

			_batchEvents.put(syncAccountId, batchEvent);

			return batchEvent;
		}
		catch (Exception e) {
			return null;
		}
	}

	private static final Map<Long, BatchEvent> _batchEvents = new HashMap<>();

}