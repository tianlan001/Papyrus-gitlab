/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Fix some port location issue.
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AffixedNodeAlignmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AllowResizeAffixedNodeAlignmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;


/**
 * 
 * This class just takes care of installing a new policy that calls the resize command of a child item (a port in this case).
 * 
 * @author Trung-Truc Nguyen
 *
 */
public class CustomClassCompositeEditPart extends ClassCompositeEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public CustomClassCompositeEditPart(View view) {
		super(view);
		installEditPolicy(AffixedNodeAlignmentEditPolicy.AFFIXED_CHILD_ALIGNMENT_ROLE, new AllowResizeAffixedNodeAlignmentEditPolicy());
	}

	@Override
	public void installEditPolicy(Object key, EditPolicy editPolicy) {
		if (AffixedNodeAlignmentEditPolicy.AFFIXED_CHILD_ALIGNMENT_ROLE.equals(key)) {
			if (editPolicy instanceof AllowResizeAffixedNodeAlignmentEditPolicy)
				super.installEditPolicy(key, editPolicy);
		} else
			super.installEditPolicy(key, editPolicy);
	}
}
