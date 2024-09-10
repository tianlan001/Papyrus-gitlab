/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 459174
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.factories.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IAdviceBindingDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IContainerDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration;
import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;
import org.eclipse.papyrus.infra.types.ContainerConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.Activator;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType;
import org.eclipse.papyrus.infra.types.core.impl.NullEditHelperAdvice;
import org.eclipse.papyrus.infra.types.core.registries.AdviceConfigurationTypeRegistry;
import org.eclipse.papyrus.infra.types.core.registries.ContainerConfigurationTypeRegistry;
import org.eclipse.papyrus.infra.types.core.registries.MatcherConfigurationTypeRegistry;

public class SpecializationTypeFactory extends AbstractElementTypeConfigurationFactory<SpecializationTypeConfiguration> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IHintedType createElementType(SpecializationTypeConfiguration elementTypeConfiguration) {
		return new ConfiguredHintedSpecializationElementType(getID(elementTypeConfiguration), getIconURL(elementTypeConfiguration), getDisplayName(elementTypeConfiguration), getSpecializedID(elementTypeConfiguration),
				createElementMatcher(elementTypeConfiguration), createContainerDescriptor(elementTypeConfiguration), getEditHelperAdvice(elementTypeConfiguration), getSemanticHint(elementTypeConfiguration), elementTypeConfiguration);
	}

	protected IEditHelperAdvice getEditHelperAdvice(SpecializationTypeConfiguration elementTypeConfiguration) {
		AbstractEditHelperAdviceConfiguration editHelperAdviceConfiguration = elementTypeConfiguration.getEditHelperAdviceConfiguration();
		if (editHelperAdviceConfiguration == null) {
			return NullEditHelperAdvice.getInstance();
		}
		IAdviceBindingDescriptor editHelperAdviceDescriptor = AdviceConfigurationTypeRegistry.getInstance().getEditHelperAdviceDecriptor(editHelperAdviceConfiguration);
		return editHelperAdviceDescriptor.getEditHelperAdvice();
	}

	protected IContainerDescriptor createContainerDescriptor(SpecializationTypeConfiguration elementTypeConfiguration) {
		ContainerConfiguration containerConfiguration = elementTypeConfiguration.getContainerConfiguration();
		if (containerConfiguration == null) {
			return null;
		}
		IContainerDescriptor containerDescriptor = ContainerConfigurationTypeRegistry.getInstance().getContainerDescriptor(containerConfiguration);
		return containerDescriptor;
	}

	protected IElementType[] getSpecializedID(SpecializationTypeConfiguration elementTypeConfiguration) {
		// Specialized elementTypes
		List<IElementType> specializedTypes = new ArrayList<IElementType>((elementTypeConfiguration).getSpecializedTypes().size());
		for (ElementTypeConfiguration specializedTypeConfiguration : (elementTypeConfiguration).getSpecializedTypes()) {
			IElementType specializedType = ElementTypeRegistry.getInstance().getType(specializedTypeConfiguration.getIdentifier());
			if (specializedType != null) {
				specializedTypes.add(specializedType);
			} else {
				Activator.log.info("Unable to add specialization \"" + getID(elementTypeConfiguration) + "\" to \"" + specializedTypeConfiguration + "\"");
			}
		}
		IElementType[] elementTypes = specializedTypes.toArray(new IElementType[] {});
		return elementTypes;
	}

	protected IElementMatcher createElementMatcher(SpecializationTypeConfiguration configuration) {
		AbstractMatcherConfiguration matcherConfiguration = configuration.getMatcherConfiguration();
		if (matcherConfiguration == null) {
			return null;
		}
		IElementMatcher matcher = MatcherConfigurationTypeRegistry.getInstance().getMatcher(matcherConfiguration);
		return matcher;
	}
}
