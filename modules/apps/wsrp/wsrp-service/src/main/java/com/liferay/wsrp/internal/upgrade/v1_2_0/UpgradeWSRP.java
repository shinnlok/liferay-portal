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

package com.liferay.wsrp.internal.upgrade.v1_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class UpgradeWSRP extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateWSRPConsumers();
	}

	protected List<String> getWSRPProducerUuids() throws Exception {
		List<String> uuids = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select uuid_ from WSRP_WSRPProducer");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				uuids.add(rs.getString(1));
			}
		}

		return uuids;
	}

	protected void updateWSRPConsumer(
			long wsrpConsumerId, String url, String wsdl)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update WSRP_WSRPConsumer set url = ?, wsdl = ? where " +
					"wsrpConsumerId = ?")) {

			ps.setString(1, url);
			ps.setString(2, wsdl);
			ps.setLong(3, wsrpConsumerId);

			ps.executeUpdate();
		}
	}

	protected void updateWSRPConsumers() throws Exception {
		List<String> uuids = getWSRPProducerUuids();

		try (PreparedStatement ps = connection.prepareStatement(
				"select wsrpConsumerId, url, wsdl from WSRP_WSRPConsumer");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long wsrpConsumerId = rs.getLong(1);
				String url = rs.getString(2);
				String wsdl = rs.getString(3);

				String uuid = url.substring(
					url.lastIndexOf(StringPool.SLASH) + 1);

				if (!uuids.contains(uuid)) {
					continue;
				}

				url = url.replace("/wsrp-portlet/", "/o/wsrp-service/");
				wsdl = wsdl.replace("/wsrp-portlet/", "/o/wsrp-service/");

				updateWSRPConsumer(wsrpConsumerId, url, wsdl);
			}
		}
	}

}