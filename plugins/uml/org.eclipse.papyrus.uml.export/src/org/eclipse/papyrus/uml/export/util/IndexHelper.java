/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.util;

import java.util.ArrayList;
import java.util.List;


/**
 * Using a template library would probably be better but for the moment I try to
 * avoid fancy dependencies in Papyrus.
 */
@SuppressWarnings("nls")
public class IndexHelper {

	/** The Constant EOL. */
	private static final String EOL = System.getProperty("line.separator");

	/** The headers. */
	private List<String> headers = new ArrayList<>();

	/** The footers. */
	private List<String> footers = new ArrayList<>();

	/**
	 * Instantiates a new index helper.
	 *
	 * @param headers the headers
	 * @param footers the footers
	 */
	public IndexHelper(List<String> headers, List<String> footers) {
		this.headers = headers;
		this.footers = footers;

	}

	/**
	 * Gets the index.
	 *
	 * @param jsonData the json data
	 * @return the index
	 */
	public String getIndex(String jsonData) {
		StringBuilder stb = new StringBuilder();
		stb.append(getHeader());
		stb.append(getBody());
		stb.append(getFooter(jsonData));
		return stb.toString();
	}

	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public String getHeader() {
		StringBuilder stb = new StringBuilder();
		stb.append("<!DOCTYPE html>" + EOL);
		stb.append("<html>" + EOL);
		for (String header : headers) {
			stb.append(header + EOL);
		}
		return stb.toString();
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody() {
		StringBuilder stb = new StringBuilder();
		stb.append("<body>" + EOL);
		stb.append("<div id=\"html-editor\" class=\"html-editor api\">" + EOL);
		stb.append("<div id=\"diagram-explorer-div\" class=\"diagram-explorer-div split split-horizontal\">" + EOL);
		stb.append("<p id=\"diagram-explorer\">My diagram-explorer tree.</p>" + EOL);
		stb.append("</div>" + EOL);
		stb.append("<div id=\"diagram-div\" class=\"diagram-div split split-horizontal\" style=\"overflow: scroll;\">" + EOL);
		stb.append("<object id=\"diagram\" type=\"image/svg+xml\">" + EOL);
		stb.append("Your browser does not support SVG" + EOL);
		stb.append("</object>" + EOL);
		stb.append("</div>" + EOL);		
		stb.append("</div>" + EOL);
		stb.append("</body>" + EOL);
		return stb.toString();
	}

	/**
	 * Gets the footer.
	 *
	 * @param jsonData the json data
	 * @return the footer
	 */
	public String getFooter(String jsonData) {
		StringBuilder stb = new StringBuilder();
		stb.append("<script>" + EOL);
		stb.append("var jsondat = ");
		stb.append("'" + jsonData + "';" + EOL);
		stb.append("obj = JSON.parse(jsondat);" + EOL);
		stb.append("document.getElementById(\"diagram-explorer\").innerHTML = displayJson(obj);" + EOL);
		stb.append("</script>" + EOL);
		for (String footer : footers) {
			stb.append(footer + EOL);
		}
		stb.append("</html>" + EOL);
		return stb.toString();
	}

}
