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
package org.meruvian.yama.repository.jpa.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.meruvian.yama.repository.application.Application;
import org.meruvian.yama.repository.jpa.DefaultJpaPersistence;
import org.meruvian.yama.repository.jpa.JpaLogInformation;

/**
 * @author Dian Aditya
 *
 */
@Entity
@Table(name = "yama_application", indexes = { @Index(columnList = "create_by", unique = false) })
public class JpaApplication extends DefaultJpaPersistence implements Application {
	private String secret;
	private String namespace;
	private String displayName;
	private String domain;
	private String site;

	public JpaApplication() {}
	
	public JpaApplication(Application application) {
		update(application);
	}
	
	@Override
	public String getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	@Column(unique = true)
	public String getNamespace() {
		return namespace;
	}
	
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	@Column(name = "display_name")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@Override
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}

	@Override
	public void update(Application application) {
		this.id = application.getId();
		this.logInformation = new JpaLogInformation(application.getLogInformation());
		this.secret = application.getSecret();
		this.namespace = application.getNamespace();
		this.displayName = application.getDisplayName();
		this.domain = application.getDomain();
		this.site = application.getSite();
	}
	
}
