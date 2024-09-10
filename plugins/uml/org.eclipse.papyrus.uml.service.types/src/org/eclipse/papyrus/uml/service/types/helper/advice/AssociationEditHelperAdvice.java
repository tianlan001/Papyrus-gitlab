/*****************************************************************************
 * Copyright (c) 2011-2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		CEA LIST - Initial API and implementation
 *		Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 493430
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.Activator;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ClassifierUtils;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Association edit helper advice.
 */
public class AssociationEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * This method provides the source type provided as {@link ConfigureRequest} parameter.
	 *
	 * @return the target role
	 */
	protected Classifier getSourceOwnerType(ConfigureRequest req) {
		Classifier result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.SOURCE);
		if (paramObject instanceof Classifier) {
			result = (Classifier) paramObject;
		}

		return result;
	}

	/**
	 * This method provides the target type provided as {@link ConfigureRequest} parameter.
	 *
	 * @return the target role
	 */
	protected Classifier getTargetOwnerType(ConfigureRequest req) {
		Classifier result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.TARGET);
		if (paramObject instanceof Classifier) {
			result = (Classifier) paramObject;
		}

		return result;
	}

	/**
	 * Creates a new source {@link Property} from the targetType.
	 *
	 * @param targetType
	 *            the type of the {@link Property}
	 * @return the new {@link Property}
	 */
	protected Property createSourceProperty(Type targetType) {

		Property sourceProperty = UMLFactory.eINSTANCE.createProperty();
		sourceProperty.setType(targetType);
		if (targetType.getName() != null) {
			sourceProperty.setName(targetType.getName().toLowerCase());
		}

		return sourceProperty;
	}

	/**
	 * Creates a new target {@link Property} from the sourceType.
	 *
	 * @param sourceType
	 *            the type of the {@link Property}
	 * @return the new {@link Property}
	 */
	protected Property createTargetProperty(Type sourceType) {

		Property targetProperty = UMLFactory.eINSTANCE.createProperty();
		targetProperty.setType(sourceType);
		if (sourceType.getName() != null) {
			targetProperty.setName(sourceType.getName().toLowerCase());
		}

		return targetProperty;
	}

	/**
	 * This method has to be specialized by subclasses (AggregationKind).
	 *
	 * @param sourceProperty
	 *            The property to configure.
	 * @since 3.0
	 */
	protected void configureSourceProperty(Property sourceProperty) {
		// do nothing
	}

	/**
	 * This method has to be specialized by subclasses (AggregationKind).
	 *
	 * @param targetProperty
	 *            The property to configure.
	 * @since 3.0
	 */
	protected void configureTargetProperty(Property targetProperty) {
		// do nothing
	}


	/**
	 * Add the source {@link Property} in the correct container.
	 *
	 * @param sourceEnd
	 *            the semantic end
	 * @param owner
	 *            the end container
	 * @param targetType
	 *            the target type
	 * @param association
	 *            the association
	 * @throws UnsupportedOperationException
	 */
	protected void addSourceInModel(Property sourceEnd, Classifier owner, Classifier targetType, Association association) throws UnsupportedOperationException {
		boolean added = ClassifierUtils.addOwnedAttribute(owner, sourceEnd);

		if (!added) {
			association.getOwnedEnds().add(sourceEnd);
		}
		sourceEnd.setIsNavigable(false);
	}

	/**
	 * Add the source {@link Property} in the correct container.
	 *
	 * @param targetEnd
	 *            the semantic end
	 * @param owner
	 *            the end container
	 * @param sourceType
	 *            the source type
	 * @param association
	 *            the association
	 * @throws UnsupportedOperationException
	 */
	protected void addTargetInModel(Property targetEnd, Classifier owner, Classifier sourceType, Association association) {
		boolean added = ClassifierUtils.addOwnedAttribute(owner, targetEnd);

		if (!added) {
			association.getOwnedEnds().add(targetEnd);
		}
		targetEnd.setIsNavigable(false);
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * Complete the {@link Association} creation by:
	 * 		adding its {@link Property} ends in the model
	 * 		adding the UML Nature on the {@link Association}.
	 *
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {

		final Association association = (Association) request.getElementToConfigure();
		final Classifier sourceType = getSourceOwnerType(request);
		final Classifier targetType = getTargetOwnerType(request);

		if ((sourceType == null) || (targetType == null)) {
			return UnexecutableCommand.INSTANCE;
		}

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

				// Add UML Nature on the new Association
				ElementUtil.addNature(association, UMLElementTypes.UML_NATURE);

				// Create source and target ends
				Property sourceEnd = createSourceProperty(targetType);
				Property targetEnd = createTargetProperty(sourceType);
				configureSourceProperty(sourceEnd);
				configureTargetProperty(targetEnd);
				// Add association ends references
				association.getMemberEnds().add(sourceEnd);
				association.getMemberEnds().add(targetEnd);

				// do not set the name, as this should be done by the NamedElementInitializerHelperAdvice
				// association.setName("A_" + sourceEnd.getName() + "_" + targetEnd.getName()); //$NON-NLS-1$ //$NON-NLS-2$

				// Add end properties in the model
				try {
					addSourceInModel(sourceEnd, sourceType, targetType, association);
					addTargetInModel(targetEnd, targetType, sourceType, association);
				} catch (Exception e) {
					Activator.log.error(e);
					return CommandResult.newCancelledCommandResult();
				}
				targetEnd.setOwningAssociation(association);
				return CommandResult.newOKCommandResult(association);
			}
		};
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * Add a command to destroy {@link Association} ends referenced by the {@link Association}
	 * to delete.
	 *
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest req) {
		List<EObject> dependentsToDestroy = new ArrayList<EObject>();

		List<EObject> dependentsToKeep = (req.getParameter(org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants.DEPENDENTS_TO_KEEP) != null)
				? (List<EObject>) req.getParameter(org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants.DEPENDENTS_TO_KEEP)
				: new ArrayList<EObject>();

		Association association = (Association) req.getElementToDestroy();
		for (Property end : association.getMemberEnds()) {
			if (!dependentsToKeep.contains(end)) {
				dependentsToDestroy.add(end);
			}
		}

		// Return command to destroy dependents ends
		if (!dependentsToDestroy.isEmpty()) {
			return req.getDestroyDependentsCommand(dependentsToDestroy);
		}

		return super.getBeforeDestroyDependentsCommand(req);
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * Add a command to destroy {@link Association} when only 1 end remains.
	 *
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeDestroyReferenceCommand(DestroyReferenceRequest request) {
		ICommand gmfCommand = super.getBeforeDestroyReferenceCommand(request);

		Association association = (Association) request.getContainer();
		if ((request.getContainingFeature() == UMLPackage.eINSTANCE.getAssociation_MemberEnd()) && (association.getMemberEnds().contains(request.getReferencedObject()))) {
			Set<Property> ends = new HashSet<Property>();
			ends.addAll(association.getMemberEnds());
			ends.remove(request.getReferencedObject());

			if (ends.size() <= 2) {

				DestroyElementRequest destroyRequest = new DestroyElementRequest(association, false);
				IElementEditService provider = ElementEditServiceUtils.getCommandProvider(association);
				if (provider != null) {
					ICommand destroyCommand = provider.getEditCommand(destroyRequest);
					gmfCommand = CompositeCommand.compose(gmfCommand, destroyCommand);
				}

			}
		}

		return gmfCommand;
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * Add a command to related association end during re-orient.
	 *
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeReorientRelationshipCommand(ReorientRelationshipRequest request) {

		ICommand gmfCommand = super.getBeforeReorientRelationshipCommand(request);

		MoveRequest moveRequest = null;
		SetRequest setTypeRequest = null;

		// Retrieve re-oriented association and add it to the list of re-factored elements
		Association association = (Association) request.getRelationship();

		// 1 property change its parents, if it is not contains by the association
		// 1 property change its types
		Property changeContainer = null;
		Property changeType = getMemberEnd(association, (Classifier) request.getOldRelationshipEnd());
		List<EObject> currentlyRefactoredElements = (request.getParameter(org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS) != null)
				? (List<EObject>) request.getParameter(org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS)
				: new ArrayList<EObject>();

		if (currentlyRefactoredElements.contains(association)) {
			// Abort - already treated
			return null;

		} else {
			currentlyRefactoredElements.add(association);
			request.getParameters().put(org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS, currentlyRefactoredElements);
		}

		// Retrieve property ends of the binary Association
		if (2 == association.getMemberEnds().size()) {
			// Retrieve property ends of the Association (assumed to be binary)
			Property semanticSource = association.getMemberEnds().get(0);
			Property semanticTarget = association.getMemberEnds().get(1);
			if (semanticSource.getType().equals(semanticTarget.getType())) {
				if (request.getDirection() == ReorientRequest.REORIENT_SOURCE) {
					changeType = semanticTarget;
				}

				if (request.getDirection() == ReorientRequest.REORIENT_TARGET) {
					changeType = semanticSource;
				}
			}

			// if this a binary
			// 1 property change its parents, if it is not contains by the association
			// 1 property change its types
			changeContainer = semanticSource;
			if (changeType.equals(semanticSource)) {
				changeContainer = semanticTarget;
			}
			if (!association.getOwnedEnds().contains(changeContainer)) {
				moveRequest = new MoveRequest(request.getNewRelationshipEnd(), changeContainer);
			}

			setTypeRequest = new SetRequest(changeType, UMLPackage.eINSTANCE.getTypedElement_Type(), request.getNewRelationshipEnd());

			if (moveRequest != null) {
				// Propagate parameters to the move request
				moveRequest.addParameters(request.getParameters());
				IElementEditService provider = ElementEditServiceUtils.getCommandProvider(request.getNewRelationshipEnd());
				if (provider != null) {
					ICommand moveCommand = provider.getEditCommand(moveRequest);
					gmfCommand = CompositeCommand.compose(gmfCommand, moveCommand);
				}
			}

			if (gmfCommand != null) {
				gmfCommand.reduce();
			}
		} else {
			// Process nary-association
			// we do pay attention to change container
			// Forbid source reorient
			if (request.getDirection() == ReorientRequest.REORIENT_SOURCE) {
				return UnexecutableCommand.INSTANCE;
			}

			// Update the reoriented end
			changeType = getMemberEnd(association, (Classifier) request.getOldRelationshipEnd());

			if (changeType != null) {
				setTypeRequest = new SetRequest(changeType, UMLPackage.eINSTANCE.getTypedElement_Type(), request.getNewRelationshipEnd());
			}

		}

		if (setTypeRequest != null) {
			// Propagate parameters to the set request
			setTypeRequest.addParameters(request.getParameters());

			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(changeType);
			if (provider != null) {
				ICommand setTypeCommand = provider.getEditCommand(setTypeRequest);
				gmfCommand = CompositeCommand.compose(gmfCommand, setTypeCommand);
			}
		}


		return gmfCommand;
	}


	/**
	 * Find the first memberEnd of an association that is typed by the specified classifier
	 *
	 * @param association
	 * @param classifier
	 * @return
	 */
	protected Property getMemberEnd(Association association, Classifier classifier) {
		for (Property member : association.getMemberEnds()) {
			if (member.getType().equals(classifier)) {
				return member;
			}
		}
		return null;
	}
}
