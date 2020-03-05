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
import com.liferay.portal.kernel.util.ArrayUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
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
		return _entityModelListeners.get(className);
	}

	@Override
	public Collection<EntityModelListener> getEntityModelListeners() {
		return _entityModelListeners.values();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "_removeEntityModelListener"
	)
	private void _addEntityModelListener(
		EntityModelListener<?> entityModelListener) {

		_entityModelListeners.put(
			_getParameterizedClassName(entityModelListener.getClass()),
			entityModelListener);
	}

	private String _getParameterizedClassName(Class<?> clazz) {
		ParameterizedType parameterizedType =
			(ParameterizedType)clazz.getGenericSuperclass();

		Type[] types = parameterizedType.getActualTypeArguments();

		Class<?> typeClass = (Class<?>)types[0];

		return typeClass.getName();
	}

	private void _removeEntityModelListener(
		EntityModelListener<?> entityModelListener) {

		_entityModelListeners.remove(
			_getParameterizedClassName(entityModelListener.getClass()),
			entityModelListener);
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

	private final Map<String, EntityModelListener> _entityModelListeners =
		new ConcurrentHashMap<>();

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}