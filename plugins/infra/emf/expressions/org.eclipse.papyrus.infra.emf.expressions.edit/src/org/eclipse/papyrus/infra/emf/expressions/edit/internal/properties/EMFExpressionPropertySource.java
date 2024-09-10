/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.expressions.edit.internal.properties;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.papyrus.emf.ui.editor.factories.AbstractEStructuralFeatureDialogEditorFactory;
import org.eclipse.papyrus.emf.ui.properties.CustomPropertyDescriptor;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;
import org.eclipse.papyrus.infra.emf.expressions.edit.internal.editor.factories.MultiBooleanEObjectExpressionsReferenceEditorFactory;
import org.eclipse.papyrus.infra.emf.expressions.edit.internal.editor.factories.SingleBooleanEObjectExpressionReferenceEditorFactory;
import org.eclipse.papyrus.infra.emf.expressions.edit.internal.editor.factories.SingleEAttributeReferenceExpressionEditorFactory;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * The {@link PropertySource} used to provide a better edition way for some properties of the EMF Expressions
 */
public class EMFExpressionPropertySource extends PropertySource {

	/**
	 * Constructor.
	 *
	 * @param object
	 * @param itemPropertySource
	 */
	public EMFExpressionPropertySource(final Object object, final IItemPropertySource itemPropertySource) {
		super(object, itemPropertySource);
	}

	/**
	 * @see org.eclipse.emf.edit.ui.provider.PropertySource#createPropertyDescriptor(org.eclipse.emf.edit.provider.IItemPropertyDescriptor)
	 *
	 * @param itemPropertyDescriptor
	 * @return
	 */
	@Override
	protected IPropertyDescriptor createPropertyDescriptor(final IItemPropertyDescriptor itemPropertyDescriptor) {
		final EStructuralFeature f = (EStructuralFeature) itemPropertyDescriptor.getFeature(this.object);
		AbstractEStructuralFeatureDialogEditorFactory editorFactory = null;
		if (f == BooleanExpressionsPackage.eINSTANCE.getAbstractMultiBooleanEObjectExpressionsReferenceExpression_ReferencedExpressions()) {
			editorFactory = new MultiBooleanEObjectExpressionsReferenceEditorFactory();
		}
		if (f == BooleanExpressionsPackage.eINSTANCE.getAbstractSingleBooleanEObjectExpressionReferenceExpression_ReferencedExpression()) {
			editorFactory = new SingleBooleanEObjectExpressionReferenceEditorFactory();
		}

		if (f == BooleanExpressionsPackage.eINSTANCE.getSingleEAttributeValueEqualityExpression_EAttribute()) {
			editorFactory = new SingleEAttributeReferenceExpressionEditorFactory();
		}
		if (null != editorFactory) {
			return new CustomPropertyDescriptor(this.object, itemPropertyDescriptor, editorFactory);
		}
		return super.createPropertyDescriptor(itemPropertyDescriptor);
	}
}
