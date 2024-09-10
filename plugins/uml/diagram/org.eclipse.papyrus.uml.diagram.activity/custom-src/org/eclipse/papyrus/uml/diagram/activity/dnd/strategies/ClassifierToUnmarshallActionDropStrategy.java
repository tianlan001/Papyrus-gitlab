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
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.UnmarshallActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UnmarshallAction;

/**
 * Drop strategy to create an UnmarshallAction from a Classifier drop and to update
 * the "unmarshallType" reference.
 *
 * @since 3.5.0
 */
public class ClassifierToUnmarshallActionDropStrategy extends AbstractActivityNodeStrategy {


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getLabel()
	 *
	 * @return
	 */
	@Override
	public String getLabel() {
		return Messages.ClassifierToUnmarshallActionDropStrategy_Label;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getDescription()
	 *
	 * @return
	 */
	@Override
	public String getDescription() {
		return Messages.ClassifierToUnmarshallActionDropStrategy_Description;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getID()
	 *
	 * @return
	 */
	@Override
	public String getID() {
		return UMLDiagramEditorPlugin.ID + "strategy.ClassifierToUnmarshallAction"; //$NON-NLS-1$
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
				UnmarshallAction.class,
				activity,
				droppedNode,
				false,
				UMLElementTypes.UNMARSHALL_ACTION,
				UMLPackage.eINSTANCE.getUnmarshallAction_UnmarshallType(),
				location,
				UnmarshallActionEditPart.VISUAL_ID);
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
