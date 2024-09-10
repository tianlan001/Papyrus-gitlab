/**
 * Copyright (c) 2014 CEA LIST.
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
 */
package org.eclipse.papyrus.infra.types.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.infra.types.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ElementTypesConfigurationsFactoryImpl extends EFactoryImpl implements ElementTypesConfigurationsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ElementTypesConfigurationsFactory init() {
		try {
			ElementTypesConfigurationsFactory theElementTypesConfigurationsFactory = (ElementTypesConfigurationsFactory)EPackage.Registry.INSTANCE.getEFactory(ElementTypesConfigurationsPackage.eNS_URI);
			if (theElementTypesConfigurationsFactory != null) {
				return theElementTypesConfigurationsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ElementTypesConfigurationsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypesConfigurationsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION: return createElementTypeSetConfiguration();
			case ElementTypesConfigurationsPackage.ICON_ENTRY: return createIconEntry();
			case ElementTypesConfigurationsPackage.CONTAINER_CONFIGURATION: return createContainerConfiguration();
			case ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION: return createSpecializationTypeConfiguration();
			case ElementTypesConfigurationsPackage.ANNOTATION: return createAnnotation();
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION: return createMetamodelTypeConfiguration();
			case ElementTypesConfigurationsPackage.EDIT_HELPER_ADVICE_CONFIGURATION: return createEditHelperAdviceConfiguration();
			case ElementTypesConfigurationsPackage.ADVICE_BINDING_CONFIGURATION: return createAdviceBindingConfiguration();
			case ElementTypesConfigurationsPackage.MATCHER_CONFIGURATION: return createMatcherConfiguration();
			case ElementTypesConfigurationsPackage.EXTERNALLY_REGISTERED_TYPE: return createExternallyRegisteredType();
			case ElementTypesConfigurationsPackage.EXTERNALLY_REGISTERED_ADVICE: return createExternallyRegisteredAdvice();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ElementTypesConfigurationsPackage.INHERITANCE_KIND:
				return createInheritanceKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ElementTypesConfigurationsPackage.INHERITANCE_KIND:
				return convertInheritanceKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementTypeSetConfiguration createElementTypeSetConfiguration() {
		ElementTypeSetConfigurationImpl elementTypeSetConfiguration = new ElementTypeSetConfigurationImpl();
		return elementTypeSetConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IconEntry createIconEntry() {
		IconEntryImpl iconEntry = new IconEntryImpl();
		return iconEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContainerConfiguration createContainerConfiguration() {
		ContainerConfigurationImpl containerConfiguration = new ContainerConfigurationImpl();
		return containerConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SpecializationTypeConfiguration createSpecializationTypeConfiguration() {
		SpecializationTypeConfigurationImpl specializationTypeConfiguration = new SpecializationTypeConfigurationImpl();
		return specializationTypeConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Annotation createAnnotation() {
		AnnotationImpl annotation = new AnnotationImpl();
		return annotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MetamodelTypeConfiguration createMetamodelTypeConfiguration() {
		MetamodelTypeConfigurationImpl metamodelTypeConfiguration = new MetamodelTypeConfigurationImpl();
		return metamodelTypeConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EditHelperAdviceConfiguration createEditHelperAdviceConfiguration() {
		EditHelperAdviceConfigurationImpl editHelperAdviceConfiguration = new EditHelperAdviceConfigurationImpl();
		return editHelperAdviceConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdviceBindingConfiguration createAdviceBindingConfiguration() {
		AdviceBindingConfigurationImpl adviceBindingConfiguration = new AdviceBindingConfigurationImpl();
		return adviceBindingConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MatcherConfiguration createMatcherConfiguration() {
		MatcherConfigurationImpl matcherConfiguration = new MatcherConfigurationImpl();
		return matcherConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExternallyRegisteredType createExternallyRegisteredType() {
		ExternallyRegisteredTypeImpl externallyRegisteredType = new ExternallyRegisteredTypeImpl();
		return externallyRegisteredType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExternallyRegisteredAdvice createExternallyRegisteredAdvice() {
		ExternallyRegisteredAdviceImpl externallyRegisteredAdvice = new ExternallyRegisteredAdviceImpl();
		return externallyRegisteredAdvice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceKind createInheritanceKindFromString(EDataType eDataType, String initialValue) {
		InheritanceKind result = InheritanceKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInheritanceKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementTypesConfigurationsPackage getElementTypesConfigurationsPackage() {
		return (ElementTypesConfigurationsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ElementTypesConfigurationsPackage getPackage() {
		return ElementTypesConfigurationsPackage.eINSTANCE;
	}

} //ElementTypesConfigurationsFactoryImpl
