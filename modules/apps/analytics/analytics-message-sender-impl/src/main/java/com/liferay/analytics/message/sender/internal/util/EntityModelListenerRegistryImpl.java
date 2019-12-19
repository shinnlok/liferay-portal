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

import com.liferay.analytics.message.sender.model.EntityModelListenerHelper;
import com.liferay.analytics.message.sender.util.EntityModelListenerRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.BaseModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
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
	public EntityModelListenerHelper getEntityModelListenerHelper(
		String className) {

		return _serviceTrackerMap.getService(className);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, EntityModelListenerHelper.class, null,
			new EntityModelListenerHelperServiceReferenceMapper());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, EntityModelListenerHelper>
		_serviceTrackerMap;

	private class EntityModelListenerHelperServiceReferenceMapper
		<T extends BaseModel<T>>
			implements ServiceReferenceMapper
				<String, EntityModelListenerHelper<T>> {

		@Override
		public void map(
			ServiceReference<EntityModelListenerHelper<T>> serviceReference,
			Emitter<String> emitter) {

			EntityModelListenerHelper entityModelListenerHelper =
				_bundleContext.getService(serviceReference);

			Class<?> clazz = _getParameterizedClass(
				entityModelListenerHelper.getClass());

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