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

import org.eclipse.nebula.widgets.nattable.command.ILayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;

/**
 * This command handler has been created to change the structural refresh behavior of NatTable (bug 562619)
 *
 * @since 6.7
 *
 */
public class CustomStructuralRefreshCommandHandler implements ILayerCommandHandler<CustomStructuralRefreshCommand> {

	/**
	 * @see org.eclipse.nebula.widgets.nattable.command.ILayerCommandHandler#getCommandClass()
	 *
	 * @return
	 */
	@Override
	public Class<CustomStructuralRefreshCommand> getCommandClass() {
		return CustomStructuralRefreshCommand.class;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.command.ILayerCommandHandler#doCommand(org.eclipse.nebula.widgets.nattable.layer.ILayer, org.eclipse.nebula.widgets.nattable.command.ILayerCommand)
	 *
	 * @param targetLayer
	 * @param command
	 * @return
	 */
	@Override
	public boolean doCommand(final ILayer targetLayer, final CustomStructuralRefreshCommand command) {
		targetLayer.fireLayerEvent(new CustomStructuralRefreshEvent(targetLayer, command));
		return false;
	}
}
