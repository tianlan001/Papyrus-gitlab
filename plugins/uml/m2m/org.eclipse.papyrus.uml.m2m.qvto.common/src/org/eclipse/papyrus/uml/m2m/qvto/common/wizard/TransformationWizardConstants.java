/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.wizard;

import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;

/**
 * Constants used in the wizard
 */
public interface TransformationWizardConstants {
	
	/**
	 * The name used for the settings file
	 */
	public static final String TRANSFORMATION_WIZARD_SETTINGS = Activator.PLUGIN_ID + "Wizard.lastUnselection"; //$NON-NLS-1$

	/**
	 * The key to retrieve the selection preferences from the settings file
	 */
	public static final String WIZARD_SELECTION_KEY = "selectionPeferences"; //$NON-NLS-1$

	public static final String FILEDIALOG_SELECTION_KEY = "previousDialogSelection"; //$NON-NLS-1$

	/**
	 * The name of the newly created project's folders
	 */
	public static final String SRC_FOLDER = "src"; //$NON-NLS-1$

	public static final String RSC_FOLDER = "resources"; //$NON-NLS-1$

}
