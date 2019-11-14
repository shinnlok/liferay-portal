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

package com.liferay.analytics.message.storage.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchMessageEntryException extends NoSuchModelException {

	public NoSuchMessageEntryException() {
	}

	public NoSuchMessageEntryException(String msg) {
		super(msg);
	}

	public NoSuchMessageEntryException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchMessageEntryException(Throwable cause) {
		super(cause);
	}

}