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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseEntityModelListener<User> {

	@Override
	protected List<String> getAttributeNames() {
		return _attributeNames;
	}

	@Override
	protected User getOriginalModel(User user) throws Exception {
		return userLocalService.getUser(user.getUserId());
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userId";
	}

	@Override
	protected boolean isExcluded(User user) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(
				user.getCompanyId());

		if (analyticsConfiguration.syncAllContacts()) {
			return false;
		}

		try {
			for (long organizationId : user.getOrganizationIds()) {
				if (ArrayUtil.contains(
						analyticsConfiguration.syncedOrganizationIds(),
						String.valueOf(organizationId))) {

					return false;
				}
			}

			for (long userGroupId : user.getUserGroupIds()) {
				if (ArrayUtil.contains(
						analyticsConfiguration.syncedUserGroupIds(),
						String.valueOf(userGroupId))) {

					return false;
				}
			}
		}
		catch (Exception e) {
			return true;
		}

		return true;
	}

	private static final List<String> _attributeNames = Arrays.asList(
		"agreedToTermsOfUse", "comments", "companyId", "contactId",
		"createDate", "defaultUser", "emailAddress", "emailAddressVerified",
		"externalReferenceCode", "facebookId", "firstName", "googleUserId",
		"greeting", "jobTitle", "languageId", "lastName", "ldapServerId",
		"middleName", "openId", "portraitId", "screenName", "status",
		"timeZoneId", "uuid");

}