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

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.analytics.message.sender.util.UserSerializer;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
public abstract class BaseEntityModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	public abstract String getObjectType();

	protected Set<String> getModifiedAttributes(
		List<String> attributeNames, Object newObject, Object oldObject) {

		Set<String> modifiedAttributes = new HashSet<>();

		for (String attributeName : attributeNames) {
			String newValue = String.valueOf(
				BeanPropertiesUtil.getObject(newObject, attributeName));
			String oldValue = String.valueOf(
				BeanPropertiesUtil.getObject(oldObject, attributeName));

			if (!Objects.equals(newValue, oldValue)) {
				modifiedAttributes.add(attributeName);
			}
		}

		return modifiedAttributes;
	}

	protected void send(String eventType, Object object) {
		try {
			AnalyticsMessage.Builder analyticsMessageBuilder =
				AnalyticsMessage.builder(_getDataSourceId(), getObjectType());

			analyticsMessageBuilder.action(eventType);
			analyticsMessageBuilder.object(serialize(object));

			_analyticsMessageSenderClient.send(
				Collections.singletonList(analyticsMessageBuilder.build()));
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to send analytics message " + serialize(object));
			}
		}
	}

	protected String serialize(Object object) {
		if (object instanceof User) {
			return _userSerializer.serialize((User)object);
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		if (object instanceof Contact) {
			Contact contact = (Contact)object;

			try {
				JSONObject userJSONObject = JSONFactoryUtil.createJSONObject(
					_userSerializer.serialize(
						_userLocalService.getUser(contact.getUserId())));

				userJSONObject.put(
					"contact",
					JSONFactoryUtil.createJSONObject(
						jsonSerializer.serialize(contact)));

				return userJSONObject.toString();
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to serialize contact");
				}
			}
		}

		return jsonSerializer.serialize(object);
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	private String _getDataSourceId() {
		try {
			return String.valueOf(_getProperty("dataSourceId"));
		}
		catch (Exception e) {
			return null;
		}
	}

	private Object _getProperty(String key) throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			AnalyticsConfiguration.class.getName(), "?");

		Dictionary<String, Object> properties = configuration.getProperties();

		return properties.get(key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEntityModelListener.class);

	@Reference
	private AnalyticsMessageSenderClient _analyticsMessageSenderClient;

	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserSerializer _userSerializer;

}