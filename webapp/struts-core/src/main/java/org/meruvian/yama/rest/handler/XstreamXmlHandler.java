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
package org.meruvian.yama.rest.handler;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.rest.handler.ContentTypeHandler;

import com.thoughtworks.xstream.XStream;

/**
 * @author Dian Aditya
 * 
 */
public class XstreamXmlHandler implements ContentTypeHandler {

	private static final Log LOG = LogFactory.getLog(XstreamXmlHandler.class);

	protected XStream xstream;

	public XstreamXmlHandler() {
		LOG.debug("Initializing Xstream");

		xstream = new XStream();
	}

	public String fromObject(Object obj, String resultCode, Writer out)
			throws IOException {
		if (obj != null) {
			LOG.debug("Serializing " + obj.getClass());

			xstream.toXML(obj, out);
		}
		return null;
	}

	public void toObject(Reader in, Object target) {
		LOG.debug("Deserializing " + target.getClass());

		xstream.fromXML(in, target);
	}

	public String getContentType() {
		return "application/xml";
	}

	public String getExtension() {
		return "xml";
	}

}
