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

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.List;

import javassist.bytecode.ClassFile;

/**
 * Provides functionality for discovering annotation at runtime
 * 
 * @author Dian Aditya
 * 
 */
public interface AnnotationDiscoverer {
	/**
	 * 
	 * @param annotation
	 * @return The list of annotated classes
	 * @throws Exception
	 */
	List<ClassFile> discoverClassesForAnnotation(
			Class<? extends Annotation> annotation) throws Exception;

	/**
	 * 
	 * @param className
	 * @param annotation
	 * @return The list of annotated methods from specified class
	 * @throws Exception
	 */
	List<String> discoverAnnotatedMethods(ClassFile classFile,
			Class<? extends Annotation> annotation) throws Exception;

	/**
	 * 
	 * @param className
	 * @param annotation
	 * @return The list of annotated fields from specified class
	 * @throws Exception
	 */
	List<String> discoverAnnotatedFields(ClassFile classFile,
			Class<? extends Annotation> annotation) throws Exception;

}
