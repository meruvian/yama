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
package org.meruvian.yama.roleprivilege.dao;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.modulefunction.ModuleFunction;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.meruvian.yama.persistence.utils.PagingUtils;
import org.meruvian.yama.roleprivilege.RolePrivilege;
import org.springframework.stereotype.Repository;

@Repository
public class RolePrivilegeDomain extends PersistenceDAO<RolePrivilege> {
	public EntityListWrapper<ModuleFunction> findModuleFuntionByRole(
			String roleId, String orderby, String order, int max, int page) {
		String criteria = "(rp.role.id = ?)";
		criteria += " AND rp.logInformation.statusFlag = ? ORDER BY "
				+ StringUtils.defaultIfEmpty(orderby, "rp.id") + " " + order;

		Object[] params = { roleId, StatusFlag.ACTIVE };
		TypedQuery<ModuleFunction> query = createQuery(ModuleFunction.class,
				"rp.moduleFunction", "rp", criteria, params);
		if (max > 0) {
			query.setMaxResults(max);
		}
		query.setFirstResult(page * max);

		long rowcount = getRowCount("rp", criteria, params);
		EntityListWrapper<ModuleFunction> list = new EntityListWrapper<ModuleFunction>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));
		return list;
	}

	public EntityListWrapper<ModuleFunction> findModuleFuntionByRole(
			String roleId, String masterId, String field, String orderby,
			String order, int max, int page) {
		String criteria = "(rp.moduleFunction.name LIKE ? OR rp.moduleFunction.description LIKE ?)"
				+ " AND rp.role.id = ? AND rp.moduleFunction.id NOT IN (SELECT moduleFunction.id FROM RolePrivilege"
				+ " WHERE role.id = ? AND logInformation.statusFlag = ?)"
				+ " AND rp.logInformation.statusFlag = ? ORDER BY "
				+ StringUtils.defaultIfEmpty(orderby, "rp.id") + " " + order;

		Object[] params = { field, field, masterId, roleId, StatusFlag.ACTIVE,
				StatusFlag.ACTIVE };
		for (int i = 0; i < params.length - 4; i++) {
			if (params[i] instanceof String || params[i] == null) {
				params[i] = StringUtils.defaultIfEmpty((String) params[i], "");
				params[i] = StringUtils.join(new String[] { "%",
						(String) params[i], "%" });
			}
		}

		TypedQuery<ModuleFunction> query = createQuery(ModuleFunction.class,
				"rp.moduleFunction", "rp", criteria, params);

		if (max > 0) {
			query.setMaxResults(max);
		}

		query.setFirstResult(page * max);

		long rowcount = getRowCount("rp", criteria, params);
		EntityListWrapper<ModuleFunction> list = new EntityListWrapper<ModuleFunction>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}

	public RolePrivilege findByUniqueConstraint(String roleId,
			String moduleFunctionId) {
		TypedQuery<RolePrivilege> query = createQuery(entityClass, "rp", "rp",
				"rp.role.id = ? AND rp.moduleFunction.id = ?", roleId,
				moduleFunctionId);

		return query.getSingleResult();
	}
}
