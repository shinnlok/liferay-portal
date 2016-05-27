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

package com.liferay.sync.engine.lan;

import com.liferay.sync.engine.documentlibrary.handler.Handler;
import com.liferay.sync.engine.lan.util.LanClientUtil;
import com.liferay.sync.engine.lan.util.LanPEMUtil;
import com.liferay.sync.engine.lan.util.LanTokenUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncLanClient;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncLanClientService;
import com.liferay.sync.engine.service.SyncLanEndpointService;

import java.io.IOException;

import java.net.Socket;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.liferay.sync.engine.util.PropsValues;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanSession {

	private static LanSession _lanSession;

	public static LanSession getLanSession() {
		if (_lanSession == null) {
			_lanSession = new LanSession();
		}

		return _lanSession;
	}

	public LanSession() {
		startTrackTransferRate();
		RegistryBuilder<ConnectionSocketFactory> registryBuilder =
			RegistryBuilder.create();

		try {
			SSLConnectionSocketFactory sslConnectionSocketFactory =
				_getSSLSocketFactory();

			registryBuilder.register("https", sslConnectionSocketFactory);
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}

		Registry<ConnectionSocketFactory> registry = registryBuilder.build();

		PoolingHttpClientConnectionManager connectionManager =
			new PoolingHttpClientConnectionManager(registry);

		connectionManager.setMaxTotal(50);
		connectionManager.setDefaultMaxPerRoute(10);

//		httpClientBuilder.setConnectionManagerShared(true);

		HttpClientBuilder httpClientBuilder = _createHttpClientBuilder();

		httpClientBuilder.setConnectionManager(connectionManager);

		_httpClient = httpClientBuilder.build();
	}

	private static String _getUrl(
		String hostname, int port, SyncFile syncFile) {
		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncFile.getSyncAccountId());

		StringBuilder sb = new StringBuilder("https://");

		sb.append(hostname);
		sb.append(":");
		sb.append(port);
		sb.append("/");
		sb.append(syncAccount.getLanServerId());
		sb.append("/");
		sb.append(syncFile.getRepositoryId());
		sb.append("/");
		sb.append(syncFile.getTypePK());
		sb.append("/");
		sb.append(syncFile.getVersionId());

		return sb.toString();
	}

	public void downloadFile(SyncFile syncFile, final Handler handler)
		throws Exception {

		String key = syncFile.getKey();

		if (key == null || key.isEmpty()) {
			System.out.println("I don't have the key");
			return;
		}

		Object[] objects = findSyncLanClient(syncFile);

		if (objects == null) {
			handler.handleException(new Exception("none found"));

			return;
		}

		SyncLanClient syncLanClient = (SyncLanClient)objects[0];

		String url = _getUrl(
			syncLanClient.getHostname(), syncLanClient.getPort(), syncFile);

		final HttpGet httpGet = new HttpGet(url);

		String encryptedToken = (String)objects[1];

		String token = LanTokenUtil.decryptToken(key, encryptedToken);

		httpGet.addHeader("token", token);

//		HttpResponse httpResponse = _httpClient.execute(
//			httpGet, HttpClientContext.create());

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					_httpClient.execute(
						httpGet, handler, HttpClientContext.create());
				}
				catch (Exception e) {
					handler.handleException(e);
				}
			}

		};

		_executorService.execute(runnable);

//		System.out.println(httpResponse.getStatusLine());
//
//		if (httpResponse.getStatusLine().getStatusCode() != 200) {
//			return;
//		}
//			String output = EntityUtils.toString(httpResponse.getEntity());

//			System.out.println(output);

