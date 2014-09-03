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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.meruvian.yama.core.DefaultJpaPersistence;
import org.meruvian.yama.core.user.JpaUser;

/**
 * @author Dian Aditya
 *
 */
@Entity
@Table(name = "yama_user_role", uniqueConstraints = @UniqueConstraint(columnNames = { "role_id", "user_id" }))
public class JpaUserRole extends DefaultJpaPersistence implements UserRole {
	private JpaRole role = new JpaRole();
	private JpaUser user = new JpaUser();
	
	public JpaUserRole() {}

	public JpaUserRole(JpaRole role, JpaUser user) {
		this.role = role;
		this.user = user;
	}

	@Override
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	public JpaRole getRole() {
		return role;
	}
	
	public void setRole(JpaRole role) {
		this.role = role;
	}

	@Override
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	public JpaUser getUser() {
		return user;
	}

	public void setUser(JpaUser user) {
		this.user = user;
	}
}
