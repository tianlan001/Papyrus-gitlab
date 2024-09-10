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

/**
 * A non-resolver.
 */
final class IdentityEObjectResolver implements IEObjectResolver {
	static final IdentityEObjectResolver INSTANCE = new IdentityEObjectResolver();

	private IdentityEObjectResolver() {
		super();
	}

	@Override
	public Object resolve(Object object) {
		return object;
	}

	@Override
	public IEObjectResolver compose(IEObjectResolver other) {
		return other;
	}
}
