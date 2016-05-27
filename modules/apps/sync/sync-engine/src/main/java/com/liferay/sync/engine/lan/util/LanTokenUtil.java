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

package com.liferay.sync.engine.lan.util;

import java.nio.charset.Charset;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.jodah.expiringmap.ExpiringMap;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Dennis Ju
 */
public class LanTokenUtil {

	public static String createEncryptedToken(String key) throws Exception {
		UUID uuid = UUID.randomUUID();

		String tokenUuid = uuid.toString();

		String encryptedToken = encryptToken(key, tokenUuid);

		getTokens().add(tokenUuid);

		return encryptedToken;
	}

	public static String decryptToken(String key, String encryptedToken)
		throws Exception {

		byte[] bytes = DigestUtils.sha1(key);

		bytes = Arrays.copyOf(bytes, 16);

		SecretKey secretKey = new SecretKeySpec(bytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] decodedBytes = Base64.getDecoder().decode(encryptedToken);

		byte[] decryptedBytes = cipher.doFinal(decodedBytes);

		return new String(decryptedBytes, Charset.forName("UTF-8"));
	}

	public static String encryptToken(String key, String token)
		throws Exception {

		byte[] bytes = DigestUtils.sha1(key);

		bytes = Arrays.copyOf(bytes, 16);

		SecretKey secretKey = new SecretKeySpec(bytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] encryptedBytes = cipher.doFinal(
			token.getBytes(Charset.forName("UTF-8")));

		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static Set<String> getTokens() {
		if (_tokens == null) {
			ExpiringMap.Builder builder = ExpiringMap.builder();

			builder.expiration(30, TimeUnit.SECONDS);

			_tokens = Collections.newSetFromMap(builder.build());
		}

		return _tokens;
	}

	private static Set<String> _tokens;

}