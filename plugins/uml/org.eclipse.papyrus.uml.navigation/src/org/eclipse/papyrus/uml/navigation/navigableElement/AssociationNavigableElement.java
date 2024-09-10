/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Shuai Li (CEA LIST) shuai.li@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.navigation.navigableElement;

import org.eclipse.uml2.uml.Property;

/**
 * Navigable element representing an association
 *
 */
public class AssociationNavigableElement extends GenericNavigableElement {

	public AssociationNavigableElement(Property property) {
		super(property.getAssociation());
	}

	@Override
	public String getLabel() {
		return "Go to association" + getElementLabel() + "...";
	}

	@Override
	public String getDescription() {
		return "Go to the association of this property:" + getElementLabel();
	}
}
