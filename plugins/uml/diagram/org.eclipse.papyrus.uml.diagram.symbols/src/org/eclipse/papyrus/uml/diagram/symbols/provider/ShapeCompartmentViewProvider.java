/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
package org.eclipse.papyrus.uml.diagram.symbols.provider;

import org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IShapeCompartmentEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.AbstractShapeCompartmentViewProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.GraphicalTypeRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;

/**
 * View Provider for Internal Block Diagram. It adds the shape compartment edit parts
 */
public class ShapeCompartmentViewProvider extends AbstractShapeCompartmentViewProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean provides(CreateViewForKindOperation op) {
		// This method should generally not be called (https://bugs.eclipse.org/bugs/show_bug.cgi?id=346739).
		if ((diagramType == null) || (!diagramType.equals(op.getContainerView().getDiagram().getType()))) {
			return false;
		}
		throw new UnsupportedOperationException("Should never be called by the " + diagramType + " diagram."); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean provides(CreateEdgeViewOperation operation) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean provides(CreateNodeViewOperation operation) {
		if (!ProviderServiceUtil.isEnabled(this, operation.getContainerView())) {
			return false;
		}
		return (getNodeViewClass(operation.getSemanticAdapter(), operation.getContainerView(), operation.getSemanticHint()) != null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initGraphicalTypeRegistry() {
		this.registry = new ShapeCompartmentGraphicalTypeRegistry();
	}

	/**
	 * Graphical Type Registry specific to this view provider
	 */
	public class ShapeCompartmentGraphicalTypeRegistry extends GraphicalTypeRegistry {

		public ShapeCompartmentGraphicalTypeRegistry() {
			knownNodes.add(IShapeCompartmentEditPart.VIEW_TYPE);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initDiagramType() {
		// nothing here. provides will be tested differently
	}
}
