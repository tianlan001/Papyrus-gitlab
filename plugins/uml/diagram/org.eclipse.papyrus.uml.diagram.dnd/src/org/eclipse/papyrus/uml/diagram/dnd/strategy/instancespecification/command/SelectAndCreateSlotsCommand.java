/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.dnd.strategy.instancespecification.command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SlotEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.dnd.strategy.instancespecification.ui.SlotSelectionDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * An interactive Command to create slots in an InstanceSpecification.
 * A dialog will be opened for the user to select the properties he wishes
 * to instantiate.
 *
 * @author Camille Letavernier
 *
 */
public class SelectAndCreateSlotsCommand extends AbstractCommand {

	protected final List<Classifier> classifiers;

	protected final EditPart targetEditPart;

	protected final InstanceSpecification specification;

	protected final boolean headless;

	public SelectAndCreateSlotsCommand(List<Classifier> classifiers, EditPart targetEditPart) {
		this(classifiers, targetEditPart, false);
	}

	public SelectAndCreateSlotsCommand(List<Classifier> classifiers, EditPart targetEditPart, boolean headless) {
		super("Create slots");
		this.classifiers = classifiers;
		this.targetEditPart = targetEditPart;
		specification = (InstanceSpecification) EMFHelper.getEObject(targetEditPart);
		this.headless = headless;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		Object[] propertiesToInstantiate = getProperties();

		if (propertiesToInstantiate == null) {
			return CommandResult.newCancelledCommandResult();
		}

		// For each selected property, create the corresponding slot
		for (Object propertyObject : propertiesToInstantiate) {
			Property property = (Property) propertyObject;
			// Creates the slot
			TransactionalEditingDomain domain = (TransactionalEditingDomain) EMFHelper.resolveEditingDomain(targetEditPart);
			CreateElementRequest createElementRequest = new CreateElementRequest(domain, specification, UMLElementTypes.Slot_SlotLabel);

			ICommand slotCreationCommand;

			// If the slot compartment is visible, create the slot graphically
			if (isSlotCompartmentAvailable(targetEditPart)) {
				CreateElementRequestAdapter adapter = new CreateElementRequestAdapter(createElementRequest);
				ViewAndElementDescriptor descriptor = new ViewAndElementDescriptor(adapter, Node.class, UMLVisualIDRegistry.getType(SlotEditPart.VISUAL_ID), ((IGraphicalEditPart) targetEditPart).getDiagramPreferencesHint());
				Request createRequest = new CreateViewAndElementRequest(descriptor);
				EditPart realTarget = targetEditPart.getTargetEditPart(createRequest);

				Command gefCommand = realTarget.getCommand(createRequest);

				if (gefCommand instanceof ICommandProxy) {
					slotCreationCommand = ((ICommandProxy) gefCommand).getICommand();
					slotCreationCommand = slotCreationCommand.reduce();
				} else {
					slotCreationCommand = new CommandProxy(gefCommand);
				}
			} else { // The compartment is not visible ; only create the semantic slot
				slotCreationCommand = new CreateElementCommand(createElementRequest);
			}

			slotCreationCommand.execute(new NullProgressMonitor(), null);

			CommandResult commandResult = slotCreationCommand.getCommandResult();
			if (commandResult != null) {
				if (!commandResult.getStatus().isOK()) {
					return commandResult;
				}
			}

			// Retrieve the created slot, and update its properties
			Slot newSlot = getNewSlot(commandResult);
			if (newSlot == null) {
				newSlot = getNewSlot(createElementRequest);
			}

			if (newSlot != null) {
				updateSlotProperties(newSlot, property);
			} else {
				return CommandResult.newErrorCommandResult("Could not create the slot for property " + property.getName());//$NON-NLS-1$
			}

			// Initialize properties: feature & name & default value
		}

		return CommandResult.newOKCommandResult();
	}

	protected Object[] getProperties() {
		// Headless
		if (headless) {
			return getAllProperties();
		}

		// UI

		// Open the dialog to select the slots
		SlotSelectionDialog dialog = new SlotSelectionDialog(Display.getCurrent().getActiveShell(), specification, classifiers);
		if (dialog.open() != Window.OK) {
			return null;
		}

		return dialog.getResult();
	}

	protected Property[] getAllProperties() {
		Set<Property> allProperties = new HashSet<>();
		for (Classifier classifier : classifiers) {
			allProperties.addAll(classifier.getAllAttributes());
		}
		return allProperties.toArray(new Property[allProperties.size()]);
	}

	protected Slot getNewSlot(CreateElementRequest request) {
		if (request.getNewElement() instanceof Slot) {
			return (Slot) request.getNewElement();
		}
		return null;
	}

	// Retrieves a slot from a CommandResult
	protected Slot getNewSlot(CommandResult commandResult) {
		Object objectResult = commandResult.getReturnValue();
		if (objectResult instanceof List) {
			// Result of the semantic + graphical creation command
			List<?> listResult = (List<?>) objectResult;
			for (Object elementResult : listResult) {
				if (elementResult instanceof CreateElementRequestAdapter) {
					CreateElementRequest request = (CreateElementRequest) ((CreateElementRequestAdapter) elementResult).getAdapter(CreateElementRequest.class);
					if (request != null) {
						EObject newElement = request.getNewElement();
						if (newElement instanceof Slot) {
							Slot slot = (Slot) newElement;
							return slot;
						}
					}
				}
			}
		} else if (commandResult.getReturnValue() instanceof Slot) {
			// Result of the semantic creation command
			return (Slot) commandResult.getReturnValue();
		}

		return null;
	}

	// Sets the slot's property (definingFeature) and initialize its value (property#defaultValue)
	protected void updateSlotProperties(Slot slot, Property property) throws ExecutionException {
		SetRequest setFeatureRequest = new SetRequest(slot, UMLPackage.eINSTANCE.getSlot_DefiningFeature(), property);
		SetValueCommand setFeatureCommand = new SetValueCommand(setFeatureRequest);
		setFeatureCommand.execute(new NullProgressMonitor(), null);

		if (property.getDefaultValue() != null) {
			ValueSpecification defaultValue = property.getDefaultValue();
			ValueSpecification currentValue = EcoreUtil.copy(defaultValue);
			currentValue.setName(property.getName());

			SetRequest setValueRequest = new SetRequest(slot, UMLPackage.eINSTANCE.getSlot_Value(), currentValue);
			SetValueCommand setValueCommand = new SetValueCommand(setValueRequest);
			setValueCommand.execute(new NullProgressMonitor(), null);
		}
	}

	// Tests whether the slot compartment edit part is available
	protected boolean isSlotCompartmentAvailable(EditPart targetEditPart) {
		if (targetEditPart instanceof InstanceSpecificationSlotCompartmentEditPart || targetEditPart instanceof InstanceSpecificationSlotCompartmentEditPartCN) {
			return true;
		}
		for (Object editPartObject : targetEditPart.getChildren()) {
			if (isSlotCompartmentAvailable((EditPart) editPartObject)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		return null;
	}

}
