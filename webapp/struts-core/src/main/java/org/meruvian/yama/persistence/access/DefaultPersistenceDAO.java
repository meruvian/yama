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
package org.meruvian.yama.persistence.access;

import java.lang.reflect.ParameterizedType;

import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meruvian.yama.persistence.DefaultPersistence;

/**
 * @author Dian Aditya
 * 
 */
public class DefaultPersistenceDAO<T extends DefaultPersistence> extends
		PersistenceDAO<T> {

	public static final Log LOG = LogFactory
			.getLog(DefaultPersistenceDAO.class);

	public DefaultPersistenceDAO() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T load(String id) {
		LOG.debug("loading " + entityClass.getSimpleName()
				+ " instance with id: " + id);
		try {
			T instance = entityManager.getReference(entityClass, id);

			if (instance == null) {
				LOG.debug("load successful, no instance found");
			} else {
				LOG.debug("load successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			LOG.error("load failed", re);
			throw re;
		}
	}

	protected long getRowCount(String alias, String criteria,
			Object... parameters) {
		LOG.debug("getting " + entityClass.getSimpleName() + " count");
		try {
			long count = createQuery(Long.class, "count(id)", alias, criteria,
					parameters).getSingleResult();

			LOG.debug("get successful, return " + count + " row");

			return count;
		} catch (RuntimeException re) {
			LOG.error("get failed", re);
			throw re;
		}
	}

	protected <X> TypedQuery<X> createQuery(Class<X> resultClass,
			String select, String alias, String criteria, Object... parameters) {
		String ql = "SELECT " + select.trim() + " FROM "
				+ entityClass.getName() + " " + alias.trim() + " WHERE "
				+ criteria;

		TypedQuery<X> query = entityManager.createQuery(ql, resultClass);

		for (int i = 1; i <= parameters.length; i++) {
			query.setParameter(i, parameters[i - 1]);
		}

		return query;
	}
}