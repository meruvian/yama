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
package org.meruvian.yama.userprivilege.service;

import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.role.Role;
import org.meruvian.yama.userprivilege.UserPrivilege;

public interface UserPrivilegeService {
	void save(UserPrivilege rolePrivilege);

	void delete(UserPrivilege rolePrivilege);

	void delete(String roleId, String moduleFunctionId);

	UserPrivilege getUserPrivilegeById(String id);

	UserPrivilege findUserPrivilege(String roleId, String moduleFunctionId);

	EntityListWrapper<Role> findRoleByUser(String userId, String orderby,
			String order, int max, int page);

	EntityListWrapper<Role> findRoleByUserPrivilege(String roleId,
			String field, String orderby, String order, int max, int page);
}
