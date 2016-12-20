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

package com.liferay.portlet.documentlibrary.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.model.DLSyncEvent;
import com.liferay.document.library.kernel.model.DLSyncEventModel;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the DLSyncEvent service. Represents a row in the &quot;DLSyncEvent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link DLSyncEventModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DLSyncEventImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventImpl
 * @see DLSyncEvent
 * @see DLSyncEventModel
 * @generated
 */
@ProviderType
public class DLSyncEventModelImpl extends BaseModelImpl<DLSyncEvent>
	implements DLSyncEventModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a dl sync event model instance should use the {@link DLSyncEvent} interface instead.
	 */
	public static final String TABLE_NAME = "DLSyncEvent";
	public static final Object[][] TABLE_COLUMNS = {
			{ "syncEventId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "modifiedTime", Types.BIGINT },
			{ "event", Types.VARCHAR },
			{ "type_", Types.VARCHAR },
			{ "typePK", Types.BIGINT }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("syncEventId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("modifiedTime", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("event", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("typePK", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE = "create table DLSyncEvent (syncEventId LONG not null primary key,companyId LONG,modifiedTime LONG,event VARCHAR(75) null,type_ VARCHAR(75) null,typePK LONG)";
	public static final String TABLE_SQL_DROP = "drop table DLSyncEvent";
	public static final String ORDER_BY_JPQL = " ORDER BY dlSyncEvent.modifiedTime ASC";
	public static final String ORDER_BY_SQL = " ORDER BY DLSyncEvent.modifiedTime ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.document.library.kernel.model.DLSyncEvent"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.document.library.kernel.model.DLSyncEvent"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.document.library.kernel.model.DLSyncEvent"),
			true);
	public static final long MODIFIEDTIME_COLUMN_BITMASK = 1L;
	public static final long TYPEPK_COLUMN_BITMASK = 2L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.document.library.kernel.model.DLSyncEvent"));

	public DLSyncEventModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _syncEventId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSyncEventId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _syncEventId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DLSyncEvent.class;
	}

	@Override
	public String getModelClassName() {
		return DLSyncEvent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("syncEventId", getSyncEventId());
		attributes.put("companyId", getCompanyId());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("event", getEvent());
		attributes.put("type", getType());
		attributes.put("typePK", getTypePK());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long syncEventId = (Long)attributes.get("syncEventId");

		if (syncEventId != null) {
			setSyncEventId(syncEventId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		String event = (String)attributes.get("event");

		if (event != null) {
			setEvent(event);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long typePK = (Long)attributes.get("typePK");

		if (typePK != null) {
			setTypePK(typePK);
		}
	}

	@Override
	public long getSyncEventId() {
		return _syncEventId;
	}

	@Override
	public void setSyncEventId(long syncEventId) {
		_syncEventId = syncEventId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getModifiedTime() {
		return _modifiedTime;
	}

	@Override
	public void setModifiedTime(long modifiedTime) {
		_columnBitmask = -1L;

		if (!_setOriginalModifiedTime) {
			_setOriginalModifiedTime = true;

			_originalModifiedTime = _modifiedTime;
		}

		_modifiedTime = modifiedTime;
	}

	public long getOriginalModifiedTime() {
		return _originalModifiedTime;
	}

	@Override
	public String getEvent() {
		if (_event == null) {
			return StringPool.BLANK;
		}
		else {
			return _event;
		}
	}

	@Override
	public void setEvent(String event) {
		_event = event;
	}

	@Override
	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	@Override
	public void setType(String type) {
		_type = type;
	}

	@Override
	public long getTypePK() {
		return _typePK;
	}

	@Override
	public void setTypePK(long typePK) {
		_columnBitmask |= TYPEPK_COLUMN_BITMASK;

		if (!_setOriginalTypePK) {
			_setOriginalTypePK = true;

			_originalTypePK = _typePK;
		}

		_typePK = typePK;
	}

	public long getOriginalTypePK() {
		return _originalTypePK;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			DLSyncEvent.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public DLSyncEvent toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (DLSyncEvent)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		DLSyncEventImpl dlSyncEventImpl = new DLSyncEventImpl();

		dlSyncEventImpl.setSyncEventId(getSyncEventId());
		dlSyncEventImpl.setCompanyId(getCompanyId());
		dlSyncEventImpl.setModifiedTime(getModifiedTime());
		dlSyncEventImpl.setEvent(getEvent());
		dlSyncEventImpl.setType(getType());
		dlSyncEventImpl.setTypePK(getTypePK());

		dlSyncEventImpl.resetOriginalValues();

		return dlSyncEventImpl;
	}

	@Override
	public int compareTo(DLSyncEvent dlSyncEvent) {
		int value = 0;

		if (getModifiedTime() < dlSyncEvent.getModifiedTime()) {
			value = -1;
		}
		else if (getModifiedTime() > dlSyncEvent.getModifiedTime()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLSyncEvent)) {
			return false;
		}

		DLSyncEvent dlSyncEvent = (DLSyncEvent)obj;

		long primaryKey = dlSyncEvent.getPrimaryKey();

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
		DLSyncEventModelImpl dlSyncEventModelImpl = this;

		dlSyncEventModelImpl._originalModifiedTime = dlSyncEventModelImpl._modifiedTime;

		dlSyncEventModelImpl._setOriginalModifiedTime = false;

		dlSyncEventModelImpl._originalTypePK = dlSyncEventModelImpl._typePK;

		dlSyncEventModelImpl._setOriginalTypePK = false;

		dlSyncEventModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DLSyncEvent> toCacheModel() {
		DLSyncEventCacheModel dlSyncEventCacheModel = new DLSyncEventCacheModel();

		dlSyncEventCacheModel.syncEventId = getSyncEventId();

		dlSyncEventCacheModel.companyId = getCompanyId();

		dlSyncEventCacheModel.modifiedTime = getModifiedTime();

		dlSyncEventCacheModel.event = getEvent();

		String event = dlSyncEventCacheModel.event;

		if ((event != null) && (event.length() == 0)) {
			dlSyncEventCacheModel.event = null;
		}

		dlSyncEventCacheModel.type = getType();

		String type = dlSyncEventCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			dlSyncEventCacheModel.type = null;
		}

		dlSyncEventCacheModel.typePK = getTypePK();

		return dlSyncEventCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{syncEventId=");
		sb.append(getSyncEventId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", modifiedTime=");
		sb.append(getModifiedTime());
		sb.append(", event=");
		sb.append(getEvent());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", typePK=");
		sb.append(getTypePK());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.document.library.kernel.model.DLSyncEvent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>syncEventId</column-name><column-value><![CDATA[");
		sb.append(getSyncEventId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedTime</column-name><column-value><![CDATA[");
		sb.append(getModifiedTime());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>event</column-name><column-value><![CDATA[");
		sb.append(getEvent());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typePK</column-name><column-value><![CDATA[");
		sb.append(getTypePK());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = DLSyncEvent.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			DLSyncEvent.class
		};
	private long _syncEventId;
	private long _companyId;
	private long _modifiedTime;
	private long _originalModifiedTime;
	private boolean _setOriginalModifiedTime;
	private String _event;
	private String _type;
	private long _typePK;
	private long _originalTypePK;
	private boolean _setOriginalTypePK;
	private long _columnBitmask;
	private DLSyncEvent _escapedModel;
}