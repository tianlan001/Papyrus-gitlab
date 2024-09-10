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
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 472034
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;

/**
 * Label Helper for Enumeration Literal. It is used to compute the Label of the Enumeration Literal EditPart.
 * 
 * @author Céline JANSSENS
 *
 */
public class EnumerationLiteralLabelHelper extends StereotypedElementLabelHelper {

	/**
	 * singleton instance
	 */
	private static EnumerationLiteralLabelHelper labelHelper;

	/**
	 * Returns the singleton instance of this class
	 *
	 * @return the singleton instance.
	 */
	public static EnumerationLiteralLabelHelper getInstance() {
		if (null == labelHelper) {
			labelHelper = new EnumerationLiteralLabelHelper();
		}
		return labelHelper;
	}

	/** Map for masks */
	protected final Map<String, String> masks = new HashMap<String, String>();

	/**
	 * Creates a new Operation label helper.
	 */
	protected EnumerationLiteralLabelHelper() {
		// initialize the map
		masks.put(ICustomAppearance.DISP_VISIBILITY, "Visibility");
		masks.put(ICustomAppearance.DISP_NAME, "Name");
	}

	/**
	 * Computes the label that should be displayed by the figure managed by this
	 * edit part.
	 *
	 * @param editPart
	 *            the edit part that controls the {@link Operation} to be
	 *            displayed
	 * @return the label corresponding to the specific display of the property
	 *         ("default" display given by preferences or specific display given
	 *         by eAnnotation).
	 */
	@Override
	protected String elementLabel(GraphicalEditPart editPart) {
		NamedElement namedElement = getUMLElement(editPart);
		return namedElement == null ? "" : UMLLabelInternationalization.getInstance().getLabel(namedElement);
	}

	/**
	 * Returns the makp of masks used to display an {@link Operation}
	 *
	 * @return the {@link Map} of masks used to display a {@link Operation}
	 */
	public Map<String, String> getMasks() {
		return masks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumerationLiteral getUMLElement(GraphicalEditPart editPart) {
		return (EnumerationLiteral) ((View) editPart.getModel()).getElement();
	}

}
