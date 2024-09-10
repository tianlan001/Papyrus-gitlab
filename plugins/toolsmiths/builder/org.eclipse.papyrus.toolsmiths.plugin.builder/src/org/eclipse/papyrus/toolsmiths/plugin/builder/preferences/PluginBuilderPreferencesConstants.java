/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.preferences;

/**
 *
 */
public final class PluginBuilderPreferencesConstants {

	/**
	 *
	 * Constructor.
	 *
	 */
	private PluginBuilderPreferencesConstants() {
		// to prevent instanciation
	}

	/**
	 * The preference key to activate/unactivate the main Papyrus Builder
	 */
	public static final String ACTIVATE_MAIN_PAPYRUS_BUILDER = "ActivateMainPapyrusBuilder"; //$NON-NLS-1$

	/* -----------------------CHECK_MANIFEST----------------------------------- */
	/**
	 * the preference key to activate/desactivate the check of the manifest
	 */
	public static final String ACTIVATE_PAPYRUS_MANIFEST_BUILDER = "ActivatePapyrusManifestBuilder"; //$NON-NLS-1$

	/**
	 * the preferen
	 * ce key to activate the check of no reexport
	 */
	public static final String PAPYRUS_MANIFEST_BUILDER_CHECK_NO_REEXPORT = "PapyrusManifestBuilder_CheckReexport"; //$NON-NLS-1$

	/**
	 * the preference key to activate the check of dependencies ranges
	 */
	public static final String PAPYRUS_MANIFEST_BUILDER_CHECK_DEPENDENCY_RANGE = "PapyrusManifestBuilder_CheckDependencyRange"; //$NON-NLS-1$

	/* -----------------------MODEL VALIDATION----------------------------------- */
	/**
	 * the preference key to activate/desactivate the check of the model
	 */
	public static final String ACTIVATE_PAPYRUS_MODEL_BUILDER = "ActivatePapyrusModelBuilder"; //$NON-NLS-1$

	/**
	 * the preference key to activate the check of model dependencies
	 */
	public static final String PAPYRUS_MODEL_BUILDER_CHECK_MODEL_DEPENDENCIES = "PapyrusModelBuilder_CheckModelDependencies"; //$NON-NLS-1$

	/**
	 * the preference key to activate the model validation
	 */
	public static final String PAPYRUS_MODEL_BUILDER_VALIDATE_MODEL = "PapyrusModelBuilder_ValidateModel"; //$NON-NLS-1$
}
