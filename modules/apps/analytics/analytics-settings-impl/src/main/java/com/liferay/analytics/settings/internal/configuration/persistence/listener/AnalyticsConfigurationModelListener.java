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

package com.liferay.analytics.settings.internal.configuration.persistence.listener;

import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.analytics.message.sender.util.AnalyticsMessageUtil;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.analytics.settings.internal.security.auth.verifier.AnalyticsSecurityAuthVerifier;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.ContactService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PortalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.analytics.settings.configuration.AnalyticsConfiguration.scoped",
	service = ConfigurationModelListener.class
)
public class AnalyticsConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		if (Validator.isNull((String)properties.get("token"))) {
			_disable((long)properties.get("companyId"));
		}
		else {
			_enable((long)properties.get("companyId"));
		}
	}

	@Override
	public void onBeforeDelete(String pid) {
		long companyId = _analyticsConfigurationTracker.getCompanyId(pid);

		if (companyId == CompanyConstants.SYSTEM) {
			return;
		}

		_disable(companyId);
	}

	@Override
	public void onBeforeSave(
		String pid, Dictionary<String, Object> properties) {

		long companyId = _analyticsConfigurationTracker.getCompanyId(pid);

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(pid);

		String dataSourceId =
			analyticsConfiguration.liferayAnalyticsDataSourceId();

		boolean syncAllContacts = Boolean.valueOf(
			(String)properties.get("syncAllContacts"));

		_syncContacts(
			analyticsConfiguration, companyId, dataSourceId, syncAllContacts);

		if (!syncAllContacts) {
			_syncOrganizations(
				analyticsConfiguration, companyId, dataSourceId,
				(String[])properties.get("syncedOrganizationIds"));
		}
	}

	@Activate
	protected void activate(ComponentContext componentContext)
		throws Exception {

		_componentContext = componentContext;

		if (_hasConfiguration()) {
			_enableAuthVerifier();
		}
	}

	private void _addAnalyticsAdmin(long companyId) throws Exception {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user != null) {
			return;
		}

		Company company = _companyLocalService.getCompany(companyId);

		Role role = _roleLocalService.getRole(
			companyId, "Analytics Administrator");

		user = _userLocalService.addUser(
			0, companyId, true, null, null, false,
			AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN,
			"analytics.administrator@" + company.getMx(), 0, "",
			LocaleUtil.getDefault(), "Analytics", "", "Administrator", 0, 0,
			true, 0, 1, 1970, "", null, null, new long[] {role.getRoleId()},
			null, false, new ServiceContext());

		_userLocalService.updateUser(user);
	}

	private void _addAnalyticsMessages(
		long companyId, String dataSourceId, List<?> objects) {

		for (Object object : objects) {
			BaseModel<?> baseModel = (BaseModel<?>)object;

			JSONObject jsonObject = AnalyticsMessageUtil.serialize(
				baseModel, AnalyticsMessageUtil.getAttributeNames(baseModel));

			try {
				AnalyticsMessage.Builder analyticsMessageBuilder =
					AnalyticsMessage.builder(
						dataSourceId, baseModel.getModelClassName());

				analyticsMessageBuilder.action("update");
				analyticsMessageBuilder.object(jsonObject);

				String analyticsMessageJSON =
					analyticsMessageBuilder.buildJSONString();

				_analyticsMessageLocalService.addAnalyticsMessage(
					companyId, _userLocalService.getDefaultUserId(companyId),
					analyticsMessageJSON.getBytes(Charset.defaultCharset()));
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to add analytics message " +
							jsonObject.toString());
				}
			}
		}
	}

	private void _addSAPEntry(long companyId) throws Exception {
		String sapEntryName = _SAP_ENTRY_OBJECT[0];

		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, sapEntryName);

		if (sapEntry != null) {
			return;
		}

		_sapEntryLocalService.addSAPEntry(
			_userLocalService.getDefaultUserId(companyId), _SAP_ENTRY_OBJECT[1],
			false, true, sapEntryName,
			Collections.singletonMap(LocaleUtil.getDefault(), sapEntryName),
			new ServiceContext());
	}

	private void _deleteAnalyticsAdmin(long companyId) throws Exception {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user != null) {
			_userLocalService.deleteUser(user);
		}
	}

	private void _deleteSAPEntry(long companyId) throws Exception {
		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, AnalyticsSecurityConstants.SERVICE_ACCESS_POLICY_NAME);

		if (sapEntry != null) {
			_sapEntryLocalService.deleteSAPEntry(sapEntry);
		}
	}

	private void _disable(long companyId) {
		try {
			_deleteAnalyticsAdmin(companyId);
			_deleteSAPEntry(companyId);
			_disableAuthVerifier();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _disableAuthVerifier() throws Exception {
		if (!_hasConfiguration() && _authVerifierEnabled) {
			_componentContext.disableComponent(
				AnalyticsSecurityAuthVerifier.class.getName());

			_authVerifierEnabled = false;
		}
	}

	private void _enable(long companyId) {
		try {
			_addAnalyticsAdmin(companyId);
			_addSAPEntry(companyId);
			_enableAuthVerifier();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _enableAuthVerifier() {
		if (!_authVerifierEnabled) {
			_componentContext.enableComponent(
				AnalyticsSecurityAuthVerifier.class.getName());

			_authVerifierEnabled = true;
		}
	}

	private boolean _hasConfiguration() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + AnalyticsConfiguration.class.getName() + "*)");

		if (configurations == null) {
			return false;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (Validator.isNotNull(properties.get("token"))) {
				return true;
			}
		}

		return false;
	}

	private void _syncContacts(
		AnalyticsConfiguration analyticsConfiguration, long companyId,
		String dataSourceId, boolean syncAllContacts) {

		if ((analyticsConfiguration.syncAllContacts() == syncAllContacts) ||
			!syncAllContacts) {

			return;
		}

		int count = _userLocalService.getCompanyUsersCount(companyId);

		int pages = count / _DEFAULT_DELTA;

		for (int i = 0; i <= pages; i++) {
			int start = i * _DEFAULT_DELTA;

			int end = start + _DEFAULT_DELTA;

			if (end > count) {
				end = count;
			}

			List<User> users = _userLocalService.getCompanyUsers(
				companyId, start, end);

			_addAnalyticsMessages(companyId, dataSourceId, users);
		}
	}

	private void _syncOrganizations(
		AnalyticsConfiguration analyticsConfiguration, long companyId,
		String dataSourceId, String[] syncedOrganizationIds) {

		String[] oldSyncedOrganizationIds =
			analyticsConfiguration.syncedOrganizationIds();

		if (oldSyncedOrganizationIds != null) {
			Arrays.sort(oldSyncedOrganizationIds);
		}
		else {
			oldSyncedOrganizationIds = new String[0];
		}

		if (syncedOrganizationIds != null) {
			Arrays.sort(syncedOrganizationIds);
		}

		if (Arrays.equals(oldSyncedOrganizationIds, syncedOrganizationIds)) {
			return;
		}

		for (String oldSyncedOrganizationId : oldSyncedOrganizationIds) {
			syncedOrganizationIds = ArrayUtil.remove(
				syncedOrganizationIds, oldSyncedOrganizationId);
		}

		List<Organization> organizations = new ArrayList<>();

		for (String syncedOrganizationId : syncedOrganizationIds) {
			try {
				Organization organization =
					_organizationLocalService.getOrganization(
						Long.valueOf(syncedOrganizationId));

				organizations.add(organization);
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to get organization " + syncedOrganizationId);
				}
			}
		}

		if (!organizations.isEmpty()) {
			_addAnalyticsMessages(companyId, dataSourceId, organizations);
		}

		for (String syncedOrganizationId : syncedOrganizationIds) {
			int count = _userLocalService.getOrganizationUsersCount(
				Long.valueOf(syncedOrganizationId));

			int pages = count / _DEFAULT_DELTA;

			for (int i = 0; i <= pages; i++) {
				int start = i * _DEFAULT_DELTA;

				int end = start + _DEFAULT_DELTA;

				if (end > count) {
					end = count;
				}

				try {
					List<User> users = _userLocalService.getOrganizationUsers(
						Long.valueOf(syncedOrganizationId), start, end);

					_addAnalyticsMessages(companyId, dataSourceId, users);
				}
				catch (Exception e) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Unable to get organization users for " +
								"organization " + syncedOrganizationId);
					}
				}
			}
		}
	}

	private static final int _DEFAULT_DELTA = 500;

	private static final String[] _SAP_ENTRY_OBJECT = {
		AnalyticsSecurityConstants.SERVICE_ACCESS_POLICY_NAME,
		StringBundler.concat(
			"com.liferay.portal.security.audit.storage.service.",
			"AuditEventService#getAuditEvents\n",
			ContactService.class.getName(), "#getContact\n",
			CompanyService.class.getName(), "#updatePreferences\n",
			GroupService.class.getName(), "#getGroup\n",
			GroupService.class.getName(), "#getGroups\n",
			GroupService.class.getName(), "#getGroupsCount\n",
			GroupService.class.getName(), "#getGtGroups\n",
			OrganizationService.class.getName(), "#fetchOrganization\n",
			OrganizationService.class.getName(), "#getGtOrganizations\n",
			OrganizationService.class.getName(), "#getOrganization\n",
			OrganizationService.class.getName(), "#getOrganizations\n",
			OrganizationService.class.getName(), "#getOrganizationsCount\n",
			OrganizationService.class.getName(), "#getUserOrganizations\n",
			PortalService.class.getName(), "#getBuildNumber\n",
			UserService.class.getName(), "#getCompanyUsers\n",
			UserService.class.getName(), "#getCompanyUsersCount\n",
			UserService.class.getName(), "#getCurrentUser\n",
			UserService.class.getName(), "#getGtCompanyUsers\n",
			UserService.class.getName(), "#getGtOrganizationUsers\n",
			UserService.class.getName(), "#getGtUserGroupUsers\n",
			UserService.class.getName(), "#getOrganizationUsers\n",
			UserService.class.getName(), "#getOrganizationUsersCount\n",
			UserService.class.getName(),
			"#getOrganizationsAndUserGroupsUsersCount\n",
			UserService.class.getName(), "#getUserById\n",
			UserService.class.getName(), "#getUserGroupUsers\n",
			UserGroupService.class.getName(), "#fetchUserGroup\n",
			UserGroupService.class.getName(), "#getGtUserGroups\n",
			UserGroupService.class.getName(), "#getUserGroup\n",
			UserGroupService.class.getName(), "#getUserGroups\n",
			UserGroupService.class.getName(), "#getUserGroupsCount\n",
			UserGroupService.class.getName(), "#getUserUserGroups")
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsConfigurationModelListener.class);

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

	@Reference
	private AnalyticsMessageLocalService _analyticsMessageLocalService;

	private boolean _authVerifierEnabled;

	@Reference
	private CompanyLocalService _companyLocalService;

	private ComponentContext _componentContext;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}