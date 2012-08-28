package org.meruvian.yama.jaxrs.dao;

import org.meruvian.yama.jaxrs.Person;
import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAO extends PersistenceDAO<Person> {

}
