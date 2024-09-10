/**
 * Copyright (c) 2014 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.infra.types;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metamodel Type Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration#getEClass <em>EClass</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration#getEditHelperClassName <em>Edit Helper Class Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getMetamodelTypeConfiguration()
 * @model
 * @generated
 */
public interface MetamodelTypeConfiguration extends ElementTypeConfiguration {
	/**
	 * Returns the value of the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EClass</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EClass</em>' reference.
	 * @see #setEClass(EClass)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getMetamodelTypeConfiguration_EClass()
	 * @model
	 * @generated
	 */
	EClass getEClass();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration#getEClass <em>EClass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EClass</em>' reference.
	 * @see #getEClass()
	 * @generated
	 */
	void setEClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Edit Helper Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Helper Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Helper Class Name</em>' attribute.
	 * @see #setEditHelperClassName(String)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getMetamodelTypeConfiguration_EditHelperClassName()
	 * @model
	 * @generated
	 */
	String getEditHelperClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration#getEditHelperClassName <em>Edit Helper Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Helper Class Name</em>' attribute.
	 * @see #getEditHelperClassName()
	 * @generated
	 */
	void setEditHelperClassName(String value);

} // MetamodelTypeConfiguration
