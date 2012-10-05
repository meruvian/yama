package org.meruvian.yama.signup.dao;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.meruvian.yama.persistence.utils.PagingUtils;
import org.meruvian.yama.security.User;
import org.springframework.stereotype.Repository;

@Repository
public class SignUpDomain extends PersistenceDAO<User> {

	public EntityListWrapper<User> findCategoryByEmail(String email,
			String username, int max, int page) {
		String criteria = "(j.username LIKE ? OR j.email LIKE ?)";
		criteria += " AND j.logInformation.statusFlag = ?";

		Object[] params = { username, email, StatusFlag.ACTIVE };
		for (int i = 0; i < params.length - 1; i++) {
			if (params[i] instanceof String || params[i] == null) {
				params[i] = StringUtils.defaultIfEmpty((String) params[i], "");
				params[i] = StringUtils.join(new String[] { "%",
						(String) params[i], "%" });
			}
		}

		TypedQuery<User> query = createQuery(entityClass, "j", "j", criteria,
				params);
		if (max > 0) {
			query.setMaxResults(max);
		}
		query.setFirstResult(page * max);

		long rowcount = getRowCount("j", criteria, params);
		EntityListWrapper<User> list = new EntityListWrapper<User>();
		list.setCurrentPage(page);
		list.setEntityList(query.getResultList());
		list.setLimit(max);
		list.setRowCount(rowcount);
		list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));

		return list;
	}
}
