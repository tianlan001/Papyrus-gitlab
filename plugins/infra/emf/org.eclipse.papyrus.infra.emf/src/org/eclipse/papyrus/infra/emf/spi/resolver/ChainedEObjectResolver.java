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
 * A composite resolver.
 */
class ChainedEObjectResolver implements IEObjectResolver {

	private final IEObjectResolver car;
	private final IEObjectResolver cdr;

	ChainedEObjectResolver(IEObjectResolver first, IEObjectResolver second) {
		super();

		this.car = first;
		this.cdr = second;
	}

	@Override
	public Object resolve(Object object) {
		Object result = car.resolve(object);

		if ((result == null) || (result == object)) {
			result = cdr.resolve(object);
		}

		return result;
	}

}
