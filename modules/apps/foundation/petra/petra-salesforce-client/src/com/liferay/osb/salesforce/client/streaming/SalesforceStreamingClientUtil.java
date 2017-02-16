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

package com.liferay.osb.salesforce.client.streaming;

import org.cometd.bayeux.Channel;

/**
 * @author Brian Wing Shun Chan
 * @author Rachael Koestartyo
 * @author Peter Shin
 */
public class SalesforceStreamingClientUtil {

	public static boolean connect() {
		return getSalesforceStreamingClient().connect();
	}

	public static boolean disconnect() {
		return getSalesforceStreamingClient().disconnect();
	}

	public static Channel getChannel(String name) {
		return getSalesforceStreamingClient().getChannel(name);
	}

	public static SalesforceStreamingClient getSalesforceStreamingClient() {
		return _salesforceStreamingClient;
	}

	public void setSalesforceStreamingClient(
		SalesforceStreamingClient salesforceStreamingClient) {

		_salesforceStreamingClient = salesforceStreamingClient;
	}

	private static SalesforceStreamingClient _salesforceStreamingClient;

}