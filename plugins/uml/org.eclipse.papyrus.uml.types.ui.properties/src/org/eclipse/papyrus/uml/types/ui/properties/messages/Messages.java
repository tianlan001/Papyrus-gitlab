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

package org.eclipse.papyrus.uml.types.ui.properties.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Mickael ADAM
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.types.ui.properties.messages.messages"; //$NON-NLS-1$
	public static String FeatureToSetLabelProvider_undefined;
	public static String StereotypeAttributesNameValueEditor_ButtonTooltip;
	public static String StereotypeAttributesNameValueEditor_DialogMessage;
	public static String StereotypeAttributesNameValueEditor_DialogTitile;
	public static String StereotypesToApplyEditor_AddElements;
	public static String StereotypesToApplyEditor_AddFeatureToSetTitle;
	public static String StereotypesToApplyEditor_AddFeatureToSetTooltip;
	public static String StereotypesToApplyEditor_AddStereotypeToApplyTitle;
	public static String StereotypesToApplyEditor_AddStereotypeToApplyTooltip;
	public static String StereotypesToApplyEditor_MoveSelectedElementsDown;
	public static String StereotypesToApplyEditor_MoveSelectedElementsUp;
	public static String StereotypesToApplyEditor_RemoveSelectedElements;
	public static String undefined;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
