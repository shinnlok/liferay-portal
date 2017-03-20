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

package com.liferay.sync.engine.file.system.listener;

import java.nio.file.Path;

/**
 * @author Michael Young
 */
public interface WatchEventListener {

	public void addDeletedFilePathName(String filePathName);

	public void addDownloadedFilePathName(String filePathName);

	public void addRenamedFilePathName(String filePathName);

	public long getSyncAccountId();

	public void removeDeletedFilePathName(String filePathName);

	public void removeDownloadedFilePathName(String filePathName);

	public void removeRenamedFilePathName(String filePathName);

	public void watchEvent(String eventType, Path filePath);

}