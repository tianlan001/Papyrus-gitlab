/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Atos - Initial API and implementation
 *   Arthur Daussy - arthur.daussy@atos.net - Bug 368932 - [ActivitiyDiagram] Prevent Compartment of Activity group to be selected
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.part.ActivityGroup;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.BorderDisplayEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;

/**
 * Set in order to have Generation Gap Pattern.
 *
 * @author arthur daussy
 *
 */
public class CustomExpansionRegionStructuredActivityNodeContentCompartmentEditPart extends ExpansionRegionStructuredActivityNodeContentCompartmentEditPart implements IGroupCompartmentEditPart {

	public CustomExpansionRegionStructuredActivityNodeContentCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 * Remove top border
	 */
	@Override
	public IFigure createFigure() {
		ResizableCompartmentFigure result = (ResizableCompartmentFigure) super.createFigure();
		result.setTitleVisibility(false);
		// remove the top border
		result.setBorder(null);
		return result;
	}

	/**
	 * Unselectable EditPart
	 */
	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		if (request instanceof AspectUnspecifiedTypeCreationTool.CreateAspectUnspecifiedTypeRequest) {
			List<?> createElementsTypes = ((AspectUnspecifiedTypeCreationTool.CreateAspectUnspecifiedTypeRequest) request).getElementTypes();
			if (areAllNodesAffixed(createElementsTypes)) {
				return getParent();
			}
		}
		return super.getTargetEditPart(request);
	}

	private boolean areAllNodesAffixed(List<?> types) {
		for (Object type : types) {
			if (!isAffixedNodeType(type)) {
				return false;
			}
		}
		return true;
	}

	private boolean isAffixedNodeType(Object type) {
		return UMLElementTypes.ExpansionNode_InputShape.equals(type) || UMLElementTypes.ExpansionNode_OutputShape.equals(type);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(BorderDisplayEditPolicy.BORDER_DISPLAY_EDITPOLICY, new BorderDisplayEditPolicy());
	}
}
