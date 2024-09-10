/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.statemachine.tests;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.junit.Assert;

/**
 * State machine diagram util class
 */
public class StateMachineUtil {

	/**
	 * find region compartment edit part
	 */
	public static IGraphicalEditPart getRegionCompartmentEditPart(IGraphicalEditPart root) {
		IGraphicalEditPart sm = findChildBySemanticHint(root, StateMachineEditPart.VISUAL_ID);
		IGraphicalEditPart smCompartment = findChildBySemanticHint(sm, StateMachineCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart region = findChildBySemanticHint(smCompartment, RegionEditPart.VISUAL_ID);
		return findChildBySemanticHint(region, RegionCompartmentEditPart.VISUAL_ID);
	}

	public static IGraphicalEditPart findChildBySemanticHint(IGraphicalEditPart parent, String vid) {
		IGraphicalEditPart childEP = parent.getChildBySemanticHint(vid);
		Assert.assertNotNull("Parent " + parent + ", type " + parent.getNotationView() + " looking for: " + vid, childEP);
		return childEP;
	}
}
