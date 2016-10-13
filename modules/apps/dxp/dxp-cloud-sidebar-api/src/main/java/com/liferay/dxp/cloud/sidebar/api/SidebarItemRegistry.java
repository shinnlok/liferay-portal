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

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Julio Camarero
 */
@Component(immediate = true, service = SidebarItemRegistry.class)
public class SidebarItemRegistry {

	public List<SidebarItem> getSidebarItems(String sidebarCategoryKey) {
		List<SidebarItem> sidebarItems = _serviceTrackerMap.getService(
				sidebarCategoryKey);

		if (sidebarItems == null) {
			return Collections.emptyList();
		}

		return sidebarItems;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SidebarItem.class,
			"(sidebar.category.key=*)",
			new SidebarItemServiceReferenceMapper(),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator(
					"sidebar.item.order")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SidebarItemRegistry.class);

	private ServiceTrackerMap<String, List<SidebarItem>> _serviceTrackerMap;

	private class SidebarItemServiceReferenceMapper
		implements ServiceReferenceMapper<String, SidebarItem> {

		@Override
		public void map(
			ServiceReference<SidebarItem> serviceReference,
			Emitter<String> emitter) {

			String sidebarCategoryKey =
				(String)serviceReference.getProperty(
					"sidebar.category.key");

			if (Validator.isNull(sidebarCategoryKey)) {
				_log.error(
					"Unable to register sidebar item " +
						"because of missing service property " +
							"\"sidebar.category.key\"");
			}
			else {
				emitter.emit(sidebarCategoryKey);
			}
		}
	}
}
