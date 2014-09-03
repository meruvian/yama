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
package org.meruvian.yama.core.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.meruvian.yama.core.DefaultJpaPersistence;
import org.meruvian.yama.core.JpaLogInformation;
import org.meruvian.yama.core.commons.Address;
import org.meruvian.yama.core.commons.JpaAddress;
import org.meruvian.yama.core.commons.JpaFileInfo;
import org.meruvian.yama.core.commons.JpaName;
import org.meruvian.yama.core.commons.Name;
import org.meruvian.yama.core.role.JpaUserRole;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dian Aditya
 * 
 */
@Entity
@Table(name = "yama_backend_user")
public class JpaUser extends DefaultJpaPersistence implements User {
	private String username;
	private String password;
	private String email;
	private JpaName name = new JpaName();
	private JpaAddress address = new JpaAddress();
	private JpaFileInfo fileInfo;
	private List<JpaUserRole> roles = new ArrayList<JpaUserRole>();
	
	public JpaUser() {
	}
	
	public JpaUser(String username, String email, Name name, Address address) {
		update(username, email, name, address);
	}

	public JpaUser(User user) {
		update(user);
	}
	
	private void update(String username, String email, Name name, Address address) {
		this.username = username;
		this.email = email;
		this.name = new JpaName(name);
		this.address = new JpaAddress(address);
	}
	
	@Override
	public void update(User user) {
		this.id = user.getId();
		this.logInformation = new JpaLogInformation(user.getLogInformation());
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.name = new JpaName(user.getName());
		this.address = new JpaAddress(user.getAddress());
	}

	@Override
	@Column(name = "username", unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	@Embedded
	public JpaName getName() {
		return name;
	}

	public void setName(JpaName name) {
		this.name = name;
	}
	
	@Override
	@Embedded
	public JpaAddress getAddress() {
		return address;
	}

	public void setAddress(JpaAddress address) {
		this.address = address;
	}

	@Override
	@ManyToOne
	@JoinColumn(name = "file_info_id")
	public JpaFileInfo getFileInfo() {
		return fileInfo;
	}
	
	public void setFileInfo(JpaFileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	public List<JpaUserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<JpaUserRole> roles) {
		this.roles = roles;
	}
}