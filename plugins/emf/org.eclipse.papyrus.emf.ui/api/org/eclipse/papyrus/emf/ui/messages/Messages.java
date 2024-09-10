/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.emf.ui.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.emf.ui.messages.messages"; //$NON-NLS-1$
	public static String AbstractEStructuralFeatureDialogEditorFactory_PapyrusDialogTitle;
	public static String AbstractEStructuralFeatureDialogEditorFactory_SelectOne;
	public static String AbstractEStructuralFeatureDialogEditorFactory_SelectOneOrSeveral;
	public static String AbstractSelectionStatusValidator_NoSelection;
	public static String SingleEAttributeValidator_YouMustSelectOneEAttribute;
	public static String SingleEClassValidator_YouMustSelectOneEClass;
	public static String SingleEReferenceValidator_YouMustSelectOneEReference;
	public static String SingleEStructuralFeatureValidator_YouMustSelectOneEStructuralFeature;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
