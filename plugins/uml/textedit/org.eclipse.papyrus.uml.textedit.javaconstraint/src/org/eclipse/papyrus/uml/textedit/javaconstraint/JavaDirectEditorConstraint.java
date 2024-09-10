/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.textedit.javaconstraint;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Constraint for Direct Editor of Java {@link Constraint}.
 * 
 * @author Gabriel Pascual
 *
 */
public class JavaDirectEditorConstraint implements IDirectEditorConstraint {

	/** The Constant JAVA_CONSTRAINT_IDENTIFIER. */
	private static final String JAVA_CONSTRAINT_IDENTIFIER = "JAVA";

	/** The Constant CONSTRAINT_LABEL. */
	private static final String CONSTRAINT_LABEL = "Java Language constraint";

	/**
	 * Constructor.o
	 *
	 */
	public JavaDirectEditorConstraint() {
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint#appliesTo(java.lang.Object)
	 *
	 * @param selection
	 * @return
	 */
	public boolean appliesTo(Object selection) {
		boolean appliedConstraint = false;
		EObject resolveEobject = EMFHelper.getEObject(selection);
		if (resolveEobject instanceof Constraint) {
			Constraint selectedConstraint = (Constraint) resolveEobject;
			ValueSpecification constraintSpecification = selectedConstraint.getSpecification();

			if (constraintSpecification instanceof OpaqueExpression) {
				appliedConstraint = (((OpaqueExpression) constraintSpecification).getBodies().isEmpty() || ((OpaqueExpression) constraintSpecification).getLanguages().contains(JAVA_CONSTRAINT_IDENTIFIER));
			} else {
				appliedConstraint = constraintSpecification instanceof LiteralString;
			}

		}

		return appliedConstraint;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint#getLabel()
	 *
	 * @return
	 */
	public String getLabel() {
		return CONSTRAINT_LABEL;
	}
}
