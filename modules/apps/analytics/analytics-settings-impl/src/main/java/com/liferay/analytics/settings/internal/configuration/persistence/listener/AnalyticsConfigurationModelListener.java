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

import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.analytics.message.sender.util.EntityModelListenerRegistry;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.analytics.settings.internal.security.auth.verifier.AnalyticsSecurityAuthVerifier;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.ContactService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PortalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

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

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(pid);

		boolean syncAllContacts = Boolean.parseBoolean(
			(String)properties.get("syncAllContacts"));

		_syncContacts(
			analyticsConfiguration,
			_analyticsConfigurationTracker.getCompanyId(pid), syncAllContacts);

		if (!syncAllContacts) {
			_syncOrganizations(
				analyticsConfiguration,
				(String[])properties.get("syncedOrganizationIds"));
			_syncUserGroups(
				analyticsConfiguration,
				(String[])properties.get("syncedUserGroupIds"));
		}

		_syncGroups(
			analyticsConfiguration, (String[])properties.get("syncedGroupIds"));
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

	private void _addAnalyticsMessages(List<?> objects) {
		for (Object object : objects) {
			BaseModel<?> baseModel = (BaseModel<?>)object;

			EntityModelListener entityModelListener =
				_entityModelListenerRegistry.getEntityModelListener(
					baseModel.getModelClassName());

			entityModelListener.addAnalyticsMessage(
				false, "update", entityModelListener.getAttributeNames(),
				baseModel);
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
		boolean syncAllContacts) {

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

			_addAnalyticsMessages(users);
		}
	}

	private void _syncGroups(
		AnalyticsConfiguration analyticsConfiguration,
		String[] syncedGroupIds) {

		String[] oldSyncedGroupIds = analyticsConfiguration.syncedGroupIds();

		if (oldSyncedGroupIds != null) {
			Arrays.sort(oldSyncedGroupIds);
		}
		else {
			oldSyncedGroupIds = new String[0];
		}

		if (syncedGroupIds != null) {
			Arrays.sort(syncedGroupIds);
		}

		if (Arrays.equals(oldSyncedGroupIds, syncedGroupIds)) {
			return;
		}

		for (String oldSyncedGroupId : oldSyncedGroupIds) {
			syncedGroupIds = ArrayUtil.remove(syncedGroupIds, oldSyncedGroupId);
		}

		List<Group> groups = new ArrayList<>();

		for (String syncedGroupId : syncedGroupIds) {
			try {
				Group group = _groupLocalService.getGroup(
					GetterUtil.getLong(syncedGroupId));

				groups.add(group);
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to get group " + syncedGroupId);
				}
			}
		}

		if (!groups.isEmpty()) {
			_addAnalyticsMessages(groups);
		}
	}

	private void _syncOrganizations(
		AnalyticsConfiguration analyticsConfiguration,
		String[] syncedOrganizationIds) {

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
						GetterUtil.getLong(syncedOrganizationId));

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
			_addAnalyticsMessages(organizations);
		}

		for (String syncedOrganizationId : syncedOrganizationIds) {
			int count = _userLocalService.getOrganizationUsersCount(
				GetterUtil.getLong(syncedOrganizationId));

			int pages = count / _DEFAULT_DELTA;

			for (int i = 0; i <= pages; i++) {
				int start = i * _DEFAULT_DELTA;

				int end = start + _DEFAULT_DELTA;

				if (end > count) {
					end = count;
				}

				try {
					List<User> users = _userLocalService.getOrganizationUsers(
						GetterUtil.getLong(syncedOrganizationId), start, end);

					_addAnalyticsMessages(users);
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

	private void _syncUserGroups(
		AnalyticsConfiguration analyticsConfiguration,
		String[] syncedUserGroupIds) {

		String[] oldSyncedUserGroupIds =
			analyticsConfiguration.syncedUserGroupIds();

		if (oldSyncedUserGroupIds != null) {
			Arrays.sort(oldSyncedUserGroupIds);
		}
		else {
			oldSyncedUserGroupIds = new String[0];
		}

		if (syncedUserGroupIds != null) {
			Arrays.sort(syncedUserGroupIds);
		}

		if (Arrays.equals(oldSyncedUserGroupIds, syncedUserGroupIds)) {
			return;
		}

		for (String oldSyncedUserGroupId : oldSyncedUserGroupIds) {
			syncedUserGroupIds = ArrayUtil.remove(
				syncedUserGroupIds, oldSyncedUserGroupId);
		}

		List<UserGroup> userGroups = new ArrayList<>();

		for (String syncedUserGroupId : syncedUserGroupIds) {
			try {
				UserGroup userGroup = _userGroupLocalService.getUserGroup(
					GetterUtil.getLong(syncedUserGroupId));

				userGroups.add(userGroup);
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to get user group " + syncedUserGroupId);
				}
			}
		}

		if (!userGroups.isEmpty()) {
			_addAnalyticsMessages(userGroups);
		}

		for (String syncedUserGroupId : syncedUserGroupIds) {
			int count = _userLocalService.getUserGroupUsersCount(
				GetterUtil.getLong(syncedUserGroupId));

			int pages = count / _DEFAULT_DELTA;

			for (int i = 0; i <= pages; i++) {
				int start = i * _DEFAULT_DELTA;

				int end = start + _DEFAULT_DELTA;

				if (end > count) {
					end = count;
				}

				List<User> users = _userLocalService.getUserGroupUsers(
					GetterUtil.getLong(syncedUserGroupId), start, end);

				_addAnalyticsMessages(users);
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
	private EntityModelListenerRegistry _entityModelListenerRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}