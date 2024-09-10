/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, EclipseSource, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alexandra Buzila (EclipseSource) - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.toolsmiths.plugin.builder.messages"; //$NON-NLS-1$
	public static String AbstractPapyrusBuilder_PapyrusBuilder;
	public static String GenericEMFModelBuilder_noBundleFoundForNsUri;
	public static String PluginBuilderPreferenceGroup_activateBuilder;
	public static String PluginBuilderPreferenceGroup_activateBuildModel;
	public static String PluginBuilderPreferenceGroup_checkDependenciesRange;
	public static String PluginBuilderPreferenceGroup_checkModelDependencies;
	public static String PluginBuilderPreferenceGroup_checkNoReexportedDependencies;
	public static String PluginBuilderPreferenceGroup_manifestValidation;
	public static String PluginBuilderPreferenceGroup_modelValidation;
	public static String PluginBuilderPreferenceGroup_PapyrusPluginBuilder;
	public static String PluginBuilderPreferenceGroup_validateManifest;
	public static String PluginBuilderPreferenceGroup_validateModels;
	public static String PluginBuilderPreferencePage_builderPreferenceGroupDescription;
	public static String XWTModelBuilder_noBundleProvidingResourceUri;
	public static String XWTModelBuilder_noBundleWithUri;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
