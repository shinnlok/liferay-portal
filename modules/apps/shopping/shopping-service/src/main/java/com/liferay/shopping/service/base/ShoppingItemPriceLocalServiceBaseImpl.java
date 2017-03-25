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

package com.liferay.shopping.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.shopping.model.ShoppingItemPrice;
import com.liferay.shopping.service.ShoppingItemPriceLocalService;
import com.liferay.shopping.service.persistence.ShoppingItemFinder;
import com.liferay.shopping.service.persistence.ShoppingItemPersistence;
import com.liferay.shopping.service.persistence.ShoppingItemPricePersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the shopping item price local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.shopping.service.impl.ShoppingItemPriceLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.impl.ShoppingItemPriceLocalServiceImpl
 * @see com.liferay.shopping.service.ShoppingItemPriceLocalServiceUtil
 * @generated
 */
public abstract class ShoppingItemPriceLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements ShoppingItemPriceLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.shopping.service.ShoppingItemPriceLocalServiceUtil} to access the shopping item price local service.
	 */

	/**
	 * Adds the shopping item price to the database. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingItemPrice the shopping item price
	 * @return the shopping item price that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ShoppingItemPrice addShoppingItemPrice(
		ShoppingItemPrice shoppingItemPrice) {
		shoppingItemPrice.setNew(true);

		return shoppingItemPricePersistence.update(shoppingItemPrice);
	}

	/**
	 * Creates a new shopping item price with the primary key. Does not add the shopping item price to the database.
	 *
	 * @param itemPriceId the primary key for the new shopping item price
	 * @return the new shopping item price
	 */
	@Override
	public ShoppingItemPrice createShoppingItemPrice(long itemPriceId) {
		return shoppingItemPricePersistence.create(itemPriceId);
	}

	/**
	 * Deletes the shopping item price with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param itemPriceId the primary key of the shopping item price
	 * @return the shopping item price that was removed
	 * @throws PortalException if a shopping item price with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ShoppingItemPrice deleteShoppingItemPrice(long itemPriceId)
		throws PortalException {
		return shoppingItemPricePersistence.remove(itemPriceId);
	}

	/**
	 * Deletes the shopping item price from the database. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingItemPrice the shopping item price
	 * @return the shopping item price that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ShoppingItemPrice deleteShoppingItemPrice(
		ShoppingItemPrice shoppingItemPrice) {
		return shoppingItemPricePersistence.remove(shoppingItemPrice);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(ShoppingItemPrice.class,
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
		return shoppingItemPricePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return shoppingItemPricePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return shoppingItemPricePersistence.findWithDynamicQuery(dynamicQuery,
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
		return shoppingItemPricePersistence.countWithDynamicQuery(dynamicQuery);
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
		return shoppingItemPricePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public ShoppingItemPrice fetchShoppingItemPrice(long itemPriceId) {
		return shoppingItemPricePersistence.fetchByPrimaryKey(itemPriceId);
	}

	/**
	 * Returns the shopping item price with the primary key.
	 *
	 * @param itemPriceId the primary key of the shopping item price
	 * @return the shopping item price
	 * @throws PortalException if a shopping item price with the primary key could not be found
	 */
	@Override
	public ShoppingItemPrice getShoppingItemPrice(long itemPriceId)
		throws PortalException {
		return shoppingItemPricePersistence.findByPrimaryKey(itemPriceId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(shoppingItemPriceLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(ShoppingItemPrice.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("itemPriceId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(shoppingItemPriceLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(ShoppingItemPrice.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("itemPriceId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(shoppingItemPriceLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(ShoppingItemPrice.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("itemPriceId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return shoppingItemPriceLocalService.deleteShoppingItemPrice((ShoppingItemPrice)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return shoppingItemPricePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the shopping item prices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item prices
	 * @param end the upper bound of the range of shopping item prices (not inclusive)
	 * @return the range of shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> getShoppingItemPrices(int start, int end) {
		return shoppingItemPricePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of shopping item prices.
	 *
	 * @return the number of shopping item prices
	 */
	@Override
	public int getShoppingItemPricesCount() {
		return shoppingItemPricePersistence.countAll();
	}

	/**
	 * Updates the shopping item price in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingItemPrice the shopping item price
	 * @return the shopping item price that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ShoppingItemPrice updateShoppingItemPrice(
		ShoppingItemPrice shoppingItemPrice) {
		return shoppingItemPricePersistence.update(shoppingItemPrice);
	}

	/**
	 * Returns the shopping item price local service.
	 *
	 * @return the shopping item price local service
	 */
	public ShoppingItemPriceLocalService getShoppingItemPriceLocalService() {
		return shoppingItemPriceLocalService;
	}

	/**
	 * Sets the shopping item price local service.
	 *
	 * @param shoppingItemPriceLocalService the shopping item price local service
	 */
	public void setShoppingItemPriceLocalService(
		ShoppingItemPriceLocalService shoppingItemPriceLocalService) {
		this.shoppingItemPriceLocalService = shoppingItemPriceLocalService;
	}

	/**
	 * Returns the shopping item price persistence.
	 *
	 * @return the shopping item price persistence
	 */
	public ShoppingItemPricePersistence getShoppingItemPricePersistence() {
		return shoppingItemPricePersistence;
	}

	/**
	 * Sets the shopping item price persistence.
	 *
	 * @param shoppingItemPricePersistence the shopping item price persistence
	 */
	public void setShoppingItemPricePersistence(
		ShoppingItemPricePersistence shoppingItemPricePersistence) {
		this.shoppingItemPricePersistence = shoppingItemPricePersistence;
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
	 * Returns the shopping item local service.
	 *
	 * @return the shopping item local service
	 */
	public com.liferay.shopping.service.ShoppingItemLocalService getShoppingItemLocalService() {
		return shoppingItemLocalService;
	}

	/**
	 * Sets the shopping item local service.
	 *
	 * @param shoppingItemLocalService the shopping item local service
	 */
	public void setShoppingItemLocalService(
		com.liferay.shopping.service.ShoppingItemLocalService shoppingItemLocalService) {
		this.shoppingItemLocalService = shoppingItemLocalService;
	}

	/**
	 * Returns the shopping item persistence.
	 *
	 * @return the shopping item persistence
	 */
	public ShoppingItemPersistence getShoppingItemPersistence() {
		return shoppingItemPersistence;
	}

	/**
	 * Sets the shopping item persistence.
	 *
	 * @param shoppingItemPersistence the shopping item persistence
	 */
	public void setShoppingItemPersistence(
		ShoppingItemPersistence shoppingItemPersistence) {
		this.shoppingItemPersistence = shoppingItemPersistence;
	}

	/**
	 * Returns the shopping item finder.
	 *
	 * @return the shopping item finder
	 */
	public ShoppingItemFinder getShoppingItemFinder() {
		return shoppingItemFinder;
	}

	/**
	 * Sets the shopping item finder.
	 *
	 * @param shoppingItemFinder the shopping item finder
	 */
	public void setShoppingItemFinder(ShoppingItemFinder shoppingItemFinder) {
		this.shoppingItemFinder = shoppingItemFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.shopping.model.ShoppingItemPrice",
			shoppingItemPriceLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.shopping.model.ShoppingItemPrice");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return ShoppingItemPriceLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return ShoppingItemPrice.class;
	}

	protected String getModelClassName() {
		return ShoppingItemPrice.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = shoppingItemPricePersistence.getDataSource();

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

	@BeanReference(type = ShoppingItemPriceLocalService.class)
	protected ShoppingItemPriceLocalService shoppingItemPriceLocalService;
	@BeanReference(type = ShoppingItemPricePersistence.class)
	protected ShoppingItemPricePersistence shoppingItemPricePersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.shopping.service.ShoppingItemLocalService.class)
	protected com.liferay.shopping.service.ShoppingItemLocalService shoppingItemLocalService;
	@BeanReference(type = ShoppingItemPersistence.class)
	protected ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(type = ShoppingItemFinder.class)
	protected ShoppingItemFinder shoppingItemFinder;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}