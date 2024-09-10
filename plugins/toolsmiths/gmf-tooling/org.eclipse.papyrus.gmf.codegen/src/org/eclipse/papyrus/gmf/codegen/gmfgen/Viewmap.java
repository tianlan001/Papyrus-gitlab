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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.gmf.codegen.gmfgen.Attributes;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Viewmap</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap#getRequiredPluginIDs <em>Required Plugin IDs</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap#getLayoutType <em>Layout Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getViewmap()
 * @model abstract="true"
 * @generated
 */
public interface Viewmap extends EObject {
	/**
	 * Returns the value of the '<em><b>Required Plugin IDs</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Plugin IDs</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Plugin IDs</em>' attribute list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getViewmap_RequiredPluginIDs()
	 * @model
	 * @generated
	 */
	EList<String> getRequiredPluginIDs();

	/**
	 * Returns the value of the '<em><b>Layout Type</b></em>' attribute.
	 * The default value is <code>"UNKNOWN"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Layout Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layout Type</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType
	 * @see #setLayoutType(ViewmapLayoutType)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getViewmap_LayoutType()
	 * @model default="UNKNOWN"
	 * @generated
	 */
	ViewmapLayoutType getLayoutType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap#getLayoutType <em>Layout Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layout Type</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType
	 * @see #getLayoutType()
	 * @generated
	 */
	void setLayoutType(ViewmapLayoutType value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.Attributes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getViewmap_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attributes> getAttributes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns first attributes instance of specified class, if any
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	Attributes find(Class attributesClass);

} // Viewmap
