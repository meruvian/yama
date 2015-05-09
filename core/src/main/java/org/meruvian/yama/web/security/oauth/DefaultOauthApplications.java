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
package org.meruvian.yama.web.security.oauth;

import java.util.HashMap;

import javax.inject.Inject;

import org.meruvian.yama.core.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Dian Aditya
 *
 */
@Component
public class DefaultOauthApplications extends HashMap<String, Application> {
	private static Logger log = LoggerFactory.getLogger(DefaultOauthApplications.class);
	
	@Inject
	private void setEnvironment(Environment env) {
		log.info("Configure default oauth2 clients");

		RelaxedPropertyResolver props = new RelaxedPropertyResolver(env, "rest.");
		
		String key = null;
		for (int i = 0; props.containsProperty((key = "clients[" + i + "]") + ".id"); i++) {
			String appId = props.getProperty(key + ".id");
			
			Application application = new Application();
			application.setId(appId);
			application.setSecret(props.getProperty(key + ".secret"));
			application.setRegisteredRedirectUri(props.getProperty(key + ".redirect"));
			application.setAutoApprove(true);
			
			put(appId, application);
			
			log.info("Application ID: {}", appId);
		}
	}
}
