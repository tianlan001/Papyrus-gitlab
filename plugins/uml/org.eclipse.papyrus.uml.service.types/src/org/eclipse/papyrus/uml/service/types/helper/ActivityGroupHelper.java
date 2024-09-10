/*****************************************************************************
 * Copyright (c) 2012, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 462979
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * this is an helper that redirect destroy request to get destroy command from uml plugin
 *
 */

public class ActivityGroupHelper extends ElementEditHelper {

	@Override
	protected ICommand getBasicDestroyElementCommand(DestroyElementRequest req) {
		ICommand result = req.getBasicDestroyCommand();

		if (result == null) {
			result = new ActivityNodeHelper.DestroyActivityOwnedElementCommand(req);
		} else {
			// ensure that re-use of this request will not accidentally
			// propagate this command, which would destroy the wrong object
			req.setBasicDestroyCommand(null);
		}

		return result;
	}

	protected boolean isActivityNode(IElementType type) {
		return type.getEClass() != null && UMLPackage.eINSTANCE.getActivityNode().isSuperTypeOf(type.getEClass());
	}

	/**
	 * Activity nodes creation relies on substitution of the containment feature from advices. 
	 * So in contrast to base class logic, we need to approve creation requests even if the actually passed feature is not a containment. 
	 */
	protected boolean approveNonContainmentActivityNode(CreateElementRequest request) {
		IElementType type = request.getElementType();
		if (type == null || type.getEClass() == null) {
			return false;
		}
		if (UMLPackage.eINSTANCE.getActivityNode().isSuperTypeOf(type.getEClass())) {
			return true;
		}
		return super.approveRequest(request);
	}

}
