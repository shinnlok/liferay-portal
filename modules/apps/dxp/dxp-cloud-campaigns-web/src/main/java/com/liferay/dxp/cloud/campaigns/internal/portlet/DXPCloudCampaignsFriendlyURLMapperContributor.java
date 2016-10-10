/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.dxp.cloud.campaigns.internal.portlet;

import com.liferay.dxp.cloud.web.friendly.url.mapper.FriendlyURLMapperContributor;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.friendly-url-routes=META-INF/friendly-url-routes/routes-campaigns.xml",
		"javax.portlet.name=dxp_cloud_portlet"
	},
	service = FriendlyURLMapperContributor.class
)
public class DXPCloudCampaignsFriendlyURLMapperContributor implements
	FriendlyURLMapperContributor {
}
