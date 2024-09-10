/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.controlmode.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Translatable strings presented in the plug-in's UI.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.decoratormodel.internal.controlmode.messages.messages"; //$NON-NLS-1$

	public static String AbstractDecoratorModelRefactoringCommand_0;
	public static String ConfirmSaveCommand_0;
	public static String ConfirmSaveCommand_1;
	public static String ConfirmSaveCommand_2;
	public static String CrossReferenceUpdater_0;
	public static String DecoratorModelRefactoringCommandFactory_0;
	public static String PostControlDecoratorModelUpdater_0;
	public static String PostControlDecoratorModelUpdater_1;
	public static String PostUncontrolDecoratorModelUpdater_0;
	public static String PostUncontrolDecoratorModelUpdater_1;
	public static String SaveModelCommand_0;
	public static String SaveModelCommand_1;
	public static String SaveModelCommand_2;
	public static String UnloadedDecoratorModelRefactoringCommand_0;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
