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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meruvian.yama.persistence.DefaultPersistence;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 * 
 */
public abstract class PersistenceManager<T extends DefaultPersistence> {

	private static final Log LOG = LogFactory.getLog(PersistenceManager.class);

	public T findById(String id) {
		return getDao().findById(id);
	}

	@Transactional
	public void save(T object) {

		if (object.getId() == null) {
			getDao().persist(object);
		} else {
			getDao().merge(object);
		}
	}

	@Transactional
	public void delete(T object) {
		getDao().delete(object);
	}

	public EntityListWrapper<T> getAllByLimit(int limit, int page) {
		return getDao().getAll(limit, page);
	}

	public long rowCount() {
		return getDao().getRowCount();
	}

	public long totalPage(int limit) {
		return getDao().getTotalPage(limit);
	}

	protected abstract PersistenceDAO<T> getDao();
}
