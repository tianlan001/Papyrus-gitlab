/******************************************************************************
 * Copyright (c) 2006, 2020, 2021 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitri Stadnik (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 - Use project or worksapce preference as new line characters
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * @author dstadnik
 */
public class TextMerger {
	
	private final String  lineDelimiter;
	
	public TextMerger(String lineDelimiter) {
		super();
		this.lineDelimiter = lineDelimiter;
	}

	public String mergeJava(String oldText, String newText) {
		return toLocalLineSeparator(newText);
	}

	/**
	 * Convert line delimiters to local line separator.
	 * Bug 569174 - Allow Use project or worksapce preference as new line characters

	 *
	 * @param newText text with wrong line delimiters
	 * @return the cleaned text
	 */
	protected final String toLocalLineSeparator(String newText) {
		//  Bug 569174 - Use project or worksapce preference as new line characters
		// - here it is at merge - if already exist (!= creation time)
		// -- --  use post processing instead of intrusive changes in APIs
		final StringConcatenation lineConcatener = new StringConcatenation(lineDelimiter);
		 lineConcatener.append(newText);
		 return lineConcatener.toString();
	}

	public String mergeProperties(String oldText, String newText) {
		return toLocalLineSeparator(newText);
	}

	public String mergeXML(String oldText, String newText) {
		return toLocalLineSeparator(newText);
	}

	public String mergePluginXML(String oldText, String newText) {
		return toLocalLineSeparator(newText);
	}

	public String mergeManifestMF(String oldText, String newText) {
		return toLocalLineSeparator(newText);
	}

	public String process(String fileName, String oldText, String newText) {
		if (fileName == null) {
			return toLocalLineSeparator(newText);
		}
		if (fileName.endsWith(".java")) {
			return mergeJava(oldText, newText);
		} else if (fileName.endsWith(".xml")) {
			if (fileName.equals("plugin.xml")) {
				return mergePluginXML(oldText, newText);
			}
			return mergeXML(oldText, newText);
		} else if (fileName.endsWith(".properties")) {
			return mergeProperties(oldText, newText);
		} else if (fileName.equals("MANIFEST.MF")) {
			return mergeManifestMF(oldText, newText);
		}
		return toLocalLineSeparator(newText);
	}
}
