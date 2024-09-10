/**
 * Copyright (c) 2017, 2020 CEA LIST, Christian W. Damus, and others.
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
 
package org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

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
 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceFactory
 * @model kind="package"
 * @generated
 */
public interface StereotypePropertyReferenceEdgeAdvicePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "stereotypepropertyreferenceedgeadvice";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/uml/types/stereotypepropertyreferenceedgeadvice/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "stereotypepropertyreferenceedgeadvice";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StereotypePropertyReferenceEdgeAdvicePackage eINSTANCE = org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdvicePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdviceConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdviceConfigurationImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdvicePackageImpl#getStereotypePropertyReferenceEdgeAdviceConfiguration()
	 * @generated
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__DESCRIPTION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__OWNING_TYPE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__ANNOTATIONS = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__BEFORE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__AFTER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__IDENTIFIER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__CONTAINER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__MATCHER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__INHERITANCE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Apply To All Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__APPLY_TO_ALL_TYPES = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__OWNING_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Element Type Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__ELEMENT_TYPE_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET;

	/**
	 * The feature id for the '<em><b>Owning Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__OWNING_TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TARGET;

	/**
	 * The feature id for the '<em><b>Feature To Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Edge Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION_FEATURE_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Element Type Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION___GET_ELEMENT_TYPE_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION___GET_ELEMENT_TYPE_SET;

	/**
	 * The number of operations of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION_OPERATION_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration
	 * @generated
	 */
	EClass getStereotypePropertyReferenceEdgeAdviceConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration#getFeatureToSet <em>Feature To Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature To Set</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration#getFeatureToSet()
	 * @see #getStereotypePropertyReferenceEdgeAdviceConfiguration()
	 * @generated
	 */
	EAttribute getStereotypePropertyReferenceEdgeAdviceConfiguration_FeatureToSet();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration#getStereotypeQualifiedName <em>Stereotype Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stereotype Qualified Name</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration#getStereotypeQualifiedName()
	 * @see #getStereotypePropertyReferenceEdgeAdviceConfiguration()
	 * @generated
	 */
	EAttribute getStereotypePropertyReferenceEdgeAdviceConfiguration_StereotypeQualifiedName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration#getEdgeLabel <em>Edge Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edge Label</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration#getEdgeLabel()
	 * @see #getStereotypePropertyReferenceEdgeAdviceConfiguration()
	 * @generated
	 */
	EAttribute getStereotypePropertyReferenceEdgeAdviceConfiguration_EdgeLabel();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StereotypePropertyReferenceEdgeAdviceFactory getStereotypePropertyReferenceEdgeAdviceFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdviceConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdviceConfigurationImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl.StereotypePropertyReferenceEdgeAdvicePackageImpl#getStereotypePropertyReferenceEdgeAdviceConfiguration()
		 * @generated
		 */
		EClass STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION = eINSTANCE.getStereotypePropertyReferenceEdgeAdviceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Feature To Set</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET = eINSTANCE.getStereotypePropertyReferenceEdgeAdviceConfiguration_FeatureToSet();

		/**
		 * The meta object literal for the '<em><b>Stereotype Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME = eINSTANCE.getStereotypePropertyReferenceEdgeAdviceConfiguration_StereotypeQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Edge Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL = eINSTANCE.getStereotypePropertyReferenceEdgeAdviceConfiguration_EdgeLabel();

	}

} //StereotypePropertyReferenceEdgeAdvicePackage
