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
package org.meruvian.yama.webapi.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meruvian.yama.core.application.Application;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = org.meruvian.yama.webapi.Application.class)
@WebAppConfiguration
@Transactional
public class ApplicationServiceTest {
	@Inject
	private ApplicationService applicationService;
	
	@Test
	public void testInitializedApplications() {
		Application application = applicationService.getApplicationById("419c6697-14b7-4853-880e-b68e3731e317");
		assertThat(application.getRegisteredRedirectUri(), is("http://localhost:8081"));
	}
	
	@Test
	public void testNumberOfInitializedApplication() {
		Page<Application> applications = applicationService.findApplicationByName("", null);
		assertThat(applications.getTotalElements(), is(2L));
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testApplicationValidation() {
		Application application = new Application();
		applicationService.saveApplication(application);
		
		applicationService.findApplicationByName("", null);
	}
}
