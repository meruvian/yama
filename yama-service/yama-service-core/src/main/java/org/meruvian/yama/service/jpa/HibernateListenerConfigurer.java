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
package org.meruvian.yama.service.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;

/**
 * @author Dian Aditya
 *
 */
public class HibernateListenerConfigurer {
	private EventListenerRegistry registry;

	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		HibernateEntityManagerFactory f = (HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImpl sf = (SessionFactoryImpl) f.getSessionFactory();
		this.registry = sf.getServiceRegistry().getService(EventListenerRegistry.class);
	}
	
	public <T> void appendListener(EventType<T> eventType, T listener) {
		registry.getEventListenerGroup(eventType).appendListener(listener);
	}
	
	public <T> void prependListener(EventType<T> eventType, T listener) {
		registry.getEventListenerGroup(eventType).prependListener(listener);
	}
}
