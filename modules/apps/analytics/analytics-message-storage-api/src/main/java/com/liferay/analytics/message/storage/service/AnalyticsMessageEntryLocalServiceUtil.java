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

package com.liferay.analytics.message.storage.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AnalyticsMessageEntry. This utility wraps
 * <code>com.liferay.analytics.message.storage.service.impl.AnalyticsMessageEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageEntryLocalService
 * @generated
 */
public class AnalyticsMessageEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.analytics.message.storage.service.impl.AnalyticsMessageEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the analytics message entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageEntry the analytics message entry
	 * @return the analytics message entry that was added
	 */
	public static
		com.liferay.analytics.message.storage.model.AnalyticsMessageEntry
			addAnalyticsMessageEntry(
				com.liferay.analytics.message.storage.model.
					AnalyticsMessageEntry analyticsMessageEntry) {

		return getService().addAnalyticsMessageEntry(analyticsMessageEntry);
	}

	/**
	 * Creates a new analytics message entry with the primary key. Does not add the analytics message entry to the database.
	 *
	 * @param analyticsMessageEntryId the primary key for the new analytics message entry
	 * @return the new analytics message entry
	 */
	public static
		com.liferay.analytics.message.storage.model.AnalyticsMessageEntry
			createAnalyticsMessageEntry(long analyticsMessageEntryId) {

		return getService().createAnalyticsMessageEntry(
			analyticsMessageEntryId);
	}

	/**
	 * Deletes the analytics message entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageEntry the analytics message entry
	 * @return the analytics message entry that was removed
	 */
	public static
		com.liferay.analytics.message.storage.model.AnalyticsMessageEntry
			deleteAnalyticsMessageEntry(
				com.liferay.analytics.message.storage.model.
					AnalyticsMessageEntry analyticsMessageEntry) {

		return getService().deleteAnalyticsMessageEntry(analyticsMessageEntry);
	}

	/**
	 * Deletes the analytics message entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry that was removed
	 * @throws PortalException if a analytics message entry with the primary key could not be found
	 */
	public static
		com.liferay.analytics.message.storage.model.AnalyticsMessageEntry
				deleteAnalyticsMessageEntry(long analyticsMessageEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAnalyticsMessageEntry(
			analyticsMessageEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static
		com.liferay.analytics.message.storage.model.AnalyticsMessageEntry
			fetchAnalyticsMessageEntry(long analyticsMessageEntryId) {

		return getService().fetchAnalyticsMessageEntry(analyticsMessageEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the analytics message entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics message entries
	 * @param end the upper bound of the range of analytics message entries (not inclusive)
	 * @return the range of analytics message entries
	 */
	public static java.util.List
		<com.liferay.analytics.message.storage.model.AnalyticsMessageEntry>
			getAnalyticsMessageEntries(int start, int end) {

		return getService().getAnalyticsMessageEntries(start, end);
	}

	/**
	 * Returns the number of analytics message entries.
	 *
	 * @return the number of analytics message entries
	 */
	public static int getAnalyticsMessageEntriesCount() {
		return getService().getAnalyticsMessageEntriesCount();
	}

	/**
	 * Returns the analytics message entry with the primary key.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry
	 * @throws PortalException if a analytics message entry with the primary key could not be found
	 */
	public static
		com.liferay.analytics.message.storage.model.AnalyticsMessageEntry
				getAnalyticsMessageEntry(long analyticsMessageEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAnalyticsMessageEntry(analyticsMessageEntryId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static com.liferay.analytics.message.storage.model.
		AnalyticsMessageEntryMessageBlobModel getMessageBlobModel(
			java.io.Serializable primaryKey) {

		return getService().getMessageBlobModel(primaryKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the analytics message entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageEntry the analytics message entry
	 * @return the analytics message entry that was updated
	 */
	public static
		com.liferay.analytics.message.storage.model.AnalyticsMessageEntry
			updateAnalyticsMessageEntry(
				com.liferay.analytics.message.storage.model.
					AnalyticsMessageEntry analyticsMessageEntry) {

		return getService().updateAnalyticsMessageEntry(analyticsMessageEntry);
	}

	public static AnalyticsMessageEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AnalyticsMessageEntryLocalService, AnalyticsMessageEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AnalyticsMessageEntryLocalService.class);

		ServiceTracker
			<AnalyticsMessageEntryLocalService,
			 AnalyticsMessageEntryLocalService> serviceTracker =
				new ServiceTracker
					<AnalyticsMessageEntryLocalService,
					 AnalyticsMessageEntryLocalService>(
						 bundle.getBundleContext(),
						 AnalyticsMessageEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}