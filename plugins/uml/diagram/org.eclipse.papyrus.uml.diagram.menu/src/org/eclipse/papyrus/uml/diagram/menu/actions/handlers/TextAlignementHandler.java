/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.menu.actions.handlers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.papyrus.commands.wrappers.EMFtoGEFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.common.handlers.ParametricAndListeningHandler;
import org.eclipse.papyrus.uml.diagram.common.layout.LayoutUtils;
import org.eclipse.papyrus.uml.diagram.menu.actions.TextAlignmentAction;

/**
 *
 * Handler for the AlignmentAction
 *
 */
public class TextAlignementHandler extends ParametricAndListeningHandler {

	/** the action executed by this handler */
	protected TextAlignmentAction action = null;

	/**
	 *
	 * Constructor.
	 *
	 */
	public TextAlignementHandler() {
		super("org.eclipse.papyrus.uml.diagram.menu.commandTextAlignmentParameter"); //$NON-NLS-1$
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
		this.action = new TextAlignmentAction(getAlignment(this.parameter), this.getSelectedElements());
		Command cmd = new EMFtoGEFCommandWrapper(action.getCommand());
		return (cmd == null) ? UnexecutableCommand.INSTANCE : cmd;

	}

	/**
	 *
	 * @param param
	 *            the parameter for the alignment action
	 * @return
	 *         the value represented by this parameter, this value can be :
	 *         <ul>
	 *         <li> {@link PositionConstants#NONE}</li>
	 *         <li> {@link PositionConstants#TOP}</li>
	 *         <li> {@link PositionConstants#BOTTOM}</li>
	 *         <li> {@link PositionConstants#MIDDLE}</li>
	 *         <li> {@link PositionConstants#LEFT}</li>
	 *         <li> {@link PositionConstants#RIGHT}</li>
	 *         <li> {@link PositionConstants#CENTER}</li>
	 *         </ul>
	 */
	public int getAlignment(String param) {
		if (param.equals(LayoutUtils.LEFT)) {
			return PositionConstants.LEFT;
		} else if (param.equals(LayoutUtils.CENTER)) {
			return PositionConstants.CENTER;
		} else if (param.equals(LayoutUtils.RIGHT)) {
			return PositionConstants.RIGHT;
		} else if (param.equals(LayoutUtils.BOTTOM)) {
			return PositionConstants.BOTTOM;
		} else if (param.equals(LayoutUtils.MIDDLE)) {
			return PositionConstants.MIDDLE;
		} else if (param.equals(LayoutUtils.TOP)) {
			return PositionConstants.TOP;
		}
		return PositionConstants.NONE;
	}

}
