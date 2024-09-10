/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.service.types.internal.ui.commands;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.papyrus.uml.service.types.helper.InstanceSpecificationEditHelper;
import org.eclipse.papyrus.uml.service.types.internal.ui.Activator;
import org.eclipse.papyrus.uml.service.types.internal.ui.advice.InstanceSpecificationLinkEditHelperAdvice;
import org.eclipse.papyrus.uml.service.types.internal.ui.dialogs.AssociationSelectionDialog;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

public class InstanceSpecificationLinkCreateCommand extends ConfigureElementCommand {

	protected InstanceSpecification source = null;

	protected InstanceSpecification target = null;

	protected HashSet<Association> commonAssociations;
	protected boolean useUI = true;

	/**
	 * @deprecated Use the {@link InstanceSpecificationEditHelper#INSTANCE_END} constant, instead.
	 */
	@Deprecated
	public static final String INSTANCE_END = InstanceSpecificationEditHelper.INSTANCE_END;

	public InstanceSpecificationLinkCreateCommand(ConfigureRequest request) {
		super(request);
		if (request.getParameter(CreateRelationshipRequest.SOURCE) instanceof InstanceSpecification) {
			source = (InstanceSpecification) request.getParameter(CreateRelationshipRequest.SOURCE);
		}
		if (request.getParameter(CreateRelationshipRequest.TARGET) instanceof InstanceSpecification) {
			target = (InstanceSpecification) request.getParameter(CreateRelationshipRequest.TARGET);
		}
		useUI = ElementTypeUtils.useGUI(request);
	}

	/**
	 * add an end in the instancespecification link by adding a eannotation if not exist
	 *
	 * @param instanceLink
	 * @param end
	 *            to add
	 */
	protected void addEnd(InstanceSpecification instanceLink, InstanceSpecification end) {
		EAnnotation endtypes = instanceLink.getEAnnotation(INSTANCE_END);
		if (endtypes == null) {
			endtypes = instanceLink.createEAnnotation(INSTANCE_END);
		}
		endtypes.getReferences().add(end);
	}

	/**
	 * remove an end in the instance specification by adding a eannotation if not exist
	 *
	 * @param instanceLink
	 * @param end
	 *            to add
	 */
	protected void removeEnd(InstanceSpecification instanceLink, InstanceSpecification end) {
		EAnnotation endtypes = instanceLink.getEAnnotation(INSTANCE_END);
		if (endtypes == null) {
			endtypes = instanceLink.createEAnnotation(INSTANCE_END);
		}
		endtypes.getReferences().remove(end);
	}

	@Override
	public boolean canExecute() {
		return InstanceSpecificationLinkEditHelperAdvice.canCreate(source, target);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (!useUI) {
			return CommandResult.newCancelledCommandResult();
		}
		ServicesRegistry registry;
		try {
			registry = ServiceUtilsForResource.getInstance().getServiceRegistry(source.eResource());
		} catch (ServiceException e) {
			Activator.log.error(e);
			return CommandResult.newCancelledCommandResult();
		}
		Association selectedAssociation = null;
		if (InstanceSpecificationLinkEditHelperAdvice.shouldSuppressDialog(getRequest())) {
			selectedAssociation = InstanceSpecificationLinkEditHelperAdvice.getSuppressedDialogResult(getRequest());
		} else {
			AssociationSelectionDialog associationSelectionDialog = new AssociationSelectionDialog(new Shell(), SWT.NATIVE, InstanceSpecificationLinkEditHelperAdvice.getModelAssociations(source, target), registry);
			associationSelectionDialog.open();
			selectedAssociation = associationSelectionDialog.getSelectedAssociation();
			if (selectedAssociation == null && associationSelectionDialog.isCanceled()) {
				return CommandResult.newCancelledCommandResult();
			}
		}
		/*
		 * Creation of the instance specification
		 * with a name a container, and set the source and target
		 */
		InstanceSpecification instanceSpecification = (InstanceSpecification) getElementToEdit();
		Set<Classifier> sourceSpecificationClassifiersSet = InstanceSpecificationLinkEditHelperAdvice.getSpecificationClassifier(source);
		Set<Classifier> targetSpecificationClassifiersSet = InstanceSpecificationLinkEditHelperAdvice.getSpecificationClassifier(target);
		boolean revertEnds = false;
		if (selectedAssociation != null) {
			instanceSpecification.getClassifiers().add(selectedAssociation);
			Type sourceType = selectedAssociation.getMemberEnds().get(0).getType();
			revertEnds = false == sourceSpecificationClassifiersSet.contains(sourceType);
		}
		if (revertEnds) {
			addEnd(instanceSpecification, target);
			addEnd(instanceSpecification, source);
		} else {
			addEnd(instanceSpecification, source);
			addEnd(instanceSpecification, target);
		}
		setupSlots(selectedAssociation, instanceSpecification, sourceSpecificationClassifiersSet, targetSpecificationClassifiersSet);
		return CommandResult.newOKCommandResult(instanceSpecification);
	}

	private void setupSlots(Association selectedAssociation, InstanceSpecification instanceSpecification, Set<Classifier> sourceSpecificationClassifiersSet, Set<Classifier> targetSpecificationClassifiersSet) {
		if (selectedAssociation == null) {
			return;
		}
		// Creation of slots into the good instance by taking in account the association
		Iterator<Property> proIterator = selectedAssociation.getMemberEnds().iterator();
		while (proIterator.hasNext()) {
			Property property = proIterator.next();
			Slot slot = UMLFactory.eINSTANCE.createSlot();
			slot.setDefiningFeature(property);
			if (sourceSpecificationClassifiersSet.contains(property.getOwner())) {
				source.getSlots().add(slot);
				associateValue((target), slot, property.getType());
			} else if (targetSpecificationClassifiersSet.contains(property.getOwner())) {
				target.getSlots().add(slot);
				associateValue((source), slot, property.getType());
			} else {
				instanceSpecification.getSlots().add(slot);
				if (sourceSpecificationClassifiersSet.contains(property.getType())) {
					associateValue((source), slot, property.getType());
				} else {
					associateValue((target), slot, property.getType());
				}
			}
		}
	}

	/**
	 * create an instanceValue for the slot (owner) with the reference to InstanceSpecification and the good type
	 *
	 * @param instanceSpecification
	 *            that is referenced by the instanceValue
	 * @param owner
	 *            of the instance value
	 * @param type
	 *            of the instanceValue
	 * @return a instanceValue
	 */
	protected InstanceValue associateValue(InstanceSpecification instanceSpecification, Slot owner, Type type) {
		InstanceValue instanceValue = UMLFactory.eINSTANCE.createInstanceValue();
		instanceValue.setName(NamedElementUtil.getDefaultNameWithIncrementFromBase(instanceValue.eClass().getName(), owner.eContents()));
		instanceValue.setType(type);
		instanceValue.setInstance(instanceSpecification);
		owner.getValues().add(instanceValue);
		return instanceValue;
	}

	@Override
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	protected Constraint getSource() {
		return (Constraint) source;
	}

	protected Namespace getTarget() {
		return (Namespace) target;
	}
}
