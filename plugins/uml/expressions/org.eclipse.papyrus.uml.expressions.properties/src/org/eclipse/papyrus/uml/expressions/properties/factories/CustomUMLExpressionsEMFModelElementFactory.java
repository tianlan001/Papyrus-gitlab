/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/


package org.eclipse.papyrus.uml.expressions.properties.factories;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;
import org.eclipse.papyrus.uml.expressions.properties.Activator;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfExpressionImpl;

/**
 * Factory used to provide a custom model element for UML Expressions.
 * Its allows us to define precisely the label provider and content provider to use to edit the feature of the UML Expressions.
 *
 */
public class CustomUMLExpressionsEMFModelElementFactory extends EMFModelElementFactory {

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory#doCreateFromSource(java.lang.Object, org.eclipse.papyrus.infra.properties.contexts.DataContextElement)
	 *
	 * @param sourceElement
	 * @param context
	 * @return
	 */
	@Override
	protected EMFModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		final EObject source = EMFHelper.getEObject(sourceElement);
		if (null == source) {
			Activator.log.warn("Unable to resolve the selected element to an EObject"); //$NON-NLS-1$
			return null;
		}
		if (sourceElement instanceof IsKindOfExpression || sourceElement instanceof IsTypeOfExpressionImpl) {
			return new UMLExpressionsModelElement((EObject) sourceElement, EMFHelper.resolveEditingDomain(sourceElement));
		}
		return super.doCreateFromSource(sourceElement, context);
	}
}