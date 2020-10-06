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

package com.liferay.analytics.settings.web.internal.display.context;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.web.internal.constants.AnalyticsSettingsWebKeys;
import com.liferay.analytics.settings.web.internal.model.Field;
import com.liferay.analytics.settings.web.internal.search.FieldChecker;
import com.liferay.analytics.settings.web.internal.search.FieldSearch;
import com.liferay.analytics.settings.web.internal.user.AnalyticsUsersManager;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Rachael Koestartyo
 */
public class FieldDisplayContext {

	public static final String[] RECOMMENDED_CONTACT_FIELD_NAMES = {
		"birthday", "firstName", "jobTitle", "lastName"
	};

	public static final String[] RECOMMENDED_USER_FIELD_NAMES = {
		"firstName", "jobTitle", "lastName", "timeZoneId"
	};

	public static final String[] REQUIRED_CONTACT_FIELD_NAMES = {
		"classPK", "contactId", "createDate", "emailAddress", "modifiedDate"
	};

	public static final String[] REQUIRED_USER_FIELD_NAMES = {
		"createDate", "emailAddress", "modifiedDate", "userId", "uuid"
	};

	public FieldDisplayContext(
		String mvcRenderCommandName, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_mvcRenderCommandName = mvcRenderCommandName;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_analyticsConfiguration =
			(AnalyticsConfiguration)renderRequest.getAttribute(
				AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);
		_analyticsUsersManager =
			(AnalyticsUsersManager)renderRequest.getAttribute(
				AnalyticsSettingsWebKeys.ANALYTICS_USERS_MANAGER);

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_companyId = themeDisplay.getCompanyId();
	}

	public FieldSearch getFieldSearch() {
		FieldSearch fieldSearch = new FieldSearch(
			_renderRequest, getPortletURL());

		List<Field> fields = new ArrayList<>();

		if (StringUtil.equalsIgnoreCase(
				_mvcRenderCommandName,
				"/analytics_settings/edit_synced_contacts_fields")) {

			for (String fieldName : REQUIRED_CONTACT_FIELD_NAMES) {
				fields.add(
					new Field(
						"Default Field", _contactFieldNames.get(fieldName),
						fieldName));
			}

			Set<String> recommendedContactFieldNames = new HashSet<>();

			Set<String> syncedContactFieldNames = SetUtil.fromArray(
				_analyticsConfiguration.syncedContactFieldNames());

			if (syncedContactFieldNames.size() <=
					REQUIRED_CONTACT_FIELD_NAMES.length) {

				recommendedContactFieldNames = SetUtil.fromArray(
					RECOMMENDED_CONTACT_FIELD_NAMES);

				for (String fieldName : RECOMMENDED_CONTACT_FIELD_NAMES) {
					fields.add(
						new Field(
							"Default Field", _contactFieldNames.get(fieldName),
							fieldName));
				}
			}

			for (Map.Entry<String, String> entry :
					_contactFieldNames.entrySet()) {

				if (ArrayUtil.contains(
						REQUIRED_CONTACT_FIELD_NAMES, entry.getKey()) ||
					recommendedContactFieldNames.contains(entry.getKey())) {

					continue;
				}

				fields.add(
					new Field(
						"Default Field", entry.getValue(), entry.getKey()));
			}

			fieldSearch.setRowChecker(
				new FieldChecker(
					_mvcRenderCommandName, _renderResponse,
					recommendedContactFieldNames,
					SetUtil.fromArray(REQUIRED_CONTACT_FIELD_NAMES),
					syncedContactFieldNames));
			fieldSearch.setTotal(
				_contactFieldNames.size() -
					REQUIRED_CONTACT_FIELD_NAMES.length);
		}
		else if (StringUtil.equalsIgnoreCase(
					_mvcRenderCommandName,
					"/analytics_settings/edit_synced_users_fields")) {

			for (String fieldName : REQUIRED_USER_FIELD_NAMES) {
				fields.add(
					new Field(
						"Default Field", _userFieldNames.get(fieldName),
						fieldName));
			}

			Set<String> recommendedUserFieldNames = new HashSet<>();

			Set<String> syncedUserFieldNames = SetUtil.fromArray(
				_analyticsConfiguration.syncedUserFieldNames());

			if (syncedUserFieldNames.size() <=
					RECOMMENDED_USER_FIELD_NAMES.length) {

				recommendedUserFieldNames = SetUtil.fromArray(
					RECOMMENDED_USER_FIELD_NAMES);

				for (String fieldName : RECOMMENDED_USER_FIELD_NAMES) {
					fields.add(
						new Field(
							"Default Field", _userFieldNames.get(fieldName),
							fieldName));
				}
			}

			for (Map.Entry<String, String> entry : _userFieldNames.entrySet()) {
				if (ArrayUtil.contains(
						REQUIRED_USER_FIELD_NAMES, entry.getKey()) ||
					recommendedUserFieldNames.contains(entry.getKey())) {

					continue;
				}

				fields.add(
					new Field(
						"Default Field", entry.getValue(), entry.getKey()));
			}

			Map<String, String> userCustomFieldNames =
				_getUserCustomFieldNames();

			for (Map.Entry<String, String> entry :
					userCustomFieldNames.entrySet()) {

				fields.add(
					new Field(
						"Custom Field", entry.getValue(), entry.getKey()));
			}

			fieldSearch.setRowChecker(
				new FieldChecker(
					_mvcRenderCommandName, _renderResponse,
					recommendedUserFieldNames,
					SetUtil.fromArray(REQUIRED_USER_FIELD_NAMES),
					syncedUserFieldNames));
			fieldSearch.setTotal(
				_userFieldNames.size() + userCustomFieldNames.size() -
					REQUIRED_USER_FIELD_NAMES.length);
		}

		fieldSearch.setResults(fields);

		return fieldSearch;
	}

