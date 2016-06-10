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

package com.liferay.sync.engine.upgrade.v3_3_0;

import com.liferay.sync.engine.service.SyncLanClientService;
import com.liferay.sync.engine.service.SyncLanEndpointService;
import com.liferay.sync.engine.service.persistence.SyncLanClientPersistence;
import com.liferay.sync.engine.service.persistence.SyncLanEndpointPersistence;
import com.liferay.sync.engine.upgrade.BaseUpgradeProcess;

/**
 * @author Dennis Ju
 * @author Shinn Lok
 */
public class UpgradeProcess_3_3_0 extends BaseUpgradeProcess {

	@Override
	public int getThreshold() {
		return 3300;
	}

	@Override
	public void upgrade() throws Exception {
	}

	@Override
	public void upgradeSchema() throws Exception {
		SyncLanClientPersistence syncLanClientPersistence =
			SyncLanClientService.getSyncLanClientPersistence();

		if (!syncLanClientPersistence.isTableExists()) {
			syncLanClientPersistence.createTable();
		}

		SyncLanEndpointPersistence syncLanEndpointPersistence =
			SyncLanEndpointService.getSyncLanEndpointPersistence();

		if (!syncLanEndpointPersistence.isTableExists()) {
			syncLanEndpointPersistence.createTable();
		}
	}

}