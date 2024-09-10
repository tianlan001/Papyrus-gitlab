/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.byExtension;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.resourceSets;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.rootsOfType;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers.ElementTypesPluginChecker.ELEMENT_TYPES_CONFIGURATION_EXTENSION;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.toolsmiths.plugin.builder.AbstractPapyrusBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.IPapyrusBuilderProvider;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusBuilderKind;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PluginCheckerBuilder;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.ListMultimap;

/**
 * Builder provider for <em>Element Types Configurations</em> models.
 */
@Component
public class ElementTypesBuilderProvider implements IPapyrusBuilderProvider {

	@Override
	public String getProblemMarkerType(PapyrusBuilderKind builderKind) {
		return ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE;
	}

	@Override
	public boolean providesBuilder(PapyrusBuilderKind builderKind, URI resourceURI) {
		return ELEMENT_TYPES_CONFIGURATION_EXTENSION.equals(resourceURI.fileExtension());
	}

	@Override
	public AbstractPapyrusBuilder getBuilder(PapyrusBuilderKind builderKind, IProject project) {
		PluginCheckerBuilder result = new PluginCheckerBuilder(ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE, this::mapElementTypesResources);

		switch (builderKind) {
		case MODEL_RESOURCE:
			result = result.withChecker(ElementTypesPluginChecker.modelValidationCheckerFactory())
					.withChecker(ElementTypesPluginChecker.customModelCheckerFactory());
			break;
		case BUNDLE_MANIFEST:
			result = result.withChecker(ElementTypesPluginChecker.modelDependenciesCheckerFactory())
					.withChecker(ElementTypesPluginChecker.buildPropertiesCheckerFactory());
			break;
		case PLUGIN_MANIFEST:
			result = result.withChecker(ElementTypesPluginChecker.extensionsCheckerFactory());
			break;
		}

		return result;
	}

	private ListMultimap<IFile, ElementTypeSetConfiguration> mapElementTypesResources(IProject project) {
		ModelResourceMapper<ElementTypeSetConfiguration> mapper = new ModelResourceMapper<>(project);
		return mapper.map(byExtension(ELEMENT_TYPES_CONFIGURATION_EXTENSION),
				resourceSets(),
				rootsOfType(ElementTypeSetConfiguration.class));
	}

}
