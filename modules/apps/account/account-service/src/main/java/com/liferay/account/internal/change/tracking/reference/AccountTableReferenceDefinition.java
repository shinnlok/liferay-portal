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

package com.liferay.account.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.AccountTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.AccountPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AccountTableReferenceDefinition
	implements TableReferenceDefinition<AccountTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AccountTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AccountTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AccountTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).parentColumnReference(
			AccountTable.INSTANCE.accountId,
			AccountTable.INSTANCE.parentAccountId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _accountPersistence;
	}

	@Override
	public AccountTable getTable() {
		return AccountTable.INSTANCE;
	}

	@Reference
	private AccountPersistence _accountPersistence;

}