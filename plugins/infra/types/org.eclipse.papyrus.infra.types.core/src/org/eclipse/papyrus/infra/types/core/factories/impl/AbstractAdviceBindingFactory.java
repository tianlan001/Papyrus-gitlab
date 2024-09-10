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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Add createAdviceBindingConfiguration method
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.factories.impl;

import org.eclipse.gmf.runtime.emf.type.core.AdviceBindingInheritance;
import org.eclipse.gmf.runtime.emf.type.core.IContainerDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;
import org.eclipse.papyrus.infra.types.ContainerConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ContainerConfigurationTypeRegistry;
import org.eclipse.papyrus.infra.types.core.registries.MatcherConfigurationTypeRegistry;

public abstract class AbstractAdviceBindingFactory<T extends AbstractAdviceBindingConfiguration> extends AbstractAdviceFactory<T> {

	private final String ALL_TYPE_ID = "*";

	@Override
	protected String getTypeId(T adviceConfiguration) {
		if (adviceConfiguration.getTarget() != null) {
			return adviceConfiguration.getTarget().getIdentifier();
		} else {
			return ALL_TYPE_ID;
		}
	}

	@Override
	protected IContainerDescriptor getContainerDescriptor(T adviceConfiguration) {
		ContainerConfiguration containerConfiguration = adviceConfiguration.getContainerConfiguration();
		if (containerConfiguration == null) {
			return null;
		}
		IContainerDescriptor containerDescriptor = ContainerConfigurationTypeRegistry.getInstance().getContainerDescriptor(containerConfiguration);
		return containerDescriptor;
	}

	@Override
	protected IElementMatcher getMatcher(T adviceConfiguration) {
		AbstractMatcherConfiguration matcherConfiguration = adviceConfiguration.getMatcherConfiguration();
		if (matcherConfiguration == null) {
			return null;
		}
		IElementMatcher matcher = MatcherConfigurationTypeRegistry.getInstance().getMatcher(matcherConfiguration);
		return matcher;
	}

	@Override
	protected AdviceBindingInheritance getInheritance(T adviceConfiguration) {
		return AdviceBindingInheritance.getAdviceBindingInheritance(adviceConfiguration.getInheritance().getName());
	}

	@Override
	protected String getId(T adviceConfiguration) {
		return adviceConfiguration.getIdentifier();
	}

	/**
	 * @return an instance of the defined advice binding configuration
	 * @since 3.0
	 */
	public abstract AbstractAdviceBindingConfiguration createAdviceBindingConfiguration();
}
