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
 * The Blob model class for lazy loading the message column in AnalyticsMessage.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessage
 * @generated
 */
public class AnalyticsMessageMessageBlobModel {

	public AnalyticsMessageMessageBlobModel() {
	}

	public AnalyticsMessageMessageBlobModel(long analyticsMessageId) {
		_analyticsMessageId = analyticsMessageId;
	}

	public AnalyticsMessageMessageBlobModel(
		long analyticsMessageId, Blob messageBlob) {

		_analyticsMessageId = analyticsMessageId;
		_messageBlob = messageBlob;
	}

	public long getAnalyticsMessageId() {
		return _analyticsMessageId;
	}

	public void setAnalyticsMessageId(long analyticsMessageId) {
		_analyticsMessageId = analyticsMessageId;
	}

	public Blob getMessageBlob() {
		return _messageBlob;
	}

	public void setMessageBlob(Blob messageBlob) {
		_messageBlob = messageBlob;
	}

	private long _analyticsMessageId;
	private Blob _messageBlob;

}