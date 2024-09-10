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

import org.eclipse.uml2.uml.Operation;

/**
 * Navigates from an Operation to it's return type declaration
 *
 * @author Camille Letavernier
 *
 */
public class OperationTypeNavigableElement extends TypedNavigableElement {

	public OperationTypeNavigableElement(Operation operation) {
		super(operation.getType());
	}

	@Override
	public String getLabel() {
		return "Go to return type" + getElementLabel() + "...";
	}

	@Override
	public String getDescription() {
		return "Go to the return type of this operation:" + getElementLabel();
	}
}
