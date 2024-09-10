/*******************************************************************************
 * Copyright (c) 2016, 2018 CEA LIST, Christian W. Damus, and others..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *     Christian W. Damus - Initial API and implementation
 *     Asma Smaoui (CEA LIST) asma.smaoui@cea.fr - Bug 541746
 *
 *******************************************************************************/



package org.eclipse.papyrus.uml.service.types.internal.ui.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.uml.service.types.helper.ExtensionEditHelper;
import org.eclipse.papyrus.uml.service.types.ui.util.ExtensionHelper;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.ExtensionEnd;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

public class ExtensionEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @deprecated Use the {@link ExtensionEditHelper#canCreate(Object, Object)} API, instead.
	 */
	@Deprecated
	public static boolean canCreate(Object source, Object target) {
		return ExtensionEditHelper.canCreate(source, target);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		final Extension newExtension = (Extension) request.getElementToConfigure();
		Object sourceParam = request.getParameter(CreateRelationshipRequest.SOURCE);
		Object targetParam = request.getParameter(CreateRelationshipRequest.TARGET);
		if (!canCreate(sourceParam, targetParam)) {
			return UnexecutableCommand.INSTANCE;
		}
		final Stereotype source = (Stereotype) sourceParam;
		final Class target = (Class) targetParam;
		final Package container = deduceContainer(source);
		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				ExtensionEnd endSource = UMLFactory.eINSTANCE.createExtensionEnd();
				endSource.setName(ExtensionHelper.EXTENSION.replaceFirst("E", "e") + source.getName()); //$NON-NLS-1$ //$NON-NLS-2$
				endSource.setType(source);
				endSource.setAggregation(AggregationKind.COMPOSITE_LITERAL);
				newExtension.getOwnedEnds().add(endSource);
				Property property = UMLFactory.eINSTANCE.createProperty();
				property.setName(ExtensionHelper.BASE + target.getName());
				property.setType(target);
				property.setAssociation(newExtension);
				property.setAggregation(AggregationKind.NONE_LITERAL);
				property.setLower(0);
				newExtension.getMemberEnds().add(property);
				source.getOwnedAttributes().add(property);
				container.getPackagedElements().add(newExtension);
				newExtension.setName(ExtensionHelper.getExtensionName(container, source, target));
				return CommandResult.newOKCommandResult(newExtension);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeReorientRelationshipCommand(final ReorientRelationshipRequest request) {
		final EObject elementToEdit = request.getRelationship();
		if (elementToEdit == null || false == elementToEdit instanceof Extension) {
			return UnexecutableCommand.INSTANCE;
		}
		final Extension extension = (Extension) elementToEdit;
		int reorientDirection = request.getDirection();
		final EObject newEnd = request.getNewRelationshipEnd();
		if (!canReorientRelationship(extension, newEnd, reorientDirection)) {
			return UnexecutableCommand.INSTANCE;
		}
		if (reorientDirection == ReorientRequest.REORIENT_SOURCE) {
			return new EditElementCommand("Reorient extension source", extension, request) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					return ExtensionHelper.reconnectSource(extension, (Stereotype) newEnd);
				}
			};
		}
		if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
			return new EditElementCommand("Reorient extension target", extension, request) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					return ExtensionHelper.reconnectTarget(extension, (Stereotype) newEnd);
				}
			};
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		EObject toDestroy = request.getElementToDestroy();
		if (toDestroy == null || false == toDestroy instanceof Extension) {
			return null;
		}
		Extension extension = (Extension) toDestroy;
		if (extension.getOwnedEnds().isEmpty()) {
			return null;
		}
		ExtensionEnd extEnd = (ExtensionEnd) extension.getOwnedEnds().get(0);
		if (extEnd == null) {
			return null;
		}
		Type type = extEnd.getType();
		if (false == type instanceof Stereotype) {
			return null;
		}
		Stereotype ste = (Stereotype) type;
		EList<Property> propertyList = ste.getOwnedAttributes();
		for (int iterProperty = 0; iterProperty < propertyList.size(); iterProperty++) {
			Association propAssoc = propertyList.get(iterProperty).getAssociation();
			if (extension == propAssoc) {
				return request.getDestroyDependentCommand(propertyList.get(iterProperty));
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	private boolean canReorientRelationship(Extension extension, EObject newEnd, int reorientDirection) {
		if (newEnd == null) {
			return false;
		}
		if (reorientDirection == ReorientRequest.REORIENT_SOURCE) {
			return canCreate(newEnd, getExtensionTarget(extension));
		}
		if (reorientDirection == ReorientRequest.REORIENT_TARGET) {
			return canReorientTarget(extension, newEnd);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	private boolean canReorientTarget(Extension extension, EObject newEnd) {
		if ((newEnd instanceof Class) || (newEnd instanceof Stereotype)) {
			return false;
		}
		return canCreate(getExtensionSource(extension), newEnd);
	}

	/**
	 * {@inheritDoc}
	 */
	private Type getExtensionSource(Extension extension) {
		return extension.getEndTypes().get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	private Type getExtensionTarget(Extension extension) {
		return extension.getEndTypes().get(1);
	}

	/**
	 * {@inheritDoc}
	 */
	private Package deduceContainer(EObject source) {
		for (EObject element = source; element != null; element = element.eContainer()) {
			if (element instanceof Package) {
				return (Package) element;
			}
		}
		return null;
	}
}
