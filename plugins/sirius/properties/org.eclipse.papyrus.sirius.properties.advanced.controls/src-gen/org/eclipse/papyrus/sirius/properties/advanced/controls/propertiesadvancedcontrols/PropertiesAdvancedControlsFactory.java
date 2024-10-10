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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage
 * @generated
 */
public interface PropertiesAdvancedControlsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PropertiesAdvancedControlsFactory eINSTANCE = org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.PropertiesAdvancedControlsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Ext Editable Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ext Editable Reference Description</em>'.
	 * @generated
	 */
	ExtEditableReferenceDescription createExtEditableReferenceDescription();

	/**
	 * Returns a new object of class '<em>Container Border Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Container Border Description</em>'.
	 * @generated
	 */
	ContainerBorderDescription createContainerBorderDescription();

	/**
	 * Returns a new object of class '<em>Language Expression Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Language Expression Description</em>'.
	 * @generated
	 */
	LanguageExpressionDescription createLanguageExpressionDescription();

	/**
	 * Returns a new object of class '<em>Profile Application Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Profile Application Description</em>'.
	 * @generated
	 */
	ProfileApplicationDescription createProfileApplicationDescription();

	/**
	 * Returns a new object of class '<em>Stereotype Application Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stereotype Application Description</em>'.
	 * @generated
	 */
	StereotypeApplicationDescription createStereotypeApplicationDescription();

	/**
	 * Returns a new object of class '<em>Input Content Papyrus Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Input Content Papyrus Reference Description</em>'.
	 * @generated
	 */
	InputContentPapyrusReferenceDescription createInputContentPapyrusReferenceDescription();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PropertiesAdvancedControlsPackage getPropertiesAdvancedControlsPackage();

} //PropertiesAdvancedControlsFactory
