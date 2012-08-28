package org.meruvian.yama.jaxrs.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.meruvian.yama.jaxrs.Person;
import org.meruvian.yama.jaxrs.service.PersonService;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/person")
public class PersonRest {

	@Autowired
	private PersonService personService;

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Person addPerson(Person person) {
		personService.save(person);
		return person;
	}

	@GET
	@Path("/{personid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@PathParam("personid") String personid) {
		return personService.findById(personid);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public EntityListWrapper<Person> getPersons() {
		return personService.findAll();
	}

	@DELETE
	@Path("/{personid}")
	public void removeBook(@PathParam("personid") String personid) {
		personService.removePerson(personid);
	}

	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Person editPerson(Person person) {
		personService.merge(person);
		return person;
	}
}