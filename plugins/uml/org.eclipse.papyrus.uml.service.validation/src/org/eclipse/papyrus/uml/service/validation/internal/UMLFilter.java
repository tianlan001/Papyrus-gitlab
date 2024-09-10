/*****************************************************************************
 * Copyright (c) 2016, 2020 CEA LIST and others.
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
 *   Jeremie TATIBOUET (CEA LIST) jeremie.tatibouet@cea.fr
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.validation.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.uml.service.validation.api.AbstractPapyrusValidationFilter;

/**
 * The UML filter enables a diagnotician to triggered on a UML Papyrus model using the UML architecture
 */
public class UMLFilter extends AbstractPapyrusValidationFilter {

	/**
	 * Get the expected context ID
	 *
	 * @return context ID
	 */
	@Override
	public List<String> getExpectedValidationContext() {
		List<String> architectureContext = new ArrayList<String>();
		architectureContext.add(TypeContext.getDefaultContextId());
		return architectureContext;
	}

}
