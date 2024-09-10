/*****************************************************************************
 * Copyright (c) 2011, 2016 Atos, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Hemery (Atos) - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.commands;

import java.util.function.BinaryOperator;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.util.CommandUtils;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.papyrus.infra.gmfdiag.commands"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/** The log helper */
	public static LogHelper log = new LogHelper();

	// Teach the infra layer how to deal with GEF commands
	static {
		// The registry prefers the GMFtoGEFCommandWrapper for GMF ICommands
		ICommandWrapper.REGISTRY.registerUnwrapper(ICommandProxy.class, ICommand.class,
				ICommandProxy::getICommand);
		// The registry prefers the GEFtoGMFCommandWrapper for GEF Commands
		ICommandWrapper.REGISTRY.registerUnwrapper(CommandProxy.class, Command.class,
				CommandProxy::getCommand);

		@SuppressWarnings("deprecation")
		BinaryOperator<Command> gefComposer = org.eclipse.papyrus.commands.util.NonDirtyingUtils::chain;
		CommandUtils.REGISTRY.registerComposer(Command.class, gefComposer);
		CommandUtils.REGISTRY.registerDecomposer(CompoundCommand.class, CompoundCommand::getCommands);
		CommandUtils.REGISTRY.registerLabeller(Command.class, Command::getLabel);
	}

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		// register the log helper
		log.setPlugin(plugin);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		log = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
