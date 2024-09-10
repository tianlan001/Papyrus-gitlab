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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.types.rulebased.Activator;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

public class RuleConfigurationTypeRegistry {

	/** private singleton instance */
	private static RuleConfigurationTypeRegistry registry;

	/** map configuration type to invariantRule */
	protected Map<String, IRule<? extends RuleConfiguration>> invariantRuleConfigurationTypeToInvariantRule = null;

	/**
	 * returns the singleton instance of this registry
	 *
	 * @return the singleton instance of this registry
	 */
	public static synchronized RuleConfigurationTypeRegistry getInstance() {
		if (registry == null) {
			registry = new RuleConfigurationTypeRegistry();
			registry.init();
		}
		return registry;
	}

	/**
	 * Inits the registry.
	 */
	protected void init() {
		invariantRuleConfigurationTypeToInvariantRule = new HashMap<String, IRule<? extends RuleConfiguration>>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(IRuleExtensionPoint.EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : elements) {
			String configurationClass = configurationElement.getAttribute(IRuleExtensionPoint.CONFIGURATION_CLASS);

			try {
				Object invariantRuleClass = configurationElement.createExecutableExtension(IRuleExtensionPoint.INVARIANT_RULE_CLASS);
				if (invariantRuleClass instanceof IRule) {
					invariantRuleConfigurationTypeToInvariantRule.put(configurationClass, (IRule<?>) invariantRuleClass);
				}
			} catch (CoreException e) {
				Activator.log.error(e);
			}
		}
	}

	public <T extends RuleConfiguration> IRule<? extends RuleConfiguration> getRule(T invariantRuleConfiguration) {
		String invariantRuleConfigurationType = invariantRuleConfiguration.eClass().getInstanceTypeName();
		// We assume here that the right invariantType is registered for the right InvariantRuleConfiguration
		@SuppressWarnings("unchecked")
		IRule<T> invariantRule = (IRule<T>) invariantRuleConfigurationTypeToInvariantRule.get(invariantRuleConfigurationType);
		if (invariantRule != null) {
			invariantRule.init(invariantRuleConfiguration);
			return invariantRule;
		} else {
			return new DefaultRule();
		}
	}
}
