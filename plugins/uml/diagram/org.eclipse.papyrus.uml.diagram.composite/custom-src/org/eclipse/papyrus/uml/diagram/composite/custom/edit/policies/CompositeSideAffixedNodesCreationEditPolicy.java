/*****************************************************************************
 * Copyright (c) 2017 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ansgar Radermacher (CEA) ansgar.radermacher@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.SideAffixedNodesCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.custom.utils.CompositeStructureDiagramUtils;
import org.eclipse.uml2.uml.Port;

/**
 * This EditPolicy takes care of the correct placement of ports, see bug 527181
 *
 * @since 3.1
 */
public class CompositeSideAffixedNodesCreationEditPolicy extends SideAffixedNodesCreationEditPolicy {

	/**
	 * Extends the inherited method with handling of ports.
	 */
	@Override
	protected ICommand getSetBoundsCommand(CreateViewRequest request, ViewDescriptor descriptor) {

		EObject portEObj = descriptor.getElementAdapter().getAdapter(EObject.class);
		if (portEObj instanceof Port) {
			final NodeEditPart partEditPart = (NodeEditPart) getHost();
			if (partEditPart.getFigure().getBounds().width != 0) {
				Point initialLocation = CompositeStructureDiagramUtils.getInitialPortLocation(getHost(), portEObj, descriptor);
				if (initialLocation != null) {
					return new SetBoundsCommand(partEditPart.getEditingDomain(),
							DiagramUIMessages.SetLocationCommand_Label_Resize, descriptor, initialLocation);
				}
			}
			else {
				// host figure does not have proper bounds yet, make deferred calculation
				partEditPart.getFigure().addFigureListener(new FigureListener() {

					@Override
					public void figureMoved(IFigure source) {
						Point initialLocation = CompositeStructureDiagramUtils.getInitialPortLocation(getHost(), portEObj, descriptor);
						// can be null, see bug 527181
						if (initialLocation != null) {
							SetBoundsCommand cmd = new SetBoundsCommand(partEditPart.getEditingDomain(),
									DiagramUIMessages.SetLocationCommand_Label_Resize, descriptor, initialLocation);
							try {
								cmd.execute(null, descriptor);
							} catch (ExecutionException e) {
								Activator.log.error(e);
							}
						}
						source.removeFigureListener(this);
					}
				});
			}
		}
		return super.getSetBoundsCommand(request, descriptor);
	}
}
