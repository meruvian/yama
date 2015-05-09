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
package org.meruvian.yama.webapi.config;

import org.meruvian.yama.webapi.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * @author Dian Aditya
 *
 */
@Configuration
@Profile(Application.PROFILE_DEV)
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class DevWebConfig extends WebMvcAutoConfigurationAdapter {	
	private final Logger log = LoggerFactory.getLogger(DevWebConfig.class);
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.debug("Configure dev's static web content");
			
		registry.addResourceHandler("/bower_components/**")
				.addResourceLocations("file:webapp/bower_components/");
		registry.addResourceHandler("/**").addResourceLocations("file:webapp/app/");
		
		super.addResourceHandlers(registry);
	}
}
