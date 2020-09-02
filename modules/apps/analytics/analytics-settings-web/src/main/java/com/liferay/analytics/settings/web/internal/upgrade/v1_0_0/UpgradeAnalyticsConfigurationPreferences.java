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

package com.liferay.analytics.settings.web.internal.upgrade.v1_0_0;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Rachael Koestartyo
 */
public class UpgradeAnalyticsConfigurationPreferences extends UpgradeProcess {

	public UpgradeAnalyticsConfigurationPreferences(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + AnalyticsConfiguration.class.getName() + "*)");

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				continue;
			}

			if (ArrayUtil.isNotEmpty(
					GetterUtil.getStringValues(
						properties.get("syncedContactFieldNames"))) ||
				ArrayUtil.isNotEmpty(
					GetterUtil.getStringValues(
						properties.get("syncedUserFieldNames")))) {

				continue;
			}

			properties.put("syncedContactFieldNames", _CONTACT_FIELD_NAMES);
			properties.put("syncedUserFieldNames", _USER_FIELD_NAMES);

			configuration.update(properties);
		}
	}

	private static final String[] _CONTACT_FIELD_NAMES = {
		"accountId", "birthday", "classNameId", "classPK", "companyId",
		"contactId", "createDate", "emailAddress", "employeeNumber",
		"employeeStatusId", "facebookSn", "firstName", "hoursOfOperation",
		"jabberSn", "jobClass", "jobTitle", "lastName", "male", "middleName",
		"modifiedDate", "parentContactId", "prefixId", "skypeSn", "smsSn",
		"suffixId", "twitterSn", "userId", "userName"
	};

	private static final String[] _USER_FIELD_NAMES = {
		"agreedToTermsOfUse", "comments", "companyId", "contactId",
		"createDate", "defaultUser", "emailAddress", "emailAddressVerified",
		"externalReferenceCode", "facebookId", "firstName", "googleUserId",
		"greeting", "jobTitle", "languageId", "lastName", "ldapServerId",
		"middleName", "modifiedDate", "openId", "portraitId", "screenName",
		"status", "timeZoneId", "userId", "uuid"
	};

	private final ConfigurationAdmin _configurationAdmin;

}