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

package com.liferay.petra.salesforce.client.streaming;

import com.liferay.petra.salesforce.client.BaseSalesforceClientImpl;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectorConfig;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.transport.LongPollingTransport;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Rachael Koestartyo
 * @author Peter Shin
 */
public class SalesforceStreamingClientImpl
	extends BaseSalesforceClientImpl implements SalesforceStreamingClient {

	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		try {
			PartnerConnection partnerConnection = getPartnerConnection();

			ConnectorConfig connectorConfig = partnerConnection.getConfig();

			String sessionId = connectorConfig.getSessionId();
			int transportTimeout = getTransportTimeout() * _TIME_MINUTE;
			URL url = new URL(connectorConfig.getServiceEndpoint());

			Map<String, Object> options = new HashMap<>();

			options.put(ClientTransport.TIMEOUT_OPTION, transportTimeout);

			_httpClient.start();

			_bayeuxClient = new BayeuxClient(
				url.getProtocol() + "://" + url.getHost() + "/cometd/37.0",
				new SalesforceTransport(sessionId, options, _httpClient));

			ClientSessionChannel handshakeClientSessionChannel =
				_bayeuxClient.getChannel(Channel.META_HANDSHAKE);

			handshakeClientSessionChannel.addListener(
				new SalesforceMessageListener());

			ClientSessionChannel connectClientSessionChannel =
				_bayeuxClient.getChannel(Channel.META_CONNECT);

			connectClientSessionChannel.addListener(
				new SalesforceMessageListener());

			ClientSessionChannel subscribeClientSessionChannel =
				_bayeuxClient.getChannel(Channel.META_SUBSCRIBE);

			subscribeClientSessionChannel.addListener(
				new SalesforceMessageListener());
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean connect() {
		if (_bayeuxClient == null) {
			afterPropertiesSet();
		}

		boolean b = true;

		if (!_bayeuxClient.isConnected()) {
			_bayeuxClient.handshake();

			int x = 10 * _TIME_SECOND;

			b = _bayeuxClient.waitFor(x, BayeuxClient.State.CONNECTED);
		}

		if (_logger.isInfoEnabled()) {
			_logger.info("Connected: " + b);
		}

		return b;
	}

	public void destroy() {
		if (_bayeuxClient.isConnected()) {
			boolean disconnected = false;

			do {
				disconnected = disconnect();
			}
			while (!disconnected);
		}

		try {
			_httpClient.stop();
		}
		catch (Exception e) {
			_logger.error("Unable to stop http client", e);
		}
	}

	@Override
	public boolean disconnect() {
		boolean b = true;

		if (!_bayeuxClient.isDisconnected()) {
			_bayeuxClient.disconnect();

			int x = 10 * _TIME_SECOND;

			b = _bayeuxClient.waitFor(x, BayeuxClient.State.DISCONNECTED);
		}

		if (_logger.isInfoEnabled()) {
			_logger.info("Disconnected: " + b);
		}

		return b;
	}

	@Override
	public Channel getChannel(String name) {
		return _bayeuxClient.getChannel(name);
	}

	@Override
	public int getTransportTimeout() {
		return _transportTimeout;
	}

	@Override
	public void setTransportTimeout(int transportTimeout) {
		_transportTimeout = transportTimeout;
	}

	private static final int _TIME_MINUTE = 1000 * 60;

	private static final int _TIME_SECOND = 1000;

	private static final Logger _logger = LoggerFactory.getLogger(
		SalesforceStreamingClientImpl.class);

	private BayeuxClient _bayeuxClient;
	private final HttpClient _httpClient = new HttpClient();
	private int _transportTimeout = 1;

	private class SalesforceMessageListener implements MessageListener {

		@Override
		public void onMessage(
			ClientSessionChannel clientSessionChannel, Message message) {

			if (_logger.isInfoEnabled()) {
				_logger.info("Received message: " + message);
			}

			if (!message.isSuccessful()) {
				_logger.error("Unable to send message");

				if (message.get("error") != null) {
					_logger.error((String)message.get("error"));
				}

				if (message.get("exception") != null) {
					Exception e = (Exception)message.get("exception");

					e.printStackTrace();
				}

				_bayeuxClient.disconnect();
			}
		}

	}

	private class SalesforceTransport extends LongPollingTransport {

		public SalesforceTransport(
			String sessionId, Map<String, Object> options,
			HttpClient httpClient) {

			super(options, httpClient);

			_sessionId = sessionId;
		}

		@Override
		protected void customize(ContentExchange exchange) {
			super.customize(exchange);

			exchange.addRequestHeader("Authorization", "OAuth " + _sessionId);
		}

		private final String _sessionId;

	}

}