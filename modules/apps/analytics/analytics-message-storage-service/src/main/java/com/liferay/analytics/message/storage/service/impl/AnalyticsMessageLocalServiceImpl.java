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

package com.liferay.analytics.message.storage.service.impl;

import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.analytics.message.storage.service.base.AnalyticsMessageLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the analytics message local service.
 *
 * @author Brian Wing Shun Chan
 * @author Rachael Koestartyo
 */
@Component(
	property = "model.class.name=com.liferay.analytics.message.storage.model.AnalyticsMessage",
	service = AopService.class
)
public class AnalyticsMessageLocalServiceImpl
	extends AnalyticsMessageLocalServiceBaseImpl {

	public AnalyticsMessage addAnalyticsMessage(
			long companyId, long userId, byte[] body)
		throws PortalException {

		long analyticsMessageId = _counterLocalService.increment();

		User user = _userLocalService.getUser(userId);

		AnalyticsMessage analyticsMessage = analyticsMessagePersistence.create(
			analyticsMessageId);

		analyticsMessage.setCompanyId(companyId);
		analyticsMessage.setUserId(userId);
		analyticsMessage.setUserName(user.getFullName());
		analyticsMessage.setCreateDate(new Date());
		analyticsMessage.setBody(
			new OutputBlob(new UnsyncByteArrayInputStream(body), body.length));

		analyticsMessagePersistence.update(analyticsMessage);

		return analyticsMessage;
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private UserLocalService _userLocalService;

}