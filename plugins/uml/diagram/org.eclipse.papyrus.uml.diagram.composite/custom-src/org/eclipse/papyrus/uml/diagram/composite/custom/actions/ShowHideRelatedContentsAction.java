/*****************************************************************************
 * Copyright (c) 2009-2011, 2017 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - bug 527181, ports on part layout
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.actions;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.actions.ShowHideContentsAction;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideRelatedContentsEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.custom.messages.Messages;
import org.eclipse.papyrus.uml.diagram.composite.custom.utils.CompositeStructureDiagramUtils;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;

public class ShowHideRelatedContentsAction extends ShowHideContentsAction {

	/**
	 * Constructor.
	 */
	public ShowHideRelatedContentsAction() {
		super(Messages.ShowHideRelatedContentsAction_Title, Messages.ShowHideRelatedContentsAction_Message, ShowHideRelatedContentsEditPolicy.SHOW_HIDE_RELATED_CONTENTS_POLICY);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.diagram.common.actions.AbstractShowHideAction#initAction()
	 *
	 */
	@Override
	protected void initAction() {
		super.initAction();
		for (IGraphicalEditPart current : this.selectedElements) {
			// the selected elements which aren't Classifier are ignored
			if (((View) current.getModel()).getElement() instanceof Property) {
				Property property = (Property) ((View) current.getModel()).getElement();
				if (property.getType() instanceof Classifier) {
					this.representations.add(new RootEditPartRepresentation(current, (Classifier) property.getType()));
				}
			}
		}
	}

	/**
	 * Return the initial position of a port on a part.
	 * 
	 * @param partEditPart edit part of the part within a composite (for which we want to display a port)
	 * @param port the semantic UML2 port which we want to display
	 * @return the initial location of the port or null (if none could be determined)
	 */
	@Override
	public Point getInitialPortLocation(EditPart partEditPart, EObject port) {
		return CompositeStructureDiagramUtils.getInitialPortLocation(partEditPart, port, null);
	}
}
