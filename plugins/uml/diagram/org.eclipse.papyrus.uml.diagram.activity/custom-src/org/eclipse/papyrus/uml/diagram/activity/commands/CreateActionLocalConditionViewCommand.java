/*****************************************************************************
 * Copyright (c) 2009, 2018 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) - Bug 381704
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.commands;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CommonDeferredCreateConnectionViewCommand;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.ui.util.EditorUtils;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityActivityContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Constraint;

/**
 * This command creates an action's local condition, then the corresponding edit
 * part and the link between the condition and the action.
 */
public class CreateActionLocalConditionViewCommand extends Command {

	/** The compartment which graphically contains the Constraint to create */
	private ActivityActivityContentCompartmentEditPart compartment = null;

	/** The command to create the Constraint element */
	private ICommandProxy elementCreationCommand = null;

	/**
	 * The command to create corresponding views (constraint view and link view)
	 */
	private CompoundCommand viewsCreationCommand = null;

	/** The edit part of the action containing the Constraint element */
	private EditPart linkedActionEditPart;

	/** The type of local condition to create */
	private IHintedType type;

	private TransactionalEditingDomain editingDomain;

	/**
	 * Constructor a new action to create the local condition and corresponding
	 * views.
	 *
	 * @param conditionType
	 *            the type of the local condition : precondition
	 *            (Constraint_LocalPreconditionShape) or postcondition (Constraint_LocalPostconditionShape)
	 * @param graphicalParent
	 *            the parent edit part which graphically contains the condition
	 * @param containerAction
	 *            the action which owns the local condition to create
	 * @param actionPart
	 *            the part of the action owning the condition
	 * @deprecated since 3.4
	 */
	@Deprecated
	public CreateActionLocalConditionViewCommand(IHintedType conditionType, ActivityActivityContentCompartmentEditPart graphicalParent, EObject containerAction, EditPart actionPart) {
		this(null, conditionType, graphicalParent, containerAction, actionPart);
	}

	/**
	 * Constructor a new action to create the local condition and corresponding
	 * views.
	 *
	 * @param conditionType
	 *            the type of the local condition : precondition
	 *            (Constraint_LocalPreconditionShape) or postcondition (Constraint_LocalPostconditionShape)
	 * @param graphicalParent
	 *            the parent edit part which graphically contains the condition
	 * @param containerAction
	 *            the action which owns the local condition to create
	 * @param actionPart
	 *            the part of the action owning the condition
	 */
	public CreateActionLocalConditionViewCommand(TransactionalEditingDomain editingDomain, IHintedType conditionType, ActivityActivityContentCompartmentEditPart graphicalParent, EObject containerAction, EditPart actionPart) {
		elementCreationCommand = getElementCreationCommand(containerAction, conditionType, graphicalParent);
		compartment = graphicalParent;
		linkedActionEditPart = actionPart;
		type = conditionType;
		this.editingDomain = editingDomain;
	}

	/**
	 * Get the Command to create the constraint element
	 *
	 * @param containerAction
	 *            the action which owns the local condition to create
	 * @param conditionType
	 *            the type of the local condition : precondition
	 *            (Constraint_LocalPreconditionShape) or postcondition (Constraint_LocalPostconditionShape)
	 * @return the command to create model element or null
	 */
	private static ICommandProxy getElementCreationCommand(EObject containerAction, IHintedType conditionType, EditPart part) {
		CreateElementRequest createElementReq = new CreateElementRequest(containerAction, conditionType);

		IElementEditService commandService = ElementEditServiceUtils.getCommandProvider(containerAction);

		if (commandService == null) {
			return null;
		}

		ICommand semanticCommand = commandService.getEditCommand(createElementReq);

		if ((semanticCommand != null) && (semanticCommand.canExecute())) {
			return new ICommandProxy(semanticCommand);
		}
		return null;
	}

