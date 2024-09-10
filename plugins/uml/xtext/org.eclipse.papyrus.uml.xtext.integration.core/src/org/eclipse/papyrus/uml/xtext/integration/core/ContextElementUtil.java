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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.xtext.integration.core;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class ContextElementUtil {

	public static EObject getContextElement(Resource resource) {
		ContextElementAdapter existingAdapter = (ContextElementAdapter) EcoreUtil.getExistingAdapter(resource,
				ContextElementAdapter.class);
		if (existingAdapter != null) {
			return existingAdapter.getElement();
		}
		return null;
	}
}
