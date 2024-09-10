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

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Child Side Affixed Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildSideAffixedNode#getPreferredSideName <em>Preferred Side Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildSideAffixedNode#getLocatorClassName <em>Locator Class Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenChildSideAffixedNode()
 * @model
 * @generated
 */
public interface GenChildSideAffixedNode extends GenChildNode {
	/**
	 * Returns the value of the '<em><b>Preferred Side Name</b></em>' attribute.
	 * The default value is <code>"NONE"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preferred Side Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preferred Side Name</em>' attribute.
	 * @see #setPreferredSideName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenChildSideAffixedNode_PreferredSideName()
	 * @model default="NONE"
	 * @generated
	 */
	String getPreferredSideName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildSideAffixedNode#getPreferredSideName <em>Preferred Side Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preferred Side Name</em>' attribute.
	 * @see #getPreferredSideName()
	 * @generated
	 */
	void setPreferredSideName(String value);

	/**
	 * Returns the value of the '<em><b>Locator Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from SpecificLocator
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Locator Class Name</em>' attribute.
	 * @see #setLocatorClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenChildSideAffixedNode_LocatorClassName()
	 * @model
	 * @generated
	 */
	String getLocatorClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildSideAffixedNode#getLocatorClassName <em>Locator Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locator Class Name</em>' attribute.
	 * @see #getLocatorClassName()
	 * @generated
	 */
	void setLocatorClassName(String value);

} // GenChildSideAffixedNode
