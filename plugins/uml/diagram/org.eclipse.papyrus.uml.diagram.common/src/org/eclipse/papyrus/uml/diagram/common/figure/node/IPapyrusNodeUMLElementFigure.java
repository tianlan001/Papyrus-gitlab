/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;

/**
 * declaration of figure that can manage stereotype display
 */
public interface IPapyrusNodeUMLElementFigure extends IPapyrusUMLElementFigure {

	/**
	 * Sets the stereotypes properties for this figure.
	 * <p>
	 * This implementation checks if the specified string is null or not.
	 * <ul>
	 * <li>if the string is <code>null</code>, it removes the label representing the stereotypes properties with brace.</li>
	 * <li>if this is not <code>null</code>, it creates the stereotype properties label if needed and displays the specified string.</li>
	 * </ul>
	 * </p>
	 *
	 * @param stereotypeProperties
	 *            the string representing the stereotype properties to be
	 *            displayed
	 */
	public void setStereotypePropertiesInBrace(String stereotypeProperties);

	/**
	 * displays the new string corresponding to the list of stereotypes.
	 *
	 * if the string is <code>null</code>, then the figure that displays the
	 * stereotype label is removed from the NodeNamedElementFigure.
	 *
	 * @param stereotypeProperties
	 *            the string to be displayed.
	 */
	public void setStereotypePropertiesInCompartment(String stereotypeProperties);

	/**
	 * Gets the stereotypes label.
	 *
	 * @return the stereotypes label
	 */
	public PapyrusWrappingLabel getStereotypesLabel();

}
