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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.clazz.custom.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractAppliedStereotypeDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.helper.EnumerationLiteralLabelHelper;
import org.eclipse.uml2.uml.EnumerationLiteral;

/**
 * This Policy is to be used for Enumeration Literal Edit Part to manage the stereotype display
 *
 * @author Céline JANSSENS
 *
 */
public class AppliedStereotypeEnumerationLiteralDisplayEditPolicy extends AbstractAppliedStereotypeDisplayEditPolicy {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EnumerationLiteral getUMLElement() {
		EObject element = super.getUMLElement();
		EnumerationLiteral literal = null;
		if (element instanceof EnumerationLiteral) {
			literal = (EnumerationLiteral) element;
		}
		return literal;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refreshDisplay() {
		// calls the helper for this edit Part
		EnumerationLiteralLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) getHost());
	}


}
