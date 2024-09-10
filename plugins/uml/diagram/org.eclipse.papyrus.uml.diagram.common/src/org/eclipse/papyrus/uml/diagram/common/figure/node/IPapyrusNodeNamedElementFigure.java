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
 *  Mickael ADAM (ALL@TEC) mickael.adam@all4tec.net - bug 462448
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;

public interface IPapyrusNodeNamedElementFigure extends IPapyrusNodeUMLElementFigure {

	/**
	 * Sets the qualified name.
	 *
	 * @param qualifiedName
	 *            the qualified name
	 */
	public void setQualifiedName(String qualifiedName);

	/**
	 * return the label that contains the qualified name.
	 *
	 * @return the label that contains the qualified name
	 */
	public WrappingLabel getQualifiedNameLabel();

	/**
	 * Get the label containing the tagged value
	 *
	 * @return
	 */
	public WrappingLabel getTaggedLabel();

	/**
	 * Sets the depth.
	 *
	 * @param depth
	 *            the new depth
	 */
	public void setDepth(int depth);

	/**
	 * Gets the name label.
	 *
	 * @return the name label
	 */
	public WrappingLabel getNameLabel();

	/**
	 * display or not the icon associated to the label
	 *
	 * @param displayNameLabelIcon
	 */

	public void setNameLabelIcon(boolean displayNameLabelIcon);

	/**
	 * restore a label that represent the name of the element.
	 */
	public void restoreNameLabel();

	/** remove the label that represent the name **/
	public void removeNameLabel();

	/** remove the label that represent the stereotype **/
	public void removeStereotypeLabel();

	/**
	 * restore a label that represent the appliedStereotype of the element.
	 */
	public void restoreStereotypeLabel();

	/**
	 * restore a label that represent the tagged of the element.
	 */
	public void restoreTaggedLabel();


	/** remove the label that represent the tagged **/
	public void removeTaggedLabel();


}
