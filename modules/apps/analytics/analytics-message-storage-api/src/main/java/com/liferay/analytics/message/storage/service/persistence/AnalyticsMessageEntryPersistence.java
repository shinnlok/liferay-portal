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

import com.liferay.analytics.message.storage.exception.NoSuchMessageEntryException;
import com.liferay.analytics.message.storage.model.AnalyticsMessageEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the analytics message entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageEntryUtil
 * @generated
 */
@ProviderType
public interface AnalyticsMessageEntryPersistence
	extends BasePersistence<AnalyticsMessageEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnalyticsMessageEntryUtil} to access the analytics message entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the analytics message entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics message entries
	 */
	public java.util.List<AnalyticsMessageEntry> findByCompanyId(
		long companyId);

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
	public java.util.List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessageEntry>
			orderByComparator);

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
	public java.util.List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message entry
	 * @throws NoSuchMessageEntryException if a matching analytics message entry could not be found
	 */
	public AnalyticsMessageEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsMessageEntry> orderByComparator)
		throws NoSuchMessageEntryException;

	/**
	 * Returns the first analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message entry, or <code>null</code> if a matching analytics message entry could not be found
	 */
	public AnalyticsMessageEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessageEntry>
			orderByComparator);

	/**
	 * Returns the last analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message entry
	 * @throws NoSuchMessageEntryException if a matching analytics message entry could not be found
	 */
	public AnalyticsMessageEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsMessageEntry> orderByComparator)
		throws NoSuchMessageEntryException;

	/**
	 * Returns the last analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message entry, or <code>null</code> if a matching analytics message entry could not be found
	 */
	public AnalyticsMessageEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessageEntry>
			orderByComparator);

	/**
	 * Returns the analytics message entries before and after the current analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param analyticsMessageEntryId the primary key of the current analytics message entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics message entry
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	public AnalyticsMessageEntry[] findByCompanyId_PrevAndNext(
			long analyticsMessageEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsMessageEntry> orderByComparator)
		throws NoSuchMessageEntryException;

	/**
	 * Removes all the analytics message entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of analytics message entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics message entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the analytics message entry in the entity cache if it is enabled.
	 *
	 * @param analyticsMessageEntry the analytics message entry
	 */
	public void cacheResult(AnalyticsMessageEntry analyticsMessageEntry);

	/**
	 * Caches the analytics message entries in the entity cache if it is enabled.
	 *
	 * @param analyticsMessageEntries the analytics message entries
	 */
	public void cacheResult(
		java.util.List<AnalyticsMessageEntry> analyticsMessageEntries);

	/**
	 * Creates a new analytics message entry with the primary key. Does not add the analytics message entry to the database.
	 *
	 * @param analyticsMessageEntryId the primary key for the new analytics message entry
	 * @return the new analytics message entry
	 */
	public AnalyticsMessageEntry create(long analyticsMessageEntryId);

	/**
	 * Removes the analytics message entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry that was removed
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	public AnalyticsMessageEntry remove(long analyticsMessageEntryId)
		throws NoSuchMessageEntryException;

	public AnalyticsMessageEntry updateImpl(
		AnalyticsMessageEntry analyticsMessageEntry);

	/**
	 * Returns the analytics message entry with the primary key or throws a <code>NoSuchMessageEntryException</code> if it could not be found.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	public AnalyticsMessageEntry findByPrimaryKey(long analyticsMessageEntryId)
		throws NoSuchMessageEntryException;

	/**
	 * Returns the analytics message entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry, or <code>null</code> if a analytics message entry with the primary key could not be found
	 */
	public AnalyticsMessageEntry fetchByPrimaryKey(
		long analyticsMessageEntryId);

	/**
	 * Returns all the analytics message entries.
	 *
	 * @return the analytics message entries
	 */
	public java.util.List<AnalyticsMessageEntry> findAll();

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
	public java.util.List<AnalyticsMessageEntry> findAll(int start, int end);

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
	public java.util.List<AnalyticsMessageEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessageEntry>
			orderByComparator);

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
	public java.util.List<AnalyticsMessageEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnalyticsMessageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the analytics message entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of analytics message entries.
	 *
	 * @return the number of analytics message entries
	 */
	public int countAll();

}