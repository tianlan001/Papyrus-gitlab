/**
 * Copyright (c) 2012 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  	Alban Ménager (Soft-Maint) - Bug 387470 - [EFacet][Custom] Editors
 * 		Grégoire Dupé (Mia-Software) - Bug 387470 - [EFacet][Custom] Editors
 */
package org.eclipse.papyrus.emf.facet.query.ocl.core.internal.evaluator;

import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.core.query.IQueryImplementation;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.DerivedTypedElement;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.extensible.Query;
import org.eclipse.papyrus.emf.facet.query.ocl.core.util.OclQueryUtil;
import org.eclipse.papyrus.emf.facet.query.ocl.metamodel.oclquery.OclQuery;

/**
 * Concrete implementation of {@link IQueryImplementation} for the OCL query.
 */
public class OclQueryImplementation implements IQueryImplementation {

	protected OCLExpression<EClassifier> oclExpression;

	@Override
	public void setValue(final Query query, final DerivedTypedElement feature, final EObject source, final List<ParameterValue> parameterValues, final Object newValue) throws DerivedTypedElementException {
		throw new UnsupportedOperationException("not implemented yet"); //$NON-NLS-1$
	}

	@Override
	public boolean isCheckResultType() {
		return true;
	}

	@Override
	public Object getValue(final Query query, final DerivedTypedElement feature, final EObject source, final List<ParameterValue> parameterValues, final IFacetManager facetManager) throws DerivedTypedElementException {
		if (!(query instanceof OclQuery)) {
			throw new IllegalArgumentException("The given DerivedTypedElement does not have a OclQuery"); //$NON-NLS-1$
		}
		try {
			return executeOclQuery(source, parameterValues, (OclQuery) query);
		} catch (Exception e) {
			throw new DerivedTypedElementException(e);
		}
	}

	protected Object executeOclQuery(final EObject source, final List<ParameterValue> parameterValues, final OclQuery oclQuery) throws ParserException {
		Object evaluateQuery = null;

		final EClassifier context = oclQuery.getContext();

		if (oclExpression == null) {
			String stringExpression = oclQuery.getOclExpression();

			// We replace all the occurrence of the parameters in the query by the
			// corresponding value.
			// if (stringExpression != null) {
			// if (parameterValues != null) {
			// for (final ParameterValue paramValue : parameterValues) {
			// final String tmp = stringExpression;
			// final String name = paramValue.getParameter().getName();
			// final Object value = paramValue.getValue();
			// String string;
			// if (value == null) {
			//						string = "null"; //$NON-NLS-1$
			// } else {
			// string = value.toString();
			// }
			// stringExpression = tmp.replaceAll(name, string);
			// }
			// }
			// }

			oclExpression = OclQueryUtil.createOCLExpression(context, stringExpression);
		}

		if (oclExpression != null) {
			evaluateQuery = OclQueryUtil.evaluateQuery(context, oclExpression, source);
		}
		return evaluateQuery;
	}

}
