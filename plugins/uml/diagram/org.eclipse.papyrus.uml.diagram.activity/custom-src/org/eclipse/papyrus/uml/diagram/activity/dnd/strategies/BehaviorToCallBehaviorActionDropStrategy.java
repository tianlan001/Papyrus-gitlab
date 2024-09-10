/*****************************************************************************
 * Copyright (c) 2015, 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 517797
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.dnd.strategies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.uml.diagram.activity.dnd.commands.CreateTAndUpdateCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.CustomMessages;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Class dedicated to the management of drag and drop of a Behavior (Functional etc..) into a Activity diagram to create automatically a callbehavor action
 *
 * @since 3.5.0
 */
public class BehaviorToCallBehaviorActionDropStrategy extends AbstractActivityNodeStrategy {

	@Override
	public String getLabel() {
		return CustomMessages.BehaviorToCallBehaviorActionDropStrategy_Label;
	}

	@Override
	public String getDescription() {
		return CustomMessages.BehaviorToCallBehaviorActionDropStrategy_Description;
	}

	@Override
	public String getID() {
		return UMLDiagramEditorPlugin.ID + ".operation.represents"; //$NON-NLS-1$
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
				CallBehaviorAction.class,
				activity,
				droppedNode,
				false,
				UMLElementTypes.CALL_BEHAVIOR_ACTION,
				UMLPackage.eINSTANCE.getCallBehaviorAction_Behavior(),
				location,
				CallBehaviorActionEditPart.VISUAL_ID);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.dnd.strategies.AbstractActivityNodeStrategy#isSourceSupportedCase(org.eclipse.emf.ecore.EObject)
	 *
	 * @param sourceElement
	 * @return
	 */
	@Override
	protected boolean isSourceSupportedCase(EObject sourceElement) {
		return sourceElement instanceof Behavior;
	}

}

