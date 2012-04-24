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
package org.meruvian.yama.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Dian Aditya
 * 
 */
@Embeddable
public class LogInformation implements Serializable {

	public enum StatusFlag {
		ACTIVE, INACTIVE
	}

	private Date createDate = new Date();
	private Date updateDate = new Date();
	private String createBy;
	private String updateBy;
	private StatusFlag statusFlag = StatusFlag.ACTIVE;

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	@Column(name = "create_by")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@JsonIgnore
	@Column(name = "update_by")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@JsonIgnore
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status_flag")
	public StatusFlag getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(StatusFlag statusFlag) {
		this.statusFlag = statusFlag;
	}

}
