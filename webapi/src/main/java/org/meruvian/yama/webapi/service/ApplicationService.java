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

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.meruvian.yama.core.application.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Dian Aditya
 *
 */
@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
public interface ApplicationService {
	@GET
	@Path("/{id}")
	Application getApplicationById(@PathParam("id") String id);
	
	@POST
	Application saveApplication(Application application);
	
	@PUT
	@Path("/{id}")
	Application updateApplication(Application application);
	
	@POST
	@Path("/{id}/secret")
	Application generateNewSecret(@PathParam("id") String id);
	
	@DELETE
	@Path("/{id}")
	void removeApplication(@PathParam("id") String id);
	
	@GET
	Page<Application> findApplicationByName(@QueryParam("q") @DefaultValue("") String name, Pageable pageable);
}
