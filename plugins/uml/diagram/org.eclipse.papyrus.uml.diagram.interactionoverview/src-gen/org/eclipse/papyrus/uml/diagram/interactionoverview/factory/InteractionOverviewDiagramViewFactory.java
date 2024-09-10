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
package org.eclipse.papyrus.uml.diagram.interactionoverview.factory;

import org.eclipse.gmf.runtime.diagram.ui.view.factories.DiagramViewFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;

public class InteractionOverviewDiagramViewFactory extends DiagramViewFactory {

	// Start of user code Custom view Factory field
	// End of user code

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MeasurementUnit getMeasurementUnit() {
		return MeasurementUnit.PIXEL_LITERAL;
	}

	@Override
	protected Diagram createDiagramView() {

		Diagram diagram = super.createDiagramView();
		DiagramVersioningUtils.stampCurrentVersion(diagram);
		return diagram;
	}
}
