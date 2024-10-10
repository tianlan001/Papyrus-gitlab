/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.activity.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ObjectFlow;

/**
 * A specific implementation of {@link DomainBasedEdgeServices} for the Activity Diagram.
 * 
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class ActivityDomainBasedEdgeServices extends DomainBasedEdgeServices {

	@Override
	protected void createAdditionalViews(EObject semanticEdge, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		// The ObjectFlow can be created from and to an OpaqueAction. In this case, an InputPin and an OutputPin
		// are created as source and target of this relation. We need to create the source and target views corresponding to those pins.
		if (semanticEdge instanceof ObjectFlow) {
			ActivityNode source = ((ObjectFlow) semanticEdge).getSource();
			ActivityNode target = ((ObjectFlow) semanticEdge).getTarget();
			Session session = new EObjectQuery(semanticEdge).getSession();
			IInterpreter interpreter = session.getInterpreter();
			if (interpreter != null) {
				if (source != sourceView.getTarget()) {
					interpreter.setVariable(CONTAINER_VIEW, sourceView);
					try {
						createView(source, sourceView, session, "aql:" + CONTAINER_VIEW); //$NON-NLS-1$
					} finally {
						interpreter.unSetVariable(CONTAINER_VIEW);
					}
				}
				if (target != targetView.getTarget()) {
					interpreter.setVariable(CONTAINER_VIEW, targetView);
					try {
						createView(target, targetView, session, "aql:" + CONTAINER_VIEW); //$NON-NLS-1$
					} finally {
						interpreter.unSetVariable(CONTAINER_VIEW);
					}
				}
			}
		} else {
			super.createAdditionalViews(semanticEdge, sourceView, targetView);
		}
	}
}
