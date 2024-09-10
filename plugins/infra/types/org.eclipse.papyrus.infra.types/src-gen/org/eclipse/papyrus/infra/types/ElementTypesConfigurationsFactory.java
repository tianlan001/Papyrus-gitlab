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
package org.eclipse.papyrus.infra.types;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage
 * @generated
 */
public interface ElementTypesConfigurationsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ElementTypesConfigurationsFactory eINSTANCE = org.eclipse.papyrus.infra.types.impl.ElementTypesConfigurationsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Element Type Set Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element Type Set Configuration</em>'.
	 * @generated
	 */
	ElementTypeSetConfiguration createElementTypeSetConfiguration();

	/**
	 * Returns a new object of class '<em>Icon Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Icon Entry</em>'.
	 * @generated
	 */
	IconEntry createIconEntry();

	/**
	 * Returns a new object of class '<em>Container Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Container Configuration</em>'.
	 * @generated
	 */
	ContainerConfiguration createContainerConfiguration();

	/**
	 * Returns a new object of class '<em>Specialization Type Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Specialization Type Configuration</em>'.
	 * @generated
	 */
	SpecializationTypeConfiguration createSpecializationTypeConfiguration();

	/**
	 * Returns a new object of class '<em>Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Annotation</em>'.
	 * @generated
	 */
	Annotation createAnnotation();

	/**
	 * Returns a new object of class '<em>Metamodel Type Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metamodel Type Configuration</em>'.
	 * @generated
	 */
	MetamodelTypeConfiguration createMetamodelTypeConfiguration();

	/**
	 * Returns a new object of class '<em>Edit Helper Advice Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edit Helper Advice Configuration</em>'.
	 * @generated
	 */
	EditHelperAdviceConfiguration createEditHelperAdviceConfiguration();

	/**
	 * Returns a new object of class '<em>Advice Binding Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Advice Binding Configuration</em>'.
	 * @generated
	 */
	AdviceBindingConfiguration createAdviceBindingConfiguration();

	/**
	 * Returns a new object of class '<em>Matcher Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Matcher Configuration</em>'.
	 * @generated
	 */
	MatcherConfiguration createMatcherConfiguration();

	/**
	 * Returns a new object of class '<em>Externally Registered Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Externally Registered Type</em>'.
	 * @generated
	 */
	ExternallyRegisteredType createExternallyRegisteredType();

	/**
	 * Returns a new object of class '<em>Externally Registered Advice</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Externally Registered Advice</em>'.
	 * @generated
	 */
	ExternallyRegisteredAdvice createExternallyRegisteredAdvice();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ElementTypesConfigurationsPackage getElementTypesConfigurationsPackage();

} //ElementTypesConfigurationsFactory
