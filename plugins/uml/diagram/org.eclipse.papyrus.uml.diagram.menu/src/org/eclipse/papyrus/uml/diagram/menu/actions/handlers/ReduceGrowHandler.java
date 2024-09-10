/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.menu.actions.handlers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.papyrus.uml.diagram.common.handlers.ParametricAndListeningHandler;
import org.eclipse.papyrus.uml.diagram.menu.actions.ReduceGrowAction;



/**
 *
 * Handler for the ReduceGrowAction
 * This allow to manipulate editparts with SHIFT+ARROW_KEYS
 *
 */
public class ReduceGrowHandler extends ParametricAndListeningHandler {

	/**
	 * Parameter id
	 */
	private static final String ORG_ECLIPSE_PAPYRUS_UML_DIAGRAM_MENU_COMMAND_GROW_PARAMETER = "org.eclipse.papyrus.uml.diagram.menu.commandGrowParameter"; //$NON-NLS-1$
	
	/** the action executed by this handler */
	protected ReduceGrowAction action = null;

	/**
	 *
	 * Constructor.
	 *
	 */
	public ReduceGrowHandler() {
		super(ORG_ECLIPSE_PAPYRUS_UML_DIAGRAM_MENU_COMMAND_GROW_PARAMETER); 
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.handlers.GraphicalCommandHandler#getCommand()
	 *
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected Command getCommand() {
		super.getCommand();
		this.action = new ReduceGrowAction(this.parameter, this.getSelectedElements());
		Command cmd = action.getCommand();
		return (cmd == null) ? UnexecutableCommand.INSTANCE : cmd;
		
	}
	
}
