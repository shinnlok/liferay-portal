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

package com.liferay.sync.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sync.constants.SyncAdminPortletKeys;
import com.liferay.sync.model.SyncDevice;
import com.liferay.sync.service.SyncDeviceLocalService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan McCann
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-sync-devices",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=My Sync Devices",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/devices.jsp",
		"javax.portlet.name=" + SyncAdminPortletKeys.SYNC_DEVICES_PORTLET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SyncDevicesPortlet extends MVCPortlet {

	public void deleteDevice(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long syncDeviceId = ParamUtil.getLong(actionRequest, "syncDeviceId");

		_checkSyncDeviceUserId(syncDeviceId, themeDisplay.getUserId());

		_syncDeviceLocalService.deleteSyncDevice(syncDeviceId);
	}

	public void updateDevice(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long syncDeviceId = ParamUtil.getLong(actionRequest, "syncDeviceId");

		_checkSyncDeviceUserId(syncDeviceId, themeDisplay.getUserId());

		int status = ParamUtil.getInteger(actionRequest, "status");

		_syncDeviceLocalService.updateStatus(syncDeviceId, status);
	}

	@Reference(unbind = "-")
	protected void setSyncLocalService(
		SyncDeviceLocalService syncDeviceLocalService) {

		_syncDeviceLocalService = syncDeviceLocalService;
	}

	private void _checkSyncDeviceUserId(long syncDeviceId, long userId)
		throws Exception {

		SyncDevice syncDevice = _syncDeviceLocalService.getSyncDevice(
			syncDeviceId);

		if (userId != syncDevice.getUserId()) {
			throw new PrincipalException();
		}
	}

	private SyncDeviceLocalService _syncDeviceLocalService;

}