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

import java.io.StringReader;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

/**
 * @author Dennis Ju
 */
public class LanPEMUtil {

	public static PrivateKey getPrivateKey(String privateKey) throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("-----BEGIN PRIVATE KEY-----\n");
		sb.append(privateKey);
		sb.append("-----END PRIVATE KEY-----\n");

		PEMParser pemParser = new PEMParser(new StringReader(sb.toString()));

		Object object = pemParser.readObject();

		if (object instanceof PrivateKeyInfo) {
			PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo)object;

			JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();

			return jcaPEMKeyConverter.getPrivateKey(privateKeyInfo);
		}

		return null;
	}

	public static X509Certificate getX509Certificate(String certificate)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("-----BEGIN CERTIFICATE-----\n");
		sb.append(certificate);
		sb.append("-----END CERTIFICATE-----\n");

		PEMParser pemParser = new PEMParser(new StringReader(sb.toString()));

		Object object = pemParser.readObject();

		if (object instanceof X509CertificateHolder) {
			X509CertificateHolder x509CertificateHolder =
				(X509CertificateHolder)object;

			JcaX509CertificateConverter jcaX509CertificateConverter =
				new JcaX509CertificateConverter();

			return jcaX509CertificateConverter.getCertificate(
				x509CertificateHolder);
		}

		return null;
	}

}