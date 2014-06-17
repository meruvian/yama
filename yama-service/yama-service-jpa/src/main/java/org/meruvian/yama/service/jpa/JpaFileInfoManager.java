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
package org.meruvian.yama.service.jpa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.repository.commons.FileInfo;
import org.meruvian.yama.repository.jpa.commons.JpaFileInfo;
import org.meruvian.yama.repository.jpa.commons.JpaFileInfoRepository;
import org.meruvian.yama.service.FileInfoManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 * 
 */
@Service
@Transactional(readOnly = true)
public class JpaFileInfoManager implements FileInfoManager {
	private JpaFileInfoRepository fileInfoRepository;
	private String defaultLocation;

	@Inject
	public void setFileInfoRepository(JpaFileInfoRepository fileInfoRepository) {
		this.fileInfoRepository = fileInfoRepository;
	}

	@Value("${upload.path}")
	public void setDefaultLocation(String defaultLocation) {
		defaultLocation = StringUtils.endsWith(defaultLocation, "/") ? defaultLocation : (defaultLocation + "/");
		this.defaultLocation = defaultLocation;
	}

	@Override
	public FileInfo getFileInfoById(String id) throws IOException {
		JpaFileInfo fileInfo = fileInfoRepository.findOne(id);
		fileInfo.setDataBlob(new FileInputStream(fileInfo.getPath()));
		
		return fileInfo;
	}

	@Override
	public Page<JpaFileInfo> findFileInfo(Pageable pageable) {
		return fileInfoRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public JpaFileInfo saveFileInfo(FileInfo fileInfo) throws IOException {
		JpaFileInfo info = new JpaFileInfo(fileInfo);
		info = fileInfoRepository.save(info);
		
		if (fileInfo.getDataBlob() != null) {
			File file = new File(defaultLocation);
			if (!file.exists()) file.mkdir();
			
			file = new File(file, info.getId());
			OutputStream oStream = new FileOutputStream(file);
			
			InputStream stream = fileInfo.getDataBlob();
			IOUtils.copy(stream, oStream);
			
			info.setPath(file.getAbsolutePath());
			info.setSize(FileUtils.sizeOf(file));
			
			IOUtils.closeQuietly(stream);
			IOUtils.closeQuietly(oStream);
		}
		
		return info;
	}
	
	@Override
	@Transactional
	public void deleteFileInfo(String id) {
		fileInfoRepository.delete(id);
	}
}
