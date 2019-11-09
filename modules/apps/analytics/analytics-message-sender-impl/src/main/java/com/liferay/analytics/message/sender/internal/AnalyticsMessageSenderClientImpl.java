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

package com.liferay.analytics.message.sender.internal;

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URL;

import java.util.Dictionary;
import java.util.List;
import java.util.Properties;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = AnalyticsMessageSenderClient.class)
public class AnalyticsMessageSenderClientImpl
	implements AnalyticsMessageSenderClient {

	@Override
	public Object send(List<AnalyticsMessage> analyticsMessages, long companyId)
		throws Exception {

		JSONWebServiceClient jsonWebServiceClient = _getJSONWebServiceClient(
			_analyticsConfigurationTracker.getCompanyAnalyticsConfiguration(
				companyId));

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		return jsonWebServiceClient.doPostAsJSON(
			"/dxp-entities", jsonSerializer.serializeDeep(analyticsMessages));
	}

	private JSONWebServiceClient _getJSONWebServiceClient(
		AnalyticsConfiguration analyticsConfiguration) {

		String hostName = null;
		int hostPort = -1;
		String protocol = null;

		try {
			URL url = new URL(analyticsConfiguration.analyticsURL());

			hostName = url.getHost();
			hostPort = url.getPort();
			protocol = url.getProtocol();
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to parse analytics URL");
			}

			return null;
		}

		if (hostPort == -1) {
			if (protocol.equals("https")) {
				hostPort = 443;
			}
			else {
				hostPort = 80;
			}
		}

		Properties properties = new Properties();

		properties.setProperty("hostName", hostName);
		properties.setProperty("hostPort", String.valueOf(hostPort));
		properties.setProperty("protocol", protocol);

		ComponentInstance componentInstance = _componentFactory.newInstance(
			(Dictionary)properties);

		return (JSONWebServiceClient)componentInstance.getInstance();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsMessageSenderClientImpl.class);

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

	@Reference(target = "(component.factory=JSONWebServiceClient)")
	private ComponentFactory _componentFactory;

}