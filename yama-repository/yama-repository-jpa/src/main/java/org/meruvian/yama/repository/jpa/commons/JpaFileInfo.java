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
package org.meruvian.yama.repository.jpa.commons;

import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.meruvian.yama.repository.commons.FileInfo;
import org.meruvian.yama.repository.jpa.DefaultJpaPersistence;
import org.meruvian.yama.repository.jpa.JpaLogInformation;

/**
 * @author Dian Aditya
 *
 */
@Entity
@Table(name = "yama_file_info")
public class JpaFileInfo extends DefaultJpaPersistence implements FileInfo {
	private String originalName;
	private String contentType;
	private String path;
	private long size = 0;
	private InputStream dataBlob;
	
	public JpaFileInfo() {}
	
	public JpaFileInfo(FileInfo info) {
		this.id = info.getId();
		this.logInformation = new JpaLogInformation(info.getLogInformation());
		this.originalName = info.getOriginalName();
		this.contentType = info.getContentType();
		this.path = info.getPath();
		this.size = info.getSize();
		this.dataBlob = info.getDataBlob();
	}
	
	@Override
	@Column(name = "content_type")
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	@Transient
	public InputStream getDataBlob() {
		return dataBlob;
	}
	
	public void setDataBlob(InputStream dataBlob) {
		this.dataBlob = dataBlob;
	}
	
	@Override
	@Column(name = "original_name", nullable = false)
	public String getOriginalName() {
		return originalName;
	}
	
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	@Override
	@Column(nullable = false)
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	@Column(nullable = false)
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
}
