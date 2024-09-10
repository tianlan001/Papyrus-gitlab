/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Bug 496189
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.uml.service.types.command.DestroyEnumerationCommand;
import org.eclipse.uml2.uml.Enumeration;

/**
 * @since 3.1
 */
public class EnumerationEditHelper extends ElementEditHelper {


	@Override
	protected ICommand getBasicDestroyElementCommand(DestroyElementRequest request) {
		if (false == request.getElementToDestroy() instanceof Enumeration) {
			return super.getBasicDestroyElementCommand(request);
		}
		ICommand result = request.getBasicDestroyCommand();

		if (result == null) {
			// Bug 496189: We need a specific destroy command for Enumerations
			result = new DestroyEnumerationCommand(request);
		} else {
			// ensure that re-use of this request will not accidentally
			// propagate this command, which would destroy the wrong object
			request.setBasicDestroyCommand(null);
		}

		return result;
	}

}
