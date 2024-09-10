/*******************************************************************************
 * Copyright (c) 2012 CEA-LIST
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Gregoire Dupe (Mia-Software) - Bug 375087 - [Table] ITableWidget.addColumn(List<ETypedElement>, List<FacetSet>)
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.util.core.internal.exported;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @since 0.2
 */
public final class ListUtils {

	private ListUtils() {
		// Must not be used.
	}

	public static <T> List<T> cleanList(final Collection<T> collection) {
		final List<T> cleanList = new ArrayList<T>(collection);
		while (cleanList.contains(null)) {
			cleanList.remove(null);
		}
		return cleanList;
	}
}
