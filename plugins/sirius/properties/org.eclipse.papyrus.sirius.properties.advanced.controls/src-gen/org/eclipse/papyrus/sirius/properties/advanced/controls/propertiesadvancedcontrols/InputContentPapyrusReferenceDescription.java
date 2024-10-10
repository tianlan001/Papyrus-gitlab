/**
 * Copyright (c) 2023 CEA LIST, Obeo
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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Content Papyrus Reference Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription#getInputContentExpression <em>Input Content Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getInputContentPapyrusReferenceDescription()
 * @model
 * @generated
 */
public interface InputContentPapyrusReferenceDescription extends ExtEditableReferenceDescription {
	/**
	 * Returns the value of the '<em><b>Input Content Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Content Expression</em>' attribute.
	 * @see #setInputContentExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getInputContentPapyrusReferenceDescription_InputContentExpression()
	 * @model dataType="org.eclipse.sirius.viewpoint.description.InterpretedExpression"
	 * @generated
	 */
	String getInputContentExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.InputContentPapyrusReferenceDescription#getInputContentExpression <em>Input Content Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input Content Expression</em>' attribute.
	 * @see #getInputContentExpression()
	 * @generated
	 */
	void setInputContentExpression(String value);

} // InputContentPapyrusReferenceDescription
