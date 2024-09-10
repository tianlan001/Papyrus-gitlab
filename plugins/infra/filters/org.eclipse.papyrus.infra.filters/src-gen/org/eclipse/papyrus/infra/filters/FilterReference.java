/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.filters;

import org.eclipse.emf.ecore.EClass;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Filter Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A filter that redirects to a referenced filter. Useful to reuse filters by reference in any
 * context where otherwise the filtered element doesn't support shared filters.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.FilterReference#getFilter <em>Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.filters.FilterReference#getOwnedFilter <em>Owned Filter</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.filters.FiltersPackage#getFilterReference()
 * @model
 * @generated
 */
public interface FilterReference extends Filter {
	/**
	 * Returns the value of the '<em><b>Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filter</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Filter</em>' reference.
	 * @see #setFilter(Filter)
	 * @see org.eclipse.papyrus.infra.filters.FiltersPackage#getFilterReference_Filter()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Filter getFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.filters.FilterReference#getFilter <em>Filter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Filter</em>' reference.
	 * @see #getFilter()
	 * @generated
	 */
	void setFilter(Filter value);

	/**
	 * Returns the value of the '<em><b>Owned Filter</b></em>' containment reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.filters.FilterReference#getFilter() <em>Filter</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Owning the indirect reference
	 * lets a dependent model that uses
	 * filters access all available child
	 * creation extenders, when it cannot
	 * access those extenders, itself.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Owned Filter</em>' containment reference.
	 * @see #setOwnedFilter(Filter)
	 * @see org.eclipse.papyrus.infra.filters.FiltersPackage#getFilterReference_OwnedFilter()
	 * @model containment="true" ordered="false"
	 *        annotation="subsets"
	 * @generated
	 */
	Filter getOwnedFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.filters.FilterReference#getOwnedFilter <em>Owned Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Owned Filter</em>' containment reference.
	 * @see #getOwnedFilter()
	 * @generated
	 */
	void setOwnedFilter(Filter value);

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.filters.Filter}, with the specified '<em><b>Name</b></em>', and sets the '<em><b>Owned Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *                   The '<em><b>Name</b></em>' for the new {@link org.eclipse.papyrus.infra.filters.Filter}, or <code>null</code>.
	 * @param eClass
	 *                   The Ecore class of the {@link org.eclipse.papyrus.infra.filters.Filter} to create.
	 * @return The new {@link org.eclipse.papyrus.infra.filters.Filter}.
	 * @see #getOwnedFilter()
	 * @generated
	 */
	Filter createOwnedFilter(String name, EClass eClass);

} // FilterReference
