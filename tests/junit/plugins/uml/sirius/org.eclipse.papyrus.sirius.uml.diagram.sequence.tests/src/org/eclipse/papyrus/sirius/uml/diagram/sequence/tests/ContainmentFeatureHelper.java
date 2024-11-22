/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This class helps to find the containment feature to use with a specified type.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class ContainmentFeatureHelper {

	/**
	 * Get the containment feature to use with the specified type.
	 * 
	 * @param elementToContainType
	 *            the type of the element to add or remove, using its containment feature.
	 * @return the containment feature to use with the specified type.
	 */
	public static EReference getContainmentFeature(Class<? extends Element> elementToContainType) {
		EReference result;
		if (elementToContainType == Constraint.class) {
			result = UMLPackage.eINSTANCE.getNamespace_OwnedRule();
		} else if (elementToContainType == Comment.class) {
			result = UMLPackage.eINSTANCE.getElement_OwnedComment();
		} else if (elementToContainType == Lifeline.class) {
			result = UMLPackage.eINSTANCE.getInteraction_Lifeline();
		} else if (InteractionFragment.class.isAssignableFrom(elementToContainType)) {
			result = UMLPackage.eINSTANCE.getInteraction_Fragment();
		} else {
			result = UMLPackage.eINSTANCE.getPackage_PackagedElement();
		}
		return result;
	}

}
