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

package org.eclipse.papyrus.toolsmiths.validation.profile.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.allElementsOfType;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.resourceSets;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.rootsOfType;
import static org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants.PROFILE_PLUGIN_VALIDATION_MARKER_TYPE;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.toolsmiths.plugin.builder.AbstractPapyrusBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.IPapyrusBuilderProvider;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusBuilderKind;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PluginCheckerBuilder;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.quickfix.StaticProfileHelper;
import org.eclipse.uml2.uml.Profile;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.ListMultimap;

/**
 * Builder provider for <em>UML Profiles</em>.
 */
@Component
public class ProfileBuilderProvider implements IPapyrusBuilderProvider {

	@Override
	public String getProblemMarkerType(PapyrusBuilderKind builderKind) {
		return PROFILE_PLUGIN_VALIDATION_MARKER_TYPE;
	}

	@Override
	public boolean providesBuilder(PapyrusBuilderKind builderKind, URI resourceURI) {
		String fileName = resourceURI.lastSegment();
		int firstDot = fileName.indexOf('.');

		// Note that if there is no dot at all, then this will be the file name
		return fileName.substring(firstDot + 1).equals(resourceURI.fileExtension());
	}

	@Override
	public AbstractPapyrusBuilder getBuilder(PapyrusBuilderKind builderKind, IProject project) {
		PluginCheckerBuilder result = null;

		switch (builderKind) {
		case MODEL_RESOURCE:
			result = new PluginCheckerBuilder(PROFILE_PLUGIN_VALIDATION_MARKER_TYPE, this::mapProfilesWithStereotypes)
					.withChecker(ProfilePluginChecker.modelValidationCheckerFactory())
					.withChecker(ProfilePluginChecker.customModelCheckerFactory());
			break;
		case BUNDLE_MANIFEST:
			result = new PluginCheckerBuilder(PROFILE_PLUGIN_VALIDATION_MARKER_TYPE, this::mapProfilesResources)
					.withChecker(ProfilePluginChecker.modelDependenciesCheckerFactory())
					.withChecker(ProfilePluginChecker.buildPropertiesCheckerFactory());
			break;
		case PLUGIN_MANIFEST:
			result = new PluginCheckerBuilder(PROFILE_PLUGIN_VALIDATION_MARKER_TYPE, this::mapAllProfilesResources)
					.withChecker(ProfilePluginChecker.extensionsCheckerFactory());
			break;
		default:
			// No model resource builder
			break;
		}

		return result;
	}

	private ListMultimap<IFile, EObject> mapProfilesWithStereotypes(IProject project) {
		ModelResourceMapper<EObject> mapper = new ModelResourceMapper<>(project);
		return mapper.map(StaticProfileHelper.umlWithGenmodel(), resourceSets(), rootsOfType(EObject.class));
	}

	private ListMultimap<IFile, Profile> mapProfilesResources(IProject project) {
		ModelResourceMapper<Profile> mapper = new ModelResourceMapper<>(project);
		return mapper.map(StaticProfileHelper.umlWithGenmodel(), resourceSets(), rootsOfType(Profile.class));
	}

	private ListMultimap<IFile, Profile> mapAllProfilesResources(IProject project) {
		ModelResourceMapper<Profile> mapper = new ModelResourceMapper<>(project);
		return mapper.map(StaticProfileHelper.umlWithGenmodel(), resourceSets(), allElementsOfType(Profile.class));
	}

}
