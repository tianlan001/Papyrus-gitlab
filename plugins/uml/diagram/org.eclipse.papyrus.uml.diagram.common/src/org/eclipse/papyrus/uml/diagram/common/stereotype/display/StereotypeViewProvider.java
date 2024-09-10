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
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.display;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.StereotypeLocationEnum;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This Class Implement the interface {@link IStereotypeViewProvider} to provide Stereotype Views.
 *
 * @see IStereotypeViewProvider
 *
 * @author Céline JANSSENS
 *
 */
public class StereotypeViewProvider implements IStereotypeViewProvider {

	private View mainView;

	private static StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	/**
	 * Constructor.
	 *
	 * @param mainView
	 *            The View on which the Stereotype is applied (i.e: A Shape, an Edge, An Operation, ...)
	 */
	public StereotypeViewProvider(View mainView) {
		super();
		this.mainView = mainView;

	}



	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewProvider#getProperty(org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.Stereotype, java.lang.String)
	 *
	 */
	@Override
	public View getProperty(Property property, Stereotype stereotype, Enum location) {
		View propertyView = null;
		if (StereotypeLocationEnum.IN_COMPARTMENT.equals(location)) {
			propertyView = getStereotypePropertyInCompartment(mainView, stereotype, property);
		} else if (StereotypeLocationEnum.IN_BRACE.equals(location)) {
			propertyView = getStereotypePropertyInBrace(mainView, stereotype, property);
		} else if (StereotypeLocationEnum.IN_COMMENT.equals(location)) {
			propertyView = getStereotypePropertyInComment(mainView, stereotype, property);
		}
		return propertyView;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewProvider#getCompartment(org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.Stereotype, java.lang.String)
	 *
	 */
	@Override
	public View getCompartment(Stereotype stereotype, Enum location) {
		View compartmentView = null;
		if (StereotypeLocationEnum.IN_COMPARTMENT.equals(location)) {
			compartmentView = getStereotypeCompartmentInCompartment(mainView, stereotype);
		} else if (StereotypeLocationEnum.IN_BRACE.equals(location)) {
			compartmentView = getStereotypeCompartmentInBrace(mainView, stereotype);
		} else if (StereotypeLocationEnum.IN_COMMENT.equals(location)) {
			compartmentView = getStereotypeCompartmentInComment(mainView, stereotype);
		}
		return compartmentView;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.display.IStereotypeViewProvider#getLabel(org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.Stereotype, java.lang.String)
	 *
	 */
	@Override
	public View getLabel(Stereotype stereotype) {
		return getStereotypeLabel(mainView, stereotype);

	}


	/**
	 * Retrieve the Stereotype Compartment related to a Stereotype and create it if null.
	 *
	 * @param view
	 *            The view on which the Compartment is checked
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 * @return The Compartment related to the Stereotype (existing or newly created)
	 */
	public View getStereotypeLabel(final View view, final Stereotype stereotype) {
		View label = helper.getStereotypeLabel(view, stereotype);

		return label;
	}


	/**
	 * Retrieve the Stereotype Compartment View related to a Stereotype in Compartment and create it if null.
	 *
	 * @param view
	 *            The view on which the Compartment is checked
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 * @return The Compartment related to the Stereotype (existing or newly created)
	 */
	public View getStereotypeCompartmentInCompartment(final View view, final Stereotype stereotype) {
		View compartment = helper.getStereotypeCompartment(view, stereotype);

		return compartment;
	}


	/**
	 * Retrieve the Stereotype Compartment View related to a Stereotype in Brace and create it if null.
	 *
	 * @param view
	 *            The view on which the Compartment is checked
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 * @return The Compartment related to the Stereotype (existing or newly created)
	 */
	public View getStereotypeCompartmentInBrace(final View view, final Stereotype stereotype) {
		View compartment = helper.getStereotypeBraceCompartment(view, stereotype);

		return compartment;
	}


	/**
	 * Retrieve the Stereotype Compartment View related to a Stereotype in Comment and create it if null.
	 *
	 * @param view
	 *            The view on which the Compartment is checked
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 * @return The Compartment related to the Stereotype (existing or newly created)
	 */
	public View getStereotypeCompartmentInComment(View view, Stereotype stereotype) {
		View comment = helper.getStereotypeComment(view);
		View compartment = helper.getStereotypeCompartment(comment, stereotype);

		return compartment;
	}

	/**
	 * Retrieve the Stereotype Property related to a Stereotype and create it if null.
	 *
	 * @param view
	 *            The view on which the Compartment is checked
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 * @param property
	 *            The UML Property of the Stereotype to be retrieved.
	 *
	 * @return The Property related with the Stereotype (existing or newly created)
	 */
	public View getStereotypePropertyInCompartment(final View view, final Stereotype stereotype, final Property property) {
		View propertyView = helper.getStereotypeProperty(view, stereotype, property);


		return propertyView;
	}


	/**
	 * Retrieve the Stereotype Property View related to a Stereotype in Brace and create it if null.
	 *
	 * @param view
	 *            The view on which the Compartment is checked
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 * @param property
	 *            The UML Property of the Stereotype to be retrieved.
	 *
	 * @return The Property related with the Stereotype (existing or newly created)
	 */
	public View getStereotypePropertyInBrace(View view, Stereotype stereotype, Property property) {

		View propertyView = helper.getStereotypePropertyInBrace(view, stereotype, property);

		return propertyView;

	}



	/**
	 * Retrieve the Stereotype Property View related to a Stereotype in Comment and create it if null.
	 *
	 * @param view
	 *            The view on which the Compartment is checked
	 * @param stereotype
	 *            The Stereotype of the Compartment that is retrieved.
	 * @param property
	 *            The UML Property of the Stereotype to be retrieved.
	 *
	 * @return The Property related with the Stereotype (existing or newly created)
	 */
	public View getStereotypePropertyInComment(View view, Stereotype stereotype, Property property) {

		View propertyView = helper.getStereotypePropertyInComment(view, stereotype, property);

		return propertyView;

	}



}
