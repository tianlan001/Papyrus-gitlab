/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.util.ExpansionModelValidationUtil;

/**
 *
 */
public class CustomRepresentationKindImpl extends RepresentationKindImpl {

	public boolean validate(DiagnosticChain diagnostic, Map context) {
		return ExpansionModelValidationUtil.validate_loadclasses(this, diagnostic, context);
	}
}
