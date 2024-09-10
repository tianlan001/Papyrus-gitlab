/*******************************************************************************
 * Copyright (c) 2006, 2018 CEA List.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA List - initial API and implementation
 *     Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 533667
 *
 *******************************************************************************/
package org.eclipse.papyrus.extensionpoints.editors.ui;

import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog used in for direct edition, when an extension is provided
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.ExtendedDirectEditionDialog} instead.
 */
@Deprecated
public class ExtendedDirectEditionDialog extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.ExtendedDirectEditionDialog {

	/**
	 * Creates a new ExtendedDirectEditionDialog
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param parameter
	 *            the editedObject
	 * @param initialValue
	 *            the initial text value
	 */
	public ExtendedDirectEditionDialog(Shell parentShell, Object object, String initialValue,
			IDirectEditorConfiguration configuration) {
		super(parentShell, object, initialValue, configuration);
	}
}
