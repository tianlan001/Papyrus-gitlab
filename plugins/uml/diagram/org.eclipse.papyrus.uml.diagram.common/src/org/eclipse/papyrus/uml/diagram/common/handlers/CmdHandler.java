/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.ui.PlatformUI;


/**
 * Simple superclass for handlers. It converts a selection into an EObject.
 * Used by C++ code generator and Qompass designer
 */
public abstract class CmdHandler extends AbstractHandler {

	/**
	 * Convert selected elements within model explorer or diagram to an eObject
	 */
	public void updateSelectedEObject() {
		// Retrieve selected elements
		IStructuredSelection selection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();

		if (selection != null) {
			selectedEObject = EMFHelper.getEObject(selection.getFirstElement());
		}
	}

	/**
	 * Store the selected EObject. Accessible for subclasses
	 */
	protected EObject selectedEObject;
}
