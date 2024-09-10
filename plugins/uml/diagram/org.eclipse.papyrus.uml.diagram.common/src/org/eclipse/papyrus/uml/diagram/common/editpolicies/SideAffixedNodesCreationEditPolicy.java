/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.Collections;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.infra.gmfdiag.common.snap.NodeSnapHelper;
import org.eclipse.papyrus.uml.diagram.common.locator.ISideAffixedNodeBorderItemLocator;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;


public class SideAffixedNodesCreationEditPolicy extends DefaultCreationEditPolicy {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getSetBoundsCommand(CreateViewRequest request, CreateViewRequest.ViewDescriptor descriptor) {
		ICommand setBoundsCommand = null;
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		// Retrieve parent location
		Point parentLoc = getHostFigure().getBounds().getLocation().getCopy();
		final Point realWantedLocation;
		Map<?, ?> params = request.getExtendedData();
		Point realLocation = (Point) params.get(AspectUnspecifiedTypeCreationTool.INITIAL_MOUSE_LOCATION_FOR_CREATION);
		if (realLocation != null) {
			realWantedLocation = realLocation.getCopy();
		} else {
			// we use this location to be able to create Port in the corners of the figure
			realWantedLocation = request.getLocation().getCopy();
		}
		// Compute relative creation location
		Point requestedLocation = realWantedLocation.getCopy();
		getHostFigure().translateToRelative(requestedLocation);
		// Create proposed creation bounds and use the locator to find the expected position
		ISideAffixedNodeBorderItemLocator locator = getPositionLocator();
		if (locator == null) {
			return null;
		}
		final Rectangle preferredBounds = locator.getPreferredLocation(new Rectangle(requestedLocation, new Dimension(-1, -1)));
		Rectangle retainedBounds = preferredBounds.getCopy();
		// find the current side of the wanted position
		final Rectangle parentBounds = getHostFigure().getBounds().getCopy();
		// break all!!! getHostFigure().translateToAbsolute(parentBounds);
		locator.setConstraint(preferredBounds.getCopy().translate(parentBounds.getLocation().getNegated()));
		int currentSide = locator.getCurrentSideOfParent();
		if (request.isSnapToEnabled() && currentSide != PositionConstants.NORTH_EAST && currentSide != PositionConstants.NORTH_WEST && currentSide != PositionConstants.SOUTH_EAST && currentSide != PositionConstants.SOUTH_WEST) { // request for snap port at the
																																																										// // creation
			// we find the best location with snap
			Point wantedPoint = preferredBounds.getLocation();
			getHostFigure().translateToAbsolute(wantedPoint);
			Rectangle portBounds = new Rectangle(wantedPoint, new Dimension(-1, -1));
			NodeSnapHelper helper = new NodeSnapHelper(getHost().getAdapter(SnapToHelper.class), portBounds, false, false, true);
			final ChangeBoundsRequest tmpRequest = new ChangeBoundsRequest("move"); //$NON-NLS-1$
			tmpRequest.setEditParts(Collections.emptyList());
			tmpRequest.setSnapToEnabled(true);
			tmpRequest.setLocation(portBounds.getLocation());
			helper.snapPoint(tmpRequest);
			preferredBounds.translate(tmpRequest.getMoveDelta());
			switch (currentSide) {
			case PositionConstants.NORTH:
			case PositionConstants.SOUTH:
				preferredBounds.y = retainedBounds.y;
				break;
			case PositionConstants.EAST:
			case PositionConstants.WEST:
				preferredBounds.x = retainedBounds.x;
				break;
			default:
				break;
			}
		}
		// Convert the calculated preferred bounds as relative to parent location
		Rectangle creationBounds = preferredBounds.getTranslated(parentLoc.getNegated());
		setBoundsCommand = new SetBoundsCommand(editingDomain, DiagramUIMessages.SetLocationCommand_Label_Resize, descriptor, creationBounds);
		return setBoundsCommand;
	}

	protected ISideAffixedNodeBorderItemLocator getPositionLocator() {
		return new PortPositionLocator(getHostFigure());
	}

	/**
	 * {@inheritDoc}
	 */
	protected IFigure getHostFigure() {
		return ((GraphicalEditPart) getHost()).getFigure();
	}
}
