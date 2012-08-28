package org.meruvian.yama.jaxrs;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.meruvian.yama.persistence.DefaultPersistence;

@Entity
@Table(name="Y_PERSON")
public class Person extends DefaultPersistence {

	private static final long serialVersionUID = -6349955081299979341L;

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
