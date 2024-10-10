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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage
 * @generated
 */
public interface EefAdvancedControlsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EefAdvancedControlsFactory eINSTANCE = org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.impl.EefAdvancedControlsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>EEF Ext Editable Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EEF Ext Editable Reference Description</em>'.
	 * @generated
	 */
	EEFExtEditableReferenceDescription createEEFExtEditableReferenceDescription();

	/**
	 * Returns a new object of class '<em>EEF Container Border Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EEF Container Border Description</em>'.
	 * @generated
	 */
	EEFContainerBorderDescription createEEFContainerBorderDescription();

	/**
	 * Returns a new object of class '<em>EEF Language Expression Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EEF Language Expression Description</em>'.
	 * @generated
	 */
	EEFLanguageExpressionDescription createEEFLanguageExpressionDescription();

	/**
	 * Returns a new object of class '<em>EEF Profile Application Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EEF Profile Application Description</em>'.
	 * @generated
	 */
	EEFProfileApplicationDescription createEEFProfileApplicationDescription();

	/**
	 * Returns a new object of class '<em>EEF Stereotype Application Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EEF Stereotype Application Description</em>'.
	 * @generated
	 */
	EEFStereotypeApplicationDescription createEEFStereotypeApplicationDescription();

	/**
	 * Returns a new object of class '<em>EEF Input Content Papyrus Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EEF Input Content Papyrus Reference Description</em>'.
	 * @generated
	 */
	EEFInputContentPapyrusReferenceDescription createEEFInputContentPapyrusReferenceDescription();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EefAdvancedControlsPackage getEefAdvancedControlsPackage();

} //EefAdvancedControlsFactory
