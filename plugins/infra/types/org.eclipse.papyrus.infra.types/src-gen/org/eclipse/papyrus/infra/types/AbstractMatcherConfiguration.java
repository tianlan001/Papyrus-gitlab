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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Matcher Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration#getMatchedType <em>Matched Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getAbstractMatcherConfiguration()
 * @model abstract="true"
 * @generated
 */
public interface AbstractMatcherConfiguration extends ConfigurationElement {

	/**
	 * Returns the value of the '<em><b>Matched Type</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getMatcherConfiguration <em>Matcher Configuration</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 *   <li>'{@link org.eclipse.papyrus.infra.types.ConfigurationElement#getOwningType() <em>Owning Type</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Matched Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Matched Type</em>' reference.
	 * @see #setMatchedType(SpecializationTypeConfiguration)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getAbstractMatcherConfiguration_MatchedType()
	 * @see org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getMatcherConfiguration
	 * @model opposite="matcherConfiguration" ordered="false"
	 *        annotation="subsets"
	 * @generated
	 */
	SpecializationTypeConfiguration getMatchedType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration#getMatchedType <em>Matched Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Matched Type</em>' reference.
	 * @see #getMatchedType()
	 * @generated
	 */
	void setMatchedType(SpecializationTypeConfiguration value);
} // AbstractMatcherConfiguration
