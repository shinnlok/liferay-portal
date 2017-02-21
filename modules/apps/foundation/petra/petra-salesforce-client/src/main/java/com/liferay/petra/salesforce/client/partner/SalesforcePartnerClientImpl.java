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

package com.liferay.petra.salesforce.client.partner;

import com.liferay.petra.salesforce.client.BaseSalesforceClientImpl;

import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.GetDeletedResult;
import com.sforce.soap.partner.GetUpdatedResult;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.UpsertResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public class SalesforcePartnerClientImpl
	extends BaseSalesforceClientImpl implements SalesforcePartnerClient {

	@Override
	public List<SaveResult> create(SObject[] sObjects)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		SaveResult[] saveResults = partnerConnection.create(sObjects);

		return toList(saveResults);
	}

	@Override
	public List<DeleteResult> delete(String[] salesforceKeys)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		DeleteResult[] deleteResults = partnerConnection.delete(salesforceKeys);

		return toList(deleteResults);
	}

	@Override
	public DescribeGlobalResult describeGlobal(int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.describeGlobal();
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod("describeGlobal");

		return (DescribeGlobalResult)invoke(method, null, retryCount);
	}

	@Override
	public List<DescribeSObjectResult> describeSObjects(
			String[] typeNames, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			DescribeSObjectResult[] describeSObjectResults =
				partnerConnection.describeSObjects(typeNames);

			return toList(describeSObjectResults);
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod("describeSObjects", String[].class);
		Object[] arguments = {typeNames};

		DescribeSObjectResult[] describeSObjectResults =
			(DescribeSObjectResult[])invoke(method, arguments, retryCount);

		return toList(describeSObjectResults);
	}

	@Override
	public GetDeletedResult getDeleted(
			String typeName, Calendar startCalendar, Calendar endCalendar,
			int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.getDeleted(
				typeName, startCalendar, endCalendar);
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod(
			"getDeleted", String.class, Calendar.class, Calendar.class);
		Object[] arguments = {typeName, startCalendar, endCalendar};

		return (GetDeletedResult)invoke(method, arguments, retryCount);
	}

	@Override
	public GetUpdatedResult getUpdated(
			String typeName, Calendar startCalendar, Calendar endCalendar,
			int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.getUpdated(
				typeName, startCalendar, endCalendar);
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod(
			"getUpdated", String.class, Calendar.class, Calendar.class);
		Object[] arguments = {typeName, startCalendar, endCalendar};

		return (GetUpdatedResult)invoke(method, arguments, retryCount);
	}

	@Override
	public LoginResult login(String username, String password, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.login(username, password);
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod("login", String.class, String.class);
		Object[] arguments = {username, password};

		return (LoginResult)invoke(method, arguments, retryCount);
	}

	@Override
	public QueryResult query(String queryString, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.query(queryString);
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod("query", String.class);
		Object[] arguments = {queryString};

		return (QueryResult)invoke(method, arguments, retryCount);
	}

	@Override
	public QueryResult queryAll(String queryString, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.queryAll(queryString);
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod("queryAll", String.class);
		Object[] arguments = {queryString};

		return (QueryResult)invoke(method, arguments, retryCount);
	}

	@Override
	public QueryResult queryMore(String queryLocator, int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			return partnerConnection.queryMore(queryLocator);
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod("queryMore", String.class);
		Object[] arguments = {queryLocator};

		return (QueryResult)invoke(method, arguments, retryCount);
	}

	@Override
	public List<SObject> retrieve(
			String fieldNames, String typeName, String[] salesforceKeys,
			int retryCount)
		throws ConnectionException {

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			SObject[] sObjects = partnerConnection.retrieve(
				fieldNames, typeName, salesforceKeys);

			return toList(sObjects);
		}
		catch (ConnectionException ce) {
			if (retryCount <= 0) {
				throw ce;
			}
		}

		Method method = getMethod(
			"retrieve", String.class, String.class, String[].class);
		Object[] arguments = {fieldNames, typeName, salesforceKeys};

		SObject[] sObjects = (SObject[])invoke(method, arguments, retryCount);

		return toList(sObjects);
	}

	@Override
	public List<SaveResult> update(SObject[] sObjects)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		SaveResult[] saveResults = partnerConnection.update(sObjects);

		return toList(saveResults);
	}

	@Override
	public List<UpsertResult> upsert(
			String salesforceExternalKeyFieldName, SObject[] sObjects)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		UpsertResult[] upsertResults = partnerConnection.upsert(
			salesforceExternalKeyFieldName, sObjects);

		return toList(upsertResults);
	}

	protected Method getMethod(String methodName, Class<?>... parameterTypes)
		throws ConnectionException {

		PartnerConnection partnerConnection = getPartnerConnection();

		Class<?> declaringClass = partnerConnection.getClass();

		MethodKey methodKey = new MethodKey(
			declaringClass, methodName, parameterTypes);

		Method method = _methods.get(methodKey);

		if (method != null) {
			return method;
		}

		try {
			method = declaringClass.getMethod(methodName, parameterTypes);
		}
		catch (NoSuchMethodException nsme) {
			throw new ConnectionException("Unable to find method", nsme);
		}

		_methods.put(methodKey, method);

		return method;
	}

	protected Object invoke(Method method, Object[] arguments, int retryCount)
		throws ConnectionException {

		for (int i = 0; i < retryCount; i++) {
			if (_logger.isInfoEnabled()) {
				_logger.info(
					"Retrying: " + method.getName() + " (" + (i + 1) + ")");
			}

			try {
				return method.invoke(getPartnerConnection(), arguments);
			}
			catch (InvocationTargetException ite) {
				if ((i + 1) >= retryCount) {
					Throwable throwable = ite.getCause();

					if (throwable instanceof ConnectionException) {
						throw (ConnectionException)throwable;
					}

					throw new ConnectionException(
						"Unable to invoke method", throwable);
				}
			}
			catch (Exception e) {
				throw new ConnectionException("Unable to invoke method", e);
			}
		}

		return null;
	}

	protected <E> List<E> toList(E[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<>();
		}

		return new ArrayList<>(Arrays.asList(array));
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SalesforcePartnerClientImpl.class);

	private final Map<MethodKey, Method> _methods = new ConcurrentHashMap<>();

	private class MethodKey {

		public MethodKey(
			Class<?> declaringClass, String methodName,
			Class<?>... parameterTypes) {

			_declaringClass = declaringClass;
			_methodName = methodName;
			_parameterTypes = parameterTypes;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof MethodKey)) {
				return false;
			}

			MethodKey methodKey = (MethodKey)obj;

			if ((_declaringClass == methodKey._declaringClass) &&
				_methodName.equals(methodKey._methodName) &&
				Arrays.equals(_parameterTypes, methodKey._parameterTypes)) {

				return true;
			}

			return false;
		}

		private Class<?> _declaringClass;
		private final String _methodName;
		private final Class<?>[] _parameterTypes;

	}

}