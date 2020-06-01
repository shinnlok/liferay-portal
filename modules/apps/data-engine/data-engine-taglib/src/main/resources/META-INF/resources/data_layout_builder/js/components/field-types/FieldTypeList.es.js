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

import classNames from 'classnames';
import React from 'react';

import CollapsablePanel from '../collapsable-panel/CollapsablePanel.es';
import FieldType from './FieldType.es';

export default ({
	deleteLabel,
	fieldTypes,
	keywords,
	onClick,
	onDelete,
	onDoubleClick,
}) => {
	const regex = new RegExp(keywords, 'ig');
	const fieldTypeList = fieldTypes
		.filter(({system}) => !system)
		.filter(({description, label}) => {
			if (!keywords) {
				return true;
			}

			return regex.test(description) || regex.test(label);
		});

	const FieldTypeWrapper = ({
		expanded,
		fieldType,
		showArrows,
		...otherProps
	}) => (
		<FieldType
			{...otherProps}
			{...fieldType}
			deleteLabel={deleteLabel}
			icon={
				showArrows
					? expanded
						? 'angle-down'
						: 'angle-right'
					: fieldType.icon
			}
		/>
	);

	return fieldTypeList.map((fieldType, index) => {
		const {isFieldSet, nestedDataDefinitionFields = []} = fieldType;

		const handleOnClick = (props) => {
			if (fieldType.disabled || !onClick) {
				return;
			}

			onClick(props);
		};

		if (nestedDataDefinitionFields.length) {
			const Header = ({expanded, setExpanded}) => (
				<FieldTypeWrapper
					expanded={expanded}
					fieldType={{
						...fieldType,
						className: `${fieldType.className} field-type-header`,
					}}
					onClick={(props) => {
						setExpanded(!expanded);

						handleOnClick(props);
					}}
					onDelete={onDelete}
					onDoubleClick={onDoubleClick}
					setExpanded={setExpanded}
					showArrows
				/>
			);

			return (
				<div className="field-type-list">
					<CollapsablePanel
						className={classNames({
							'field-type-primary': !isFieldSet,
							'field-type-success': isFieldSet,
						})}
						Header={Header}
						key={index}
					>
						<div className="field-type-item position-relative">
							{nestedDataDefinitionFields.map(
								(nestedFieldType) => (
									<FieldTypeWrapper
										draggable={false}
										fieldType={{
											...nestedFieldType,
											disabled: fieldType.disabled,
										}}
										key={`${nestedFieldType.name}_${index}`}
									/>
								)
							)}
						</div>
					</CollapsablePanel>
				</div>
			);
		}

		return (
			<FieldTypeWrapper
				fieldType={fieldType}
				key={index}
				onClick={handleOnClick}
				onDelete={onDelete}
				onDoubleClick={onDoubleClick}
			/>
		);
	});
};
