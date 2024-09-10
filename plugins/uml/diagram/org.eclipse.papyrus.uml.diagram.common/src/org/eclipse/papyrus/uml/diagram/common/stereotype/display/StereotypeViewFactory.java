/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.display;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypeCompartmentCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypePropertyViewCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateStereotypeLabelCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This Factory implement {@link IStereotypeViewFactory} and is in charge of creating Stereotype Views.
 *
 * @author Céline JANSSENS
 *
 */
public class StereotypeViewFactory implements IStereotypeViewFactory {

	private View mainView;
	private TransactionalEditingDomain domain;

	/**
	 * Constructor.
	 *
	 * @param mainView
	 *            The View on which the Stereotype is applied (i.e: A Shape, an Edge, An Operation, ...)
	 *
	 */
	public StereotypeViewFactory(View mainView) {
		super();
		this.mainView = mainView;
		this.domain = CommandUtil.resolveEditingDomain(mainView);
	}


	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewFactory#createPropertyView(org.eclipse.gmf.runtime.notation.View, org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.Stereotype, org.eclipse.gmf.runtime.notation.View,
	 *      java.lang.String)
	 *
	 */
	@Override
	public void createPropertyView(Property property, Stereotype stereotype, View owner, String location) {

		if (StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_LOCATION.equals(location)) {
			createStereotypePropertyInCompartment(owner, property);
		} else if (StereotypeDisplayConstant.STEREOTYPE_BRACE_LOCATION.equals(location)) {
			createStereotypePropertyInBrace(owner, property);
		} else if (StereotypeDisplayConstant.STEREOTYPE_COMMENT_LOCATION.equals(location)) {
			createStereotypePropertyInComment(owner, property);
		}

	}



	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewFactory#createCompartmentView(org.eclipse.gmf.runtime.notation.View, org.eclipse.uml2.uml.Stereotype, java.lang.String)
	 *
	 */
	@Override
	public void createCompartmentView(Stereotype stereotype, String location) {
		if (StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_LOCATION.equals(location)) {
			createStereotypeCompartmentInCompartment(stereotype);
		} else if (StereotypeDisplayConstant.STEREOTYPE_BRACE_LOCATION.equals(location)) {
			createStereotypeCompartmentInBrace(stereotype);
		} else if (StereotypeDisplayConstant.STEREOTYPE_COMMENT_LOCATION.equals(location)) {
			createStereotypeCompartmentInComment(stereotype);
		}


	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewFactory#createLabelView(org.eclipse.gmf.runtime.notation.View, org.eclipse.uml2.uml.Stereotype)
	 */
	@Override
	public void createLabelView(Stereotype stereotype) {
		createStereotypeLabel(stereotype);

	}


	/**
	 * Execute the creation Compartment command
	 *
	 * @param stereotype
	 *            Stereotype for which the View is created
	 */
	private void createStereotypeCompartmentInComment(Stereotype stereotype) {
		View comment = StereotypeDisplayUtil.getInstance().getStereotypeComment(mainView);
		CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(domain, comment, stereotype, StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE);
		CommandUtil.executeUnsafeCommand(command, mainView);

	}





	/**
	 * Create the Compartment of Stereotype to be shown into the Compartment of the Shape.
	 *
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 */
	private void createStereotypeCompartmentInBrace(Stereotype stereotype) {
		CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(domain, mainView, stereotype, StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE);
		CommandUtil.executeUnsafeCommand(command, mainView);

	}

	/**
	 * Create the Compartment of Stereotype to be shown into the Compartment of the Shape.
	 *
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 */
	private void createStereotypeCompartmentInCompartment(Stereotype stereotype) {
		CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(domain, mainView, stereotype, StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE);
		CommandUtil.executeUnsafeCommand(command, mainView);

	}

	/**
	 * Create the Property of the Stereotype to be shown into the Compartment of the Shape.
	 *
	 *
	 * @param compartment
	 *            The Compartment View that is the Owner of the new Property
	 * @param property
	 *            The UML Property of the Stereotype to be retrieved.
	 *
	 */
	private void createStereotypePropertyInCompartment(View compartment, Property property) {
		CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(domain, compartment, property, StereotypeDisplayConstant.STEREOTYPE_PROPERTY_TYPE);
		CommandUtil.executeUnsafeCommand(command, mainView);

	}

	/**
	 * Create the Property of the Stereotype to be shown into the Brace of the Shape.
	 *
	 *
	 * @param compartment
	 *            The Compartment View that is the Owner of the new Property
	 * @param property
	 *            The UML Property of the Stereotype to be retrieved.
	 *
	 */
	private void createStereotypePropertyInBrace(View compartmentView, Property property) {
		CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(domain, mainView, property, StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE);
		CommandUtil.executeUnsafeCommand(command, mainView);

	}

	/**
	 * Create the Property of the Stereotype to be shown into the Comment of the Shape.
	 *
	 *
	 * @param compartment
	 *            The Compartment View that is the Owner of the new Property
	 * @param property
	 *            The UML Property of the Stereotype to be retrieved.
	 *
	 */
	private void createStereotypePropertyInComment(View compartmentView, Property property) {
		CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(domain, compartmentView, property, StereotypeDisplayConstant.STEREOTYPE_PROPERTY_TYPE);
		CommandUtil.executeUnsafeCommand(command, mainView);

	}



	/**
	 * Execute the {@link CreateStereotypeLabelCommand}
	 *
	 * @param view
	 *            The view that required a Stereotype Label
	 */
	private void createStereotypeLabel(final Stereotype stereotype) {
		CreateStereotypeLabelCommand command = new CreateStereotypeLabelCommand(domain, mainView, stereotype);
		CommandUtil.executeUnsafeCommand(command, mainView);
	}





}
