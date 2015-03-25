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

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class EmailConfig implements EnvironmentAware {
	private RelaxedPropertyResolver props;
	
	@Bean
	@Scope("prototype")
	public HtmlEmail email() throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(props.getProperty("host"));
		email.setSmtpPort(props.getProperty("port", Integer.class, 0));
		email.setAuthentication(props.getProperty("username"), props.getProperty("password"));
		email.setFrom(props.getProperty("from_email"), props.getProperty("from_alias"));
		email.setSSLOnConnect(props.getProperty("ssl", Boolean.class, false));
		email.setStartTLSEnabled(props.getProperty("tls", Boolean.class, false));
		
		return email;
	}

	@Override
	public void setEnvironment(Environment env) {
		this.props = new RelaxedPropertyResolver(env, "email.");
	}
}
