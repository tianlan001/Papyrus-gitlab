/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi SCHNEKENBURGER (CEA LIST) Remi.schnekenburger@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils;

import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.ExtensionPoint;

/**
 * Utility class for {@link ExtensionPoint}
 */
// @unused
public class ExtensionPointUtil {

	/**
	 * Returns the explanation for the extension point element
	 *
	 * @return the string defining explanation for the extension point element
	 */
	// @unused
	public static String getExplanation(ExtensionPoint extensionPoint) {
		String explanation = "";
		final String name = ((extensionPoint.getName() != null) ? UMLLabelInternationalization.getInstance().getLabel(extensionPoint) : "");
		int startIndexOfExplanation = name.lastIndexOf(":");
		if ((startIndexOfExplanation > 0) && (startIndexOfExplanation != name.length())) {
			explanation = name.substring(startIndexOfExplanation + 1).trim();
		}
		return explanation;
	}
}
