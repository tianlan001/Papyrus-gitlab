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
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.editors.factory;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.emf.ui.editor.factories.CustomExtendedDialogCellEditor;
import org.eclipse.papyrus.uml.expressions.edit.internal.converters.ProfileURIDisplayConverter;
import org.eclipse.papyrus.uml.expressions.edit.internal.messages.Messages;
import org.eclipse.papyrus.uml.expressions.edit.internal.providers.ProfileTreeContentProvider;
import org.eclipse.papyrus.uml.expressions.edit.internal.providers.UMLProfileURILabelProvider;
import org.eclipse.papyrus.uml.expressions.edit.internal.sections.duplicated.AbstractUMLElementDialogEditorFactory;
import org.eclipse.papyrus.uml.expressions.edit.internal.validators.SingleRootProfileValidator;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage;

/**
 * EditorFactory for ProfileURI field
 *
 */
public class SingleProfileURIEditorFactory extends AbstractUMLElementDialogEditorFactory {

	/**
	 * The URI of this editor
	 */
	private static final URI SELF_URI = URI.createURI("http://www.eclipse.org/papyrus/umlExpressions/StereotypeExpression/ProfileURI"); //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 * @param feature
	 *            the edited feature
	 */
	public SingleProfileURIEditorFactory() {
		super(SELF_URI, UMLExpressionsPackage.eINSTANCE.getAbstractStereotypeExpression_ProfileURI());
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
		cellEditor.setDialogMessage(Messages.SingleProfileURIEditorFactory_SelectARootProfileURI);
		cellEditor.setContentProvider(new ProfileTreeContentProvider());
		cellEditor.setDisplayConverter(new ProfileURIDisplayConverter());
		cellEditor.setSelectionStatusValidator(new SingleRootProfileValidator());
	}

	/**
	 * @see org.eclipse.papyrus.uml.expressions.edit.internal.sections.duplicated.AbstractUMLElementDialogEditorFactory#getOrCreateLabelProvider()
	 *
	 * @return
	 */
	@Override
	public ILabelProvider getOrCreateLabelProvider() {
		if (null == this.labelProvider) {
			this.labelProvider = new UMLProfileURILabelProvider();
		}
		return this.labelProvider;
	}
}
