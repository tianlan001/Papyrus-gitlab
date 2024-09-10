/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.export.file;

import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.papyrus.infra.nattable.export.file.command.PapyrusFileExportCommand;
import org.eclipse.swt.widgets.Shell;

/**
 * The handler for the file export.
 * 
 * @since 2.0
 */
public class PapyrusFileExportCommandHandler extends
		AbstractLayerCommandHandler<PapyrusFileExportCommand> {

	/**
	 * The layer to use.
	 */
	private final ILayer layer;
	
	/**
	 * Boolean to determinate if this must be used in a shell or not.
	 */
	private final boolean runInShell;

	/**
	 * Constructor.
	 *
	 * @param layer The layer to use.
	 */
	public PapyrusFileExportCommandHandler(final ILayer layer) {
		this(layer, true);
	}
	
	/**
	 * Constructor.
	 *
	 * @param layer The layer to use.
	 * @param runInShell Boolean to determinate if this must be used in a shell or not.
	 */
	public PapyrusFileExportCommandHandler(final ILayer layer, final boolean runInShell) {
		this.layer = layer;
		this.runInShell = runInShell;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler#doCommand(org.eclipse.nebula.widgets.nattable.command.ILayerCommand)
	 */
	@Override
	public boolean doCommand(final PapyrusFileExportCommand command) {
		final Shell shell = command.getShell();
		final IConfigRegistry configRegistry = command.getConfigRegistry();

		new PapyrusFileNatExporter(shell, runInShell).exportSingleLayer(this.layer, configRegistry);

		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.command.ILayerCommandHandler#getCommandClass()
	 */
	@Override
	public Class<PapyrusFileExportCommand> getCommandClass() {
		return PapyrusFileExportCommand.class;
	}

}
