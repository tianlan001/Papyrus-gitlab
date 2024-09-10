/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomTemplateInput;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTemplateInvocationBase;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Fixed Inputs Template Invocation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFixedInputsTemplateInvocation#getFixedInputs <em>Fixed Inputs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFixedInputsTemplateInvocation()
 * @model
 * @generated
 */
public interface GenFixedInputsTemplateInvocation extends GenTemplateInvocationBase, GenCustomTemplateInput {
	/**
	 * Returns the value of the '<em><b>Fixed Inputs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Predefined set of input elements for template. 
	 * 		Due to reconciling it normally makes sense only for local links inside the same extension or 
	 * 		for extensions created by custom bridge extension (self.extension.fromCustomBridge = true)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Fixed Inputs</em>' reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFixedInputsTemplateInvocation_FixedInputs()
	 * @model
	 * @generated
	 */
	EList<EObject> getFixedInputs();

} // GenFixedInputsTemplateInvocation
