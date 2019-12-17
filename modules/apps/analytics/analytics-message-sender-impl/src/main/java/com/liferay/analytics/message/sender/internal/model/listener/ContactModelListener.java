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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ContactLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class ContactModelListener extends BaseEntityModelListener<Contact> {

	@Override
	protected Contact getOriginalModel(Contact contact) throws Exception {
		return _contactLocalService.getContact(contact.getContactId());
	}

	@Override
	protected boolean isExcluded(Contact contact) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(
				contact.getCompanyId());

		if (analyticsConfiguration.syncAllContacts()) {
			return false;
		}

		try {
			User user = userLocalService.getUser(contact.getClassPK());

			return isExcluded(analyticsConfiguration, user);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return true;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContactModelListener.class);

	@Reference
	private ContactLocalService _contactLocalService;

}