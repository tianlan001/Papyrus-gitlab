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
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContext;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenRuleTarget;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Auditable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Target suitable for auditing
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditable#getContextSelector <em>Context Selector</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditable()
 * @model abstract="true"
 * @generated
 */
public interface GenAuditable extends GenRuleTarget {
	/**
	 * Returns the value of the '<em><b>Context Selector</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContext#getRuleTargets <em>Rule Targets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * To apply audit to this target, we need to select appropriate input, and here's selector that helps with that
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Context Selector</em>' reference.
	 * @see #setContextSelector(GenAuditContext)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditable_ContextSelector()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContext#getRuleTargets
	 * @model opposite="ruleTargets"
	 * @generated
	 */
	GenAuditContext getContextSelector();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditable#getContextSelector <em>Context Selector</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context Selector</em>' reference.
	 * @see #getContextSelector()
	 * @generated
	 */
	void setContextSelector(GenAuditContext value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Gets the validation target class in terms of EMFT validation framework.
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	GenClass getTargetClass();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Consists of ecore meta-model only package names and target class simple name
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getTargetClassModelQualifiedName();

} // GenAuditable
