/*******************************************************************************
 * Copyright (c) 2006, 2020 Eclipse.org, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    dvorak - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.popup.actions;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.gmf.internal.codegen.popup.actions.messages"; //$NON-NLS-1$

	public static String DiagnosticsDialog_Copy_menuItem;

	public static String DiagnosticsDialog_exceptStackTrace_toolTip;

	public static String DiagnosticsDialog_gotoProblem_menuItem;

	public static String GMFGenExtensionModelWizard_classChooserLabel;

	public static String GMFGenExtensionModelWizard_pageDescription;

	public static String GMFGenExtensionModelWizard_pageTitle;

	public static String GMFGenExtensionModelWizard_unnamedClassName;

	public static String GMFGenExtensionModelWizard_windowTitle;

	public static String migration_confirmModelOverwriteMessage;

	public static String migration_confirmModelOverwriteTitle;

	public static String migration_modelDestinationFileTitle;

	public static String migration_specifyFileNameLabel;

	public static String migration_destinationModelSaveError;

	public static String migration_problemsDetectedTitle;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