	public String getMVCRenderCommandName() {
		return _mvcRenderCommandName;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", _mvcRenderCommandName);

		return portletURL;
	}

	private String _getDataType(int type) {
		if ((type == ExpandoColumnConstants.BOOLEAN) ||
			(type == ExpandoColumnConstants.BOOLEAN_ARRAY)) {

			return "Boolean";
		}
		else if ((type == ExpandoColumnConstants.DATE) ||
				 (type == ExpandoColumnConstants.DATE_ARRAY)) {

			return "Date";
		}
		else if ((type == ExpandoColumnConstants.DOUBLE) ||
				 (type == ExpandoColumnConstants.DOUBLE_ARRAY) ||
				 (type == ExpandoColumnConstants.FLOAT) ||
				 (type == ExpandoColumnConstants.FLOAT_ARRAY)) {

			return "Decimal";
		}
		else if ((type == ExpandoColumnConstants.INTEGER) ||
				 (type == ExpandoColumnConstants.INTEGER_ARRAY)) {

			return "Integer";
		}
		else if ((type == ExpandoColumnConstants.LONG) ||
				 (type == ExpandoColumnConstants.LONG_ARRAY)) {

			return "Long";
		}
		else if ((type == ExpandoColumnConstants.NUMBER) ||
				 (type == ExpandoColumnConstants.NUMBER_ARRAY) ||
				 (type == ExpandoColumnConstants.SHORT) ||
				 (type == ExpandoColumnConstants.SHORT_ARRAY)) {

			return "Number";
		}

		return "String";
	}

	private Map<String, String> _getUserCustomFieldNames() {
		Map<String, String> userCustomFieldNames = new TreeMap<>();

		for (ExpandoColumn expandoColumn :
				_analyticsUsersManager.getUserExpandoColumns(_companyId)) {

			userCustomFieldNames.put(
				expandoColumn.getName(), _getDataType(expandoColumn.getType()));
		}

		return userCustomFieldNames;
	}

	private static final Map<String, String> _contactFieldNames =
		TreeMapBuilder.put(
			"accountId", "Long"
		).put(
			"birthday", "Date"
		).put(
			"classNameId", "Long"
		).put(
			"classPK", "Long"
		).put(
			"companyId", "Long"
		).put(
			"contactId", "Long"
		).put(
			"createDate", "Date"
		).put(
			"emailAddress", "String"
		).put(
			"employeeNumber", "String"
		).put(
			"employeeStatusId", "String"
		).put(
			"facebookSn", "String"
		).put(
			"firstName", "String"
		).put(
			"hoursOfOperation", "String"
		).put(
			"jabberSn", "String"
		).put(
			"jobClass", "String"
		).put(
			"jobTitle", "String"
		).put(
			"lastName", "String"
		).put(
			"male", "Boolean"
		).put(
			"middleName", "String"
		).put(
			"modifiedDate", "Date"
		).put(
			"parentContactId", "Long"
		).put(
			"prefixId", "Long"
		).put(
			"skypeSn", "String"
		).put(
			"smsSn", "String"
		).put(
			"suffixId", "Long"
		).put(
			"twitterSn", "String"
		).build();
	private static final Map<String, String> _userFieldNames =
		TreeMapBuilder.put(
			"agreedToTermsOfUse", "Boolean"
		).put(
			"comments", "String"
		).put(
			"companyId", "Long"
		).put(
			"contactId", "Long"
		).put(
			"createDate", "Date"
		).put(
			"defaultUser", "Boolean"
		).put(
			"emailAddress", "String"
		).put(
			"emailAddressVerified", "Boolean"
		).put(
			"externalReferenceCode", "String"
		).put(
			"facebookId", "Long"
		).put(
			"firstName", "String"
		).put(
			"googleUserId", "String"
		).put(
			"greeting", "String"
		).put(
			"jobTitle", "String"
		).put(
			"languageId", "String"
		).put(
			"lastName", "String"
		).put(
			"ldapServerId", "Long"
		).put(
			"middleName", "String"
		).put(
			"modifiedDate", "Date"
		).put(
			"openId", "String"
		).put(
			"portraitId", "Long"
		).put(
			"screenName", "String"
		).put(
			"status", "Integer"
		).put(
			"timeZoneId", "String"
		).put(
			"userId", "Long"
		).put(
			"uuid", "String"
		).build();

	private final AnalyticsConfiguration _analyticsConfiguration;
	private final AnalyticsUsersManager _analyticsUsersManager;
	private final long _companyId;
	private final String _mvcRenderCommandName;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}