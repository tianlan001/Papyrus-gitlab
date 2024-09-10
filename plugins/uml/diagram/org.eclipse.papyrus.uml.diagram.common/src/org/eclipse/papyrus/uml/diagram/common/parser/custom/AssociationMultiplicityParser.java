/****************************************************************************
 * Copyright (c) 2009 Atos Origin.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Thibault Landre (Atos Origin) - Initial API and implementation
 *
 ****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.parser.custom;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.uml.tools.utils.MultiplicityElementUtil;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A specific parser to manage association end multiplicity.
 *
 * @author tlandre
 */
public class AssociationMultiplicityParser extends AbstractAssociationEndParser {

	/**
	 * Constructor
	 *
	 * @param memberEndIndex
	 *            the position of the attribute end.
	 */
	public AssociationMultiplicityParser(int memberEndIndex) {
		super(memberEndIndex);
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		Property property = doAdapt(element);
		if (property != null) {
			return MultiplicityElementUtil.formatMultiplicity(property);
		}
		return "";
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {

		boolean isAffectingEvent = false;

		EStructuralFeature feature = getEStructuralFeature(event);

		if (UMLPackage.eINSTANCE.getMultiplicityElement_Lower().equals(feature) || UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue().equals(feature) || UMLPackage.eINSTANCE.getMultiplicityElement_Lower().equals(feature)
				|| UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue().equals(feature) || UMLPackage.eINSTANCE.getLiteralInteger_Value().equals(feature) || UMLPackage.eINSTANCE.getLiteralUnlimitedNatural_Value().equals(feature)) {
			isAffectingEvent = true;
		}
		return isAffectingEvent;
	}

}
