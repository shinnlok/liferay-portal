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
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class UserGroupModelListener extends BaseEntityModelListener<UserGroup> {

	@Override
	protected UserGroup getOriginalModel(UserGroup userGroup) throws Exception {
		return _userGroupLocalService.getUserGroup(userGroup.getUserGroupId());
	}

	@Override
	protected boolean isExcluded(UserGroup userGroup) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(
				userGroup.getCompanyId());

		if (!ArrayUtil.contains(
				analyticsConfiguration.syncedUserGroupIds(),
				userGroup.getUserGroupId())) {

			return true;
		}

		return false;
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}