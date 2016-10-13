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

package com.liferay.dxp.cloud.web.internal.portlet;

import com.liferay.dxp.cloud.web.friendly.url.mapper.FriendlyURLMapperContributor;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;

import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.util.List;
import java.util.Map;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.friendly-url-routes=META-INF/friendly-url-routes/routes.xml",
		"javax.portlet.name=dxp_cloud_portlet"
	},
	service = FriendlyURLMapper.class
)
public class DXPCloudFriendlyURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public String getMapping() {
		return _MAPPING;
	}

	@Override
	public boolean isCheckMappingWithPrefix() {
		return false;
	}

	@Override
	public void setRouter(Router router) {
		if (this.router != null) {
			return;
		}

		super.setRouter(router);

		_serviceTrackerList.open();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerList = ServiceTrackerListFactory.create(
			bundleContext,
			FriendlyURLMapperContributor.class,
			"(javax.portlet.name=dxp_cloud_portlet)",
			new FriendlyURLMapperContributorServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList _serviceTrackerList;

	protected void unRegisterRouter(String xml) throws Exception {

		// TO DO: Update the router to remove paths
	}

	protected void registerRouter(String xml) throws Exception {
		if (Validator.isNull(xml)) {
			return;
		}

		Document document = UnsecureSAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		List<Element> routeElements = rootElement.elements("route");

		for (Element routeElement : routeElements) {
			String pattern = routeElement.elementText("pattern");

			Route route = router.addRoute(pattern);

			for (Element generatedParameterElement :
					routeElement.elements("generated-parameter")) {

				String name = generatedParameterElement.attributeValue(
					"name");
				String value = generatedParameterElement.getText();

				route.addGeneratedParameter(name, value);
			}

			for (Element ignoredParameterElement :
					routeElement.elements("ignored-parameter")) {

				String name = ignoredParameterElement.attributeValue(
					"name");

				route.addIgnoredParameter(name);
			}

			for (Element implicitParameterElement :
					routeElement.elements("implicit-parameter")) {

				String name = implicitParameterElement.attributeValue(
					"name");
				String value = implicitParameterElement.getText();

				route.addImplicitParameter(name, value);
			}

			for (Element overriddenParameterElement :
					routeElement.elements("overridden-parameter")) {

				String name = overriddenParameterElement.attributeValue(
					"name");
				String value = overriddenParameterElement.getText();

				route.addOverriddenParameter(name, value);
			}
		}
	}

	private class FriendlyURLMapperContributorServiceTrackerCustomizer
		implements
		ServiceTrackerCustomizer<FriendlyURLMapperContributor, FriendlyURLMapperContributor> {

		@Override
		public FriendlyURLMapperContributor addingService(
			ServiceReference<FriendlyURLMapperContributor> serviceReference) {

			FriendlyURLMapperContributor friendlyURLMapperContributor =
				_bundleContext.getService(serviceReference);

			String friendlyURLRoutes = (String)serviceReference.getProperty(
				"com.liferay.portlet.friendly-url-routes");

			if (Validator.isNotNull(friendlyURLRoutes)) {
				Class<?> clazz = friendlyURLMapperContributor.getClass();

				try {
					String xml = getContent(
						clazz.getClassLoader(), friendlyURLRoutes);

					registerRouter(xml);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			return friendlyURLMapperContributor;
		}

		@Override
		public void modifiedService(
			ServiceReference<FriendlyURLMapperContributor> serviceReference,
			FriendlyURLMapperContributor friendlyURLMapperContributor) {

			removedService(serviceReference, friendlyURLMapperContributor);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<FriendlyURLMapperContributor> serviceReference,
			FriendlyURLMapperContributor service) {

			String friendlyURLRoutes = (String)serviceReference.getProperty(
				"com.liferay.portlet.friendly-url-routes");

			if (Validator.isNotNull(friendlyURLRoutes)) {
				Class<?> clazz = service.getClass();

				try {
					String xml = getContent(
						clazz.getClassLoader(), friendlyURLRoutes);

					unRegisterRouter(xml);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		protected String getContent(ClassLoader classLoader, String fileName)
			throws Exception {

			String queryString = HttpUtil.getQueryString(fileName);

			if (Validator.isNull(queryString)) {
				return StringUtil.read(classLoader, fileName);
			}

			int pos = fileName.indexOf(StringPool.QUESTION);

			String xml = StringUtil.read(classLoader, fileName.substring(0, pos));

			Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
				queryString);

			if (parameterMap == null) {
				return xml;
			}

			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				String name = entry.getKey();
				String[] values = entry.getValue();

				if (values.length == 0) {
					continue;
				}

				String value = values[0];

				xml = StringUtil.replace(xml, "@" + name + "@", value);
			}

			return xml;
		}
	}

	private BundleContext _bundleContext;
	private static final String _MAPPING = "dxp";

}