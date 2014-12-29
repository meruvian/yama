/**
 * Copyright 2014 Meruvian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WIUserRoleHOUUserRole WARRANUserRoleIES OR CONDIUserRoleIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.yama.core.role;

import org.meruvian.yama.core.DefaultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 * 
 */
@Repository
public interface UserRoleRepository extends DefaultRepository<UserRole> {
	Page<UserRole> findByUserId(String id, Pageable pageable);
	
	Page<UserRole> findByUserUsername(String username, Pageable pageable);
	
	Page<UserRole> findByRoleId(String id, Pageable pageable);
	
	Page<UserRole> findByRoleName(String name, Pageable pageable);
	
	UserRole findByUserIdAndRoleId(String userId, String roleId);
}
