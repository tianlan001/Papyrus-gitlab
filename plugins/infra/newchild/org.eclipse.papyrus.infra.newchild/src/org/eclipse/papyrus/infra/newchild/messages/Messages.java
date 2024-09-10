/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.newchild.messages;

import org.eclipse.osgi.util.NLS;

/**
 * The Messages class.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.newchild.messages.messages"; //$NON-NLS-1$
	public static String CreationMenuRegistry_Error_ModelCanBeLoaded;
	public static String CreationMenuRegistry_Error_UnableToCreateExtension;
	public static String CreationMenuRegistry_Error_UnableToLoadCreationMenu;
	public static String CreationMenuRegistry_Error_UnableToUnloadCreationMenu;
	public static String DeployCreationMenuModelHandler_Deploy;
	public static String DeployCreationMenuModelHandler_DeploymentSuccess;
	public static String DeployCreationMenuModelHandler_DeployMenuJobTitle;
	public static String DeployCreationMenuModelHandler_DoployingMonitorTasksLabel;
	public static String DeployCreationMenuModelHandler_ElementNotAFile;
	public static String DeployCreationMenuModelHandler_ErrorDeployFile;
	public static String DeployCreationMenuModelHandler_SomeErorsOccured;
	public static String DeployCreationMenuModelHandler_Status_deployed;
	public static String DeployCreationMenuModelHandler_SuccesLabel;
	public static String UndeployCreationMenuModelHandler_Erro_SomeErrorsOccurred;
	public static String UndeployCreationMenuModelHandler_Error_NotAFile;
	public static String UndeployCreationMenuModelHandler_Error_NotAValidFile;
	public static String UndeployCreationMenuModelHandler_Error_UndeployFile;
	public static String UndeployCreationMenuModelHandler_SubTaskTitle_ResetRegistry;
	public static String UndeployCreationMenuModelHandler_Success_UndeployMenu;
	public static String UndeployCreationMenuModelHandler_SuccessfullyUndeployed;
	public static String UndeployCreationMenuModelHandler_SuccessLabel;
	public static String UndeployCreationMenuModelHandler_Undeploy;
	public static String UndeployCreationMenuModelHandler_UndeployMenuJobTitle;
	public static String UndeployCreationMenuModelHandler_UndeploySectionTasksTitle;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
