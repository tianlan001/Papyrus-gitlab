/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@ - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.onefile.model;

import org.eclipse.core.resources.IContainer;

/**
 * The Di view filter needed to get its Di parent container
 *
 * @since 2.2
 */
public interface IDiViewFilter {

	/**
	 * The file name for di search in parent container.
	 *
	 * @param fileName
	 *            The initial file name.
	 * @param parentn
	 *            The parent container.
	 * @return The base of the di to search in the parent container.
	 * @since 2.2
	 */
	public String getFileNameForDi(final String fileName, final IContainer parent);

}
