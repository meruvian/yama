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
package org.meruvian.yama.webapi.config;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.meruvian.yama.core.DefaultPersistence;
import org.meruvian.yama.core.LogInformation;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.web.SessionCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class JpaLogInformationListenerConfig {
	@Inject
	private HibernateEntityManagerFactory entityManagerFactory;
	
	@Inject
	private JpaLogInformationListener logInformationListener;
	
	@PostConstruct
	public void postConstruct() {
		SessionFactoryImpl factory = (SessionFactoryImpl) entityManagerFactory.getSessionFactory();
		EventListenerRegistry registry = factory.getServiceRegistry()
				.getService(EventListenerRegistry.class);
		
		registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(logInformationListener);
		registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(logInformationListener);
	}
	
	@Bean
	public JpaLogInformationListener logInformationListener() {
		return new JpaLogInformationListener();
	}
	
	static class JpaLogInformationListener implements PreInsertEventListener, PreUpdateEventListener {
		private String getCurrentUserId() {
			User user = SessionCredentials.getCurrentUser();
			String userId = null;
			
			if (user != null) {
				userId = user.getId();
			}
			
			return userId;
		}
		
		@Override
		public boolean onPreInsert(PreInsertEvent event) {
			String userId = getCurrentUserId();
			
			if (event.getEntity() instanceof DefaultPersistence) {
				DefaultPersistence p = (DefaultPersistence) event.getEntity();
				LogInformation logInfo = p.getLogInformation();
			
				logInfo.setCreateDate(new Date());
				logInfo.setLastUpdateDate(new Date());
				logInfo.setCreateBy(userId);
				logInfo.setLastUpdateBy(userId);
				
				Object[] state = event.getState();
				
				for (int i = 0; i < state.length; i++) {
					if (state[i] instanceof LogInformation) {
						state[i] = logInfo;
						
						break;
					}
				}
			}
			
			return false;
		}

		@Override
		public boolean onPreUpdate(PreUpdateEvent event) {
			String userId = getCurrentUserId();
			
			if (event.getEntity() instanceof DefaultPersistence) {
				DefaultPersistence p = (DefaultPersistence) event.getEntity();
				LogInformation logInfo = p.getLogInformation();
				
				Object[] oldState = event.getOldState();
				
				for (int i = 0; i < oldState.length; i++) {
					if (oldState[i] instanceof LogInformation) {
						LogInformation oldLogInfo = (LogInformation) oldState[i];
						
						logInfo.setCreateDate(oldLogInfo.getCreateDate());
						logInfo.setLastUpdateDate(new Date());
						logInfo.setCreateBy(oldLogInfo.getCreateBy());
						logInfo.setLastUpdateBy(userId);
						
						break;
					}
				}
			}
			
			return false;
		}
	}
}
