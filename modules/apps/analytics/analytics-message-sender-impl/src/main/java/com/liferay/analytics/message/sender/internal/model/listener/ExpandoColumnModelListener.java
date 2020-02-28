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

import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class ExpandoColumnModelListener
	extends BaseEntityModelListener<ExpandoColumn> {

	@Override
	public List<String> getAttributeNames() {
		return _attributeNames;
	}

	@Override
	public void onBeforeUpdate(ExpandoColumn expandoColumn)
		throws ModelListenerException {

		ExpandoColumn oldExpandoColumn =
			_expandoColumnLocalService.fetchExpandoColumn(
				expandoColumn.getColumnId());

		if (Objects.equals(
				oldExpandoColumn.getName(), expandoColumn.getName()) &&
			Objects.equals(
				oldExpandoColumn.getType(), expandoColumn.getType())) {

			return;
		}

		addAnalyticsMessage("update", getAttributeNames(), expandoColumn);
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			_expandoColumnLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property tableProperty = PropertyFactoryUtil.forName("tableId");

				long organizationClassNameId =
					_classNameLocalService.getClassNameId(
						Organization.class.getName());
				long userClassNameId = _classNameLocalService.getClassNameId(
					User.class.getName());

				dynamicQuery.add(
					tableProperty.in(
						_getTableDynamicQuery(
							organizationClassNameId, userClassNameId,
							ExpandoTableConstants.DEFAULT_TABLE_NAME)));
			});

		return actionableDynamicQuery;
	}

	@Override
	protected ExpandoColumn getModel(long id) throws Exception {
		return _expandoColumnLocalService.getColumn(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "name";
	}

	@Override
	protected boolean isExcluded(ExpandoColumn expandoColumn) {
		if (_isCustomField(Organization.class.getName(), expandoColumn) ||
			_isCustomField(User.class.getName(), expandoColumn)) {

			return false;
		}

		return true;
	}

	@Override
	protected JSONObject serialize(
		List<String> includeAttributeNames, ExpandoColumn expandoColumn) {

		String className = User.class.getName();

		if (_isCustomField(Organization.class.getName(), expandoColumn)) {
			className = Organization.class.getName();
		}

		return JSONUtil.put(
			"className", className
		).put(
			"companyId", expandoColumn.getColumnId()
		).put(
			"dataType",
			ExpandoColumnConstants.getDataType(expandoColumn.getType())
		).put(
			"displayType",
			ExpandoColumnConstants.getDefaultDisplayTypeProperty(
				expandoColumn.getType(),
				expandoColumn.getTypeSettingsProperties())
		).put(
			"name", expandoColumn.getName()
		).put(
			"typeLabel",
			ExpandoColumnConstants.getTypeLabel(expandoColumn.getType())
		);
	}

	private DynamicQuery _getTableDynamicQuery(
		long organizationClassNameId, long userClassNameId, String name) {

		DynamicQuery dynamicQuery = _expandoTableLocalService.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(
			RestrictionsFactoryUtil.or(
				classNameIdProperty.eq(organizationClassNameId),
				classNameIdProperty.eq(userClassNameId)));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.eq(name));

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tableId"));

		return dynamicQuery;
	}

	private boolean _isCustomField(
		String className, ExpandoColumn expandoColumn) {

		long classNameId = _classNameLocalService.getClassNameId(className);

		try {
			ExpandoTable expandoTable = _expandoTableLocalService.getTable(
				expandoColumn.getTableId());

			if (ExpandoTableConstants.DEFAULT_TABLE_NAME.equals(
					expandoTable.getName()) &&
				(expandoTable.getClassNameId() == classNameId)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to get expando table " +
						expandoColumn.getTableId());
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoColumnModelListener.class);

	private static final List<String> _attributeNames = Arrays.asList(
		"className", "companyId", "dataType", "displayType", "name",
		"typeLabel");

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}