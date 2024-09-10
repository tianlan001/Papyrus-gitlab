/*****************************************************************************
 * Copyright (c) 2010-2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *		Arthur Daussy - arthur.daussy@atos.net - 395920: [Block Diagram Definition] All element contained by a block should be able to be linked to constraint or comment
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.symbols.provider;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.ShapeCompartmentEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;
import org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedBorderNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.figure.node.NodeNamedElementFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;

/**
 * Edit policy provider for the shape compartment
 */
public class ShapeEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean provides(IOperation operation) {
		CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;
		if (!ProviderServiceUtil.isEnabled(this, epOperation.getEditPart())) {
			return false;
		}
		if (!(epOperation.getEditPart() instanceof IGraphicalEditPart)) {
			return false;
		}

		// Verify whether graphical edit part provides symbol
		IGraphicalEditPart gep = (IGraphicalEditPart) epOperation.getEditPart();
		return provides(gep);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createEditPolicies(EditPart editPart) {
		// add behavior for the shapes display
		if (provides(editPart)) {
			editPart.installEditPolicy(ShapeCompartmentEditPolicy.SHAPE_COMPARTMENT_EDIT_POLICY, new ShapeCompartmentEditPolicy());
		}
	}

	/**
	 * check whether edit part provides a symbol compartment
	 * 
	 * @param the
	 *                edit part
	 * @return
	 */
	protected boolean provides(EditPart ep) {
		// Provides for edit parts that represent nodes in Block Definition diagram and
		// nodes that represent ports in composite structure (and similar, e.g. pseudo states)
		if (ep instanceof NamedElementEditPart || ep instanceof RoundedBorderNamedElementEditPart) {
			return true;
		}

		// Provides for NodeEditParts with figures which can contain shapes
		if (isNodeEPWithSymbolCompartmentFigure(ep)) {
			return true;
		}

		return false;
	}

	/**
	 * @return true if figure contain container for shape elements and container edit part is a NodeEditPart
	 */
	private boolean isNodeEPWithSymbolCompartmentFigure(EditPart ep) {
		if (ep instanceof NodeEditPart) {
			IFigure figure = ((NodeEditPart) ep).getPrimaryShape();
			return figure instanceof RoundedCompartmentFigure || figure instanceof NodeNamedElementFigure;
		}
		return false;
	}
}
