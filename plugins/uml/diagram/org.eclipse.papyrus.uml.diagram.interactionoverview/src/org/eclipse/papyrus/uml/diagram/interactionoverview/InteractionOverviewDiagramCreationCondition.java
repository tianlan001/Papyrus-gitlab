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
package org.eclipse.papyrus.uml.diagram.interactionoverview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.ui.extension.commands.PerspectiveContextDependence;

/**
 * PackageDiagramCreationCondition class allows to check if a Interaction Overview diagram can
 * be added to the selected element.
 */
public class InteractionOverviewDiagramCreationCondition extends PerspectiveContextDependence {

	/**
	 * @return whether the diagram can be created.
	 */
	@Override
	public boolean create(final EObject selectedElement) {
		return false;
	}

}
