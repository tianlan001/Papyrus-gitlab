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
 * $Id: GenDomainAttributeTarget.java,v 1.2 2006/04/27 10:10:37 radvorak Exp $
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Domain Attribute Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Represents value based target, useful for audit rules expression not capable of ecore meta-model access
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDomainAttributeTarget#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDomainAttributeTarget#isNullAsError <em>Null As Error</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDomainAttributeTarget()
 * @model
 * @generated
 */
public interface GenDomainAttributeTarget extends GenAuditable {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(GenFeature)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDomainAttributeTarget_Attribute()
	 * @model required="true"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='attribute.ecoreFeature.oclIsKindOf(ecore::EAttribute)' description='EAttribute element required for auditable domain attribute'"
	 * @generated
	 */
	GenFeature getAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDomainAttributeTarget#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(GenFeature value);

	/**
	 * Returns the value of the '<em><b>Null As Error</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates whether null value of the attribute is reported as audit failure or success
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Null As Error</em>' attribute.
	 * @see #setNullAsError(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDomainAttributeTarget_NullAsError()
	 * @model
	 * @generated
	 */
	boolean isNullAsError();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDomainAttributeTarget#isNullAsError <em>Null As Error</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Null As Error</em>' attribute.
	 * @see #isNullAsError()
	 * @generated
	 */
	void setNullAsError(boolean value);

} // GenDomainAttributeTarget
