/**
 * Copyright 2012 BlueOxygen Technology
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
package org.meruvian.yama.security.user;

import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 * 
 */
@Repository
public class BackendUserDAO extends PersistenceDAO<BackendUser> {
	private static final Log LOG = LogFactory.getLog(BackendUserDAO.class);

	public BackendUser getUserByUsername(String username) {
		TypedQuery<BackendUser> query = createQuery(BackendUser.class, "d",
				"d", "d.logInformation.statusFlag = ? AND d.username = ?",
				StatusFlag.ACTIVE, username);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			LOG.error("No entity found within key " + username, e);

			return null;
		}
	}

	@Override
	public long getRowCount() {
		String ql = "SELECT count(id) FROM " + entityClass.getName()
				+ " WHERE logInformation.statusFlag = ?";

		return entityManager.createQuery(ql, Long.class)
				.setParameter(1, StatusFlag.ACTIVE).getSingleResult();
	}
}
