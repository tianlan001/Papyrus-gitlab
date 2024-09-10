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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.StructuredActivityNode;

public class ActivityEdgeUtil {


	/**
	 * Deduce the container of the ActivityEgde
	 * The first StructuredActivityNode or the first Activity (16.14.55.6 in [UML 2.5])
	 * 
	 * @param source
	 * @param target
	 * @return the deduce container
	 */
	public static Element deduceContainer(EObject source, EObject target) {
		// Find container element for the new link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null; element = element.eContainer()) {
			if (element instanceof Activity) {
				return (Activity) element;
			} else if (element instanceof StructuredActivityNode) {
				if (((StructuredActivityNode) element).allOwnedElements().contains(target)) {
					return (StructuredActivityNode) element;
				}
			}
		}
		return null;
	}

}
