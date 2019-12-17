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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class GroupModelListener extends BaseEntityModelListener<Group> {

	@Override
	protected Group getOriginalModel(Group group) throws Exception {
		return _groupLocalService.getGroup(group.getGroupId());
	}

	@Override
	protected boolean isExcluded(Group group) {
		if (!group.isSite()) {
			return true;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(
				group.getCompanyId());

		if (!ArrayUtil.contains(
				analyticsConfiguration.syncedGroupIds(), group.getGroupId())) {

			return true;
		}

		return false;
	}

	@Reference
	private GroupLocalService _groupLocalService;

}