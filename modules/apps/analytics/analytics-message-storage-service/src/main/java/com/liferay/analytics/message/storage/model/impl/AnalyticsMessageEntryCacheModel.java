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

package com.liferay.analytics.message.storage.model.impl;

import com.liferay.analytics.message.storage.model.AnalyticsMessageEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AnalyticsMessageEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnalyticsMessageEntryCacheModel
	implements CacheModel<AnalyticsMessageEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AnalyticsMessageEntryCacheModel)) {
			return false;
		}

		AnalyticsMessageEntryCacheModel analyticsMessageEntryCacheModel =
			(AnalyticsMessageEntryCacheModel)obj;

		if ((analyticsMessageEntryId ==
				analyticsMessageEntryCacheModel.analyticsMessageEntryId) &&
			(mvccVersion == analyticsMessageEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, analyticsMessageEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", analyticsMessageEntryId=");
		sb.append(analyticsMessageEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", destination=");
		sb.append(destination);
		sb.append(", message=");
		sb.append(message);
		sb.append(", retryCount=");
		sb.append(retryCount);
		sb.append(", sentDate=");
		sb.append(sentDate);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AnalyticsMessageEntry toEntityModel() {
		AnalyticsMessageEntryImpl analyticsMessageEntryImpl =
			new AnalyticsMessageEntryImpl();

		analyticsMessageEntryImpl.setMvccVersion(mvccVersion);
		analyticsMessageEntryImpl.setAnalyticsMessageEntryId(
			analyticsMessageEntryId);
		analyticsMessageEntryImpl.setCompanyId(companyId);
		analyticsMessageEntryImpl.setUserId(userId);

		if (userName == null) {
			analyticsMessageEntryImpl.setUserName("");
		}
		else {
			analyticsMessageEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			analyticsMessageEntryImpl.setCreateDate(null);
		}
		else {
			analyticsMessageEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			analyticsMessageEntryImpl.setModifiedDate(null);
		}
		else {
			analyticsMessageEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (destination == null) {
			analyticsMessageEntryImpl.setDestination("");
		}
		else {
			analyticsMessageEntryImpl.setDestination(destination);
		}

		if (message == null) {
			analyticsMessageEntryImpl.setMessage("");
		}
		else {
			analyticsMessageEntryImpl.setMessage(message);
		}

		analyticsMessageEntryImpl.setRetryCount(retryCount);

		if (sentDate == Long.MIN_VALUE) {
			analyticsMessageEntryImpl.setSentDate(null);
		}
		else {
			analyticsMessageEntryImpl.setSentDate(new Date(sentDate));
		}

		analyticsMessageEntryImpl.setStatus(status);

		analyticsMessageEntryImpl.resetOriginalValues();

		return analyticsMessageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		analyticsMessageEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		destination = objectInput.readUTF();
		message = objectInput.readUTF();

		retryCount = objectInput.readInt();
		sentDate = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(analyticsMessageEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (destination == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(destination);
		}

		if (message == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(message);
		}

		objectOutput.writeInt(retryCount);
		objectOutput.writeLong(sentDate);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long analyticsMessageEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String destination;
	public String message;
	public int retryCount;
	public long sentDate;
	public int status;

}