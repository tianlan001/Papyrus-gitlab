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

package org.eclipse.papyrus.infra.types.rulebased.core;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrus.infra.types.rulebased.AndRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;


public class DefaultRuleElementMatcher implements IElementMatcher {


	RuleBasedTypeConfiguration configuration;

	public DefaultRuleElementMatcher(RuleBasedTypeConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementMatcher#matches(org.eclipse.emf.ecore.EObject)
	 *
	 * @param eObject
	 * @return
	 */
	@Override
	public boolean matches(EObject eObject) {
		RuleConfiguration RuleConfiguration = configuration.getRuleConfiguration();

		return processRule(RuleConfiguration, eObject);
	}

	/**
	 * @param RuleConfiguration
	 * @param eObject
	 * @return
	 */
	protected boolean processRule(RuleConfiguration RuleConfiguration, EObject eObject) {
		if (RuleConfiguration instanceof CompositeRuleConfiguration) {
			return processCompositeRule((CompositeRuleConfiguration) RuleConfiguration, eObject);
		} else if (RuleConfiguration instanceof NotRuleConfiguration) {
			RuleConfiguration composedRule = ((NotRuleConfiguration) RuleConfiguration).getComposedRule();
			return !processRule(composedRule, eObject);
		} else {
			return RuleConfigurationTypeRegistry.getInstance().getRule(RuleConfiguration).matches(eObject);
		}
	}

	/**
	 * @param compositeRule
	 * @param eObject
	 * @return
	 */
	protected boolean processCompositeRule(CompositeRuleConfiguration compositeRule, EObject eObject) {
		Iterator<RuleConfiguration> iterator = compositeRule.getComposedRules().iterator();
		RuleConfiguration nextComposedRuleConfiguration = iterator.next();
		boolean result = processRule(nextComposedRuleConfiguration, eObject);

		while (iterator.hasNext()) {
			nextComposedRuleConfiguration = iterator.next();

			boolean resultNextComposedRuleConfiguration = processRule(nextComposedRuleConfiguration, eObject);

			if (compositeRule instanceof OrRuleConfiguration) {
				if (result == false && resultNextComposedRuleConfiguration) {
					result = true;
				}
			} else if (compositeRule instanceof AndRuleConfiguration) {
				if (result == true && !resultNextComposedRuleConfiguration) {
					result = false;
				}
			}
		}

		return result;
	}



}
