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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.byContentType;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.byExtension;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.resourceSets;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.rootsOfType;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers.PropertiesEnvironmentPluginChecker.CONTENT_TYPE;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers.PropertiesEnvironmentPluginChecker.ENVIRONMENT_EXTENSION;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers.PropertiesEnvironmentPluginChecker.XMI_EXTENSION;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.properties.environment.Environment;
import org.eclipse.papyrus.toolsmiths.plugin.builder.AbstractPapyrusBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.IPapyrusBuilderProvider;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusBuilderKind;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PluginCheckerBuilder;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.ListMultimap;

/**
 * Builder provider for <em>Properties Environment</em> models.
 */
@Component
public class PropertiesEnvironmentBuilderProvider implements IPapyrusBuilderProvider {

	@Override
	public String getProblemMarkerType(PapyrusBuilderKind builderKind) {
		return PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE;
	}

	@Override
	public boolean providesBuilder(PapyrusBuilderKind builderKind, URI resourceURI) {
		return ENVIRONMENT_EXTENSION.equals(resourceURI.fileExtension())
				|| (XMI_EXTENSION.equals(resourceURI.fileExtension()) && hasContentType(resourceURI, CONTENT_TYPE));
	}

	@Override
	public AbstractPapyrusBuilder getBuilder(PapyrusBuilderKind builderKind, IProject project) {
		PluginCheckerBuilder result = new PluginCheckerBuilder(PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE, this::mapEnvironmentResources);

		switch (builderKind) {
		case MODEL_RESOURCE:
			result = result.withChecker(PropertiesEnvironmentPluginChecker.modelValidationCheckerFactory())
					.withChecker(PropertiesEnvironmentPluginChecker.customModelCheckerFactory());
			break;
		case BUNDLE_MANIFEST:
			result = result.withChecker(PropertiesEnvironmentPluginChecker.buildPropertiesCheckerFactory())
					.withChecker(PropertiesEnvironmentPluginChecker.modelDependenciesCheckerFactory());
			break;
		case PLUGIN_MANIFEST:
			result = result.withChecker(PropertiesEnvironmentPluginChecker.extensionsCheckerFactory());
			break;
		}

		return result;
	}

	private ListMultimap<IFile, Environment> mapEnvironmentResources(IProject project) {
		ModelResourceMapper<Environment> mapper = new ModelResourceMapper<>(project);
		return mapper.map(byExtension(ENVIRONMENT_EXTENSION).or(byExtension(XMI_EXTENSION).and(byContentType(CONTENT_TYPE))),
				resourceSets(ResourceUtils.createWorkspaceAwarePackageRegistry()),
				rootsOfType(Environment.class));
	}

}
