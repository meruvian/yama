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
package org.meruvian.yama.service.jaxrs;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.repository.user.DefaultUser;
import org.meruvian.yama.repository.user.User;
import org.springframework.data.domain.Page;

/**
 * @author Dian Aditya
 * 
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public interface UserService {
	@GET
	@Path("/{username}")
	User getUserByUsername(@PathParam("username") String username);

	@GET
	Page<? extends User> findUserByKeyword(@QueryParam("q") @DefaultValue("") String keyword, 
			@QueryParam("max") @DefaultValue("10") int max,
			@QueryParam("page") @DefaultValue("0") int page);

	@DELETE
	@Path("/{username}")
	boolean removeUser(@PathParam("username") String username);

	@POST
	User saveUser(DefaultUser user);

	@POST
	@Path("/{username}/password")
	User updateUserPassword(@PathParam("username") String username,
			@FormParam("cPassword") String currentPassword, 
			@FormParam("nPassword") String newPassword);
	
	@POST
	@Path("/{username}/roles")
	boolean addRoleToUser(@PathParam("username") String username, String roleName);
	
	@DELETE
	@Path("/{username}/roles")
	boolean removeRoleFromUser(@PathParam("username") String username, String roleName);
	
	@GET
	@Path("/{username}/roles")
	Page<? extends Role> findRoleByUser(@PathParam("username") String username, 
			@QueryParam("max") @DefaultValue("10") int max,
			@QueryParam("page") @DefaultValue("0") int page);
}
