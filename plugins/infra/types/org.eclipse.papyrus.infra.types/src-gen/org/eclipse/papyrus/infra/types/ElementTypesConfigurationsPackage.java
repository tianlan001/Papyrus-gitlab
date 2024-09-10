/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 568782, 568853
 */
package org.eclipse.papyrus.infra.types;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
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
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL'"
 * @generated
 */
public interface ElementTypesConfigurationsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "types";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/elementtypesconfigurations/1.2";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "elementtypesconfigurations";

	/**
	 * The package content type ID.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eCONTENT_TYPE = "org.eclipse.papyrus.infra.types";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ElementTypesConfigurationsPackage eINSTANCE = org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.ConfigurationElementImpl <em>Configuration Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.ConfigurationElementImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getConfigurationElement()
	 * @generated
	 */
	int CONFIGURATION_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT__OWNING_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT__ANNOTATIONS = 2;

	/**
	 * The number of structural features of the '<em>Configuration Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Configuration Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl <em>Element Type Set Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getElementTypeSetConfiguration()
	 * @generated
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__DESCRIPTION = CONFIGURATION_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__OWNING_TYPE = CONFIGURATION_ELEMENT__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__ANNOTATIONS = CONFIGURATION_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__IDENTIFIER = CONFIGURATION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__NAME = CONFIGURATION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Advice Bindings Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS = CONFIGURATION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>All Advice Bindings</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__ALL_ADVICE_BINDINGS = CONFIGURATION_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Element Type Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS = CONFIGURATION_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Metamodel Ns URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI = CONFIGURATION_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Element Type Set Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION_FEATURE_COUNT = CONFIGURATION_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Get All Advice Bindings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION___GET_ALL_ADVICE_BINDINGS = CONFIGURATION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Element Type Set Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_SET_CONFIGURATION_OPERATION_COUNT = CONFIGURATION_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl <em>Element Type Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getElementTypeConfiguration()
	 * @generated
	 */
	int ELEMENT_TYPE_CONFIGURATION = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__DESCRIPTION = CONFIGURATION_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__OWNING_TYPE = CONFIGURATION_ELEMENT__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__ANNOTATIONS = CONFIGURATION_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__IDENTIFIER = CONFIGURATION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__NAME = CONFIGURATION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__HINT = CONFIGURATION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__KIND = CONFIGURATION_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Icon Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY = CONFIGURATION_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Owned Advice</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE = CONFIGURATION_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__OWNING_SET = CONFIGURATION_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Owned Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS = CONFIGURATION_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Element Type Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT = CONFIGURATION_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Element Type Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_TYPE_CONFIGURATION_OPERATION_COUNT = CONFIGURATION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.IconEntryImpl <em>Icon Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.IconEntryImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getIconEntry()
	 * @generated
	 */
	int ICON_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Icon Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON_ENTRY__ICON_PATH = 0;

	/**
	 * The feature id for the '<em><b>Bundle Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON_ENTRY__BUNDLE_ID = 1;

	/**
	 * The number of structural features of the '<em>Icon Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Icon Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.IdentifiedConfiguration <em>Identified Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.IdentifiedConfiguration
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getIdentifiedConfiguration()
	 * @generated
	 */
	int IDENTIFIED_CONFIGURATION = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.NamedConfiguration <em>Named Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.NamedConfiguration
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getNamedConfiguration()
	 * @generated
	 */
	int NAMED_CONFIGURATION = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.AdviceConfigurationImpl <em>Advice Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.AdviceConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAdviceConfiguration()
	 * @generated
	 */
	int ADVICE_CONFIGURATION = 5;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONFIGURATION__DESCRIPTION = CONFIGURATION_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONFIGURATION__OWNING_TYPE = CONFIGURATION_ELEMENT__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONFIGURATION__ANNOTATIONS = CONFIGURATION_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONFIGURATION__BEFORE = CONFIGURATION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONFIGURATION__AFTER = CONFIGURATION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONFIGURATION_FEATURE_COUNT = CONFIGURATION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_CONFIGURATION_OPERATION_COUNT = CONFIGURATION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl <em>Abstract Advice Binding Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION = 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__DESCRIPTION = ADVICE_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TYPE = ADVICE_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__ANNOTATIONS = ADVICE_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__BEFORE = ADVICE_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__AFTER = ADVICE_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__IDENTIFIER = ADVICE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET = ADVICE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION = ADVICE_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION = ADVICE_CONFIGURATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__INHERITANCE = ADVICE_CONFIGURATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Apply To All Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES = ADVICE_CONFIGURATION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_SET = ADVICE_CONFIGURATION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Element Type Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET = ADVICE_CONFIGURATION_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Owning Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TARGET = ADVICE_CONFIGURATION_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Abstract Advice Binding Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT = ADVICE_CONFIGURATION_FEATURE_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Element Type Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION___GET_ELEMENT_TYPE_SET = ADVICE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Abstract Advice Binding Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ADVICE_BINDING_CONFIGURATION_OPERATION_COUNT = ADVICE_CONFIGURATION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.ContainerConfigurationImpl <em>Container Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.ContainerConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getContainerConfiguration()
	 * @generated
	 */
	int CONTAINER_CONFIGURATION = 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_CONFIGURATION__DESCRIPTION = CONFIGURATION_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_CONFIGURATION__OWNING_TYPE = CONFIGURATION_ELEMENT__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_CONFIGURATION__ANNOTATIONS = CONFIGURATION_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Container Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_CONFIGURATION__CONTAINER_MATCHER_CONFIGURATION = CONFIGURATION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contained Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_CONFIGURATION__CONTAINED_TYPE = CONFIGURATION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>EContainment Features</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_CONFIGURATION__ECONTAINMENT_FEATURES = CONFIGURATION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Container Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_CONFIGURATION_FEATURE_COUNT = CONFIGURATION_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Container Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_CONFIGURATION_OPERATION_COUNT = CONFIGURATION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.AbstractMatcherConfigurationImpl <em>Abstract Matcher Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.AbstractMatcherConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAbstractMatcherConfiguration()
	 * @generated
	 */
	int ABSTRACT_MATCHER_CONFIGURATION = 7;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MATCHER_CONFIGURATION__DESCRIPTION = CONFIGURATION_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE = CONFIGURATION_ELEMENT__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MATCHER_CONFIGURATION__ANNOTATIONS = CONFIGURATION_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Matched Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE = CONFIGURATION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Matcher Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MATCHER_CONFIGURATION_FEATURE_COUNT = CONFIGURATION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Abstract Matcher Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MATCHER_CONFIGURATION_OPERATION_COUNT = CONFIGURATION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.SpecializationTypeConfigurationImpl <em>Specialization Type Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.SpecializationTypeConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getSpecializationTypeConfiguration()
	 * @generated
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION = 8;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__DESCRIPTION = ELEMENT_TYPE_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__OWNING_TYPE = ELEMENT_TYPE_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__ANNOTATIONS = ELEMENT_TYPE_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__IDENTIFIER = ELEMENT_TYPE_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__NAME = ELEMENT_TYPE_CONFIGURATION__NAME;

	/**
	 * The feature id for the '<em><b>Hint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__HINT = ELEMENT_TYPE_CONFIGURATION__HINT;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__KIND = ELEMENT_TYPE_CONFIGURATION__KIND;

	/**
	 * The feature id for the '<em><b>Icon Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__ICON_ENTRY = ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY;

	/**
	 * The feature id for the '<em><b>Owned Advice</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__OWNED_ADVICE = ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__OWNING_SET = ELEMENT_TYPE_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Owned Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS = ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS;

	/**
	 * The feature id for the '<em><b>Edit Helper Advice Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Specialized Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__SPECIALIZED_TYPES = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Specialization Type Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION_FEATURE_COUNT = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Specialization Type Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPECIALIZATION_TYPE_CONFIGURATION_OPERATION_COUNT = ELEMENT_TYPE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.AbstractEditHelperAdviceConfigurationImpl <em>Abstract Edit Helper Advice Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.AbstractEditHelperAdviceConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAbstractEditHelperAdviceConfiguration()
	 * @generated
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION = 9;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__DESCRIPTION = ADVICE_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__OWNING_TYPE = ADVICE_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__ANNOTATIONS = ADVICE_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__BEFORE = ADVICE_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__AFTER = ADVICE_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__TARGET = ADVICE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Edit Helper Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION_FEATURE_COUNT = ADVICE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Abstract Edit Helper Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION_OPERATION_COUNT = ADVICE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED_CONFIGURATION__IDENTIFIER = 0;

	/**
	 * The number of structural features of the '<em>Identified Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED_CONFIGURATION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Identified Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIED_CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CONFIGURATION__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CONFIGURATION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Named Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.AnnotationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 12;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Configuration Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__CONFIGURATION_ELEMENT = 2;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.MetamodelTypeConfigurationImpl <em>Metamodel Type Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.MetamodelTypeConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getMetamodelTypeConfiguration()
	 * @generated
	 */
	int METAMODEL_TYPE_CONFIGURATION = 13;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__DESCRIPTION = ELEMENT_TYPE_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__OWNING_TYPE = ELEMENT_TYPE_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__ANNOTATIONS = ELEMENT_TYPE_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__IDENTIFIER = ELEMENT_TYPE_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__NAME = ELEMENT_TYPE_CONFIGURATION__NAME;

	/**
	 * The feature id for the '<em><b>Hint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__HINT = ELEMENT_TYPE_CONFIGURATION__HINT;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__KIND = ELEMENT_TYPE_CONFIGURATION__KIND;

	/**
	 * The feature id for the '<em><b>Icon Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__ICON_ENTRY = ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY;

	/**
	 * The feature id for the '<em><b>Owned Advice</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__OWNED_ADVICE = ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__OWNING_SET = ELEMENT_TYPE_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Owned Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS = ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS;

	/**
	 * The feature id for the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__ECLASS = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edit Helper Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION__EDIT_HELPER_CLASS_NAME = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Metamodel Type Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION_FEATURE_COUNT = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Metamodel Type Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAMODEL_TYPE_CONFIGURATION_OPERATION_COUNT = ELEMENT_TYPE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.EditHelperAdviceConfigurationImpl <em>Edit Helper Advice Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.EditHelperAdviceConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getEditHelperAdviceConfiguration()
	 * @generated
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION = 14;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION__DESCRIPTION = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION__OWNING_TYPE = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION__ANNOTATIONS = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION__BEFORE = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION__AFTER = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION__TARGET = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Edit Helper Advice Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Edit Helper Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION_FEATURE_COUNT = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Edit Helper Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_HELPER_ADVICE_CONFIGURATION_OPERATION_COUNT = ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.AdviceBindingConfigurationImpl <em>Advice Binding Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.AdviceBindingConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAdviceBindingConfiguration()
	 * @generated
	 */
	int ADVICE_BINDING_CONFIGURATION = 15;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__DESCRIPTION = ABSTRACT_ADVICE_BINDING_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__OWNING_TYPE = ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__ANNOTATIONS = ABSTRACT_ADVICE_BINDING_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__BEFORE = ABSTRACT_ADVICE_BINDING_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__AFTER = ABSTRACT_ADVICE_BINDING_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__IDENTIFIER = ABSTRACT_ADVICE_BINDING_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__TARGET = ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION = ABSTRACT_ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION = ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__INHERITANCE = ABSTRACT_ADVICE_BINDING_CONFIGURATION__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Apply To All Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES = ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__OWNING_SET = ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Element Type Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET = ABSTRACT_ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET;

	/**
	 * The feature id for the '<em><b>Owning Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__OWNING_TARGET = ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TARGET;

	/**
	 * The feature id for the '<em><b>Edit Helper Advice Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME = ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Advice Binding Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT = ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Element Type Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION___GET_ELEMENT_TYPE_SET = ABSTRACT_ADVICE_BINDING_CONFIGURATION___GET_ELEMENT_TYPE_SET;

	/**
	 * The number of operations of the '<em>Advice Binding Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVICE_BINDING_CONFIGURATION_OPERATION_COUNT = ABSTRACT_ADVICE_BINDING_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.MatcherConfigurationImpl <em>Matcher Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.MatcherConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getMatcherConfiguration()
	 * @generated
	 */
	int MATCHER_CONFIGURATION = 16;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCHER_CONFIGURATION__DESCRIPTION = ABSTRACT_MATCHER_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCHER_CONFIGURATION__OWNING_TYPE = ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCHER_CONFIGURATION__ANNOTATIONS = ABSTRACT_MATCHER_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Matched Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCHER_CONFIGURATION__MATCHED_TYPE = ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE;

	/**
	 * The feature id for the '<em><b>Matcher Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCHER_CONFIGURATION__MATCHER_CLASS_NAME = ABSTRACT_MATCHER_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Matcher Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCHER_CONFIGURATION_FEATURE_COUNT = ABSTRACT_MATCHER_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Matcher Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCHER_CONFIGURATION_OPERATION_COUNT = ABSTRACT_MATCHER_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.ExternallyRegisteredTypeImpl <em>Externally Registered Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.ExternallyRegisteredTypeImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getExternallyRegisteredType()
	 * @generated
	 */
	int EXTERNALLY_REGISTERED_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__DESCRIPTION = ELEMENT_TYPE_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__OWNING_TYPE = ELEMENT_TYPE_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__ANNOTATIONS = ELEMENT_TYPE_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__IDENTIFIER = ELEMENT_TYPE_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__NAME = ELEMENT_TYPE_CONFIGURATION__NAME;

	/**
	 * The feature id for the '<em><b>Hint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__HINT = ELEMENT_TYPE_CONFIGURATION__HINT;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__KIND = ELEMENT_TYPE_CONFIGURATION__KIND;

	/**
	 * The feature id for the '<em><b>Icon Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__ICON_ENTRY = ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY;

	/**
	 * The feature id for the '<em><b>Owned Advice</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__OWNED_ADVICE = ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__OWNING_SET = ELEMENT_TYPE_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Owned Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE__OWNED_CONFIGURATIONS = ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS;

	/**
	 * The number of structural features of the '<em>Externally Registered Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE_FEATURE_COUNT = ELEMENT_TYPE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Externally Registered Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_TYPE_OPERATION_COUNT = ELEMENT_TYPE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.impl.ExternallyRegisteredAdviceImpl <em>Externally Registered Advice</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.impl.ExternallyRegisteredAdviceImpl
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getExternallyRegisteredAdvice()
	 * @generated
	 */
	int EXTERNALLY_REGISTERED_ADVICE = 18;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__DESCRIPTION = ADVICE_BINDING_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__OWNING_TYPE = ADVICE_BINDING_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__ANNOTATIONS = ADVICE_BINDING_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__BEFORE = ADVICE_BINDING_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__AFTER = ADVICE_BINDING_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__IDENTIFIER = ADVICE_BINDING_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__TARGET = ADVICE_BINDING_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__CONTAINER_CONFIGURATION = ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__MATCHER_CONFIGURATION = ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__INHERITANCE = ADVICE_BINDING_CONFIGURATION__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Apply To All Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__APPLY_TO_ALL_TYPES = ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__OWNING_SET = ADVICE_BINDING_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Element Type Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__ELEMENT_TYPE_SET = ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET;

	/**
	 * The feature id for the '<em><b>Owning Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__OWNING_TARGET = ADVICE_BINDING_CONFIGURATION__OWNING_TARGET;

	/**
	 * The feature id for the '<em><b>Edit Helper Advice Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE__EDIT_HELPER_ADVICE_CLASS_NAME = ADVICE_BINDING_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME;

	/**
	 * The number of structural features of the '<em>Externally Registered Advice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE_FEATURE_COUNT = ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Element Type Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE___GET_ELEMENT_TYPE_SET = ADVICE_BINDING_CONFIGURATION___GET_ELEMENT_TYPE_SET;

	/**
	 * The number of operations of the '<em>Externally Registered Advice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNALLY_REGISTERED_ADVICE_OPERATION_COUNT = ADVICE_BINDING_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.InheritanceKind <em>Inheritance Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.InheritanceKind
	 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getInheritanceKind()
	 * @generated
	 */
	int INHERITANCE_KIND = 19;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration <em>Element Type Set Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Type Set Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration
	 * @generated
	 */
	EClass getElementTypeSetConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getElementTypeConfigurations <em>Element Type Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Element Type Configurations</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getElementTypeConfigurations()
	 * @see #getElementTypeSetConfiguration()
	 * @generated
	 */
	EReference getElementTypeSetConfiguration_ElementTypeConfigurations();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAdviceBindingsConfigurations <em>Advice Bindings Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Advice Bindings Configurations</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAdviceBindingsConfigurations()
	 * @see #getElementTypeSetConfiguration()
	 * @generated
	 */
	EReference getElementTypeSetConfiguration_AdviceBindingsConfigurations();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings <em>All Advice Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All Advice Bindings</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings()
	 * @see #getElementTypeSetConfiguration()
	 * @generated
	 */
	EReference getElementTypeSetConfiguration_AllAdviceBindings();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getMetamodelNsURI <em>Metamodel Ns URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metamodel Ns URI</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getMetamodelNsURI()
	 * @see #getElementTypeSetConfiguration()
	 * @generated
	 */
	EAttribute getElementTypeSetConfiguration_MetamodelNsURI();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings() <em>Get All Advice Bindings</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Advice Bindings</em>' operation.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration#getAllAdviceBindings()
	 * @generated
	 */
	EOperation getElementTypeSetConfiguration__GetAllAdviceBindings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.ConfigurationElement <em>Configuration Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration Element</em>'.
	 * @see org.eclipse.papyrus.infra.types.ConfigurationElement
	 * @generated
	 */
	EClass getConfigurationElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.ConfigurationElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.papyrus.infra.types.ConfigurationElement#getDescription()
	 * @see #getConfigurationElement()
	 * @generated
	 */
	EAttribute getConfigurationElement_Description();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.types.ConfigurationElement#getOwningType <em>Owning Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owning Type</em>'.
	 * @see org.eclipse.papyrus.infra.types.ConfigurationElement#getOwningType()
	 * @see #getConfigurationElement()
	 * @generated
	 */
	EReference getConfigurationElement_OwningType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.types.ConfigurationElement#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see org.eclipse.papyrus.infra.types.ConfigurationElement#getAnnotations()
	 * @see #getConfigurationElement()
	 * @generated
	 */
	EReference getConfigurationElement_Annotations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration <em>Element Type Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Type Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration
	 * @generated
	 */
	EClass getElementTypeConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getHint <em>Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hint</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getHint()
	 * @see #getElementTypeConfiguration()
	 * @generated
	 */
	EAttribute getElementTypeConfiguration_Hint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getKind()
	 * @see #getElementTypeConfiguration()
	 * @generated
	 */
	EAttribute getElementTypeConfiguration_Kind();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getIconEntry <em>Icon Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Icon Entry</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getIconEntry()
	 * @see #getElementTypeConfiguration()
	 * @generated
	 */
	EReference getElementTypeConfiguration_IconEntry();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedAdvice <em>Owned Advice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Owned Advice</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedAdvice()
	 * @see #getElementTypeConfiguration()
	 * @generated
	 */
	EReference getElementTypeConfiguration_OwnedAdvice();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwningSet <em>Owning Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owning Set</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwningSet()
	 * @see #getElementTypeConfiguration()
	 * @generated
	 */
	EReference getElementTypeConfiguration_OwningSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedConfigurations <em>Owned Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Configurations</em>'.
	 * @see org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedConfigurations()
	 * @see #getElementTypeConfiguration()
	 * @generated
	 */
	EReference getElementTypeConfiguration_OwnedConfigurations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.IconEntry <em>Icon Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Icon Entry</em>'.
	 * @see org.eclipse.papyrus.infra.types.IconEntry
	 * @generated
	 */
	EClass getIconEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.IconEntry#getIconPath <em>Icon Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon Path</em>'.
	 * @see org.eclipse.papyrus.infra.types.IconEntry#getIconPath()
	 * @see #getIconEntry()
	 * @generated
	 */
	EAttribute getIconEntry_IconPath();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.IconEntry#getBundleId <em>Bundle Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bundle Id</em>'.
	 * @see org.eclipse.papyrus.infra.types.IconEntry#getBundleId()
	 * @see #getIconEntry()
	 * @generated
	 */
	EAttribute getIconEntry_BundleId();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.IdentifiedConfiguration <em>Identified Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identified Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.IdentifiedConfiguration
	 * @generated
	 */
	EClass getIdentifiedConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.IdentifiedConfiguration#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see org.eclipse.papyrus.infra.types.IdentifiedConfiguration#getIdentifier()
	 * @see #getIdentifiedConfiguration()
	 * @generated
	 */
	EAttribute getIdentifiedConfiguration_Identifier();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.NamedConfiguration <em>Named Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.NamedConfiguration
	 * @generated
	 */
	EClass getNamedConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.NamedConfiguration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.types.NamedConfiguration#getName()
	 * @see #getNamedConfiguration()
	 * @generated
	 */
	EAttribute getNamedConfiguration_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see org.eclipse.papyrus.infra.types.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.Annotation#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.eclipse.papyrus.infra.types.Annotation#getSource()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Source();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.Annotation#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.papyrus.infra.types.Annotation#getValue()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Value();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.types.Annotation#getConfigurationElement <em>Configuration Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Configuration Element</em>'.
	 * @see org.eclipse.papyrus.infra.types.Annotation#getConfigurationElement()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_ConfigurationElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration <em>Abstract Advice Binding Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Advice Binding Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration
	 * @generated
	 */
	EClass getAbstractAdviceBindingConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getTarget()
	 * @see #getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	EReference getAbstractAdviceBindingConfiguration_Target();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getContainerConfiguration <em>Container Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Container Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getContainerConfiguration()
	 * @see #getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	EReference getAbstractAdviceBindingConfiguration_ContainerConfiguration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getMatcherConfiguration <em>Matcher Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Matcher Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getMatcherConfiguration()
	 * @see #getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	EReference getAbstractAdviceBindingConfiguration_MatcherConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getInheritance <em>Inheritance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inheritance</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getInheritance()
	 * @see #getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	EAttribute getAbstractAdviceBindingConfiguration_Inheritance();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#isApplyToAllTypes <em>Apply To All Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Apply To All Types</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#isApplyToAllTypes()
	 * @see #getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	EAttribute getAbstractAdviceBindingConfiguration_ApplyToAllTypes();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningSet <em>Owning Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owning Set</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningSet()
	 * @see #getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	EReference getAbstractAdviceBindingConfiguration_OwningSet();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet <em>Element Type Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element Type Set</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet()
	 * @see #getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	EReference getAbstractAdviceBindingConfiguration_ElementTypeSet();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningTarget <em>Owning Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owning Target</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getOwningTarget()
	 * @see #getAbstractAdviceBindingConfiguration()
	 * @generated
	 */
	EReference getAbstractAdviceBindingConfiguration_OwningTarget();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet() <em>Get Element Type Set</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Element Type Set</em>' operation.
	 * @see org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration#getElementTypeSet()
	 * @generated
	 */
	EOperation getAbstractAdviceBindingConfiguration__GetElementTypeSet();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.AdviceConfiguration <em>Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Advice Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.AdviceConfiguration
	 * @generated
	 */
	EClass getAdviceConfiguration();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.types.AdviceConfiguration#getBefore <em>Before</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Before</em>'.
	 * @see org.eclipse.papyrus.infra.types.AdviceConfiguration#getBefore()
	 * @see #getAdviceConfiguration()
	 * @generated
	 */
	EReference getAdviceConfiguration_Before();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.types.AdviceConfiguration#getAfter <em>After</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>After</em>'.
	 * @see org.eclipse.papyrus.infra.types.AdviceConfiguration#getAfter()
	 * @see #getAdviceConfiguration()
	 * @generated
	 */
	EReference getAdviceConfiguration_After();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.ContainerConfiguration <em>Container Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.ContainerConfiguration
	 * @generated
	 */
	EClass getContainerConfiguration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.types.ContainerConfiguration#getContainerMatcherConfiguration <em>Container Matcher Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Container Matcher Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.ContainerConfiguration#getContainerMatcherConfiguration()
	 * @see #getContainerConfiguration()
	 * @generated
	 */
	EReference getContainerConfiguration_ContainerMatcherConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.ContainerConfiguration#getContainedType <em>Contained Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contained Type</em>'.
	 * @see org.eclipse.papyrus.infra.types.ContainerConfiguration#getContainedType()
	 * @see #getContainerConfiguration()
	 * @generated
	 */
	EReference getContainerConfiguration_ContainedType();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.types.ContainerConfiguration#getEContainmentFeatures <em>EContainment Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>EContainment Features</em>'.
	 * @see org.eclipse.papyrus.infra.types.ContainerConfiguration#getEContainmentFeatures()
	 * @see #getContainerConfiguration()
	 * @generated
	 */
	EReference getContainerConfiguration_EContainmentFeatures();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration <em>Abstract Matcher Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Matcher Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration
	 * @generated
	 */
	EClass getAbstractMatcherConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration#getMatchedType <em>Matched Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Matched Type</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration#getMatchedType()
	 * @see #getAbstractMatcherConfiguration()
	 * @generated
	 */
	EReference getAbstractMatcherConfiguration_MatchedType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration <em>Specialization Type Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Specialization Type Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration
	 * @generated
	 */
	EClass getSpecializationTypeConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getEditHelperAdviceConfiguration <em>Edit Helper Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Edit Helper Advice Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getEditHelperAdviceConfiguration()
	 * @see #getSpecializationTypeConfiguration()
	 * @generated
	 */
	EReference getSpecializationTypeConfiguration_EditHelperAdviceConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getContainerConfiguration <em>Container Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Container Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getContainerConfiguration()
	 * @see #getSpecializationTypeConfiguration()
	 * @generated
	 */
	EReference getSpecializationTypeConfiguration_ContainerConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getMatcherConfiguration <em>Matcher Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Matcher Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getMatcherConfiguration()
	 * @see #getSpecializationTypeConfiguration()
	 * @generated
	 */
	EReference getSpecializationTypeConfiguration_MatcherConfiguration();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getSpecializedTypes <em>Specialized Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Specialized Types</em>'.
	 * @see org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getSpecializedTypes()
	 * @see #getSpecializationTypeConfiguration()
	 * @generated
	 */
	EReference getSpecializationTypeConfiguration_SpecializedTypes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration <em>Abstract Edit Helper Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Edit Helper Advice Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration
	 * @generated
	 */
	EClass getAbstractEditHelperAdviceConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration#getTarget()
	 * @see #getAbstractEditHelperAdviceConfiguration()
	 * @generated
	 */
	EReference getAbstractEditHelperAdviceConfiguration_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration <em>Metamodel Type Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metamodel Type Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration
	 * @generated
	 */
	EClass getMetamodelTypeConfiguration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration#getEClass <em>EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EClass</em>'.
	 * @see org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration#getEClass()
	 * @see #getMetamodelTypeConfiguration()
	 * @generated
	 */
	EReference getMetamodelTypeConfiguration_EClass();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration#getEditHelperClassName <em>Edit Helper Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edit Helper Class Name</em>'.
	 * @see org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration#getEditHelperClassName()
	 * @see #getMetamodelTypeConfiguration()
	 * @generated
	 */
	EAttribute getMetamodelTypeConfiguration_EditHelperClassName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.EditHelperAdviceConfiguration <em>Edit Helper Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edit Helper Advice Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.EditHelperAdviceConfiguration
	 * @generated
	 */
	EClass getEditHelperAdviceConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.EditHelperAdviceConfiguration#getEditHelperAdviceClassName <em>Edit Helper Advice Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edit Helper Advice Class Name</em>'.
	 * @see org.eclipse.papyrus.infra.types.EditHelperAdviceConfiguration#getEditHelperAdviceClassName()
	 * @see #getEditHelperAdviceConfiguration()
	 * @generated
	 */
	EAttribute getEditHelperAdviceConfiguration_EditHelperAdviceClassName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.AdviceBindingConfiguration <em>Advice Binding Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Advice Binding Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.AdviceBindingConfiguration
	 * @generated
	 */
	EClass getAdviceBindingConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.AdviceBindingConfiguration#getEditHelperAdviceClassName <em>Edit Helper Advice Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edit Helper Advice Class Name</em>'.
	 * @see org.eclipse.papyrus.infra.types.AdviceBindingConfiguration#getEditHelperAdviceClassName()
	 * @see #getAdviceBindingConfiguration()
	 * @generated
	 */
	EAttribute getAdviceBindingConfiguration_EditHelperAdviceClassName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.MatcherConfiguration <em>Matcher Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Matcher Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.MatcherConfiguration
	 * @generated
	 */
	EClass getMatcherConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.types.MatcherConfiguration#getMatcherClassName <em>Matcher Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Matcher Class Name</em>'.
	 * @see org.eclipse.papyrus.infra.types.MatcherConfiguration#getMatcherClassName()
	 * @see #getMatcherConfiguration()
	 * @generated
	 */
	EAttribute getMatcherConfiguration_MatcherClassName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.ExternallyRegisteredType <em>Externally Registered Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Externally Registered Type</em>'.
	 * @see org.eclipse.papyrus.infra.types.ExternallyRegisteredType
	 * @generated
	 */
	EClass getExternallyRegisteredType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.ExternallyRegisteredAdvice <em>Externally Registered Advice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Externally Registered Advice</em>'.
	 * @see org.eclipse.papyrus.infra.types.ExternallyRegisteredAdvice
	 * @generated
	 */
	EClass getExternallyRegisteredAdvice();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrus.infra.types.InheritanceKind <em>Inheritance Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Inheritance Kind</em>'.
	 * @see org.eclipse.papyrus.infra.types.InheritanceKind
	 * @generated
	 */
	EEnum getInheritanceKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ElementTypesConfigurationsFactory getElementTypesConfigurationsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl <em>Element Type Set Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getElementTypeSetConfiguration()
		 * @generated
		 */
		EClass ELEMENT_TYPE_SET_CONFIGURATION = eINSTANCE.getElementTypeSetConfiguration();

		/**
		 * The meta object literal for the '<em><b>Element Type Configurations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS = eINSTANCE.getElementTypeSetConfiguration_ElementTypeConfigurations();

		/**
		 * The meta object literal for the '<em><b>Advice Bindings Configurations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS = eINSTANCE.getElementTypeSetConfiguration_AdviceBindingsConfigurations();

		/**
		 * The meta object literal for the '<em><b>All Advice Bindings</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_TYPE_SET_CONFIGURATION__ALL_ADVICE_BINDINGS = eINSTANCE.getElementTypeSetConfiguration_AllAdviceBindings();

		/**
		 * The meta object literal for the '<em><b>Metamodel Ns URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI = eINSTANCE.getElementTypeSetConfiguration_MetamodelNsURI();

		/**
		 * The meta object literal for the '<em><b>Get All Advice Bindings</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ELEMENT_TYPE_SET_CONFIGURATION___GET_ALL_ADVICE_BINDINGS = eINSTANCE.getElementTypeSetConfiguration__GetAllAdviceBindings();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.ConfigurationElementImpl <em>Configuration Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.ConfigurationElementImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getConfigurationElement()
		 * @generated
		 */
		EClass CONFIGURATION_ELEMENT = eINSTANCE.getConfigurationElement();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_ELEMENT__DESCRIPTION = eINSTANCE.getConfigurationElement_Description();

		/**
		 * The meta object literal for the '<em><b>Owning Type</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_ELEMENT__OWNING_TYPE = eINSTANCE.getConfigurationElement_OwningType();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_ELEMENT__ANNOTATIONS = eINSTANCE.getConfigurationElement_Annotations();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl <em>Element Type Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getElementTypeConfiguration()
		 * @generated
		 */
		EClass ELEMENT_TYPE_CONFIGURATION = eINSTANCE.getElementTypeConfiguration();

		/**
		 * The meta object literal for the '<em><b>Hint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_TYPE_CONFIGURATION__HINT = eINSTANCE.getElementTypeConfiguration_Hint();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_TYPE_CONFIGURATION__KIND = eINSTANCE.getElementTypeConfiguration_Kind();

		/**
		 * The meta object literal for the '<em><b>Icon Entry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY = eINSTANCE.getElementTypeConfiguration_IconEntry();

		/**
		 * The meta object literal for the '<em><b>Owned Advice</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE = eINSTANCE.getElementTypeConfiguration_OwnedAdvice();

		/**
		 * The meta object literal for the '<em><b>Owning Set</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_TYPE_CONFIGURATION__OWNING_SET = eINSTANCE.getElementTypeConfiguration_OwningSet();

		/**
		 * The meta object literal for the '<em><b>Owned Configurations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS = eINSTANCE.getElementTypeConfiguration_OwnedConfigurations();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.IconEntryImpl <em>Icon Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.IconEntryImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getIconEntry()
		 * @generated
		 */
		EClass ICON_ENTRY = eINSTANCE.getIconEntry();

		/**
		 * The meta object literal for the '<em><b>Icon Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ICON_ENTRY__ICON_PATH = eINSTANCE.getIconEntry_IconPath();

		/**
		 * The meta object literal for the '<em><b>Bundle Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ICON_ENTRY__BUNDLE_ID = eINSTANCE.getIconEntry_BundleId();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.IdentifiedConfiguration <em>Identified Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.IdentifiedConfiguration
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getIdentifiedConfiguration()
		 * @generated
		 */
		EClass IDENTIFIED_CONFIGURATION = eINSTANCE.getIdentifiedConfiguration();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIED_CONFIGURATION__IDENTIFIER = eINSTANCE.getIdentifiedConfiguration_Identifier();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.NamedConfiguration <em>Named Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.NamedConfiguration
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getNamedConfiguration()
		 * @generated
		 */
		EClass NAMED_CONFIGURATION = eINSTANCE.getNamedConfiguration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_CONFIGURATION__NAME = eINSTANCE.getNamedConfiguration_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.AnnotationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__SOURCE = eINSTANCE.getAnnotation_Source();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__VALUE = eINSTANCE.getAnnotation_Value();

		/**
		 * The meta object literal for the '<em><b>Configuration Element</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__CONFIGURATION_ELEMENT = eINSTANCE.getAnnotation_ConfigurationElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl <em>Abstract Advice Binding Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAbstractAdviceBindingConfiguration()
		 * @generated
		 */
		EClass ABSTRACT_ADVICE_BINDING_CONFIGURATION = eINSTANCE.getAbstractAdviceBindingConfiguration();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET = eINSTANCE.getAbstractAdviceBindingConfiguration_Target();

		/**
		 * The meta object literal for the '<em><b>Container Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION = eINSTANCE.getAbstractAdviceBindingConfiguration_ContainerConfiguration();

		/**
		 * The meta object literal for the '<em><b>Matcher Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION = eINSTANCE.getAbstractAdviceBindingConfiguration_MatcherConfiguration();

		/**
		 * The meta object literal for the '<em><b>Inheritance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_ADVICE_BINDING_CONFIGURATION__INHERITANCE = eINSTANCE.getAbstractAdviceBindingConfiguration_Inheritance();

		/**
		 * The meta object literal for the '<em><b>Apply To All Types</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES = eINSTANCE.getAbstractAdviceBindingConfiguration_ApplyToAllTypes();

		/**
		 * The meta object literal for the '<em><b>Owning Set</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_SET = eINSTANCE.getAbstractAdviceBindingConfiguration_OwningSet();

		/**
		 * The meta object literal for the '<em><b>Element Type Set</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET = eINSTANCE.getAbstractAdviceBindingConfiguration_ElementTypeSet();

		/**
		 * The meta object literal for the '<em><b>Owning Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TARGET = eINSTANCE.getAbstractAdviceBindingConfiguration_OwningTarget();

		/**
		 * The meta object literal for the '<em><b>Get Element Type Set</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ABSTRACT_ADVICE_BINDING_CONFIGURATION___GET_ELEMENT_TYPE_SET = eINSTANCE.getAbstractAdviceBindingConfiguration__GetElementTypeSet();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.AdviceConfigurationImpl <em>Advice Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.AdviceConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAdviceConfiguration()
		 * @generated
		 */
		EClass ADVICE_CONFIGURATION = eINSTANCE.getAdviceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Before</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADVICE_CONFIGURATION__BEFORE = eINSTANCE.getAdviceConfiguration_Before();

		/**
		 * The meta object literal for the '<em><b>After</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADVICE_CONFIGURATION__AFTER = eINSTANCE.getAdviceConfiguration_After();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.ContainerConfigurationImpl <em>Container Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.ContainerConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getContainerConfiguration()
		 * @generated
		 */
		EClass CONTAINER_CONFIGURATION = eINSTANCE.getContainerConfiguration();

		/**
		 * The meta object literal for the '<em><b>Container Matcher Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER_CONFIGURATION__CONTAINER_MATCHER_CONFIGURATION = eINSTANCE.getContainerConfiguration_ContainerMatcherConfiguration();

		/**
		 * The meta object literal for the '<em><b>Contained Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER_CONFIGURATION__CONTAINED_TYPE = eINSTANCE.getContainerConfiguration_ContainedType();

		/**
		 * The meta object literal for the '<em><b>EContainment Features</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER_CONFIGURATION__ECONTAINMENT_FEATURES = eINSTANCE.getContainerConfiguration_EContainmentFeatures();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.AbstractMatcherConfigurationImpl <em>Abstract Matcher Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.AbstractMatcherConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAbstractMatcherConfiguration()
		 * @generated
		 */
		EClass ABSTRACT_MATCHER_CONFIGURATION = eINSTANCE.getAbstractMatcherConfiguration();

		/**
		 * The meta object literal for the '<em><b>Matched Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE = eINSTANCE.getAbstractMatcherConfiguration_MatchedType();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.SpecializationTypeConfigurationImpl <em>Specialization Type Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.SpecializationTypeConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getSpecializationTypeConfiguration()
		 * @generated
		 */
		EClass SPECIALIZATION_TYPE_CONFIGURATION = eINSTANCE.getSpecializationTypeConfiguration();

		/**
		 * The meta object literal for the '<em><b>Edit Helper Advice Configuration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION = eINSTANCE.getSpecializationTypeConfiguration_EditHelperAdviceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Container Configuration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION = eINSTANCE.getSpecializationTypeConfiguration_ContainerConfiguration();

		/**
		 * The meta object literal for the '<em><b>Matcher Configuration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION = eINSTANCE.getSpecializationTypeConfiguration_MatcherConfiguration();

		/**
		 * The meta object literal for the '<em><b>Specialized Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPECIALIZATION_TYPE_CONFIGURATION__SPECIALIZED_TYPES = eINSTANCE.getSpecializationTypeConfiguration_SpecializedTypes();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.AbstractEditHelperAdviceConfigurationImpl <em>Abstract Edit Helper Advice Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.AbstractEditHelperAdviceConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAbstractEditHelperAdviceConfiguration()
		 * @generated
		 */
		EClass ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION = eINSTANCE.getAbstractEditHelperAdviceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_EDIT_HELPER_ADVICE_CONFIGURATION__TARGET = eINSTANCE.getAbstractEditHelperAdviceConfiguration_Target();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.MetamodelTypeConfigurationImpl <em>Metamodel Type Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.MetamodelTypeConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getMetamodelTypeConfiguration()
		 * @generated
		 */
		EClass METAMODEL_TYPE_CONFIGURATION = eINSTANCE.getMetamodelTypeConfiguration();

		/**
		 * The meta object literal for the '<em><b>EClass</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METAMODEL_TYPE_CONFIGURATION__ECLASS = eINSTANCE.getMetamodelTypeConfiguration_EClass();

		/**
		 * The meta object literal for the '<em><b>Edit Helper Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METAMODEL_TYPE_CONFIGURATION__EDIT_HELPER_CLASS_NAME = eINSTANCE.getMetamodelTypeConfiguration_EditHelperClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.EditHelperAdviceConfigurationImpl <em>Edit Helper Advice Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.EditHelperAdviceConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getEditHelperAdviceConfiguration()
		 * @generated
		 */
		EClass EDIT_HELPER_ADVICE_CONFIGURATION = eINSTANCE.getEditHelperAdviceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Edit Helper Advice Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDIT_HELPER_ADVICE_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME = eINSTANCE.getEditHelperAdviceConfiguration_EditHelperAdviceClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.AdviceBindingConfigurationImpl <em>Advice Binding Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.AdviceBindingConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getAdviceBindingConfiguration()
		 * @generated
		 */
		EClass ADVICE_BINDING_CONFIGURATION = eINSTANCE.getAdviceBindingConfiguration();

		/**
		 * The meta object literal for the '<em><b>Edit Helper Advice Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVICE_BINDING_CONFIGURATION__EDIT_HELPER_ADVICE_CLASS_NAME = eINSTANCE.getAdviceBindingConfiguration_EditHelperAdviceClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.MatcherConfigurationImpl <em>Matcher Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.MatcherConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getMatcherConfiguration()
		 * @generated
		 */
		EClass MATCHER_CONFIGURATION = eINSTANCE.getMatcherConfiguration();

		/**
		 * The meta object literal for the '<em><b>Matcher Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATCHER_CONFIGURATION__MATCHER_CLASS_NAME = eINSTANCE.getMatcherConfiguration_MatcherClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.ExternallyRegisteredTypeImpl <em>Externally Registered Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.ExternallyRegisteredTypeImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getExternallyRegisteredType()
		 * @generated
		 */
		EClass EXTERNALLY_REGISTERED_TYPE = eINSTANCE.getExternallyRegisteredType();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.impl.ExternallyRegisteredAdviceImpl <em>Externally Registered Advice</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.impl.ExternallyRegisteredAdviceImpl
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getExternallyRegisteredAdvice()
		 * @generated
		 */
		EClass EXTERNALLY_REGISTERED_ADVICE = eINSTANCE.getExternallyRegisteredAdvice();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.InheritanceKind <em>Inheritance Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.InheritanceKind
		 * @see org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsPackageImpl#getInheritanceKind()
		 * @generated
		 */
		EEnum INHERITANCE_KIND = eINSTANCE.getInheritanceKind();

	}

} //ElementTypesConfigurationsPackage
