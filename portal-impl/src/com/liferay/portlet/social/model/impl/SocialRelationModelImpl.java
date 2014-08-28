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

package com.liferay.portlet.social.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.SocialRelationModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the SocialRelation service. Represents a row in the &quot;SocialRelation&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.social.model.SocialRelationModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SocialRelationImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelationImpl
 * @see com.liferay.portlet.social.model.SocialRelation
 * @see com.liferay.portlet.social.model.SocialRelationModel
 * @generated
 */
@ProviderType
public class SocialRelationModelImpl extends BaseModelImpl<SocialRelation>
	implements SocialRelationModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a social relation model instance should use the {@link com.liferay.portlet.social.model.SocialRelation} interface instead.
	 */
	public static final String TABLE_NAME = "SocialRelation";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "relationId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "createDate", Types.BIGINT },
			{ "userId1", Types.BIGINT },
			{ "userId2", Types.BIGINT },
			{ "type_", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialRelation (uuid_ VARCHAR(75) null,relationId LONG not null primary key,companyId LONG,createDate LONG,userId1 LONG,userId2 LONG,type_ INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table SocialRelation";
	public static final String ORDER_BY_JPQL = " ORDER BY socialRelation.relationId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY SocialRelation.relationId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialRelation"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialRelation"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.social.model.SocialRelation"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long TYPE_COLUMN_BITMASK = 2L;
	public static final long USERID1_COLUMN_BITMASK = 4L;
	public static final long USERID2_COLUMN_BITMASK = 8L;
	public static final long UUID_COLUMN_BITMASK = 16L;
	public static final long RELATIONID_COLUMN_BITMASK = 32L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialRelation"));

	public SocialRelationModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _relationId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setRelationId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _relationId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SocialRelation.class;
	}

	@Override
	public String getModelClassName() {
		return SocialRelation.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("relationId", getRelationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("userId1", getUserId1());
		attributes.put("userId2", getUserId2());
		attributes.put("type", getType());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long relationId = (Long)attributes.get("relationId");

		if (relationId != null) {
			setRelationId(relationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long createDate = (Long)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long userId1 = (Long)attributes.get("userId1");

		if (userId1 != null) {
			setUserId1(userId1);
		}

		Long userId2 = (Long)attributes.get("userId2");

		if (userId2 != null) {
			setUserId2(userId2);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getRelationId() {
		return _relationId;
	}

	@Override
	public void setRelationId(long relationId) {
		_relationId = relationId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(long createDate) {
		_createDate = createDate;
	}

	@Override
	public long getUserId1() {
		return _userId1;
	}

	@Override
	public void setUserId1(long userId1) {
		_columnBitmask |= USERID1_COLUMN_BITMASK;

		if (!_setOriginalUserId1) {
			_setOriginalUserId1 = true;

			_originalUserId1 = _userId1;
		}

		_userId1 = userId1;
	}

	public long getOriginalUserId1() {
		return _originalUserId1;
	}

	@Override
	public long getUserId2() {
		return _userId2;
	}

	@Override
	public void setUserId2(long userId2) {
		_columnBitmask |= USERID2_COLUMN_BITMASK;

		if (!_setOriginalUserId2) {
			_setOriginalUserId2 = true;

			_originalUserId2 = _userId2;
		}

		_userId2 = userId2;
	}

	public long getOriginalUserId2() {
		return _originalUserId2;
	}

	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (!_setOriginalType) {
			_setOriginalType = true;

			_originalType = _type;
		}

		_type = type;
	}

	public int getOriginalType() {
		return _originalType;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			SocialRelation.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SocialRelation toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (SocialRelation)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		SocialRelationImpl socialRelationImpl = new SocialRelationImpl();

		socialRelationImpl.setUuid(getUuid());
		socialRelationImpl.setRelationId(getRelationId());
		socialRelationImpl.setCompanyId(getCompanyId());
		socialRelationImpl.setCreateDate(getCreateDate());
		socialRelationImpl.setUserId1(getUserId1());
		socialRelationImpl.setUserId2(getUserId2());
		socialRelationImpl.setType(getType());

		socialRelationImpl.resetOriginalValues();

		return socialRelationImpl;
	}

	@Override
	public int compareTo(SocialRelation socialRelation) {
		long primaryKey = socialRelation.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialRelation)) {
			return false;
		}

		SocialRelation socialRelation = (SocialRelation)obj;

		long primaryKey = socialRelation.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		SocialRelationModelImpl socialRelationModelImpl = this;

		socialRelationModelImpl._originalUuid = socialRelationModelImpl._uuid;

		socialRelationModelImpl._originalCompanyId = socialRelationModelImpl._companyId;

		socialRelationModelImpl._setOriginalCompanyId = false;

		socialRelationModelImpl._originalUserId1 = socialRelationModelImpl._userId1;

		socialRelationModelImpl._setOriginalUserId1 = false;

		socialRelationModelImpl._originalUserId2 = socialRelationModelImpl._userId2;

		socialRelationModelImpl._setOriginalUserId2 = false;

		socialRelationModelImpl._originalType = socialRelationModelImpl._type;

		socialRelationModelImpl._setOriginalType = false;

		socialRelationModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SocialRelation> toCacheModel() {
		SocialRelationCacheModel socialRelationCacheModel = new SocialRelationCacheModel();

		socialRelationCacheModel.uuid = getUuid();

		String uuid = socialRelationCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			socialRelationCacheModel.uuid = null;
		}

		socialRelationCacheModel.relationId = getRelationId();

		socialRelationCacheModel.companyId = getCompanyId();

		socialRelationCacheModel.createDate = getCreateDate();

		socialRelationCacheModel.userId1 = getUserId1();

		socialRelationCacheModel.userId2 = getUserId2();

		socialRelationCacheModel.type = getType();

		return socialRelationCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", relationId=");
		sb.append(getRelationId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", userId1=");
		sb.append(getUserId1());
		sb.append(", userId2=");
		sb.append(getUserId2());
		sb.append(", type=");
		sb.append(getType());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialRelation");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>relationId</column-name><column-value><![CDATA[");
		sb.append(getRelationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId1</column-name><column-value><![CDATA[");
		sb.append(getUserId1());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId2</column-name><column-value><![CDATA[");
		sb.append(getUserId2());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = SocialRelation.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			SocialRelation.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _relationId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _createDate;
	private long _userId1;
	private long _originalUserId1;
	private boolean _setOriginalUserId1;
	private long _userId2;
	private long _originalUserId2;
	private boolean _setOriginalUserId2;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private long _columnBitmask;
	private SocialRelation _escapedModel;
}