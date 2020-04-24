<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
RedirectDisplayContext redirectDisplayContext = new RedirectDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

SearchContainer<RedirectEntry> redirectSearchContainer = redirectDisplayContext.searchContainer();

RedirectManagementToolbarDisplayContext redirectManagementToolbarDisplayContext = new RedirectManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, redirectSearchContainer);
%>

<clay:management-toolbar
	displayContext="<%= redirectManagementToolbarDisplayContext %>"
/>

<div class="closed container-fluid-1280 redirect-entries sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/redirect/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="<%= redirectDisplayContext.getSearchContainerId() %>"
	>
		<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<aui:form action="<%= redirectSearchContainer.getIteratorURL() %>" cssClass="container-fluid-1280" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:search-container
				id="<%= redirectDisplayContext.getSearchContainerId() %>"
				searchContainer="<%= redirectSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.redirect.model.RedirectEntry"
					keyProperty="redirectEntryId"
					modelVar="redirectEntry"
				>

					<%
					row.setData(HashMapBuilder.<String, Object>put("actions", redirectManagementToolbarDisplayContext.getAvailableActions(redirectEntry)).build());
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="source-url"
					>

						<%
						String sourceUrl = RedirectUtil.getGroupBaseURL(themeDisplay) + StringPool.SLASH + redirectEntry.getSourceURL();
						%>

						<span data-title="<%= sourceUrl %>">
							<%= HtmlUtil.escape(sourceUrl) %>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="destination-url"
					>

						<%
						String destinationUrl = HtmlUtil.escape(redirectEntry.getDestinationURL());

						Map<String, String> data = HashMapBuilder.put(
							"href", destinationUrl
						).build();
						%>

						<clay:button
							data="<%= data %>"
							elementClasses="icon-shortcut"
							icon="shortcut"
							monospaced="<%= true %>"
							size="sm"
							style="unstyled"
							title='<%= LanguageUtil.get(request, "try-redirection") %>'
						/>

						<span data-title="<%= destinationUrl %>">
							<%= destinationUrl %>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest"
						name="type"
					>
						<liferay-ui:message key='<%= redirectEntry.isPermanent() ? "permanent" : "temporary" %>' />
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest"
						name="expiration"
					>
						<c:choose>
							<c:when test="<%= Validator.isNull(redirectEntry.getExpirationDate()) %>">
								<%= StringPool.DASH %>
							</c:when>
							<c:when test="<%= DateUtil.compareTo(redirectEntry.getExpirationDate(), DateUtil.newDate()) <= 0 %>">
								<strong><liferay-ui:message key="expired" /></strong>
							</c:when>
							<c:otherwise>
								<%= redirectDisplayContext.formatExpirationDate(redirectEntry.getExpirationDate()) %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							dropdownItems="<%= redirectDisplayContext.getActionDropdownItems(redirectEntry) %>"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchContainer="<%= redirectSearchContainer %>"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>

<liferay-frontend:component
	componentId="<%= redirectManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= redirectManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/RedirectManagementToolbarDefaultEventHandler.es"
/>

<aui:script require="metal-dom/src/all/dom as dom">
	dom.delegate(
		document.querySelector('#<portlet:namespace />fm'),
		'click',
		'.icon-shortcut',
		function(event) {
			var delegateTarget = event.delegateTarget;

			var destinationUrl = delegateTarget.dataset.href;

			if (destinationUrl) {
				window.open(destinationUrl, '_blank');
			}
		}
	);
</aui:script>