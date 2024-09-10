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

package org.eclipse.papyrus.infra.tools.util;

import java.util.function.BiFunction;

/**
 * A function analogous to the {@link BiFunction} that takes three inputs.
 *
 * @since 4.2
 */
@FunctionalInterface
public interface TriFunction<F, G, H, R> {

	/**
	 * Process three inputs to obtain a result.
	 *
	 * @param f
	 *            the first input
	 * @param g
	 *            the second input
	 * @param h
	 *            the third input
	 * @return the result
	 */
	R apply(F f, G g, H h);

}
