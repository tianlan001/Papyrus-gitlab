/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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

import java.util.Objects;

import org.eclipse.emf.common.util.URI;

final class ClassifiedURIImpl implements OpaqueResourceProvider.ClassifiedURI {
	private final URI uri;
	private final Object classifier;

	ClassifiedURIImpl(URI uri, Object classifier) {
		super();

		this.uri = uri;
		this.classifier = (classifier == null || String.valueOf(classifier).isBlank()) ? DEFAULT_CLASSIFIER : classifier;
	}

	@Override
	public URI uri() {
		return uri;
	}

	@Override
	public Object classifier() {
		return classifier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(classifier, uri);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ClassifiedURIImpl)) {
			return false;
		}
		ClassifiedURIImpl other = (ClassifiedURIImpl) obj;
		return Objects.equals(classifier, other.classifier) && Objects.equals(uri, other.uri);
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", classifier, uri); //$NON-NLS-1$
	}

}
