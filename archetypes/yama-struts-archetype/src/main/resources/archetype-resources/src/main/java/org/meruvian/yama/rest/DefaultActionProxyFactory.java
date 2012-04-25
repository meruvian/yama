#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright 2012 Meruvian Foundation
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
package org.meruvian.yama.rest;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.rest.RestActionProxyFactory;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.actions.annotations.ActionParam;

import com.opensymphony.xwork2.ActionProxy;

/**
 * @author Dian Aditya
 * 
 */
public class DefaultActionProxyFactory extends RestActionProxyFactory {
	private static final Log LOG = LogFactory
			.getLog(RestActionProxyFactory.class);

	public ActionProxy createActionProxy(String namespace, String actionName,
			String methodName, Map extraContext, boolean executeResult,
			boolean cleanupContext) {

		ActionProxy proxy = super.createActionProxy(namespace, actionName,
				methodName, extraContext, executeResult, cleanupContext);

		Object action = proxy.getAction();

		if (action instanceof DefaultAction) {
			Object model = ((DefaultAction) action).getModel();

			if (model instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) model;
				try {
					for (Field field : action.getClass().getDeclaredFields()) {
						if (field.isAnnotationPresent(ActionParam.class)) {
							field.setAccessible(true);

							map.put(field.getAnnotation(ActionParam.class)
									.value(), field.get(action));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return proxy;
	}
}
