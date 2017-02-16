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

package com.liferay.osb.salesforce.client;

import com.sforce.ws.ConnectorConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public class SalesforceConnectorImpl implements SalesforceConnector {

	public void afterPropertiesSet() {
		_connectorConfig.setAuthEndpoint(getAuthEndpoint());
		_connectorConfig.setConnectionTimeout(
			getConnectionTimeout() * _TIME_MINUTE);
		_connectorConfig.setPassword(getPassword());
		_connectorConfig.setReadTimeout(getReadTimeout() * _TIME_MINUTE);
		_connectorConfig.setUsername(getUserName());

		if (isDebugEnabled()) {
			_connectorConfig.setPrettyPrintXml(true);
			_connectorConfig.setTraceMessage(true);

			String baseDir = System.getProperty("default.liferay.home");

			if (baseDir == null) {
				baseDir = System.getProperty("user.dir") + "/liferay";
			}

			String path = baseDir + "/logs/salesforce.log";

			if (_logger.isInfoEnabled()) {
				_logger.info("Salesforce log file: " + path);
			}

			File file = new File(path);

			if (!file.exists()) {
				File parentFile = file.getParentFile();

				parentFile.mkdirs();

				try {
					file.createNewFile();
				}
				catch (IOException ioe) {
					_logger.error("Unable to create log file", ioe);
				}
			}

			try {
				_connectorConfig.setTraceFile(path);
			}
			catch (FileNotFoundException fnfe) {
				_logger.error("File not found", fnfe);
			}
		}
	}

	@Override
	public String getAuthEndpoint() {
		return _authEndpoint;
	}

	@Override
	public int getConnectionTimeout() {
		return _connectionTimeout;
	}

	@Override
	public ConnectorConfig getConnectorConfig() {
		return _connectorConfig;
	}

	@Override
	public String getPassword() {
		return _password;
	}

	@Override
	public int getReadTimeout() {
		return _readTimeout;
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public boolean isDebugEnabled() {
		return _debugEnabled;
	}

	@Override
	public void setAuthEndpoint(String authEndpoint) {
		_authEndpoint = authEndpoint;
	}

	@Override
	public void setConnectionTimeout(int connectionTimeout) {
		_connectionTimeout = connectionTimeout;
	}

	@Override
	public void setDebugEnabled(boolean debugEnabled) {
		_debugEnabled = debugEnabled;
	}

	@Override
	public void setPassword(String password) {
		_password = password;
	}

	@Override
	public void setReadTimeout(int readTimeout) {
		_readTimeout = readTimeout;
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	private static final int _TIME_MINUTE = 1000 * 60;

	private static final Logger _logger = LoggerFactory.getLogger(
		SalesforceConnectorImpl.class);

	private String _authEndpoint;
	private int _connectionTimeout = 1;
	private ConnectorConfig _connectorConfig = new ConnectorConfig();
	private boolean _debugEnabled;
	private String _password;
	private int _readTimeout = 1;
	private String _userName;

}