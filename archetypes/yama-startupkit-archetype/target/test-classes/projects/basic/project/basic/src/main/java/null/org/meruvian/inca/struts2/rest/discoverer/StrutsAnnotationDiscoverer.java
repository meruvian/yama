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
package org.meruvian.inca.struts2.rest.discoverer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;

import org.meruvian.inca.struts2.rest.commons.ClasspathUtil;

import com.opensymphony.xwork2.util.finder.Test;
import com.opensymphony.xwork2.util.finder.UrlSet;

/**
 * @author Dian Aditya
 * 
 */
public class StrutsAnnotationDiscoverer implements AnnotationDiscoverer {

	private Test<String> filter;

	public StrutsAnnotationDiscoverer(Test<String> filter) {
		this.filter = filter;
	}

	public List<ClassFile> discoverClassesForAnnotation(
			Class<? extends java.lang.annotation.Annotation> annotation)
			throws Exception {
		List<ClassFile> annotatedClasses = new ArrayList<ClassFile>();

		for (InputStream stream : getClassFiles(filter)) {
			DataInputStream inputStream = new DataInputStream(
					new BufferedInputStream(stream));
			try {
				ClassFile classFile = new ClassFile(inputStream);

				Set<Annotation> annotations = new HashSet<Annotation>();

				AnnotationsAttribute visibleAnnotataion = (AnnotationsAttribute) classFile
						.getAttribute(AnnotationsAttribute.visibleTag);
				AnnotationsAttribute invisibleAnnotataion = (AnnotationsAttribute) classFile
						.getAttribute(AnnotationsAttribute.invisibleTag);

				if (visibleAnnotataion != null) {
					annotations.addAll(Arrays.asList(visibleAnnotataion
							.getAnnotations()));
				}

				if (invisibleAnnotataion != null) {
					annotations.addAll(Arrays.asList(invisibleAnnotataion
							.getAnnotations()));
				}

				for (Annotation a : annotations) {
					if (annotation.getName().equals(a.getTypeName())) {
						annotatedClasses.add(classFile);
					}
				}
			} finally {
				inputStream.close();
				stream.close();
			}
		}

		return annotatedClasses;
	}

	public List<String> discoverAnnotatedMethods(ClassFile cf,
			Class<? extends java.lang.annotation.Annotation> annotation)
			throws Exception {
		List<String> annotatedMethods = new ArrayList<String>();

		for (MethodInfo info : (List<MethodInfo>) cf.getMethods()) {
			Set<Annotation> annotations = new HashSet<Annotation>();

			AnnotationsAttribute visibleAnnotataion = (AnnotationsAttribute) info
					.getAttribute(AnnotationsAttribute.visibleTag);
			AnnotationsAttribute invisibleAnnotataion = (AnnotationsAttribute) info
					.getAttribute(AnnotationsAttribute.invisibleTag);

			if (visibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(visibleAnnotataion
						.getAnnotations()));
			}

			if (invisibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(invisibleAnnotataion
						.getAnnotations()));
			}

			for (Annotation a : annotations) {
				if (annotation.getName().equals(a.getTypeName())) {
					annotatedMethods.add(info.getName());
				}
			}
		}

		return annotatedMethods;
	}

	public List<String> discoverAnnotatedFields(ClassFile cf,
			Class<? extends java.lang.annotation.Annotation> annotation)
			throws Exception {
		List<String> annotatedFields = new ArrayList<String>();

		for (FieldInfo info : (List<FieldInfo>) cf.getFields()) {
			Set<Annotation> annotations = new HashSet<Annotation>();

			AnnotationsAttribute visibleAnnotataion = (AnnotationsAttribute) info
					.getAttribute(AnnotationsAttribute.visibleTag);
			AnnotationsAttribute invisibleAnnotataion = (AnnotationsAttribute) info
					.getAttribute(AnnotationsAttribute.invisibleTag);

			if (visibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(visibleAnnotataion
						.getAnnotations()));
			}

			if (invisibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(invisibleAnnotataion
						.getAnnotations()));
			}

			for (Annotation a : annotations) {
				if (annotation.getName().equals(a.getTypeName())) {
					annotatedFields.add(info.getName());
				}
			}
		}

		return annotatedFields;
	}

	protected List<InputStream> getClassFiles(final Test<String> test)
			throws Exception {
		List<InputStream> classStream = new ArrayList<InputStream>();

		UrlSet urlSet = ClasspathUtil.getUrlSet();

		for (URL url : urlSet.getUrls()) {
			String urlString = url.toString();
			if (urlString.endsWith("!/")) {
				urlString = urlString.substring(4);
				urlString = urlString.substring(0, urlString.length() - 2);
				url = new URL(urlString);
			}

			if (!urlString.endsWith("/")) {
				JarFile file = new JarFile(new File(url.getPath()));
				Enumeration<JarEntry> enumeration = file.entries();
				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = enumeration.nextElement();
					if (test.test(jarEntry.getName())) {
						classStream.add(file.getInputStream(jarEntry));
					}
				}
			} else {
				File file = new File(url.getPath());
				if (file.isDirectory()) {
					for (File f : getFilesFromDirectory(file, test)) {
						classStream.add(new FileInputStream(f));
					}
				}
			}
		}

		return classStream;
	}

	protected List<File> getFilesFromDirectory(File dir, final Test<String> test) {
		List<File> files = new ArrayList<File>();

		if (dir.isDirectory()) {
			for (File f : dir.listFiles()) {
				if (!f.isDirectory() && test.test(f.getPath())) {
					files.add(f);
				}
				if (f.isDirectory()) {
					files.addAll(getFilesFromDirectory(f, test));
				}
			}
		}

		return files;
	}
}