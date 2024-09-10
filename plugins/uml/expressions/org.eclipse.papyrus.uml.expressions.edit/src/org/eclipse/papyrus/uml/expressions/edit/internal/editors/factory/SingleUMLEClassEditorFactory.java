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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.emf.ui.editor.factories.AbstractEcoreEReferenceDialogEditorFactory;
import org.eclipse.papyrus.emf.ui.editor.factories.CustomExtendedDialogCellEditor;
import org.eclipse.papyrus.emf.ui.validators.SingleEClassValidator;
import org.eclipse.papyrus.uml.expressions.edit.internal.providers.EClassTreeContentProvider;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * EditorFactory to select a UMLEClass
 */
public class SingleUMLEClassEditorFactory extends AbstractEcoreEReferenceDialogEditorFactory {

	/**
	 * This editor is registered with this URI with the extension point org.eclipse.emf.edit.propertyEditorFactories.
	 * This editor is also registered with this URI in an EAnnotation in the ecore model
	 */
	private static final URI SELF_URI = URI.createURI("http://www.eclipse.org/papyrus/umlExpressions/UMLEClassExpression/UMLEClass"); //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param propertyEditorFactoryURI
	 */
	public SingleUMLEClassEditorFactory() {
		super(SELF_URI, UMLExpressionsPackage.eINSTANCE.getAbstractUMLEClassExpression_UmlEClass());
	}


	/**
	 * @param editedObject
	 * @param cellEditor
	 * @see org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.AbstractEStructuralFeatureDialogEditorFactory#configureCellEditor(Object,
	 *      org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.CustomExtendedDialogCellEditor)
	 */
	@Override
	protected void configureCellEditor(final Object editedObject, final CustomExtendedDialogCellEditor cellEditor) {
		super.configureCellEditor(editedObject, cellEditor);
		cellEditor.setContentProvider(new EClassTreeContentProvider());
		cellEditor.setSelectionStatusValidator(new SingleEClassValidator());
	}

	/**
	 * @see org.eclipse.papyrus.emf.ui.editor.factories.AbstractEcoreEReferenceDialogEditorFactory#getEditedMetamodelEPackage(org.eclipse.emf.ecore.EObject)
	 *
	 * @param editedObject
	 * @return
	 */
	@Override
	protected Collection<EPackage> getEditedMetamodelEPackage(final EObject editedObject) {
		final Collection<EPackage> packages = new ArrayList<>();
		packages.add(UMLFactory.eINSTANCE.getEPackage());
		return packages;
	}
}
