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

package com.liferay.analytics.message.storage.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AnalyticsMessageEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageEntryService
 * @generated
 */
public class AnalyticsMessageEntryServiceWrapper
	implements AnalyticsMessageEntryService,
			   ServiceWrapper<AnalyticsMessageEntryService> {

	public AnalyticsMessageEntryServiceWrapper(
		AnalyticsMessageEntryService analyticsMessageEntryService) {

		_analyticsMessageEntryService = analyticsMessageEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _analyticsMessageEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public AnalyticsMessageEntryService getWrappedService() {
		return _analyticsMessageEntryService;
	}

	@Override
	public void setWrappedService(
		AnalyticsMessageEntryService analyticsMessageEntryService) {

		_analyticsMessageEntryService = analyticsMessageEntryService;
	}

	private AnalyticsMessageEntryService _analyticsMessageEntryService;

}