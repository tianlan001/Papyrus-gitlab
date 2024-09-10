/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.command;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.uml.diagram.clazz.custom.helper.MultiAssociationHelper;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationNodeEditPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This is the command in charge to delete the association and associated properties.
 */
public class DisplayAllAssociationBranchCommand implements IObjectActionDelegate {

	/** The selected element. */
	private AssociationNodeEditPart selectedElement;

	/**
	 * constructor of this command.
	 */
	public DisplayAllAssociationBranchCommand() {
		super();
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void run(IAction action) {
		MultiAssociationHelper multiAssociationHelper = new MultiAssociationHelper(selectedElement.getEditingDomain());
		selectedElement.getDiagramEditDomain().getDiagramCommandStack().execute(multiAssociationHelper.displayAllBranchesCommand(selectedElement));
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object selectedobject = ((IStructuredSelection) selection).getFirstElement();
			if (selectedobject instanceof AssociationNodeEditPart) {
				selectedElement = (AssociationNodeEditPart) selectedobject;
			}
		}
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
