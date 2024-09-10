/*****************************************************************************
 * Copyright (c) 2011 Atos Origin.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Arthur Daussy - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.resources.refactoring;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.ui.resources.refactoring.messages"; //$NON-NLS-1$

	public static String DirtyEditorChange_0;

	public static String DirtyEditorChange_1;

	public static String DirtyEditorChange_2;

	public static String DirtyEditorChange_3;

	public static String MoveModelParticipant_Name;

	public static String RenameModelChange_0;

	public static String RenameModelChange_5;

	public static String RenameModelChange_6;

	public static String RenameModelChange_7;

	public static String RenameModelChange_8;

	public static String RenameModelChange_Change;

	public static String RenameModelChange_DaveDirtyEditor;

	public static String RenameModelChange_ErrorLoading;

	public static String RenameModelChange_LoadingEMF;

	public static String RenameModelChange_ModifyURI;

	public static String RenameModelChange_Name;

	public static String RenameModelChange_RemoveOldFile;

	public static String RenameModelChange_savingResource;

	public static String RenameModelChange_Unloading;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
