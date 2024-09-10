/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.constraint;

import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.constraints.constraints.JavaQuery;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusLabelEditPart;

/**
 * A constraint to know if we are able to get a diagram editor for PapyrusLabel
 * attached to a port.
 */
public class IsPortLabelConstraint implements JavaQuery {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.constraints.constraints.JavaQuery#match(java.lang.Object)
	 *
	 * @param selection
	 * @return true if match
	 */
	@Override
	public boolean match(Object selection) {
		boolean match = false;
		if (selection instanceof EditPart) {
			if (selection instanceof PapyrusLabelEditPart && ((EditPart) selection)
					.getParent() instanceof org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart) {
				match = true;
			}
		}
		return match;
	}

}
