/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Tessier CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This helper is overloaded in order to fin a appropriate container for an observation
 * 
 * @since 3.0
 */
public class ObservationEditHelper extends ElementEditHelper {


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected ICommand getEditContextCommand(GetEditContextRequest req) {
		if (req.getEditCommandRequest() instanceof CreateElementRequest) {
			CreateElementRequest createRequest = (CreateElementRequest) req.getEditCommandRequest();
			// If the container is not a package we have to look for a container that is a package.
			if (!(createRequest.getContainer() instanceof org.eclipse.uml2.uml.Package)) {

				// If no common container is found try source nearest package
				EObject proposedContainer = EMFCoreUtil.getContainer(createRequest.getContainer(), UMLPackage.eINSTANCE.getPackage());
				if (proposedContainer != null) {
					createRequest.setContainer(proposedContainer);
					req.setEditContext(proposedContainer);
				}
			}
		}
		return super.getEditContextCommand(req); 
	}


}
