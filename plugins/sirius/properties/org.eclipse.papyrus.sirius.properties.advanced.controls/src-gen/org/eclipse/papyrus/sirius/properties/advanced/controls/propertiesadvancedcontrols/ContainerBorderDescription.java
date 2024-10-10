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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container Border Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription#getLabelExpression <em>Label Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getContainerBorderDescription()
 * @model
 * @generated
 */
public interface ContainerBorderDescription extends ContainerDescription {
	/**
	 * Returns the value of the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label Expression</em>' attribute.
	 * @see #setLabelExpression(String)
	 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage#getContainerBorderDescription_LabelExpression()
	 * @model dataType="org.eclipse.sirius.viewpoint.description.InterpretedExpression"
	 * @generated
	 */
	String getLabelExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription#getLabelExpression <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Expression</em>' attribute.
	 * @see #getLabelExpression()
	 * @generated
	 */
	void setLabelExpression(String value);

} // ContainerBorderDescription
