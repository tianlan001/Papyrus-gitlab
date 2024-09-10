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

import org.eclipse.nebula.widgets.nattable.command.AbstractContextFreeCommand;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.swt.widgets.Shell;

/**
 * Command to export the NatTable to image.
 *
 * <p>
 * Warning: Using this class is risky as it could cause severe damage to
 * papyrus NatTables that show huge data sets (for example: a table with more than 20k
 * rows).
 * </p>
 */
public class PapyrusImageExportCommand extends AbstractContextFreeCommand {

	/** The config registry of the NatTable. */
	private IConfigRegistry configRegistry;

	/** The parent shell. */
	private Shell shell;

	/**
	 * Default constructor.
	 *
	 * @param configRegistry
	 *            The NatTable config registry
	 * @param shell
	 *            The parent shell
	 */
	public PapyrusImageExportCommand(final IConfigRegistry configRegistry, final Shell shell) {
		this.configRegistry = configRegistry;
		this.shell = shell;
	}

	/**
	 * @return The NatTable config registry of this command
	 */
	public IConfigRegistry getConfigRegistry() {
		return this.configRegistry;
	}

	/**
	 * @return The parent shell
	 */
	public Shell getShell() {
		return this.shell;
	}
}
