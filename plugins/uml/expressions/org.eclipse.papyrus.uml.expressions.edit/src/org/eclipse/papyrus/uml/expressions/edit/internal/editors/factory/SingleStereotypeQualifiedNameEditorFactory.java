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

package org.eclipse.papyrus.uml.expressions.edit.internal.editors.factory;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.emf.ui.editor.factories.CustomExtendedDialogCellEditor;
import org.eclipse.papyrus.uml.expressions.edit.internal.converters.StereotypeDisplayConverter;
import org.eclipse.papyrus.uml.expressions.edit.internal.providers.StereotypeTreeContentProvider;
import org.eclipse.papyrus.uml.expressions.edit.internal.sections.duplicated.AbstractUMLElementDialogEditorFactory;
import org.eclipse.papyrus.uml.expressions.edit.internal.validators.SingleStereotypeValidator;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage;

/**
 * EditorFactory to edit a Stereotype qualified name
 */
public class SingleStereotypeQualifiedNameEditorFactory extends AbstractUMLElementDialogEditorFactory {


	/**
	 * The URI of this editor
	 */
	private static final URI SELF_URI = URI.createURI("http://www.eclipse.org/papyrus/umlExpressions/StereotypeExpression/StereotypeQualifiedName"); //$NON-NLS-1$


	/**
	 * Constructor.
	 *
	 * @param propertyEditorFactoryURI
	 * @param editedFeature
	 */
	public SingleStereotypeQualifiedNameEditorFactory() {
		super(SELF_URI, UMLExpressionsPackage.eINSTANCE.getAbstractStereotypeExpression_StereotypeQualifiedName());
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
		cellEditor.setDialogMessage("Select one Stereotype");
		cellEditor.setContentProvider(new StereotypeTreeContentProvider());
		cellEditor.setDisplayConverter(new StereotypeDisplayConverter());
		cellEditor.setSelectionStatusValidator(new SingleStereotypeValidator());
	}
}
