/**
 * Copyright (c) 2014 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.uml.filters;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.infra.filters.FiltersPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.uml.filters.UMLFiltersFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='umlfilters'"
 * @generated
 */
public interface UMLFiltersPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "filters"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/Papyrus/2014/uml/filters"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "umlfilters"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	UMLFiltersPackage eINSTANCE = org.eclipse.papyrus.uml.filters.impl.UMLFiltersPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.filters.impl.ProfileAppliedImpl <em>Profile Applied</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.filters.impl.ProfileAppliedImpl
	 * @see org.eclipse.papyrus.uml.filters.impl.UMLFiltersPackageImpl#getProfileApplied()
	 * @generated
	 */
	int PROFILE_APPLIED = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLIED__NAME = FiltersPackage.FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Profile Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLIED__PROFILE_QUALIFIED_NAME = FiltersPackage.FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Profile URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLIED__PROFILE_URI = FiltersPackage.FILTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Profile Applied</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLIED_FEATURE_COUNT = FiltersPackage.FILTER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLIED___MATCHES__OBJECT = FiltersPackage.FILTER___MATCHES__OBJECT;

	/**
	 * The operation id for the '<em>Resolve Profile</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLIED___RESOLVE_PROFILE__OBJECT = FiltersPackage.FILTER_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Profile Applied</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PROFILE_APPLIED_OPERATION_COUNT = FiltersPackage.FILTER_OPERATION_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.filters.ProfileApplied <em>Profile Applied</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Profile Applied</em>'.
	 * @see org.eclipse.papyrus.uml.filters.ProfileApplied
	 * @generated
	 */
	EClass getProfileApplied();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.filters.ProfileApplied#getProfileQualifiedName <em>Profile Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Profile Qualified Name</em>'.
	 * @see org.eclipse.papyrus.uml.filters.ProfileApplied#getProfileQualifiedName()
	 * @see #getProfileApplied()
	 * @generated
	 */
	EAttribute getProfileApplied_ProfileQualifiedName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.filters.ProfileApplied#getProfileURI <em>Profile URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Profile URI</em>'.
	 * @see org.eclipse.papyrus.uml.filters.ProfileApplied#getProfileURI()
	 * @see #getProfileApplied()
	 * @generated
	 */
	EAttribute getProfileApplied_ProfileURI();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.uml.filters.ProfileApplied#resolveProfile(java.lang.Object) <em>Resolve Profile</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Resolve Profile</em>' operation.
	 * @see org.eclipse.papyrus.uml.filters.ProfileApplied#resolveProfile(java.lang.Object)
	 * @generated
	 */
	EOperation getProfileApplied__ResolveProfile__Object();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UMLFiltersFactory getUMLFiltersFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
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
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.filters.impl.ProfileAppliedImpl <em>Profile Applied</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.uml.filters.impl.ProfileAppliedImpl
		 * @see org.eclipse.papyrus.uml.filters.impl.UMLFiltersPackageImpl#getProfileApplied()
		 * @generated
		 */
		EClass PROFILE_APPLIED = eINSTANCE.getProfileApplied();

		/**
		 * The meta object literal for the '<em><b>Profile Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PROFILE_APPLIED__PROFILE_QUALIFIED_NAME = eINSTANCE.getProfileApplied_ProfileQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Profile URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PROFILE_APPLIED__PROFILE_URI = eINSTANCE.getProfileApplied_ProfileURI();

		/**
		 * The meta object literal for the '<em><b>Resolve Profile</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation PROFILE_APPLIED___RESOLVE_PROFILE__OBJECT = eINSTANCE.getProfileApplied__ResolveProfile__Object();

	}

} // UMLFiltersPackage