//		HttpEntity httpEntity = httpResponse.getEntity();
//
//		InputStream inputStream =
//new CountingInputStream(httpEntity.getContent()) {
//
//
//			@Override
//			protected synchronized void afterRead(int n) {
//				incrementDownloadedBytes(n);
//
//				super.afterRead(n);
//			}
//
//		};
//
//		OutputStream os = new FileOutputStream(
//	new File("/Users/deju/Desktop/testing123.pptx"));
//
//		long start = System.currentTimeMillis();
//		System.out.println("start");
//
//		IOUtils.copy(inputStream, os);
//
//		long end = System.currentTimeMillis();
//		System.out.println("end: " + ((end - start)/1000));
//
//		EntityUtils.consumeQuietly(httpResponse.getEntity());
	}

	public Object[] findSyncLanClient(SyncFile syncFile) throws Exception {
		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncFile.getSyncAccountId());

		List<String> syncLanClientUuids =
			SyncLanEndpointService.findSyncLanClientUuids(
				syncAccount.getLanServerId(), syncFile.getRepositoryId());

		List<SyncLanClient> syncLanClients = new ArrayList<>(
			syncLanClientUuids.size());

		for (String syncLanClientUuid : syncLanClientUuids) {
			syncLanClients.add(
				SyncLanClientService.fetchSyncLanClient(syncLanClientUuid));
		}

		List<Callable<Object[]>> callables = new ArrayList<>();

		for (final SyncLanClient syncLanClient : syncLanClients) {
			String url = _getUrl(
				syncLanClient.getHostname(), syncLanClient.getPort(), syncFile);

			final HttpHead httpHead = new HttpHead(url);

			Callable<Object[]> callable = new Callable<Object[]>() {

				@Override
				public Object[] call() throws Exception {
					HttpResponse httpResponse = _httpClient.execute(
						httpHead, HttpClientContext.create());

					StatusLine statusLine = httpResponse.getStatusLine();

					if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
						throw new Exception();
					}

					Header[] headers = httpResponse.getHeaders("token");

					if (headers.length == 0) {
						throw new Exception("");
					}

					Header header = headers[0];

					String token = header.getValue();

					return new Object[] {syncLanClient, token};
				}

			};

			callables.add(callable);
		}

		try {
//			List<Future<String[]>> futures =
			//	_getExecutorService().invokeAll(callables, 5000, TimeUnit.MILLISECONDS);

			return _getExecutorService().invokeAny(
				callables, 5000, TimeUnit.MILLISECONDS);
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}

		return null;
	}

	private static HttpClientBuilder _createHttpClientBuilder() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.disableAutomaticRetries();

		RequestConfig.Builder builder = RequestConfig.custom();

		builder.setConnectTimeout(500);
		builder.setSocketTimeout(10000);

		httpClientBuilder.setDefaultRequestConfig(builder.build());
		httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());

		return httpClientBuilder;
	}

	private static ThreadPoolExecutor _threadPoolExecutor;

	private static final Logger _logger = LoggerFactory.getLogger(
		LanSession.class);

	private static SSLConnectionSocketFactory _getSSLSocketFactory()
		throws Exception {

		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

		keyStore.load(null, null);

		for (SyncAccount syncAccount : SyncAccountService.findAll()) {
			if (!syncAccount.isActive()) {
				continue;
			}

			PrivateKey privateKey = LanPEMUtil.getPrivateKey(
				syncAccount.getLanKey());

			if (privateKey == null) {
				_logger.error(
					"SyncAccount {} missing valid private key",
					syncAccount.getSyncAccountId());

				continue;
			}

			X509Certificate x509Certificate = LanPEMUtil.getX509Certificate(
				syncAccount.getLanCertificate());

			if (x509Certificate == null) {
				_logger.error(
					"SyncAccount {} missing valid certificate",
					syncAccount.getSyncAccountId());

				continue;
			}

			keyStore.setCertificateEntry(
				syncAccount.getLanServerId(), x509Certificate);

			keyStore.setKeyEntry(
				syncAccount.getLanServerId(), privateKey, "".toCharArray(),
				new Certificate[] {x509Certificate});
		}

		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
			KeyManagerFactory.getDefaultAlgorithm());

		keyManagerFactory.init(keyStore, "".toCharArray());

		TrustManagerFactory trustManagerFactory =
			TrustManagerFactory.getInstance(
				TrustManagerFactory.getDefaultAlgorithm());

		trustManagerFactory.init(keyStore);

		SSLContext sslContext = SSLContext.getInstance("TLS");

		sslContext.init(
			keyManagerFactory.getKeyManagers(),
			trustManagerFactory.getTrustManagers(), null);

		return new SSLConnectionSocketFactory(
			sslContext, new NoopHostnameVerifier()) {

				@Override
				public Socket createLayeredSocket(
						final Socket socket, final String target,
						final int port, final HttpContext httpContext)
					throws IOException {

					HttpClientContext httpClientContext =
						(HttpClientContext)httpContext;

					HttpRequest httpRequest = httpClientContext.getRequest();

					RequestLine requestLine = httpRequest.getRequestLine();

					String[] parts = StringUtils.split(
						requestLine.getUri(), "/");

					String sniCompliantLanServerId =
						LanClientUtil.getSNICompliantLanServerId(parts[0]);

//					return super.createLayeredSocket(
//						socket, target, port, httpContext);
//
					return super.createLayeredSocket(
						socket, sniCompliantLanServerId, port, httpContext);
				}

			};
	}

	public int getUploadRate() {
		return _uploadRate;
	}

	public void incrementDownloadedBytes(int bytes) {
		_downloadedBytes.getAndAdd(bytes);
	}

	public void incrementUploadedBytes(int bytes) {
		_uploadedBytes.getAndAdd(bytes);
	}

	public void startTrackTransferRate() {
		if ((_trackTransferRateScheduledFuture != null) &&
			!_trackTransferRateScheduledFuture.isDone()) {

			return;
		}

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				_downloadRate = _downloadedBytes.get();

				_downloadedBytes.set(0);

				_uploadRate = _uploadedBytes.get();

				_uploadedBytes.set(0);
			}

		};

		_trackTransferRateScheduledFuture =
			_scheduledExecutorService.scheduleWithFixedDelay(
				runnable, 0, 1, TimeUnit.SECONDS);
	}

	public void stopTrackTransferRate() {
		if (_trackTransferRateScheduledFuture == null) {
			return;
		}

		_trackTransferRateScheduledFuture.cancel(false);
	}

	private static ExecutorService _getExecutorService() {
		if (_threadPoolExecutor != null) {
			return _threadPoolExecutor;
		}

		_threadPoolExecutor = new ThreadPoolExecutor(
			32, 32, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

		_threadPoolExecutor.allowCoreThreadTimeOut(true);

		return _threadPoolExecutor;
	}

	private final HttpClient _httpClient;

	private final AtomicInteger _downloadedBytes = new AtomicInteger(0);
	private volatile int _downloadRate;

	private ScheduledFuture<?> _trackTransferRateScheduledFuture;
	private final AtomicInteger _uploadedBytes = new AtomicInteger(0);
	private volatile int _uploadRate;
	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newSingleThreadScheduledExecutor();
	private final ExecutorService _executorService =
		Executors.newCachedThreadPool();

}