/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.restrictions;

import org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ViewProviderTester;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.SequenceDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.symbols.provider.ShapeCompartmentViewProvider;
import org.osgi.service.component.annotations.Component;

@Component
public class SequenceViewProviderTester implements ViewProviderTester {

	@Override
	public boolean isEnabled(IViewProvider provider, View view) {
		if (isSequenceDiagram(view)) {
			if (provider instanceof ShapeCompartmentViewProvider) {
				return false;
			}
			return true;
		}
		return true;
	}

	private boolean isSequenceDiagram(View view) {
		// XXX Should we use Architecture? We probably want to restrain only what we
		// know,
		// and let extension plug-ins provide their own restrictions if they need to.
		Diagram diagram = view.getDiagram();
		return diagram != null && SequenceDiagramEditPart.MODEL_ID.equals(diagram.getType());
	}

}
