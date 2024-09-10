/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Tessie (CEA LIST) - Initial API and implementation
 *   Jeremie TATIBOUET (CEA LIST) jeremie.tatibouet@cea.fr
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.validation.api;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.services.validation.IValidationFilter;

/**
 * A Papyrus validation filter defines the conditions that must hold in order
 * to enable a diagnostician. In particular, the Papyrus validation filter
 * checks if the architecture enabled at validation time is the one expected
 * by the diagnostician.
 */
public abstract class AbstractPapyrusValidationFilter implements IValidationFilter {

	public AbstractPapyrusValidationFilter() {
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.validation.IValidationFilter#isApplicable(org.eclipse.emf.ecore.EObject)
	 *
	 * @param an
	 *            element of the model to validate
	 *
	 * @return true if (1) the model element is not null, (2) contained in a resource that is itsel in a model set and (3) the current architecture is the expected one.
	 */
	public boolean isApplicable(EObject element) {
		boolean applicable = false;
		if (element != null && element.eResource() != null && element.eResource().getResourceSet() instanceof ModelSet) {
			Iterator<String> contextIt = getExpectedValidationContext().iterator();
			while (!applicable && contextIt.hasNext()) {
				applicable = checkContext(element, contextIt.next());
			}
		}
		return applicable;
	}

	/**
	 * Check if the context is the one expected by the diagnotician owning this filter
	 *
	 * @param element
	 *            - the context model element
	 *
	 * @param expectedContextID
	 *
	 * @return true if the context is the expected one and false otherwise
	 */
	private boolean checkContext(final EObject element, final String expectedContextID) {
		boolean applicable = false;
		ArchitectureDescriptionUtils adUtils = new ArchitectureDescriptionUtils((ModelSet) element.eResource().getResourceSet());
		MergedArchitectureContext architectureContext = adUtils.getArchitectureContext();
		if (architectureContext != null) {
			applicable = architectureContext.getId().equals(expectedContextID);
		}
		return applicable;
	}

	/**
	 * Get the expected context ID
	 *
	 * I this method add context where the filter shall be applied.
	 *
	 * @return context ID
	 */
	public abstract List<String> getExpectedValidationContext();

}