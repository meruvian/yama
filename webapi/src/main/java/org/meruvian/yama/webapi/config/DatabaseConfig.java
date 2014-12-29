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
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class DatabaseConfig {
	@Inject
	private Environment env;
	
	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(env.getProperty("db.datasource"));
		config.setMaximumPoolSize(env.getProperty("db.max_pool_size", int.class));
		config.setConnectionTestQuery(env.getProperty("db.test_query"));
		
		config.addDataSourceProperty("url", env.getProperty("db.url"));
		config.addDataSourceProperty("user", env.getProperty("db.username"));
		config.addDataSourceProperty("password", env.getProperty("db.password"));
		
		return new HikariDataSource(config);
	}
}
