/**
 * Copyright (c) 2015 CEA LIST and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *
 */
package org.eclipse.papyrus.infra.viewpoints.configuration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see org.eclipse.papyrus.infra.viewpoints.configuration.ConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigurationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "configuration";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/viewpoints/configuration";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "configuration";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConfigurationPackage eINSTANCE = org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusViewImpl <em>Papyrus View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusViewImpl
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl#getPapyrusView()
	 * @generated
	 */
	int PAPYRUS_VIEW = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_VIEW__NAME = 0;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_VIEW__ICON = 1;

	/**
	 * The feature id for the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_VIEW__IMPLEMENTATION_ID = 2;

	/**
	 * The number of structural features of the '<em>Papyrus View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_VIEW_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Papyrus View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_VIEW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusDiagramImpl <em>Papyrus Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusDiagramImpl
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl#getPapyrusDiagram()
	 * @generated
	 */
	int PAPYRUS_DIAGRAM = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__NAME = PAPYRUS_VIEW__NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__ICON = PAPYRUS_VIEW__ICON;

	/**
	 * The feature id for the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__IMPLEMENTATION_ID = PAPYRUS_VIEW__IMPLEMENTATION_ID;

	/**
	 * The number of structural features of the '<em>Papyrus Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM_FEATURE_COUNT = PAPYRUS_VIEW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Papyrus Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM_OPERATION_COUNT = PAPYRUS_VIEW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusSyncTableImpl <em>Papyrus Sync Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusSyncTableImpl
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl#getPapyrusSyncTable()
	 * @generated
	 */
	int PAPYRUS_SYNC_TABLE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_SYNC_TABLE__NAME = PAPYRUS_VIEW__NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_SYNC_TABLE__ICON = PAPYRUS_VIEW__ICON;

	/**
	 * The feature id for the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_SYNC_TABLE__IMPLEMENTATION_ID = PAPYRUS_VIEW__IMPLEMENTATION_ID;

	/**
	 * The number of structural features of the '<em>Papyrus Sync Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_SYNC_TABLE_FEATURE_COUNT = PAPYRUS_VIEW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Papyrus Sync Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_SYNC_TABLE_OPERATION_COUNT = PAPYRUS_VIEW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusTableImpl <em>Papyrus Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusTableImpl
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl#getPapyrusTable()
	 * @generated
	 */
	int PAPYRUS_TABLE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__NAME = PAPYRUS_VIEW__NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__ICON = PAPYRUS_VIEW__ICON;

	/**
	 * The feature id for the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__IMPLEMENTATION_ID = PAPYRUS_VIEW__IMPLEMENTATION_ID;

	/**
	 * The number of structural features of the '<em>Papyrus Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE_FEATURE_COUNT = PAPYRUS_VIEW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Papyrus Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE_OPERATION_COUNT = PAPYRUS_VIEW_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView <em>Papyrus View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Papyrus View</em>'.
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView
	 * @generated
	 */
	EClass getPapyrusView();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView#getName()
	 * @see #getPapyrusView()
	 * @generated
	 */
	EAttribute getPapyrusView_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView#getIcon()
	 * @see #getPapyrusView()
	 * @generated
	 */
	EAttribute getPapyrusView_Icon();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView#getImplementationID <em>Implementation ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation ID</em>'.
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusView#getImplementationID()
	 * @see #getPapyrusView()
	 * @generated
	 */
	EAttribute getPapyrusView_ImplementationID();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusDiagram <em>Papyrus Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Papyrus Diagram</em>'.
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusDiagram
	 * @generated
	 */
	EClass getPapyrusDiagram();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusSyncTable <em>Papyrus Sync Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Papyrus Sync Table</em>'.
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusSyncTable
	 * @generated
	 */
	EClass getPapyrusSyncTable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusTable <em>Papyrus Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Papyrus Table</em>'.
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusTable
	 * @generated
	 */
	EClass getPapyrusTable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConfigurationFactory getConfigurationFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusViewImpl <em>Papyrus View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusViewImpl
		 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl#getPapyrusView()
		 * @generated
		 */
		EClass PAPYRUS_VIEW = eINSTANCE.getPapyrusView();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAPYRUS_VIEW__NAME = eINSTANCE.getPapyrusView_Name();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAPYRUS_VIEW__ICON = eINSTANCE.getPapyrusView_Icon();

		/**
		 * The meta object literal for the '<em><b>Implementation ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAPYRUS_VIEW__IMPLEMENTATION_ID = eINSTANCE.getPapyrusView_ImplementationID();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusDiagramImpl <em>Papyrus Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusDiagramImpl
		 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl#getPapyrusDiagram()
		 * @generated
		 */
		EClass PAPYRUS_DIAGRAM = eINSTANCE.getPapyrusDiagram();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusSyncTableImpl <em>Papyrus Sync Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusSyncTableImpl
		 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl#getPapyrusSyncTable()
		 * @generated
		 */
		EClass PAPYRUS_SYNC_TABLE = eINSTANCE.getPapyrusSyncTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusTableImpl <em>Papyrus Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.PapyrusTableImpl
		 * @see org.eclipse.papyrus.infra.viewpoints.configuration.impl.ConfigurationPackageImpl#getPapyrusTable()
		 * @generated
		 */
		EClass PAPYRUS_TABLE = eINSTANCE.getPapyrusTable();

	}

} //ConfigurationPackage
