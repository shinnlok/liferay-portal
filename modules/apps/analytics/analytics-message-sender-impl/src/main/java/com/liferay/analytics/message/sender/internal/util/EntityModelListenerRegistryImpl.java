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

import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.analytics.message.sender.util.EntityModelListenerRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Collection;
import java.util.function.Function;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = EntityModelListenerRegistry.class)
public class EntityModelListenerRegistryImpl
	implements EntityModelListenerRegistry {

	@Override
	public void disableEntityModelListeners() {
		_toggleEntityModelListeners(_serviceComponentRuntime::disableComponent);
	}

	@Override
	public void enableEntityModelListeners() {
		_toggleEntityModelListeners(_serviceComponentRuntime::enableComponent);
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

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, EntityModelListener.class, null,
			new EntityModelListenerServiceReferenceMapper());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _toggleEntityModelListeners(
		Function<ComponentDescriptionDTO, Object> function) {

		for (ComponentDescriptionDTO componentDescriptionDTO :
				_serviceComponentRuntime.getComponentDescriptionDTOs()) {

			if (ArrayUtil.contains(
					componentDescriptionDTO.serviceInterfaces,
					EntityModelListener.class.getName())) {

				function.apply(componentDescriptionDTO);
			}
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

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