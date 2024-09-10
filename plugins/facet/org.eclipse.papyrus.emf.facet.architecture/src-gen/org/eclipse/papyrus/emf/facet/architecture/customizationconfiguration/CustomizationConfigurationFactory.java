/**
 *  Copyright (c) 2020 CEA LIST and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent LORENZO - Initial API and implementation
 */
package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage
 * @generated
 */
public interface CustomizationConfigurationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	CustomizationConfigurationFactory eINSTANCE = org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.CustomizationConfigurationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>EMF Facet Tree Viewer Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>EMF Facet Tree Viewer Configuration</em>'.
	 * @generated
	 */
	EMFFacetTreeViewerConfiguration createEMFFacetTreeViewerConfiguration();

	/**
	 * Returns a new object of class '<em>Customization Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Customization Reference</em>'.
	 * @generated
	 */
	CustomizationReference createCustomizationReference();

	/**
	 * Returns a new object of class '<em>Absolute Order</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Absolute Order</em>'.
	 * @generated
	 */
	AbsoluteOrder createAbsoluteOrder();

	/**
	 * Returns a new object of class '<em>Relative Order</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Relative Order</em>'.
	 * @generated
	 */
	RelativeOrder createRelativeOrder();

	/**
	 * Returns a new object of class '<em>Redefinition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Redefinition</em>'.
	 * @generated
	 */
	Redefinition createRedefinition();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	CustomizationConfigurationPackage getCustomizationConfigurationPackage();

} // CustomizationConfigurationFactory
