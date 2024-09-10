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

package org.eclipse.papyrus.uml.expressions.edit.internal.sections;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.papyrus.emf.ui.editor.factories.AbstractEStructuralFeatureDialogEditorFactory;
import org.eclipse.papyrus.emf.ui.properties.CustomPropertyDescriptor;
import org.eclipse.papyrus.uml.expressions.edit.internal.editors.factory.SingleProfileURIEditorFactory;
import org.eclipse.papyrus.uml.expressions.edit.internal.editors.factory.SingleStereotypeAttributeEditorFactory;
import org.eclipse.papyrus.uml.expressions.edit.internal.editors.factory.SingleStereotypeQualifiedNameEditorFactory;
import org.eclipse.papyrus.uml.expressions.edit.internal.editors.factory.SingleUMLEClassEditorFactory;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * The {@link PropertySource} used to provide a better edition way for some properties of the UMLExpression metamodel
 */
public class UMLExpressionPropertySource extends PropertySource {

	/**
	 * Constructor.
	 *
	 * @param object
	 * @param itemPropertySource
	 */
	public UMLExpressionPropertySource(final Object object, final IItemPropertySource itemPropertySource) {
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
		// stereotype qualified name
		if (f == UMLExpressionsPackage.eINSTANCE.getAbstractStereotypeExpression_StereotypeQualifiedName()) {
			editorFactory = new SingleStereotypeQualifiedNameEditorFactory();

			// profile URI
		} else if (f == UMLExpressionsPackage.eINSTANCE.getAbstractStereotypeExpression_ProfileURI()) {
			editorFactory = new SingleProfileURIEditorFactory();

			// stereotype's property name
		} else if (f == UMLExpressionsPackage.eINSTANCE.getSingleStereotypeAttributeEqualityExpression_PropertyName()) {
			editorFactory = new SingleStereotypeAttributeEditorFactory();

			// UMLEClass
		} else if (f == UMLExpressionsPackage.eINSTANCE.getAbstractUMLEClassExpression_UmlEClass()) {
			editorFactory = new SingleUMLEClassEditorFactory();
		}


		if (null != editorFactory) {
			return new CustomPropertyDescriptor(this.object, itemPropertyDescriptor, editorFactory);
		}
		return super.createPropertyDescriptor(itemPropertyDescriptor);
	}
}
