#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright 2011 Meruvian
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
package org.meruvian.yama.actions;

import org.apache.struts2.rest.RestActionSupport;
import org.meruvian.yama.actions.model.DefaultModelParam;
import org.meruvian.yama.actions.model.ModelParam;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Dian Aditya
 * 
 */
public class DefaultAction extends RestActionSupport implements
		ModelDriven<Object> {

	public static final String INDEX = "INDEX";
	public static final String CREATE = "CREATE";
	public static final String NEW = "NEW";
	public static final String EDIT = "EDIT";
	public static final String SHOW = "SHOW";
	public static final String DELETE = "DELETE";

	protected ModelParam parameter = new DefaultModelParam();
	protected Object model = null;

	protected String id() {
		return parameter.getString("id");
	}

	public Object getModel() {
		return model == null ? parameter : model;
	}

}
