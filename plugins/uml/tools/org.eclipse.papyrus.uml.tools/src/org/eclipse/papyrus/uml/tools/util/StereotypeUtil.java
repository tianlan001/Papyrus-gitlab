/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Utils class for stereotype
 *
 * @since 4.3
 *
 */
public class StereotypeUtil {

	private StereotypeUtil() {
		// to prevent instanciation
	}

	/**
	 *
	 * @param stereotype
	 *            a stereotype
	 * @param ignoreVoidOperations
	 *            if <code>true</code>, void operations won't be included in the result
	 * @param ignoreOperationsWithParameters
	 *            if <code>true</code>, operations with parameters won't be included in the result
	 * @param inheritedOperation
	 *            if <code>true</code> inherited operations will be included in the result
	 * @return
	 *         a collection of operations of the stereotypes, matching the values of the parameters
	 */
	public static final Collection<Operation> getAllStereotypeOperations(final Stereotype stereotype, final boolean ignoreVoidOperations, final boolean ignoreOperationsWithParameters, final boolean inheritedOperation) {
		if (inheritedOperation) {
			return filterOperations(stereotype.getAllOperations(), ignoreVoidOperations, ignoreOperationsWithParameters);
		}
		return filterOperations(stereotype.getOwnedOperations(), ignoreVoidOperations, ignoreOperationsWithParameters);
	}

	/**
	 *
	 * @param operations
	 *            a collection of operations
	 * @param ignoreVoidOperations
	 *            if <code>true</code>, void operations won't be included in the result
	 * @param ignoreOperationsWithParameters
	 *            * if <code>true</code>, operations with parameters won't be included in the result
	 *
	 * @return
	 *         a collection of operations of the stereotypes, matching the values of the parameters
	 */
	private static final Collection<Operation> filterOperations(final Collection<Operation> operations, final boolean ignoreVoidOperations, final boolean ignoreOperationsWithParameters) {
		if (!ignoreOperationsWithParameters && !ignoreVoidOperations) {
			return Collections.unmodifiableCollection(operations);
		}
		final Collection<Operation> filteredOperation = new HashSet<>();
		for (Operation current : operations) {
			final Collection<Parameter> returnParameters = new ArrayList<>();
			final Collection<Parameter> othersParameters = new ArrayList<>();
			for (final Parameter p : current.getOwnedParameters()) {
				if (p.getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
					returnParameters.add(p);
				} else {
					othersParameters.add(p);
				}
			}
			if ((ignoreVoidOperations && !returnParameters.isEmpty())
					&& (ignoreOperationsWithParameters && othersParameters.isEmpty())) {
				filteredOperation.add(current);
			}
		}
		return filteredOperation;
	}

}
