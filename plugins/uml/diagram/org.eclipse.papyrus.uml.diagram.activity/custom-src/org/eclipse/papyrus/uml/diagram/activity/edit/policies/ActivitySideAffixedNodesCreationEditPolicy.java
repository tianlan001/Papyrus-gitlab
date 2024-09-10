/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.papyrus.uml.diagram.activity.locator.ActivityParameterNodePositionLocator;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.SideAffixedNodesCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.locator.ISideAffixedNodeBorderItemLocator;

/**
 * This class extends {@link SideAffixedNodesCreationEditPolicy} to redefine the PositionLocator
 * and use the {@link ActivityParameterNodePositionLocator}
 */
public class ActivitySideAffixedNodesCreationEditPolicy extends SideAffixedNodesCreationEditPolicy {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.SideAffixedNodesCreationEditPolicy#getPositionLocator()
	 *
	 * @return
	 */
	@Override
	protected ISideAffixedNodeBorderItemLocator getPositionLocator() {
		return new ActivityParameterNodePositionLocator(getHostFigure(), PositionConstants.NONE);
	}

}
