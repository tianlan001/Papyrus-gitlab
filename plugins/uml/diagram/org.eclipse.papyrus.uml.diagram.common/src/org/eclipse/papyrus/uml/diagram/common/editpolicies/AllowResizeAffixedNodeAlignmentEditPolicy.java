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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * This class replaces the original AffixedNodeAlignmentEditPolicy
 * it just overrides the command (that is null in the class AffixedNodeAlignmentEditPolicy) that
 * causes to resize a child figure.
 * see the class CustomFullPortAffixedEditPart
 * 
 * @author Trung-Truc Nguyen
 *
 */
public class AllowResizeAffixedNodeAlignmentEditPolicy extends AffixedNodeAlignmentEditPolicy {

	// without this command, port resize command will never be called.
	public Command getCommand(Request request) {
		if (REQ_RESIZE_CHILDREN.equals(request.getType())) {
			return getResizeChildrenCommand((ChangeBoundsRequest) request);
		}
		return super.getCommand(request);
	}
}
