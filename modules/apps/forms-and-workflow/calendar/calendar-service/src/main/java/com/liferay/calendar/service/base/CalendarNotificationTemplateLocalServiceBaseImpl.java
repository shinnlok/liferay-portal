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

package com.liferay.calendar.service.base;

import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.service.CalendarNotificationTemplateLocalService;
import com.liferay.calendar.service.persistence.CalendarBookingFinder;
import com.liferay.calendar.service.persistence.CalendarBookingPersistence;
import com.liferay.calendar.service.persistence.CalendarFinder;
import com.liferay.calendar.service.persistence.CalendarNotificationTemplatePersistence;
import com.liferay.calendar.service.persistence.CalendarPersistence;
import com.liferay.calendar.service.persistence.CalendarResourceFinder;
import com.liferay.calendar.service.persistence.CalendarResourcePersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the calendar notification template local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.calendar.service.impl.CalendarNotificationTemplateLocalServiceImpl}.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see com.liferay.calendar.service.impl.CalendarNotificationTemplateLocalServiceImpl
 * @see com.liferay.calendar.service.CalendarNotificationTemplateLocalServiceUtil
 * @generated
 */
public abstract class CalendarNotificationTemplateLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CalendarNotificationTemplateLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.calendar.service.CalendarNotificationTemplateLocalServiceUtil} to access the calendar notification template local service.
	 */

	/**
	 * Adds the calendar notification template to the database. Also notifies the appropriate model listeners.
	 *
	 * @param calendarNotificationTemplate the calendar notification template
	 * @return the calendar notification template that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CalendarNotificationTemplate addCalendarNotificationTemplate(
		CalendarNotificationTemplate calendarNotificationTemplate) {
		calendarNotificationTemplate.setNew(true);

		return calendarNotificationTemplatePersistence.update(calendarNotificationTemplate);
	}

	/**
	 * Creates a new calendar notification template with the primary key. Does not add the calendar notification template to the database.
	 *
	 * @param calendarNotificationTemplateId the primary key for the new calendar notification template
	 * @return the new calendar notification template
	 */
	@Override
	public CalendarNotificationTemplate createCalendarNotificationTemplate(
		long calendarNotificationTemplateId) {
		return calendarNotificationTemplatePersistence.create(calendarNotificationTemplateId);
	}

	/**
	 * Deletes the calendar notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param calendarNotificationTemplateId the primary key of the calendar notification template
	 * @return the calendar notification template that was removed
	 * @throws PortalException if a calendar notification template with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CalendarNotificationTemplate deleteCalendarNotificationTemplate(
		long calendarNotificationTemplateId) throws PortalException {
		return calendarNotificationTemplatePersistence.remove(calendarNotificationTemplateId);
	}

	/**
	 * Deletes the calendar notification template from the database. Also notifies the appropriate model listeners.
	 *
	 * @param calendarNotificationTemplate the calendar notification template
	 * @return the calendar notification template that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CalendarNotificationTemplate deleteCalendarNotificationTemplate(
		CalendarNotificationTemplate calendarNotificationTemplate) {
		return calendarNotificationTemplatePersistence.remove(calendarNotificationTemplate);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(CalendarNotificationTemplate.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return calendarNotificationTemplatePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.calendar.model.impl.CalendarNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return calendarNotificationTemplatePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.calendar.model.impl.CalendarNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return calendarNotificationTemplatePersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return calendarNotificationTemplatePersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return calendarNotificationTemplatePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public CalendarNotificationTemplate fetchCalendarNotificationTemplate(
		long calendarNotificationTemplateId) {
		return calendarNotificationTemplatePersistence.fetchByPrimaryKey(calendarNotificationTemplateId);
	}

	/**
	 * Returns the calendar notification template matching the UUID and group.
	 *
	 * @param uuid the calendar notification template's UUID
	 * @param groupId the primary key of the group
	 * @return the matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchCalendarNotificationTemplateByUuidAndGroupId(
		String uuid, long groupId) {
		return calendarNotificationTemplatePersistence.fetchByUUID_G(uuid,
			groupId);
	}

	/**
	 * Returns the calendar notification template with the primary key.
	 *
	 * @param calendarNotificationTemplateId the primary key of the calendar notification template
	 * @return the calendar notification template
	 * @throws PortalException if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate getCalendarNotificationTemplate(
		long calendarNotificationTemplateId) throws PortalException {
		return calendarNotificationTemplatePersistence.findByPrimaryKey(calendarNotificationTemplateId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(calendarNotificationTemplateLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CalendarNotificationTemplate.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"calendarNotificationTemplateId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(calendarNotificationTemplateLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CalendarNotificationTemplate.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"calendarNotificationTemplateId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(calendarNotificationTemplateLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CalendarNotificationTemplate.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"calendarNotificationTemplateId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CalendarNotificationTemplate>() {
				@Override
				public void performAction(
					CalendarNotificationTemplate calendarNotificationTemplate)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						calendarNotificationTemplate);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(
					CalendarNotificationTemplate.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return calendarNotificationTemplateLocalService.deleteCalendarNotificationTemplate((CalendarNotificationTemplate)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return calendarNotificationTemplatePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the calendar notification templates matching the UUID and company.
	 *
	 * @param uuid the UUID of the calendar notification templates
	 * @param companyId the primary key of the company
	 * @return the matching calendar notification templates, or an empty list if no matches were found
	 */
	@Override
	public List<CalendarNotificationTemplate> getCalendarNotificationTemplatesByUuidAndCompanyId(
		String uuid, long companyId) {
		return calendarNotificationTemplatePersistence.findByUuid_C(uuid,
			companyId);
	}

	/**
	 * Returns a range of calendar notification templates matching the UUID and company.
	 *
	 * @param uuid the UUID of the calendar notification templates
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching calendar notification templates, or an empty list if no matches were found
	 */
	@Override
	public List<CalendarNotificationTemplate> getCalendarNotificationTemplatesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {
		return calendarNotificationTemplatePersistence.findByUuid_C(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the calendar notification template matching the UUID and group.
	 *
	 * @param uuid the calendar notification template's UUID
	 * @param groupId the primary key of the group
	 * @return the matching calendar notification template
	 * @throws PortalException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate getCalendarNotificationTemplateByUuidAndGroupId(
		String uuid, long groupId) throws PortalException {
		return calendarNotificationTemplatePersistence.findByUUID_G(uuid,
			groupId);
	}

	/**
	 * Returns a range of all the calendar notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.calendar.model.impl.CalendarNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @return the range of calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> getCalendarNotificationTemplates(
		int start, int end) {
		return calendarNotificationTemplatePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of calendar notification templates.
	 *
	 * @return the number of calendar notification templates
	 */
	@Override
	public int getCalendarNotificationTemplatesCount() {
		return calendarNotificationTemplatePersistence.countAll();
	}

	/**
	 * Updates the calendar notification template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param calendarNotificationTemplate the calendar notification template
	 * @return the calendar notification template that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CalendarNotificationTemplate updateCalendarNotificationTemplate(
		CalendarNotificationTemplate calendarNotificationTemplate) {
		return calendarNotificationTemplatePersistence.update(calendarNotificationTemplate);
	}

	/**
	 * Returns the calendar local service.
	 *
	 * @return the calendar local service
	 */
	public com.liferay.calendar.service.CalendarLocalService getCalendarLocalService() {
		return calendarLocalService;
	}

	/**
	 * Sets the calendar local service.
	 *
	 * @param calendarLocalService the calendar local service
	 */
	public void setCalendarLocalService(
		com.liferay.calendar.service.CalendarLocalService calendarLocalService) {
		this.calendarLocalService = calendarLocalService;
	}

	/**
	 * Returns the calendar persistence.
	 *
	 * @return the calendar persistence
	 */
	public CalendarPersistence getCalendarPersistence() {
		return calendarPersistence;
	}

	/**
	 * Sets the calendar persistence.
	 *
	 * @param calendarPersistence the calendar persistence
	 */
	public void setCalendarPersistence(CalendarPersistence calendarPersistence) {
		this.calendarPersistence = calendarPersistence;
	}

	/**
	 * Returns the calendar finder.
	 *
	 * @return the calendar finder
	 */
	public CalendarFinder getCalendarFinder() {
		return calendarFinder;
	}

	/**
	 * Sets the calendar finder.
	 *
	 * @param calendarFinder the calendar finder
	 */
	public void setCalendarFinder(CalendarFinder calendarFinder) {
		this.calendarFinder = calendarFinder;
	}

	/**
	 * Returns the calendar booking local service.
	 *
	 * @return the calendar booking local service
	 */
	public com.liferay.calendar.service.CalendarBookingLocalService getCalendarBookingLocalService() {
		return calendarBookingLocalService;
	}

	/**
	 * Sets the calendar booking local service.
	 *
	 * @param calendarBookingLocalService the calendar booking local service
	 */
	public void setCalendarBookingLocalService(
		com.liferay.calendar.service.CalendarBookingLocalService calendarBookingLocalService) {
		this.calendarBookingLocalService = calendarBookingLocalService;
	}

	/**
	 * Returns the calendar booking persistence.
	 *
	 * @return the calendar booking persistence
	 */
	public CalendarBookingPersistence getCalendarBookingPersistence() {
		return calendarBookingPersistence;
	}

	/**
	 * Sets the calendar booking persistence.
	 *
	 * @param calendarBookingPersistence the calendar booking persistence
	 */
	public void setCalendarBookingPersistence(
		CalendarBookingPersistence calendarBookingPersistence) {
		this.calendarBookingPersistence = calendarBookingPersistence;
	}

	/**
	 * Returns the calendar booking finder.
	 *
	 * @return the calendar booking finder
	 */
	public CalendarBookingFinder getCalendarBookingFinder() {
		return calendarBookingFinder;
	}

	/**
	 * Sets the calendar booking finder.
	 *
	 * @param calendarBookingFinder the calendar booking finder
	 */
	public void setCalendarBookingFinder(
		CalendarBookingFinder calendarBookingFinder) {
		this.calendarBookingFinder = calendarBookingFinder;
	}

	/**
	 * Returns the calendar notification template local service.
	 *
	 * @return the calendar notification template local service
	 */
	public CalendarNotificationTemplateLocalService getCalendarNotificationTemplateLocalService() {
		return calendarNotificationTemplateLocalService;
	}

	/**
	 * Sets the calendar notification template local service.
	 *
	 * @param calendarNotificationTemplateLocalService the calendar notification template local service
	 */
	public void setCalendarNotificationTemplateLocalService(
		CalendarNotificationTemplateLocalService calendarNotificationTemplateLocalService) {
		this.calendarNotificationTemplateLocalService = calendarNotificationTemplateLocalService;
	}

	/**
	 * Returns the calendar notification template persistence.
	 *
	 * @return the calendar notification template persistence
	 */
	public CalendarNotificationTemplatePersistence getCalendarNotificationTemplatePersistence() {
		return calendarNotificationTemplatePersistence;
	}

	/**
	 * Sets the calendar notification template persistence.
	 *
	 * @param calendarNotificationTemplatePersistence the calendar notification template persistence
	 */
	public void setCalendarNotificationTemplatePersistence(
		CalendarNotificationTemplatePersistence calendarNotificationTemplatePersistence) {
		this.calendarNotificationTemplatePersistence = calendarNotificationTemplatePersistence;
	}

	/**
	 * Returns the calendar resource local service.
	 *
	 * @return the calendar resource local service
	 */
	public com.liferay.calendar.service.CalendarResourceLocalService getCalendarResourceLocalService() {
		return calendarResourceLocalService;
	}

	/**
	 * Sets the calendar resource local service.
	 *
	 * @param calendarResourceLocalService the calendar resource local service
	 */
	public void setCalendarResourceLocalService(
		com.liferay.calendar.service.CalendarResourceLocalService calendarResourceLocalService) {
		this.calendarResourceLocalService = calendarResourceLocalService;
	}

	/**
	 * Returns the calendar resource persistence.
	 *
	 * @return the calendar resource persistence
	 */
	public CalendarResourcePersistence getCalendarResourcePersistence() {
		return calendarResourcePersistence;
	}

	/**
	 * Sets the calendar resource persistence.
	 *
	 * @param calendarResourcePersistence the calendar resource persistence
	 */
	public void setCalendarResourcePersistence(
		CalendarResourcePersistence calendarResourcePersistence) {
		this.calendarResourcePersistence = calendarResourcePersistence;
	}

	/**
	 * Returns the calendar resource finder.
	 *
	 * @return the calendar resource finder
	 */
	public CalendarResourceFinder getCalendarResourceFinder() {
		return calendarResourceFinder;
	}

	/**
	 * Sets the calendar resource finder.
	 *
	 * @param calendarResourceFinder the calendar resource finder
	 */
	public void setCalendarResourceFinder(
		CalendarResourceFinder calendarResourceFinder) {
		this.calendarResourceFinder = calendarResourceFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.calendar.model.CalendarNotificationTemplate",
			calendarNotificationTemplateLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.calendar.model.CalendarNotificationTemplate");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CalendarNotificationTemplateLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CalendarNotificationTemplate.class;
	}

	protected String getModelClassName() {
		return CalendarNotificationTemplate.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = calendarNotificationTemplatePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.calendar.service.CalendarLocalService.class)
	protected com.liferay.calendar.service.CalendarLocalService calendarLocalService;
	@BeanReference(type = CalendarPersistence.class)
	protected CalendarPersistence calendarPersistence;
	@BeanReference(type = CalendarFinder.class)
	protected CalendarFinder calendarFinder;
	@BeanReference(type = com.liferay.calendar.service.CalendarBookingLocalService.class)
	protected com.liferay.calendar.service.CalendarBookingLocalService calendarBookingLocalService;
	@BeanReference(type = CalendarBookingPersistence.class)
	protected CalendarBookingPersistence calendarBookingPersistence;
	@BeanReference(type = CalendarBookingFinder.class)
	protected CalendarBookingFinder calendarBookingFinder;
	@BeanReference(type = CalendarNotificationTemplateLocalService.class)
	protected CalendarNotificationTemplateLocalService calendarNotificationTemplateLocalService;
	@BeanReference(type = CalendarNotificationTemplatePersistence.class)
	protected CalendarNotificationTemplatePersistence calendarNotificationTemplatePersistence;
	@BeanReference(type = com.liferay.calendar.service.CalendarResourceLocalService.class)
	protected com.liferay.calendar.service.CalendarResourceLocalService calendarResourceLocalService;
	@BeanReference(type = CalendarResourcePersistence.class)
	protected CalendarResourcePersistence calendarResourcePersistence;
	@BeanReference(type = CalendarResourceFinder.class)
	protected CalendarResourceFinder calendarResourceFinder;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}