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

import com.liferay.sync.engine.document.library.event.Event;
import com.liferay.sync.engine.document.library.util.FileEventUtil;
import com.liferay.sync.engine.lan.NoSuchSyncLanClientException;
import com.liferay.sync.engine.model.SyncFile;

import org.apache.http.ConnectionClosedException;
import org.apache.http.conn.ConnectTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanDownloadFileHandler extends DownloadFileHandler {

	public LanDownloadFileHandler(Event event) {
		super(event);
	}

	@Override
	public void handleException(Exception e) {
		if (isEventCancelled()) {
			return;
		}

		SyncFile syncFile = getLocalSyncFile();

		if ((e instanceof ConnectTimeoutException) ||
			(e instanceof ConnectionClosedException)) {

			_logger.error(
				"Download exception {} for {}", e, syncFile.getFilePathName());

			FileEventUtil.downloadFile(
				getSyncAccountId(), syncFile, true, true);
		}
		else {
			if (!(e instanceof NoSuchSyncLanClientException)) {
				_logger.error(
					"Download exception {} for {}", e.getMessage(),
					syncFile.getFilePathName());
			}

			FileEventUtil.downloadFile(
				getSyncAccountId(), syncFile, true, false);
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanDownloadFileHandler.class);

}