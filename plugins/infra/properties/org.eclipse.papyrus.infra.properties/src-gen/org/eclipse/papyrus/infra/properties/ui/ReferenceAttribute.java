/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.ui.ReferenceAttribute#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.properties.ui.UiPackage#getReferenceAttribute()
 * @model
 * @generated
 */
public interface ReferenceAttribute extends WidgetAttribute {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(UIComponent)
	 * @see org.eclipse.papyrus.infra.properties.ui.UiPackage#getReferenceAttribute_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	UIComponent getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.ui.ReferenceAttribute#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(UIComponent value);

} // ReferenceAttribute
