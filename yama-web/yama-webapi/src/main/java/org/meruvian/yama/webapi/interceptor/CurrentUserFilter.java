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
package org.meruvian.yama.webapi.interceptor;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.meruvian.yama.web.SessionCredentials;
import org.springframework.stereotype.Service;

/**
 * Intercept request URI with path parameter {username}, replace parameter value
 * with current authorized username if parameter value equals "me"
 * 
 * @author Dian Aditya
 * 
 */
@Provider
@Service
@DetectCurrentUser
public class CurrentUserFilter implements ContainerRequestFilter {
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		UriInfo uriInfo = requestContext.getUriInfo();
		MultivaluedMap<String, String> parameters = uriInfo.getPathParameters();
		List<String> usernames = parameters.get("username");
		
		if (usernames == null) return;
		
		if (usernames.contains("me")) {
			usernames.set(usernames.indexOf("me"), SessionCredentials.getCurrentUsername());
		}
	}
}
