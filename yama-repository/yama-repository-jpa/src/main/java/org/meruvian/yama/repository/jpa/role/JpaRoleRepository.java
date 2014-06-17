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
package org.meruvian.yama.repository.jpa.role;

import org.meruvian.yama.repository.role.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 *
 */
@Repository
public interface JpaRoleRepository extends RoleRepository<JpaRole> {
	@Override
	@Query("SELECT r FROM JpaRole r WHERE r.name LIKE :keyword% OR r.description LIKE :keyword%")
	Page<JpaRole> find(@Param("keyword") String keyword, Pageable pageable);
	
	@Override
	@Query("SELECT r FROM JpaRole r WHERE (r.name LIKE :keyword% OR r.description LIKE :keyword%) AND r.logInformation.activeFlag = :flag")
	public Page<JpaRole> find(@Param("keyword") String keyword, @Param("flag") int activeFlag, Pageable pageable);
	
	@Override
	@Query("SELECT ur.role FROM JpaUserRole ur WHERE ur.user.id = ?1")
	Page<JpaRole> findByUserId(String userId, Pageable pageable);
	
	@Override
	@Query("SELECT ur.role FROM JpaUserRole ur WHERE ur.user.username = ?1")
	Page<JpaRole> findByUserUsername(String username, Pageable pageable);
}