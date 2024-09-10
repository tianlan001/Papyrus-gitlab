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

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * This validator extends the checker for basic EObject constraints with validation of OCL constraint annotation.
 * 
 * @author dvorak
 */
public class AnnotatedOclValidator extends AbstractValidator implements EValidator {

	public AnnotatedOclValidator() {
	}

	public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate(eObject.eClass(), eObject, diagnostics, context);
	}

	public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (eObject instanceof EAnnotation) {
			return handleEAnnotation((EAnnotation) eObject, diagnostics, context);
		} else if (eClass.getEPackage() != EcorePackage.eINSTANCE) {
			return handleMetaModel(eClass, eObject, diagnostics, context);
		}

		return true;
	}

	private boolean handleEAnnotation(EAnnotation annotation, DiagnosticChain diagnostics, Map<Object, Object> context) {
		ExternModelImport importer = ExternModelImport.getImporter(context);				
		ExpressionCache.Validator validator = ExpressionCache.get(annotation, diagnostics, importer);
		if (validator == null) {
			return true;
		}
		return validator.checkConstraints(diagnostics);
	}


	private boolean handleMetaModel(EClass eClass, EObject modelElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		ExpressionCache cacheAccess = (ExpressionCache) context.get(ExpressionCache.class);
		if (cacheAccess == null) {
			cacheAccess = new ExpressionCache();
			context.put(ExpressionCache.class, cacheAccess);
		}

		// initializer EPackages cross-referenced from the validated model 
		// to be visible in EMFT ocl environment lookup 
		ExternModelImport importer = ExternModelImport.getImporter(context);				

		ExpressionCache.Validator allExpressions = cacheAccess.get(eClass, diagnostics, importer);
		if (allExpressions == null) {
			return true; // no constraints, valid
		}
		return allExpressions.validate(modelElement, diagnostics, getOptions(context));
	}
}
