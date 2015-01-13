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

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author Dian Aditya
 *
 */
@Provider
@Component
public class PageableParameterInterceptor implements ContainerRequestFilter, ReaderInterceptor {
	public static final int DEFAULT_MAX_ROW = 10;
	public static final String MAX_PARAMETER = "max";
	public static final String PAGE_PARAMETER = "page";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		UriInfo uriInfo = requestContext.getUriInfo();
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
		
		int max = DEFAULT_MAX_ROW;
		if (queryParams.containsKey(MAX_PARAMETER)) {
			max = Integer.parseInt(queryParams.getFirst(MAX_PARAMETER));
		}
		
		int page = 0;
		if (queryParams.containsKey(PAGE_PARAMETER)) {
			page = Integer.parseInt(queryParams.getFirst(PAGE_PARAMETER));
		}
		
		requestContext.setProperty(MAX_PARAMETER, max);
		requestContext.setProperty(PAGE_PARAMETER, page);
	}
	
	@Override
	public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
		int max = (Integer) context.getProperty(MAX_PARAMETER);
		int page = (Integer) context.getProperty(PAGE_PARAMETER);
		
		if (context.getType().isAssignableFrom(Pageable.class)) {
			return new PageRequest(page, max);
		}
		
		return context.proceed();
	}
}
