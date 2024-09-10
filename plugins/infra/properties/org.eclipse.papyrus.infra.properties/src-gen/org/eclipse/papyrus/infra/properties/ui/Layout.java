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

import org.eclipse.papyrus.infra.properties.environment.LayoutType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.ui.Layout#getLayoutType <em>Layout Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.properties.ui.UiPackage#getLayout()
 * @model
 * @generated
 */
public interface Layout extends UIComponent {
	/**
	 * Returns the value of the '<em><b>Layout Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Layout Type</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layout Type</em>' reference.
	 * @see #setLayoutType(LayoutType)
	 * @see org.eclipse.papyrus.infra.properties.ui.UiPackage#getLayout_LayoutType()
	 * @model required="true"
	 * @generated
	 */
	LayoutType getLayoutType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.properties.ui.Layout#getLayoutType <em>Layout Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layout Type</em>' reference.
	 * @see #getLayoutType()
	 * @generated
	 */
	void setLayoutType(LayoutType value);

} // Layout
