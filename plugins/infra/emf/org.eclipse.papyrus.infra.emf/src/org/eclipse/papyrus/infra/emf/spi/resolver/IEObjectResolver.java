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

package org.eclipse.papyrus.infra.emf.spi.resolver;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;

/**
 * A service interface for resolution of the {@linkplain EObject EMF object} wrapped
 * in an EMF Facet content-provider node or anything else that doesn't implement
 * the usual {@link IAdaptable} protocol with respect to provision of an {@link EObject}.
 * 
 * @since 2.0
 */
@FunctionalInterface
public interface IEObjectResolver {
	/**
	 * Resolves the object wrapped in a possible content-provider node.
	 * 
	 * @param object
	 *            an object that may be a content-provider node from a selection
	 *            in an EMFFacet-based view, or something even more exotic
	 * 
	 * @return the wrapped object, or the original {@code object} if it isn't our kind of wrapper
	 */
	Object resolve(Object object);

	default IEObjectResolver compose(IEObjectResolver other) {
		return new ChainedEObjectResolver(this, other);
	}

	static IEObjectResolver identity() {
		return IdentityEObjectResolver.INSTANCE;
	}
}
