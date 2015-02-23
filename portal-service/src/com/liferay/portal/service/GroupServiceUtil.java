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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for Group. This utility wraps
 * {@link com.liferay.portal.service.impl.GroupServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see GroupService
 * @see com.liferay.portal.service.base.GroupServiceBaseImpl
 * @see com.liferay.portal.service.impl.GroupServiceImpl
 * @generated
 */
@ProviderType
public class GroupServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.GroupServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addGroup(long, String,
	String, int, String, boolean, boolean, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Group addGroup(
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addGroup(name, description, type, friendlyURL, site,
			active, serviceContext);
	}

	/**
	* Adds a group.
	*
	* @param parentGroupId the primary key of the parent group
	* @param liveGroupId the primary key of the live group
	* @param name the entity's name
	* @param description the group's description (optionally
	<code>null</code>)
	* @param type the group's type. For more information see {@link
	GroupConstants}.
	* @param manualMembership whether manual membership is allowed for the
	group
	* @param membershipRestriction the group's membership restriction. For
	more information see {@link GroupConstants}.
	* @param friendlyURL the group's friendlyURL (optionally
	<code>null</code>)
	* @param site whether the group is to be associated with a main site
	* @param active whether the group is active
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the asset category IDs and asset
	tag names for the group, and can set whether the group is for
	staging
	* @return the group
	* @throws PortalException if the user did not have permission to add
	the group, if a creator could not be found, if the group's
	information was invalid, if a layout could not be found, or
	if a valid friendly URL could not be created for the group
	* @deprecated As of 7.0.0, replaced by {@link #addGroup(long, long, Map,
	Map, int, boolean, int, String, boolean, boolean,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Group addGroup(long parentGroupId,
		long liveGroupId, java.lang.String name, java.lang.String description,
		int type, boolean manualMembership, int membershipRestriction,
		java.lang.String friendlyURL, boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addGroup(parentGroupId, liveGroupId, name, description,
			type, manualMembership, membershipRestriction, friendlyURL, site,
			active, serviceContext);
	}

	public static com.liferay.portal.model.Group addGroup(long parentGroupId,
		long liveGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int type, boolean manualMembership, int membershipRestriction,
		java.lang.String friendlyURL, boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addGroup(parentGroupId, liveGroupId, nameMap,
			descriptionMap, type, manualMembership, membershipRestriction,
			friendlyURL, site, active, serviceContext);
	}

	public static com.liferay.portal.model.Group addGroup(long parentGroupId,
		long liveGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int type, boolean manualMembership, int membershipRestriction,
		java.lang.String friendlyURL, boolean site, boolean inheritContent,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addGroup(parentGroupId, liveGroupId, nameMap,
			descriptionMap, type, manualMembership, membershipRestriction,
			friendlyURL, site, inheritContent, active, serviceContext);
	}

	/**
	* Adds the group using the group default live group ID.
	*
	* @param parentGroupId the primary key of the parent group
	* @param name the entity's name
	* @param description the group's description (optionally
	<code>null</code>)
	* @param type the group's type. For more information see {@link
	GroupConstants}.
	* @param friendlyURL the group's friendlyURL
	* @param site whether the group is to be associated with a main site
	* @param active whether the group is active
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the group, and can set whether the group is for
	staging
	* @return the group
	* @throws PortalException if the user did not have permission to add
	the group, if a creator could not be found, if the group's
	information was invalid, if a layout could not be found, or
	if a valid friendly URL could not be created for the group
	* @deprecated As of 6.2.0, replaced by {@link #addGroup(long, long, Map,
	Map, int, boolean, int, String, boolean, boolean,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Group addGroup(long parentGroupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addGroup(parentGroupId, name, description, type,
			friendlyURL, site, active, serviceContext);
	}

	/**
	* Adds the groups to the role.
	*
	* @param roleId the primary key of the role
	* @param groupIds the primary keys of the groups
	* @throws PortalException if the user did not have permission to update the
	role
	*/
	public static void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addRoleGroups(roleId, groupIds);
	}

	/**
	* Checks that the current user is permitted to use the group for Remote
	* Staging.
	*
	* @param groupId the primary key of the group
	* @throws PortalException if a group with the primary key could not be
	found, if the current user did not have permission to view the
	group, or if the group's company was different from the current
	user's company
	*/
	public static void checkRemoteStagingGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkRemoteStagingGroup(groupId);
	}

	/**
	* Deletes the group.
	*
	* <p>
	* The group is unstaged and its assets and resources including layouts,
	* membership requests, subscriptions, teams, blogs, bookmarks, calendar
	* events, image gallery, journals, message boards, polls, shopping related
	* entities, software catalog, and wikis are also deleted.
	* </p>
	*
	* @param groupId the primary key of the group
	* @throws PortalException if the user did not have permission to delete the
	group or its assets or resources, if a group with the primary key
	could not be found, or if the group was a system group
	*/
	public static void deleteGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteGroup(groupId);
	}

	public static void disableStaging(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().disableStaging(groupId);
	}

	public static void enableStaging(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().enableStaging(groupId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Returns the company group.
	*
	* @param companyId the primary key of the company
	* @return the group associated with the company
	* @throws PortalException if a matching group could not be found
	*/
	public static com.liferay.portal.model.Group getCompanyGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCompanyGroup(companyId);
	}

	/**
	* Returns the group with the name.
	*
	* @param companyId the primary key of the company
	* @param groupKey the group key
	* @return the group with the group key
	* @throws PortalException if a matching group could not be found or if the
	current user did not have permission to view the group
	*/
	public static com.liferay.portal.model.Group getGroup(long companyId,
		java.lang.String groupKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroup(companyId, groupKey);
	}

	/**
	* Returns the group with the primary key.
	*
	* @param groupId the primary key of the group
	* @return the group with the primary key
	* @throws PortalException if a group with the primary key could not be
	found or if the current user did not have permission to view the
	group
	*/
	public static com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroup(groupId);
	}

	/**
	* Returns all the groups that are direct children of the parent group.
	*
	* @param companyId the primary key of the company
	* @param parentGroupId the primary key of the parent group
	* @param site whether the group is to be associated with a main site
	* @return the matching groups, or <code>null</code> if no matches were
	found
	* @throws PortalException if the user did not have permission to view the
	group or if a portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long companyId, long parentGroupId, boolean site)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroups(companyId, parentGroupId, site);
	}

	/**
	* Returns a range of all the site groups for which the user has control
	* panel access.
	*
	* @param portlets the portlets to manage
	* @param max the upper bound of the range of groups to consider (not
	inclusive)
	* @return the range of site groups for which the user has Control Panel
	access
	* @throws PortalException if a portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getManageableSiteGroups(
		java.util.Collection<com.liferay.portal.model.Portlet> portlets, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getManageableSiteGroups(portlets, max);
	}

	/**
	* Returns a range of all the site groups for which the user has control
	* panel access.
	*
	* @param portlets the portlets to manage
	* @param max the upper bound of the range of groups to consider (not
	inclusive)
	* @return the range of site groups for which the user has Control Panel
	access
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 6.2.0, replaced by {@link
	#getManageableSiteGroups(Collection, int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portal.model.Group> getManageableSites(
		java.util.Collection<com.liferay.portal.model.Portlet> portlets, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getManageableSites(portlets, max);
	}

	/**
	* Returns the groups associated with the organizations.
	*
	* @param organizations the organizations
	* @return the groups associated with the organizations
	* @throws PortalException if a portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getOrganizationsGroups(
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganizationsGroups(organizations);
	}

	/**
	* Returns the group directly associated with the user.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @return the group directly associated with the user
	* @throws PortalException if a matching group could not be found or if the
	current user did not have permission to view the group
	*/
	public static com.liferay.portal.model.Group getUserGroup(long companyId,
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserGroup(companyId, userId);
	}

	/**
	* Returns the groups associated with the user groups.
	*
	* @param userGroups the user groups
	* @return the groups associated with the user groups
	* @throws PortalException if any one of the user group's group could not be
	found
	*/
	public static java.util.List<com.liferay.portal.model.Group> getUserGroupsGroups(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserGroupsGroups(userGroups);
	}

	/**
	* Returns the range of all groups associated with the user's organization
	* groups, including the ancestors of the organization groups, unless portal
	* property <code>organizations.membership.strict</code> is set to
	* <code>true</code>.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of groups to consider
	* @param end the upper bound of the range of groups to consider (not
	inclusive)
	* @return the range of groups associated with the user's organizations
	* @throws PortalException if a user with the primary key could not be found
	or if another portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getUserOrganizationsGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserOrganizationsGroups(userId, start, end);
	}

	/**
	* Returns the guest or current user's groups &quot;sites&quot; associated
	* with the group entity class names, including the Control Panel group if
	* the user is permitted to view the Control Panel.
	*
	* <ul>
	* <li>
	* Class name &quot;User&quot; includes the user's layout set
	* group.
	* </li>
	* <li>
	* Class name &quot;Organization&quot; includes the user's
	* immediate organization groups and inherited organization groups.
	* </li>
	* <li>
	* Class name &quot;Group&quot; includes the user's immediate
	* organization groups and site groups.
	* </li>
	* <li>
	* A <code>classNames</code>
	* value of <code>null</code> includes the user's layout set group,
	* organization groups, inherited organization groups, and site groups.
	* </li>
	* </ul>
	*
	* @param classNames the group entity class names (optionally
	<code>null</code>). For more information see {@link
	#getUserSitesGroups(String[], int)}.
	* @param max the maximum number of groups to return
	* @return the user's groups &quot;sites&quot;
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroups(String[],
	int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portal.model.Group> getUserPlaces(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserPlaces(classNames, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroups(long,
	String[], boolean, int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portal.model.Group> getUserPlaces(
		long userId, java.lang.String[] classNames,
		boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUserPlaces(userId, classNames, includeControlPanel, max);
	}

	/**
	* Returns the user's groups &quot;sites&quot; associated with the group
	* entity class names, including the Control Panel group if the user is
	* permitted to view the Control Panel.
	*
	* <ul>
	* <li>
	* Class name &quot;User&quot; includes the user's layout set
	* group.
	* </li>
	* <li>
	* Class name &quot;Organization&quot; includes the user's
	* immediate organization groups and inherited organization groups.
	* </li>
	* <li>
	* Class name &quot;Group&quot; includes the user's immediate
	* organization groups and site groups.
	* </li>
	* <li>
	* A <code>classNames</code>
	* value of <code>null</code> includes the user's layout set group,
	* organization groups, inherited organization groups, and site groups.
	* </li>
	* </ul>
	*
	* @param userId the primary key of the user
	* @param classNames the group entity class names (optionally
	<code>null</code>). For more information see {@link
	#getUserSitesGroups(long, String[], int)}.
	* @param max the maximum number of groups to return
	* @return the user's groups &quot;sites&quot;
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroups(long,
	String[], int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portal.model.Group> getUserPlaces(
		long userId, java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserPlaces(userId, classNames, max);
	}

	/**
	* Returns the number of the guest or current user's groups
	* &quot;sites&quot; associated with the group entity class names, including
	* the Control Panel group if the user is permitted to view the Control
	* Panel.
	*
	* @return the number of user's groups &quot;sites&quot;
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroupsCount()}
	*/
	@Deprecated
	public static int getUserPlacesCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserPlacesCount();
	}

	/**
	* Returns the guest or current user's layout set group, organization
	* groups, inherited organization groups, and site groups.
	*
	* @return the user's layout set group, organization groups, and
	inherited organization groups, and site groups
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #getUserSitesGroups}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portal.model.Group> getUserSites()
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserSites();
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserSitesGroups()
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserSitesGroups();
	}

	/**
	* Returns the guest or current user's groups &quot;sites&quot; associated
	* with the group entity class names, including the Control Panel group if
	* the user is permitted to view the Control Panel.
	*
	* <ul>
	* <li>
	* Class name &quot;User&quot; includes the user's layout set
	* group.
	* </li>
	* <li>
	* Class name &quot;Organization&quot; includes the user's
	* immediate organization groups and inherited organization groups.
	* </li>
	* <li>
	* Class name &quot;Group&quot; includes the user's immediate
	* organization groups and site groups.
	* </li>
	* <li>
	* A <code>classNames</code>
	* value of <code>null</code> includes the user's layout set group,
	* organization groups, inherited organization groups, and site groups.
	* </li>
	* </ul>
	*
	* @param classNames the group entity class names (optionally
	<code>null</code>). For more information see {@link
	#getUserSitesGroups(long, String[], boolean, int)}.
	* @param max the maximum number of groups to return
	* @return the user's groups &quot;sites&quot;
	* @throws PortalException if a portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getUserSitesGroups(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserSitesGroups(classNames, max);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserSitesGroups(
		long userId, java.lang.String[] classNames,
		boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUserSitesGroups(userId, classNames, includeControlPanel,
			max);
	}

	/**
	* Returns the user's groups &quot;sites&quot; associated with the group
	* entity class names, including the Control Panel group if the user is
	* permitted to view the Control Panel.
	*
	* <ul>
	* <li>
	* Class name &quot;User&quot; includes the user's layout set
	* group.
	* </li>
	* <li>
	* Class name &quot;Organization&quot; includes the user's
	* immediate organization groups and inherited organization groups.
	* </li>
	* <li>
	* Class name &quot;Group&quot; includes the user's immediate
	* organization groups and site groups.
	* </li>
	* <li>
	* A <code>classNames</code>
	* value of <code>null</code> includes the user's layout set group,
	* organization groups, inherited organization groups, and site groups.
	* </li>
	* </ul>
	*
	* @param userId the primary key of the user
	* @param classNames the group entity class names (optionally
	<code>null</code>). For more information see {@link
	#getUserSitesGroups(long, String[], boolean, int)}.
	* @param max the maximum number of groups to return
	* @return the user's groups &quot;sites&quot;
	* @throws PortalException if a portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getUserSitesGroups(
		long userId, java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserSitesGroups(userId, classNames, max);
	}

	/**
	* Returns the number of the guest or current user's groups
	* &quot;sites&quot; associated with the group entity class names, including
	* the Control Panel group if the user is permitted to view the Control
	* Panel.
	*
	* @return the number of user's groups &quot;sites&quot;
	* @throws PortalException if a portal exception occurred
	*/
	public static int getUserSitesGroupsCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserSitesGroupsCount();
	}

	/**
	* Returns <code>true</code> if the user is associated with the group,
	* including the user's inherited organizations and user groups. System and
	* staged groups are not included.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return <code>true</code> if the user is associated with the group;
	<code>false</code> otherwise
	* @throws PortalException if the current user did not have permission to
	view the user or group members
	*/
	public static boolean hasUserGroup(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().hasUserGroup(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Group> search(
		long companyId, long[] classNameIds, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(companyId, classNameIds, keywords, params, start,
			end, obc);
	}

	public static java.util.List<com.liferay.portal.model.Group> search(
		long companyId, long[] classNameIds, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(companyId, classNameIds, name, description, params,
			andOperator, start, end, obc);
	}

	/**
	* Returns an ordered range of all the site groups and organization groups
	* that match the name and description, optionally including the user's
	* inherited organization groups and user groups. System and staged groups
	* are not included.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the company
	* @param name the group's name (optionally <code>null</code>)
	* @param description the group's description (optionally
	<code>null</code>)
	* @param params the finder params (optionally <code>null</code>). To
	include the user's inherited organizations and user groups in the
	search, add entries having &quot;usersGroups&quot; and
	&quot;inherit&quot; as keys mapped to the the user's ID. For more
	information see {@link
	com.liferay.portal.service.persistence.GroupFinder}.
	* @param start the lower bound of the range of groups to return
	* @param end the upper bound of the range of groups to return (not
	inclusive)
	* @return the matching groups ordered by name
	* @throws PortalException if a portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.String[] params, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(companyId, name, description, params, start, end);
	}

	/**
	* Returns the number of groups and organization groups that match the name
	* and description, optionally including the user's inherited organizations
	* and user groups. System and staged groups are not included.
	*
	* @param companyId the primary key of the company
	* @param name the group's name (optionally <code>null</code>)
	* @param description the group's description (optionally
	<code>null</code>)
	* @param params the finder params (optionally <code>null</code>). To
	include the user's inherited organizations and user groups in the
	search, add entries having &quot;usersGroups&quot; and
	&quot;inherit&quot; as keys mapped to the the user's ID. For more
	information see {@link
	com.liferay.portal.service.persistence.GroupFinder}.
	* @return the number of matching groups
	*/
	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.String[] params) {
		return getService().searchCount(companyId, name, description, params);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Sets the groups associated with the role, removing and adding
	* associations as necessary.
	*
	* @param roleId the primary key of the role
	* @param groupIds the primary keys of the groups
	* @throws PortalException if the user did not have permission to update
	update the role
	*/
	public static void setRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().setRoleGroups(roleId, groupIds);
	}

	/**
	* Removes the groups from the role.
	*
	* @param roleId the primary key of the role
	* @param groupIds the primary keys of the groups
	* @throws PortalException if the user did not have permission to update the
	role
	*/
	public static void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsetRoleGroups(roleId, groupIds);
	}

	/**
	* Updates the group's friendly URL.
	*
	* @param groupId the primary key of the group
	* @param friendlyURL the group's new friendlyURL (optionally
	<code>null</code>)
	* @return the group
	* @throws PortalException if the user did not have permission to update the
	group, if a group with the primary key could not be found, or if
	a valid friendly URL could not be created for the group
	*/
	public static com.liferay.portal.model.Group updateFriendlyURL(
		long groupId, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateFriendlyURL(groupId, friendlyURL);
	}

	/**
	* Updates the group.
	*
	* @param groupId the primary key of the group
	* @param parentGroupId the primary key of the parent group
	* @param name the group's name
	* @param description the group's new description (optionally
	<code>null</code>)
	* @param type the group's new type. For more information see {@link
	GroupConstants}.
	* @param manualMembership whether manual membership is allowed for the
	group
	* @param membershipRestriction the group's membership restriction. For
	more information see {@link GroupConstants}.
	* @param friendlyURL the group's new friendlyURL (optionally
	<code>null</code>)
	* @param active whether the group is active
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the asset category IDs and asset
	tag names for the group.
	* @return the group
	* @throws PortalException if the user did not have permission to update
	the group, if a group with the primary key could not be
	found, if the friendly URL was invalid or could one not be
	created
	* @deprecated As of 7.0.0, replaced by {@link #updateGroup(long, long, Map,
	Map, int, boolean, int, String, boolean, boolean,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Group updateGroup(long groupId,
		long parentGroupId, java.lang.String name,
		java.lang.String description, int type, boolean manualMembership,
		int membershipRestriction, java.lang.String friendlyURL,
		boolean inheritContent, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateGroup(groupId, parentGroupId, name, description,
			type, manualMembership, membershipRestriction, friendlyURL,
			inheritContent, active, serviceContext);
	}

	public static com.liferay.portal.model.Group updateGroup(long groupId,
		long parentGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int type, boolean manualMembership, int membershipRestriction,
		java.lang.String friendlyURL, boolean inheritContent, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateGroup(groupId, parentGroupId, nameMap,
			descriptionMap, type, manualMembership, membershipRestriction,
			friendlyURL, inheritContent, active, serviceContext);
	}

	/**
	* Updates the group's type settings.
	*
	* @param groupId the primary key of the group
	* @param typeSettings the group's new type settings (optionally
	<code>null</code>)
	* @return the group
	* @throws PortalException if the user did not have permission to update the
	group or if a group with the primary key could not be found
	*/
	public static com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateGroup(groupId, typeSettings);
	}

	public static void updateStagedPortlets(long groupId,
		java.util.Map<java.lang.String, java.lang.String> stagedPortletIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateStagedPortlets(groupId, stagedPortletIds);
	}

	public static GroupService getService() {
		if (_service == null) {
			_service = (GroupService)PortalBeanLocatorUtil.locate(GroupService.class.getName());

			ReferenceRegistry.registerReference(GroupServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(GroupService service) {
	}

	private static GroupService _service;
}