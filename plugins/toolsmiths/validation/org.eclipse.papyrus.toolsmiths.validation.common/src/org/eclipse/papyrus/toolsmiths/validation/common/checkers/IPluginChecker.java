/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.checkers;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This allows to define the plug-in checker concept for the validation of papyrus plug-ins.
 */
public interface IPluginChecker {

	/**
	 * This allows to validate.
	 *
	 * @param monitor
	 *            The progress monitor.
	 */
	public void check(final IProgressMonitor monitor);

}
