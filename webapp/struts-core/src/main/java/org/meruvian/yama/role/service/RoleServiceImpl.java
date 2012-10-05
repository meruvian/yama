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
package org.meruvian.yama.role.service;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.role.Role;
import org.meruvian.yama.role.dao.RoleDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 * 
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
	@Inject
	private RoleDomain roleDomain;

	@Transactional
	public Role saveRole(Role role) {
		String treePath = "";

		if (role.getMaster() != null) {
			Role master = role.getMaster();
			if (StringUtils.isBlank(master.getId())) {
				role.setMaster(null);
			} else {
				master = roleDomain.load(master.getId());
				role.setMaster(master);
				treePath = master.getTreePath();
			}
		}

		if (StringUtils.isBlank(role.getId())) {
			role.setId(null);
			role.setLogInformation(new LogInformation());

			roleDomain.persist(role);
		} else {
			Role r = roleDomain.load(role.getId());
			r.setName(role.getName());
			r.setDescription(role.getDescription());

			r.getLogInformation().setUpdateDate(new Date());

			role = r;
		}

		treePath = StringUtils.isBlank(treePath) ? "" : (treePath + ".");
		role.setTreePath(treePath + role.getId());

		return role;
	}

	@Transactional
	public void deleteRole(Role role) {
		role = roleDomain.load(role.getId());
		role.getLogInformation().setStatusFlag(StatusFlag.INACTIVE);
	}

	public Role getRoleById(String id) {
		return roleDomain.findById(id);
	}

	public EntityListWrapper<Role> findRoles(String name, String description,
			String orderby, String order, int max, int page) {
		return roleDomain.findRole(name, description, orderby, order, max,
				page, "AND");
	}

	public EntityListWrapper<Role> findRoles(String field, int max,
			String orderBy, String order, int page) {
		return roleDomain.findRole(field, field, orderBy, order, max, page,
				"OR");
	}

	public EntityListWrapper<Role> findMastersForRole(String masterId,
			String field, String orderby, String order, int max, int page) {
		return roleDomain.findMasters(masterId, field, orderby, order, max,
				page);
	}

	public EntityListWrapper<Role> findRoles(String field, Role createBy,
			String orderby, String order, int max, int page) {
		return roleDomain.findRole(field, field, createBy, orderby, order, max,
				page, "OR");
	}

	public Role saveRoleLocation(Role role) {
		return null;
	}
}
