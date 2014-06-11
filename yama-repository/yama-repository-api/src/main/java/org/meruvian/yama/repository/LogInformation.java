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
package org.meruvian.yama.repository;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Umar Khatab umar@intercitra.com
 * 
 */
public class LogInformation {
	private Date createDate = new Date();
	private Date lastUpdateDate;
	private String createBy;
	private String lastUpdateBy;
	private int activeFlag = ACTIVE;

	public final static int ACTIVE = 1;
	public final static int INACTIVE = 0;

	public LogInformation() {
	}

	public LogInformation(String userId, int flag) {
		this.createBy = userId;
		this.lastUpdateBy = userId;
		this.activeFlag = flag;
	}
	
	public LogInformation(LogInformation logInfo) {
		this.createBy = logInfo.getCreateBy();
		this.createDate = logInfo.getCreateDate();
		this.lastUpdateBy = logInfo.getLastUpdateBy();
		this.lastUpdateDate = logInfo.getLastUpdateDate();
		this.activeFlag = logInfo.getActiveFlag();
	}

	public LogInformation(LogInformation logInfo, String userId, int flag) {
		this(logInfo);
		this.lastUpdateBy = userId;
		this.lastUpdateDate = new Date();
		this.activeFlag = flag;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the lastUpdateDate
	 */
	@JsonIgnore
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate
	 *            the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return the createBy
	 */
	@JsonIgnore
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the lastUpdateBy
	 */
	@JsonIgnore
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	/**
	 * @param lastUpdateBy
	 *            the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * @return the activeFlag
	 */
	@JsonIgnore
	public int getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag
	 *            the activeFlag to set
	 */
	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	@JsonIgnore
	public boolean isActive() {
		return (getActiveFlag() == ACTIVE);
	}

	@JsonIgnore
	public boolean isInactive() {
		return (getActiveFlag() == INACTIVE);
	}
}
