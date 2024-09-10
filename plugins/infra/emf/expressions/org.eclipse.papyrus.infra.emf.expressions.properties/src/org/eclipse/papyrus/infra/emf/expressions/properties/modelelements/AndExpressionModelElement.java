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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression;
import org.eclipse.papyrus.infra.emf.expressions.properties.provider.ExpressionCatalogContentProvider;
import org.eclipse.papyrus.infra.emf.expressions.properties.utils.BooleanEObjectExpressionPropertyEditorFactory;
import org.eclipse.papyrus.infra.emf.expressions.properties.utils.ExpressionsConstants;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * ModelElement for {@link AndExpression}
 *
 */
public class AndExpressionModelElement extends EMFModelElement {

	/**
	 * Constructor.
	 *
	 * @param source
	 * @param domain
	 */
	public AndExpressionModelElement(EObject source, EditingDomain domain) {
		super(source, domain);
	}

	/**
	 * Constructor.
	 *
	 * @param source
	 */
	public AndExpressionModelElement(EObject source) {
		super(source);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getValueFactory(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public ReferenceValueFactory getValueFactory(final String propertyPath) {
		if (ExpressionsConstants.OWNED_EXPRESSIONS.equals(propertyPath)) {
			if (getDomain() instanceof AdapterFactoryEditingDomain) {
				return new BooleanEObjectExpressionPropertyEditorFactory((AdapterFactoryEditingDomain) getDomain(), (EReference) getFeature(propertyPath));
			}
		}
		return super.getValueFactory(propertyPath);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public IStaticContentProvider getContentProvider(final String propertyPath) {
		if (ExpressionsConstants.REFERENCED_EXPRESSIONS.equals(propertyPath)) {
			return new ExpressionCatalogContentProvider((EReference) getFeature(propertyPath));
		}
		return super.getContentProvider(propertyPath);
	}

}
