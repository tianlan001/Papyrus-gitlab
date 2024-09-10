/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.emf.types.constraints;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.filters.FiltersPackage;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

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
 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='constraintAdvice'"
 * @generated
 */
public interface ConstraintAdvicePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "constraints";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/Papyrus/2021/types/constraints";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "constraints";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	ConstraintAdvicePackage eINSTANCE = org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdviceConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdviceConfigurationImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getConstraintAdviceConfiguration()
	 * @generated
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__DESCRIPTION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__OWNING_TYPE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__ANNOTATIONS = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__BEFORE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__AFTER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__IDENTIFIER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__CONTAINER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__MATCHER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__INHERITANCE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Apply To All Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__APPLY_TO_ALL_TYPES = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__OWNING_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Element Type Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__ELEMENT_TYPE_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET;

	/**
	 * The feature id for the '<em><b>Owning Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__OWNING_TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TARGET;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION_FEATURE_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Element Type Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION___GET_ELEMENT_TYPE_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION___GET_ELEMENT_TYPE_SET;

	/**
	 * The number of operations of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ADVICE_CONFIGURATION_OPERATION_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.AdviceConstraintImpl <em>Advice Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.AdviceConstraintImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getAdviceConstraint()
	 * @generated
	 */
	int ADVICE_CONSTRAINT = 1;

	/**
	 * The feature id for the '<em><b>Composite</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONSTRAINT__COMPOSITE = 0;

	/**
	 * The feature id for the '<em><b>Advice</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONSTRAINT__ADVICE = 1;

	/**
	 * The feature id for the '<em><b>Owning Advice</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONSTRAINT__OWNING_ADVICE = 2;

	/**
	 * The number of structural features of the '<em>Advice Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONSTRAINT_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Approve Request</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST = 0;

	/**
	 * The operation id for the '<em>Get Advice</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONSTRAINT___GET_ADVICE = 1;

	/**
	 * The number of operations of the '<em>Advice Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONSTRAINT_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceConstraintImpl <em>Reference Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceConstraintImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getReferenceConstraint()
	 * @generated
	 */
	int REFERENCE_CONSTRAINT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferencePermissionImpl <em>Reference Permission</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferencePermissionImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getReferencePermission()
	 * @generated
	 */
	int REFERENCE_PERMISSION = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.AnyReferenceImpl <em>Any Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.AnyReferenceImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getAnyReference()
	 * @generated
	 */
	int ANY_REFERENCE = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceImpl <em>Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getReference()
	 * @generated
	 */
	int REFERENCE = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ElementTypeFilterImpl <em>Element Type Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ElementTypeFilterImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getElementTypeFilter()
	 * @generated
	 */
	int ELEMENT_TYPE_FILTER = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.CompositeConstraintImpl <em>Composite Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.CompositeConstraintImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getCompositeConstraint()
	 * @generated
	 */
	int COMPOSITE_CONSTRAINT = 2;

	/**
	 * The feature id for the '<em><b>Composite</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT__COMPOSITE = ADVICE_CONSTRAINT__COMPOSITE;

	/**
	 * The feature id for the '<em><b>Advice</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT__ADVICE = ADVICE_CONSTRAINT__ADVICE;

	/**
	 * The feature id for the '<em><b>Owning Advice</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT__OWNING_ADVICE = ADVICE_CONSTRAINT__OWNING_ADVICE;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT__OPERATOR = ADVICE_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT__CONSTRAINT = ADVICE_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Composite Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT_FEATURE_COUNT = ADVICE_CONSTRAINT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Approve Request</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST = ADVICE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST;

	/**
	 * The operation id for the '<em>Get Advice</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT___GET_ADVICE = ADVICE_CONSTRAINT___GET_ADVICE;

	/**
	 * The number of operations of the '<em>Composite Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_CONSTRAINT_OPERATION_COUNT = ADVICE_CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Composite</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CONSTRAINT__COMPOSITE = ADVICE_CONSTRAINT__COMPOSITE;

	/**
	 * The feature id for the '<em><b>Advice</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CONSTRAINT__ADVICE = ADVICE_CONSTRAINT__ADVICE;

	/**
	 * The feature id for the '<em><b>Owning Advice</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CONSTRAINT__OWNING_ADVICE = ADVICE_CONSTRAINT__OWNING_ADVICE;

	/**
	 * The feature id for the '<em><b>Permission</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CONSTRAINT__PERMISSION = ADVICE_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Reference Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CONSTRAINT_FEATURE_COUNT = ADVICE_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Approve Request</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST = ADVICE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST;

	/**
	 * The operation id for the '<em>Get Advice</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CONSTRAINT___GET_ADVICE = ADVICE_CONSTRAINT___GET_ADVICE;

	/**
	 * The number of operations of the '<em>Reference Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CONSTRAINT_OPERATION_COUNT = ADVICE_CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_PERMISSION__FILTER = FiltersPackage.FILTERED_ELEMENT__FILTER;

	/**
	 * The feature id for the '<em><b>Permitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_PERMISSION__PERMITTED = FiltersPackage.FILTERED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Reference Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_PERMISSION_FEATURE_COUNT = FiltersPackage.FILTERED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_PERMISSION___MATCHES__EREFERENCE = FiltersPackage.FILTERED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Reference Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_PERMISSION_OPERATION_COUNT = FiltersPackage.FILTERED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANY_REFERENCE__FILTER = REFERENCE_PERMISSION__FILTER;

	/**
	 * The feature id for the '<em><b>Permitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANY_REFERENCE__PERMITTED = REFERENCE_PERMISSION__PERMITTED;

	/**
	 * The feature id for the '<em><b>Reference Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANY_REFERENCE__REFERENCE_KIND = REFERENCE_PERMISSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Any Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANY_REFERENCE_FEATURE_COUNT = REFERENCE_PERMISSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANY_REFERENCE___MATCHES__EREFERENCE = REFERENCE_PERMISSION___MATCHES__EREFERENCE;

	/**
	 * The number of operations of the '<em>Any Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ANY_REFERENCE_OPERATION_COUNT = REFERENCE_PERMISSION_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE__FILTER = REFERENCE_PERMISSION__FILTER;

	/**
	 * The feature id for the '<em><b>Permitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE__PERMITTED = REFERENCE_PERMISSION__PERMITTED;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE__REFERENCE = REFERENCE_PERMISSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_FEATURE_COUNT = REFERENCE_PERMISSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE___MATCHES__EREFERENCE = REFERENCE_PERMISSION___MATCHES__EREFERENCE;

	/**
	 * The number of operations of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int REFERENCE_OPERATION_COUNT = REFERENCE_PERMISSION_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER__NAME = FiltersPackage.FILTER__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER__ELEMENT_TYPE = FiltersPackage.FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Relationship</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER__RELATIONSHIP = FiltersPackage.FILTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Element Type Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER_FEATURE_COUNT = FiltersPackage.FILTER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER___MATCHES__OBJECT = FiltersPackage.FILTER___MATCHES__OBJECT;

	/**
	 * The number of operations of the '<em>Element Type Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_FILTER_OPERATION_COUNT = FiltersPackage.FILTER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.RelationshipConstraintImpl <em>Relationship Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.RelationshipConstraintImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getRelationshipConstraint()
	 * @generated
	 */
	int RELATIONSHIP_CONSTRAINT = 8;

	/**
	 * The feature id for the '<em><b>Composite</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_CONSTRAINT__COMPOSITE = ADVICE_CONSTRAINT__COMPOSITE;

	/**
	 * The feature id for the '<em><b>Advice</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_CONSTRAINT__ADVICE = ADVICE_CONSTRAINT__ADVICE;

	/**
	 * The feature id for the '<em><b>Owning Advice</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_CONSTRAINT__OWNING_ADVICE = ADVICE_CONSTRAINT__OWNING_ADVICE;

	/**
	 * The feature id for the '<em><b>Permission</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_CONSTRAINT__PERMISSION = ADVICE_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Relationship Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_CONSTRAINT_FEATURE_COUNT = ADVICE_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Approve Request</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST = ADVICE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST;

	/**
	 * The operation id for the '<em>Get Advice</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_CONSTRAINT___GET_ADVICE = ADVICE_CONSTRAINT___GET_ADVICE;

	/**
	 * The number of operations of the '<em>Relationship Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_CONSTRAINT_OPERATION_COUNT = ADVICE_CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.EndPermissionImpl <em>End Permission</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.EndPermissionImpl
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getEndPermission()
	 * @generated
	 */
	int END_PERMISSION = 9;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int END_PERMISSION__FILTER = FiltersPackage.FILTERED_ELEMENT__FILTER;

	/**
	 * The feature id for the '<em><b>End Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int END_PERMISSION__END_KIND = FiltersPackage.FILTERED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Permitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int END_PERMISSION__PERMITTED = FiltersPackage.FILTERED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>End Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int END_PERMISSION__END_FILTER = FiltersPackage.FILTERED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>End Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int END_PERMISSION_FEATURE_COUNT = FiltersPackage.FILTERED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int END_PERMISSION___MATCHES__EOBJECT = FiltersPackage.FILTERED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>End Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int END_PERMISSION_OPERATION_COUNT = FiltersPackage.FILTERED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind <em>Reference Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getReferenceKind()
	 * @generated
	 */
	int REFERENCE_KIND = 10;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind <em>Element Type Relationship Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getElementTypeRelationshipKind()
	 * @generated
	 */
	int ELEMENT_TYPE_RELATIONSHIP_KIND = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndKind <em>End Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndKind
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getEndKind()
	 * @generated
	 */
	int END_KIND = 12;

	/**
	 * The meta object id for the '<em>Edit Command Request</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getEditCommandRequest()
	 * @generated
	 */
	int EDIT_COMMAND_REQUEST = 13;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration
	 * @generated
	 */
	EClass getConstraintAdviceConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration#getConstraints <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Constraint</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration#getConstraints()
	 * @see #getConstraintAdviceConfiguration()
	 * @generated
	 */
	EReference getConstraintAdviceConfiguration_Constraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint <em>Advice Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Advice Constraint</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint
	 * @generated
	 */
	EClass getAdviceConstraint();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getComposite <em>Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the container reference '<em>Composite</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getComposite()
	 * @see #getAdviceConstraint()
	 * @generated
	 */
	EReference getAdviceConstraint_Composite();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getAdvice <em>Advice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Advice</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getAdvice()
	 * @see #getAdviceConstraint()
	 * @generated
	 */
	EReference getAdviceConstraint_Advice();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getOwningAdvice <em>Owning Advice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the container reference '<em>Owning Advice</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getOwningAdvice()
	 * @see #getAdviceConstraint()
	 * @generated
	 */
	EReference getAdviceConstraint_OwningAdvice();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest) <em>Approve Request</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Approve Request</em>' operation.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 * @generated
	 */
	EOperation getAdviceConstraint__ApproveRequest__IEditCommandRequest();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getAdvice() <em>Get Advice</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Get Advice</em>' operation.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint#getAdvice()
	 * @generated
	 */
	EOperation getAdviceConstraint__GetAdvice();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint <em>Reference Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Reference Constraint</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint
	 * @generated
	 */
	EClass getReferenceConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint#getPermissions <em>Permission</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Permission</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint#getPermissions()
	 * @see #getReferenceConstraint()
	 * @generated
	 */
	EReference getReferenceConstraint_Permission();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission <em>Reference Permission</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Reference Permission</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission
	 * @generated
	 */
	EClass getReferencePermission();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission#isPermitted <em>Permitted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Permitted</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission#isPermitted()
	 * @see #getReferencePermission()
	 * @generated
	 */
	EAttribute getReferencePermission_Permitted();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission#matches(org.eclipse.emf.ecore.EReference) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Matches</em>' operation.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission#matches(org.eclipse.emf.ecore.EReference)
	 * @generated
	 */
	EOperation getReferencePermission__Matches__EReference();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.AnyReference <em>Any Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Any Reference</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AnyReference
	 * @generated
	 */
	EClass getAnyReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.constraints.AnyReference#getReferenceKind <em>Reference Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Reference Kind</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.AnyReference#getReferenceKind()
	 * @see #getAnyReference()
	 * @generated
	 */
	EAttribute getAnyReference_ReferenceKind();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.Reference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Reference</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.Reference
	 * @generated
	 */
	EClass getReference();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.emf.types.constraints.Reference#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Reference</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.Reference#getReference()
	 * @see #getReference()
	 * @generated
	 */
	EReference getReference_Reference();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter <em>Element Type Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Element Type Filter</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter
	 * @generated
	 */
	EClass getElementTypeFilter();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Element Type</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#getElementType()
	 * @see #getElementTypeFilter()
	 * @generated
	 */
	EReference getElementTypeFilter_ElementType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#getRelationship <em>Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Relationship</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#getRelationship()
	 * @see #getElementTypeFilter()
	 * @generated
	 */
	EAttribute getElementTypeFilter_Relationship();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.RelationshipConstraint <em>Relationship Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Relationship Constraint</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.RelationshipConstraint
	 * @generated
	 */
	EClass getRelationshipConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.emf.types.constraints.RelationshipConstraint#getPermissions <em>Permission</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Permission</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.RelationshipConstraint#getPermissions()
	 * @see #getRelationshipConstraint()
	 * @generated
	 */
	EReference getRelationshipConstraint_Permission();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission <em>End Permission</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>End Permission</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndPermission
	 * @generated
	 */
	EClass getEndPermission();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#getEndKind <em>End Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>End Kind</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#getEndKind()
	 * @see #getEndPermission()
	 * @generated
	 */
	EAttribute getEndPermission_EndKind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#isPermitted <em>Permitted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Permitted</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#isPermitted()
	 * @see #getEndPermission()
	 * @generated
	 */
	EAttribute getEndPermission_Permitted();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#getEndFilter <em>End Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>End Filter</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#getEndFilter()
	 * @see #getEndPermission()
	 * @generated
	 */
	EReference getEndPermission_EndFilter();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#matches(org.eclipse.emf.ecore.EObject) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the '<em>Matches</em>' operation.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndPermission#matches(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getEndPermission__Matches__EObject();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint <em>Composite Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Composite Constraint</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint
	 * @generated
	 */
	EClass getCompositeConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getConstraints <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Constraint</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getConstraints()
	 * @see #getCompositeConstraint()
	 * @generated
	 */
	EReference getCompositeConstraint_Constraint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint#getOperator()
	 * @see #getCompositeConstraint()
	 * @generated
	 */
	EAttribute getCompositeConstraint_Operator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind <em>Reference Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Reference Kind</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind
	 * @generated
	 */
	EEnum getReferenceKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind <em>Element Type Relationship Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Element Type Relationship Kind</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind
	 * @generated
	 */
	EEnum getElementTypeRelationshipKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndKind <em>End Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>End Kind</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndKind
	 * @generated
	 */
	EEnum getEndKind();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest <em>Edit Command Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for data type '<em>Edit Command Request</em>'.
	 * @see org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest
	 * @model instanceClass="org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest"
	 * @generated
	 */
	EDataType getEditCommandRequest();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConstraintAdviceFactory getConstraintAdviceFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdviceConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdviceConfigurationImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getConstraintAdviceConfiguration()
		 * @generated
		 */
		EClass CONSTRAINT_ADVICE_CONFIGURATION = eINSTANCE.getConstraintAdviceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT = eINSTANCE.getConstraintAdviceConfiguration_Constraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.AdviceConstraintImpl <em>Advice Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.AdviceConstraintImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getAdviceConstraint()
		 * @generated
		 */
		EClass ADVICE_CONSTRAINT = eINSTANCE.getAdviceConstraint();

		/**
		 * The meta object literal for the '<em><b>Composite</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ADVICE_CONSTRAINT__COMPOSITE = eINSTANCE.getAdviceConstraint_Composite();

		/**
		 * The meta object literal for the '<em><b>Advice</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ADVICE_CONSTRAINT__ADVICE = eINSTANCE.getAdviceConstraint_Advice();

		/**
		 * The meta object literal for the '<em><b>Owning Advice</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ADVICE_CONSTRAINT__OWNING_ADVICE = eINSTANCE.getAdviceConstraint_OwningAdvice();

		/**
		 * The meta object literal for the '<em><b>Approve Request</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation ADVICE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST = eINSTANCE.getAdviceConstraint__ApproveRequest__IEditCommandRequest();

		/**
		 * The meta object literal for the '<em><b>Get Advice</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation ADVICE_CONSTRAINT___GET_ADVICE = eINSTANCE.getAdviceConstraint__GetAdvice();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceConstraintImpl <em>Reference Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceConstraintImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getReferenceConstraint()
		 * @generated
		 */
		EClass REFERENCE_CONSTRAINT = eINSTANCE.getReferenceConstraint();

		/**
		 * The meta object literal for the '<em><b>Permission</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference REFERENCE_CONSTRAINT__PERMISSION = eINSTANCE.getReferenceConstraint_Permission();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferencePermissionImpl <em>Reference Permission</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferencePermissionImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getReferencePermission()
		 * @generated
		 */
		EClass REFERENCE_PERMISSION = eINSTANCE.getReferencePermission();

		/**
		 * The meta object literal for the '<em><b>Permitted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute REFERENCE_PERMISSION__PERMITTED = eINSTANCE.getReferencePermission_Permitted();

		/**
		 * The meta object literal for the '<em><b>Matches</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation REFERENCE_PERMISSION___MATCHES__EREFERENCE = eINSTANCE.getReferencePermission__Matches__EReference();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.AnyReferenceImpl <em>Any Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.AnyReferenceImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getAnyReference()
		 * @generated
		 */
		EClass ANY_REFERENCE = eINSTANCE.getAnyReference();

		/**
		 * The meta object literal for the '<em><b>Reference Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ANY_REFERENCE__REFERENCE_KIND = eINSTANCE.getAnyReference_ReferenceKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceImpl <em>Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getReference()
		 * @generated
		 */
		EClass REFERENCE = eINSTANCE.getReference();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference REFERENCE__REFERENCE = eINSTANCE.getReference_Reference();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ElementTypeFilterImpl <em>Element Type Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ElementTypeFilterImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getElementTypeFilter()
		 * @generated
		 */
		EClass ELEMENT_TYPE_FILTER = eINSTANCE.getElementTypeFilter();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ELEMENT_TYPE_FILTER__ELEMENT_TYPE = eINSTANCE.getElementTypeFilter_ElementType();

		/**
		 * The meta object literal for the '<em><b>Relationship</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ELEMENT_TYPE_FILTER__RELATIONSHIP = eINSTANCE.getElementTypeFilter_Relationship();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.RelationshipConstraintImpl <em>Relationship Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.RelationshipConstraintImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getRelationshipConstraint()
		 * @generated
		 */
		EClass RELATIONSHIP_CONSTRAINT = eINSTANCE.getRelationshipConstraint();

		/**
		 * The meta object literal for the '<em><b>Permission</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference RELATIONSHIP_CONSTRAINT__PERMISSION = eINSTANCE.getRelationshipConstraint_Permission();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.EndPermissionImpl <em>End Permission</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.EndPermissionImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getEndPermission()
		 * @generated
		 */
		EClass END_PERMISSION = eINSTANCE.getEndPermission();

		/**
		 * The meta object literal for the '<em><b>End Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute END_PERMISSION__END_KIND = eINSTANCE.getEndPermission_EndKind();

		/**
		 * The meta object literal for the '<em><b>Permitted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute END_PERMISSION__PERMITTED = eINSTANCE.getEndPermission_Permitted();

		/**
		 * The meta object literal for the '<em><b>End Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference END_PERMISSION__END_FILTER = eINSTANCE.getEndPermission_EndFilter();

		/**
		 * The meta object literal for the '<em><b>Matches</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EOperation END_PERMISSION___MATCHES__EOBJECT = eINSTANCE.getEndPermission__Matches__EObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.CompositeConstraintImpl <em>Composite Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.CompositeConstraintImpl
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getCompositeConstraint()
		 * @generated
		 */
		EClass COMPOSITE_CONSTRAINT = eINSTANCE.getCompositeConstraint();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPOSITE_CONSTRAINT__CONSTRAINT = eINSTANCE.getCompositeConstraint_Constraint();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute COMPOSITE_CONSTRAINT__OPERATOR = eINSTANCE.getCompositeConstraint_Operator();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind <em>Reference Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getReferenceKind()
		 * @generated
		 */
		EEnum REFERENCE_KIND = eINSTANCE.getReferenceKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind <em>Element Type Relationship Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getElementTypeRelationshipKind()
		 * @generated
		 */
		EEnum ELEMENT_TYPE_RELATIONSHIP_KIND = eINSTANCE.getElementTypeRelationshipKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.constraints.EndKind <em>End Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.EndKind
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getEndKind()
		 * @generated
		 */
		EEnum END_KIND = eINSTANCE.getEndKind();

		/**
		 * The meta object literal for the '<em>Edit Command Request</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest
		 * @see org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdvicePackageImpl#getEditCommandRequest()
		 * @generated
		 */
		EDataType EDIT_COMMAND_REQUEST = eINSTANCE.getEditCommandRequest();

	}

} // ConstraintAdvicePackage
