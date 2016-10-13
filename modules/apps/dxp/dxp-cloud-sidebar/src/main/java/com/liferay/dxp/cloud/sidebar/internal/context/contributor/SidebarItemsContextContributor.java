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

import com.liferay.dxp.cloud.sidebar.api.SidebarCategory;
import com.liferay.dxp.cloud.sidebar.api.SidebarCategoryRegistry;
import com.liferay.dxp.cloud.sidebar.api.SidebarItem;
import com.liferay.dxp.cloud.sidebar.api.SidebarItemRegistry;
import com.liferay.portal.kernel.template.TemplateContextContributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		List<SidebarCategory> sidebarCategories =
			_sidebarCategoryRegistry.getSidebarCategories("root");

		List<SidebarItem> sidebarItems = new ArrayList<>();

		for (SidebarCategory sidebarCategory : sidebarCategories) {
			sidebarItems.addAll(
				_sidebarItemsRegistry.getSidebarItems(
					sidebarCategory.getKey()));
		}

		Map<String, Object> sidebar = new HashMap();

		sidebar.put("sidebarItems", sidebarItems);

		contextObjects.put("sidebar", sidebar);
	}

	@Reference(unbind = "-")
	protected void setSidebarCategoryRegistry(
		SidebarCategoryRegistry sidebarCategoryRegistry) {

		_sidebarCategoryRegistry =	sidebarCategoryRegistry;
	}

	@Reference(unbind = "-")
	protected void setSidebarItemsRegistry(
		SidebarItemRegistry sidebarItemsRegistry) {

		_sidebarItemsRegistry =	sidebarItemsRegistry;
	}

	private SidebarCategoryRegistry _sidebarCategoryRegistry;
	private SidebarItemRegistry _sidebarItemsRegistry;

}