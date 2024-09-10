/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 546413
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.dnd.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityContentCompartmentEditPart;

/**
 * A class creating a new element of type T
 * when drop an element of type S in an element of type E.
 * This class also update the newly created element to set
 * one of its feature with the dropped element.
 *
 * @since 3.5.0
 */
public class CreateTAndUpdateCommand<T extends EObject, E extends EObject, S extends EObject> extends AbstractCommand {

	protected final EditPart targetEditPart;
	protected final boolean headless;
	protected final Class<T> typeParameterClass;
	protected final E targetElement;
	protected final S sourceElement;
	private final IHintedType typeToCreate;
	private final Point location;
	private String dropEditPartVisualID;
	private EStructuralFeature structuralFeatureToSet;

	public CreateTAndUpdateCommand(EditPart targetEditPart, Class<T> typeParameterClass, E targetElement, S sourceElement, boolean headless, IHintedType typeToCreate, EStructuralFeature structuralFeatureToSet, Point location,
			String dropEditPartVisualID) {
		super("Create and update in drop action"); //$NON-NLS-1$

		this.targetEditPart = targetEditPart;
		this.headless = headless;
		this.typeParameterClass = typeParameterClass;

		this.targetElement = targetElement;
		this.sourceElement = sourceElement;
		this.typeToCreate = typeToCreate;
		this.location = location;
		this.dropEditPartVisualID = dropEditPartVisualID;
		this.structuralFeatureToSet = structuralFeatureToSet;
	}

	@SuppressWarnings("unchecked")
	protected T getNewEObject(CreateElementRequest request) {
		if (typeParameterClass.isAssignableFrom(request.getNewElement().getClass())) {
			return (T) request.getNewElement();
		}
		return null;
	}

	/**
	 * Retrieves the new object from a CommandResult
	 *
	 * @param commandResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getNewEObject(CommandResult commandResult) {
		Object objectResult = commandResult.getReturnValue();
		if (objectResult instanceof List) {
			// Result of the semantic + graphical creation command
			List<?> listResult = (List<?>) objectResult;
			for (Object elementResult : listResult) {
				if (elementResult instanceof CreateElementRequestAdapter) {
					CreateElementRequest request = (CreateElementRequest) ((CreateElementRequestAdapter) elementResult).getAdapter(CreateElementRequest.class);
					if (request != null) {
						EObject newElement = request.getNewElement();
						if (typeParameterClass.isAssignableFrom(newElement.getClass())) {
							T newObject = (T) newElement;
							return newObject;
						}
					}
				}
			}
		} else if (typeParameterClass.isAssignableFrom(commandResult.getReturnValue().getClass())) {
			// Result of the semantic creation command
			return (T) commandResult.getReturnValue();
		}

		return null;
	}


	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param progressMonitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		// Creates the slot
		TransactionalEditingDomain domain = (TransactionalEditingDomain) EMFHelper.resolveEditingDomain(targetEditPart);
		CreateElementRequest createElementRequest = new CreateElementRequest(domain, targetElement, typeToCreate);
		ICommand creationElementCommand = null;

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(targetElement);
		if (provider != null) {
			creationElementCommand = provider.getEditCommand(createElementRequest);
		}
		if (creationElementCommand != null) {
			creationElementCommand.execute(new NullProgressMonitor(), null);

			CommandResult commandResult = creationElementCommand.getCommandResult();
			if (commandResult != null) {
				if (!commandResult.getStatus().isOK()) {
					return commandResult;
				}
			}

			// Retrieve the created slot, and update its properties
			T newCreatedElement = getNewEObject(commandResult);
			if (newCreatedElement == null) {
				newCreatedElement = getNewEObject(createElementRequest);
			}

			if (newCreatedElement != null) {
				updateNewlyCreatedEObjectWithEObjectDragged(newCreatedElement, sourceElement);
			} else {
				return CommandResult.newErrorCommandResult("Could not create the action"); //$NON-NLS-1$
			}

			// Create the element graphically if possible
			final EditPart ownerEditPart = getOwnerEditPart();
			dropCreatedElement(ownerEditPart, newCreatedElement);
		}
		return CommandResult.newOKCommandResult();
	}

	/**
	 * @param contentCompartmentEditPart
	 * @param createdElement
	 */
	protected void dropCreatedElement(EditPart contentCompartmentEditPart, T createdElement) {
		// If the content compartment edit part of the activity is visible, add the created Action
		if (null != contentCompartmentEditPart) {
			// Create the view request, its associated command and execute it
			final ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(createdElement), Node.class, dropEditPartVisualID, ((IGraphicalEditPart) targetEditPart).getDiagramPreferencesHint());
			final CreateViewRequest createViewRequest = new CreateViewRequest(descriptor);
			createViewRequest.setLocation(location);
			final Command createViewCommand = contentCompartmentEditPart.getCommand(createViewRequest);
			if (null != createViewCommand && createViewCommand.canExecute()) {
				createViewCommand.execute();
			}
		}
	}

	/**
	 *
	 */
	protected EditPart getOwnerEditPart() {
		// Get the compartment edit part
		CompartmentEditPart compartmentEditPart = null;
		if (targetEditPart instanceof ActivityActivityContentCompartmentEditPart) {
			compartmentEditPart = (ActivityActivityContentCompartmentEditPart) targetEditPart;
		} else {
			Iterator<?> children = targetEditPart.getChildren().iterator();
			while (children.hasNext() && null == compartmentEditPart) {
				Object child = children.next();
				if (child instanceof ActivityActivityContentCompartmentEditPart) {
					compartmentEditPart = (ActivityActivityContentCompartmentEditPart) child;
				}
			}
		}
		return compartmentEditPart;
	}

	/**
	 * Sets the slot's property (definingFeature) and initialize its value (property#defaultValue)
	 *
	 * @param elementToSet
	 * @param value
	 * @throws ExecutionException
	 */
	protected void updateNewlyCreatedEObjectWithEObjectDragged(T elementToSet, S value) throws ExecutionException {
		setElementFeature(elementToSet, structuralFeatureToSet, value);
	}

	/**
	 * Sets the feature of the elementToSet to the value
	 *
	 * @param elementToSet
	 * @param structuralFeature
	 * @param value
	 * @throws ExecutionException
	 */
	protected void setElementFeature(EObject elementToSet, EStructuralFeature structuralFeature, Object value) throws ExecutionException {
		if (structuralFeature != null) {
			SetRequest setFeatureRequest = new SetRequest(elementToSet, structuralFeature, value);
			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(elementToSet);
			if (provider != null) {
				ICommand setCommand = provider.getEditCommand(setFeatureRequest);
				if (setCommand != null && setCommand.canExecute()) {
					setCommand.execute(new NullProgressMonitor(), null);
				}
			}
		}
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doRedoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param progressMonitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		throw new ExecutionException("not implemented"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doUndoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param progressMonitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		throw new ExecutionException("not implemented"); //$NON-NLS-1$
	}

}
