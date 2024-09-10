/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  
 *    Radek Dvorak (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - refactoring
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.gmf.internal.validate.IDefElementProvider.StringValProvider;
import org.eclipse.papyrus.gmf.internal.validate.IDefElementProvider.TypeProvider;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IModelExpression;

class ConstraintDef extends ValueSpecDef {
	
	private static final TypeProvider BOOLEAN_TYPE = 
		new TypeProvider() {
			public IStatus getStatus() {			
				return Status.OK_STATUS;
			}
			public EClassifier getType(EObject context) {						
				return EcorePackage.eINSTANCE.getEBooleanObject();
			}
			public boolean isAssignable(EObject context, IModelExpression expression) {
				return expression.isAssignableTo(EcorePackage.eINSTANCE.getEBooleanObject());
			}

			public String toString() {			
				return EcorePackage.eINSTANCE.getEBooleanObject().getName();
			}
		};
					
	public ConstraintDef(StringValProvider body, StringValProvider lang) {
		super(body, lang, BOOLEAN_TYPE);
	}
	
	public String getLabel() {
		return Messages.def_ConstraintDefLabel;
	}	
	
	public String toString() {
		return "constraint: " + super.toString(); //$NON-NLS-1$
	}
}
