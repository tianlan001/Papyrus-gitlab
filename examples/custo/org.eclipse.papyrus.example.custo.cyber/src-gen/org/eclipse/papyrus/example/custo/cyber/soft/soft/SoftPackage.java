/**
 * Copyright (c) 2021 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.example.custo.cyber.soft.soft;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.SoftFactory
 * @model kind="package"
 * @generated
 */
public interface SoftPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "soft"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://soft_papyrus"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "soft"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SoftPackage eINSTANCE = org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftLayerImpl <em>Layer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftLayerImpl
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftPackageImpl#getSoftLayer()
	 * @generated
	 */
	int SOFT_LAYER = 0;

	/**
	 * The feature id for the '<em><b>Base Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFT_LAYER__BASE_CLASS = 0;

	/**
	 * The number of structural features of the '<em>Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFT_LAYER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFT_LAYER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftImpl <em>Soft</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftImpl
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftPackageImpl#getSoft()
	 * @generated
	 */
	int SOFT = 1;

	/**
	 * The feature id for the '<em><b>Base Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFT__BASE_PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFT__VERSION = 1;

	/**
	 * The number of structural features of the '<em>Soft</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Soft</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOFT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.CommunicationImpl <em>Communication</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.CommunicationImpl
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftPackageImpl#getCommunication()
	 * @generated
	 */
	int COMMUNICATION = 2;

	/**
	 * The feature id for the '<em><b>Base Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__BASE_CONNECTOR = 0;

	/**
	 * The number of structural features of the '<em>Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.SoftLayer <em>Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Layer</em>'.
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.SoftLayer
	 * @generated
	 */
	EClass getSoftLayer();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.SoftLayer#getBase_Class <em>Base Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Class</em>'.
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.SoftLayer#getBase_Class()
	 * @see #getSoftLayer()
	 * @generated
	 */
	EReference getSoftLayer_Base_Class();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.Soft <em>Soft</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Soft</em>'.
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.Soft
	 * @generated
	 */
	EClass getSoft();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.Soft#getBase_Property <em>Base Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Property</em>'.
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.Soft#getBase_Property()
	 * @see #getSoft()
	 * @generated
	 */
	EReference getSoft_Base_Property();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.Soft#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.Soft#getVersion()
	 * @see #getSoft()
	 * @generated
	 */
	EAttribute getSoft_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.Communication <em>Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communication</em>'.
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.Communication
	 * @generated
	 */
	EClass getCommunication();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.Communication#getBase_Connector <em>Base Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Connector</em>'.
	 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.Communication#getBase_Connector()
	 * @see #getCommunication()
	 * @generated
	 */
	EReference getCommunication_Base_Connector();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SoftFactory getSoftFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftLayerImpl <em>Layer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftLayerImpl
		 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftPackageImpl#getSoftLayer()
		 * @generated
		 */
		EClass SOFT_LAYER = eINSTANCE.getSoftLayer();

		/**
		 * The meta object literal for the '<em><b>Base Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOFT_LAYER__BASE_CLASS = eINSTANCE.getSoftLayer_Base_Class();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftImpl <em>Soft</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftImpl
		 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftPackageImpl#getSoft()
		 * @generated
		 */
		EClass SOFT = eINSTANCE.getSoft();

		/**
		 * The meta object literal for the '<em><b>Base Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOFT__BASE_PROPERTY = eINSTANCE.getSoft_Base_Property();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOFT__VERSION = eINSTANCE.getSoft_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.CommunicationImpl <em>Communication</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.CommunicationImpl
		 * @see org.eclipse.papyrus.example.custo.cyber.soft.soft.impl.SoftPackageImpl#getCommunication()
		 * @generated
		 */
		EClass COMMUNICATION = eINSTANCE.getCommunication();

		/**
		 * The meta object literal for the '<em><b>Base Connector</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION__BASE_CONNECTOR = eINSTANCE.getCommunication_Base_Connector();

	}

} //SoftPackage
