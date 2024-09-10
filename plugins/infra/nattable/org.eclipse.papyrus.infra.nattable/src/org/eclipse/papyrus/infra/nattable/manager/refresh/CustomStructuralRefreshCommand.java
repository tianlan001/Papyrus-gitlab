/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.refresh;

import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.command.StructuralRefreshCommand;

/**
 * This command has been created to change the structural refresh behavior of NatTable (bug 562619)
 *
 * @since 6.7
 *
 */
public class CustomStructuralRefreshCommand extends StructuralRefreshCommand {

	/**
	 * boolean indicating that this command instance already notified the sort model
	 */
	private boolean sortModelIsAlreadyNotified = false;

	/**
	 *
	 * Constructor.
	 *
	 */
	public CustomStructuralRefreshCommand() {
		// nothing to do
	}

	/**
	 * Specific behavior, the clone method returns the same instance
	 *
	 * @see org.eclipse.nebula.widgets.nattable.command.StructuralRefreshCommand#cloneCommand()
	 *
	 * @return
	 */
	@Override
	public ILayerCommand cloneCommand() {
		return this;
	}

	/**
	 * @return
	 *         <code>true</code> if this command has already updated the comparator chooser
	 */
	public boolean isSortModelAlreadyNotified() {
		return this.sortModelIsAlreadyNotified;
	}

	/**
	 * set the field sortModelIsAlreadyNotified to true
	 */
	public void setSortModelAlreadyNotified() {
		this.sortModelIsAlreadyNotified = true;
	}

}
