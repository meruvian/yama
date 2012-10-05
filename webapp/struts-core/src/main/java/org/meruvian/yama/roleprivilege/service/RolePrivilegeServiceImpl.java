/**
 * Copyright 2012 Meruvian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.yama.roleprivilege.service;

import java.util.Date;

import javax.inject.Inject;

import org.meruvian.yama.modulefunction.ModuleFunction;
import org.meruvian.yama.modulefunction.dao.ModuleFunctionDomain;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.role.Role;
import org.meruvian.yama.role.dao.RoleDomain;
import org.meruvian.yama.roleprivilege.RolePrivilege;
import org.meruvian.yama.roleprivilege.dao.RolePrivilegeDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
	@Inject
	private RolePrivilegeDomain rolePrivilegeDomain;

	@Inject
	private ModuleFunctionDomain moduleFunctionDomain;

	@Inject
	private RoleDomain roleDomain;

	@Transactional
	public void save(RolePrivilege rolePrivilege) {
		if (rolePrivilege != null) {
			if (rolePrivilege.getId() == null
					|| "".equalsIgnoreCase(rolePrivilege.getId())) {
				rolePrivilege.getLogInformation().setCreateDate(new Date());
				rolePrivilege.getLogInformation().setUpdateDate(new Date());
				rolePrivilege.getLogInformation().setStatusFlag(
						StatusFlag.ACTIVE);

				rolePrivilegeDomain.persist(rolePrivilege);
			}
		}
	}

	public RolePrivilege findRolePrivilege(String roleId,
			String moduleFunctionId) {
		return rolePrivilegeDomain.findByUniqueConstraint(roleId,
				moduleFunctionId);
	}

	@Transactional
	public void delete(RolePrivilege rolePrivilege) {
		if (rolePrivilege != null && rolePrivilege.getId() != null) {
			rolePrivilege = getRolePrivilegeById(rolePrivilege.getId());

			rolePrivilegeDomain.delete(rolePrivilege);
		}
	}

	@Transactional
	public void delete(String roleId, String moduleFunctionId) {
		rolePrivilegeDomain.delete(findRolePrivilege(roleId, moduleFunctionId));
	}

	@Transactional(readOnly = true)
	public RolePrivilege getRolePrivilegeById(String id) {
		return getRolePrivilegeById(id);
	}

	public EntityListWrapper<ModuleFunction> findModuleFunctionByRole(
			String roleId, String orderby, String order, int max, int page) {

		return rolePrivilegeDomain.findModuleFuntionByRole(roleId, orderby,
				order, max, page);
	}

	public EntityListWrapper<ModuleFunction> findModuleFunctionByRolePrivilege(
			String roleId, String field, String orderby, String order, int max,
			int page) {
		Role role = roleDomain.load(roleId).getMaster();
		if (role == null) {
			return moduleFunctionDomain.findByRolePrivilege(roleId, field,
					orderby, order, max, page);
		} else {
			return rolePrivilegeDomain.findModuleFuntionByRole(roleId,
					role.getId(), field, orderby, order, max, page);
		}
	}
}
