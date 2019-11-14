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

package com.liferay.analytics.message.storage.service.persistence;

import com.liferay.analytics.message.storage.model.AnalyticsMessageEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the analytics message entry service. This utility wraps <code>com.liferay.analytics.message.storage.service.persistence.impl.AnalyticsMessageEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageEntryPersistence
 * @generated
 */
public class AnalyticsMessageEntryUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(AnalyticsMessageEntry analyticsMessageEntry) {
		getPersistence().clearCache(analyticsMessageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, AnalyticsMessageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AnalyticsMessageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnalyticsMessageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnalyticsMessageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AnalyticsMessageEntry update(
		AnalyticsMessageEntry analyticsMessageEntry) {

		return getPersistence().update(analyticsMessageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AnalyticsMessageEntry update(
		AnalyticsMessageEntry analyticsMessageEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(analyticsMessageEntry, serviceContext);
	}

	/**
	 * Returns all the analytics message entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics message entries
	 */
	public static List<AnalyticsMessageEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the analytics message entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics message entries
	 * @param end the upper bound of the range of analytics message entries (not inclusive)
	 * @return the range of matching analytics message entries
	 */
	public static List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the analytics message entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics message entries
	 * @param end the upper bound of the range of analytics message entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics message entries
	 */
	public static List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics message entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics message entries
	 * @param end the upper bound of the range of analytics message entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics message entries
	 */
	public static List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message entry
	 * @throws NoSuchMessageEntryException if a matching analytics message entry could not be found
	 */
	public static AnalyticsMessageEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<AnalyticsMessageEntry> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message entry, or <code>null</code> if a matching analytics message entry could not be found
	 */
	public static AnalyticsMessageEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message entry
	 * @throws NoSuchMessageEntryException if a matching analytics message entry could not be found
	 */
	public static AnalyticsMessageEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<AnalyticsMessageEntry> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message entry, or <code>null</code> if a matching analytics message entry could not be found
	 */
	public static AnalyticsMessageEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the analytics message entries before and after the current analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param analyticsMessageEntryId the primary key of the current analytics message entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics message entry
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	public static AnalyticsMessageEntry[] findByCompanyId_PrevAndNext(
			long analyticsMessageEntryId, long companyId,
			OrderByComparator<AnalyticsMessageEntry> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			analyticsMessageEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the analytics message entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of analytics message entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics message entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Caches the analytics message entry in the entity cache if it is enabled.
	 *
	 * @param analyticsMessageEntry the analytics message entry
	 */
	public static void cacheResult(
		AnalyticsMessageEntry analyticsMessageEntry) {

		getPersistence().cacheResult(analyticsMessageEntry);
	}

	/**
	 * Caches the analytics message entries in the entity cache if it is enabled.
	 *
	 * @param analyticsMessageEntries the analytics message entries
	 */
	public static void cacheResult(
		List<AnalyticsMessageEntry> analyticsMessageEntries) {

		getPersistence().cacheResult(analyticsMessageEntries);
	}

	/**
	 * Creates a new analytics message entry with the primary key. Does not add the analytics message entry to the database.
	 *
	 * @param analyticsMessageEntryId the primary key for the new analytics message entry
	 * @return the new analytics message entry
	 */
	public static AnalyticsMessageEntry create(long analyticsMessageEntryId) {
		return getPersistence().create(analyticsMessageEntryId);
	}

	/**
	 * Removes the analytics message entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry that was removed
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	public static AnalyticsMessageEntry remove(long analyticsMessageEntryId)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageEntryException {

		return getPersistence().remove(analyticsMessageEntryId);
	}

	public static AnalyticsMessageEntry updateImpl(
		AnalyticsMessageEntry analyticsMessageEntry) {

		return getPersistence().updateImpl(analyticsMessageEntry);
	}

	/**
	 * Returns the analytics message entry with the primary key or throws a <code>NoSuchMessageEntryException</code> if it could not be found.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	public static AnalyticsMessageEntry findByPrimaryKey(
			long analyticsMessageEntryId)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageEntryException {

		return getPersistence().findByPrimaryKey(analyticsMessageEntryId);
	}

	/**
	 * Returns the analytics message entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry, or <code>null</code> if a analytics message entry with the primary key could not be found
	 */
	public static AnalyticsMessageEntry fetchByPrimaryKey(
		long analyticsMessageEntryId) {

		return getPersistence().fetchByPrimaryKey(analyticsMessageEntryId);
	}

	/**
	 * Returns all the analytics message entries.
	 *
	 * @return the analytics message entries
	 */
	public static List<AnalyticsMessageEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the analytics message entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics message entries
	 * @param end the upper bound of the range of analytics message entries (not inclusive)
	 * @return the range of analytics message entries
	 */
	public static List<AnalyticsMessageEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the analytics message entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics message entries
	 * @param end the upper bound of the range of analytics message entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics message entries
	 */
	public static List<AnalyticsMessageEntry> findAll(
		int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics message entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics message entries
	 * @param end the upper bound of the range of analytics message entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics message entries
	 */
	public static List<AnalyticsMessageEntry> findAll(
		int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the analytics message entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of analytics message entries.
	 *
	 * @return the number of analytics message entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AnalyticsMessageEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AnalyticsMessageEntryPersistence, AnalyticsMessageEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AnalyticsMessageEntryPersistence.class);

		ServiceTracker
			<AnalyticsMessageEntryPersistence, AnalyticsMessageEntryPersistence>
				serviceTracker =
					new ServiceTracker
						<AnalyticsMessageEntryPersistence,
						 AnalyticsMessageEntryPersistence>(
							 bundle.getBundleContext(),
							 AnalyticsMessageEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}