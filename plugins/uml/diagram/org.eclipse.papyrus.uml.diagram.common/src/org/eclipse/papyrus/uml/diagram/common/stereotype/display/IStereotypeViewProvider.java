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
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This interface provides the View of the Stereotype Structure
 * If the view doesn't exist it creates the node thanks to the {@link IStereotypeViewFactory } Class.
 * It should never return null;
 * 
 * @author Céline JANSSENS
 *
 */
public interface IStereotypeViewProvider {

	/**
	 * Get the Property View for a specific location
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
	 * @return The Property view (existing or newly created)
	 */
	public View getProperty(Property property, Stereotype stereotype, Enum location);

	/**
	 * Get the Compartment View for a specific location
	 * 
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
	 * @return The Compartment view (existing or newly created)
	 */
	public View getCompartment(Stereotype stereotype, Enum location);


	/**
	 * Get the Label View for a specific location
	 * 
	 * 
	 * @param stereotype
	 *            The UML Stereotype for which the property is required
	 * 
	 * @return The Label view (existing or newly created)
	 */
	public View getLabel(Stereotype stereotype);

}
