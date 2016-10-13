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

package com.liferay.dxp.cloud.sidebar.api;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	service = SidebarCategoryRegistry.class
)
public class SidebarCategoryRegistry {

	public List<SidebarCategory> getSidebarCategories(
			String sidebarCategoryKey) {

		List<SidebarCategory> sidebarCategories =
			_sidebarCategoryServiceTrackerMap.getService(sidebarCategoryKey);

		if (sidebarCategories == null) {
			return Collections.emptyList();
		}

		return new ArrayList<>(sidebarCategories);
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_sidebarCategoryServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, SidebarCategory.class,
				"(sidebar.category.key=*)",
				new SidebarCategoryServiceReferenceMapper(),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator(
						"sidebar.category.order")));
	}

	@Deactivate
	protected void deactivate() {
		_sidebarCategoryServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SidebarCategoryRegistry.class);

	private ServiceTrackerMap <String, List<SidebarCategory>>
		_sidebarCategoryServiceTrackerMap;

	private class SidebarCategoryServiceReferenceMapper
		implements ServiceReferenceMapper<String, SidebarCategory> {

		@Override
		public void map(
			ServiceReference<SidebarCategory> serviceReference,
			Emitter<String> emitter) {

			String sidebarCategoryKey =
				(String) serviceReference.getProperty("sidebar.category.key");

			if (Validator.isNull(sidebarCategoryKey)) {
				_log.error(
					"Unable to register sidebar category " +
					"because of missing service property " +
					"\"sidebar.category.key\"");
			}
			else {
				emitter.emit(sidebarCategoryKey);
			}
		}
	}


}