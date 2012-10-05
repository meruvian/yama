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
package org.meruvian.yama.userprivilege;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.meruvian.yama.persistence.DefaultPersistence;
import org.meruvian.yama.role.Role;
import org.meruvian.yama.security.User;

/**
 * @author frans
 * 
 */
@Entity
@Table(name = "yama_user_role", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"id", "role", "user" }) })
public class UserPrivilege extends DefaultPersistence {
	private User user = new User();
	private Role role = new Role();

	@ManyToOne
	@JoinColumn(name = "role")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name = "user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
