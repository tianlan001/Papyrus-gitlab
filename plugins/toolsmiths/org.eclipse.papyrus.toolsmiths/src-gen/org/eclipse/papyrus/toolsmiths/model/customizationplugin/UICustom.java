/**
 * Copyright (c) 2012 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.toolsmiths.model.customizationplugin;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UI Custom</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UICustom#isLoadByDefault <em>Load By Default</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getUICustom()
 * @model
 * @generated
 */
public interface UICustom extends FileBasedCustomizableElement {
	/**
	 * Returns the value of the '<em><b>Load By Default</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load By Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load By Default</em>' attribute.
	 * @see #setLoadByDefault(boolean)
	 * @see org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage#getUICustom_LoadByDefault()
	 * @model default="false"
	 * @generated
	 */
	boolean isLoadByDefault();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.toolsmiths.model.customizationplugin.UICustom#isLoadByDefault <em>Load By Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load By Default</em>' attribute.
	 * @see #isLoadByDefault()
	 * @generated
	 */
	void setLoadByDefault(boolean value);

} // UICustom
