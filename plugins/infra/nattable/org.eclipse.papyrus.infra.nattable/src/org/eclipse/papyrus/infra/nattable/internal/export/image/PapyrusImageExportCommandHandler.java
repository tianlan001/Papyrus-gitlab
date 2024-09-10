/*******************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Thanh Liem PHAN (ALL4TEC) <thanhliem.phan@all4tec.net> - Bug 417095
 ******************************************************************************/
package org.eclipse.papyrus.infra.nattable.internal.export.image;

import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.swt.widgets.Shell;

/**
 * Class to handle the {@link PapyrusImageExportCommand}.
 *
 * <p>
 * Warning: Using this class is risky as it could cause severe damage to
 * Papyrus NatTables that show huge data sets (for example: a table with more than 20k
 * rows).
 * </p>
 */
public class PapyrusImageExportCommandHandler extends
		AbstractLayerCommandHandler<PapyrusImageExportCommand> {

	/**
	 * The layer to be exported.
	 */
	private final ILayer layer;

	/**
	 * Default constructor.
	 *
	 * @param layer
	 *            The layer to be exported
	 */
	public PapyrusImageExportCommandHandler(final ILayer layer) {
		this.layer = layer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean doCommand(final PapyrusImageExportCommand command) {
		Shell shell = command.getShell();
		IConfigRegistry configRegistry = command.getConfigRegistry();

		new PapyrusNatExporter(shell).exportSingleTable(this.layer, configRegistry);

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<PapyrusImageExportCommand> getCommandClass() {
		return PapyrusImageExportCommand.class;
	}

}
