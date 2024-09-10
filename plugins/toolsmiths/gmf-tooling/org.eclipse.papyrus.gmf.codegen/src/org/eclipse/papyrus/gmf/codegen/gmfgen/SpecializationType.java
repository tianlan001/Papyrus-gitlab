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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Specialization Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Specialization of metamodel type
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.SpecializationType#getMetamodelType <em>Metamodel Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.SpecializationType#getEditHelperAdviceClassName <em>Edit Helper Advice Class Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getSpecializationType()
 * @model
 * @generated
 */
public interface SpecializationType extends ElementType {

	public static final String EDIT_HELPER_ADVICE_SUFFIX = "EditHelperAdvice"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Metamodel Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodel Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metamodel Type</em>' reference.
	 * @see #setMetamodelType(MetamodelType)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getSpecializationType_MetamodelType()
	 * @model
	 * @generated
	 */
	MetamodelType getMetamodelType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.SpecializationType#getMetamodelType <em>Metamodel Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metamodel Type</em>' reference.
	 * @see #getMetamodelType()
	 * @generated
	 */
	void setMetamodelType(MetamodelType value);

	/**
	 * Returns the value of the '<em><b>Edit Helper Advice Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Helper Advice Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Helper Advice Class Name</em>' attribute.
	 * @see #setEditHelperAdviceClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getSpecializationType_EditHelperAdviceClassName()
	 * @model
	 * @generated
	 */
	String getEditHelperAdviceClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.SpecializationType#getEditHelperAdviceClassName <em>Edit Helper Advice Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Helper Advice Class Name</em>' attribute.
	 * @see #getEditHelperAdviceClassName()
	 * @generated
	 */
	void setEditHelperAdviceClassName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	GenClass getMetamodelClass();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getEditHelperAdviceQualifiedClassName();

} // SpecializationType
