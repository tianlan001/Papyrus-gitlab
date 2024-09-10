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
package org.eclipse.papyrus.emf.ui.editor.factories;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.emf.ui.contentproviders.EcoreContentProvider;
import org.eclipse.papyrus.emf.ui.validators.SingleEAttributeValidator;

/**
 * Abstract factory used to reference an EAttribute
 */
public abstract class AbtractSingleEAttributeReferenceEditorFactory extends AbstractEcoreEReferenceDialogEditorFactory {

	/**
	 *
	 * Constructor.
	 *
	 */
	public AbtractSingleEAttributeReferenceEditorFactory(final URI uri, final EStructuralFeature feature) {
		super(uri, feature);
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
		cellEditor.setContentProvider(new EcoreContentProvider());
		cellEditor.setSelectionStatusValidator(new SingleEAttributeValidator());
	}


}
