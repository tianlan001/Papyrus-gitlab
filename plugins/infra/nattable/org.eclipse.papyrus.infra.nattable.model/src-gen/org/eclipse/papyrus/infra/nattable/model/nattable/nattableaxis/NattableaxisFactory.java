/**
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisPackage
 * @generated
 */
public interface NattableaxisFactory extends EFactory {

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NattableaxisFactory eINSTANCE = org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.impl.NattableaxisFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Id Tree Item Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Id Tree Item Axis</em>'.
	 * @generated
	 */
	IdTreeItemAxis createIdTreeItemAxis();

	/**
	 * Returns a new object of class '<em>EObject Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EObject Axis</em>'.
	 * @generated
	 */
	EObjectAxis createEObjectAxis();

	/**
	 * Returns a new object of class '<em>EObject Tree Item Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EObject Tree Item Axis</em>'.
	 * @generated
	 */
	EObjectTreeItemAxis createEObjectTreeItemAxis();

	/**
	 * Returns a new object of class '<em>Feature Id Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Id Axis</em>'.
	 * @generated
	 */
	FeatureIdAxis createFeatureIdAxis();

	/**
	 * Returns a new object of class '<em>Feature Id Tree Item Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Id Tree Item Axis</em>'.
	 * @generated
	 */
	FeatureIdTreeItemAxis createFeatureIdTreeItemAxis();

	/**
	 * Returns a new object of class '<em>EStructural Feature Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EStructural Feature Axis</em>'.
	 * @generated
	 */
	EStructuralFeatureAxis createEStructuralFeatureAxis();

	/**
	 * Returns a new object of class '<em>EOperation Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EOperation Axis</em>'.
	 * @generated
	 */
	EOperationAxis createEOperationAxis();

	/**
	 * Returns a new object of class '<em>EStructural Feature Tree Item Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EStructural Feature Tree Item Axis</em>'.
	 * @generated
	 */
	EStructuralFeatureTreeItemAxis createEStructuralFeatureTreeItemAxis();

	/**
	 * Returns a new object of class '<em>EOperation Tree Item Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EOperation Tree Item Axis</em>'.
	 * @generated
	 */
	EOperationTreeItemAxis createEOperationTreeItemAxis();

	/**
	 * Returns a new object of class '<em>Object Id Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Id Axis</em>'.
	 * @generated
	 */
	ObjectIdAxis createObjectIdAxis();

	/**
	 * Returns a new object of class '<em>Object Id Tree Item Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Id Tree Item Axis</em>'.
	 * @generated
	 */
	ObjectIdTreeItemAxis createObjectIdTreeItemAxis();

	/**
	 * Returns a new object of class '<em>Axis Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Axis Group</em>'.
	 * @generated
	 */
	AxisGroup createAxisGroup();

	/**
	 * Returns a new object of class '<em>Operation Id Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Id Axis</em>'.
	 * @generated
	 */
	OperationIdAxis createOperationIdAxis();

	/**
	 * Returns a new object of class '<em>Operation Id Tree Item Axis</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Id Tree Item Axis</em>'.
	 * @generated
	 */
	OperationIdTreeItemAxis createOperationIdTreeItemAxis();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NattableaxisPackage getNattableaxisPackage();
} // NattableaxisFactory
