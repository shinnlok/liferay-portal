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

package com.liferay.analytics.settings.internal.util;

import com.liferay.analytics.settings.util.AnalyticsUsersManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = AnalyticsUsersManager.class)
public class AnalyticsUsersManagerImpl implements AnalyticsUsersManager {

	@Override
	public List<User> getCompanyUsers(long companyId, int start, int end) {
		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			start, end);

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setCompanyId(companyId);

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsers(searchRequest);
	}

	@Override
	public int getCompanyUsersCount(long companyId) {
		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setCompanyId(companyId);

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsersCount(searchRequest);
	}

	@Override
	public int getOrganizationsAndUserGroupsUsersCount(
		long[] organizationIds, long[] userGroupIds) {

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"selectedOrganizationIds", organizationIds);
				searchContext.setAttribute(
					"selectedUserGroupIds", userGroupIds);
				searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsersCount(searchRequest);
	}

	@Override
	public List<User> getOrganizationUsers(
		long organizationId, int start, int end) {

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			start, end);

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"selectedOrganizationIds", new long[] {organizationId});
				searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsers(searchRequest);
	}

	@Override
	public int getOrganizationUsersCount(long organizationId) {
		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"selectedOrganizationIds", new long[] {organizationId});
				searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsersCount(searchRequest);
	}

	@Override
	public List<User> getUserGroupUsers(long userGroupId, int start, int end) {
		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			start, end);

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"selectedUserGroupIds", new long[] {userGroupId});
				searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsers(searchRequest);
	}

	@Override
	public int getUserGroupUsersCount(long userGroupId) {
		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"selectedUserGroupIds", new long[] {userGroupId});
				searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsersCount(searchRequest);
	}

	private SearchRequestBuilder _getSearchRequestBuilder(int start, int end) {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		if ((start != QueryUtil.ALL_POS) && (end != QueryUtil.ALL_POS)) {
			searchRequestBuilder.from(start);
			searchRequestBuilder.size(end - start);
		}

		return searchRequestBuilder.entryClassNames(
			User.class.getName()
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		);
	}

	private List<User> _getUsers(SearchRequest searchRequest) {
		SearchResponse searchResponse = _searcher.search(searchRequest);

		SearchHits searchHits = searchResponse.getSearchHits();

		List<User> users = TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				long userId = document.getLong("userId");

				return _userLocalService.getUser(userId);
			});

		BaseModelSearchResult<User> baseModelSearchResult =
			new BaseModelSearchResult<>(users, searchResponse.getTotalHits());

		return baseModelSearchResult.getBaseModels();
	}

	private int _getUsersCount(SearchRequest searchRequest) {
		SearchResponse searchResponse = _searcher.search(searchRequest);

		SearchHits searchHits = searchResponse.getSearchHits();

		Long totalHits = Long.valueOf(searchHits.getTotalHits());

		return totalHits.intValue();
	}

	private void _populateSearchContext(SearchContext searchContext) {
		Role role = _roleLocalService.fetchRole(
			CompanyThreadLocal.getCompanyId(),
			RoleConstants.ANALYTICS_ADMINISTRATOR);

		if (role != null) {
			searchContext.setAttribute(
				"excludedRoleIds", new long[] {role.getRoleId()});
		}
	}

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private UserLocalService _userLocalService;

}