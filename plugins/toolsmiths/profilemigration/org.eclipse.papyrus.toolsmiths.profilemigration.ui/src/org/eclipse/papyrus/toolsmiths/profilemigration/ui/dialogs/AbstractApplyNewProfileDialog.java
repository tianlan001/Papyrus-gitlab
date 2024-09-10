/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs;

import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.uml2.uml.Profile;

/**
 * Abstract dialog to ask the user if he want to reapply a profile
 */
public abstract class AbstractApplyNewProfileDialog extends AbstractProfileMigrationDialog {

	private Button radioYes;

	private Button radioNo;

	private boolean reapply = false;

	/**
	 * The new profile to apply
	 */
	protected Profile newProfile;

	/**
	 * Constructor.
	 *
	 * @param shell
	 *            the active shell
	 * @param title
	 *            the title of the dialog
	 * @param prefConst
	 *            the preference constant (show or not the dialog)
	 * @param newProfile
	 *            the new profile to apply
	 */
	public AbstractApplyNewProfileDialog(Shell shell, String title, String prefConst, Profile newProfile) {
		super(shell, title, prefConst);
		this.newProfile = newProfile;
	}

	/**
	 * @see org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.AbstractProfileMigrationDialog#getMigrationActionSectionContent(org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
	 *
	 * @param body
	 * @param toolkit
	 */
	@Override
	protected void getMigrationActionSectionContent(Composite body, FormToolkit toolkit) {
		toolkit.createLabel(body, NLS.bind(Messages.MoveStereotypeDialog_content, newProfile.getName()));
		radioYes = new Button(body, SWT.RADIO);
		radioYes.setText(Messages.MoveStereotypeDialog_Yes);
		radioYes.setSelection(true);
		radioNo = new Button(body, SWT.RADIO);
		radioNo.setText(Messages.MoveStereotypeDialog_No);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if (radioYes.getSelection()) {
			reapply = true;
		} else {
			reapply = false;
		}
		super.okPressed();
	}

	/**
	 * @return the reapply
	 */
	public boolean isReapply() {
		return reapply;
	}


}
