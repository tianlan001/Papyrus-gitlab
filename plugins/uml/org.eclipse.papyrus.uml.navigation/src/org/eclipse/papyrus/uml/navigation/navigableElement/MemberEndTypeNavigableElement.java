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
 * Navigable element representing the type of a member end of an association
 *
 */
public class MemberEndTypeNavigableElement extends GenericNavigableElement {

	public MemberEndTypeNavigableElement(Property property) {
		super(property.getType());
	}

	@Override
	public String getLabel() {
		return "Go to member end type" + getElementLabel() + "...";
	}

	@Override
	public String getDescription() {
		return "Go to the member end type of this association:" + getElementLabel();
	}
}
