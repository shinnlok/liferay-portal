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

package com.liferay.dxp.cloud.sidebar.internal.context.contributor;

import com.liferay.portal.kernel.template.TemplateContextContributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 *
 */
@Component(
	immediate = true,
	property = {"type=" + TemplateContextContributor.TYPE_GLOBAL},
	service = TemplateContextContributor.class
)
public class SidebarItemsContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects, HttpServletRequest request) {

		Map<String, Object> sidebarItem1 = new HashMap();
		sidebarItem1.put("href", "/web/guest/home/-/dxp/home");
		sidebarItem1.put("icon", "/o/dxp-cloud-sidebar/assets/images/dxp.png");
		sidebarItem1.put("label", "Home");
		sidebarItem1.put("mvcRenderCommandName", "Home");

		Map<String, Object> sidebarItem2 = new HashMap();
		sidebarItem2.put("href", "/web/guest/home/-/dxp/assets");
		sidebarItem2.put("icon", "/o/dxp-cloud-sidebar/assets/images/Assets.png");
		sidebarItem2.put("label", "Assets");
		sidebarItem2.put("mvcRenderCommandName", "/assets/AssetsHome");

		Map<String, Object> sidebarItem3 = new HashMap();
		sidebarItem3.put("href", "/web/guest/home/-/dxp/campaigns");
		sidebarItem3.put("icon", "/o/dxp-cloud-sidebar/assets/images/Campaigns.png");
		sidebarItem3.put("label", "Campaigns");
		sidebarItem3.put("mvcRenderCommandName", "/campaigns/CampaignsHome");

		Map<String, Object> sidebarItem4 = new HashMap();
		sidebarItem4.put("href", "/web/guest/home/-/dxp/contacts");
		sidebarItem4.put("icon", "/o/dxp-cloud-sidebar/assets/images/Contacts.png");
		sidebarItem4.put("label", "Contacts");
		sidebarItem4.put("mvcRenderCommandName", "/contacts/ContactsHome");

		Map<String, Object> sidebarItem5 = new HashMap();
		sidebarItem5.put("href", "/web/guest/home/-/dxp/touchpoints");
		sidebarItem5.put("icon", "/o/dxp-cloud-sidebar/assets/images/Touchpoints.png");
		sidebarItem5.put("label", "TouchPoints");
		sidebarItem5.put("mvcRenderCommandName", "/touchpoints/TouchPointsHome");

		Map<String, Object> sidebarItem6 = new HashMap();
		sidebarItem6.put("href", "/web/guest/home/-/dxp/settings");
		sidebarItem6.put("icon", "/o/dxp-cloud-sidebar/assets/images/Settings.png");
		sidebarItem6.put("label", "Settings");
		sidebarItem6.put("mvcRenderCommandName", "/settings/SettingsHome");

		List<Map<String, Object>> sidebarItems = new ArrayList<>();
		sidebarItems.add(sidebarItem1);
		sidebarItems.add(sidebarItem2);
		sidebarItems.add(sidebarItem3);
		sidebarItems.add(sidebarItem4);
		sidebarItems.add(sidebarItem5);
		sidebarItems.add(sidebarItem6);

		Map<String, Object> sidebar = new HashMap();
		sidebar.put("items", sidebarItems);

		contextObjects.put("sidebar", sidebar);
	}
}