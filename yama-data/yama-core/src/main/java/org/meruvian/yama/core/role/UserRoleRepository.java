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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.yama.core.role;

import org.meruvian.yama.core.DefaultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Dian Aditya
 * 
 */
public interface UserRoleRepository<T extends UserRole> extends DefaultRepository<T> {
	Page<T> findByUserId(String id, Pageable pageable);
	
	Page<T> findByUserUsername(String username, Pageable pageable);
	
	Page<T> findByRoleId(String id, Pageable pageable);
	
	Page<T> findByRoleName(String name, Pageable pageable);
	
	T findByUserIdAndRoleId(String userId, String roleId);
}
