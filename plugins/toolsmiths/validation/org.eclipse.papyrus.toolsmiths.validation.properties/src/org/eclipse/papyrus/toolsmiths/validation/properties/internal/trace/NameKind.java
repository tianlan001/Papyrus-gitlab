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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.trace;

/**
 * Enumeration of the kinds of names to extract from source model objects,
 * which usually depend on the context in which the names are used.
 */
public enum NameKind {
	/** The simplest name, usable primarily for information and not unique reference. */
	SIMPLE,
	/** A fully qualified name, for when it is known <em>a priori</em> that such is required. */
	QUALIFIED,
	/**
	 * A name used for reference in an 'is-a-kind-of' constraint. Some constraints
	 * need different kinds of qualification of a name than others.
	 */
	CONSTRAINT,
}
