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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.types.ui.properties.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Mickael ADAM
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.emf.types.ui.properties.messages.messages"; //$NON-NLS-1$
	public static String AbstractCustomMultipleEditor_CreateButtonTooltip;
	public static String AbstractCustomMultipleEditor_CreateTooltip;
	public static String AbstractCustomMultipleEditor_DeleteTooltip;
	public static String AbstractCustomMultipleEditor_moveDownButtonTooltip;
	public static String AbstractCustomMultipleEditor_moveUpButtonTooltip;
	public static String AbstractCustomMultipleEditor_MoveDownTooltip;
	public static String AbstractCustomMultipleEditor_MoveUpTooltip;
	public static String AbstractCustomMultipleEditor_RemoveButtonTooltip;
	public static String FeatureNameValueEditor_browseButtonTooltip;
	public static String FeatureNameValueEditor_dialogMessage;
	public static String FeatureNameValueEditor_dialogTitle;
	public static String undefinedValue;
	public static String RuntimeValuesModelElement_browserDescription;
	public static String RuntimeValuesModelElement_browserTitle;
	public static String ViewToDisplayValueEditor_browserButtonTooltip;
	public static String ViewToDisplayValueEditor_dialogMessage;
	public static String ViewToDisplayValueEditor_dialogTitle;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
