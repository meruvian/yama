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
package org.meruvian.yama.repository.jpa.user;

import org.meruvian.yama.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 * 
 */
@Repository
public interface JpaUserRepository extends UserRepository<JpaUser> {
	
	@Override
	@Query("SELECT u FROM JpaUser u WHERE u.username LIKE ?1%")
	Page<JpaUser> find(String keyword, Pageable pageable);
	
	@Override
	@Query("SELECT u FROM JpaUser u WHERE u.username LIKE ?1% AND u.logInformation.activeFlag = ?2")
	public Page<JpaUser> find(String keyword, int activeFlag, Pageable pageable);
	
	@Override
	@Query("SELECT ur.user FROM JpaUserRole ur WHERE ur.role.id = ?1")
	Page<JpaUser> findByRoleId(String roleId, Pageable pageable);
	
	@Override
	@Query("SELECT ur.user FROM JpaUserRole ur WHERE ur.role.name = ?1")
	Page<JpaUser> findByRoleName(String roleName, Pageable pageable);
}