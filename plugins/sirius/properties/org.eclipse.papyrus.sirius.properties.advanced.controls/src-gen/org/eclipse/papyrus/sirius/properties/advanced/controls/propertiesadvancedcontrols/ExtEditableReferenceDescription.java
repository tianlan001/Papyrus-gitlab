/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols;

import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.ExtReferenceDescription;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ext Editable Reference Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getRemoveExpression <em>Remove Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getCreateExpression <em>Create Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getBrowseExpression <em>Browse Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getExtEditableReferenceDescription()
 * @model
 * @generated
 */
public interface ExtEditableReferenceDescription extends ExtReferenceDescription {
	/**
	 * Returns the value of the '<em><b>Remove Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remove Expression</em>' attribute.
	 * @see #setRemoveExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getExtEditableReferenceDescription_RemoveExpression()
	 * @model dataType="org.eclipse.sirius.viewpoint.description.InterpretedExpression"
	 * @generated
	 */
	String getRemoveExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getRemoveExpression <em>Remove Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remove Expression</em>' attribute.
	 * @see #getRemoveExpression()
	 * @generated
	 */
	void setRemoveExpression(String value);

	/**
	 * Returns the value of the '<em><b>Create Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Expression</em>' attribute.
	 * @see #setCreateExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getExtEditableReferenceDescription_CreateExpression()
	 * @model dataType="org.eclipse.sirius.viewpoint.description.InterpretedExpression"
	 * @generated
	 */
	String getCreateExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getCreateExpression <em>Create Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Expression</em>' attribute.
	 * @see #getCreateExpression()
	 * @generated
	 */
	void setCreateExpression(String value);

	/**
	 * Returns the value of the '<em><b>Browse Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Browse Expression</em>' attribute.
	 * @see #setBrowseExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getExtEditableReferenceDescription_BrowseExpression()
	 * @model dataType="org.eclipse.sirius.viewpoint.description.InterpretedExpression"
	 * @generated
	 */
	String getBrowseExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription#getBrowseExpression <em>Browse Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Browse Expression</em>' attribute.
	 * @see #getBrowseExpression()
	 * @generated
	 */
	void setBrowseExpression(String value);

} // ExtEditableReferenceDescription
