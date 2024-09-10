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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Matcher Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.MatcherConfiguration#getMatcherClassName <em>Matcher Class Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getMatcherConfiguration()
 * @model
 * @generated
 */
public interface MatcherConfiguration extends AbstractMatcherConfiguration {
	/**
	 * Returns the value of the '<em><b>Matcher Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Matcher Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Matcher Class Name</em>' attribute.
	 * @see #setMatcherClassName(String)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getMatcherConfiguration_MatcherClassName()
	 * @model required="true"
	 * @generated
	 */
	String getMatcherClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.MatcherConfiguration#getMatcherClassName <em>Matcher Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Matcher Class Name</em>' attribute.
	 * @see #getMatcherClassName()
	 * @generated
	 */
	void setMatcherClassName(String value);

} // MatcherConfiguration
