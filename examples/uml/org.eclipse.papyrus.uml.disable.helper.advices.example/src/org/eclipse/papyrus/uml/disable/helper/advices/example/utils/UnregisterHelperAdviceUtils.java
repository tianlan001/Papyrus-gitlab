/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.disable.helper.advices.example.utils;

import java.util.Map;

import org.eclipse.gmf.runtime.emf.type.core.IAdviceBindingDescriptor;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.AdviceConfigurationTypeRegistry;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.types.core.utils.ElementTypeRegistryUtils;

/**
 * This class allows to define utils methods to unregister the helper advice
 */
public class UnregisterHelperAdviceUtils {

	/**
	 * This allows to unregister helper advice depending to the context, elementTypeSet and the advice identifier.
	 *
	 * @param contextId
	 *            The client context identifier.
	 * @param elementTypeSetId
	 *            The element types set identifier.
	 * @param adviceIdentifier
	 *            The helper advice identifier.
	 * @return <code>true</code> if the helper advice was successfully unregistered, <code>false</code> otherwise.
	 */
	public static boolean unregisterHelperAdvice(final String contextId, final String elementTypeSetId, final String adviceIdentifier) {
		final Map<String, Map<String, ElementTypeSetConfiguration>> elementTypeSetConfigurationsArchitecture = ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations();
		if (null == elementTypeSetConfigurationsArchitecture || elementTypeSetConfigurationsArchitecture.isEmpty()) {
			return false;
		}
		final Map<String, ElementTypeSetConfiguration> elementTypeSetConfigurations = elementTypeSetConfigurationsArchitecture.get(contextId);
		if (null == elementTypeSetConfigurations || elementTypeSetConfigurations.isEmpty()) {
			return false;
		}
		final ElementTypeSetConfiguration elementTypeSet = elementTypeSetConfigurations.get(elementTypeSetId);
		if (null == elementTypeSet) {
			return false;
		}

		// Remove adviceBindings
		for (final AbstractAdviceBindingConfiguration adviceBindingConfiguration : elementTypeSet.getAdviceBindingsConfigurations()) {
			final IAdviceBindingDescriptor advice = AdviceConfigurationTypeRegistry.getInstance().getEditHelperAdviceDecriptor(adviceBindingConfiguration);
			if (advice != null && adviceIdentifier.equals(advice.getId())) {
				ElementTypeRegistryUtils.removeAdviceDescriptorFromBindings(advice);
				return true;
			}
		}

		return false;
	}

}
