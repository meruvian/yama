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

import javax.inject.Inject;

import org.meruvian.yama.web.security.DefaultUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Dian Aditya
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Inject
	private DefaultUserDetailsService userDetailsService;
	
	@Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login", "/auth/login", "/login/social/**").anonymous()
				.antMatchers("/oauth/authorize").fullyAuthenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/auth/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/")
				.failureUrl("/login?failure")
				.and()
			.logout()
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				.and()
			.rememberMe()
				.userDetailsService(userDetailsService)
				.and()
			.csrf()
				.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
				.disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/oauth/uncache_approvals", "/oauth/cache_approvals")
				.antMatchers("/views/**")
				.antMatchers("/scripts/**")
				.antMatchers("/styles/**")
				.antMatchers("/images/**")
				.antMatchers("/bower_components/**");
	}
	
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true, jsr250Enabled = true)
	protected static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	    @Override
	    protected MethodSecurityExpressionHandler createExpressionHandler() {
	        return new OAuth2MethodSecurityExpressionHandler();
	    }
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder("yama");
	}
	
	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.noOpText();
	}
}
