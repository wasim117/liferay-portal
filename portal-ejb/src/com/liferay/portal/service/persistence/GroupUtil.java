/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="GroupUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupUtil {
	public static com.liferay.portal.model.Group create(
		java.lang.String groupId) {
		return getPersistence().create(groupId);
	}

	public static com.liferay.portal.model.Group remove(
		java.lang.String groupId)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(groupId));
		}

		com.liferay.portal.model.Group group = getPersistence().remove(groupId);

		if (listener != null) {
			listener.onAfterRemove(group);
		}

		return group;
	}

	public static com.liferay.portal.model.Group remove(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(group);
		}

		group = getPersistence().remove(group);

		if (listener != null) {
			listener.onAfterRemove(group);
		}

		return group;
	}

	public static com.liferay.portal.model.Group update(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = group.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(group);
			}
			else {
				listener.onBeforeUpdate(group);
			}
		}

		group = getPersistence().update(group);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(group);
			}
			else {
				listener.onAfterUpdate(group);
			}
		}

		return group;
	}

	public static com.liferay.portal.model.Group update(
		com.liferay.portal.model.Group group, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = group.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(group);
			}
			else {
				listener.onBeforeUpdate(group);
			}
		}

		group = getPersistence().update(group, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(group);
			}
			else {
				listener.onAfterUpdate(group);
			}
		}

		return group;
	}

	public static com.liferay.portal.model.Group findByPrimaryKey(
		java.lang.String groupId)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(groupId);
	}

	public static com.liferay.portal.model.Group fetchByPrimaryKey(
		java.lang.String groupId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(groupId);
	}

	public static com.liferay.portal.model.Group findByC_N(
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Group fetchByC_N(
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Group findByC_F(
		java.lang.String companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_F(companyId, friendlyURL);
	}

	public static com.liferay.portal.model.Group fetchByC_F(
		java.lang.String companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_F(companyId, friendlyURL);
	}

	public static com.liferay.portal.model.Group findByC_C_C(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Group fetchByC_C_C(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C_C(companyId, className, classPK);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByC_N(java.lang.String companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeByC_F(java.lang.String companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_F(companyId, friendlyURL);
	}

	public static void removeByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, className, classPK);
	}

	public static int countByC_N(java.lang.String companyId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countByC_F(java.lang.String companyId,
		java.lang.String friendlyURL) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_F(companyId, friendlyURL);
	}

	public static int countByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, className, classPK);
	}

	public static java.util.List getOrganizations(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk);
	}

	public static java.util.List getOrganizations(java.lang.String pk,
		int begin, int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk, begin, end);
	}

	public static java.util.List getOrganizations(java.lang.String pk,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk, begin, end, obc);
	}

	public static int getOrganizationsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getOrganizationsSize(pk);
	}

	public static boolean containsOrganization(java.lang.String pk,
		java.lang.String organizationPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsOrganization(pk, organizationPK);
	}

	public static boolean containsOrganizations(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsOrganizations(pk);
	}

	public static void addOrganization(java.lang.String pk,
		java.lang.String organizationPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().addOrganization(pk, organizationPK);
	}

	public static void addOrganization(java.lang.String pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().addOrganization(pk, organization);
	}

	public static void addOrganizations(java.lang.String pk,
		java.lang.String[] organizationPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().addOrganizations(pk, organizationPKs);
	}

	public static void addOrganizations(java.lang.String pk,
		java.util.List organizations)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().addOrganizations(pk, organizations);
	}

	public static void clearOrganizations(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().clearOrganizations(pk);
	}

	public static void removeOrganization(java.lang.String pk,
		java.lang.String organizationPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().removeOrganization(pk, organizationPK);
	}

	public static void removeOrganization(java.lang.String pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().removeOrganization(pk, organization);
	}

	public static void removeOrganizations(java.lang.String pk,
		java.lang.String[] organizationPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().removeOrganizations(pk, organizationPKs);
	}

	public static void removeOrganizations(java.lang.String pk,
		java.util.List organizations)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().removeOrganizations(pk, organizations);
	}

	public static void setOrganizations(java.lang.String pk,
		java.lang.String[] organizationPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().setOrganizations(pk, organizationPKs);
	}

	public static void setOrganizations(java.lang.String pk,
		java.util.List organizations)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().setOrganizations(pk, organizations);
	}

	public static java.util.List getPermissions(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk);
	}

	public static java.util.List getPermissions(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk, begin, end);
	}

	public static java.util.List getPermissions(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk, begin, end, obc);
	}

	public static int getPermissionsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getPermissionsSize(pk);
	}

	public static boolean containsPermission(java.lang.String pk,
		java.lang.String permissionPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsPermission(pk, permissionPK);
	}

	public static boolean containsPermissions(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsPermissions(pk);
	}

	public static void addPermission(java.lang.String pk,
		java.lang.String permissionPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().addPermission(pk, permissionPK);
	}

	public static void addPermission(java.lang.String pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().addPermission(pk, permission);
	}

	public static void addPermissions(java.lang.String pk,
		java.lang.String[] permissionPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().addPermissions(pk, permissionPKs);
	}

	public static void addPermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().addPermissions(pk, permissions);
	}

	public static void clearPermissions(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().clearPermissions(pk);
	}

	public static void removePermission(java.lang.String pk,
		java.lang.String permissionPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().removePermission(pk, permissionPK);
	}

	public static void removePermission(java.lang.String pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().removePermission(pk, permission);
	}

	public static void removePermissions(java.lang.String pk,
		java.lang.String[] permissionPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().removePermissions(pk, permissionPKs);
	}

	public static void removePermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().removePermissions(pk, permissions);
	}

	public static void setPermissions(java.lang.String pk,
		java.lang.String[] permissionPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().setPermissions(pk, permissionPKs);
	}

	public static void setPermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().setPermissions(pk, permissions);
	}

	public static java.util.List getRoles(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk);
	}

	public static java.util.List getRoles(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk, begin, end);
	}

	public static java.util.List getRoles(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk, begin, end, obc);
	}

	public static int getRolesSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getRolesSize(pk);
	}

	public static boolean containsRole(java.lang.String pk,
		java.lang.String rolePK) throws com.liferay.portal.SystemException {
		return getPersistence().containsRole(pk, rolePK);
	}

	public static boolean containsRoles(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsRoles(pk);
	}

	public static void addRole(java.lang.String pk, java.lang.String rolePK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().addRole(pk, rolePK);
	}

	public static void addRole(java.lang.String pk,
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().addRole(pk, role);
	}

	public static void addRoles(java.lang.String pk, java.lang.String[] rolePKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().addRoles(pk, rolePKs);
	}

	public static void addRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().addRoles(pk, roles);
	}

	public static void clearRoles(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().clearRoles(pk);
	}

	public static void removeRole(java.lang.String pk, java.lang.String rolePK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().removeRole(pk, rolePK);
	}

	public static void removeRole(java.lang.String pk,
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().removeRole(pk, role);
	}

	public static void removeRoles(java.lang.String pk,
		java.lang.String[] rolePKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().removeRoles(pk, rolePKs);
	}

	public static void removeRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().removeRoles(pk, roles);
	}

	public static void setRoles(java.lang.String pk, java.lang.String[] rolePKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().setRoles(pk, rolePKs);
	}

	public static void setRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().setRoles(pk, roles);
	}

	public static java.util.List getUserGroups(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUserGroups(pk);
	}

	public static java.util.List getUserGroups(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUserGroups(pk, begin, end);
	}

	public static java.util.List getUserGroups(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUserGroups(pk, begin, end, obc);
	}

	public static int getUserGroupsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUserGroupsSize(pk);
	}

	public static boolean containsUserGroup(java.lang.String pk,
		java.lang.String userGroupPK) throws com.liferay.portal.SystemException {
		return getPersistence().containsUserGroup(pk, userGroupPK);
	}

	public static boolean containsUserGroups(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUserGroups(pk);
	}

	public static void addUserGroup(java.lang.String pk,
		java.lang.String userGroupPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().addUserGroup(pk, userGroupPK);
	}

	public static void addUserGroup(java.lang.String pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().addUserGroup(pk, userGroup);
	}

	public static void addUserGroups(java.lang.String pk,
		java.lang.String[] userGroupPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().addUserGroups(pk, userGroupPKs);
	}

	public static void addUserGroups(java.lang.String pk,
		java.util.List userGroups)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().addUserGroups(pk, userGroups);
	}

	public static void clearUserGroups(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().clearUserGroups(pk);
	}

	public static void removeUserGroup(java.lang.String pk,
		java.lang.String userGroupPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeUserGroup(pk, userGroupPK);
	}

	public static void removeUserGroup(java.lang.String pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeUserGroup(pk, userGroup);
	}

	public static void removeUserGroups(java.lang.String pk,
		java.lang.String[] userGroupPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeUserGroups(pk, userGroupPKs);
	}

	public static void removeUserGroups(java.lang.String pk,
		java.util.List userGroups)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeUserGroups(pk, userGroups);
	}

	public static void setUserGroups(java.lang.String pk,
		java.lang.String[] userGroupPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().setUserGroups(pk, userGroupPKs);
	}

	public static void setUserGroups(java.lang.String pk,
		java.util.List userGroups)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().setUserGroups(pk, userGroups);
	}

	public static java.util.List getUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end, obc);
	}

	public static int getUsersSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static boolean containsUser(java.lang.String pk,
		java.lang.String userPK) throws com.liferay.portal.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	public static boolean containsUsers(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUsers(pk);
	}

	public static void addUser(java.lang.String pk, java.lang.String userPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().addUser(pk, userPK);
	}

	public static void addUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().addUser(pk, user);
	}

	public static void addUsers(java.lang.String pk, java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().addUsers(pk, userPKs);
	}

	public static void addUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().clearUsers(pk);
	}

	public static void removeUser(java.lang.String pk, java.lang.String userPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().removeUser(pk, userPK);
	}

	public static void removeUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().removeUser(pk, user);
	}

	public static void removeUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().removeUsers(pk, userPKs);
	}

	public static void removeUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().removeUsers(pk, users);
	}

	public static void setUsers(java.lang.String pk, java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, userPKs);
	}

	public static void setUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static GroupPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(GroupPersistence persistence) {
		_persistence = persistence;
	}

	private static GroupUtil _getUtil() {
		if (_util == null) {
			ApplicationContext ctx = SpringUtil.getContext();
			_util = (GroupUtil)ctx.getBean(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = GroupUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Group"));
	private static Log _log = LogFactory.getLog(GroupUtil.class);
	private static GroupUtil _util;
	private GroupPersistence _persistence;
}