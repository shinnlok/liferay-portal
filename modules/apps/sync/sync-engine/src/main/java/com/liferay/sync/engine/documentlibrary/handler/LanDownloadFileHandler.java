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

import org.apache.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			"Failed to find Lan file" + getLocalSyncFile().getName());

		if (isEventCancelled()) {
			return;
		}

		FileEventUtil.downloadFile(
			getSyncAccountId(), getLocalSyncFile(), true);

//		super.handleException(e);
	}

//	@Override
//	protected void doHandleResponse(HttpResponse httpResponse)
//		throws Exception {
//
//		Header errorHeader = httpResponse.getFirstHeader("Sync-Error");
//
//		if (errorHeader != null) {
//			handleSiteDeactivatedException();
//		}
//
//		final Session session = SessionManager.getSession(getSyncAccountId());
//
//		Header tokenHeader = httpResponse.getFirstHeader("Sync-JWT");
//
//		if (tokenHeader != null) {
//			session.addHeader("Sync-JWT", tokenHeader.getValue());
//		}
//
//		InputStream inputStream = null;
//
//		SyncFile syncFile = getLocalSyncFile();
//
//		if ((syncFile == null) || isUnsynced(syncFile)) {
//			return;
//		}
//
//		Path filePath = Paths.get(syncFile.getFilePathName());
//
//		try {
//			HttpEntity httpEntity = httpResponse.getEntity();
//
//			inputStream = new CountingInputStream(httpEntity.getContent()) {
//
//				@Override
//				protected synchronized void afterRead(int n) {
////					session.incrementDownloadedBytes(n);
//
//					super.afterRead(n);
//				}
//
//			};
//
//			if (httpResponse.getFirstHeader("Accept-Ranges") != null) {
//				copyFile(syncFile, filePath, inputStream, true);
//			}
//			else {
//				copyFile(syncFile, filePath, inputStream, false);
//			}
//		}
//		finally {
//			StreamUtil.cleanUp(inputStream);
//		}
//	}

	@Override
	protected void doHandleResponse(HttpResponse httpResponse)
		throws Exception {

		System.out.println("Found Lan file! " + getLocalSyncFile().getName());

		super.doHandleResponse(httpResponse);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanDownloadFileHandler.class);

}