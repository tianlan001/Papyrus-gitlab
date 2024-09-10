/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
package org.eclipse.papyrus.infra.types.rulebased.core;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

public class DefaultRule implements IRule<RuleConfiguration> {



	/**
	 * @see org.eclipse.papyrus.infra.types.rulebased.core.IRule#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		
		return true;
	}

	/**
	 * @see org.eclipse.papyrus.infra.types.rulebased.core.IRule#init(org.eclipse.papyrus.infra.types.rulebasedtypes.InvariantRuleConfiguration)
	 *
	 * @param invariantRuleConfiguration
	 */
	@Override
	public void init(RuleConfiguration invariantRuleConfiguration) {

	}

	/**
	 * @see org.eclipse.papyrus.infra.types.rulebased.core.IRule#matches(org.eclipse.emf.ecore.EObject)
	 *
	 * @param eObject
	 * @return
	 */
	@Override
	public boolean matches(EObject eObject) {
		return true;
	}


}
