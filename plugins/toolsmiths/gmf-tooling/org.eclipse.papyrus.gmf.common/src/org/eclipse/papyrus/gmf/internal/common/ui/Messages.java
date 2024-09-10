/******************************************************************************
 * Copyright (c) 2006, 2020 Eclipse.org, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.ui;

import org.eclipse.osgi.util.NLS;

/**
 * @author dstadnik
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.gmf.internal.common.ui.messages"; //$NON-NLS-1$

	public static String CreateNewModelExtensionCreateNewModel;

	public static String ModelSelectionPageBadURI;

	public static String ModelSelectionPageBrowseFS;

	public static String ModelSelectionPageBrowseWS;

	public static String ModelSelectionPageErrorLoadingModel;

	public static String ModelSelectionPageFindInWS;

	public static String ModelSelectionPageLoad;

	public static String ModelSelectionPageModelNA;

	public static String ModelSelectionPageModelURI;

	public static String ModelSelectionPageSelectModel;

	public static String ModelSelectionPageSelectModelDesc;

	public static String PredefinedModelExtensionPredefinedModels;

	/**
	 * @since 2.0
	 */
	public static String ValidationHelper_e_marker_creation;

	/**
	 * @since 2.0
	 */
	public static String ValidationHelper_task_validate;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
