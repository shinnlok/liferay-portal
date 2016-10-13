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

package com.liferay.dxp.cloud.sidebar.api.category;

import com.liferay.dxp.cloud.sidebar.api.SidebarCategory;
import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"sidebar.category.key=root",
		"sidebar.category.order:Integer=100"
	},
	service = SidebarCategory.class
)
public class TopSidebarCategory implements SidebarCategory {

	public static final String KEY = "top";

	@Override
	public String getKey() {
		return KEY;
	}

}