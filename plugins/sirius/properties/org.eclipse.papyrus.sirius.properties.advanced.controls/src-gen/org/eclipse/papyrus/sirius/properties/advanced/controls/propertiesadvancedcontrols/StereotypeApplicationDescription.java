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

import org.eclipse.sirius.properties.ContainerDescription;
import org.eclipse.sirius.properties.WidgetDescription;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stereotype Application Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription#getHelpExpression <em>Help Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getStereotypeApplicationDescription()
 * @model
 * @generated
 */
public interface StereotypeApplicationDescription extends WidgetDescription, ContainerDescription {

	/**
	 * Returns the value of the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label Expression</em>' attribute.
	 * @see #setLabelExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getStereotypeApplicationDescription_LabelExpression()
	 * @model dataType="org.eclipse.sirius.viewpoint.description.InterpretedExpression"
	 * @generated
	 */
	String getLabelExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription#getLabelExpression <em>Label Expression</em>}' attribute.
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
	 * @return the value of the '<em>Help Expression</em>' attribute.
	 * @see #setHelpExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getStereotypeApplicationDescription_HelpExpression()
	 * @model dataType="org.eclipse.sirius.viewpoint.description.InterpretedExpression"
	 * @generated
	 */
	String getHelpExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.StereotypeApplicationDescription#getHelpExpression <em>Help Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help Expression</em>' attribute.
	 * @see #getHelpExpression()
	 * @generated
	 */
	void setHelpExpression(String value);
} // StereotypeApplicationDescription
