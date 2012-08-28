package org.meruvian.yama.jaxrs.service;

import javax.inject.Inject;

import org.meruvian.yama.jaxrs.Person;
import org.meruvian.yama.jaxrs.dao.PersonDAO;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

	@Inject
	private PersonDAO dao;

	@Transactional
	@Override
	public Person save(Person person) {
		dao.persist(person);
		return person;
	}

	@Override
	public Person findById(String id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public void removePerson(String id) {
		Person person = findById(id);
		dao.delete(person);
	}

	@Override 
	@Transactional
	public Person merge(Person person) {
		return dao.merge(person);
	}

	@Override
	public EntityListWrapper<Person> findAll() {
		return dao.getAll(10, 0);
	}
}
