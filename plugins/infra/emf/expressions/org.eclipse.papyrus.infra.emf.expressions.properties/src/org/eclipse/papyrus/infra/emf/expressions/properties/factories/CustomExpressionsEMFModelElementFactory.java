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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.expressions.properties.factories;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.OrExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression;
import org.eclipse.papyrus.infra.emf.expressions.properties.Activator;
import org.eclipse.papyrus.infra.emf.expressions.properties.modelelements.AndExpressionModelElement;
import org.eclipse.papyrus.infra.emf.expressions.properties.modelelements.NotExpressionModelElement;
import org.eclipse.papyrus.infra.emf.expressions.properties.modelelements.OrExpressionModelElement;
import org.eclipse.papyrus.infra.emf.expressions.properties.modelelements.ReferenceBooleanEObjectExpressionModelElement;
import org.eclipse.papyrus.infra.emf.expressions.properties.modelelements.SingleEAttributeValueEqualityExpressionModelElement;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;

/**
 * Custom Factory for EMF Expressions
 *
 */
public class CustomExpressionsEMFModelElementFactory extends EMFModelElementFactory {

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
		if (sourceElement instanceof ReferenceBooleanExpression) {
			return new ReferenceBooleanEObjectExpressionModelElement((EObject) sourceElement, EMFHelper.resolveEditingDomain(sourceElement));
		}
		if (sourceElement instanceof AndExpression) {
			return new AndExpressionModelElement((EObject) sourceElement, EMFHelper.resolveEditingDomain(sourceElement));
		}
		if (sourceElement instanceof OrExpression) {
			return new OrExpressionModelElement((EObject) sourceElement, EMFHelper.resolveEditingDomain(sourceElement));
		}
		if (sourceElement instanceof NotExpression) {
			return new NotExpressionModelElement((EObject) sourceElement, EMFHelper.resolveEditingDomain(sourceElement));
		}
		if (sourceElement instanceof SingleEAttributeValueEqualityExpression) {
			return new SingleEAttributeValueEqualityExpressionModelElement((EObject) sourceElement, EMFHelper.resolveEditingDomain(sourceElement));
		}

		return super.doCreateFromSource(sourceElement, context);
	}
}
