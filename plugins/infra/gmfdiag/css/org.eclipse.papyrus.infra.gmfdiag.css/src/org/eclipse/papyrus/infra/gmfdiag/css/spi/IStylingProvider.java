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

package org.eclipse.papyrus.infra.gmfdiag.css.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.notation.View;

import com.google.common.collect.Iterables;

/**
 * Service interface for participants in the styling of notation elements.
 */
public interface IStylingProvider {
	IStylingProvider NONE = new IStylingProvider() {
		@Override
		public void resetStyle(View view) {
			// Pass
		}

		@Override
		public Iterable<EClass> getSupportedSemanticClasses() {
			return Collections.emptyList();
		}

		@Override
		public Predicate<EStructuralFeature> getSemanticPropertySupportedPredicate() {
			return feature -> false;
		}
	};

	/**
	 * Resets the domain-specific (from the service implementation's perspective)
	 * styling of a given notation {@code view}.
	 * 
	 * @param view
	 *            the notation view which is having its styling reset
	 */
	void resetStyle(View view);

	/**
	 * Obtains a collection of Ecore classes representing the supported CSS classes for
	 * semantic elements.
	 * 
	 * @return a collection of semantic CSS classes, or {@link Collection#isEmpty() empty} if none
	 */
	Iterable<EClass> getSupportedSemanticClasses();

	/**
	 * Obtains a predicate determining whether a feature of a
	 * {@linkplain #getSupportedSemanticClasses() supported class} is supported as a CSS
	 * property for semantic elements.
	 * 
	 * @return the semantic CSS property predicate
	 */
	Predicate<EStructuralFeature> getSemanticPropertySupportedPredicate();

	default IStylingProvider compose(IStylingProvider other) {
		IStylingProvider self = this;
		return (self == NONE)
				? other
				: (other == NONE)
						? self
						: new IStylingProvider() {
							@Override
							public void resetStyle(View view) {
								self.resetStyle(view);
								other.resetStyle(view);
							}

							@Override
							public Iterable<EClass> getSupportedSemanticClasses() {
								return Iterables.concat(self.getSupportedSemanticClasses(),
										other.getSupportedSemanticClasses());
							}

							@Override
							public Predicate<EStructuralFeature> getSemanticPropertySupportedPredicate() {
								return self.getSemanticPropertySupportedPredicate().or(
										other.getSemanticPropertySupportedPredicate());
							}
						};
	}
}
