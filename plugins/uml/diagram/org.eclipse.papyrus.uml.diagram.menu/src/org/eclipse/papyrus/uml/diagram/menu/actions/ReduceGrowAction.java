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
package org.eclipse.papyrus.uml.diagram.menu.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.common.layout.LayoutUtils;

/**
 * This action is used to expand or reduce editparts in the diagram
 *
 */
public class ReduceGrowAction extends AbstractGraphicalParametricAction {

	/**
	 * Default expand reduce speed
	 */
	public static final int DEFAULT_SPEED = 2;
	
	/**
	 *
	 * Constructor.
	 *
	 * @param parameter
	 */
	public ReduceGrowAction(String parameter, List<IGraphicalEditPart> selection) {
		super(parameter, selection);
	}


	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.menu.actions.AbstractParametricAction#getBuildedCommand()
	 *
	 * @return
	 */
	@Override
	protected Command getBuildedCommand() {
		CompoundCommand command = new CompoundCommand("Reduce/Grow Command"); //$NON-NLS-1$

		ChangeBoundsRequest request = createRequest(); 
		for (Iterator<IGraphicalEditPart> iter = getSelection().iterator(); iter.hasNext();) {
			EditPart element = iter.next();
			Command cmd = element.getCommand(request);
			if (cmd != null && cmd.canExecute()) {
				command.add(cmd);
			}
		}
		return command.isEmpty() ? null : command;
	}

	/**
	 * Create the request corresponding to the chosen parameter
	 * @return
	 */
	protected ChangeBoundsRequest createRequest() {
		ChangeBoundsRequest request = new ChangeBoundsRequest();
		request.setType(org.eclipse.gef.RequestConstants.REQ_RESIZE);
		switch (getParameter()) {
		case LayoutUtils.LEFT:
			request.setSizeDelta(new Dimension(-2 * DEFAULT_SPEED, 0));
			request.setMoveDelta(new Point(DEFAULT_SPEED, 0));			
			break;
		case LayoutUtils.RIGHT:
			request.setSizeDelta(new Dimension(2 * DEFAULT_SPEED, 0));
			request.setMoveDelta(new Point(-DEFAULT_SPEED, 0));			
			break;
		case LayoutUtils.TOP:
			request.setSizeDelta(new Dimension(0, 2 * DEFAULT_SPEED));
			request.setMoveDelta(new Point(0,-DEFAULT_SPEED));	
			break;
		case LayoutUtils.BOTTOM:
			request.setSizeDelta(new Dimension(0, -2 * DEFAULT_SPEED));
			request.setMoveDelta(new Point(0,DEFAULT_SPEED));			
			break;			
		default:
			break;
		}
		return request;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.menu.actions.AbstractParametricAction#isEnabled()
	 *
	 * @return
	 */
	@Override
	public boolean isEnabled() { 
		if (LayoutUtils.LEFT.equals(getParameter()) || LayoutUtils.RIGHT.equals(getParameter())
			||LayoutUtils.TOP.equals(getParameter()) || LayoutUtils.BOTTOM.equals(getParameter())) {
			return true;
		}
		return super.isEnabled();
	}

}
