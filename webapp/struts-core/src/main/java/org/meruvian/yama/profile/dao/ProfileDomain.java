package org.meruvian.yama.profile.dao;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.persistence.access.DefaultPersistenceDAO;
import org.meruvian.yama.persistence.utils.PagingUtils;
import org.meruvian.yama.security.User;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDomain extends DefaultPersistenceDAO<User> {
	public EntityListWrapper<User> findCategoryByEmail(String id, String email,
			String username, int max, int page) {

		EntityListWrapper<User> list = new EntityListWrapper<User>();
		String criteria = "";

		if (!StringUtils.isEmpty(email)) {
			criteria = "j.id NOT LIKE ? AND j.email LIKE ?";
			criteria += " AND j.logInformation.statusFlag = ?";

			Object[] params = { id, email, StatusFlag.ACTIVE };
			for (int i = 0; i < params.length - 1; i++) {
				if (params[i] instanceof String || params[i] == null) {
					params[i] = StringUtils.defaultIfEmpty((String) params[i],
							"");
					params[i] = StringUtils.join(new String[] { "%",
							(String) params[i], "%" });
				}
			}

			TypedQuery<User> query = createQuery(entityClass, "j", "j",
					criteria, params);
			if (max > 0) {
				query.setMaxResults(max);
			}
			query.setFirstResult(page * max);

			long rowcount = getRowCount("j", criteria, params);
			list.setCurrentPage(page);
			list.setEntityList(query.getResultList());
			list.setLimit(max);
			list.setRowCount(rowcount);
			list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));
		}

		if (!StringUtils.isEmpty(username)) {
			criteria = "j.id NOT LIKE ? AND j.username LIKE ?";
			criteria += " AND j.logInformation.statusFlag = ?";

			Object[] params = { id, username, StatusFlag.ACTIVE };
			for (int i = 0; i < params.length - 1; i++) {
				if (params[i] instanceof String || params[i] == null) {
					params[i] = StringUtils.defaultIfEmpty((String) params[i],
							"");
					params[i] = StringUtils.join(new String[] { "%",
							(String) params[i], "%" });
				}
			}

			TypedQuery<User> query = createQuery(entityClass, "j", "j",
					criteria, params);
			if (max > 0) {
				query.setMaxResults(max);
			}
			query.setFirstResult(page * max);

			long rowcount = getRowCount("j", criteria, params);
			list.setCurrentPage(page);
			list.setEntityList(query.getResultList());
			list.setLimit(max);
			list.setRowCount(rowcount);
			list.setTotalPage(PagingUtils.getTotalPage(rowcount, max));
		}

		return list;
	}
}
