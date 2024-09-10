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
 *  Christian W. Damus - bug 568853
 */
package org.eclipse.papyrus.infra.types;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Specialization Type Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getEditHelperAdviceConfiguration <em>Edit Helper Advice Configuration</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getContainerConfiguration <em>Container Configuration</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getSpecializedTypes <em>Specialized Types</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getMatcherConfiguration <em>Matcher Configuration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getSpecializationTypeConfiguration()
 * @model
 * @generated
 */
public interface SpecializationTypeConfiguration extends ElementTypeConfiguration {
	/**
	 * Returns the value of the '<em><b>Edit Helper Advice Configuration</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration#getTarget <em>Target</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedConfigurations() <em>Owned Configurations</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Helper Advice Configuration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Helper Advice Configuration</em>' reference.
	 * @see #setEditHelperAdviceConfiguration(AbstractEditHelperAdviceConfiguration)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getSpecializationTypeConfiguration_EditHelperAdviceConfiguration()
	 * @see org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration#getTarget
	 * @model opposite="target"
	 *        annotation="subsets"
	 * @generated
	 */
	AbstractEditHelperAdviceConfiguration getEditHelperAdviceConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getEditHelperAdviceConfiguration <em>Edit Helper Advice Configuration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Helper Advice Configuration</em>' reference.
	 * @see #getEditHelperAdviceConfiguration()
	 * @generated
	 */
	void setEditHelperAdviceConfiguration(AbstractEditHelperAdviceConfiguration value);

	/**
	 * Returns the value of the '<em><b>Container Configuration</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.types.ContainerConfiguration#getContainedType <em>Contained Type</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedConfigurations() <em>Owned Configurations</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container Configuration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container Configuration</em>' reference.
	 * @see #setContainerConfiguration(ContainerConfiguration)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getSpecializationTypeConfiguration_ContainerConfiguration()
	 * @see org.eclipse.papyrus.infra.types.ContainerConfiguration#getContainedType
	 * @model opposite="containedType"
	 *        annotation="subsets"
	 * @generated
	 */
	ContainerConfiguration getContainerConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getContainerConfiguration <em>Container Configuration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container Configuration</em>' reference.
	 * @see #getContainerConfiguration()
	 * @generated
	 */
	void setContainerConfiguration(ContainerConfiguration value);

	/**
	 * Returns the value of the '<em><b>Matcher Configuration</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration#getMatchedType <em>Matched Type</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration#getOwnedConfigurations() <em>Owned Configurations</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Matcher Configuration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Matcher Configuration</em>' reference.
	 * @see #setMatcherConfiguration(AbstractMatcherConfiguration)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getSpecializationTypeConfiguration_MatcherConfiguration()
	 * @see org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration#getMatchedType
	 * @model opposite="matchedType"
	 *        annotation="subsets"
	 * @generated
	 */
	AbstractMatcherConfiguration getMatcherConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getMatcherConfiguration <em>Matcher Configuration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Matcher Configuration</em>' reference.
	 * @see #getMatcherConfiguration()
	 * @generated
	 */
	void setMatcherConfiguration(AbstractMatcherConfiguration value);

	/**
	 * Returns the value of the '<em><b>Specialized Types</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.types.ElementTypeConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specialized Types</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specialized Types</em>' reference list.
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getSpecializationTypeConfiguration_SpecializedTypes()
	 * @model
	 * @generated
	 */
	EList<ElementTypeConfiguration> getSpecializedTypes();

} // SpecializationTypeConfiguration
