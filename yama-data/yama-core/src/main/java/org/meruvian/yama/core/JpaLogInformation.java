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
package org.meruvian.yama.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * @author Dian Aditya
 * 
 */
@Embeddable
public class JpaLogInformation extends LogInformation {
	public JpaLogInformation() {
	}
	
	public JpaLogInformation(LogInformation logInformation) {
		super(logInformation);
	}
	
	@Column(name = "create_by")
	public String getCreateBy() {
		return super.getCreateBy();
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return super.getCreateDate();
	}

	@Column(name = "update_by")
	public String getLastUpdateBy() {
		return super.getLastUpdateBy();
	}

	@Column(name = "update_date")
	public Date getLastUpdateDate() {
		return super.getLastUpdateDate();
	}

	@Column(name = "active_flag")
	public int getActiveFlag() {
		return super.getActiveFlag();
	}

	@Transient
	public boolean isActive() {
		return (getActiveFlag() == ACTIVE);
	}

	@Transient
	public boolean isInactive() {
		return (getActiveFlag() == INACTIVE);
	}
}
