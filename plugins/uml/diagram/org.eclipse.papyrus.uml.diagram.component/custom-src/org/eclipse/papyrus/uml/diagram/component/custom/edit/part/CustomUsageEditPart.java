/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.edit.part;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageEditPart;

/**
 * this class is used to display an interface Realization as UML or as fill line for lollipop
 *
 * @since 3.0
 *
 */
public class CustomUsageEditPart extends UsageEditPart {

	/**
	 *
	 * Constructor.
	 *
	 * @param view
	 */
	public CustomUsageEditPart(View view) {
		super(view);
	}

	@Override
	public void refresh() {
		super.refresh();
		if ((getTarget() instanceof InterfaceEditPart) || (getTarget() instanceof InterfaceEditPartPCN)) {
			getPrimaryShape().displayAsAlink();
		} else {
			getPrimaryShape().displayAsUMLShape();
		}
	}
}
