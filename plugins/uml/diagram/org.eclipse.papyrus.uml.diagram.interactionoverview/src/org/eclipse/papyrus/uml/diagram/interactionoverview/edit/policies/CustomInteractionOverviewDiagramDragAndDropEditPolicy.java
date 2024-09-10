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
package org.eclipse.papyrus.uml.diagram.interactionoverview.edit.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.policies.CustomDiagramDragDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.interactionoverview.part.UMLVisualIDRegistry;

public class CustomInteractionOverviewDiagramDragAndDropEditPolicy extends CustomDiagramDragDropEditPolicy {

	@Override
	public String getNodeVisualID(final View containerView, final EObject domainElement) {
		return UMLVisualIDRegistry.customGetNodeVisualID(containerView, domainElement);
	}
}
