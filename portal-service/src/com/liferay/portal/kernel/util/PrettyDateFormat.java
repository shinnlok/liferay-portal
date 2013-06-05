/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Alexander Chow
 */
public class PrettyDateFormat extends DateFormat {

	public PrettyDateFormat(Locale locale, TimeZone timeZone) {
		_locale = locale;
		_timeZone = timeZone;
		_todayString = LanguageUtil.get(_locale, "today");
		_yesterdayString = LanguageUtil.get(_locale, "yesterday");
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public PrettyDateFormat(long companyId, Locale locale, TimeZone timeZone) {
		this(locale, timeZone);
	}

	@Override
	public StringBuffer format(Date date, StringBuffer sb, FieldPosition pos) {
		String dateString = StringPool.NBSP;

		if (date == null) {
			return sb.append(dateString);
		}

		Date today = new Date();

		Calendar cal = Calendar.getInstance(_timeZone, _locale);

		cal.setTime(today);
		cal.add(Calendar.DATE, -1);

		Date yesterday = cal.getTime();

		Format dateFormat = FastDateFormatFactoryUtil.getDate(
			_locale, _timeZone);
		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			_locale, _timeZone);
		Format timeFormat = FastDateFormatFactoryUtil.getTime(
			_locale, _timeZone);

		dateString = dateFormat.format(date);

		if (dateString.equals(dateFormat.format(today))) {
			dateString =
				_todayString + StringPool.SPACE +
					timeFormat.format(date);
		}
		else if (dateString.equals(dateFormat.format(yesterday))) {
			dateString =
				_yesterdayString + StringPool.SPACE +
					timeFormat.format(date);
		}
		else {
			dateString = dateTimeFormat.format(date);
		}

		return sb.append(dateString);
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		Format dateFormat = FastDateFormatFactoryUtil.getDate(
			_locale, _timeZone);
		DateFormat dateTimeFormat = DateFormatFactoryUtil.getDateTime(
			_locale, _timeZone);

		Date today = new Date();

		String dateString = source.substring(pos.getIndex());

		if (dateString.startsWith(_todayString)) {
			dateString = dateString.replaceFirst(
				_todayString, dateFormat.format(today));
		}
		else if (dateString.startsWith(_yesterdayString)) {
			Calendar cal = Calendar.getInstance(_timeZone, _locale);

			cal.setTime(today);
			cal.add(Calendar.DATE, -1);

			Date yesterday = cal.getTime();

			dateString = dateString.replaceFirst(
				_todayString, dateFormat.format(yesterday));
		}

		return dateTimeFormat.parse(dateString, new ParsePosition(0));
	}

	private Locale _locale;
	private TimeZone _timeZone;
	private String _todayString;
	private String _yesterdayString;

}