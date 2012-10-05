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
package org.meruvian.yama.userprivilege.dao;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.meruvian.yama.persistence.utils.PagingUtils;
import org.meruvian.yama.role.Role;
import org.meruvian.yama.security.User;
import org.meruvian.yama.userprivilege.UserPrivilege;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 * 
 */
@Repository
public class UserPrivilegeDomain extends PersistenceDAO<UserPrivilege> {
	public EntityListWrapper<Role> findRoleByUser(String userId,
			String orderby, String order, int max, int page) {
		String criteria = "(ur.user.id = ?)";
		criteria += " AND ur.logInformation.statusFlag = ? ORDER BY "
				+ StringUtils.defaultIfEmpty(orderby, "ur.id") + " " + order;

		Object[] params = { userId, StatusFlag.ACTIVE };
		TypedQuery<Role> query = createQuery(Role.class, "ur.role", "ur",
				criteria, params);
		if (max > 0) {
			query.setMaxResults(max);
		}
		query.setFirstResult(page * max);

		long rowcount = getRowCount("ur", criteria, params);
		EntityListWrapper<Role> list = new EntityListWrapper<Role>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}

	public EntityListWrapper<User> findUserByRole(String roleId,
			String orderby, String order, int max, int page, String condition) {
		String criteria = "(ur.role.id = ?)";
		criteria += " AND ur.logInformation.statusFlag = ? ORDER BY "
				+ StringUtils.defaultIfEmpty(orderby, "ur.id") + " " + order;

		Object[] params = { roleId, StatusFlag.ACTIVE };
		TypedQuery<User> query = createQuery(User.class, "ur.user", "ur",
				criteria, params);
		if (max > 0) {
			query.setMaxResults(max);
		}
		query.setFirstResult(page * max);

		long rowcount = getRowCount("ur", criteria, params);
		EntityListWrapper<User> list = new EntityListWrapper<User>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}

	public UserPrivilege findByUniqueConstraint(String userId, String roleId) {
		TypedQuery<UserPrivilege> query = createQuery(entityClass, "ur", "ur",
				"ur.user.id = ? AND ur.role.id = ?", userId, roleId);

		return query.getSingleResult();
	}
}