	/**
	 * Get the corresponding local condition link type
	 *
	 * @return link type or null
	 */
	private IHintedType getLinkType() {
		if (UMLElementTypes.Constraint_LocalPreconditionShape.equals(type)) {
			return (IHintedType) UMLElementTypes.Action_LocalPreconditionEdge;
		} else if (UMLElementTypes.Constraint_LocalPostconditionShape.equals(type)) {
			return (IHintedType) UMLElementTypes.Action_LocalPostconditionEdge;
		} else if (UMLElementTypes.IntervalConstraint_LocalPreconditionShape.equals(type)) {
			return (IHintedType) UMLElementTypes.Action_LocalPreconditionEdge;
		} else if (UMLElementTypes.IntervalConstraint_LocalPostconditionShape.equals(type)) {
			return (IHintedType) UMLElementTypes.Action_LocalPostconditionEdge;
		} else if (UMLElementTypes.DurationConstraint_LocalPreconditionShape.equals(type)) {
			return (IHintedType) UMLElementTypes.Action_LocalPreconditionEdge;
		} else if (UMLElementTypes.DurationConstraint_LocalPostconditionShape.equals(type)) {
			return (IHintedType) UMLElementTypes.Action_LocalPostconditionEdge;
		} else if (UMLElementTypes.TimeConstraint_LocalPreconditionShape.equals(type)) {
			return (IHintedType) UMLElementTypes.Action_LocalPreconditionEdge;
		} else if (UMLElementTypes.TimeConstraint_LocalPostconditionShape.equals(type)) {
			return (IHintedType) UMLElementTypes.Action_LocalPostconditionEdge;
		} else {
			return null;
		}
	}

	@Override
	public boolean canExecute() {
		return elementCreationCommand != null && elementCreationCommand.canExecute();
	}

	/**
	 * Execute the command : create the model element, then the corresponding
	 * views
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		Object constraint = null;
		if (elementCreationCommand != null) {
			elementCreationCommand.execute();
			constraint = elementCreationCommand.getICommand().getCommandResult().getReturnValue();
		}
		if (constraint instanceof Constraint && compartment != null && type != null) {
			// construct the complete command for views creation and execute it.
			viewsCreationCommand = new CompoundCommand();
			// creation of the node by the compartment
			ViewDescriptor descriptor = new CreateViewRequest.ViewDescriptor(new EObjectAdapter((EObject) constraint), Node.class, type.getSemanticHint(), compartment.getDiagramPreferencesHint());
			CreateViewRequest request = new CreateViewRequest(descriptor);
			Command nodeCreationCommand = compartment.getCommand(request);
			viewsCreationCommand.add(nodeCreationCommand);
			// try and recover the created edit part, then create the link
			if (linkedActionEditPart != null && getLinkType() != null) {

				IAdaptable targetAdapter = extractResult(nodeCreationCommand);
				if (targetAdapter != null) {
					IAdaptable sourceAdapter = new SemanticAdapter(null, linkedActionEditPart.getModel());
					// descriptor of the link
					CreateConnectionViewRequest.ConnectionViewDescriptor linkdescriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(getLinkType(), getLinkType().getSemanticHint(), compartment.getDiagramPreferencesHint());

					// TODO: To remove when the deprecated constructor will be removed
					TransactionalEditingDomain usedEditingDomain = editingDomain;
					if (null == usedEditingDomain) {
						usedEditingDomain = EditorUtils.getTransactionalEditingDomain();
					}

					CommonDeferredCreateConnectionViewCommand aLinkCommand = new CommonDeferredCreateConnectionViewCommand(usedEditingDomain, getLinkType().getSemanticHint(), sourceAdapter, targetAdapter, compartment.getViewer(),
							compartment.getDiagramPreferencesHint(), linkdescriptor, null);
					aLinkCommand.setElement((EObject) constraint);
					viewsCreationCommand.add(new ICommandProxy(aLinkCommand));
				}
			}
			viewsCreationCommand.execute();
		}
	}

	/**
	 * Extract the result out of a node creation command
	 *
	 * @param nodeCreationCommand
	 *            the command
	 * @return the adaptable result of the command or null
	 */
	private IAdaptable extractResult(Command nodeCreationCommand) {
		if (nodeCreationCommand instanceof ICommandProxy) {
			ICommand createConstraintNodeCommand = ((ICommandProxy) nodeCreationCommand).getICommand();
			// creation of the link between edit parts
			IAdaptable targetAdapter = (IAdaptable) createConstraintNodeCommand.getCommandResult().getReturnValue();
			return targetAdapter;
		} else if (nodeCreationCommand instanceof CompoundCommand) {
			Object[] childrenCmd = ((CompoundCommand) nodeCreationCommand).getChildren();
			for (Object command : childrenCmd) {
				if (command instanceof Command) {
					IAdaptable result = extractResult((Command) command);
					if (result != null) {
						return result;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Undo model and views creation
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (viewsCreationCommand != null) {
			viewsCreationCommand.undo();
		}
		if (elementCreationCommand != null) {
			elementCreationCommand.undo();
		}
	}
}
