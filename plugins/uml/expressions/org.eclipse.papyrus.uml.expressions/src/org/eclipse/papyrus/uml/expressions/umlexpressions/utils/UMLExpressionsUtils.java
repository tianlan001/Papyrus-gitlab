/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.umlexpressions.utils;

import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;

/**
 * Utils methods for UML Expressions Framework
 *
 */
public class UMLExpressionsUtils {


	/**
	 * Constructor.
	 *
	 */
	private UMLExpressionsUtils() {
		// to prevent instanciation
	}
	
	/**
	 * 
	 * @param element
	 *            a profile
	 * @return
	 * 		the uri of the top profile owning the element or an empty string
	 * 
	 *         Warning, doesn't change this method, it is also used by the UML Expressions toolsmiths generating catalog for profile
	 */
	public static final String getTopProfileURI(final Element element) {
		Element parent = element;
		Profile topProfile = null;
		if (element instanceof Profile) {
			topProfile = (Profile) element;
		}

		while (null != parent.getOwner()) {
			parent = parent.getOwner();
			if (parent instanceof Profile) {
				topProfile = (Profile) parent;
			}
		}

		return null != topProfile ? topProfile.getURI() : ""; //$NON-NLS-1$
	}
}
