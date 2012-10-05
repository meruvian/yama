/**
 * Copyright 2012 Meruvian
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
package org.meruvian.yama.struts.iterceptors;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Dian Aditya
 * 
 */
public class NoCacheInterceptor extends AbstractInterceptor implements
		StrutsStatics {

	private static final String CACHE_CONTROL_PARAM = "Cache-Control";
	private static final String CACHE_CONTROL_VALUE = "no-cache";

	private static final String PRAGMA_PARAM = "Pragma";
	private static final String PRAGMA_VALUE = "no-cache";

	private static final String EXPIRES_PARAM = "Expires";
	private static final int EXPIRES_VALUE = 0;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String inv = invocation.invoke();

		HttpServletResponse response = (HttpServletResponse) invocation
				.getInvocationContext().get(HTTP_RESPONSE);
		response.setHeader(CACHE_CONTROL_PARAM, CACHE_CONTROL_VALUE);
		response.setHeader(PRAGMA_PARAM, PRAGMA_VALUE);
		response.setIntHeader(EXPIRES_PARAM, EXPIRES_VALUE);

		return inv;
	}

}
