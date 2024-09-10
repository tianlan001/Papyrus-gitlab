/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.providers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.GraphicalTypeRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice;

/**
 * View provider for papyrus view used in all diagram.
 *
 * @author Mickael ADAM
 *
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeViewProvider extends CustomAbstractViewProvider {

	/** Map containing node view types supported by this provider. */
	protected Map<String, Class<?>> nodeMap = new HashMap<>();

	/** Map containing edge view types supported by this provider. */
	protected Map<String, Class<?>> edgeMap = new HashMap<>();

	/**
	 * Constructor.
	 */
	public StereotypePropertyReferenceEdgeViewProvider() {
		this.registry = new PapyrusGraphicalTypeRegistry();
		edgeMap.put(IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT, StereotypePropertyReferenceEdgeViewFactory.class);
	}

	/**
	 * {@inheritDoc}
	 *
	 * We check that it's a Papyrus diagram.
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider#isRelevantDiagram(org.eclipse.gmf.runtime.notation.Diagram)
	 */
	@Override
	protected boolean isRelevantDiagram(final Diagram diagram) {
		ViewPrototype prototype = DiagramUtils.getPrototype(diagram);
		PapyrusRepresentationKind representationKind = null;
		if (prototype != null) {
			representationKind = prototype.getRepresentationKind();
		}
		return representationKind instanceof PapyrusDiagram;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.AbstractViewProvider#getNodeViewClass(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.notation.View, java.lang.String)
	 */
	@Override
	protected Class<?> getNodeViewClass(final IAdaptable semanticAdapter, final View containerView, final String graphicalType) {
		return nodeMap.get(graphicalType);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.AbstractViewProvider#getEdgeViewClass(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.notation.View, java.lang.String)
	 */
	@Override
	protected Class<?> getEdgeViewClass(final IAdaptable semanticAdapter, final View containerView, final String graphicalType) {
		return edgeMap.get(graphicalType);
	}

	/**
	 * Graphical Type Registry specific to this view provider.
	 */
	public class PapyrusGraphicalTypeRegistry extends GraphicalTypeRegistry {
		/**
		 * Constructor.
		 */
		public PapyrusGraphicalTypeRegistry() {
			knownEdges.add(IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.providers.CustomAbstractViewProvider#provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation)
	 */
	@Override
	protected boolean provides(final CreateViewForKindOperation op) {
		return false;
	}

}
