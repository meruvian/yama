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
package org.meruvian.yama.webapi.service.commons;

import java.io.IOException;
import java.io.StringWriter;

import javax.inject.Inject;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Dian Aditya
 *
 */
@Service
public class EmailService {
	private final Logger log = LoggerFactory.getLogger(EmailService.class);
	
	@Inject
	private HtmlEmail htmlEmail;
	
	@Inject
	private Configuration configuration;

	@Async
	public void sendEmail(String to, String subject, String template, Object templateModel) {
		log.debug("Sending email to {}", to);
		
		try {
			StringWriter emailContent = new StringWriter();
			Template emailTemplate = configuration.getTemplate(template);
			emailTemplate.process(templateModel, emailContent);
			
			htmlEmail.setSubject(subject);
			htmlEmail.addTo(to);
			htmlEmail.setHtmlMsg(emailContent.toString());
			htmlEmail.send();
		} catch (IOException e) {
			log.error("Error occured while creating email template", e);
		} catch (TemplateException e) {
			log.error("Error occured while creating email template", e);
		} catch (EmailException e) {
			log.warn("Email could not be sent to {}", to, e);
		}
	}
}
