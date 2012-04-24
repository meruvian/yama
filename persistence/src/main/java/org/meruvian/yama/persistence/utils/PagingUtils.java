/**
 * Copyright 2012 BlueOxygen Technology
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
package org.meruvian.yama.persistence.utils;

/**
 * @author Dian Aditya
 * 
 */
public class PagingUtils {
	protected PagingUtils() {
	}

	public static long getTotalPage(long rowcount, long limit) {
		if (limit < 1)
			return 0;

		long page = rowcount / limit;

		return rowcount % limit > 0 ? page + 1 : page;
	}
}
