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
package org.eclipse.papyrus.infra.types.core.factories.impl;

import org.eclipse.gmf.runtime.emf.type.core.AdviceBindingInheritance;
import org.eclipse.gmf.runtime.emf.type.core.IContainerDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration;
import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;
import org.eclipse.papyrus.infra.types.ContainerConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ContainerConfigurationTypeRegistry;
import org.eclipse.papyrus.infra.types.core.registries.MatcherConfigurationTypeRegistry;

public abstract class AbstractEditHelperAdviceFactory<T extends AbstractEditHelperAdviceConfiguration> extends AbstractAdviceFactory<T> {

	@Override
	protected String getTypeId(T adviceConfiguration) {
		return adviceConfiguration.getTarget().getIdentifier();
	}

	@Override
	protected AdviceBindingInheritance getInheritance(T adviceConfiguration) {
		return AdviceBindingInheritance.ALL;
	}

	@Override
	protected IContainerDescriptor getContainerDescriptor(T adviceConfiguration) {
		ContainerConfiguration containerConfiguration = ((SpecializationTypeConfiguration) adviceConfiguration.eContainer()).getContainerConfiguration();
		if (containerConfiguration == null) {
			return null;
		}
		IContainerDescriptor containerDescriptor = ContainerConfigurationTypeRegistry.getInstance().getContainerDescriptor(containerConfiguration);
		return containerDescriptor;
	}

	@Override
	protected IElementMatcher getMatcher(T adviceConfiguration) {
		AbstractMatcherConfiguration matcherConfiguration = ((SpecializationTypeConfiguration) adviceConfiguration.eContainer()).getMatcherConfiguration();
		if (matcherConfiguration == null) {
			return null;
		}
		IElementMatcher matcher = MatcherConfigurationTypeRegistry.getInstance().getMatcher(matcherConfiguration);
		return matcher;
	}

	@Override
	protected String getId(T adviceConfiguration) {
		return adviceConfiguration.getTarget().getIdentifier();
	}
}
