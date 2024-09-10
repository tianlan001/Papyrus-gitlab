/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.dnd.strategies;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Activity;

/**
 * This is an abstract strategy to drop an element on the activity compartment
 *
 * @since 3.5.0
 */
public abstract class AbstractActivityNodeStrategy extends TransactionalDropStrategy {

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalDropStrategy#doGetCommand(org.eclipse.gef.Request, org.eclipse.gef.EditPart)
	 *
	 * @param request
	 * @param targetEditPart
	 * @return
	 */
	@Override
	protected Command doGetCommand(Request request, EditPart targetEditPart) {
		// Step 1: check the environment
		if (false == request instanceof DropObjectsRequest) {
			return null;
		}
		// Step 1.1: The only supported case is "Drop a single element"
		List<EObject> sourceElements = getSourceEObjects(request);

		if (sourceElements.size() != 1) {
			return null;
		}
		final EObject sourceElement = sourceElements.get(0);

		// Step 1.2: The only source supported case is Operation
		if (!isSourceSupportedCase(sourceElement)) {
			return null;
		}
		final EObject droppedNode = sourceElement;

		// Step 1.3: The only target supported case is Activity
		final EObject targetElement = getTargetSemanticElement(targetEditPart);
		if (!(targetElement instanceof org.eclipse.uml2.uml.Activity)) {
			return null;
		}
		if (!isGraphicalTargetSupported(targetEditPart)) {
			return null;
		}
		final Activity activity = (Activity) targetElement;

		// Step 2: create the commands
		// CompositeCommand to hold the commands
		CompositeCommand cc = new CompositeCommand(getLabel());

		ICommand editSlotsCommand = getCreateAndUpdateCommand(targetEditPart, activity, droppedNode, getLocation(request));
		if (editSlotsCommand != null) {
			cc.add(editSlotsCommand);
		}

		return cc.canExecute() ? new ICommandProxy(cc.reduce()) : null;
	}

	/**
	 *
	 * Get the command which create drop and set semantic feature
	 *
	 * @param targetEditPart
	 *            the target edit part
	 * @param activity
	 *            the target activity
	 * @param droppedNode
	 *            the drop element
	 * @param location
	 *            the location of the drop
	 * @return the command which create drop and set semantic feature
	 */
	protected abstract ICommand getCreateAndUpdateCommand(EditPart targetEditPart, Activity activity, EObject droppedNode, Point location);

	/**
	 * Check if the sourceElement is supported by this strategy
	 *
	 * @param sourceElement
	 * @return true if the sourceElement is supported by this strategy, false otherwise
	 */
	protected abstract boolean isSourceSupportedCase(EObject sourceElement);

	/**
	 * Check if the targetEditPart is supported by this strategy
	 *
	 * @param targetEditPart
	 * @return true if the targetEditPart is supported by this strategy, false otherwise
	 */
	protected boolean isGraphicalTargetSupported(EditPart targetEditPart) {
		return true;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getImage()
	 *
	 * @return
	 */
	@Override
	public Image getImage() {
		return null;
	}

	/**
	 * Get the location of the getting request
	 *
	 * @param request
	 * @return the location of the request if it is a DropObjectRequest otherwise return default value (100, 100)
	 */
	private Point getLocation(Request request) {
		if (request instanceof DropObjectsRequest) {
			return ((DropObjectsRequest) request).getLocation();
		}
		return new Point(100, 100);
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getPriority()
	 *
	 * @return
	 * @deprecated
	 */
	@Deprecated
	@Override
	public int getPriority() {
		return 0;
	}

}
