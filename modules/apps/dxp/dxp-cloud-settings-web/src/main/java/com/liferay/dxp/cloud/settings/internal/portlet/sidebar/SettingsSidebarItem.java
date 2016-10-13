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
package com.liferay.dxp.cloud.settings.internal.portlet.sidebar;

import com.liferay.dxp.cloud.sidebar.api.SidebarItem;
import com.liferay.dxp.cloud.sidebar.api.category.BottomSidebarCategory;
import com.liferay.dxp.cloud.sidebar.api.category.TopSidebarCategory;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"sidebar.category.key=" + BottomSidebarCategory.KEY,
		"sidebar.item.order:Integer=100"
	},
	service = SidebarItem.class
)
public class SettingsSidebarItem implements SidebarItem {

	@Override
	public String getHref() {
		return "/web/guest/home/dxp/settings";
	}

	@Override
	public String getLabel() {
		return "Settings";
	}

	@Override
	public String getIcon() {
		return "/o/dxp-cloud-sidebar/assets/images/Settings.png";
	}

	@Override
	public String getKey() {
		return "/settings/SettingsHome";
	}

}
