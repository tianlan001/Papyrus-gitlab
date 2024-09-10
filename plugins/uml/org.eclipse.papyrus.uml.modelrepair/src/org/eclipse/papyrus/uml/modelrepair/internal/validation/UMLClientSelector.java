/*****************************************************************************
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
 *****************************************************************************/

package org.eclipse.papyrus.uml.modelrepair.internal.validation;

import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.uml2.uml.Element;

/**
 * A validation client selector that matches UML elements in a Papyrus {@link ModelSet}.
 */
public class UMLClientSelector implements IClientSelector {

	public UMLClientSelector() {
		super();
	}

	public boolean selects(Object object) {
		boolean result = false;

		if (object instanceof Element) {
			result = EMFHelper.getResourceSet((Element) object) instanceof ModelSet;
		}

		return result;
	}

}
