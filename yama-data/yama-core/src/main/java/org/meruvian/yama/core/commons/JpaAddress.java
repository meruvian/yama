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
package org.meruvian.yama.core.commons;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Dian Aditya
 *
 */
@Embeddable
public class JpaAddress extends Address {
	public JpaAddress() {}
	
	public JpaAddress(Address address) {
		super(address.getStreet1(), address.getStreet2(), address.getCity(), address.getState(), address.getZip());
	}
	
	@Override
	@Column(name = "address_city")
	public String getCity() {
		return super.getCity();
	}
	
	@Override
	@Column(name = "address_state")
	public String getState() {
		return super.getState();
	}
	
	@Override
	@Column(name = "address_street1")
	public String getStreet1() {
		return super.getStreet1();
	}
	
	@Override
	@Column(name = "address_street2")
	public String getStreet2() {
		return super.getStreet2();
	}

	@Override
	@Column(name = "address_zip")
	public String getZip() {
		return super.getZip();
	}
}