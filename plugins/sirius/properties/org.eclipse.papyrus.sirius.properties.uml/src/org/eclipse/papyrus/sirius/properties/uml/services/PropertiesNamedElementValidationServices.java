/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;

/**
 * This service class includes services used for validation of NamedElements.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesNamedElementValidationServices {

	/**
	 * Checks if an element has a valid name. It is valid if its Namespace has no other members with the same name.
	 * 
	 * @param target
	 *            the semantic element to check
	 * @return <code>true</code> if the name of the element is valid;<code>false</code> otherwise.
	 */
	public boolean validateNamedElement(EObject target) {
		boolean result = true;
		if (target instanceof NamedElement) {
			NamedElement self = (NamedElement) target;
			Namespace ns = self.getNamespace();
			if (ns != null) {
				for (NamedElement namedElement : ns.getMembers()) {
					if ((self != namedElement) && !self.isDistinguishableFrom(namedElement, ns)) {
						result = false;
						// Name is indistinguishable from another element in the Namespace.
					}
				}
			}
		}
		return result;
	}
}
