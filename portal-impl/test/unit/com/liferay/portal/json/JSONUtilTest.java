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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class JSONUtilTest {

	@Before
	public void setUp() throws Exception {
		JSONInit.init();

		JSONFactoryImpl jsonFactoryImpl = new JSONFactoryImpl();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(jsonFactoryImpl);
	}

	@Test
	public void testAddToStringCollection() {
		List<String> values = new ArrayList<String>() {
			{
				add("foo");
				add("bar");
			}
		};

		JSONUtil.addToStringCollection(values, JSONUtil.put("baz"));

		Assert.assertEquals(values.toString(), 3, values.size());
		Assert.assertTrue(values.contains("baz"));
	}

	@Test
	public void testAddToStringCollectionWithKey() {
		List<String> values = new ArrayList<String>() {
			{
				add("1");
				add("2");
			}
		};

		JSONUtil.addToStringCollection(
			values, JSONUtil.put(JSONUtil.put("foo", "3")), "foo");

		Assert.assertEquals(values.toString(), 3, values.size());
		Assert.assertTrue(values.contains("3"));
	}

	@Test
	public void testGetValue() {
		JSONObject jsonObject = JSONUtil.put(
			"foo", JSONUtil.put("bar", JSONUtil.put("baz")));

		Assert.assertEquals(
			"baz",
			JSONUtil.getValue(
				jsonObject, "JSONObject/foo", "JSONArray/bar", "Object/0"));
	}

	@Test
	public void testHasValue() {
		Assert.assertTrue(
			JSONUtil.hasValue(JSONUtil.put("foo", "bar", "baz"), "baz"));

		Assert.assertFalse(
			JSONUtil.hasValue(JSONUtil.put("foo", "bar", "baz"), "1"));
	}

	@Test
	public void testMerge() throws Exception {
		JSONObject jsonObject1 = JSONUtil.put("foo", "1");
		JSONObject jsonObject2 = JSONUtil.put(
			"bar", "2"
		).put(
			"baz", "3"
		);

		JSONObject jsonObject3 = JSONUtil.merge(jsonObject1, jsonObject2);

		Assert.assertEquals(3, jsonObject3.length());
		Assert.assertTrue(jsonObject3.has("foo"));
		Assert.assertTrue(jsonObject3.has("bar"));
		Assert.assertTrue(jsonObject3.has("baz"));
	}

	@Test
	public void testPutKeyValue() {
		JSONObject jsonObject1 = _getJSONObject();

		jsonObject1.put("foo", "bar");

		JSONObject jsonObject2 = JSONUtil.put("foo", "bar");

		Assert.assertEquals(jsonObject1.get("foo"), jsonObject2.get("foo"));
	}

	@Test
	public void testPutValue() {
		JSONArray jsonArray1 = _getJSONArray();

		jsonArray1.put("foo");

		JSONArray jsonArray2 = JSONUtil.put("foo");

		Assert.assertEquals(jsonArray1.length(), jsonArray2.length());
		Assert.assertEquals(jsonArray1.get(0), jsonArray2.get(0));
	}

	@Test
	public void testPutValues() {
		JSONArray jsonArray1 = _getJSONArray();

		jsonArray1.put(
			"foo"
		).put(
			"bar"
		).put(
			"baz"
		);

		JSONArray jsonArray2 = JSONUtil.put("foo", "bar", "baz");

		for (int i = 0; i < jsonArray1.length(); i++) {
			Assert.assertEquals(jsonArray1.get(i), jsonArray2.get(i));
		}
	}

	@Test
	public void testReplace() {
		JSONArray jsonArray = JSONUtil.put(
			JSONUtil.put(
				"foo", "1"
			).put(
				"bar", "2"
			));

		JSONObject jsonObject = JSONUtil.put(
			"foo", "1"
		).put(
			"bar", "3"
		).put(
			"baz", "4"
		);

		jsonArray = JSONUtil.replace(jsonArray, "foo", jsonObject);

		jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("3", jsonObject.get("bar"));
		Assert.assertEquals("4", jsonObject.get("baz"));
	}

	@Test
	public void testSerializeArraysWithObjects() {
		JSONArray jsonArray = JSONUtil.put(_getJSONObject());

		Assert.assertEquals("[{}]", jsonArray.toString());
	}

	@Test
	public void testToLongArray() {
		Assert.assertArrayEquals(
			new long[] {1, 2}, JSONUtil.toLongArray(JSONUtil.put(1, 2)));
	}

	@Test
	public void testToLongArrayWithKey() {
		Assert.assertArrayEquals(
			new long[] {1, 2},
			JSONUtil.toLongArray(
				JSONUtil.put(
					JSONUtil.put("foo", 1), JSONUtil.put("foo", 2),
					JSONUtil.put("bar", 3)),
				"foo"));
	}

	@Test
	public void testToLongList() {
		Assert.assertEquals(
			new ArrayList<Long>() {
				{
					add(1L);
					add(2L);
					add(3L);
				}
			},
			JSONUtil.toLongList(JSONUtil.put(1, 2, 3)));
	}

	@Test
	public void testToLongListWithKey() {
		Assert.assertEquals(
			new ArrayList<Long>() {
				{
					add(1L);
					add(2L);
					add(3L);
				}
			},
			JSONUtil.toLongList(
				JSONUtil.put(
					JSONUtil.put("foo", 1), JSONUtil.put("foo", 2),
					JSONUtil.put("foo", 3)),
				"foo"));
	}

	@Test
	public void testToLongSet() {
		Assert.assertEquals(
			new HashSet<Long>() {
				{
					add(1L);
					add(2L);
					add(3L);
				}
			},
			JSONUtil.toLongSet(JSONUtil.put(1, 2, 3)));
	}

	@Test
	public void testToLongSetWithKey() {
		Assert.assertEquals(
			new HashSet<Long>() {
				{
					add(1L);
					add(2L);
				}
			},
			JSONUtil.toLongSet(
				JSONUtil.put(
					JSONUtil.put("foo", 1), JSONUtil.put("foo", 2),
					JSONUtil.put("bar", 3)),
				"foo"));
	}

	@Test
	public void testToObjectArray() {
		Assert.assertArrayEquals(
			new Object[] {1, "bar", true},
			JSONUtil.toObjectArray(JSONUtil.put(1, "bar", true)));
	}

	@Test
	public void testToObjectArrayWithKey() {
		Assert.assertArrayEquals(
			new Object[] {1, true},
			JSONUtil.toObjectArray(
				JSONUtil.put(
					JSONUtil.put("foo", 1), JSONUtil.put("foo", true),
					JSONUtil.put("bar", 3)),
				"foo"));
	}

	@Test
	public void testToObjectList() {
		Assert.assertEquals(
			new ArrayList<Object>() {
				{
					add(1);
					add("bar");
					add(true);
				}
			},
			JSONUtil.toObjectList(JSONUtil.put(1, "bar", true)));
	}

	@Test
	public void testToObjectListWithKey() {
		Assert.assertEquals(
			new ArrayList<Object>() {
				{
					add(1);
					add("bar");
				}
			},
			JSONUtil.toObjectList(
				JSONUtil.put(
					JSONUtil.put("foo", 1), JSONUtil.put("foo", "bar"),
					JSONUtil.put("bar", true)),
				"foo"));
	}

	@Test
	public void testToObjectSet() {
		Assert.assertEquals(
			new HashSet<Object>() {
				{
					add(1);
					add("bar");
					add(true);
				}
			},
			JSONUtil.toObjectSet(JSONUtil.put(1, "bar", true)));
	}

	@Test
	public void testToObjectSetWithKey() {
		Assert.assertEquals(
			new HashSet<Object>() {
				{
					add(1);
					add("bar");
				}
			},
			JSONUtil.toObjectSet(
				JSONUtil.put(
					JSONUtil.put("foo", 1), JSONUtil.put("foo", "bar"),
					JSONUtil.put("bar", true)),
				"foo"));
	}

	@Test
	public void testToStringArray() {
		Assert.assertArrayEquals(
			new String[] {"foo", "bar", "baz"},
			JSONUtil.toStringArray(JSONUtil.put("foo", "bar", "baz")));
	}

	@Test
	public void testToStringArrayWithKey() {
		Assert.assertArrayEquals(
			new String[] {"foo", "bar"},
			JSONUtil.toStringArray(
				JSONUtil.put(
					JSONUtil.put("foo", "foo"), JSONUtil.put("foo", "bar"),
					JSONUtil.put("bar", "baz")),
				"foo"));
	}

	@Test
	public void testToStringList() {
		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("foo");
					add("bar");
					add("baz");
				}
			},
			JSONUtil.toStringList(JSONUtil.put("foo", "bar", "baz")));
	}

	@Test
	public void testToStringListWithKey() {
		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("foo");
					add("bar");
				}
			},
			JSONUtil.toStringList(
				JSONUtil.put(
					JSONUtil.put("foo", "foo"), JSONUtil.put("foo", "bar"),
					JSONUtil.put("bar", "baz")),
				"foo"));
	}

	@Test
	public void testToStringSet() {
		Assert.assertEquals(
			new HashSet<String>() {
				{
					add("foo");
					add("bar");
					add("baz");
				}
			},
			JSONUtil.toStringSet(JSONUtil.put("foo", "bar", "baz")));
	}

	@Test
	public void testToStringSetWithKey() {
		Assert.assertEquals(
			new HashSet<String>() {
				{
					add("foo");
					add("bar");
				}
			},
			JSONUtil.toStringSet(
				JSONUtil.put(
					JSONUtil.put("foo", "foo"), JSONUtil.put("foo", "bar"),
					JSONUtil.put("bar", "baz")),
				"foo"));
	}

	private JSONArray _getJSONArray() {
		return JSONFactoryUtil.createJSONArray();
	}

	private JSONObject _getJSONObject() {
		return JSONFactoryUtil.createJSONObject();
	}

}