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
 *   Remi Schnekenburger (EclipseSource) - Initial API and implementation
 *   Christian W. Damus - bugs 570097, 573886
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.profile.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Messages internationalization
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.toolsmiths.validation.profile.internal.messages.messages"; //$NON-NLS-1$
	public static String ProfileCustomValidator_0;
	public static String ProfileCustomValidator_1;
	public static String ProfileExtensionsChecker_noExternsionsDeclared;
	public static String ProfilePluginChecker_0;
	public static String ProfilePluginChecker_validateProfilePluginTask;
	public static String StaticProfilePluginErrorReporter_missingExtensionPointAttribute;
	public static String StaticProfileExtensionsBuilder_nsURI_differs;
	public static String StaticProfileExtensionsBuilder_subTask_checkingFile;
	public static String StaticProfilePluginErrorReporter_noEcoreGeneratedPackageFound;
	public static String StaticProfilePluginErrorReporter_NoPapyrusProfileExtensionFound;
	public static String StaticProfilePluginErrorReporter_noUML2GeneratedPackage;
	public static String StaticProfilePluginErrorReporter_resourceMissingFromBinaryBuild;
	public static String StaticProfilePluginErrorReporter_uiLabelIsNull;
	public static String StaticProfilePluginErrorReporter_wrongLocationForProfile;
	public static String StaticProfilePluginErrorReporter_stereotypeMismatch;
	public static String MissingGenModelAttributeMarkerResolution_description;
	public static String MissingGenModelAttributeMarkerResolution_label;
	public static String MissingUriAttributeMarkerResolution_description;
	public static String MissingUriAttributeMarkerResolution_label;
	public static String NoEcoreGenPackageMarkerResolution_description;
	public static String NoEcoreGenPackageMarkerResolution_label;
	public static String NoLocationUMLGenPackageMarkerResolution_description;
	public static String NoLocationUMLGenPackageMarkerResolution_label;
	public static String NoPapyrusProfileExtensionMarkerResolution_description;
	public static String NoPapyrusProfileExtensionMarkerResolution_label;
	public static String NoUMLGenPackageMarkerResolution_description;
	public static String NoUMLGenPackageMarkerResolution_label;
	public static String PapyrusProfileExtensionMissingNameMarkerResolution_description;
	public static String PapyrusProfileExtensionMissingNameMarkerResolution_label;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
