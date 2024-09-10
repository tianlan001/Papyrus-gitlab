/*****************************************************************************
 * Copyright (c) 2008, 2018 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 533667
 *
 *****************************************************************************/
package org.eclipse.papyrus.extensionpoints.editors.ui;

/**
 * Extended annotation class for error annotation in embedded editors
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.ErrorAnnotation} instead.
 */
@Deprecated
public class ErrorAnnotation extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.ErrorAnnotation {

	/**
	 * Creates a new annotation with the given properties.
	 *
	 * @param isPersistent
	 *            <code>true</code> if this annotation is persistent, <code>false</code> otherwise
	 * @param text
	 *            the text associated with this annotation
	 */
	public ErrorAnnotation(boolean isPersistent, String text) {
		super(isPersistent, text);
	}

	/**
	 * Creates a new annotation with the given persistence state.
	 *
	 * @param isPersistent
	 *            <code>true</code> if persistent, <code>false</code> otherwise
	 */
	// @unused
	public ErrorAnnotation(boolean isPersistent) {
		super(isPersistent);
	}
}
