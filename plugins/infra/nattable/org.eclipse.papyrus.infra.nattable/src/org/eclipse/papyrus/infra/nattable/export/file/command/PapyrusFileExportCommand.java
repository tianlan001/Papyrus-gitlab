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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@ll4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.export.file.command;

import org.eclipse.nebula.widgets.nattable.command.AbstractContextFreeCommand;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.swt.widgets.Shell;

/**
 * The file export command.
 * 
 * @since 2.0
 */
public class PapyrusFileExportCommand extends AbstractContextFreeCommand {

	/**
	 * The config registry.
	 */
	private IConfigRegistry configRegistry;
	
	/**
	 * The parent shell.
	 */
    private final Shell shell;

    /**
     * Constructor.
     *
     * @param configRegistry The config registry.
     * @param shell The parent shell.
     */
    public PapyrusFileExportCommand(final IConfigRegistry configRegistry, final Shell shell) {
        this.configRegistry = configRegistry;
        this.shell = shell;
    }

    /**
     * Get the config registry.
     * 
     * @return The config registry.
     */
    public IConfigRegistry getConfigRegistry() {
        return this.configRegistry;
    }

    /**
     * Get the shell.
     * 
     * @return The shell.
     */
    public Shell getShell() {
        return this.shell;
    }
}
