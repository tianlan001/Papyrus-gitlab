/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IShapeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.providers.CustomUMLEditPartProvider;

public class InheritedActivityDiagramEditPartProvider extends CustomUMLEditPartProvider {

	/**
	 * This is workaround for bug #464093.
	 * The view type 'compartment_shape_display' is auxiliary and is provided externally
	 * (from the org.eclipse.papyrus.uml.diagram.symbols plugin) for all diagrams.
	 *
	 * Views of this kind shares semantic element with the main node so we can't distinguish them by semantic checks
	 * and have to explicitly rule them out to avoid interferention
	 */
	public static final String ALIEN_VIEW_TYPE = IShapeCompartmentEditPart.VIEW_TYPE;

	@Override
	public synchronized boolean provides(final IOperation operation) {
		if (operation instanceof CreateGraphicEditPartOperation) {
			final View view = ((IEditPartOperation) operation).getView();

			// Ensure current diagram is a InteractionOverviewDiagram Diagram
			if (!ElementTypes.DIAGRAM_ID.equals(view.getDiagram().getType())) {
				return false;
			}

			if (ALIEN_VIEW_TYPE.equals(view.getType())) {
				return false;
			}

			// Test supported inherited types
			final EObject eobject = view.getElement();

			/** Nodes (and ChildLabelNodes) *********** */
			if (eobject instanceof org.eclipse.uml2.uml.ActivityFinalNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.CallBehaviorAction) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.Comment) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.ConditionalNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.DecisionNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.FlowFinalNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.ForkNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.InitialNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.JoinNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.LoopNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.MergeNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.NamedElement) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.SequenceNode) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.Constraint) {
				return true;
			}
			if (eobject instanceof org.eclipse.uml2.uml.Parameter) {
				return true;
			}

			/** Edges *********** */
			if (eobject instanceof org.eclipse.uml2.uml.ControlFlow) {
				return true;
			}

			// Additional test needed here to decide whether to support Feature
			// type links.
			// As feature type link are not related to a MetaClass from the
			// domain model
			// they are not already handled by previous tests.
			// Also concerns NotationType.
			final String hint = view.getType();

			/** Edges (Feature) : COMMENT_ANNOTATED_ELEMENT *********** */
			if (ElementTypes.COMMENT_ANNOTATED_ELEMENT.getSemanticHint().equals(hint)) {
				return true;
			}

		}
		return false;
	}
}
