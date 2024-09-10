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

package org.eclipse.papyrus.toolsmiths.validation.architecture.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.internal.checkers.ArchitecturePluginChecker.ARCHITECTURE_EXTENSION;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.byExtension;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.resourceSets;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.rootsOfType;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.toolsmiths.plugin.builder.AbstractPapyrusBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.IPapyrusBuilderProvider;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusBuilderKind;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PluginCheckerBuilder;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.ListMultimap;

/**
 * Builder provider for <em>Architecture Domain</em> models.
 */
@Component
public class ArchitectureBuilderProvider implements IPapyrusBuilderProvider {

	@Override
	public String getProblemMarkerType(PapyrusBuilderKind builderKind) {
		return ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE;
	}

	@Override
	public boolean providesBuilder(PapyrusBuilderKind builderKind, URI resourceURI) {
		return ARCHITECTURE_EXTENSION.equals(resourceURI.fileExtension());
	}

	@Override
	public AbstractPapyrusBuilder getBuilder(PapyrusBuilderKind builderKind, IProject project) {
		PluginCheckerBuilder result = new PluginCheckerBuilder(ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE, this::mapArchitectureResources);

		switch (builderKind) {
		case MODEL_RESOURCE:
			result = result.withChecker(ArchitecturePluginChecker.modelValidationCheckerFactory())
					.withChecker(ArchitecturePluginChecker.customModelCheckerFactory());
			break;
		case BUNDLE_MANIFEST:
			result = result.withChecker(ArchitecturePluginChecker.buildPropertiesCheckerFactory())
					.withChecker(ArchitecturePluginChecker.modelDependenciesCheckerFactory());
			break;
		case PLUGIN_MANIFEST:
			result = result.withChecker(ArchitecturePluginChecker.extensionsCheckerFactory());
			break;
		}

		return result;
	}

	private ListMultimap<IFile, ArchitectureDomain> mapArchitectureResources(IProject project) {
		ModelResourceMapper<ArchitectureDomain> mapper = new ModelResourceMapper<>(project);
		return mapper.map(byExtension(ARCHITECTURE_EXTENSION),
				resourceSets(ResourceUtils.createWorkspaceAwarePackageRegistry()),
				rootsOfType(ArchitectureDomain.class));
	}

}
