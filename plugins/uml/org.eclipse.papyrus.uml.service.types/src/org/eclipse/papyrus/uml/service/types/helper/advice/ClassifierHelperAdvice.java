/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *		Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 476873, 481317, 500642
 *		Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 348657
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.common.util.CrossReferencerUtil;
import org.eclipse.papyrus.uml.diagram.common.util.GeneralizationUtil;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterConstants;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AttributeOwner;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <pre>
 * This HelperAdvice completes {@link Classifier} edit commands with the deletion of :
 * - any Generalization related to the Classifier (source or target).
 * - any Association related to the Classifier (source or target type).
 *
 * This helper also add Association re-factor command when an Association member end is
 * moved.
 *
 * This helper prohibits the move of an Association Property into another Classifier
 * and the move of an Association Property into another Property.
 * </pre>
 */
public class ClassifierHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * While deleting a Classifier:
	 * - remove {@link Generalization} in which this Classifier is involved
	 * - remove {@link Association} in which this Classifier is involved
	 *
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {

		List<EObject> dependents = new ArrayList<EObject>();
		if (request.getElementToDestroy() instanceof Classifier) {

			Classifier classifierToDelete = (Classifier) request.getElementToDestroy();

			// Get related generalizations
			dependents.addAll(classifierToDelete.getSourceDirectedRelationships(UMLPackage.eINSTANCE.getGeneralization()));
			dependents.addAll(classifierToDelete.getTargetDirectedRelationships(UMLPackage.eINSTANCE.getGeneralization()));

			// Get related association for this classifier, then delete member ends for which this classifier is the type.
			for (Association association : classifierToDelete.getAssociations()) {
				for (Property end : association.getMemberEnds()) {
					if (end.getType() == classifierToDelete) {
						dependents.add(association);
					}
				}
			}
		}

		// Return the command to destroy all these dependent elements
		if (!dependents.isEmpty()) {
			return request.getDestroyDependentsCommand(dependents);
		}

		return null;
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * While moving a {@link Property} to a Classifier:
	 * - prohibit the drag&drop of an Association's Property into a Classifier
	 * - re-orient Association possibly related to the moved Property
	 * - remove deprecated connectorEnd
	 *
	 * NB: this method handles the case ViewerDropAdapter.LOCATION_ON where a property is moved into a Classifier.
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeMoveCommand(final MoveRequest request) {

		ICommand gmfCommand = super.getBeforeMoveCommand(request);

		// Find any ConnectorEnd that would become invalid after the Property move
		for (Object movedObject : request.getElementsToMove().keySet()) {

			// Select Property (excluding Port) in the list of moved elements
			if (!(movedObject instanceof Property) || (movedObject instanceof Port)) {
				continue;
			}

			Property movedProperty = (Property) movedObject;

			// If the current property is a child of an association, the move request must be prohibited
			// NB: Do not use movedPropety.getAssociation() as it will forbid also the association reorient, which must be allowed in all cases
			if (movedProperty.eContainer() instanceof Association) {
				return UnexecutableCommand.INSTANCE;
			}

			// Find ConnectorEnd referencing the edited Property as partWithPort or role
			EReference[] refs = new EReference[] { UMLPackage.eINSTANCE.getConnectorEnd_PartWithPort(), UMLPackage.eINSTANCE.getConnectorEnd_Role() };
			@SuppressWarnings("unchecked")
			Collection<ConnectorEnd> referencers = EMFCoreUtil.getReferencers(movedProperty, refs);

			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(movedProperty);
			if (null != provider) {
				for (ConnectorEnd end : referencers) {
					// General case, delete the ConnectorEnd
					DestroyElementRequest req = new DestroyElementRequest(end, false);
					ICommand deleteCommand = provider.getEditCommand(req);

					// Add current EObject destroy command to the global command
					gmfCommand = CompositeCommand.compose(gmfCommand, deleteCommand);
				}
			}
		}

		// Treat related associations that required a re-factor action
		// Retrieve elements already under re-factor.
		List<EObject> currentlyRefactoredElements = (request.getParameter(RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS) != null) ? (List<EObject>) request.getParameter(RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS)
				: new ArrayList<EObject>();

		// Find Associations related to any moved Property
		for (Object movedObject : request.getElementsToMove().keySet()) {

			// Select Property (excluding Port) in the list of moved elements
			if (!(movedObject instanceof Property) || (movedObject instanceof Port)) {
				continue;
			}

			Property movedProperty = (Property) movedObject;
			Association relatedAssociation = movedProperty.getAssociation();

			// The moved property has to be related to a UML association
			if ((movedProperty.getAssociation() == null) || !(ElementUtil.hasNature(movedProperty.getAssociation(), UMLElementTypes.UML_NATURE))) {
				continue;
			}

			// Make sure the target differs from current container
			if ((movedProperty.eContainer() == request.getTargetContainer()) && (movedProperty.eContainingFeature() == request.getTargetFeature(movedProperty))) {
				continue;
			}

			// Moved element already under re-factor ?
			if (currentlyRefactoredElements.contains(movedObject) || currentlyRefactoredElements.contains(relatedAssociation)) {
				continue;

			} else {
				currentlyRefactoredElements.add((EObject) movedObject);
				request.getParameters().put(RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS, currentlyRefactoredElements);
			}

			// refactor the opposite only this is the binary association,
			// in other case the property end are owned by the association.
			if (relatedAssociation.getMemberEnds().size() == 2) {
				ICommand refactorCommand = getOppositePropertyRefactoringCommand(movedProperty, relatedAssociation, request);
				gmfCommand = CompositeCommand.compose(gmfCommand, refactorCommand);
			}
		}

		if (null != gmfCommand) {
			gmfCommand = gmfCommand.reduce();
		}

		return gmfCommand;
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * Overridden to prohibit the move of an association property to a position which is before or after an element of an {@link AttributeOwner}.
	 * It handles the case ViewerDropAdapter.LOCATION_BEFORE or ViewerDropAdapter.LOCATION_AFTER. In this case, a SetRequest is triggered
	 * instead of a MoveRequest.
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeSetCommand(final SetRequest request) {
		ICommand gmfCommand = super.getBeforeSetCommand(request);

		if (request.getValue() instanceof ArrayList && request.getElementToEdit() instanceof AttributeOwner && request.getFeature().equals(UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute())) {
			ArrayList<?> newPropertyList = (ArrayList<?>) request.getValue();
			EList<Property> oldPropertyList = ((AttributeOwner) request.getElementToEdit()).getOwnedAttributes();

			// Traverse the new property list
			for (Object object : newPropertyList) {
				// If there exists the one that is a non existing Property and its parent is an Association
				// then the command must be prohibited
				if (object instanceof Property && !oldPropertyList.contains(object) && (((Property) object).eContainer() instanceof Association)) {
					return UnexecutableCommand.INSTANCE;
				}
			}
		}

		return gmfCommand;
	}

	/**
	 * Create a re-factoring command related to a Property move.
	 *
	 * @param movedProperty
	 *            the moved property
	 * @param associationToRefactor
	 *            the association to re-factor (re-orient action)
	 * @param request
	 *            the original move request
	 * @return the re-factoring command
	 */
	private ICommand getOppositePropertyRefactoringCommand(final Property movedProperty, final Association associationToRefactor, final MoveRequest request) {
		Association relatedAssociation = movedProperty.getAssociation(); // Should not be null, test before calling method.
		// get the opposite the property opposite
		Property oppositeEnd = associationToRefactor.getMemberEnds().get(0);

		// when we move the property
		// Re-orient the related association (do not use edit service to avoid infinite loop here)
		if (movedProperty == associationToRefactor.getMemberEnds().get(0)) {
			oppositeEnd = associationToRefactor.getMemberEnds().get(1);
		}

		SetRequest setType = new SetRequest(oppositeEnd, UMLPackage.eINSTANCE.getTypedElement_Type(), request.getTargetContainer());

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(relatedAssociation);
		if (null != provider) {
			return provider.getEditCommand(setType);
		}

		return null;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterMoveCommand(org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterMoveCommand(final MoveRequest request) {
		ICommand moveCommand = super.getAfterMoveCommand(request);

		Set<View> viewsToDestroy = new HashSet<View>();

		@SuppressWarnings("unchecked")
		final Iterator<EObject> it = request.getElementsToMove().keySet().iterator();
		final EObject targetContainer = request.getTargetContainer();
		while (it.hasNext()) {
			final EObject eObject = it.next();

			if (eObject instanceof Generalization) {
				viewsToDestroy.addAll(getViewsToDestroy(eObject));
				viewsToDestroy.addAll(getViewsAccordingToGeneralization((Generalization) eObject));
			}
			else if (eObject instanceof Port) {
				// remove view of ports attached to parts, if the port changes its owner.
				for (View viewToDestroy : getViewsToDestroy(eObject)) {
					View parentView = ViewUtil.getContainerView(viewToDestroy);
					if (parentView.getElement() instanceof Property) {
						Property part = (Property) parentView.getElement();
						boolean destroy = true;
						// check if the view does not need to be destroyed since the port is still
						// owned by the partType or one of its super-classes
						if (part.getType() instanceof org.eclipse.uml2.uml.Class) {
							org.eclipse.uml2.uml.Class partType = (org.eclipse.uml2.uml.Class) part.getType();
							if (partType == targetContainer || partType.getGenerals().contains(targetContainer)) {
								destroy = false;
							}
						}
						if (destroy) {
							viewsToDestroy.add(viewToDestroy);
						}
					}
				}
			}
			else if (eObject instanceof Feature || eObject instanceof Classifier) {
				viewsToDestroy.addAll(getViewsAccordingToEObject(eObject, targetContainer));
			}
		}

		final Iterator<View> viewToDestroyIterator = viewsToDestroy.iterator();
		while (viewToDestroyIterator.hasNext()) {
			final View view = viewToDestroyIterator.next();
			final TransactionalEditingDomain editingDomain = request.getEditingDomain();
			// Need to selectively delete the views as we do not want to delete the moved view
			if (!view.equals(request.getParameter(RequestParameterConstants.AFFECTED_VIEW))) {
				final DeleteCommand destroyViewsCommand = new DeleteCommand(editingDomain, view);
				moveCommand = CompositeCommand.compose(moveCommand, destroyViewsCommand);
			}
		}

		return moveCommand;
	}

	/**
	 * This methods looks for inconsistent views to delete in case the Classifier or a child is deleted or
	 * re-oriented.
	 *
	 * @param movedObject
	 *            the modified Classifier
	 * @return the list of {@link View} to delete
	 */
	private Set<View> getViewsToDestroy(final EObject movedObject) {
		Set<View> viewsToDestroy = new HashSet<View>();

		final Iterator<View> viewIt = CrossReferencerUtil.getCrossReferencingViews(movedObject, null).iterator();
		while (viewIt.hasNext()) {
			final View view = viewIt.next();

			final String containerType = ViewUtil.getViewContainer(view) != null ? ViewUtil.getViewContainer(view).getType() : null;
			if (null != containerType) {
				viewsToDestroy.add(view);
			}
		}

		return viewsToDestroy;
	}

	/**
	 * This methods looks for inconsistent views to delete in case the generalization is deleted or
	 * re-oriented.
	 *
	 * @param generalization
	 *            the modified generalization
	 * @return the list of {@link View} to delete
	 */
	protected Set<View> getViewsAccordingToGeneralization(final Generalization generalization) {
		Set<View> viewsToDestroy = new HashSet<View>();

		final Classifier general = generalization.getGeneral();
		if (null != general) {
			// Parse members
			final EList<NamedElement> members = general.getMembers();
			for (final NamedElement member : members) {

				// Find Views in Composite Structure Diagram that are referencing current member
				final Iterator<View> viewIt = CrossReferencerUtil.getCrossReferencingViews(member, null).iterator();
				while (viewIt.hasNext()) {
					final View view = viewIt.next();

					// Test if current view (member) is concerned by the deletion (re-orientation) of the generalization
					final GeneralizationUtil util = new GeneralizationUtil();
					if (util.isConcernedByGeneralizationChanges(generalization, view)) {
						viewsToDestroy.add(view);
					}
				}
			}
		}

		return viewsToDestroy;
	}

	/**
	 * This method returns a list of views to be deleted after a move of a Property.
	 *
	 * @param property
	 *            The Property
	 * @param targetContainer
	 *            The target container of the move.
	 * @return The list of view to delete.
	 */
	protected Set<View> getViewsAccordingToEObject(final EObject property, final EObject targetContainer) {
		Set<View> viewsToDestroy = new HashSet<View>();
		if (targetContainer instanceof Classifier) {
			for (View view : getViewsToDestroy(property)) {
				View containerView = ViewUtil.getContainerView(view);
				if (null != containerView) {
					EObject containerSemanticElement = ViewUtil.resolveSemanticElement(containerView);
					if (containerSemanticElement instanceof Classifier) {
						if (!containerSemanticElement.equals(targetContainer) && containerSemanticElement != property) {
							EList<Classifier> allParents = ((Classifier) containerSemanticElement).allParents();
							if (!allParents.contains(targetContainer)) {
								viewsToDestroy.add(view);
							}
						}
					}
				}
			}
		}
		return viewsToDestroy;
	}
}
