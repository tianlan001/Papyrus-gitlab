/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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
 * CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.viewpoints.policy;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.architecture.representation.ModelRule;

/**
 * Manager for the rule constraints
 */
public class RuleConstraintManager {

	/**
	 * The singleton instance
	 */
	private static RuleConstraintManager instance;

	public static synchronized RuleConstraintManager getInstance() {
		if (instance == null) {
			instance = new RuleConstraintManager();
		}
		return instance;
	}

	public boolean matchRule(ModelRule rule, EObject element) {
		// if no rule, return true
		List<ConstraintDescriptor> constraintDescriptors = rule.getConstraints();
		if (constraintDescriptors == null || constraintDescriptors.isEmpty()) {
			return true;
		}

		return ModelRuleConstraintEngine.getInstance().matchesRule(rule, element);

	}

}
