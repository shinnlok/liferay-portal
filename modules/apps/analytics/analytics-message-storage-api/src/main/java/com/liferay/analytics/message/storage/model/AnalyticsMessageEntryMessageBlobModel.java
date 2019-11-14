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

package com.liferay.analytics.message.storage.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the message column in AnalyticsMessageEntry.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageEntry
 * @generated
 */
public class AnalyticsMessageEntryMessageBlobModel {

	public AnalyticsMessageEntryMessageBlobModel() {
	}

	public AnalyticsMessageEntryMessageBlobModel(long analyticsMessageEntryId) {
		_analyticsMessageEntryId = analyticsMessageEntryId;
	}

	public AnalyticsMessageEntryMessageBlobModel(
		long analyticsMessageEntryId, Blob messageBlob) {

		_analyticsMessageEntryId = analyticsMessageEntryId;
		_messageBlob = messageBlob;
	}

	public long getAnalyticsMessageEntryId() {
		return _analyticsMessageEntryId;
	}

	public void setAnalyticsMessageEntryId(long analyticsMessageEntryId) {
		_analyticsMessageEntryId = analyticsMessageEntryId;
	}

	public Blob getMessageBlob() {
		return _messageBlob;
	}

	public void setMessageBlob(Blob messageBlob) {
		_messageBlob = messageBlob;
	}

	private long _analyticsMessageEntryId;
	private Blob _messageBlob;

}