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

package com.liferay.analytics.message.sender.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rachael Koestartyo
 */
public class AnalyticsMessageUtil {

	public static List<String> getAttributeNames(BaseModel baseModel) {
		if (Objects.equals(
				baseModel.getModelClassName(), Contact.class.getName())) {

			return Arrays.asList(
				"accountId", "birthday", "classNameId", "classPK", "companyId",
				"createDate", "emailAddress", "employeeNumber",
				"employeeStatusId", "facebookSn", "firstName",
				"hoursOfOperation", "jabberSn", "jobClass", "jobTitle",
				"lastName", "male", "middleName", "parentContactId", "prefixId",
				"skypeSn", "smsSn", "suffixId", "twitterSn", "userId",
				"userName");
		}
		else if (Objects.equals(
					baseModel.getModelClassName(), Group.class.getName())) {

			return Arrays.asList(
				"active", "classNameId", "classPK", "companyId",
				"creatorUserId", "description", "descriptionCurrentValue",
				"descriptiveName", "friendlyURL", "groupKey", "inheritContent",
				"liveGroupId", "manualMembership", "membershipRestriction",
				"name", "nameCurrentValue", "parentGroupId",
				"remoteStagingGroupCount", "site", "treePath", "type", "uuid");
		}
		else if (Objects.equals(
					baseModel.getModelClassName(),
					Organization.class.getName())) {

			return Arrays.asList(
				"comments", "companyId", "countryId", "createDate",
				"externalReferenceCode", "logoId", "name",
				"parentOrganizationId", "recursable", "regionId", "statusId",
				"treePath", "type", "userId", "userName", "uuid");
		}
		else if (Objects.equals(
					baseModel.getModelClassName(), UserGroup.class.getName())) {

			return Arrays.asList(
				"addedByLDAPImport", "companyId", "createDate", "description",
				"externalReferenceCode", "name", "parentUserGroupId", "userId",
				"userName", "uuid");
		}
		else if (Objects.equals(
					baseModel.getModelClassName(), User.class.getName())) {

			return Arrays.asList(
				"agreedToTermsOfUse", "comments", "companyId", "contactId",
				"createDate", "defaultUser", "emailAddress",
				"emailAddressVerified", "externalReferenceCode", "facebookId",
				"firstName", "googleUserId", "greeting", "jobTitle",
				"languageId", "lastName", "ldapServerId", "middleName",
				"openId", "portraitId", "screenName", "status", "timeZoneId",
				"uuid");
		}

		return Collections.emptyList();
	}

	public static String getPrimaryKeyName(BaseModel baseModel) {
		if (Objects.equals(
				baseModel.getModelClassName(), Contact.class.getName())) {

			return "contactId";
		}
		else if (Objects.equals(
					baseModel.getModelClassName(), Group.class.getName())) {

			return "groupId";
		}
		else if (Objects.equals(
					baseModel.getModelClassName(),
					Organization.class.getName())) {

			return "organizationId";
		}
		else if (Objects.equals(
					baseModel.getModelClassName(), UserGroup.class.getName())) {

			return "userGroupId";
		}
		else if (Objects.equals(
					baseModel.getModelClassName(), User.class.getName())) {

			return "userId";
		}

		return null;
	}

	public static JSONObject serialize(
		BaseModel baseModel, List<String> includeAttributeNames) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, Object> modelAttributes = baseModel.getModelAttributes();

		for (String includeAttributeName : includeAttributeNames) {
			jsonObject.put(
				includeAttributeName,
				modelAttributes.get(includeAttributeName));
		}

		jsonObject.put(
			getPrimaryKeyName(baseModel),
			String.valueOf(baseModel.getPrimaryKeyObj()));

		return jsonObject;
	}

}