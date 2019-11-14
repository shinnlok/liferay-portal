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

package com.liferay.analytics.message.storage.service.persistence.impl;

import com.liferay.analytics.message.storage.exception.NoSuchMessageEntryException;
import com.liferay.analytics.message.storage.model.AnalyticsMessageEntry;
import com.liferay.analytics.message.storage.model.impl.AnalyticsMessageEntryImpl;
import com.liferay.analytics.message.storage.model.impl.AnalyticsMessageEntryModelImpl;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsMessageEntryPersistence;
import com.liferay.analytics.message.storage.service.persistence.impl.constants.AnalyticsPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the analytics message entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AnalyticsMessageEntryPersistence.class)
public class AnalyticsMessageEntryPersistenceImpl
	extends BasePersistenceImpl<AnalyticsMessageEntry>
	implements AnalyticsMessageEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AnalyticsMessageEntryUtil</code> to access the analytics message entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AnalyticsMessageEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the analytics message entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics message entries
	 */
	@Override
	public List<AnalyticsMessageEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
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
	@Override
	public List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
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
	@Override
	public List<AnalyticsMessageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<AnalyticsMessageEntry> list = null;

		if (useFinderCache) {
			list = (List<AnalyticsMessageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnalyticsMessageEntry analyticsMessageEntry : list) {
					if (companyId != analyticsMessageEntry.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ANALYTICSMESSAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(AnalyticsMessageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<AnalyticsMessageEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message entry
	 * @throws NoSuchMessageEntryException if a matching analytics message entry could not be found
	 */
	@Override
	public AnalyticsMessageEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<AnalyticsMessageEntry> orderByComparator)
		throws NoSuchMessageEntryException {

		AnalyticsMessageEntry analyticsMessageEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (analyticsMessageEntry != null) {
			return analyticsMessageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchMessageEntryException(msg.toString());
	}

	/**
	 * Returns the first analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message entry, or <code>null</code> if a matching analytics message entry could not be found
	 */
	@Override
	public AnalyticsMessageEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		List<AnalyticsMessageEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message entry
	 * @throws NoSuchMessageEntryException if a matching analytics message entry could not be found
	 */
	@Override
	public AnalyticsMessageEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<AnalyticsMessageEntry> orderByComparator)
		throws NoSuchMessageEntryException {

		AnalyticsMessageEntry analyticsMessageEntry = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (analyticsMessageEntry != null) {
			return analyticsMessageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchMessageEntryException(msg.toString());
	}

	/**
	 * Returns the last analytics message entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message entry, or <code>null</code> if a matching analytics message entry could not be found
	 */
	@Override
	public AnalyticsMessageEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AnalyticsMessageEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AnalyticsMessageEntry[] findByCompanyId_PrevAndNext(
			long analyticsMessageEntryId, long companyId,
			OrderByComparator<AnalyticsMessageEntry> orderByComparator)
		throws NoSuchMessageEntryException {

		AnalyticsMessageEntry analyticsMessageEntry = findByPrimaryKey(
			analyticsMessageEntryId);

		Session session = null;

		try {
			session = openSession();

			AnalyticsMessageEntry[] array = new AnalyticsMessageEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, analyticsMessageEntry, companyId, orderByComparator,
				true);

			array[1] = analyticsMessageEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, analyticsMessageEntry, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnalyticsMessageEntry getByCompanyId_PrevAndNext(
		Session session, AnalyticsMessageEntry analyticsMessageEntry,
		long companyId,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANALYTICSMESSAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AnalyticsMessageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						analyticsMessageEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AnalyticsMessageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the analytics message entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AnalyticsMessageEntry analyticsMessageEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(analyticsMessageEntry);
		}
	}

	/**
	 * Returns the number of analytics message entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics message entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ANALYTICSMESSAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"analyticsMessageEntry.companyId = ?";

	public AnalyticsMessageEntryPersistenceImpl() {
		setModelClass(AnalyticsMessageEntry.class);

		setModelImplClass(AnalyticsMessageEntryImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the analytics message entry in the entity cache if it is enabled.
	 *
	 * @param analyticsMessageEntry the analytics message entry
	 */
	@Override
	public void cacheResult(AnalyticsMessageEntry analyticsMessageEntry) {
		entityCache.putResult(
			entityCacheEnabled, AnalyticsMessageEntryImpl.class,
			analyticsMessageEntry.getPrimaryKey(), analyticsMessageEntry);

		analyticsMessageEntry.resetOriginalValues();
	}

	/**
	 * Caches the analytics message entries in the entity cache if it is enabled.
	 *
	 * @param analyticsMessageEntries the analytics message entries
	 */
	@Override
	public void cacheResult(
		List<AnalyticsMessageEntry> analyticsMessageEntries) {

		for (AnalyticsMessageEntry analyticsMessageEntry :
				analyticsMessageEntries) {

			if (entityCache.getResult(
					entityCacheEnabled, AnalyticsMessageEntryImpl.class,
					analyticsMessageEntry.getPrimaryKey()) == null) {

				cacheResult(analyticsMessageEntry);
			}
			else {
				analyticsMessageEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all analytics message entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AnalyticsMessageEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the analytics message entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnalyticsMessageEntry analyticsMessageEntry) {
		entityCache.removeResult(
			entityCacheEnabled, AnalyticsMessageEntryImpl.class,
			analyticsMessageEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<AnalyticsMessageEntry> analyticsMessageEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AnalyticsMessageEntry analyticsMessageEntry :
				analyticsMessageEntries) {

			entityCache.removeResult(
				entityCacheEnabled, AnalyticsMessageEntryImpl.class,
				analyticsMessageEntry.getPrimaryKey());
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, AnalyticsMessageEntryImpl.class,
				primaryKey);
		}
	}

	/**
	 * Creates a new analytics message entry with the primary key. Does not add the analytics message entry to the database.
	 *
	 * @param analyticsMessageEntryId the primary key for the new analytics message entry
	 * @return the new analytics message entry
	 */
	@Override
	public AnalyticsMessageEntry create(long analyticsMessageEntryId) {
		AnalyticsMessageEntry analyticsMessageEntry =
			new AnalyticsMessageEntryImpl();

		analyticsMessageEntry.setNew(true);
		analyticsMessageEntry.setPrimaryKey(analyticsMessageEntryId);

		analyticsMessageEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return analyticsMessageEntry;
	}

	/**
	 * Removes the analytics message entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry that was removed
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	@Override
	public AnalyticsMessageEntry remove(long analyticsMessageEntryId)
		throws NoSuchMessageEntryException {

		return remove((Serializable)analyticsMessageEntryId);
	}

	/**
	 * Removes the analytics message entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the analytics message entry
	 * @return the analytics message entry that was removed
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	@Override
	public AnalyticsMessageEntry remove(Serializable primaryKey)
		throws NoSuchMessageEntryException {

		Session session = null;

		try {
			session = openSession();

			AnalyticsMessageEntry analyticsMessageEntry =
				(AnalyticsMessageEntry)session.get(
					AnalyticsMessageEntryImpl.class, primaryKey);

			if (analyticsMessageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMessageEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(analyticsMessageEntry);
		}
		catch (NoSuchMessageEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected AnalyticsMessageEntry removeImpl(
		AnalyticsMessageEntry analyticsMessageEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(analyticsMessageEntry)) {
				analyticsMessageEntry = (AnalyticsMessageEntry)session.get(
					AnalyticsMessageEntryImpl.class,
					analyticsMessageEntry.getPrimaryKeyObj());
			}

			if (analyticsMessageEntry != null) {
				session.delete(analyticsMessageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (analyticsMessageEntry != null) {
			clearCache(analyticsMessageEntry);
		}

		return analyticsMessageEntry;
	}

	@Override
	public AnalyticsMessageEntry updateImpl(
		AnalyticsMessageEntry analyticsMessageEntry) {

		boolean isNew = analyticsMessageEntry.isNew();

		if (!(analyticsMessageEntry instanceof
				AnalyticsMessageEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(analyticsMessageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					analyticsMessageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in analyticsMessageEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AnalyticsMessageEntry implementation " +
					analyticsMessageEntry.getClass());
		}

		AnalyticsMessageEntryModelImpl analyticsMessageEntryModelImpl =
			(AnalyticsMessageEntryModelImpl)analyticsMessageEntry;

		Session session = null;

		try {
			session = openSession();

			if (analyticsMessageEntry.isNew()) {
				session.save(analyticsMessageEntry);

				analyticsMessageEntry.setNew(false);
			}
			else {
				session.evict(analyticsMessageEntry);
				session.saveOrUpdate(analyticsMessageEntry);
			}

			session.flush();
			session.clear();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				analyticsMessageEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((analyticsMessageEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					analyticsMessageEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					analyticsMessageEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, AnalyticsMessageEntryImpl.class,
			analyticsMessageEntry.getPrimaryKey(), analyticsMessageEntry,
			false);

		analyticsMessageEntry.resetOriginalValues();

		return analyticsMessageEntry;
	}

	/**
	 * Returns the analytics message entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the analytics message entry
	 * @return the analytics message entry
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	@Override
	public AnalyticsMessageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMessageEntryException {

		AnalyticsMessageEntry analyticsMessageEntry = fetchByPrimaryKey(
			primaryKey);

		if (analyticsMessageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMessageEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return analyticsMessageEntry;
	}

	/**
	 * Returns the analytics message entry with the primary key or throws a <code>NoSuchMessageEntryException</code> if it could not be found.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry
	 * @throws NoSuchMessageEntryException if a analytics message entry with the primary key could not be found
	 */
	@Override
	public AnalyticsMessageEntry findByPrimaryKey(long analyticsMessageEntryId)
		throws NoSuchMessageEntryException {

		return findByPrimaryKey((Serializable)analyticsMessageEntryId);
	}

	/**
	 * Returns the analytics message entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsMessageEntryId the primary key of the analytics message entry
	 * @return the analytics message entry, or <code>null</code> if a analytics message entry with the primary key could not be found
	 */
	@Override
	public AnalyticsMessageEntry fetchByPrimaryKey(
		long analyticsMessageEntryId) {

		return fetchByPrimaryKey((Serializable)analyticsMessageEntryId);
	}

	/**
	 * Returns all the analytics message entries.
	 *
	 * @return the analytics message entries
	 */
	@Override
	public List<AnalyticsMessageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AnalyticsMessageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<AnalyticsMessageEntry> findAll(
		int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<AnalyticsMessageEntry> findAll(
		int start, int end,
		OrderByComparator<AnalyticsMessageEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<AnalyticsMessageEntry> list = null;

		if (useFinderCache) {
			list = (List<AnalyticsMessageEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ANALYTICSMESSAGEENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ANALYTICSMESSAGEENTRY;

				sql = sql.concat(AnalyticsMessageEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<AnalyticsMessageEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the analytics message entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AnalyticsMessageEntry analyticsMessageEntry : findAll()) {
			remove(analyticsMessageEntry);
		}
	}

	/**
	 * Returns the number of analytics message entries.
	 *
	 * @return the number of analytics message entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ANALYTICSMESSAGEENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "analyticsMessageEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ANALYTICSMESSAGEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AnalyticsMessageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the analytics message entry persistence.
	 */
	@Activate
	public void activate() {
		AnalyticsMessageEntryModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		AnalyticsMessageEntryModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AnalyticsMessageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AnalyticsMessageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AnalyticsMessageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AnalyticsMessageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			AnalyticsMessageEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			AnalyticsMessageEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AnalyticsMessageEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.analytics.message.storage.model.AnalyticsMessageEntry"),
			true);
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AnalyticsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ANALYTICSMESSAGEENTRY =
		"SELECT analyticsMessageEntry FROM AnalyticsMessageEntry analyticsMessageEntry";

	private static final String _SQL_SELECT_ANALYTICSMESSAGEENTRY_WHERE =
		"SELECT analyticsMessageEntry FROM AnalyticsMessageEntry analyticsMessageEntry WHERE ";

	private static final String _SQL_COUNT_ANALYTICSMESSAGEENTRY =
		"SELECT COUNT(analyticsMessageEntry) FROM AnalyticsMessageEntry analyticsMessageEntry";

	private static final String _SQL_COUNT_ANALYTICSMESSAGEENTRY_WHERE =
		"SELECT COUNT(analyticsMessageEntry) FROM AnalyticsMessageEntry analyticsMessageEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"analyticsMessageEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AnalyticsMessageEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AnalyticsMessageEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsMessageEntryPersistenceImpl.class);

	static {
		try {
			Class.forName(AnalyticsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}