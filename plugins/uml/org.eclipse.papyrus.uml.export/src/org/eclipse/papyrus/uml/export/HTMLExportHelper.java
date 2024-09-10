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
package org.eclipse.papyrus.uml.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.papyrus.uml.export.extension.AdditionalInformations;
import org.eclipse.papyrus.uml.export.extension.AnnotateSVG;

/**
 * The Class HTMLExportHelper.
 */
public class HTMLExportHelper {
	
	/** The annotations. */
	private  List<AnnotateSVG> annotations = new ArrayList<>();

	/** The additionnal datas. */
	private  Map<String, AdditionalInformations> additionnalDatas = new HashMap<>();

	/** The headers. */
	private  List<String> headers = new ArrayList<>();

	/** The footers. */
	private  List<String> footers = new ArrayList<>();

	/** The from TO. */
	private  Map<String, String> fromTO = new HashMap<>();		
	
	/**
	 * Gets the annotations.
	 *
	 * @return the annotations
	 */
	public List<AnnotateSVG> getAnnotations() {
		return annotations;
	}

	/**
	 * Gets the additionnal datas.
	 *
	 * @return the additionnal datas
	 */
	public Map<String, AdditionalInformations> getAdditionnalDatas() {
		return additionnalDatas;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public List<String> getHeaders() {
		return headers;
	}

	/**
	 * Gets the footers.
	 *
	 * @return the footers
	 */
	public List<String> getFooters() {
		return footers;
	}

	/**
	 * Gets the from TO.
	 *
	 * @return the from TO
	 */
	public Map<String, String> getFromTO() {
		return fromTO;
	}

	
	/**
	 * Instantiates a new HTML export helper.
	 */
	public HTMLExportHelper() {}
	
	/**
	 * Instantiates a new HTML export helper.
	 *
	 * @param annotations the annotations
	 * @param additionnalDatas the additionnal datas
	 * @param headers the headers
	 * @param footers the footers
	 * @param fromTO the from TO
	 */
	public HTMLExportHelper(List<AnnotateSVG> annotations, Map<String, AdditionalInformations> additionnalDatas,
			List<String> headers, List<String> footers, Map<String, String> fromTO) {
		this.annotations = annotations;
		this.additionnalDatas = additionnalDatas;
		this.headers = headers;
		this.footers = footers;
		this.fromTO = fromTO;
	}

}
