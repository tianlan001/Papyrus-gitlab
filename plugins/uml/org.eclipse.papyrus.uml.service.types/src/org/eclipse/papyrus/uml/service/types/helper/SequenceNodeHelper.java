/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.uml2.uml.UMLPackage;

public class SequenceNodeHelper extends StructuredActivityNodeHelper {

	@Override
	protected ICommand getCreateCommand(CreateElementRequest req) {
		if (isControlNode(req.getElementType())) {
			return null;
		}
		return super.getCreateCommand(req);
	}

	protected boolean isControlNode(IElementType type) {
		return type.getEClass() != null && UMLPackage.eINSTANCE.getControlNode().isSuperTypeOf(type.getEClass());
	}
}