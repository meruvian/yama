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
package org.meruvian.yama.modulefunction.dao;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.modulefunction.ModuleFunction;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.meruvian.yama.persistence.utils.PagingUtils;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 * 
 */
@Repository
public class ModuleFunctionDomain extends PersistenceDAO<ModuleFunction> {
	public EntityListWrapper<ModuleFunction> findModuleFunction(String name,
			String description, String moduleUrl, String orderby, String order,
			int max, int page, String condition) {
		String criteria = "(m.name LIKE ? AND m.description LIKE ? AND m.moduleUrl LIKE ?)";
		criteria = criteria.replace("AND", condition);
		criteria += " AND m.logInformation.statusFlag = ? ORDER BY "
				+ StringUtils.defaultIfEmpty(orderby, "m.id") + " " + order;

		Object[] params = { name, description, moduleUrl, StatusFlag.ACTIVE };
		for (int i = 0; i < params.length - 1; i++) {
			if (params[i] instanceof String || params[i] == null) {
				params[i] = StringUtils.defaultIfEmpty((String) params[i], "");
				params[i] = StringUtils.join(new String[] { "%",
						(String) params[i], "%" });
			}
		}

		TypedQuery<ModuleFunction> query = createQuery(entityClass, "m", "m",
				criteria, params);
		if (max > 0) {
			query.setMaxResults(max);
		}
		query.setFirstResult(page * max);

		long rowcount = getRowCount("m", criteria, params);
		EntityListWrapper<ModuleFunction> list = new EntityListWrapper<ModuleFunction>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}

	public EntityListWrapper<ModuleFunction> findMasters(String masterId,
			String field, String orderby, String order, int max, int page) {
		String treePath = "";
		if (!StringUtils.isBlank(masterId)) {
			ModuleFunction master = load(masterId);
			treePath = master.getTreePath();
		}

		String criteria = "(m.name LIKE ? OR m.description LIKE ?) AND m.treePath NOT LIKE ? AND m.logInformation.statusFlag = ? ORDER BY "
				+ StringUtils.defaultIfEmpty(orderby, "m.id") + " " + order;

		Object[] params = { field, field,
				StringUtils.join(new Object[] { treePath, "%" }),
				StatusFlag.ACTIVE };
		for (int i = 0; i < params.length - 2; i++) {
			if (params[i] instanceof String || params[i] == null) {
				params[i] = StringUtils.defaultIfEmpty((String) params[i], "");
				params[i] = StringUtils.join(new String[] { "%",
						(String) params[i], "%" });
			}
		}

		TypedQuery<ModuleFunction> query = createQuery(entityClass, "m", "m",
				criteria, params);

		if (max > 0) {
			query.setMaxResults(max);
		}

		query.setFirstResult(page * max);

		long rowcount = getRowCount("m", criteria, params);
		EntityListWrapper<ModuleFunction> list = new EntityListWrapper<ModuleFunction>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}

	public EntityListWrapper<ModuleFunction> findChilds(String masterId,
			String field, String orderby, String order, int max, int page) {
		String criteria = "(m.name LIKE ? OR m.description LIKE ?) AND m.master.id = ? AND m.logInformation.statusFlag = ? ORDER BY "
				+ StringUtils.defaultIfEmpty(orderby, "m.id") + " " + order;

		Object[] params = { field, field, masterId, StatusFlag.ACTIVE };
		for (int i = 0; i < params.length - 2; i++) {
			if (params[i] instanceof String || params[i] == null) {
				params[i] = StringUtils.defaultIfEmpty((String) params[i], "");
				params[i] = StringUtils.join(new String[] { "%",
						(String) params[i], "%" });
			}
		}

		TypedQuery<ModuleFunction> query = createQuery(entityClass, "m", "m",
				criteria, params);

		if (max > 0) {
			query.setMaxResults(max);
		}

		query.setFirstResult(page * max);

		long rowcount = getRowCount("m", criteria, params);
		EntityListWrapper<ModuleFunction> list = new EntityListWrapper<ModuleFunction>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}

	public EntityListWrapper<ModuleFunction> findByRolePrivilege(String roleId,
			String field, String orderby, String order, int max, int page) {
		String criteria = "(m.name LIKE ? OR m.description LIKE ?)"
				+ " AND m.master IS NULL AND m.id NOT IN (SELECT rp.moduleFunction.id FROM RolePrivilege rp"
				+ " WHERE rp.role.id = ? AND rp.logInformation.statusFlag = ?)"
				+ " AND m.logInformation.statusFlag = ? ORDER BY "
				+ StringUtils.defaultIfEmpty(orderby, "m.id") + " " + order;

		Object[] params = { field, field, roleId, StatusFlag.ACTIVE,
				StatusFlag.ACTIVE };
		for (int i = 0; i < params.length - 3; i++) {
			if (params[i] instanceof String || params[i] == null) {
				params[i] = StringUtils.defaultIfEmpty((String) params[i], "");
				params[i] = StringUtils.join(new String[] { "%",
						(String) params[i], "%" });
			}
		}

		TypedQuery<ModuleFunction> query = createQuery(entityClass, "m", "m",
				criteria, params);

		if (max > 0) {
			query.setMaxResults(max);
		}

		query.setFirstResult(page * max);

		long rowcount = getRowCount("m", criteria, params);
		EntityListWrapper<ModuleFunction> list = new EntityListWrapper<ModuleFunction>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}
}
