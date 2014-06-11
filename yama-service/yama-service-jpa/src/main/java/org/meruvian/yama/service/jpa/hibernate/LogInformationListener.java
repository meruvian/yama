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
package org.meruvian.yama.service.jpa.hibernate;

import java.util.Date;

import javax.inject.Inject;

import org.hibernate.event.spi.AbstractPreDatabaseOperationEvent;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.meruvian.yama.repository.DefaultPersistence;
import org.meruvian.yama.repository.LogInformation;
import org.meruvian.yama.service.SessionCredential;
import org.springframework.stereotype.Component;

/**
 * @author Dian Aditya
 *
 */
@Component
public class LogInformationListener implements PreInsertEventListener, PreUpdateEventListener {
	private SessionCredential sessionCredential;

	@Inject
	public void setSessionCredential(SessionCredential sessionCredential) {
		this.sessionCredential = sessionCredential;
	}
	
	private boolean onPreInsertOrUpdate(AbstractPreDatabaseOperationEvent event) {
		String username = sessionCredential.getCurrentUsername();
		
		if (event.getEntity() instanceof DefaultPersistence) {
			DefaultPersistence p = (DefaultPersistence) event.getEntity();
			LogInformation logInfo = p.getLogInformation();
			
			if (event instanceof PreUpdateEventListener) {
				logInfo.setLastUpdateDate(new Date());
				logInfo.setLastUpdateBy(username);
			} else if (event instanceof PreInsertEventListener) {
				logInfo.setCreateDate(new Date());
				logInfo.setLastUpdateDate(new Date());
				logInfo.setCreateBy(username);
				logInfo.setLastUpdateBy(username);
			}
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		return onPreInsertOrUpdate(event);
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		return onPreInsertOrUpdate(event);
	}

}
