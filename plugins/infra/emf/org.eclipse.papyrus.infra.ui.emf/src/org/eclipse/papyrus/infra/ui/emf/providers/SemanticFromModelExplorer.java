/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
package org.eclipse.papyrus.infra.ui.emf.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.IElementWithSemantic;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * This class is used to obtain the semantic element for element of the model explorer
 */
// FIXME: Remove the dependency to gmf
public class SemanticFromModelExplorer implements IElementWithSemantic {

	/**
	 * {@inheritDoc}
	 */
	public Object getSemanticElement(Object wrapper) {
		EObject semantic = EMFHelper.getEObject(wrapper);
		if (semantic != null) {
			return semantic;
		}

		if (wrapper instanceof Diagram) {
			return wrapper;
		}

		return null;
	}

}
