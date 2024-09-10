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
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 460356 : Refactor Stereotype Display
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype;


/**
 * This enumeration is used to list the existing possible locations of a Stereotype property to be displayed
 * 
 * @author Céline JANSSENS
 *
 */
public enum StereotypeLocationEnum {
	/**
	 * Defines the Brace Label on top of the figure that contains the list of Stereotype Properties
	 */
	IN_BRACE,

	/**
	 * Defines the Compartment into the figure dedicated to the Stereotype Properties
	 */
	IN_COMPARTMENT,

	/**
	 * Defines the Brace Label into a separate Comment Figure that contains the list of Stereotype Properties
	 * {@link Deprecated Not used}
	 */
	@Deprecated
	IN_COMMENT_BRACE,

	/**
	 * Defines the Compartment into a separate Comment Figure dedicated to the Stereotype Properties
	 */
	IN_COMMENT


}
