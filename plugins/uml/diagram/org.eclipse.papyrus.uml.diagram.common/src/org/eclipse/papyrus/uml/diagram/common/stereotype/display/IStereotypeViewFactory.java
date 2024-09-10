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

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This Factory creates the different Stereotype View related to the Location.
 *
 * @author Céline JANSSENS
 *
 */
public interface IStereotypeViewFactory {

	/**
	 * Create Property View according to a specific Location.
	 *
	 * @param property
	 *            The UML property for the View to be created
	 * @param stereotype
	 *            The UML Stereotype for which the property is required
	 * @param location
	 *            The property location can be:
	 *            <ul>
	 *            <li>{@link StereotypeDisplayConstant#STEREOTYPE_COMMENT_LOCATION}</li>
	 *            <li>{@link StereotypeDisplayConstant#STEREOTYPE_COMPARTMENT_LOCATION}</li>
	 *            <li>{@link StereotypeDisplayConstant#STEREOTYPE_BRACE_LOCATION}</li>
	 *            </ul>
	 * @param owner
	 *            The Compartment View that should contained the Property View
	 */
	public void createPropertyView(Property property, Stereotype stereotype, View owner, String location);

	/**
	 * Create Compartment View according to a specific Location.
	 *
	 * @param stereotype
	 *            The UML Stereotype for which the property is required
	 * @param location
	 *            The property location can be:
	 *            <ul>
	 *            <li>{@link StereotypeDisplayConstant#STEREOTYPE_COMMENT_LOCATION}</li>
	 *            <li>{@link StereotypeDisplayConstant#STEREOTYPE_COMPARTMENT_LOCATION}</li>
	 *            <li>{@link StereotypeDisplayConstant#STEREOTYPE_BRACE_LOCATION}</li>
	 *            </ul>
	 */
	public void createCompartmentView(Stereotype stereotype, String location);


	/**
	 * Create Label View.
	 *
	 * @param stereotype
	 *            The UML Stereotype for which the property is required
	 *
	 */
	public void createLabelView(Stereotype stereotype);




}
