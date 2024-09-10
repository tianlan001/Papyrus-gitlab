/**
 * Copyright (c) 2014, 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.filters;

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.CompoundFilter#getFilters <em>Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.filters.CompoundFilter#getOwnedFilters <em>Owned Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.filters.CompoundFilter#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.filters.FiltersPackage#getCompoundFilter()
 * @model
 * @generated
 */
public interface CompoundFilter extends Filter {
	/**
	 * Returns the value of the '<em><b>Filter</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.filters.Filter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filter</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Filter</em>' reference list.
	 * @see org.eclipse.papyrus.infra.filters.FiltersPackage#getCompoundFilter_Filter()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	EList<Filter> getFilters();

	/**
	 * Retrieves the first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>' from the '<em><b>Filter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *                 The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getFilters()
	 * @generated
	 */
	Filter getFilter(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>' from the '<em><b>Filter</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *                       The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *                       Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass
	 *                       The Ecore class of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getFilters()
	 * @generated
	 */
	Filter getFilter(String name, boolean ignoreCase, EClass eClass);

	/**
	 * Returns the value of the '<em><b>Owned Filter</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.filters.Filter}.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrus.infra.filters.CompoundFilter#getFilters() <em>Filter</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Filter</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Owned Filter</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.filters.FiltersPackage#getCompoundFilter_OwnedFilter()
	 * @model containment="true" ordered="false"
	 *        annotation="subsets"
	 * @generated
	 */
	EList<Filter> getOwnedFilters();

	/**
	 * Creates a new {@link org.eclipse.papyrus.infra.filters.Filter}, with the specified '<em><b>Name</b></em>', and appends it to the '<em><b>Owned Filter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *                   The '<em><b>Name</b></em>' for the new {@link org.eclipse.papyrus.infra.filters.Filter}, or <code>null</code>.
	 * @param eClass
	 *                   The Ecore class of the {@link org.eclipse.papyrus.infra.filters.Filter} to create.
	 * @return The new {@link org.eclipse.papyrus.infra.filters.Filter}.
	 * @see #getOwnedFilters()
	 * @generated
	 */
	Filter createOwnedFilter(String name, EClass eClass);

	/**
	 * Retrieves the first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Filter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *                 The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @return The first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedFilters()
	 * @generated
	 */
	Filter getOwnedFilter(String name);

	/**
	 * Retrieves the first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Filter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param name
	 *                           The '<em><b>Name</b></em>' of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @param ignoreCase
	 *                           Whether to ignore case in {@link java.lang.String} comparisons.
	 * @param eClass
	 *                           The Ecore class of the {@link org.eclipse.papyrus.infra.filters.Filter} to retrieve, or <code>null</code>.
	 * @param createOnDemand
	 *                           Whether to create a {@link org.eclipse.papyrus.infra.filters.Filter} on demand if not found.
	 * @return The first {@link org.eclipse.papyrus.infra.filters.Filter} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedFilters()
	 * @generated
	 */
	Filter getOwnedFilter(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.filters.OperatorKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.papyrus.infra.filters.OperatorKind
	 * @see #setOperator(OperatorKind)
	 * @see org.eclipse.papyrus.infra.filters.FiltersPackage#getCompoundFilter_Operator()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	OperatorKind getOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.filters.CompoundFilter#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *                  the new value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.papyrus.infra.filters.OperatorKind
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(OperatorKind value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A filter may not directly or indirectly compose itself.
	 *
	 * @param diagnostics
	 *                        The chain of diagnostics to which problems are to be appended.
	 * @param context
	 *                        The cache of context-specific information.
	 *                        <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean validateAcyclic(DiagnosticChain diagnostics, Map<Object, Object> context);

} // CompoundFilter
