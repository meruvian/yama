#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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
package ${package}.interceptor;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.service.SessionCredential;
import org.springframework.beans.factory.annotation.Value;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author Dian Aditya
 *
 */
public class SessionCredentialInterceptor extends AbstractInterceptor {
	private SessionCredential sessionCredential;
	private String adminRole;
	
	@Inject
	public void setSessionCredential(SessionCredential sessionCredential) {
		this.sessionCredential = sessionCredential;
	}
	
	@Value("${symbol_dollar}{init.role.admin}")
	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ValueStack stack = invocation.getStack();
		stack.set("currentUser", sessionCredential.getCurrentUser());
		stack.set("adminRole", adminRole);

		List<String> roles = sessionCredential.getCurrentRoles();
		boolean isAdmin = roles.contains(StringUtils.upperCase(adminRole));

		stack.set("currentRoles", roles);
		stack.set("isAdmin", isAdmin);
		
		return invocation.invoke();
	}

}
