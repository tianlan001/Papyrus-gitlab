/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.helper;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.helper.StereotypedElementLabelHelper;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Helper for nested classifier labels displaying
 */
public class NestedStereotypeLabelHelper extends StereotypedElementLabelHelper {

	private static NestedStereotypeLabelHelper myHelper;

	/**
	 * {@inheritDoc}
	 */
	public static NestedStereotypeLabelHelper getInstance() {
		if (myHelper == null) {
			myHelper = new NestedStereotypeLabelHelper();
		}
		return myHelper;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.helper.StereotypedElementLabelHelper#elementLabel(org.eclipse.gef.GraphicalEditPart)
	 *
	 * @param editPart
	 * @return
	 */
	@Override
	protected String elementLabel(GraphicalEditPart editPart) {
		NamedElement namedElement = getUMLElement(editPart);
		return namedElement == null ? "" : UMLLabelInternationalization.getInstance().getLabel(namedElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NamedElement getUMLElement(GraphicalEditPart editPart) {
		if (editPart instanceof UMLCompartmentEditPart && editPart.getModel() instanceof View) {
			View view = (View) editPart.getModel();
			if (view.getElement() instanceof NamedElement) {
				return (NamedElement) view.getElement();
			}
		}
		return null;
	}
}