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

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNodeLabel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen External Node Label</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Label attached to node
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel#getLocatorClassName <em>Locator Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel#getLabelVisibilityPreference <em>Label Visibility Preference</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenExternalNodeLabel()
 * @model
 * @generated
 */
public interface GenExternalNodeLabel extends GenNodeLabel {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from ExtendedGenView
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenExternalNodeLabel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Locator Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from SpecificLocatorExternalLabel
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Locator Class Name</em>' attribute.
	 * @see #setLocatorClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenExternalNodeLabel_LocatorClassName()
	 * @model
	 * @generated
	 */
	String getLocatorClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel#getLocatorClassName <em>Locator Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locator Class Name</em>' attribute.
	 * @see #getLocatorClassName()
	 * @generated
	 */
	void setLocatorClassName(String value);

	/**
	 * Returns the value of the '<em><b>Label Visibility Preference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from LabelVisibilityPreference
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Label Visibility Preference</em>' containment reference.
	 * @see #setLabelVisibilityPreference(GenFloatingLabel)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenExternalNodeLabel_LabelVisibilityPreference()
	 * @model containment="true"
	 * @generated
	 */
	GenFloatingLabel getLabelVisibilityPreference();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel#getLabelVisibilityPreference <em>Label Visibility Preference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Visibility Preference</em>' containment reference.
	 * @see #getLabelVisibilityPreference()
	 * @generated
	 */
	void setLabelVisibilityPreference(GenFloatingLabel value);
} // GenExternalNodeLabel
