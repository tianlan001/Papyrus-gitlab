/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Benoit maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.BehaviorPortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CompositeStructureDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLViewProvider;
import org.eclipse.uml2.uml.Port;

/**
 * Provide views to create innerPort in Composite diagram
 */
public class InnerPortViewProvider extends CustomAbstractViewProvider implements IViewProvider {

		
	private UMLViewProvider umlViewProvider = new UMLViewProvider();

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider#provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation)
	 *
	 * @param operation
	 * @return
	 */
	@Override
	protected boolean provides(CreateNodeViewOperation operation) {
		// check that view container element is a PORT
		View containerView = operation.getContainerView();
		if (containerView != null){
			ViewPrototype viewPrototype = org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils.getPrototype(containerView.getDiagram());
			// Check the type of diagram derived from composite to avoid contamination
			if (CompositeStructureDiagramEditPart.MODEL_ID.equals(viewPrototype.getImplementation())){
				EObject element = containerView.getElement();
				if (element instanceof Port){
					String semanticHint = operation.getSemanticHint();
					return BehaviorPortEditPart.VISUAL_ID.equals(semanticHint) || PortEditPart.VISUAL_ID.equals(semanticHint);
				}				
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider#provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation)
	 *
	 * @param operation
	 * @return
	 */
	@Override
	protected boolean provides(CreateEdgeViewOperation operation) {
		return false;
	}


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider#provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation)
	 *
	 * @param op
	 * @return
	 */
	@Override
	protected boolean provides(CreateViewForKindOperation op) {
		return false;
	}


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider#createNode(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.notation.View, java.lang.String, int, boolean,
	 *      org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint)
	 *
	 * @param semanticAdapter
	 * @param containerView
	 * @param semanticHint
	 * @param index
	 * @param persisted
	 * @param preferencesHint
	 * @return
	 */
	@Override
	public Node createNode(IAdaptable semanticAdapter, View containerView, String semanticHint, int index, boolean persisted, PreferencesHint preferencesHint) {
		final EObject domainElement = getSemanticElement(semanticAdapter);
		final String visualID;
		if (semanticHint == null) {
			visualID = UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		} else {
			visualID = UMLVisualIDRegistry.getVisualID(semanticHint);
		}
		if (visualID != null) {
			switch (visualID) {
			case BehaviorPortEditPart.VISUAL_ID:
				return umlViewProvider.createPort_BehaviorShape(domainElement, containerView, index, persisted, preferencesHint);
			case PortEditPart.VISUAL_ID:
				return umlViewProvider.createPort_Shape(domainElement, containerView, index, persisted, preferencesHint);
			}
		}
		return null;
	}
}
