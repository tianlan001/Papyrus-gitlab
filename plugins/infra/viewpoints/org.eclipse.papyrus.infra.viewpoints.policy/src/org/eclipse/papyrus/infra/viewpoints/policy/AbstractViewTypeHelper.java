/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 527580
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.viewpoints.policy;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;

/**
 * Partial implementation of a view-type helper.
 *
 * @author Laurent Wouters
 * 
 * @since 3.0
 */
public abstract class AbstractViewTypeHelper<T extends PapyrusRepresentationKind> implements IViewTypeHelper {

	private final Class<T> representationType;

	/**
	 * The cache of prototypes.
	 */
	private final Map<T, ViewPrototype> cache = new HashMap<>();

	protected AbstractViewTypeHelper(Class<T> representationType) {
		super();

		this.representationType = representationType;
	}

	/**
	 * Queries the type of representation kind that I support.
	 * 
	 * @return my representation type
	 */
	protected final Class<T> getRepresentationType() {
		return representationType;
	}

	@Override
	public boolean isSupported(EClass type) {
		return getRepresentationType().isAssignableFrom(type.getInstanceClass());
	}

	@Override
	public ViewPrototype getPrototypeFor(PapyrusRepresentationKind kind) {
		if (!isSupported(kind.eClass())) {
			return null;
		}

		T specificKind = getRepresentationType().cast(kind);
		ViewPrototype result = cache.get(specificKind);
		if (result != null) {
			// The unavailable type records an explicit null result in the cache
			if (result.isUnavailable()) {
				result = null;
			}
			return result;
		}

		result = doGetPrototypeFor(specificKind);

		if (result == null) {
			// Record the explicit null result
			cache.put(specificKind, ViewPrototype.UNAVAILABLE_VIEW);
		} else {
			cache.put(specificKind, result);
		}

		return result;
	}

	/**
	 * Determines the view prototype type for the given specific representation {@code kind}.
	 * 
	 * @param kind
	 *            the specific representation kind
	 * @return the view prototype, or {@code null} if there is none for the {@code kind}
	 *         or somehow it failed to be determined
	 */
	protected abstract ViewPrototype doGetPrototypeFor(T kind);

	@Override
	public ViewPrototype getPrototypeOf(EObject view) {
		if (!isSupported(view)) {
			return null;
		}
		return doGetPrototypeOf(view);
	}

	/**
	 * Determines the view prototype for the given supported {@code view} object.
	 * 
	 * @param view
	 *            a view known to the {@linkplain #isSupported(EObject) supported} by this helper
	 * @return its view prototype
	 */
	protected abstract ViewPrototype doGetPrototypeOf(EObject view);

	/**
	 * Obtains the most appropriate policy-checker for a {@code view}.
	 * 
	 * @param view
	 *            a view object
	 * @return its policy-checker
	 */
	protected PolicyChecker getPolicyChecker(EObject view) {
		return PolicyChecker.getFor(view);
	}
}
