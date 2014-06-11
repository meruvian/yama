/**
 * Copyright 2004 Meruvian
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
package org.meruvian.yama.repository.commons;

import java.io.Serializable;

/**
 * @author Dian Aditya
 * 
 */
public class Name implements Serializable {
	private String prefix;
	private String first;
	private String middle;
	private String last;

	public Name() {}

	/**
	 * @param prefix
	 * @param first
	 * @param middle
	 * @param last
	 */
	public Name(String prefix, String first, String middle, String last) {
		this.prefix = prefix;
		this.first = first;
		this.middle = middle;
		this.last = last;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the first
	 */
	public String getFirst() {
		return first;
	}

	/**
	 * @param first
	 *            the first to set
	 */
	public void setFirst(String first) {
		this.first = first;
	}

	/**
	 * @return the middle
	 */
	public String getMiddle() {
		return middle;
	}

	/**
	 * @param middle
	 *            the middle to set
	 */
	public void setMiddle(String middle) {
		this.middle = middle;
	}

	/**
	 * @return the last
	 */
	public String getLast() {
		return last;
	}

	/**
	 * @param last
	 *            the last to set
	 */
	public void setLast(String last) {
		this.last = last;
	}
}