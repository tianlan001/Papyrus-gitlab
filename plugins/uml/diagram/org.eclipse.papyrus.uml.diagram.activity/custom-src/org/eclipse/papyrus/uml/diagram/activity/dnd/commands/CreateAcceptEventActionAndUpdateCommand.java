/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.dnd.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.diagram.activity.dnd.Messages;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Extends the generic class to create and update an AcceptEventAction,
 * create and trigger and a signalEvent then set the trigger
 *
 * @since 3.5.0
 */
public class CreateAcceptEventActionAndUpdateCommand<T extends EObject, E extends EObject, S extends EObject> extends CreateTAndUpdateCommand<T, E, S> {

	private static final String EVENT_PACKAGE_NAME = Messages.CreateAcceptEventActionAndUpdateCommand_PackageName;

	/**
	 * Constructor.
	 *
	 * @param targetEditPart
	 * @param typeParameterClass
	 * @param targetElementDiagram
	 * @param sourceElement
	 * @param headless
	 * @param typeToCreate
	 * @param location
	 * @param dropEditPartVisualID
	 */
	public CreateAcceptEventActionAndUpdateCommand(EditPart targetEditPart, Class<T> typeParameterClass, E targetElementDiagram, S sourceElement, boolean headless, IHintedType typeToCreate, Point location,
			String dropEditPartVisualID) {
		super(targetEditPart, typeParameterClass, targetElementDiagram, sourceElement, headless, typeToCreate, null, location, dropEditPartVisualID);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.dnd.commands.CreateTAndUpdateCommand#updateNewlyCreatedEObjectWithEObjectDragged(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 *
	 * @param slot
	 * @param property
	 * @throws ExecutionException
	 */
	@Override
	protected void updateNewlyCreatedEObjectWithEObjectDragged(T slot, S property) throws ExecutionException {
		AcceptEventAction action = (AcceptEventAction) slot;
		action.setIsUnmarshall(true);
		Signal signal = (Signal) sourceElement;

		// Retrieve the created slot, and update its properties
		EObject newCreatedElement = null;
		if (!action.getTriggers().isEmpty()) {
			newCreatedElement = action.getTriggers().get(0);
		} else {
			newCreatedElement = createElement(action, UMLElementTypes.TRIGGER);
		}
		if (newCreatedElement == null || false == newCreatedElement instanceof Trigger) {
			return;
		}
		final Trigger trigger = (Trigger) newCreatedElement;

		Package packageEvent = getEventPackage(action);

		newCreatedElement = createElement(packageEvent, UMLElementTypes.SIGNAL_EVENT);
		if (newCreatedElement == null || false == newCreatedElement instanceof SignalEvent) {
			return;
		}
		final SignalEvent signalEvent = (SignalEvent) newCreatedElement;

		setElementFeature(signalEvent, UMLPackage.eINSTANCE.getSignalEvent_Signal(), signal);
		setElementFeature(trigger, UMLPackage.eINSTANCE.getTrigger_Event(), signalEvent);
	}

	private Package getEventPackage(Element context) throws ExecutionException {
		Package ret = null;
		Model root = context.getModel();

		ret = root.getNestedPackage(EVENT_PACKAGE_NAME);
		if (ret == null) {
			EObject newElement = createElement(root, UMLElementTypes.PACKAGE);
			if (newElement == null || false == newElement instanceof Package) {
				return null;
			}
			ret = (Package) newElement;
			ret.setName(EVENT_PACKAGE_NAME);
		}
		return ret;
	}

	private EObject createElement(EObject owner, IElementType type) throws ExecutionException {
		TransactionalEditingDomain domain = (TransactionalEditingDomain) EMFHelper.resolveEditingDomain(targetEditPart);
		CreateElementRequest createElementRequest = new CreateElementRequest(domain, owner, type);
		ICommand creationElementCommand;

		creationElementCommand = new CreateElementCommand(createElementRequest);

		if (creationElementCommand.canExecute()) {
			creationElementCommand.execute(new NullProgressMonitor(), null);
		}

		CommandResult commandResult = creationElementCommand.getCommandResult();
		if (commandResult != null) {
			if (!commandResult.getStatus().isOK()) {
				return null;
			}
		}

		// Retrieve the created slot, and update its properties
		return commandResult.getReturnValue() instanceof EObject ? (EObject) commandResult.getReturnValue() : null;
	}

}
