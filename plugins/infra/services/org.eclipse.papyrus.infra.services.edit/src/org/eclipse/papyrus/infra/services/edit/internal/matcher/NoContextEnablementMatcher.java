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
 *
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.internal.matcher;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;

/**
 * This matcher is used to always disable the context where the semantic
 * creation service are defined.
 * This means the the creation service requires to be called which explicit
 * context specification.
 *
 * This is a temporary matcher implementation used in order to avoid undesired
 * side effects from the centralized edit service and any possible use of
 * GMF extensible type framework made by diagram (either Papyrus or not) GMF tooling
 * generated diagrams.
 */
public class NoContextEnablementMatcher implements IElementMatcher {

	@Override
	public boolean matches(EObject eObject) {
		return false;
	}

}
