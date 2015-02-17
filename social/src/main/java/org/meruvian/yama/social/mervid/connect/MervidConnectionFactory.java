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
package org.meruvian.yama.social.mervid.connect;

import org.meruvian.yama.social.mervid.api.Mervid;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author Dian Aditya
 *
 */
public class MervidConnectionFactory extends OAuth2ConnectionFactory<Mervid> {

	public MervidConnectionFactory(String appId, String appSecret) {
		super("mervid", new MervidServiceProvider(appId, appSecret), new MervidAdapter());
	}
}
