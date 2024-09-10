/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.internal.clipboard;

import java.util.function.BiPredicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory;

/**
 * Default implementation of the copier configuration protocol.
 */
class DefaultConfiguration implements ICopierFactory.Configuration {

	private final boolean resolveReferences;
	private final boolean useOriginalReferences;

	private BiPredicate<EReference, EObject> referenceFilter;

	DefaultConfiguration(boolean resolveReferences, boolean useOriginalReferences) {
		super();

		this.resolveReferences = resolveReferences;
		this.useOriginalReferences = useOriginalReferences;
	}

	@Override
	public boolean isResolveReferences() {
		return resolveReferences;
	}

	@Override
	public boolean isUseOriginalReferences() {
		return useOriginalReferences;
	}

	/**
	 * Queries whether I am an empty configuration.
	 * 
	 * @return whether I have no copier configuration rules
	 */
	boolean isEmpty() {
		return referenceFilter == null;
	}

	@Override
	public void filterReferences(BiPredicate<? super EReference, ? super EObject> filter) {
		if (referenceFilter == null) {
			// We will only use it for these specific inputs
			@SuppressWarnings("unchecked")
			BiPredicate<EReference, EObject> filter_ = (BiPredicate<EReference, EObject>) filter;
			referenceFilter = filter_;
		} else {
			referenceFilter = referenceFilter.or(filter);
		}
	}

	boolean shouldCopyReference(EReference reference, EObject owner) {
		return (referenceFilter == null) || !referenceFilter.test(reference, owner);
	}
}
