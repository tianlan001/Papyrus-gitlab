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

package org.eclipse.papyrus.infra.emf.resource.index;

import java.util.function.Supplier;

/**
 * A provider of a model index on the <tt>org.eclipse.papyrus.infra.emf.index</tt>
 * extension point.
 * 
 * @since 2.1
 */
@FunctionalInterface
public interface IWorkspaceModelIndexProvider extends Supplier<WorkspaceModelIndex<?>> {
	// Nothing to add
}
