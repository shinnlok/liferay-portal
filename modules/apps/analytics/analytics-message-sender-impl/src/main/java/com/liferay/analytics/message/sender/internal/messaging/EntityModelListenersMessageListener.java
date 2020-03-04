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

package com.liferay.analytics.message.sender.internal.messaging;

import com.liferay.analytics.message.sender.constants.AnalyticsMessagesDestinationNames;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesProcessorCommand;
import com.liferay.analytics.message.sender.util.EntityModelListenerRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true,
	property = "destination.name=" + AnalyticsMessagesDestinationNames.ANALYTICS_MESSAGES_PROCESSOR,
	service = MessageListener.class
)
public class EntityModelListenersMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) {
		AnalyticsMessagesProcessorCommand analyticsMessagesProcessorCommand =
			(AnalyticsMessagesProcessorCommand)message.get("command");

		if (analyticsMessagesProcessorCommand == null) {
			return;
		}

		if (analyticsMessagesProcessorCommand ==
				AnalyticsMessagesProcessorCommand.DISABLE) {

			if (_log.isInfoEnabled()) {
				_log.info("Disabling entity model listeners...");
			}

			_entityModelListenerRegistry.disableEntityModelListeners();

			if (_log.isInfoEnabled()) {
				_log.info("Entity model listeners were disabled successfully");
			}
		}
		else if (analyticsMessagesProcessorCommand ==
					AnalyticsMessagesProcessorCommand.ENABLE) {

			if (_log.isInfoEnabled()) {
				_log.info("Enabling entity model listeners...");
			}

			_entityModelListenerRegistry.enableEntityModelListeners();

			if (_log.isInfoEnabled()) {
				_log.info("Entity model listeners were enabled successfully");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EntityModelListenersMessageListener.class);

	@Reference
	private EntityModelListenerRegistry _entityModelListenerRegistry;

}