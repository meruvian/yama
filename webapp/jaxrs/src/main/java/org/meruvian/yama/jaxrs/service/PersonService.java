package org.meruvian.yama.jaxrs.service;

import org.meruvian.yama.jaxrs.Person;
import org.meruvian.yama.persistence.EntityListWrapper;

public interface PersonService {

	Person save(Person person);
	
	Person merge(Person person);
	
	Person findById(String id);
	
	void removePerson(String id);

	EntityListWrapper<Person> findAll();
}
