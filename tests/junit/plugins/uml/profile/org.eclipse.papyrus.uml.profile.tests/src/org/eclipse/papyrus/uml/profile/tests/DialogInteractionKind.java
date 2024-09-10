/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.profile.tests;

/**
 * An enumeration of a test's possible modes of interaction
 * with the Papyrus editor's profile migration dialog.
 */
public enum DialogInteractionKind {
	/** No interaction: the dialog is not expected to appear. */
	NONE, //
	/** Cancel the dialog without migrating profiles. */
	CANCEL, //
	/** Press OK to apply the default profile migrations. */
	OK;
}
