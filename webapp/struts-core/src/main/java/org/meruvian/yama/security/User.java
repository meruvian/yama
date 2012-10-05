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
package org.meruvian.yama.security;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.meruvian.yama.persistence.DefaultPersistence;
import org.meruvian.yama.user.Address;
import org.meruvian.yama.user.Name;

/**
 * @author Dian Aditya
 * 
 */
@Entity
@Table(name = "yama_backend_user")
public class User extends DefaultPersistence {
	private String username;
	private String password;
	private Name name = new Name();
	private Address address = new Address();
	private String email;
	private String description;
	private String workspaceType;

	@Column(unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWorkspaceType() {
		return workspaceType;
	}

	public void setWorkspaceType(String workspaceType) {
		this.workspaceType = workspaceType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Embedded
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Embedded
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}