/*****************************************************************************
 * Copyright (c) 2019 CEA LIST.
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
package org.eclipse.papyrus.infra.emf.expressions.edit.internal.editor.factories;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.ui.contentproviders.EcoreContentProvider;
import org.eclipse.papyrus.emf.ui.converters.IdentityDisplayConverter;
import org.eclipse.papyrus.emf.ui.editor.factories.AbstractEStructuralFeatureDialogEditorFactory;
import org.eclipse.papyrus.emf.ui.editor.factories.CustomExtendedDialogCellEditor;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;
import org.eclipse.papyrus.infra.emf.expressions.catalog.ExpressionCatalogRegistry;
import org.eclipse.papyrus.infra.emf.expressions.edit.internal.validators.SingleBooleanExpressionValidator;

/**
 *
 * This editor allows to edit a single reference to an expression
 *
 */
public class SingleBooleanEObjectExpressionReferenceEditorFactory extends AbstractEStructuralFeatureDialogEditorFactory {

	/**
	 * The URI of this editor
	 */
	private static final URI SELF_URI = URI.createURI("http://www.eclipse.org/papyrus/expressions/SingleBooleanEObjectExpressionReferenceExpression/referencedExpression"); //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 */
	public SingleBooleanEObjectExpressionReferenceEditorFactory() {
		super(SELF_URI, BooleanExpressionsPackage.eINSTANCE.getAbstractSingleBooleanEObjectExpressionReferenceExpression_ReferencedExpression());
	}


	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.edit.editors.factories.duplicated.AbstractEStructuralFeatureDialogEditorFactory#configureCellEditor(java.lang.Object,
	 *      org.eclipse.papyrus.infra.emf.expressions.edit.editors.factories.duplicated.CustomExtendedDialogCellEditor)
	 *
	 * @param editedObject
	 * @param cellEditor
	 */
	@Override
	protected void configureCellEditor(Object editedObject, CustomExtendedDialogCellEditor cellEditor) {
		super.configureCellEditor(editedObject, cellEditor);
		cellEditor.setContentProvider(new EcoreContentProvider());
		cellEditor.setDisplayConverter(new IdentityDisplayConverter());
		cellEditor.setSelectionStatusValidator(new SingleBooleanExpressionValidator());

	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.edit.editors.factories.duplicated.AbstractEStructuralFeatureDialogEditorFactory#getDialogInput(org.eclipse.emf.ecore.EObject)
	 *
	 * @param editedElement
	 * @return
	 */
	@Override
	protected Collection<?> getDialogInput(final EObject editedElement) {
		return ExpressionCatalogRegistry.INSTANCE.getAllRegisteredCatalog();
	}

}
