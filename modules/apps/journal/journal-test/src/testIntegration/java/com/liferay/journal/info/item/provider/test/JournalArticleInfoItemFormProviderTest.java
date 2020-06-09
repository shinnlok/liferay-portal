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

package com.liferay.journal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemServiceTracker;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class JournalArticleInfoItemFormProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule testRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetInfoForm() throws Exception {
		InfoItemFormProvider infoItemFormProvider =
			_infoItemFormProviderTracker.getInfoItemFormProvider(
				JournalArticle.class.getName());

		JournalArticle journalArticle = _getJournalArticle();

		InfoForm infoForm = infoItemFormProvider.getInfoForm(journalArticle);

		List<InfoField> infoFields = infoForm.getAllInfoFields();

		infoFields.sort(Comparator.comparing(InfoField::getName));

		Assert.assertEquals(infoFields.toString(), 12, infoFields.size());

		InfoField infoField = infoFields.get(0);

		Assert.assertEquals("DDM_Text", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());

		infoField = infoFields.get(1);

		Assert.assertEquals("authorName", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(2);

		Assert.assertEquals("authorProfileImage", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(3);

		Assert.assertEquals("categories", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(4);

		Assert.assertEquals("description", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());

		infoField = infoFields.get(5);

		Assert.assertEquals("displayPageURL", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(6);

		Assert.assertEquals("lastEditorName", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(7);

		Assert.assertEquals("lastEditorProfileImage", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(8);

		Assert.assertEquals("publishDate", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(9);

		Assert.assertEquals("smallImage", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(10);

		Assert.assertEquals("tagNames", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = infoFields.get(11);

		Assert.assertEquals("title", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());
	}

	@Test
	public void testGetInfoFormValues() throws Exception {
		InfoItemFormProvider infoItemFormProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoItemFormProvider.class, JournalArticle.class.getName());

		JournalArticle journalArticle = _getJournalArticle();

		InfoFormValues infoFormValues = infoItemFormProvider.getInfoFormValues(
			journalArticle);

		InfoItemClassPKReference infoItemClassPKReference =
			infoFormValues.getInfoItemClassPKReference();

		Assert.assertEquals(
			journalArticle.getResourcePrimKey(),
			infoItemClassPKReference.getClassPK());
		Assert.assertEquals(
			JournalArticle.class.getName(),
			infoItemClassPKReference.getClassName());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoFormValues.getInfoFieldValues();

		Assert.assertEquals(
			infoFieldValues.toString(), 9, infoFieldValues.size());

		InfoFieldValue<Object> descriptionInfoFieldValue =
			infoFormValues.getInfoFieldValue("description");

		Assert.assertEquals(
			"Description",
			descriptionInfoFieldValue.getValue(LocaleUtil.getDefault()));

		Assert.assertEquals(
			"Descripción",
			descriptionInfoFieldValue.getValue(LocaleUtil.SPAIN));

		InfoFieldValue<Object> titleInfoFieldValue =
			infoFormValues.getInfoFieldValue("title");

		Assert.assertEquals(
			"Test Article",
			titleInfoFieldValue.getValue(LocaleUtil.getDefault()));

		Assert.assertEquals(
			"Artículo de prueba",
			titleInfoFieldValue.getValue(LocaleUtil.SPAIN));

		InfoFieldValue<Object> ddmTextInfoFieldValue =
			infoFormValues.getInfoFieldValue("DDM_Text");

		Assert.assertEquals(
			"Some text",
			ddmTextInfoFieldValue.getValue(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"Un poco de texto",
			ddmTextInfoFieldValue.getValue(LocaleUtil.SPAIN));

		Collection<InfoFieldValue<Object>> ddmTextInfoFieldValues =
			infoFormValues.getInfoFieldValues("DDM_Text");

		Iterator<InfoFieldValue<Object>> ddmTextInfoFieldValuesIterator =
			ddmTextInfoFieldValues.iterator();

		InfoFieldValue<Object> firstDDMTextInfoFieldValue =
			ddmTextInfoFieldValuesIterator.next();

		Assert.assertEquals(
			"Some text",
			firstDDMTextInfoFieldValue.getValue(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"Un poco de texto",
			firstDDMTextInfoFieldValue.getValue(LocaleUtil.SPAIN));

		InfoFieldValue<Object> secondDDMTextInfoFieldValue =
			ddmTextInfoFieldValuesIterator.next();

		Assert.assertEquals(
			"Some more text",
			secondDDMTextInfoFieldValue.getValue(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"Un poco más de texto",
			secondDDMTextInfoFieldValue.getValue(LocaleUtil.SPAIN));
	}

	private JournalArticle _getJournalArticle() throws Exception {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				_readFileToString("dependencies/test-ddm-form.json"));

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);

		JournalArticle journalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(),
				_readFileToString("dependencies/test-journal-content.xml"),
				ddmStructure.getStructureKey(), null);

		journalArticle.setDescriptionMap(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), "Description"
			).put(
				LocaleUtil.SPAIN, "Descripción"
			).build());

		journalArticle = _journalArticleLocalService.updateArticleTranslation(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), LocaleUtil.getDefault(),
			journalArticle.getTitle(), "Description",
			journalArticle.getContent(), null,
			ServiceContextTestUtil.getServiceContext(
				journalArticle.getGroupId()));

		return _journalArticleLocalService.updateArticleTranslation(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), LocaleUtil.SPAIN, "Artículo de prueba",
			"Descripción", journalArticle.getContent(), null,
			ServiceContextTestUtil.getServiceContext(
				journalArticle.getGroupId()));
	}

	private String _readFileToString(String s) throws Exception {
		return new String(FileUtil.getBytes(getClass(), s));
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private DDMFormDeserializer _ddmFormDeserializer;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

}