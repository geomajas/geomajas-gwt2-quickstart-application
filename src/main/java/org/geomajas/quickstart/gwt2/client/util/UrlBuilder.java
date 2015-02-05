/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2015 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.geomajas.quickstart.gwt2.client.util;

import com.google.gwt.http.client.URL;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds parametrized URL from a base URL.
 *
 * @author Jan De Moerloose
 */
public class UrlBuilder {

	private final Map<String, String> params = new HashMap<String, String>();

	private String baseUrl;

	/**
	 * Constructor using the given base URL.
	 *
	 * @param baseUrl base URL
	 */
	public UrlBuilder(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Add a parameter.
	 *
	 * @param name
	 *            name of parameter
	 * @param value
	 *            value of parameter
	 * @return this to allow concatenation
	 */
	public UrlBuilder addParameter(String name, String value) {
		if (value == null) {
			value = "";
		}
		params.put(name, value);
		return this;
	}

	/**
	 * Add a path extension.
	 *
	 * @param path
	 *            path
	 * @return this to allow concatenation
	 */
	public UrlBuilder addPath(String path) {
		if (path.startsWith("/") && baseUrl.endsWith("/")) {
			baseUrl = baseUrl + path.substring(1);
		} else if (baseUrl.endsWith("/")) {
			baseUrl = baseUrl + path;
		} else {
			baseUrl = baseUrl + "/" + path;
		}
		return this;
	}

	/**
	 * Build the URL and return it as an encoded string.
	 *
	 * @return the encoded URL string
	 */
	public String toString() {
		StringBuilder url = new StringBuilder(baseUrl);
		if (params.size() > 0) {
			url.append("?");
			for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
				String name = iterator.next();
				url.append(name).append("=").append(params.get(name));
				if (iterator.hasNext()) {
					url.append("&");
				}
			}
		}
		return URL.encode(url.toString());
	}

}