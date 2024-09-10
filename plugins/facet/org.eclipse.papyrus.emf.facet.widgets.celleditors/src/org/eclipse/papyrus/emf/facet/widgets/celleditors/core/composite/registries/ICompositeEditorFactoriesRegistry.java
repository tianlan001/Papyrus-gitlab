/*******************************************************************************
 * Copyright (c) 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Guyomar (Mia-Software) - Bug 339554 - org.eclipse.papyrus.emf.facet.widgets.celleditors API cleaning
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.widgets.celleditors.core.composite.registries;

import org.eclipse.papyrus.emf.facet.widgets.celleditors.ICompositeEditorFactory;
import org.eclipse.papyrus.emf.facet.widgets.celleditors.internal.composite.registries.CompositeEditorFactoriesRegistry;

/**
 * Registry for the "compositeEditorFactories" extension point
 *
 * @deprecated replaced by
 *             org.eclipse.papyrus.emf.facet.widgets.celleditors.core.composite
 *             .registry.ICompositeEditorFactoriesRegistry
 */
@Deprecated
public interface ICompositeEditorFactoriesRegistry {

	/** the singleton {@link ICompositeEditorFactoriesRegistry} */
	ICompositeEditorFactoriesRegistry INSTANCE = new CompositeEditorFactoriesRegistry();

	/** Whether there is a {@link CompositeEditorFactory} for the given type */
	boolean hasCompositeEditorFactory(final Class<?> type);

	/**
	 * @return the {@link CompositeEditorFactory} for the given type, or <code>null</code> if none is
	 *         registered
	 */
	<T> ICompositeEditorFactory<T> getCompositeEditorFactory(final Class<T> type);

}
