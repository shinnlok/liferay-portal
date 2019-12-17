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
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class OrganizationModelListener
	extends BaseEntityModelListener<Organization> {

	@Override
	protected Organization getOriginalModel(Organization organization)
		throws Exception {

		return _organizationLocalService.getOrganization(
			organization.getOrganizationId());
	}

	@Override
	protected boolean isExcluded(Organization organization) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(
				organization.getCompanyId());

		if (!ArrayUtil.contains(
				analyticsConfiguration.syncedOrganizationIds(),
				organization.getOrganizationId())) {

			return true;
		}

		return false;
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

}