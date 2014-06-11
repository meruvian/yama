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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Dian Aditya
 *
 */
@Component
public class HibernateListenerConfigurer {
	private EntityManagerFactory entityManagerFactory;
	private LogInformationListener logInformationListener;
	
	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	@Inject
	public void setLogInformationListener(LogInformationListener logInformationListener) {
		this.logInformationListener = logInformationListener;
	}
	
	@PostConstruct
	public void init() {
		HibernateEntityManagerFactory f = (HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImpl sf = (SessionFactoryImpl) f.getSessionFactory();
		EventListenerRegistry registry = sf.getServiceRegistry().getService(EventListenerRegistry.class);
		registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(logInformationListener);
		registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(logInformationListener);
	}
}
