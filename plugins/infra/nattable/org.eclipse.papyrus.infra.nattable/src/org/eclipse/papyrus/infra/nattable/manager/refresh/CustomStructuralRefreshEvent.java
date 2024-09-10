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

import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.event.StructuralRefreshEvent;

/**
 * This event has been created to change the structural refresh behavior of NatTable (bug 562619)
 *
 * @since 6.7
 *
 */
public class CustomStructuralRefreshEvent extends StructuralRefreshEvent {

	/**
	 * The command from which this event has been created
	 */
	private CustomStructuralRefreshCommand command;

	/**
	 * Constructor.
	 *
	 * @param layer
	 *            the layer laucnhing the event
	 * @param command
	 *            the command initializing this event
	 */
	public CustomStructuralRefreshEvent(final ILayer layer, final CustomStructuralRefreshCommand command) {
		super(layer);
		this.command = command;
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the SortModel has already been notified
	 */
	public boolean isSortModelAlreadyNotified() {
		return this.command.isSortModelAlreadyNotified();
	}

	/**
	 * the sort model calls this method in order to be able to know it has already been notified
	 */
	public void setSortModelAlreadyNotified() {
		this.command.setSortModelAlreadyNotified();
	}

}
