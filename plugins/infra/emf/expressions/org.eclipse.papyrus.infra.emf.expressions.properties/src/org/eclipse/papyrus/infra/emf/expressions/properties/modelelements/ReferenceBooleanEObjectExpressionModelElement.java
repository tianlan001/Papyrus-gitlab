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

package org.eclipse.papyrus.infra.emf.expressions.properties.modelelements;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression;
import org.eclipse.papyrus.infra.emf.expressions.properties.provider.ExpressionCatalogContentProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * ModelElement for {@link ReferenceBooleanExpression}
 */
public class ReferenceBooleanEObjectExpressionModelElement extends EMFModelElement {

	/**
	 * Constructor.
	 *
	 * @param source
	 * @param domain
	 */
	public ReferenceBooleanEObjectExpressionModelElement(final EObject source, final EditingDomain domain) {
		super(source, domain);
	}

	/**
	 * Constructor.
	 *
	 * @param source
	 */
	public ReferenceBooleanEObjectExpressionModelElement(final EObject source) {
		super(source);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public IStaticContentProvider getContentProvider(final String propertyPath) {
		if (BooleanExpressionsPackage.eINSTANCE.getAbstractMultiBooleanEObjectExpressionsReferenceExpression_ReferencedExpressions().getName().equals(propertyPath)) {
			return new ExpressionCatalogContentProvider(BooleanExpressionsPackage.eINSTANCE.getAbstractMultiBooleanEObjectExpressionsReferenceExpression_ReferencedExpressions());
		}
		return super.getContentProvider(propertyPath);
	}

}
