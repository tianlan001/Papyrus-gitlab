/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 *  All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols;

import org.eclipse.eef.EEFContainerDescription;
import org.eclipse.eef.EEFControlDescription;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EEF Stereotype Application Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription#getHelpExpression <em>Help Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage#getEEFStereotypeApplicationDescription()
 * @model
 * @generated
 */
public interface EEFStereotypeApplicationDescription extends EEFControlDescription, EEFContainerDescription {
	/**
	 * Returns the value of the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The label of the Widget visible by the end-users.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Label Expression</em>' attribute.
	 * @see #setLabelExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage#getEEFStereotypeApplicationDescription_LabelExpression()
	 * @model
	 * @generated
	 */
	String getLabelExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription#getLabelExpression <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Expression</em>' attribute.
	 * @see #getLabelExpression()
	 * @generated
	 */
	void setLabelExpression(String value);

	/**
	 * Returns the value of the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The tooltip of the help icon visible by the end-users
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Help Expression</em>' attribute.
	 * @see #setHelpExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage#getEEFStereotypeApplicationDescription_HelpExpression()
	 * @model
	 * @generated
	 */
	String getHelpExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription#getHelpExpression <em>Help Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help Expression</em>' attribute.
	 * @see #getHelpExpression()
	 * @generated
	 */
	void setHelpExpression(String value);

} // EEFStereotypeApplicationDescription
