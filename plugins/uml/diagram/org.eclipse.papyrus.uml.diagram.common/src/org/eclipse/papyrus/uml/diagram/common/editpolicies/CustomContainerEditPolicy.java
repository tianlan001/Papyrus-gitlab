/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerNodeEditPolicy;

/**
 * this class is used in order to trace link between elements even if the
 * element contains a XYlayout. the method ContainerEditPolicy is overloaded the
 * method has to return null in order to select the editpart parent. this policy
 * has to be associated to container XY layout
 */
public class CustomContainerEditPolicy extends ContainerNodeEditPolicy {

	/**
	 *
	 * {@inheritedDoc}
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		return null;
	}
}
