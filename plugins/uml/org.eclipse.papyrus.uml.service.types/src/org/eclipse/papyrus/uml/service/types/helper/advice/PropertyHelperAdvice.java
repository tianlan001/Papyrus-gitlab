/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST and others.
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
 *		Christian W. Damus (CEA) - bug 413703
 *		Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 348657
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterConstants;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <pre>
 * This HelperAdvice completes {@link Property} edit commands with:
 * 		- the deletion of any ConnectorEnd related to the Property.
 * 		- the deletion of any {@link Association} related to the Property when less than 2 ends remains.
 *
 * This helper also prohibits the move of an Association's Property into another Property.
 * </pre>
 */
public class PropertyHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * While deleting a {@link Property}:
	 * - remove related {@link ConnectorEnd}
	 * - remove related {@link Association} when less than 2 ends remains.
	 *
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {

		if (request.getElementToDestroy() instanceof Property) {

			List<EObject> dependents = new ArrayList<>();
			EReference[] refs = null;

			Property propertyToDelete = (Property) request.getElementToDestroy();

			// Get related ConnectorEnd to be destroyed with the property
			// Possible references from ConnectorEnd to Property (or Port)
			refs = new EReference[] { UMLPackage.eINSTANCE.getConnectorEnd_Role(), UMLPackage.eINSTANCE.getConnectorEnd_PartWithPort() };
			@SuppressWarnings("unchecked")
			Collection<ConnectorEnd> connectorEndRefs = EMFCoreUtil.getReferencers(propertyToDelete, refs);

			dependents.addAll(connectorEndRefs);

			// Get possible associations using this Property as end
			refs = new EReference[] { UMLPackage.eINSTANCE.getAssociation_MemberEnd() };
			@SuppressWarnings("unchecked")
			Collection<Association> associationRefs = EMFCoreUtil.getReferencers(propertyToDelete, refs);
			for (Association association : associationRefs) {

				// Test the number of remaining ends considering the dependents elements deletion in progress
				List<Property> remainingMembers = new ArrayList<>();
				remainingMembers.addAll(association.getMemberEnds());
				remainingMembers.removeAll(request.getDependentElementsToDestroy());

				if (remainingMembers.size() <= 2) {
					dependents.add(association);
				}
			}

			// Return the command to destroy all these dependents
			if (!dependents.isEmpty()) {
				return request.getDestroyDependentsCommand(dependents);
			}
		}

		return null;
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * While setting {@link Property} (excluding {@link Port}) type:
	 * - remove related {@link ConnectorEnd} if they become inconsistent due to the new {@link Type}.
	 * - add possibly required (UML) association re-factor command when needed.
	 *
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeSetCommand(SetRequest request) {
		ICommand gmfCommand = super.getBeforeSetCommand(request);

		EObject elementToEdit = request.getElementToEdit();

		// Two member ends of an association cannot be set to composite at the same time. To avoid
		// such a situation this helper turns other ends into aggregation none before changing the property aggregation.
		if ((elementToEdit instanceof Property) && !(elementToEdit instanceof Port) && (request.getFeature() == UMLPackage.eINSTANCE.getProperty_Aggregation()) && (request.getValue() != AggregationKind.NONE_LITERAL)) {
			Property propertyToEdit = (Property) elementToEdit;

			// Only apply if the property is an association end.
			Association relatedAssociation = propertyToEdit.getAssociation();
			if (relatedAssociation != null) {
				Set<Property> members = new HashSet<>();
				members.addAll(relatedAssociation.getMemberEnds());
				members.remove(propertyToEdit);

				for (Property member : members) {
					if (member.getAggregation() != AggregationKind.NONE_LITERAL) {
						SetRequest setRequest = new SetRequest(member, UMLPackage.eINSTANCE.getProperty_Aggregation(), AggregationKind.NONE_LITERAL);
						SetValueCommand setAggregationCommand = new SetValueCommand(setRequest);
						gmfCommand = CompositeCommand.compose(gmfCommand, setAggregationCommand);
					}
				}
			}
		}

		// Type set to null implies the property should be kept and the association deleted: see https://bugs.eclipse.org/bugs/show_bug.cgi?id=477724
		if ((elementToEdit instanceof Property) && !(elementToEdit instanceof Port) && (request.getFeature() == UMLPackage.eINSTANCE.getTypedElement_Type()) && (request.getValue() == null)) {
			Property propertyToEdit = (Property) elementToEdit;
			Association relatedAssociation = propertyToEdit.getAssociation();

			if (relatedAssociation != null) {
				// General case, delete the ConnectorEnd
				IElementEditService provider = ElementEditServiceUtils.getCommandProvider(relatedAssociation);
				if (provider != null) {
					DestroyElementRequest destroyRequest = new DestroyElementRequest(relatedAssociation, false);
					List<EObject> ps = new ArrayList<>();
					ps.add(propertyToEdit);
					destroyRequest.setParameter(org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants.DEPENDENTS_TO_KEEP, ps);
					ICommand destroyCommand = provider.getEditCommand(destroyRequest);
					gmfCommand = CompositeCommand.compose(gmfCommand, destroyCommand);
				}
			}
		}

		if ((elementToEdit instanceof Property) && !(elementToEdit instanceof Port) && (request.getFeature() == UMLPackage.eINSTANCE.getTypedElement_Type()) && (request.getValue() instanceof Type)) {

			Property propertyToEdit = (Property) elementToEdit;

			// Find ConnectorEnd referencing the edited Property as partWithPort
			EReference[] refs = new EReference[] { UMLPackage.eINSTANCE.getConnectorEnd_PartWithPort() };
			@SuppressWarnings("unchecked")
			Collection<ConnectorEnd> referencers = EMFCoreUtil.getReferencers(propertyToEdit, refs);

			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(propertyToEdit);
			if (provider != null) {
				for (ConnectorEnd end : referencers) {
					Type newType = (Type) request.getValue();

					// End role should be a Port
					ConnectableElement cElt = end.getRole();
					if ((newType != null) && (newType instanceof EncapsulatedClassifier) && (cElt != null) && (cElt instanceof Port)) {

						// Take the new type into account to decide if current role and partWithPort will remains
						// valid after type modification.
						Port role = (Port) cElt;
						EncapsulatedClassifier composite = (EncapsulatedClassifier) newType;

						// If the role is valid, the ConnectorEnd should not be deleted
						if (composite.getAllAttributes().contains(role)) {
							continue;
						}
					}

					// General case, delete the ConnectorEnd
					DestroyElementRequest req = new DestroyElementRequest(end, false);
					ICommand deleteCommand = provider.getEditCommand(req);

					// Add current EObject destroy command to the global command
					gmfCommand = CompositeCommand.compose(gmfCommand, deleteCommand);
				}
			}

			// Setting new type can be related to an association re-orient (or trigger the association re-orient)
			// Retrieve elements already under re-factor.
			Association relatedAssociation = propertyToEdit.getAssociation();

			// The edited property has to be related to a UML association
			if ((relatedAssociation == null) || !(ElementUtil.hasNature(relatedAssociation, UMLElementTypes.UML_NATURE))) {
				return gmfCommand;
			}

			// the request comes from re-orient association? so do not remove the edge
			if (request.getParameter(RequestParameterConstants.ASSOCIATION_REFACTORED_ELEMENTS) == null) {
				// Destroy inconsistent views of the association
				Set<View> viewsToDestroy = new HashSet<>();
				viewsToDestroy.addAll(getViewsToDestroy(relatedAssociation));


				// return the command to destroy all these views
				if (!viewsToDestroy.isEmpty()) {
					DestroyDependentsRequest ddr = new DestroyDependentsRequest(request.getEditingDomain(), relatedAssociation, false);
					ddr.setClientContext(request.getClientContext());
					ddr.addParameters(request.getParameters());
					ICommand destroyViewsCommand = ddr.getDestroyDependentsCommand(viewsToDestroy);
					gmfCommand = CompositeCommand.compose(gmfCommand, destroyViewsCommand);
				}
				if (gmfCommand != null) {
					gmfCommand.reduce();
				}
			}
		}

		if (gmfCommand != null) {
			gmfCommand = gmfCommand.reduce();
		}

		return gmfCommand;
	}

	@Override
	public void configureRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			configureCreateElementRequest((CreateElementRequest) request);
		} else {
			super.configureRequest(request);
		}
	}

	protected void configureCreateElementRequest(CreateElementRequest request) {
		if ((request.getContainmentFeature() == null) && UMLPackage.Literals.VALUE_SPECIFICATION.isSuperTypeOf(request.getElementType().getEClass())) {
			// Prefer to create value specifications as property default values, not as lower/upper values for multiplicity
			request.setContainmentFeature(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE);
		}
	}

	/**
	 * Returns all views referencing Association except the view currently re-oriented.
	 *
	 * @param association
	 *            the association referenced by views
	 * @param request
	 *            the re-orient relationship request
	 * @return the list of views to be destroy
	 */
	private HashSet<View> getViewsToDestroy(Association association) {
		HashSet<View> viewsToDestroy = new HashSet<>();

		// Find all views representing the Associations
		EReference[] refs = new EReference[] { NotationPackage.eINSTANCE.getView_Element() };
		@SuppressWarnings("unchecked")
		Collection<View> associationViews = EMFCoreUtil.getReferencers(association, refs);

		viewsToDestroy.addAll(associationViews);

		return viewsToDestroy;
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * Prohibit the move command of a {@link Property}, which belongs to an {@link Association}, to a {@link Property}.
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeMoveCommand(final MoveRequest request) {

		ICommand gmfCommand = super.getBeforeMoveCommand(request);

		for (Object movedObject : request.getElementsToMove().keySet()) {

			// Prohibit the move request if the current property belongs to an association
			if (movedObject instanceof Property && ((Property) movedObject).eContainer() instanceof Association) {
				return UnexecutableCommand.INSTANCE;
			}
		}

		return gmfCommand;
	}
}
