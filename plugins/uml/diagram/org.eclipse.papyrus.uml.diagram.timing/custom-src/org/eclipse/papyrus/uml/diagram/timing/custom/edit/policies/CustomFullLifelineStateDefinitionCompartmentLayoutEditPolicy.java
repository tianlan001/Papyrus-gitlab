/*******************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.edit.policies;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.timing.custom.utils.EditPartUtils;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineEditPartCN;

/** Handles re-ordering StateDefinitions inside their compartment through drag&drop */
public class CustomFullLifelineStateDefinitionCompartmentLayoutEditPolicy extends GenericListCompartmentLayoutEditPolicy {

	@Override
	protected GraphicalEditPart getEditPartToRefresh() {
		return (GraphicalEditPart) EditPartUtils.findParentEditPartWithId(getHost(), FullLifelineEditPartCN.VISUAL_ID);
	}
}
