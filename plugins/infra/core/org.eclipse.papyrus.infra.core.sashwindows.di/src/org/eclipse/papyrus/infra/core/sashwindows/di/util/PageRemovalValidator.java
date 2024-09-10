/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.core.sashwindows.di.util;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;

/**
 * A protocol for an adapter that can be attached to the Sash model to validate
 * the closing of pages.
 */
@FunctionalInterface
public interface PageRemovalValidator {
	/** A validator that always permits removing the page. */
	PageRemovalValidator ALWAYS_REMOVE = new PageRemovalValidator() {

		@Override
		public boolean canRemovePage(PageRef page) {
			return true;
		}

		@Override
		public Collection<? extends PageRef> filterRemovablePages(Collection<? extends PageRef> pages) {
			return pages;
		}
	};

	/**
	 * Queries whether the given page may be closed.
	 * 
	 * @param page
	 *            the page to be closed
	 * 
	 * @return whether the page may be closed
	 */
	boolean canRemovePage(PageRef page);

	/**
	 * Filters a set of pages to be closed, returning those that actually may be closed.
	 * This accounts for the possibility where a page may be closeable on its own,
	 * but not if some other pages are also to be closed.
	 * 
	 * @param pages
	 *            a collection of pages to be closed
	 * 
	 * @return the subset (possibly all or none) of the pages that may be closed
	 */
	default Collection<? extends PageRef> filterRemovablePages(Collection<? extends PageRef> pages) {
		return pages.stream().filter(this::canRemovePage).collect(Collectors.toList());
	}

	/**
	 * Composes myself with an{@code other} removal validator.
	 * 
	 * @param other
	 *            another removal validator
	 * 
	 * @return a removal validator that applies my filtering and the {@code other}'s, both
	 */
	default PageRemovalValidator compose(PageRemovalValidator other) {
		return new CompositePageRemovalValidator(this, other);
	}

	/**
	 * Obtains the page removal validator for the given {@code notifier}.
	 * 
	 * @param notifier
	 *            an element of (usually) the Sash model
	 * 
	 * @return the appropriate removal validator, never {@code null} (though perhaps the
	 *         {@link #ALWAYS_REMOVE} instance)
	 */
	static PageRemovalValidator getInstance(Notifier notifier) {
		return notifier.eAdapters().stream()
				.filter(PageRemovalValidator.class::isInstance).map(PageRemovalValidator.class::cast)
				.reduce(PageRemovalValidator::compose)
				.orElse(ALWAYS_REMOVE);
	}
}
