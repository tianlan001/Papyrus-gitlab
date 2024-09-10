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

import java.util.Iterator;
import java.util.List;

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
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.diagram.activity.edit.dialogs.CreateLinkEndDateDialog;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.CreateLinkAction;
import org.eclipse.uml2.uml.DestroyLinkAction;
import org.eclipse.uml2.uml.LinkAction;
import org.eclipse.uml2.uml.LinkEndData;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ReadLinkAction;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Extends the generic class to create and update an AcceptCallAction,
 * create and trigger and a callEvent then set the trigger
 *
 * @since 3.5
 */
public class CreateLinkActionAndUpdateCommand<T extends EObject, E extends EObject, S extends EObject> extends CreateTAndUpdateCommand<T, E, S> {

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
	public CreateLinkActionAndUpdateCommand(EditPart targetEditPart, Class<T> typeParameterClass, E targetElementDiagram, S sourceElement, boolean headless, IHintedType typeToCreate, Point location,
			String dropEditPartVisualID) {
		super(targetEditPart, typeParameterClass, targetElementDiagram, sourceElement, headless, typeToCreate, null, location, dropEditPartVisualID);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.dnd.commands.CreateTAndUpdateCommand#updateNewlyCreatedEObjectWithEObjectDragged(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 *
	 * @param elementToSet
	 * @param value
	 * @throws ExecutionException
	 */
	@Override
	protected void updateNewlyCreatedEObjectWithEObjectDragged(T elementToSet, S value) throws ExecutionException {
		if (false == value instanceof Association) {
			return;
		}
		LinkAction linkAction = (LinkAction) elementToSet;
		List<Property> selectedProperties = null;

		CreateLinkEndDateDialog dialog = new CreateLinkEndDateDialog(Display.getDefault().getActiveShell(), (Association) value);
		if (dialog.open() == Window.OK) {
			selectedProperties = dialog.getSelectedProperties();
		}
		EObject newCreatedElement = null;
		if (selectedProperties != null) {
			Iterator<Property> iterator = selectedProperties.iterator();
			while (iterator.hasNext()) {
				Property property = iterator.next();
				newCreatedElement = createElement(linkAction, getTypeToCreate(elementToSet));
				if (false == newCreatedElement instanceof LinkEndData) {
					return;
				}
				LinkEndData linkEndData = (LinkEndData) newCreatedElement;
				setElementFeature(linkEndData, UMLPackage.eINSTANCE.getLinkEndData_End(), property);
			}
		}
	}

	private IHintedType getTypeToCreate(T elementToSet) {
		if (elementToSet instanceof CreateLinkAction) {
			return UMLElementTypes.LINK_END_CREATION_DATA;
		}
		if (elementToSet instanceof DestroyLinkAction) {
			return UMLElementTypes.LINK_END_DESTRUCTION_DATA;
		}
		if (elementToSet instanceof ReadLinkAction) {
			return UMLElementTypes.LINK_END_DATA;
		}
		return UMLElementTypes.LINK_END_DATA;
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
