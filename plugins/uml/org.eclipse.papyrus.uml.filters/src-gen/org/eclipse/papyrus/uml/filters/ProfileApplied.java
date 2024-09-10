/**
 * Copyright (c) 2014 Christian W. Damus and others.
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
package org.eclipse.papyrus.uml.filters;

import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.uml2.uml.Profile;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Profile Applied</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.filters.ProfileApplied#getProfileQualifiedName <em>Profile Qualified Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.filters.ProfileApplied#getProfileURI <em>Profile URI</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.filters.UMLFiltersPackage#getProfileApplied()
 * @model
 * @generated
 */
public interface ProfileApplied extends Filter {
	/**
	 * Returns the value of the '<em><b>Profile Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profile Qualified Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Profile Qualified Name</em>' attribute.
	 * @see #setProfileQualifiedName(String)
	 * @see org.eclipse.papyrus.uml.filters.UMLFiltersPackage#getProfileApplied_ProfileQualifiedName()
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getProfileQualifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.filters.ProfileApplied#getProfileQualifiedName <em>Profile Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Profile Qualified Name</em>' attribute.
	 * @see #getProfileQualifiedName()
	 * @generated
	 */
	void setProfileQualifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Profile URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profile URI</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Profile URI</em>' attribute.
	 * @see #setProfileURI(String)
	 * @see org.eclipse.papyrus.uml.filters.UMLFiltersPackage#getProfileApplied_ProfileURI()
	 * @model dataType="org.eclipse.uml2.types.String" required="true" ordered="false"
	 * @generated
	 */
	String getProfileURI();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.uml.filters.ProfileApplied#getProfileURI <em>Profile URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Profile URI</em>' attribute.
	 * @see #getProfileURI()
	 * @generated
	 */
	void setProfileURI(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @model ordered="false" contextDataType="org.eclipse.papyrus.infra.filters.Object" contextRequired="true" contextOrdered="false"
	 * @generated
	 */
	Profile resolveProfile(Object context);

} // ProfileApplied
