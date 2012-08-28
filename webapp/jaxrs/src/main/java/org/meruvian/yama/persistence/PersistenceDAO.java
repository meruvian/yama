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
package org.meruvian.yama.persistence;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.persistence.utils.PagingUtils;

/**
 * @author Dian Aditya
 * 
 */
public class PersistenceDAO<T extends DefaultPersistence> {

	public static final Log LOG = LogFactory.getLog(PersistenceDAO.class);

	@PersistenceContext
	protected EntityManager entityManager;

	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public PersistenceDAO() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void persist(T transientInstance) {
		LOG.debug("persisting " + entityClass.getSimpleName() + " instance");
		try {
			entityManager.persist(transientInstance);
			LOG.debug("persist successful");
		} catch (RuntimeException re) {
			LOG.error("persist failed", re);
			throw re;
		}
	}

	public void attachClean(T instance) {
		LOG.debug("attaching clean " + entityClass.getSimpleName()
				+ " instance");
		try {
			entityManager.lock(instance, LockModeType.NONE);
			LOG.debug("attach successful");
		} catch (RuntimeException re) {
			LOG.error("attach failed", re);
			throw re;
		}
	}

	public void delete(T persistentInstance) {
		LOG.debug("deleting " + entityClass.getSimpleName() + " instance");
		try {
			entityManager.remove(persistentInstance);
			LOG.debug("delete successful");
		} catch (RuntimeException re) {
			LOG.error("delete failed", re);
			throw re;
		}
	}

	public T merge(T detachedInstance) {
		LOG.debug("merging " + entityClass.getSimpleName() + " instance");
		try {
			T result = entityManager.merge(detachedInstance);
			LOG.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			LOG.error("merge failed", re);
			throw re;
		}
	}

	public T findById(String id) {
		LOG.debug("getting " + entityClass.getSimpleName()
				+ " instance with id: " + id);
		try {
			T instance = entityManager.find(entityClass, id);

			if (instance == null) {
				LOG.debug("get successful, no instance found");
			} else {
				LOG.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			LOG.error("get failed", re);
			throw re;
		}
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

	public EntityListWrapper<T> getAll(int limit, int page) {
		TypedQuery<T> query = createQuery(entityClass, "d", "d",
				"d.logInformation.statusFlag = ?", StatusFlag.ACTIVE);

		if (limit > 0) {
			query.setMaxResults(limit);
		}

		query.setFirstResult(page);

		EntityListWrapper<T> paging = new EntityListWrapper<T>();
		paging.setCurrentPage(page);
		paging.setLimit(limit);
		paging.setEntityList(query.getResultList());

		return paging;
	}

	public long getRowCount() {
		return getRowCount("d", "d.logInformation.statusFlag = ?",
				StatusFlag.ACTIVE);
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

		entityManager.clear();
		String ql = "SELECT " + select.trim() + " FROM "
				+ entityClass.getName() + " " + alias.trim() + " WHERE "
				+ criteria;

		TypedQuery<X> query = entityManager.createQuery(ql, resultClass);

		for (int i = 1; i <= parameters.length; i++) {
			query.setParameter(i, parameters[i - 1]);
		}

		return query;
	}

	public long getTotalPage(int limit) {
		return PagingUtils.getTotalPage(getRowCount(), limit);
	}
}
