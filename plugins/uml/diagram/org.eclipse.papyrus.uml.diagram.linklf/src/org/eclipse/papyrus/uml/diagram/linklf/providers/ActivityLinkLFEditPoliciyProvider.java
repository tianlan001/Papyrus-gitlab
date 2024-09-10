/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Svyatoslav Kovalsky - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.linklf.providers;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.OpaqueActionEditPart;
import org.eclipse.papyrus.uml.diagram.linklf.policy.graphicalnode.LinksLFObjectFlowWithPinsCreationEditPolicies;

public class ActivityLinkLFEditPoliciyProvider extends LinksLFEditPolicyProvider {

	@Override
	protected void installGraphicalNodeEditPolicy(INodeEditPart nodeEP) {
		if (nodeEP instanceof OpaqueActionEditPart) {
			nodeEP.installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new LinksLFObjectFlowWithPinsCreationEditPolicies());
		} else {
			super.installGraphicalNodeEditPolicy(nodeEP);
		}
	}
}
