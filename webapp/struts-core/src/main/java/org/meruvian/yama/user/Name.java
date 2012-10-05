/**
 * Copyright 2012 Meruvian
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
package org.meruvian.yama.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * @since 1.0
 * 
 */
@Embeddable
public class Name implements Serializable {
	private String prefix;
	private String first;
	private String middle;
	private String last;

	@Transient
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name = "first_name", length = 128)
	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	@Transient
	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	@Column(name = "last_name", length = 128)
	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
}
