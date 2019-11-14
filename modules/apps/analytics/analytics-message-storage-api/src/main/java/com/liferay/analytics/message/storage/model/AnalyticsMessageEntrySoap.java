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

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnalyticsMessageEntrySoap implements Serializable {

	public static AnalyticsMessageEntrySoap toSoapModel(
		AnalyticsMessageEntry model) {

		AnalyticsMessageEntrySoap soapModel = new AnalyticsMessageEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAnalyticsMessageEntryId(
			model.getAnalyticsMessageEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setMessage(model.getMessage());

		return soapModel;
	}

	public static AnalyticsMessageEntrySoap[] toSoapModels(
		AnalyticsMessageEntry[] models) {

		AnalyticsMessageEntrySoap[] soapModels =
			new AnalyticsMessageEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AnalyticsMessageEntrySoap[][] toSoapModels(
		AnalyticsMessageEntry[][] models) {

		AnalyticsMessageEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AnalyticsMessageEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AnalyticsMessageEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AnalyticsMessageEntrySoap[] toSoapModels(
		List<AnalyticsMessageEntry> models) {

		List<AnalyticsMessageEntrySoap> soapModels =
			new ArrayList<AnalyticsMessageEntrySoap>(models.size());

		for (AnalyticsMessageEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AnalyticsMessageEntrySoap[soapModels.size()]);
	}

	public AnalyticsMessageEntrySoap() {
	}

	public long getPrimaryKey() {
		return _analyticsMessageEntryId;
	}

	public void setPrimaryKey(long pk) {
		setAnalyticsMessageEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAnalyticsMessageEntryId() {
		return _analyticsMessageEntryId;
	}

	public void setAnalyticsMessageEntryId(long analyticsMessageEntryId) {
		_analyticsMessageEntryId = analyticsMessageEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Blob getMessage() {
		return _message;
	}

	public void setMessage(Blob message) {
		_message = message;
	}

	private long _mvccVersion;
	private long _analyticsMessageEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Blob _message;

}