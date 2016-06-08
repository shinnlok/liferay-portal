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

package com.liferay.sync.verify;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.sync.constants.SyncDLObjectConstants;
import com.liferay.sync.service.configuration.SyncServiceConfigurationKeys;
import com.liferay.sync.service.configuration.SyncServiceConfigurationValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 * @author Dennis Ju
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.sync.service"},
	service = VerifyProcess.class
)
public class SyncServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (final Company company : companies) {
			if (!PrefsPropsUtil.getBoolean(
					company.getCompanyId(),
					SyncServiceConfigurationKeys.SYNC_SERVICES_ENABLED,
					SyncServiceConfigurationValues.SYNC_SERVICES_ENABLED)) {

				continue;
			}

			ActionableDynamicQuery actionableDynamicQuery =
				GroupLocalServiceUtil.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				new ActionableDynamicQuery.AddCriteriaMethod() {

					@Override
					public void addCriteria(DynamicQuery dynamicQuery) {
						Property classNameId = PropertyFactoryUtil.forName(
							"classNameId");
						Property siteProperty = PropertyFactoryUtil.forName(
							"site");

						dynamicQuery.add(
							RestrictionsFactoryUtil.or(
								classNameId.eq(
									PortalUtil.getClassNameId(User.class)),
								siteProperty.eq(true)));

						dynamicQuery.add(
							RestrictionsFactoryUtil.eq(
								"companyId", company.getCompanyId()));
					}

				});
			actionableDynamicQuery.setParallel(true);
			actionableDynamicQuery.setPerformActionMethod(
				new ActionableDynamicQuery.PerformActionMethod<Group>() {

					@Override
					public void performAction(Group group) {
						if (group.isStaged()) {
							return;
						}

						_log.error(
							"DEJU: Verifying group " + group.getGroupId());

						if (_log.isDebugEnabled()) {
							_log.debug("Verifying group " + group.getGroupId());
						}

						try {
							verifyDLFileEntriesAndFolders(
								company.getCompanyId(), group.getGroupId());

							verifyLocks(
								company.getCompanyId(), group.getGroupId());

							verifyMacPackages(
								company.getCompanyId(), group.getGroupId());
						}
						catch (Exception e) {
							_log.error(e, e);
						}

						_log.error(
							"DEJU: DONE with group " + group.getGroupId());
					}

				});

			actionableDynamicQuery.performActions();

			if (_log.isDebugEnabled()) {
				_log.debug("Verification completed");
			}
		}
	}

	protected void verifyDLFileEntriesAndFolders(long companyId, long groupId)
		throws Exception {

		StringBundler sb = new StringBundler(61);

		sb.append("select DLFolder.companyId, DLFolder.userId, ");
		sb.append("DLFolder.userName, DLFolder.createDate, ");
		sb.append("DLFolder.modifiedDate, DLFolder.repositoryId, ");
		sb.append("DLFolder.parentFolderId as parentFolderId, ");
		sb.append("DLFolder.treePath, DLFolder.name, null as extension, ");
		sb.append("null as mimeType, DLFolder.description, null as ");
		sb.append("changeLog, null as version, null as versionId, 0 as size, ");
		sb.append(
			StringUtil.quote(
				SyncDLObjectConstants.TYPE_FOLDER, CharPool.APOSTROPHE));
		sb.append(" as type, DLFolder.folderId as typePK, ");
		sb.append("DLFolder.uuid_ as typeUuid, DLFolder.status ");
		sb.append("from DLFolder where DLFolder.companyId = ");
		sb.append(companyId);
		sb.append(" and DLFolder.repositoryId = ");
		sb.append(groupId);
		sb.append(" union all select DLFileVersion.companyId, ");
		sb.append("DLFileVersion.userId, DLFileVersion.userName, ");
		sb.append("DLFileVersion.createDate, DLFileVersion.modifiedDate, ");
		sb.append("DLFileVersion.repositoryId, DLFileVersion.folderId as ");
		sb.append("parentFolderId, DLFileVersion.treePath, ");
		sb.append("DLFileVersion.title as name, DLFileVersion.extension, ");
		sb.append("DLFileVersion.mimeType, DLFileVersion.description, ");
		sb.append("DLFileVersion.changeLog, DLFileVersion.version, ");
		sb.append("DLFileVersion.fileVersionId as versionId, ");
		sb.append("DLFileVersion.size_ as size, ");
		sb.append(
			StringUtil.quote(
				SyncDLObjectConstants.TYPE_FILE, CharPool.APOSTROPHE));
		sb.append(" as type, DLFileVersion.fileEntryId as typePK, ");
		sb.append("DLFileEntry.uuid_ as typeUuid, DLFileVersion.status ");
		sb.append("from DLFileEntry, DLFileVersion where ");
		sb.append("DLFileEntry.companyId = ");
		sb.append(companyId);
		sb.append(" and DLFileEntry.repositoryId = ");
		sb.append(groupId);
		sb.append(" and DLFileEntry.fileEntryId = DLFileVersion.fileEntryId ");
		sb.append("and DLFileEntry.version = DLFileVersion.version ");
		sb.append("union all select DLFileVersion.companyId, ");
		sb.append("DLFileVersion.userId, DLFileVersion.userName, ");
		sb.append("DLFileVersion.createDate, DLFileVersion.modifiedDate, ");
		sb.append("DLFileVersion.repositoryId, DLFileVersion.folderId  ");
		sb.append("as parentFolderId, DLFileVersion.treePath, ");
		sb.append("DLFileVersion.title as name, DLFileVersion.extension, ");
		sb.append("DLFileVersion.mimeType, DLFileVersion.description, ");
		sb.append("DLFileVersion.changeLog, DLFileVersion.version, ");
		sb.append("DLFileVersion.fileVersionId as versionId, ");
		sb.append("DLFileVersion.size_ as size, ");
		sb.append(
			StringUtil.quote(
				SyncDLObjectConstants.TYPE_PRIVATE_WORKING_COPY,
				CharPool.APOSTROPHE));
		sb.append(" as type, DLFileVersion.fileEntryId as typePK, ");
		sb.append("DLFileEntry.uuid_ as typeUuid, DLFileVersion.status ");
		sb.append("from DLFileEntry, DLFileVersion where ");
		sb.append("DLFileEntry.companyId = ");
		sb.append(companyId);
		sb.append(" and DLFileEntry.repositoryId = ");
		sb.append(groupId);
		sb.append(" and DLFileEntry.fileEntryId = DLFileVersion.fileEntryId ");
		sb.append("and DLFileVersion.version = ");
		sb.append(
			StringUtil.quote(
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
				CharPool.APOSTROPHE));

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"insert into SyncDLObject (syncDLObjectId, companyId, " +
						"userId, userName, createTime, modifiedTime, " +
						"repositoryId, parentFolderId, treePath, name, " +
						"extension, mimeType, description, changeLog, " +
						"version, versionId, size_, event, type_, typePK, " +
						"typeUuid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				int status = rs.getInt("status");

				String event = StringPool.BLANK;

				if (status == WorkflowConstants.STATUS_IN_TRASH) {
					event = SyncDLObjectConstants.EVENT_TRASH;
				}
				else {
					event = SyncDLObjectConstants.EVENT_ADD;
				}

				ps2.setLong(1, _increment());
				ps2.setLong(2, companyId);
				ps2.setLong(3, rs.getLong("userId"));
				ps2.setString(4, rs.getString("userName"));
				ps2.setLong(5, createDate.getTime());
				ps2.setLong(6, modifiedDate.getTime());
				ps2.setLong(7, groupId);
				ps2.setLong(8, rs.getLong("parentFolderId"));
				ps2.setString(9, rs.getString("treePath"));
				ps2.setString(10, rs.getString("name"));
				ps2.setString(11, rs.getString("extension"));
				ps2.setString(12, rs.getString("mimeType"));
				ps2.setString(13, rs.getString("description"));
				ps2.setString(14, rs.getString("changeLog"));
				ps2.setString(15, rs.getString("version"));
				ps2.setLong(16, rs.getLong("versionId"));
				ps2.setLong(17, rs.getLong("size"));
				ps2.setString(18, event);
				ps2.setString(19, rs.getString("type"));
				ps2.setLong(20, rs.getLong("typePK"));
				ps2.setString(21, rs.getString("typeUuid"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void verifyLocks(long companyId, long groupId) throws Exception {
		StringBundler sb1 = new StringBundler(6);

		sb1.append("update SyncDLObject set lockExpirationDate = ?, ");
		sb1.append("lockUserId = ?, lockUserName = ? where typePK = ? and ");
		sb1.append("companyId = ");
		sb1.append(companyId);
		sb1.append(" and repositoryId = ");
		sb1.append(groupId);

		StringBundler sb2 = new StringBundler(5);

		sb2.append("select Lock_.expirationDate, Lock_.userId, ");
		sb2.append("Lock_.userName, DLFileVersion.fileEntryId from ");
		sb2.append("DLFileVersion, Lock_ where DLFileVersion.version = ");
		sb2.append(
			StringUtil.quote(
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
				CharPool.APOSTROPHE));
		sb2.append(" and DLFileVersion.fileEntryId = Lock_.key_");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(sb2.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb1.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				ps2.setTimestamp(1, rs.getTimestamp("expirationDate"));
				ps2.setLong(2, rs.getLong("userId"));
				ps2.setString(3, rs.getString("userName"));
				ps2.setLong(4, rs.getLong("fileEntryId"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void verifyMacPackages(long companyId, long groupId)
		throws Exception {

		StringBundler sb1 = new StringBundler();

		String[] fileNames =
			SyncServiceConfigurationValues.SYNC_MAC_PACKAGE_METADATA_FILE_NAMES;

		for (int i = 0; i < fileNames.length; i++) {
			sb1.append(StringUtil.quote(fileNames[i], CharPool.APOSTROPHE));

			if (i != fileNames.length - 1) {
				sb1.append(CharPool.COMMA);
			}
		}

		StringBundler sb2 = new StringBundler(9);

		sb2.append("select DLFolder.folderId, DLFolder.name from DLFolder, ");
		sb2.append("DLFileEntry where DLFolder.folderId = ");
		sb2.append("DLFileEntry.folderId and DLFolder.companyId = ");
		sb2.append(companyId);
		sb2.append(" and DLFolder.repositoryId = ");
		sb2.append(groupId);
		sb2.append(" AND DLFileEntry.title in (");
		sb2.append(sb1.toString());
		sb2.append(")");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(sb2.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SyncDLObject set extraSettings = ? where " +
						"typePK = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");

				String extension = FileUtil.getExtension(name);

				if (!ArrayUtil.contains(
						SyncServiceConfigurationValues.
							SYNC_MAC_PACKAGE_FOLDER_EXTENSIONS, extension)) {

					continue;
				}

				JSONObject extraSettingsJSONObject =
					JSONFactoryUtil.createJSONObject();

				extraSettingsJSONObject.put("macPackage", true);

				ps2.setString(1, extraSettingsJSONObject.toString());
				ps2.setLong(2, folderId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private static long _increment() {
		DB db = DBManagerUtil.getDB();

		return db.increment();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SyncServiceVerifyProcess.class);

}