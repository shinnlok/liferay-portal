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

import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;

/**
 * @author Shinn Lok
 */
public class LanDownloadFileHandler extends DownloadFileHandler {

	public LanDownloadFileHandler(Event event) {
		super(event);
	}

	@Override
	public void handleException(Exception e) {
		System.out.println(
			"Failed to find Lan file " + getLocalSyncFile().getName());

		if (isEventCancelled()) {
			return;
		}

		FileEventUtil.downloadFile(
			getSyncAccountId(), getLocalSyncFile(), true, false);
	}

}