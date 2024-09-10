/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Diagnostic;

import com.google.common.collect.Lists;

/**
 * An encapsulation of the equivalence semantics of {@link Diagnostic}s, which
 * intrinsically do not implement {@link Object#equals(Object) equals()} and
 * for which diagnostics from different checkers may need different treatment.
 */
public interface DiagnosticEquivalence {

	/**
	 * Default equivalence of diagnostics is based on all of their properties.
	 */
	DiagnosticEquivalence DEFAULT = new DiagnosticEquivalence() {

		@Override
		public int hashCode(Diagnostic diagnostic) {
			return Objects.hash(diagnostic.getSource(),
					diagnostic.getSeverity(), diagnostic.getCode(), diagnostic.getMessage(),
					diagnostic.getData(), diagnostic.getChildren());
		}

		@Override
		public boolean equals(Diagnostic diagnostic1, Diagnostic diagnostic2) {
			return Objects.equals(diagnostic1.getSource(), diagnostic2.getSource())
					&& diagnostic1.getSeverity() == diagnostic2.getSeverity()
					&& diagnostic1.getCode() == diagnostic2.getCode()
					&& Objects.equals(diagnostic1.getMessage(), diagnostic2.getMessage())
					&& Objects.equals(diagnostic1.getData(), diagnostic2.getData()) // data list can be null
					&& Objects.equals(diagnostic1.getChildren(), diagnostic2.getChildren());
		}

	};

	/**
	 * Compute the hash code of a {@code diagnostic}. Subclasses may override this to implement custom equality
	 * semantics for their diagnostics.
	 *
	 * @param diagnostic
	 * @return its hash code
	 */
	int hashCode(Diagnostic diagnostic);

	/**
	 * Compute the hash code of a {@code diagnostic}. Subclasses may override this to implement custom equality
	 * semantics for their diagnostics.
	 *
	 * @param diagnostic
	 * @return its hash code
	 */
	boolean equals(Diagnostic diagnostic1, Diagnostic diagnostic2);

	/**
	 * Wrap a {@code diagnostic} for hash and equality using my equivalence semantics.
	 *
	 * @param diagnostic
	 *            the diagnostic to wrap
	 * @param data
	 *            additional data to add to it
	 *
	 * @return the wrapped diagnostic
	 */
	default Diagnostic wrap(Diagnostic diagnostic, Object... additionalData) {
		class DiagnosticWrapper implements Diagnostic {
			private final Diagnostic delegate;

			private final List<Diagnostic> children;
			private final List<?> data;

			DiagnosticWrapper(Diagnostic delegate) {
				this.delegate = delegate;
				this.children = delegate.getChildren().stream().map(child -> wrap(child, additionalData)).collect(Collectors.toList());

				// The data list of the parent diagnostic of validation is null
				List<?> originalData = delegate.getData() == null ? Collections.emptyList() : delegate.getData();

				if (additionalData.length > 0) {
					// Add the additional data
					List<Object> toAdd = Lists.newArrayList(additionalData);
					toAdd.removeAll(originalData); // Don't repeat anything already there

					List<Object> copy = new ArrayList<>(originalData);
					copy.addAll(toAdd);
					data = Collections.unmodifiableList(copy);
				} else {
					data = Collections.unmodifiableList(originalData);
				}
			}

			@Override
			public List<Diagnostic> getChildren() {
				return children;
			}

			@Override
			public int hashCode() {
				return DiagnosticEquivalence.this.hashCode(this);
			}

			@Override
			public boolean equals(Object obj) {
				return (obj instanceof Diagnostic) && DiagnosticEquivalence.this.equals(this, (Diagnostic) obj);
			}

			@Override
			public List<?> getData() {
				return data;
			}

			//
			// Delegation
			//

			@Override
			public int getSeverity() {
				return delegate.getSeverity();
			}

			@Override
			public String getMessage() {
				return delegate.getMessage();
			}

			@Override
			public String getSource() {
				return delegate.getSource();
			}

			@Override
			public int getCode() {
				return delegate.getCode();
			}

			@Override
			public Throwable getException() {
				return delegate.getException();
			}

			@Override
			public String toString() {
				StringBuilder result = new StringBuilder();

				switch (getSeverity()) {
				case Diagnostic.OK:
					result.append("OK"); //$NON-NLS-1$
					break;
				case Diagnostic.INFO:
					result.append("Info"); //$NON-NLS-1$
					break;
				case Diagnostic.WARNING:
					result.append("Warning"); //$NON-NLS-1$
					break;
				case Diagnostic.CANCEL:
					result.append("Cancel"); //$NON-NLS-1$
					break;
				default:
					result.append("Error"); //$NON-NLS-1$
					break;
				}

				result.append('(');
				result.append(getSource()).append(", "); //$NON-NLS-1$
				result.append(getCode()).append(", "); //$NON-NLS-1$
				result.append(getData());
				result.append("): "); //$NON-NLS-1$
				result.append(getMessage());

				return result.toString();
			}

		}

		return (diagnostic instanceof DiagnosticWrapper)
				? diagnostic // Assume it has already has the same additional data
				: new DiagnosticWrapper(diagnostic);
	}

}
