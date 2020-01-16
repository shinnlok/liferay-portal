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

package com.liferay.analytics.settings.web.internal.activator;

import com.liferay.analytics.settings.util.AnalyticsUsersManager;
import com.liferay.analytics.settings.util.AnalyticsUsersManagerUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Rachael Koestartyo
 */
public class AnalyticsSettingsWebBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_serviceTracker =
			new ServiceTracker<AnalyticsUsersManager, AnalyticsUsersManager>(
				bundleContext, AnalyticsUsersManager.class, null) {

				@Override
				public AnalyticsUsersManager addingService(
					ServiceReference<AnalyticsUsersManager> serviceReference) {

					AnalyticsUsersManager analyticsUsersManager =
						bundleContext.getService(serviceReference);

					AnalyticsUsersManagerUtil.setAnalyticsUsersManager(
						analyticsUsersManager);

					return analyticsUsersManager;
				}

			};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceTracker.close();
	}

	private ServiceTracker<AnalyticsUsersManager, AnalyticsUsersManager>
		_serviceTracker;

}