/**
 * Copyright (c) 2006, 2015, 2020, 2021 Borland Software Corporation, CEA LIST, ARTAL
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Borland - Initial API and implementation for code duplicated from gmf tooling repository
 *   CEA LIST - Initial API and implementation for code from Papyrus gmfgenextension
 *   Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Refresh Hook</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This class is used to refresh the figure by taking in account a property of domain element (in the case of Ecore, it can be an Efeature or EReference)
 * The refreshCondtion describe the needed condition to launch the refreshAction on the figure.  these two properties can be code lines.
 * 
 * For instance we would like to display active class when the efeature class is "active". Or display composite kind of the association when the kind of the property end is "composite".
 * Bug 569174 : from PropertyRefreshHook
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.RefreshHook#getRefreshCondition <em>Refresh Condition</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.RefreshHook#getRefreshAction <em>Refresh Action</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getRefreshHook()
 * @model
 * @generated
 */
public interface RefreshHook extends EObject {
	/**
	 * Returns the value of the '<em><b>Refresh Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refresh Condition</em>' attribute.
	 * @see #setRefreshCondition(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getRefreshHook_RefreshCondition()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getRefreshCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.RefreshHook#getRefreshCondition <em>Refresh Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refresh Condition</em>' attribute.
	 * @see #getRefreshCondition()
	 * @generated
	 */
	void setRefreshCondition(String value);

	/**
	 * Returns the value of the '<em><b>Refresh Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refresh Action</em>' attribute.
	 * @see #setRefreshAction(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getRefreshHook_RefreshAction()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getRefreshAction();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.RefreshHook#getRefreshAction <em>Refresh Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refresh Action</em>' attribute.
	 * @see #getRefreshAction()
	 * @generated
	 */
	void setRefreshAction(String value);

} // RefreshHook
