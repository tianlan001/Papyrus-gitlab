/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.internal.common.services;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.spi.IGraphicalDeletionHelper;

/**
 * UML-specific graphical deletion helper strategy.
 */
public class UMLGraphicalDeletionHelper implements IGraphicalDeletionHelper {

	public UMLGraphicalDeletionHelper() {
		super();
	}

	@Override
	public boolean canDelete(IGraphicalEditPart editPart) {
		boolean result = false;

		final Object model = editPart.getModel();
		if ((model instanceof View) && (((View) model).getElement() instanceof org.eclipse.uml2.uml.Class)) {
			// we want to be able to delete Metaclass (to do a hide action) with Delete from Model see bug 477084
			org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) ((View) model).getElement();
			result = clazz.isMetaclass();
		}

		return result;
	}
}
