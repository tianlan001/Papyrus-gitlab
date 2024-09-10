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

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.widgets.Shell;

// equivalent au inputDialog (code recopie) mais donne un sourceviewer a la place du label
/**
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.LabelEditorDialog} instead.
 */
@Deprecated
public class LabelEditorDialog extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.LabelEditorDialog {

	/**
	 * Creates a dialog with OK and Cancel buttons. Note that the dialog will have no visual
	 * representation (no widgets) until it is told to open.
	 * <p>
	 * Note that the <code>open</code> method blocks for input dialogs.
	 * </p>
	 *
	 * @param dialogTitle
	 *            the dialog title, or <code>null</code> if none
	 * @param validator
	 *            an input validator, or <code>null</code> if none
	 * @param parentShell
	 *            the parent shell, or <code>null</code> to create a top-level shell
	 * @param initialValue
	 *            the initial input value, or <code>null</code> if none (equivalent to the empty
	 *            string)
	 */
	public LabelEditorDialog(Shell parentShell, String dialogTitle, String initialValue, IInputValidator validator) {
		super(parentShell, dialogTitle, initialValue, validator);
	}
}
