/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.providers;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionSingleton;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionsRegistry;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.InducedRepresentationCreationEditPolicy;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * Edit policy provider to install InducedRepresentationPolicy in charge to create
 * compartments displaying shapes for an element by reading the expansion model
 * see #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_011
 */
public class InducedRepresentationPolicyProvider extends AbstractProvider implements IEditPolicyProvider {


	private DiagramExpansionsRegistry diagramExpansionRegistry = null;

	/**
	 * Constructor.
	 *
	 */
	public InducedRepresentationPolicyProvider() {
		this.diagramExpansionRegistry = DiagramExpansionSingleton.getInstance().getDiagramExpansionRegistry();
	}

	/**
	 * get the diagram type from a view.
	 * 
	 * @param currentView
	 *                        the current view
	 * @return the diagram type it can be also a view point
	 */
	protected String getDiagramType(View currentView) {
		Diagram diagram = currentView.getDiagram();
		String currentDiagramType = null;
		ViewPrototype viewPrototype = org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils.getPrototype(diagram);
		if (viewPrototype != null) {
			currentDiagramType = viewPrototype.getLabel();
		} else {
			currentDiagramType = diagram.getType();
		}
		return currentDiagramType;
	}

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

		// Make sure this concern Block Definition Diagram only
		IGraphicalEditPart gep = (IGraphicalEditPart) epOperation.getEditPart();
		String diagramType = getDiagramType(gep.getNotationView());
		if (diagramExpansionRegistry.mapChildreen.get(diagramType) != null) {
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createEditPolicies(EditPart editPart) {
		// add behavior for the shapes display
		if (editPart instanceof IGraphicalEditPart) { // add to Block Property Composite and Block Composite
			editPart.installEditPolicy(InducedRepresentationCreationEditPolicy.INDUCED_REPRESENTATION_CREATOR_EDITPOLICY, new InducedRepresentationCreationEditPolicy());
		}
	}
}
