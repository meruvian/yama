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
package org.meruvian.yama.persistence;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.meruvian.yama.security.SessionCredentials;
import org.meruvian.yama.security.User;

/**
 * @author Dian Aditya
 * 
 */

public class LogInformationListener {
	@PrePersist
	public void onPrePersist(Object entity) {
		if (entity instanceof DefaultPersistence) {
			User user = SessionCredentials.currentUser();
			DefaultPersistence e = (DefaultPersistence) entity;
			e.setLogInformation(new LogInformation());
			e.getLogInformation().setCreateBy(user);
			e.getLogInformation().setUpdateBy(user);
		}
	}

	@PreUpdate
	public void onPreUpdate(Object entity) {
		if (entity instanceof DefaultPersistence) {
			DefaultPersistence e = (DefaultPersistence) entity;
			e.getLogInformation().setUpdateBy(SessionCredentials.currentUser());
			e.getLogInformation().setUpdateDate(new Date());
		}
	}
}
