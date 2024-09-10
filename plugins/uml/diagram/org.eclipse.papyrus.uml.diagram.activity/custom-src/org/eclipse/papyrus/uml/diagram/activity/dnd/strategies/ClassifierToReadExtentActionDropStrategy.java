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
import org.eclipse.papyrus.uml.diagram.activity.dnd.commands.CreateTAndUpdateCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadExtentActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ReadExtentAction;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Drop strategy to create a ReadExtentAction from a Classifier drop and to update
 * the "classifier" reference.
 *
 * @since 3.5.0
 */
public class ClassifierToReadExtentActionDropStrategy extends AbstractActivityNodeStrategy {


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getLabel()
	 *
	 * @return
	 */
	@Override
	public String getLabel() {
		return Messages.ClassifierToReadExtentActionDropStrategy_Label;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getDescription()
	 *
	 * @return
	 */
	@Override
	public String getDescription() {
		return Messages.ClassifierToReadExtentActionDropStrategy_Description;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getID()
	 *
	 * @return
	 */
	@Override
	public String getID() {
		return UMLDiagramEditorPlugin.ID + "strategy.ClassifierToReadExtentAction"; //$NON-NLS-1$
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
		return new CreateTAndUpdateCommand<>(
				targetEditPart,
				ReadExtentAction.class,
				activity,
				droppedNode,
				false,
				UMLElementTypes.READ_EXTENT_ACTION,
				UMLPackage.eINSTANCE.getReadExtentAction_Classifier(),
				location,
				ReadExtentActionEditPart.VISUAL_ID);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.dnd.strategies.AbstractActivityNodeStrategy#isSourceSupportedCase(org.eclipse.emf.ecore.EObject)
	 *
	 * @param sourceElement
	 * @return
	 */
	@Override
	protected boolean isSourceSupportedCase(EObject sourceElement) {
		return sourceElement instanceof Classifier;
	}
}
