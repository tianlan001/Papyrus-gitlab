/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.validation.preferences;

/**
 * The preference constants for validation
 */
public class PreferenceConstants {

	/**
	 * Should the validation operation automatically open the validation view, once done (and errors/warnings are present)?
	 */
	public static final String AUTO_SHOW_VALIDATION_VIEW = "org.eclipse.papyrus.infra.services.validation.AutoShowValidationView"; //$NON-NLS-1$
	/**
	 * different ways to show markers that belong to children
	 */
	public static final String HIERARCHICAL_MARKERS = "org.eclipse.papyrus.infra.services.validation.HierarchicalMarkers"; //$NON-NLS-1$

}
