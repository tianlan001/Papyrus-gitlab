/**
 * Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.nattable.representation;

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
 * @see org.eclipse.papyrus.infra.nattable.representation.RepresentationFactory
 * @model kind="package"
 * @generated
 */
public interface RepresentationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "representation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/nattable/representation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nattablerepresentation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepresentationPackage eINSTANCE = org.eclipse.papyrus.infra.nattable.representation.impl.RepresentationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.representation.impl.PapyrusTableImpl <em>Papyrus Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.representation.impl.PapyrusTableImpl
	 * @see org.eclipse.papyrus.infra.nattable.representation.impl.RepresentationPackageImpl#getPapyrusTable()
	 * @generated
	 */
	int PAPYRUS_TABLE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__ID = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__NAME = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__DESCRIPTION = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__QUALIFIED_NAME = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__ICON = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__ICON;

	/**
	 * The feature id for the '<em><b>Language</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__LANGUAGE = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__LANGUAGE;

	/**
	 * The feature id for the '<em><b>Concerns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__CONCERNS = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__CONCERNS;

	/**
	 * The feature id for the '<em><b>Grayed Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__GRAYED_ICON = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__GRAYED_ICON;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__PARENT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT;

	/**
	 * The feature id for the '<em><b>Model Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__MODEL_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES;

	/**
	 * The feature id for the '<em><b>Owning Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__OWNING_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES;

	/**
	 * The feature id for the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__IMPLEMENTATION_ID = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__CONFIGURATION = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Creation Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE__CREATION_COMMAND = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Papyrus Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE_FEATURE_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Papyrus Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_TABLE_OPERATION_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.nattable.representation.PapyrusTable <em>Papyrus Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Papyrus Table</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.representation.PapyrusTable
	 * @generated
	 */
	EClass getPapyrusTable();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.nattable.representation.PapyrusTable#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.representation.PapyrusTable#getConfiguration()
	 * @see #getPapyrusTable()
	 * @generated
	 */
	EReference getPapyrusTable_Configuration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.nattable.representation.PapyrusTable#getCreationCommand <em>Creation Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Command</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.representation.PapyrusTable#getCreationCommand()
	 * @see #getPapyrusTable()
	 * @generated
	 */
	EAttribute getPapyrusTable_CreationCommand();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RepresentationFactory getRepresentationFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.representation.impl.PapyrusTableImpl <em>Papyrus Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.representation.impl.PapyrusTableImpl
		 * @see org.eclipse.papyrus.infra.nattable.representation.impl.RepresentationPackageImpl#getPapyrusTable()
		 * @generated
		 */
		EClass PAPYRUS_TABLE = eINSTANCE.getPapyrusTable();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAPYRUS_TABLE__CONFIGURATION = eINSTANCE.getPapyrusTable_Configuration();

		/**
		 * The meta object literal for the '<em><b>Creation Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAPYRUS_TABLE__CREATION_COMMAND = eINSTANCE.getPapyrusTable_CreationCommand();

	}

} //RepresentationPackage
