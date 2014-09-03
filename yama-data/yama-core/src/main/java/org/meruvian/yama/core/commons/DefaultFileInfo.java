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
package org.meruvian.yama.core.commons;

import java.io.InputStream;

import org.meruvian.yama.core.LogInformation;

/**
 * @author Dian Aditya
 *
 */
public class DefaultFileInfo implements FileInfo {
	private String id;
	private LogInformation logInformation = new LogInformation();
	private String originalName;
	private String contentType;
	private String path;
	private long size = 0;
	private InputStream dataBlob;
	
	public DefaultFileInfo() {}
	
	public DefaultFileInfo(FileInfo info) {
		this.id = info.getId();
		this.logInformation = info.getLogInformation();
		this.originalName = info.getOriginalName();
		this.contentType = info.getContentType();
		this.path = info.getPath();
		this.size = info.getSize();
		this.dataBlob = info.getDataBlob();
	}
	
	@Override
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public InputStream getDataBlob() {
		return dataBlob;
	}
	
	public void setDataBlob(InputStream dataBlob) {
		this.dataBlob = dataBlob;
	}
	
	@Override
	public String getOriginalName() {
		return originalName;
	}
	
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	@Override
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public LogInformation getLogInformation() {
		return logInformation;
	}
	
	public void setLogInformation(LogInformation logInformation) {
		this.logInformation = logInformation;
	}
}
