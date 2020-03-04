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

package com.liferay.analytics.message.sender.internal.util;

import com.liferay.analytics.message.sender.internal.model.listener.ContactModelListener;
import com.liferay.analytics.message.sender.internal.model.listener.ExpandoColumnModelListener;
import com.liferay.analytics.message.sender.internal.model.listener.ExpandoRowModelListener;
import com.liferay.analytics.message.sender.internal.model.listener.GroupModelListener;
import com.liferay.analytics.message.sender.internal.model.listener.OrganizationModelListener;
import com.liferay.analytics.message.sender.internal.model.listener.RoleModelListener;
import com.liferay.analytics.message.sender.internal.model.listener.TeamModelListener;
import com.liferay.analytics.message.sender.internal.model.listener.UserGroupModelListener;
import com.liferay.analytics.message.sender.internal.model.listener.UserModelListener;
import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.analytics.message.sender.util.EntityModelListenerRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.BaseModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = EntityModelListenerRegistry.class)
public class EntityModelListenerRegistryImpl
	implements EntityModelListenerRegistry {

	@Override
	public void disableEntityModelListeners() {
		for (String className : _entityModelListenersClassNames) {
			_componentContext.disableComponent(className);
		}
	}

	@Override
	public void enableEntityModelListeners() {
		for (String className : _entityModelListenersClassNames) {
			_componentContext.enableComponent(className);
		}
	}

	@Override
	public EntityModelListener getEntityModelListener(String className) {
		return _serviceTrackerMap.getService(className);
	}

	@Override
	public Collection<EntityModelListener> getEntityModelListeners() {
		return _serviceTrackerMap.values();
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, ComponentContext componentContext) {

		_bundleContext = bundleContext;
		_componentContext = componentContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, EntityModelListener.class, null,
			new EntityModelListenerServiceReferenceMapper());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final List<String> _entityModelListenersClassNames =
		Arrays.asList(
			ContactModelListener.class.getName(),
			ExpandoColumnModelListener.class.getName(),
			ExpandoRowModelListener.class.getName(),
			GroupModelListener.class.getName(),
			OrganizationModelListener.class.getName(),
			RoleModelListener.class.getName(),
			TeamModelListener.class.getName(),
			UserGroupModelListener.class.getName(),
			UserModelListener.class.getName());

	private BundleContext _bundleContext;
	private ComponentContext _componentContext;
	private ServiceTrackerMap<String, EntityModelListener> _serviceTrackerMap;

	private class EntityModelListenerServiceReferenceMapper
		<T extends BaseModel<T>>
			implements ServiceReferenceMapper<String, EntityModelListener<T>> {

		@Override
		public void map(
			ServiceReference<EntityModelListener<T>> serviceReference,
			Emitter<String> emitter) {

			EntityModelListener entityModelListener = _bundleContext.getService(
				serviceReference);

			Class<?> clazz = _getParameterizedClass(
				entityModelListener.getClass());

			try {
				emitter.emit(clazz.getName());
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

		private Class<?> _getParameterizedClass(Class<?> clazz) {
			ParameterizedType parameterizedType =
				(ParameterizedType)clazz.getGenericSuperclass();

			Type[] types = parameterizedType.getActualTypeArguments();

			return (Class<?>)types[0];
		}

	}

}