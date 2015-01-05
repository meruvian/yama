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
package org.meruvian.yama.webapp.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class WebappConfig extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer {
	@Inject
	private Environment env;
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean();
		bean.setServlet(new ProxyServlet());
		bean.addUrlMappings(env.getProperty("rest.proxy.mapping"));
		
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("targetUri", env.getProperty("rest.proxy.url"));
		initParameters.put("log", env.getProperty("rest.proxy.log"));
		
		bean.setInitParameters(initParameters);
		
		return bean;
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		mappings.add("woff", "application/x-font-woff");

		container.setMimeMappings(mappings);
	}
}
