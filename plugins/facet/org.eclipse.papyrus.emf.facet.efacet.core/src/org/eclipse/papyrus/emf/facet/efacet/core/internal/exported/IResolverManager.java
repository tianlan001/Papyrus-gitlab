/**
 * Copyright (c) 2012 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gregoire Dupe (Mia-Software) - Bug 375087 - [Table] ITableWidget.addColumn(List<ETypedElement>, List<FacetSet>)
 *    Gregoire Dupe (Mia-Software) - Bug 372626 - Aggregates
 */
package org.eclipse.papyrus.emf.facet.efacet.core.internal.exported;

import java.util.List;

import org.eclipse.papyrus.emf.facet.efacet.core.internal.ResolverManager;

public interface IResolverManager {

	IResolverManager DEFAULT = new ResolverManager();

	<T> T resolve(Object object, Class<T> aClass);

	<T> List<T> selectionPropagation(Object selectedObject, Class<T> aClass);

	<T> T selectionRoot(Object selectedObject, Class<T> aClass);

}
