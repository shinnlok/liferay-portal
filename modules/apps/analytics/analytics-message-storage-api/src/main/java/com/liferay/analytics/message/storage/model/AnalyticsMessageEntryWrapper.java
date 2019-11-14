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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.sql.Blob;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AnalyticsMessageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageEntry
 * @generated
 */
public class AnalyticsMessageEntryWrapper
	extends BaseModelWrapper<AnalyticsMessageEntry>
	implements AnalyticsMessageEntry, ModelWrapper<AnalyticsMessageEntry> {

	public AnalyticsMessageEntryWrapper(
		AnalyticsMessageEntry analyticsMessageEntry) {

		super(analyticsMessageEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("analyticsMessageEntryId", getAnalyticsMessageEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("message", getMessage());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long analyticsMessageEntryId = (Long)attributes.get(
			"analyticsMessageEntryId");

		if (analyticsMessageEntryId != null) {
			setAnalyticsMessageEntryId(analyticsMessageEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Blob message = (Blob)attributes.get("message");

		if (message != null) {
			setMessage(message);
		}
	}

	/**
	 * Returns the analytics message entry ID of this analytics message entry.
	 *
	 * @return the analytics message entry ID of this analytics message entry
	 */
	@Override
	public long getAnalyticsMessageEntryId() {
		return model.getAnalyticsMessageEntryId();
	}

	/**
	 * Returns the company ID of this analytics message entry.
	 *
	 * @return the company ID of this analytics message entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this analytics message entry.
	 *
	 * @return the create date of this analytics message entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the message of this analytics message entry.
	 *
	 * @return the message of this analytics message entry
	 */
	@Override
	public Blob getMessage() {
		return model.getMessage();
	}

	/**
	 * Returns the mvcc version of this analytics message entry.
	 *
	 * @return the mvcc version of this analytics message entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this analytics message entry.
	 *
	 * @return the primary key of this analytics message entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this analytics message entry.
	 *
	 * @return the user ID of this analytics message entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this analytics message entry.
	 *
	 * @return the user name of this analytics message entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this analytics message entry.
	 *
	 * @return the user uuid of this analytics message entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a analytics message entry model instance should use the <code>AnalyticsMessageEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the analytics message entry ID of this analytics message entry.
	 *
	 * @param analyticsMessageEntryId the analytics message entry ID of this analytics message entry
	 */
	@Override
	public void setAnalyticsMessageEntryId(long analyticsMessageEntryId) {
		model.setAnalyticsMessageEntryId(analyticsMessageEntryId);
	}

	/**
	 * Sets the company ID of this analytics message entry.
	 *
	 * @param companyId the company ID of this analytics message entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this analytics message entry.
	 *
	 * @param createDate the create date of this analytics message entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the message of this analytics message entry.
	 *
	 * @param message the message of this analytics message entry
	 */
	@Override
	public void setMessage(Blob message) {
		model.setMessage(message);
	}

	/**
	 * Sets the mvcc version of this analytics message entry.
	 *
	 * @param mvccVersion the mvcc version of this analytics message entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this analytics message entry.
	 *
	 * @param primaryKey the primary key of this analytics message entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this analytics message entry.
	 *
	 * @param userId the user ID of this analytics message entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this analytics message entry.
	 *
	 * @param userName the user name of this analytics message entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this analytics message entry.
	 *
	 * @param userUuid the user uuid of this analytics message entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AnalyticsMessageEntryWrapper wrap(
		AnalyticsMessageEntry analyticsMessageEntry) {

		return new AnalyticsMessageEntryWrapper(analyticsMessageEntry);
	}

}