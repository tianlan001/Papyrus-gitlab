/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *
 * 		Maged Elaasar - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.architecture.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;

/**
 * A no context matcher used to register element type bindings in the extension points  
 */
public class NoContextEnablementMatcher implements IElementMatcher {

	@Override
	public boolean matches(EObject eObject) {
		return false;
	}
}
