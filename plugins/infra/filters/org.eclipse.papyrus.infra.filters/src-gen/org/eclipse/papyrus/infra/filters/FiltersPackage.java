/**
 * Copyright (c) 2014, 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.filters;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.papyrus.infra.filters.FiltersFactory
 * @model kind="package"
 * @generated
 */
public interface FiltersPackage extends EPackage {
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
	String eNS_URI = "http://www.eclipse.org/Papyrus/2014/common/filters"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "filters"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	FiltersPackage eINSTANCE = org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.filters.Filter <em>Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.Filter
	 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getFilter()
	 * @generated
	 */
	int FILTER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER___MATCHES__OBJECT = 0;

	/**
	 * The number of operations of the '<em>Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.filters.impl.CompoundFilterImpl <em>Compound Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.impl.CompoundFilterImpl
	 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getCompoundFilter()
	 * @generated
	 */
	int COMPOUND_FILTER = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.filters.impl.EqualsImpl <em>Equals</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.impl.EqualsImpl
	 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getEquals()
	 * @generated
	 */
	int EQUALS = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.filters.impl.FilteredElementImpl <em>Filtered Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.impl.FilteredElementImpl
	 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getFilteredElement()
	 * @generated
	 */
	int FILTERED_ELEMENT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.filters.impl.FilterReferenceImpl <em>Filter Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.impl.FilterReferenceImpl
	 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getFilterReference()
	 * @generated
	 */
	int FILTER_REFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER_REFERENCE__NAME = FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER_REFERENCE__FILTER = FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owned Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER_REFERENCE__OWNED_FILTER = FILTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Filter Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER_REFERENCE_FEATURE_COUNT = FILTER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER_REFERENCE___MATCHES__OBJECT = FILTER___MATCHES__OBJECT;

	/**
	 * The number of operations of the '<em>Filter Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTER_REFERENCE_OPERATION_COUNT = FILTER_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_FILTER__NAME = FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_FILTER__FILTER = FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owned Filter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_FILTER__OWNED_FILTER = FILTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_FILTER__OPERATOR = FILTER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Compound Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_FILTER_FEATURE_COUNT = FILTER_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_FILTER___MATCHES__OBJECT = FILTER___MATCHES__OBJECT;

	/**
	 * The operation id for the '<em>Validate Acyclic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_FILTER___VALIDATE_ACYCLIC__DIAGNOSTICCHAIN_MAP = FILTER_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Compound Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_FILTER_OPERATION_COUNT = FILTER_OPERATION_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EQUALS__NAME = FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EQUALS__OBJECT = FILTER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Equals</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EQUALS_FEATURE_COUNT = FILTER_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EQUALS___MATCHES__OBJECT = FILTER___MATCHES__OBJECT;

	/**
	 * The number of operations of the '<em>Equals</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EQUALS_OPERATION_COUNT = FILTER_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTERED_ELEMENT__FILTER = 0;

	/**
	 * The number of structural features of the '<em>Filtered Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTERED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Filtered Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FILTERED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.filters.OperatorKind <em>Operator Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.OperatorKind
	 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getOperatorKind()
	 * @generated
	 */
	int OPERATOR_KIND = 5;

