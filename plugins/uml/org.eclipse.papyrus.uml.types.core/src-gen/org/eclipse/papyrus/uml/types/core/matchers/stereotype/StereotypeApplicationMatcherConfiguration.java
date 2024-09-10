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
package org.eclipse.papyrus.uml.types.core.matchers.stereotype;

import org.eclipse.emf.common.util.EList;

import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration#getStereotypesQualifiedNames <em>Stereotypes Qualified Names</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration#getProfileUri <em>Profile Uri</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage#getStereotypeApplicationMatcherConfiguration()
 * @model
 * @generated
 */
public interface StereotypeApplicationMatcherConfiguration extends AbstractMatcherConfiguration {
	/**
	 * Returns the value of the '<em><b>Stereotypes Qualified Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stereotypes Qualified Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stereotypes Qualified Names</em>' attribute list.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage#getStereotypeApplicationMatcherConfiguration_StereotypesQualifiedNames()
	 * @model required="true"
	 * @generated
	 */
	EList<String> getStereotypesQualifiedNames();

	/**
	 * Returns the value of the '<em><b>Profile Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profile Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Profile Uri</em>' attribute.
	 * @see #setProfileUri(String)
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage#getStereotypeApplicationMatcherConfiguration_ProfileUri()
	 * @model
	 * @generated
	 */
	String getProfileUri();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration#getProfileUri <em>Profile Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Profile Uri</em>' attribute.
	 * @see #getProfileUri()
	 * @generated
	 */
	void setProfileUri(String value);

} // StereotypeApplicationMatcherConfiguration
