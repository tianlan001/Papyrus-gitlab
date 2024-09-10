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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.emf.ui.editor.factories.AbtractSingleEAttributeReferenceEditorFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;

/**
 *
 * This editor allows to edit the EAttribute of an Expression
 *
 */
public class SingleEAttributeReferenceExpressionEditorFactory extends AbtractSingleEAttributeReferenceEditorFactory {

	/**
	 * The URI of this editor
	 */
	private static final URI SELF_URI = URI.createURI("http://www.eclipse.org/papyrus/expressions/singleEAttributeReference"); //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public SingleEAttributeReferenceExpressionEditorFactory() {
		super(SELF_URI, BooleanExpressionsPackage.eINSTANCE.getSingleEAttributeValueEqualityExpression_EAttribute());
	}

	/**
	 * @see org.eclipse.papyrus.emf.ui.editor.factories.AbstractEcoreEReferenceDialogEditorFactory#getEditedMetamodelEPackage(org.eclipse.emf.ecore.EObject)
	 *
	 * @param editedObject
	 * @return
	 */
	@Override
	protected Collection<EPackage> getEditedMetamodelEPackage(final EObject editedObject) {
		// Expression framework is reused in several context and never alone. So we are not able to know on which metamodel we are running.
		return null;
	}



}
