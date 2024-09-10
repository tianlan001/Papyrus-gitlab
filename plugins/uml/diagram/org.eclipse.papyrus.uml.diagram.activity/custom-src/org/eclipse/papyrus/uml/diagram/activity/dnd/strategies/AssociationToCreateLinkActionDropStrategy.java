/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.dnd.strategies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.uml.diagram.activity.dnd.Messages;
import org.eclipse.papyrus.uml.diagram.activity.dnd.commands.CreateLinkActionAndUpdateCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CreateLinkActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.CreateLinkAction;

/**
 * Drop strategy to create a CreateLinkAction from an Association drop and to update
 * the "endData" reference.
 *
 * @since 3.5.0
 */
public class AssociationToCreateLinkActionDropStrategy extends AbstractActivityNodeStrategy {


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getLabel()
	 *
	 * @return
	 */
	@Override
	public String getLabel() {
		return Messages.AssociationToCreateLinkActionDropStrategy_Label;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getDescription()
	 *
	 * @return
	 */
	@Override
	public String getDescription() {
		return Messages.AssociationToCreateLinkActionDropStrategy_Description;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getID()
	 *
	 * @return
	 */
	@Override
	public String getID() {
		return UMLDiagramEditorPlugin.ID + "strategy.AssociationToCreateLinkAction"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.dnd.strategies.AbstractActivityNodeStrategy#getCreateAndUpdateCommand(org.eclipse.gef.EditPart, org.eclipse.uml2.uml.Activity, org.eclipse.emf.ecore.EObject, org.eclipse.draw2d.geometry.Point)
	 *
	 * @param targetEditPart
	 * @param activity
	 * @param droppedNode
	 * @param location
	 * @return
	 */
	@Override
	protected ICommand getCreateAndUpdateCommand(EditPart targetEditPart, Activity activity, EObject droppedNode, Point location) {
		return new CreateLinkActionAndUpdateCommand<>(
				targetEditPart,
				CreateLinkAction.class,
				activity,
				droppedNode,
				false,
				UMLElementTypes.CREATE_LINK_ACTION,
				location,
				CreateLinkActionEditPart.VISUAL_ID);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.dnd.strategies.AbstractActivityNodeStrategy#isSourceSupportedCase(org.eclipse.emf.ecore.EObject)
	 *
	 * @param sourceElement
	 * @return
	 */
	@Override
	protected boolean isSourceSupportedCase(EObject sourceElement) {
		return sourceElement instanceof Association;
	}

}
