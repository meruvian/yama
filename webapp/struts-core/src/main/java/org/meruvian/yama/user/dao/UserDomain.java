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
package org.meruvian.yama.user.dao;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.meruvian.yama.persistence.utils.PagingUtils;
import org.meruvian.yama.security.User;
import org.springframework.stereotype.Repository;

/**
 * @author Edy Setiawan
 * 
 */
@Repository
public class UserDomain extends PersistenceDAO<User> {
	public EntityListWrapper<User> findUsers(String username, String nameFirst,
			String nameLast, String company, String position, String orderby,
			String order, int max, int page, String condition) {
		String criteria = "(c.username LIKE ? AND c.name.first LIKE ? AND c.name.last LIKE ?)";
		criteria = criteria.replace("AND", condition);
		criteria += " AND c.logInformation.statusFlag = ? ORDER BY " + orderby
				+ " " + order;

		Object[] params = { username, nameFirst, nameLast, StatusFlag.ACTIVE };
		for (int i = 0; i < params.length - 1; i++) {
			if (params[i] instanceof String || params[i] == null) {
				params[i] = StringUtils.defaultIfEmpty((String) params[i], "");
				params[i] = StringUtils.join(new String[] { "%",
						(String) params[i], "%" });
			}
		}

		TypedQuery<User> query = createQuery(entityClass, "c", "c", criteria,
				params);
		if (max > 0) {
			query.setMaxResults(max);
		}
		query.setFirstResult(page * max);

		long rowcount = getRowCount("c", criteria, params);
		EntityListWrapper<User> list = new EntityListWrapper<User>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}

	public User findUser(String username) {
		return createQuery(entityClass, "u", "u",
				"u.username = ? AND u.logInformation.statusFlag = ?", username,
				StatusFlag.ACTIVE).getSingleResult();
	}
	
	public User findUserByEmail(String email) {
		return createQuery(entityClass, "u", "u",
				"u.email = ? AND u.logInformation.statusFlag = ?", email,
				StatusFlag.ACTIVE).getSingleResult();
	}
}
