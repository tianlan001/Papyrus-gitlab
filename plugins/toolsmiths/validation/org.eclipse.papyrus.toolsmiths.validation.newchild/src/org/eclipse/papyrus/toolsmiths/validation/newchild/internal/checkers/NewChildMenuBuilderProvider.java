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

package org.eclipse.papyrus.toolsmiths.validation.newchild.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.byExtension;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.resourceSets;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.rootsOfType;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.checkers.NewChildMenuPluginChecker.NEW_CHILD_MENU_EXTENSION;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu;
import org.eclipse.papyrus.toolsmiths.plugin.builder.AbstractPapyrusBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.IPapyrusBuilderProvider;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusBuilderKind;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PluginCheckerBuilder;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.ListMultimap;

/**
 * Builder provider for <em>Element Creation Menu</em> models.
 */
@Component
public class NewChildMenuBuilderProvider implements IPapyrusBuilderProvider {

	@Override
	public String getProblemMarkerType(PapyrusBuilderKind builderKind) {
		return NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE;
	}

	@Override
	public boolean providesBuilder(PapyrusBuilderKind builderKind, URI resourceURI) {
		return NEW_CHILD_MENU_EXTENSION.equals(resourceURI.fileExtension());
	}

	@Override
	public AbstractPapyrusBuilder getBuilder(PapyrusBuilderKind builderKind, IProject project) {
		PluginCheckerBuilder result = new PluginCheckerBuilder(NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE, this::mapNewChildResources);

		switch (builderKind) {
		case MODEL_RESOURCE:
			result = result.withChecker(NewChildMenuPluginChecker.modelValidationCheckerFactory())
					.withChecker(NewChildMenuPluginChecker.customModelCheckerFactory());
			break;
		case BUNDLE_MANIFEST:
			result = result.withChecker(NewChildMenuPluginChecker.buildPropertiesCheckerFactory())
					.withChecker(NewChildMenuPluginChecker.modelDependenciesCheckerFactory());
			break;
		case PLUGIN_MANIFEST:
			result = result.withChecker(NewChildMenuPluginChecker.extensionsCheckerFactory());
			break;
		}

		return result;
	}

	private ListMultimap<IFile, Menu> mapNewChildResources(IProject project) {
		ModelResourceMapper<Menu> mapper = new ModelResourceMapper<>(project);
		return mapper.map(byExtension(NEW_CHILD_MENU_EXTENSION), resourceSets(), rootsOfType(Menu.class));
	}

}
