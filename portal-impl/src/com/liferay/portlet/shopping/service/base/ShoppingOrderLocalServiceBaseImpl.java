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

package com.liferay.portlet.shopping.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.ShoppingOrderLocalService;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the shopping order local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.shopping.service.impl.ShoppingOrderLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.shopping.service.impl.ShoppingOrderLocalServiceImpl
 * @see com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class ShoppingOrderLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements ShoppingOrderLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil} to access the shopping order local service.
	 */

	/**
	 * Adds the shopping order to the database. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingOrder the shopping order
	 * @return the shopping order that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ShoppingOrder addShoppingOrder(ShoppingOrder shoppingOrder) {
		shoppingOrder.setNew(true);

		return shoppingOrderPersistence.update(shoppingOrder);
	}

	/**
	 * Creates a new shopping order with the primary key. Does not add the shopping order to the database.
	 *
	 * @param orderId the primary key for the new shopping order
	 * @return the new shopping order
	 */
	@Override
	public ShoppingOrder createShoppingOrder(long orderId) {
		return shoppingOrderPersistence.create(orderId);
	}

	/**
	 * Deletes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param orderId the primary key of the shopping order
	 * @return the shopping order that was removed
	 * @throws PortalException if a shopping order with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ShoppingOrder deleteShoppingOrder(long orderId)
		throws PortalException {
		return shoppingOrderPersistence.remove(orderId);
	}

	/**
	 * Deletes the shopping order from the database. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingOrder the shopping order
	 * @return the shopping order that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ShoppingOrder deleteShoppingOrder(ShoppingOrder shoppingOrder) {
		return shoppingOrderPersistence.remove(shoppingOrder);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
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
		return shoppingOrderPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return shoppingOrderPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return shoppingOrderPersistence.findWithDynamicQuery(dynamicQuery,
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
		return shoppingOrderPersistence.countWithDynamicQuery(dynamicQuery);
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
		return shoppingOrderPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public ShoppingOrder fetchShoppingOrder(long orderId) {
		return shoppingOrderPersistence.fetchByPrimaryKey(orderId);
	}

	/**
	 * Returns the shopping order with the primary key.
	 *
	 * @param orderId the primary key of the shopping order
	 * @return the shopping order
	 * @throws PortalException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder getShoppingOrder(long orderId)
		throws PortalException {
		return shoppingOrderPersistence.findByPrimaryKey(orderId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(ShoppingOrder.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("orderId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(ShoppingOrder.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("orderId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return shoppingOrderLocalService.deleteShoppingOrder((ShoppingOrder)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return shoppingOrderPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the shopping orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of shopping orders
	 */
	@Override
	public List<ShoppingOrder> getShoppingOrders(int start, int end) {
		return shoppingOrderPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of shopping orders.
	 *
	 * @return the number of shopping orders
	 */
	@Override
	public int getShoppingOrdersCount() {
		return shoppingOrderPersistence.countAll();
	}

	/**
	 * Updates the shopping order in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingOrder the shopping order
	 * @return the shopping order that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ShoppingOrder updateShoppingOrder(ShoppingOrder shoppingOrder) {
		return shoppingOrderPersistence.update(shoppingOrder);
	}

	/**
	 * Returns the shopping order local service.
	 *
	 * @return the shopping order local service
	 */
	public ShoppingOrderLocalService getShoppingOrderLocalService() {
		return shoppingOrderLocalService;
	}

	/**
	 * Sets the shopping order local service.
	 *
	 * @param shoppingOrderLocalService the shopping order local service
	 */
	public void setShoppingOrderLocalService(
		ShoppingOrderLocalService shoppingOrderLocalService) {
		this.shoppingOrderLocalService = shoppingOrderLocalService;
	}

	/**
	 * Returns the shopping order remote service.
	 *
	 * @return the shopping order remote service
	 */
	public com.liferay.portlet.shopping.service.ShoppingOrderService getShoppingOrderService() {
		return shoppingOrderService;
	}

	/**
	 * Sets the shopping order remote service.
	 *
	 * @param shoppingOrderService the shopping order remote service
	 */
	public void setShoppingOrderService(
		com.liferay.portlet.shopping.service.ShoppingOrderService shoppingOrderService) {
		this.shoppingOrderService = shoppingOrderService;
	}

	/**
	 * Returns the shopping order persistence.
	 *
	 * @return the shopping order persistence
	 */
	public ShoppingOrderPersistence getShoppingOrderPersistence() {
		return shoppingOrderPersistence;
	}

	/**
	 * Sets the shopping order persistence.
	 *
	 * @param shoppingOrderPersistence the shopping order persistence
	 */
	public void setShoppingOrderPersistence(
		ShoppingOrderPersistence shoppingOrderPersistence) {
		this.shoppingOrderPersistence = shoppingOrderPersistence;
	}

	/**
	 * Returns the shopping order finder.
	 *
	 * @return the shopping order finder
	 */
	public ShoppingOrderFinder getShoppingOrderFinder() {
		return shoppingOrderFinder;
	}

	/**
	 * Sets the shopping order finder.
	 *
	 * @param shoppingOrderFinder the shopping order finder
	 */
	public void setShoppingOrderFinder(ShoppingOrderFinder shoppingOrderFinder) {
		this.shoppingOrderFinder = shoppingOrderFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the mail remote service.
	 *
	 * @return the mail remote service
	 */
	public com.liferay.mail.service.MailService getMailService() {
		return mailService;
	}

	/**
	 * Sets the mail remote service.
	 *
	 * @param mailService the mail remote service
	 */
	public void setMailService(com.liferay.mail.service.MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * Returns the company local service.
	 *
	 * @return the company local service
	 */
	public com.liferay.portal.service.CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	/**
	 * Sets the company local service.
	 *
	 * @param companyLocalService the company local service
	 */
	public void setCompanyLocalService(
		com.liferay.portal.service.CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	/**
	 * Returns the company remote service.
	 *
	 * @return the company remote service
	 */
	public com.liferay.portal.service.CompanyService getCompanyService() {
		return companyService;
	}

	/**
	 * Sets the company remote service.
	 *
	 * @param companyService the company remote service
	 */
	public void setCompanyService(
		com.liferay.portal.service.CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * Returns the company persistence.
	 *
	 * @return the company persistence
	 */
	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	/**
	 * Sets the company persistence.
	 *
	 * @param companyPersistence the company persistence
	 */
	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	/**
	 * Returns the subscription local service.
	 *
	 * @return the subscription local service
	 */
	public com.liferay.portal.service.SubscriptionLocalService getSubscriptionLocalService() {
		return subscriptionLocalService;
	}

	/**
	 * Sets the subscription local service.
	 *
	 * @param subscriptionLocalService the subscription local service
	 */
	public void setSubscriptionLocalService(
		com.liferay.portal.service.SubscriptionLocalService subscriptionLocalService) {
		this.subscriptionLocalService = subscriptionLocalService;
	}

	/**
	 * Returns the subscription persistence.
	 *
	 * @return the subscription persistence
	 */
	public SubscriptionPersistence getSubscriptionPersistence() {
		return subscriptionPersistence;
	}

	/**
	 * Sets the subscription persistence.
	 *
	 * @param subscriptionPersistence the subscription persistence
	 */
	public void setSubscriptionPersistence(
		SubscriptionPersistence subscriptionPersistence) {
		this.subscriptionPersistence = subscriptionPersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
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

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the message-boards message local service.
	 *
	 * @return the message-boards message local service
	 */
	public com.liferay.portlet.messageboards.service.MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	/**
	 * Sets the message-boards message local service.
	 *
	 * @param mbMessageLocalService the message-boards message local service
	 */
	public void setMBMessageLocalService(
		com.liferay.portlet.messageboards.service.MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	/**
	 * Returns the message-boards message remote service.
	 *
	 * @return the message-boards message remote service
	 */
	public com.liferay.portlet.messageboards.service.MBMessageService getMBMessageService() {
		return mbMessageService;
	}

	/**
	 * Sets the message-boards message remote service.
	 *
	 * @param mbMessageService the message-boards message remote service
	 */
	public void setMBMessageService(
		com.liferay.portlet.messageboards.service.MBMessageService mbMessageService) {
		this.mbMessageService = mbMessageService;
	}

	/**
	 * Returns the message-boards message persistence.
	 *
	 * @return the message-boards message persistence
	 */
	public MBMessagePersistence getMBMessagePersistence() {
		return mbMessagePersistence;
	}

	/**
	 * Sets the message-boards message persistence.
	 *
	 * @param mbMessagePersistence the message-boards message persistence
	 */
	public void setMBMessagePersistence(
		MBMessagePersistence mbMessagePersistence) {
		this.mbMessagePersistence = mbMessagePersistence;
	}

	/**
	 * Returns the message-boards message finder.
	 *
	 * @return the message-boards message finder
	 */
	public MBMessageFinder getMBMessageFinder() {
		return mbMessageFinder;
	}

	/**
	 * Sets the message-boards message finder.
	 *
	 * @param mbMessageFinder the message-boards message finder
	 */
	public void setMBMessageFinder(MBMessageFinder mbMessageFinder) {
		this.mbMessageFinder = mbMessageFinder;
	}

	/**
	 * Returns the shopping item local service.
	 *
	 * @return the shopping item local service
	 */
	public com.liferay.portlet.shopping.service.ShoppingItemLocalService getShoppingItemLocalService() {
		return shoppingItemLocalService;
	}

	/**
	 * Sets the shopping item local service.
	 *
	 * @param shoppingItemLocalService the shopping item local service
	 */
	public void setShoppingItemLocalService(
		com.liferay.portlet.shopping.service.ShoppingItemLocalService shoppingItemLocalService) {
		this.shoppingItemLocalService = shoppingItemLocalService;
	}

	/**
	 * Returns the shopping item remote service.
	 *
	 * @return the shopping item remote service
	 */
	public com.liferay.portlet.shopping.service.ShoppingItemService getShoppingItemService() {
		return shoppingItemService;
	}

	/**
	 * Sets the shopping item remote service.
	 *
	 * @param shoppingItemService the shopping item remote service
	 */
	public void setShoppingItemService(
		com.liferay.portlet.shopping.service.ShoppingItemService shoppingItemService) {
		this.shoppingItemService = shoppingItemService;
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

	/**
	 * Returns the shopping item field local service.
	 *
	 * @return the shopping item field local service
	 */
	public com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService getShoppingItemFieldLocalService() {
		return shoppingItemFieldLocalService;
	}

	/**
	 * Sets the shopping item field local service.
	 *
	 * @param shoppingItemFieldLocalService the shopping item field local service
	 */
	public void setShoppingItemFieldLocalService(
		com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService shoppingItemFieldLocalService) {
		this.shoppingItemFieldLocalService = shoppingItemFieldLocalService;
	}

	/**
	 * Returns the shopping item field persistence.
	 *
	 * @return the shopping item field persistence
	 */
	public ShoppingItemFieldPersistence getShoppingItemFieldPersistence() {
		return shoppingItemFieldPersistence;
	}

	/**
	 * Sets the shopping item field persistence.
	 *
	 * @param shoppingItemFieldPersistence the shopping item field persistence
	 */
	public void setShoppingItemFieldPersistence(
		ShoppingItemFieldPersistence shoppingItemFieldPersistence) {
		this.shoppingItemFieldPersistence = shoppingItemFieldPersistence;
	}

	/**
	 * Returns the shopping order item local service.
	 *
	 * @return the shopping order item local service
	 */
	public com.liferay.portlet.shopping.service.ShoppingOrderItemLocalService getShoppingOrderItemLocalService() {
		return shoppingOrderItemLocalService;
	}

	/**
	 * Sets the shopping order item local service.
	 *
	 * @param shoppingOrderItemLocalService the shopping order item local service
	 */
	public void setShoppingOrderItemLocalService(
		com.liferay.portlet.shopping.service.ShoppingOrderItemLocalService shoppingOrderItemLocalService) {
		this.shoppingOrderItemLocalService = shoppingOrderItemLocalService;
	}

	/**
	 * Returns the shopping order item persistence.
	 *
	 * @return the shopping order item persistence
	 */
	public ShoppingOrderItemPersistence getShoppingOrderItemPersistence() {
		return shoppingOrderItemPersistence;
	}

	/**
	 * Sets the shopping order item persistence.
	 *
	 * @param shoppingOrderItemPersistence the shopping order item persistence
	 */
	public void setShoppingOrderItemPersistence(
		ShoppingOrderItemPersistence shoppingOrderItemPersistence) {
		this.shoppingOrderItemPersistence = shoppingOrderItemPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.shopping.model.ShoppingOrder",
			shoppingOrderLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.shopping.model.ShoppingOrder");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return ShoppingOrder.class;
	}

	protected String getModelClassName() {
		return ShoppingOrder.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = shoppingOrderPersistence.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = ShoppingOrderLocalService.class)
	protected ShoppingOrderLocalService shoppingOrderLocalService;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingOrderService.class)
	protected com.liferay.portlet.shopping.service.ShoppingOrderService shoppingOrderService;
	@BeanReference(type = ShoppingOrderPersistence.class)
	protected ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(type = ShoppingOrderFinder.class)
	protected ShoppingOrderFinder shoppingOrderFinder;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.mail.service.MailService.class)
	protected com.liferay.mail.service.MailService mailService;
	@BeanReference(type = com.liferay.portal.service.CompanyLocalService.class)
	protected com.liferay.portal.service.CompanyLocalService companyLocalService;
	@BeanReference(type = com.liferay.portal.service.CompanyService.class)
	protected com.liferay.portal.service.CompanyService companyService;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = com.liferay.portal.service.SubscriptionLocalService.class)
	protected com.liferay.portal.service.SubscriptionLocalService subscriptionLocalService;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBMessageLocalService.class)
	protected com.liferay.portlet.messageboards.service.MBMessageLocalService mbMessageLocalService;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBMessageService.class)
	protected com.liferay.portlet.messageboards.service.MBMessageService mbMessageService;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBMessageFinder.class)
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingItemLocalService.class)
	protected com.liferay.portlet.shopping.service.ShoppingItemLocalService shoppingItemLocalService;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingItemService.class)
	protected com.liferay.portlet.shopping.service.ShoppingItemService shoppingItemService;
	@BeanReference(type = ShoppingItemPersistence.class)
	protected ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(type = ShoppingItemFinder.class)
	protected ShoppingItemFinder shoppingItemFinder;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService.class)
	protected com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService shoppingItemFieldLocalService;
	@BeanReference(type = ShoppingItemFieldPersistence.class)
	protected ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingOrderItemLocalService.class)
	protected com.liferay.portlet.shopping.service.ShoppingOrderItemLocalService shoppingOrderItemLocalService;
	@BeanReference(type = ShoppingOrderItemPersistence.class)
	protected ShoppingOrderItemPersistence shoppingOrderItemPersistence;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}