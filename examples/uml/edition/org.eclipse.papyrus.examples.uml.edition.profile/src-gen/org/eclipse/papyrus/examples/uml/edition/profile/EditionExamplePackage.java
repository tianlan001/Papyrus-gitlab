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
 *    Ibtihel Khemir (CEA LIST) ibtihel.khemir@cea.fr- Initial API and implementation
 */
package org.eclipse.papyrus.examples.uml.edition.profile;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.examples.uml.edition.profile.EditionExampleFactory
 * @model kind="package" annotation="http://www.eclipse.org/uml2/2.0.0/UML
 *        originalName='EditionExample'"
 * @generated
 */
public interface EditionExamplePackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "profile"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/uml/edition/example"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "EditionExample"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	EditionExamplePackage eINSTANCE = org.eclipse.papyrus.examples.uml.edition.profile.impl.EditionExamplePackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.papyrus.examples.uml.edition.profile.impl.LastEditionImpl
	 * <em>Last Edition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.examples.uml.edition.profile.impl.LastEditionImpl
	 * @see org.eclipse.papyrus.examples.uml.edition.profile.impl.EditionExamplePackageImpl#getLastEdition()
	 * @generated
	 */
	int LAST_EDITION = 0;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAST_EDITION__DATE = 0;

	/**
	 * The feature id for the '<em><b>Base Named Element</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAST_EDITION__BASE_NAMED_ELEMENT = 1;

	/**
	 * The number of structural features of the '<em>Last Edition</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAST_EDITION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Last Edition</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAST_EDITION_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.papyrus.examples.uml.edition.profile.LastEdition <em>Last
	 * Edition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Last Edition</em>'.
	 * @see org.eclipse.papyrus.examples.uml.edition.profile.LastEdition
	 * @generated
	 */
	EClass getLastEdition();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.examples.uml.edition.profile.LastEdition#getDate
	 * <em>Date</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.papyrus.examples.uml.edition.profile.LastEdition#getDate()
	 * @see #getLastEdition()
	 * @generated
	 */
	EAttribute getLastEdition_Date();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.papyrus.examples.uml.edition.profile.LastEdition#getBase_NamedElement
	 * <em>Base Named Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Base Named Element</em>'.
	 * @see org.eclipse.papyrus.examples.uml.edition.profile.LastEdition#getBase_NamedElement()
	 * @see #getLastEdition()
	 * @generated
	 */
	EReference getLastEdition_Base_NamedElement();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EditionExampleFactory getEditionExampleFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.examples.uml.edition.profile.impl.LastEditionImpl
		 * <em>Last Edition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.examples.uml.edition.profile.impl.LastEditionImpl
		 * @see org.eclipse.papyrus.examples.uml.edition.profile.impl.EditionExamplePackageImpl#getLastEdition()
		 * @generated
		 */
		EClass LAST_EDITION = eINSTANCE.getLastEdition();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LAST_EDITION__DATE = eINSTANCE.getLastEdition_Date();

		/**
		 * The meta object literal for the '<em><b>Base Named Element</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LAST_EDITION__BASE_NAMED_ELEMENT = eINSTANCE.getLastEdition_Base_NamedElement();

	}

} // EditionExamplePackage
