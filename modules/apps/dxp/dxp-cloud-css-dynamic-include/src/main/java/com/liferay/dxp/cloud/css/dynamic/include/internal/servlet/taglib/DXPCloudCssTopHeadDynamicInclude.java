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

package com.liferay.dxp.cloud.css.dynamic.include.internal.servlet.taglib;

import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = DynamicInclude.class)
public class DXPCloudCssTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PrintWriter printWriter = response.getWriter();

		StringBundler sb = new StringBundler(6);

		sb.append("<link href=\"");
		sb.append(themeDisplay.getPortalURL());
		sb.append(PortalUtil.getPathProxy());
		sb.append("/o/dxp-cloud-web");
		sb.append("/assets/css/dxpweb.css\" rel=\"stylesheet\" type = ");
		sb.append("\"text/css\" />");

		sb.append("<link href=\"");
		sb.append(themeDisplay.getPortalURL());
		sb.append(PortalUtil.getPathProxy());
		sb.append("/o/dxp-cloud-sidebar");
		sb.append("/assets/css/sidebar.css\" rel=\"stylesheet\" type = ");
		sb.append("\"text/css\" />");

		sb.append("<link href=\"");
		sb.append(themeDisplay.getPortalURL());
		sb.append(PortalUtil.getPathProxy());
		sb.append("/o/dxp-cloud-topbar");
		sb.append("/assets/css/topbar.css\" rel=\"stylesheet\" type = ");
		sb.append("\"text/css\" />");

		printWriter.println(sb.toString());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}
}