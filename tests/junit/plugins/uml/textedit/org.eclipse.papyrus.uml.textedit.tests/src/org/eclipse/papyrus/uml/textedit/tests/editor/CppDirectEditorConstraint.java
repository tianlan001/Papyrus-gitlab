/*****************************************************************************
 * Copyright (c) 2018 CEA LIST.
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
 * 	Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.textedit.tests.editor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Assure that a C++ editor is used, if the constraint contains an opaque expression with "C++" as language
 */
public class CppDirectEditorConstraint implements IDirectEditorConstraint {

	private static final String CPP_LANGUAGE_CONSTRAINT = "C++ Language Constraint"; //$NON-NLS-1$

	private static final String CPP_LANGUAGE_BODY = "C++"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 */
	public CppDirectEditorConstraint() {
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint#getLabel()
	 *
	 * @return
	 */
	public String getLabel() {
		return CPP_LANGUAGE_CONSTRAINT;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint#appliesTo(java.lang.Object)
	 *
	 * @param selection
	 * @return
	 */
	public boolean appliesTo(Object selection) {
		boolean appliedConstraint = false;
		EObject resolvedEObject = EMFHelper.getEObject(selection);
		if (resolvedEObject instanceof Constraint) {
			Constraint selectedConstraint = (Constraint) resolvedEObject;
			appliedConstraint = checkValueSpecification(selectedConstraint.getSpecification());
		}
		else if (resolvedEObject instanceof ValueSpecification) {
			appliedConstraint = checkValueSpecification((ValueSpecification) resolvedEObject);
		}
		return appliedConstraint;
	}
	
	/**
	 * check whether a value specification complies with C++ editor
	 * @param specification
	 * @return true, if either opaque-expression (empty or C++ as language) or a literal string
	 */
	public boolean checkValueSpecification(ValueSpecification specification) {
		if (specification instanceof OpaqueExpression) {
			return ((OpaqueExpression) specification).getBodies().isEmpty() || ((OpaqueExpression) specification).getLanguages().contains(CPP_LANGUAGE_BODY);
		}
		else {
			return specification instanceof LiteralString;
		}
	}
}