	/**
	 * The meta object id for the '<em>Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see java.lang.Object
	 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getObject()
	 * @generated
	 */
	int OBJECT = 6;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.filters.CompoundFilter <em>Compound Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Compound Filter</em>'.
	 * @see org.eclipse.papyrus.infra.filters.CompoundFilter
	 * @generated
	 */
	EClass getCompoundFilter();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.filters.CompoundFilter#getFilters <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Filter</em>'.
	 * @see org.eclipse.papyrus.infra.filters.CompoundFilter#getFilters()
	 * @see #getCompoundFilter()
	 * @generated
	 */
	EReference getCompoundFilter_Filter();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.filters.CompoundFilter#getOwnedFilters <em>Owned Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Owned Filter</em>'.
	 * @see org.eclipse.papyrus.infra.filters.CompoundFilter#getOwnedFilters()
	 * @see #getCompoundFilter()
	 * @generated
	 */
	EReference getCompoundFilter_OwnedFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.filters.CompoundFilter#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.eclipse.papyrus.infra.filters.CompoundFilter#getOperator()
	 * @see #getCompoundFilter()
	 * @generated
	 */
	EAttribute getCompoundFilter_Operator();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.filters.CompoundFilter#validateAcyclic(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Validate Acyclic</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Validate Acyclic</em>' operation.
	 * @see org.eclipse.papyrus.infra.filters.CompoundFilter#validateAcyclic(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getCompoundFilter__ValidateAcyclic__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.filters.Filter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Filter</em>'.
	 * @see org.eclipse.papyrus.infra.filters.Filter
	 * @generated
	 */
	EClass getFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.filters.Filter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.filters.Filter#getName()
	 * @see #getFilter()
	 * @generated
	 */
	EAttribute getFilter_Name();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Matches</em>' operation.
	 * @see org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object)
	 * @generated
	 */
	EOperation getFilter__Matches__Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.filters.Equals <em>Equals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Equals</em>'.
	 * @see org.eclipse.papyrus.infra.filters.Equals
	 * @generated
	 */
	EClass getEquals();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.filters.Equals#getObject <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Object</em>'.
	 * @see org.eclipse.papyrus.infra.filters.Equals#getObject()
	 * @see #getEquals()
	 * @generated
	 */
	EAttribute getEquals_Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.filters.FilteredElement <em>Filtered Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Filtered Element</em>'.
	 * @see org.eclipse.papyrus.infra.filters.FilteredElement
	 * @generated
	 */
	EClass getFilteredElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.filters.FilteredElement#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Filter</em>'.
	 * @see org.eclipse.papyrus.infra.filters.FilteredElement#getFilter()
	 * @see #getFilteredElement()
	 * @generated
	 */
	EReference getFilteredElement_Filter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.filters.FilterReference <em>Filter Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Filter Reference</em>'.
	 * @see org.eclipse.papyrus.infra.filters.FilterReference
	 * @generated
	 */
	EClass getFilterReference();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.filters.FilterReference#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Filter</em>'.
	 * @see org.eclipse.papyrus.infra.filters.FilterReference#getFilter()
	 * @see #getFilterReference()
	 * @generated
	 */
	EReference getFilterReference_Filter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.filters.FilterReference#getOwnedFilter <em>Owned Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Owned Filter</em>'.
	 * @see org.eclipse.papyrus.infra.filters.FilterReference#getOwnedFilter()
	 * @see #getFilterReference()
	 * @generated
	 */
	EReference getFilterReference_OwnedFilter();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrus.infra.filters.OperatorKind <em>Operator Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Operator Kind</em>'.
	 * @see org.eclipse.papyrus.infra.filters.OperatorKind
	 * @generated
	 */
	EEnum getOperatorKind();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Object</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 * @generated
	 */
	EDataType getObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FiltersFactory getFiltersFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.filters.impl.CompoundFilterImpl <em>Compound Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.filters.impl.CompoundFilterImpl
		 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getCompoundFilter()
		 * @generated
		 */
		EClass COMPOUND_FILTER = eINSTANCE.getCompoundFilter();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPOUND_FILTER__FILTER = eINSTANCE.getCompoundFilter_Filter();

		/**
		 * The meta object literal for the '<em><b>Owned Filter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPOUND_FILTER__OWNED_FILTER = eINSTANCE.getCompoundFilter_OwnedFilter();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute COMPOUND_FILTER__OPERATOR = eINSTANCE.getCompoundFilter_Operator();

		/**
		 * The meta object literal for the '<em><b>Validate Acyclic</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation COMPOUND_FILTER___VALIDATE_ACYCLIC__DIAGNOSTICCHAIN_MAP = eINSTANCE.getCompoundFilter__ValidateAcyclic__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.filters.Filter <em>Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.filters.Filter
		 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getFilter()
		 * @generated
		 */
		EClass FILTER = eINSTANCE.getFilter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute FILTER__NAME = eINSTANCE.getFilter_Name();

		/**
		 * The meta object literal for the '<em><b>Matches</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation FILTER___MATCHES__OBJECT = eINSTANCE.getFilter__Matches__Object();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.filters.impl.EqualsImpl <em>Equals</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.filters.impl.EqualsImpl
		 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getEquals()
		 * @generated
		 */
		EClass EQUALS = eINSTANCE.getEquals();

		/**
		 * The meta object literal for the '<em><b>Object</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute EQUALS__OBJECT = eINSTANCE.getEquals_Object();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.filters.impl.FilteredElementImpl <em>Filtered Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.filters.impl.FilteredElementImpl
		 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getFilteredElement()
		 * @generated
		 */
		EClass FILTERED_ELEMENT = eINSTANCE.getFilteredElement();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference FILTERED_ELEMENT__FILTER = eINSTANCE.getFilteredElement_Filter();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.filters.impl.FilterReferenceImpl <em>Filter Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.filters.impl.FilterReferenceImpl
		 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getFilterReference()
		 * @generated
		 */
		EClass FILTER_REFERENCE = eINSTANCE.getFilterReference();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference FILTER_REFERENCE__FILTER = eINSTANCE.getFilterReference_Filter();

		/**
		 * The meta object literal for the '<em><b>Owned Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference FILTER_REFERENCE__OWNED_FILTER = eINSTANCE.getFilterReference_OwnedFilter();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.filters.OperatorKind <em>Operator Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.filters.OperatorKind
		 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getOperatorKind()
		 * @generated
		 */
		EEnum OPERATOR_KIND = eINSTANCE.getOperatorKind();

		/**
		 * The meta object literal for the '<em>Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see java.lang.Object
		 * @see org.eclipse.papyrus.infra.filters.impl.FiltersPackageImpl#getObject()
		 * @generated
		 */
		EDataType OBJECT = eINSTANCE.getObject();

	}

} // FiltersPackage
