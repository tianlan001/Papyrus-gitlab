/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 563983
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.editors.factory;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.emf.ui.editor.factories.CustomExtendedDialogCellEditor;
import org.eclipse.papyrus.uml.expressions.edit.internal.converters.StereotypePropertyDisplayConverter;
import org.eclipse.papyrus.uml.expressions.edit.internal.messages.Messages;
import org.eclipse.papyrus.uml.expressions.edit.internal.providers.StereotypePropertyTreeContentProvider;
import org.eclipse.papyrus.uml.expressions.edit.internal.sections.duplicated.AbstractUMLElementDialogEditorFactory;
import org.eclipse.papyrus.uml.expressions.edit.internal.validators.SinglePropertyAttributeValidator;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage;

/**
 * EditorFactory for Stereotype Property typed with a PrimitiveType
 */
public class SingleStereotypeAttributeEditorFactory extends AbstractUMLElementDialogEditorFactory {

	/**
	 * The URI of this editor
	 */
	private static final URI SELF_URI = URI.createURI("http://www.eclipse.org/papyrus/umlExpressions/StereotypeAttributeEqualityExpression/AttributeName"); //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public SingleStereotypeAttributeEditorFactory() {
		super(SELF_URI, UMLExpressionsPackage.eINSTANCE.getSingleStereotypeAttributeEqualityExpression_PropertyName());
	}

	/**
	 * @see org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.AbstractEStructuralFeatureDialogEditorFactory#configureCellEditor(Object,
	 *      org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.CustomExtendedDialogCellEditor)
	 *
	 * @param editedObject
	 * @param cellEditor
	 */
	@Override
	protected void configureCellEditor(final Object editedObject, final CustomExtendedDialogCellEditor cellEditor) {
		super.configureCellEditor(editedObject, cellEditor);
		cellEditor.setDialogMessage(Messages.SingleStereotypeAttributeEditorFactory_SelectAStereotypePropertyTypedWithAPrimitiveTypeOrEnumeration);
		cellEditor.setContentProvider(new StereotypePropertyTreeContentProvider());
		cellEditor.setDisplayConverter(new StereotypePropertyDisplayConverter());
		cellEditor.setSelectionStatusValidator(new SinglePropertyAttributeValidator());
	}
}
