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
package org.meruvian.yama.userprivilege.service;

import java.util.Date;

import javax.inject.Inject;

import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.role.Role;
import org.meruvian.yama.role.dao.RoleDomain;
import org.meruvian.yama.userprivilege.UserPrivilege;
import org.meruvian.yama.userprivilege.dao.UserPrivilegeDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserPrivilegeServiceImpl implements UserPrivilegeService {
	@Inject
	private UserPrivilegeDomain userPrivilegeDomain;

	@Inject
	private RoleDomain roleDomain;

	@Transactional
	public void save(UserPrivilege userPrivilege) {
		if (userPrivilege != null) {
			if (userPrivilege.getId() == null
					|| "".equalsIgnoreCase(userPrivilege.getId())) {
				userPrivilege.getLogInformation().setCreateDate(new Date());
				userPrivilege.getLogInformation().setUpdateDate(new Date());
				userPrivilege.getLogInformation().setStatusFlag(
						StatusFlag.ACTIVE);

				userPrivilegeDomain.persist(userPrivilege);
			}
		}
	}

	public UserPrivilege findUserPrivilege(String userId, String roleId) {
		return userPrivilegeDomain.findByUniqueConstraint(userId, roleId);
	}

	@Transactional
	public void delete(UserPrivilege userPrivilege) {
		if (userPrivilege != null && userPrivilege.getId() != null) {
			userPrivilege = getUserPrivilegeById(userPrivilege.getId());

			userPrivilegeDomain.delete(userPrivilege);
		}
	}

	@Transactional
	public void delete(String userId, String roleId) {
		userPrivilegeDomain.delete(findUserPrivilege(userId, roleId));
	}

	@Transactional(readOnly = true)
	public UserPrivilege getUserPrivilegeById(String id) {
		return getUserPrivilegeById(id);
	}

	public EntityListWrapper<Role> findRoleByUser(String userId,
			String orderby, String order, int max, int page) {
		return userPrivilegeDomain.findRoleByUser(userId, orderby, "ASC", max,
				page);
	}

	public EntityListWrapper<Role> findRoleByUserPrivilege(String userId,
			String field, String orderby, String order, int max, int page) {
		return roleDomain.findByUserPrivilege(userId, field, orderby, order,
				max, page);
	}
}
