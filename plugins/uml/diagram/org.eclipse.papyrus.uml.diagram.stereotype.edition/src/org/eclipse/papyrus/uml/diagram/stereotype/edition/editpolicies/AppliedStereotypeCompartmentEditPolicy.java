/*****************************************************************************
 * Copyright (c) 2012, 2016, 2024 CEA LIST, Christian W. Damus, and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 323802
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.fr - Bug 393532
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotypes Display
 *  Christian W. Damus - bug 492482
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 583091
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.stereotype.edition.editpolicies;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeNodeLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeUMLElementFigure;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypeCompartmentCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypePropertyViewCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * this edit policy can be apply only on {@link IPapyrusEditPart} in order to
 * access to primary figure. the primary figure has to be a {@link IPapyrusNodeUMLElementFigure}
 *
 */

public class AppliedStereotypeCompartmentEditPolicy extends AppliedStereotypeNodeLabelDisplayEditPolicy {

	/** constant for this edit policy role */
	public static final String STEREOTYPE_COMPARTMENT_POLICY = "AppliedStereotypeCompartmentEditPolicy"; //$NON-NLS-1$

	/**
	 * Creates a new AppliedStereotype display edit policy
	 */
	public AppliedStereotypeCompartmentEditPolicy() {
		super();
	}


	/**
	 * Refresh the NotationStructure of the Node for this Stereotype
	 *
	 * @param notationView
	 * @param stereo
	 */
	@Override
	public void refreshStereotypeStructure() {

		// Build the structure from the Stereotype List
		if (!stereotypeList.isEmpty()) {
			for (Stereotype stereotype : stereotypeList) {
				refreshStereotypeCompartmentStructure(stereotype);
				subscribe(helper.getStereotypeCompartment(hostView, stereotype));

			}
		}
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeNodeLabelDisplayEditPolicy#refreshStereotypeDisplay()
	 *
	 */
	@Override
	protected void refreshStereotypeDisplay() {
		// Nothing to refresh
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractAppliedStereotypeDisplayEditPolicy#removeListener()
	 *
	 */
	@Override
	public void removeListener() {
		// Remove Listener for Compartment and properties
		if (!stereotypeList.isEmpty()) {
			for (Stereotype stereotype : stereotypeList) {
				Node compartment = helper.getStereotypeCompartment(hostView, stereotype);

				if (null != compartment && null != stereotype) {
					unsubscribe(compartment);
					EList<Property> properties = stereotype.allAttributes();
					for (Property property : properties) {
						unsubscribe(helper.getStereotypeProperty(hostView, stereotype, property));
					}
					unsubscribe(helper.getStereotypeCompartment(hostView, stereotype));



				}
			}
		}
		super.removeListener();
	}

	/**
	 * Refresh The StereotypeCompartment notation structure.
	 *
	 * @param view
	 * @param stereotype
	 */

	protected void refreshStereotypeCompartmentStructure(Stereotype stereotype) {

		BasicCompartment compartment = helper.getStereotypeCompartment(hostView, stereotype);
		if (null == compartment) { // No Compartment Exist for this Stereotype
			createAppliedStereotypeCompartment(stereotype);
		}
		createAppliedStereotypeProperties(stereotype);
	}

	/**
	 * This method creates a node for the compartment of stereotype if it does not exist.
	 *
	 * @param stereotypeApplication
	 *            the stereotype application
	 */
	protected void createAppliedStereotypeCompartment(final Stereotype stereotype) {
			final View node = hostEditPart.getNotationView();
			// doesn't exist already
			if (!helper.isCompartmentExist(node, stereotype)) {
				// Create Compartment
				executeAppliedStereotypeCompartmentCreation(hostEditPart, stereotype);
			}
		}


	/**
	 * In charge of properties view creation
	 *
	 * @param eObject
	 *            The Edit Part of which the Properties should be created
	 */

	protected void createAppliedStereotypeProperties(final Stereotype stereotype) {

			Node compartment = helper.getStereotypeCompartment(hostEditPart.getNotationView(), stereotype);
			if (null != compartment && null != stereotype) {

				EList<Property> properties = stereotype.allAttributes();
				for (Property property : properties) {
					createAppliedStereotypeProperty(compartment, property);
					subscribe(helper.getStereotypeProperty(hostView, stereotype, property));

				}

		}
	}

	/**
	 * In Charge of PropertyView Creation
	 *
	 * @param propertyType
	 *            Type of Property {@link StereotypeDisplayConstant#STEREOTYPE_PROPERTY_TYPE} or {@link StereotypeDisplayConstant#STEREOTYPE_PROPERTY_BRACE_TYPE}
	 * @param compartment
	 *            The Compartment owner of the Property that will be created
	 * @param property
	 *            The UML Property of the View to create
	 */

	protected void createAppliedStereotypeProperty(Node compartment, Property property) {
		// if stereotype is null all property of stereotype has to be removed!
		if (null != property && !property.getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
			if (!helper.isPropertyExist(compartment, property)) {
					// go through each stereotype property
					executeAppliedStereotypePropertyViewCreation(hostEditPart, compartment, property);

				}
			}
		}


	/**
	 * the goal of this method is to execute the a command to create a notation node for a compartment of stereotype
	 *
	 * @param editPart
	 *            the editPart owner of the new compartment
	 * @param appliedstereotype
	 *            the stereotype application
	 */
	protected void executeAppliedStereotypeCompartmentCreation(final IGraphicalEditPart editPart, final Stereotype stereotype) {
		CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(editPart.getEditingDomain(), editPart.getNotationView(), stereotype, StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE);

		// Record for undo if possible, otherwise unprotected
		execute(command);
	}


	/**
	 * this method is used to create a notation node for the property of the stereotype
	 *
	 * @param editPart
	 *            the editPart container
	 * @param compartment
	 * @param stereotypeApplication
	 * @param stereotype
	 *            the stereotype associated to compartment node
	 */
	protected void executeAppliedStereotypePropertyViewCreation(final IGraphicalEditPart editPart, final Node compartment, final Property stereotypeProperty) {
		CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(editPart.getEditingDomain(), compartment, stereotypeProperty, StereotypeDisplayConstant.STEREOTYPE_PROPERTY_TYPE);

		// Record for undo if possible, otherwise unprotected
		execute(command);
	}


}
