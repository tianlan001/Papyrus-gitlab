/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.provider;

import org.eclipse.core.runtime.IStatus;

/**
 * The interface defining for the paste in nattable command provider.
 */
public interface PasteNattableCommandProvider {

	/**
	 * This allows to execute the paste from string with a command.
	 * 
	 * @param useProgressMonitor
	 *            Determinate if a progress monitor will be used.
	 * @param openDialog
	 *            Determinate if the dialog must be opened during the process.
	 * @return the status.
	 */
	public IStatus executePasteFromStringCommand(final boolean useProgressMonitor, final boolean openDialog);

}
