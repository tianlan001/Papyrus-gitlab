/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.navigation.navigableElement;

import org.eclipse.uml2.uml.Type;

/**
 * Navigates from a TypedElement to its Type declaration
 *
 * @author Camille Letavernier
 */
public class TypedNavigableElement extends GenericNavigableElement {
	/**
	 *
	 * @param type
	 *            The Type to navigate to. May be null.
	 */
	public TypedNavigableElement(Type type) {
		super(type);
	}

	public String getLabel() {
		String label = "Go to type" + getElementLabel() + "...";
		return label;
	}

	public String getDescription() {
		return "Go to the type of this typed element: " + getElementLabel();
	}
}
