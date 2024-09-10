/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

/* Glazed Lists                                                 (c) 2003-2006 */
/* http://publicobject.com/glazedlists/                      publicobject.com,*/
/*                            						   O'Dell Engineering Ltd.*/
package org.eclipse.papyrus.infra.nattable.filter;

import ca.odell.glazedlists.FunctionList;

/**
 * A utility class containing all sorts of random things that are useful for
 * implementing Glazed Lists but not for using Glazed Lists.
 *
 * @author <a href="mailto:jesse@swank.ca">Jesse Wilson</a>
 * 
 *         Duplicated code from GlazzedList
 */
public final class GlazedListsImpl {



	/**
	 * Returns a {@link FunctionList.Function} that simply reflects the
	 * function's argument as its result.
	 */
	public static <E> FunctionList.Function<E, E> identityFunction() {
		return new IdentityFunction<E>();
	}

	private static class IdentityFunction<E> implements FunctionList.Function<E, E> {
		public E evaluate(E sourceValue) {
			return sourceValue;
		}
	}


}