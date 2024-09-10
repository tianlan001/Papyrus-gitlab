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
package org.eclipse.papyrus.infra.filters.internal.operations;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.papyrus.infra.filters.CompoundFilter;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.filters.internal.FiltersPlugin;
import org.eclipse.papyrus.infra.filters.util.FiltersValidator;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Composite Filter</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.filters.CompoundFilter#validateAcyclic(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Validate Acyclic</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompoundFilterOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CompoundFilterOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean matches(CompoundFilter compoundFilter, Object input) {
		boolean result;

		switch (compoundFilter.getOperator()) {
		case AND: {
			result = false;
			for (Filter next : compoundFilter.getFilters()) {
				result = next.matches(input);
				if (!result) {
					break;
				}
			}
			break;
		}
		case OR: {
			result = false;
			for (Filter next : compoundFilter.getFilters()) {
				result = next.matches(input);
				if (result) {
					break;
				}
			}
			break;
		}
		case XOR: {
			int count = 0;
			for (Filter next : compoundFilter.getFilters()) {
				count += next.matches(input) ? 1 : 0;
			}
			result = count == 1;
			break;
		}
		case NOT: {
			result = true;
			for (Filter next : compoundFilter.getFilters()) {
				result = !next.matches(input);
				if (!result) {
					break;
				}
			}
			break;
		}
		default:
			throw new IllegalArgumentException("unrecognized composite filter operator: " + compoundFilter.getOperator().getName());
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A filter may not directly or indirectly compose itself.
	 *
	 * @param compoundFilter
	 *                           The receiving '<em><b>Composite Filter</b></em>' model object.
	 * @param diagnostics
	 *                           The chain of diagnostics to which problems are to be appended.
	 * @param context
	 *                           The cache of context-specific information.
	 *                           <!-- end-model-doc -->
	 * @generated NOT
	 */
	public static boolean validateAcyclic(final CompoundFilter compoundFilter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = true;

		Iterator<Filter> allFilters = new AbstractTreeIterator<>(compoundFilter, false) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<? extends Filter> getChildren(Object object) {
				return (object instanceof CompoundFilter) ? ((CompoundFilter) object).getFilters().iterator() : Collections.<Filter> emptyIterator();
			}
		};

		while (allFilters.hasNext()) {
			if (allFilters.next() == compoundFilter) {
				result = false;
				break;
			}
		}

		if (!result && (diagnostics != null)) {
			diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
					FiltersValidator.DIAGNOSTIC_SOURCE,
					FiltersValidator.COMPOUND_FILTER__ACYCLIC,
					FiltersPlugin.INSTANCE.getString("_UI_acyclic_diagnostic", new Object[] { EObjectValidator.getObjectLabel(compoundFilter, context) }), //$NON-NLS-1$
					new Object[] { compoundFilter }));
		}

		return result;
	}

} // CompoundFilterOperations
