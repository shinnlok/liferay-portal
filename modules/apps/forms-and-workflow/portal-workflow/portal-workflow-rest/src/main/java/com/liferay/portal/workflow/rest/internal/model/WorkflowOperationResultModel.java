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

package com.liferay.portal.workflow.rest.internal.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam Brandizzi
 */
@XmlRootElement
public class WorkflowOperationResultModel {

	public static final String STATUS_ERROR = "error";

	public static final String STATUS_SUCCESS = "success";

	public WorkflowOperationResultModel() {
	}

	public WorkflowOperationResultModel(String status) {
		this(status, null);
	}

	public WorkflowOperationResultModel(String status, String message) {
		_status = status;
		_message = message;
	}

	@XmlElement
	public String getMessage() {
		return _message;
	}

	@XmlElement
	public String getStatus() {
		return _status;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setStatus(String status) {
		_status = status;
	}

	private String _message;
	private String _status;

}