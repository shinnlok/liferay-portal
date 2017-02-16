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

package com.liferay.osb.salesforce.client.bulk;

import com.sforce.async.AsyncApiException;
import com.sforce.async.BatchInfo;
import com.sforce.async.JobInfo;
import com.sforce.async.QueryResultList;
import com.sforce.ws.ConnectionException;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public class SalesforceBulkClientUtil {

	public static JobInfo abortJob(String jobInfoId, int retryCount)
		throws AsyncApiException, ConnectionException {

		return getSalesforceBulkClient().abortJob(jobInfoId, retryCount);
	}

	public static JobInfo closeJob(String jobInfoId, int retryCount)
		throws AsyncApiException, ConnectionException {

		return getSalesforceBulkClient().closeJob(jobInfoId, retryCount);
	}

	public static BatchInfo createBatchFromStream(
			JobInfo jobInfo, InputStream inputStream, int retryCount)
		throws AsyncApiException, ConnectionException {

		return getSalesforceBulkClient().createBatchFromStream(
			jobInfo, inputStream, retryCount);
	}

	public static JobInfo createJob(JobInfo jobInfo, int retryCount)
		throws AsyncApiException, ConnectionException {

		return getSalesforceBulkClient().createJob(jobInfo, retryCount);
	}

	public static BatchInfo getBatchInfo(
			String jobInfoId, String batchInfoId, int retryCount)
		throws AsyncApiException, ConnectionException {

		return getSalesforceBulkClient().getBatchInfo(
			jobInfoId, batchInfoId, retryCount);
	}

	public static QueryResultList getQueryResultList(
			String jobInfoId, String batchInfoId, int retryCount)
		throws AsyncApiException, ConnectionException {

		return getSalesforceBulkClient().getQueryResultList(
			jobInfoId, batchInfoId, retryCount);
	}

	public static InputStream getQueryResultStream(
			String jobInfoId, String batchInfoId, String queryResultId,
			int retryCount)
		throws AsyncApiException, ConnectionException {

		return getSalesforceBulkClient().getQueryResultStream(
			jobInfoId, batchInfoId, queryResultId, retryCount);
	}

	public static SalesforceBulkClient getSalesforceBulkClient() {
		return _salesforceBulkClient;
	}

	public void setSalesforceBulkClient(
		SalesforceBulkClient salesforceBulkClient) {

		_salesforceBulkClient = salesforceBulkClient;
	}

	private static SalesforceBulkClient _salesforceBulkClient;

}