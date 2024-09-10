/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  Mickael ADAM (ALL@TEC) mickael.adam@all4tec.net - bug 462448
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.swt.graphics.Image;

/**
 * This a WrappingLabel that can display applied stereotypes
 */
public class QualifiedNameWrappingLabelFigure extends PapyrusWrappingLabel implements IPapyrusNodeNamedElementFigure {

	/** the depth of the qualified name **/
	private int depth = 0;


	@Override
	public PapyrusWrappingLabel getStereotypesLabel() {
		return null;
	}

	/**
	 * Calculate the partial qualified name with a specified depth.
	 *
	 * @param qualifiedName
	 *            the qualified name can return null
	 */
	public String getQualifiedName(String qualifiedName, int depth) {
		int n = -1;
		if (qualifiedName == null) {
			return null;
		}
		int i = 0;
		if (depth <= 0) {
			return qualifiedName;
		}

		while (i < depth) {
			if ((n = qualifiedName.indexOf("::", n + 1)) != -1) {
				i++;
			} else {
				return null;
			}
		}

		if (n == -1) {
			return qualifiedName;
		} else {
			return qualifiedName.substring(n + 2);
		}

	}

	@Override
	public void setQualifiedName(String qualifiedName) {
		String tmpQualifiedName = getQualifiedName(qualifiedName, depth);
		// two raisons to remove label!
		// null
		// or the qualified name is equal to 1
		if (qualifiedName == null || tmpQualifiedName == null || !tmpQualifiedName.contains("::")) { // Remove
			setText("");
			setTextWrap(true);
			return;
		}

		// we have to not display name.

		int i = tmpQualifiedName.lastIndexOf("::");
		if (i != -1) {
			tmpQualifiedName = tmpQualifiedName.substring(0, i);
		}
		this.setText("(" + tmpQualifiedName.trim() + ")");
		setTextWrap(true);

	}

	/**
	 * Sets the depth.
	 *
	 * @param depth
	 *            the new depth
	 */
	@Override
	public void setDepth(int depth) {
		this.depth = depth;
	}

	// unused Method
	@Override
	public void setStereotypePropertiesInBrace(String stereotypeProperties) {
	}

	@Override
	public void setStereotypePropertiesInCompartment(String stereotypeProperties) {
	}

	@Override
	public WrappingLabel getQualifiedNameLabel() {
		return null;
	}

	@Override
	public WrappingLabel getTaggedLabel() {
		return null;
	}

	@Override
	public void setStereotypeDisplay(String stereotypes, Image image) {
	}

	@Override
	public void setNameLabelIcon(boolean displayNameLabelIcon) {
	}

	@Override
	public WrappingLabel getNameLabel() {
		return null;
	}


	@Override
	public void restoreNameLabel() {


	}

	@Override
	public void removeNameLabel() {


	}

	@Override
	public void removeStereotypeLabel() {


	}

	@Override
	public void restoreStereotypeLabel() {


	}

	@Override
	public void restoreTaggedLabel() {


	}

	@Override
	public void removeTaggedLabel() {


	}

}
