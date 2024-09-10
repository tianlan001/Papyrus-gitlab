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
 * Contributors:
 *  Patrick Tessier (CEA LIST) patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.part;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.AcceptTimeEventActionAppliedStereotypeEditPart;


/**
 * this edit part prevent to a bad refresh with CSS
 *
 */
public class CustomAcceptTimeEventActionAppliedStereotypeEditPart extends AcceptTimeEventActionAppliedStereotypeEditPart {

	public CustomAcceptTimeEventActionAppliedStereotypeEditPart(View view) {
		super(view);

	}

	@Override
	protected void refreshLabel() {
		// do nothing the label is only refresh by the editpolicy
	}
}
