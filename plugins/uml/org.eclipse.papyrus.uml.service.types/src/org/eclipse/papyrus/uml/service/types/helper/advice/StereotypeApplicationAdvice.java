/*
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.uml2.uml.Element;

/**
 * An advisor of element destruction that ensure destruction of the stereotype applications attached to the element.
 */
public class StereotypeApplicationAdvice extends AbstractEditHelperAdvice {

	public StereotypeApplicationAdvice() {
		super();
	}

	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		ICommand result = super.getBeforeDestroyDependentsCommand(request);

		Element destructee = TypeUtils.as(request.getElementToDestroy(), Element.class);
		if (destructee != null) {
			Collection<? extends EObject> stereotypeApplications = destructee.getStereotypeApplications();
			if (stereotypeApplications != null) {
				// Destroy them
				DestroyDependentsRequest dependents = new DestroyDependentsRequest(request.getEditingDomain(), destructee, false);
				dependents.setClientContext(request.getClientContext());
				dependents.addParameters(request.getParameters());
				ICommand dependentsCommand = dependents.getDestroyDependentsCommand(stereotypeApplications);

				if (dependentsCommand != null) {
					result = (result == null) ? dependentsCommand : result.compose(dependentsCommand);
				}
			}
		}

		return result;
	}
}
