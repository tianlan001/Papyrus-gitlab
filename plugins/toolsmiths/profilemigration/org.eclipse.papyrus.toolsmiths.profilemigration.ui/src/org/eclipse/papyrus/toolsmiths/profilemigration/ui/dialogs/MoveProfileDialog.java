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
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Profile;

/**
 * Dialog to ask to user if he want to apply a profile or not
 */
public class MoveProfileDialog extends AbstractApplyNewProfileDialog {

	private static String DIALOG_TITLE = Messages.MoveProfileDialog_title;

	private Profile movedProfile;

	/**
	 * Constructor.
	 *
	 * @param shell
	 *            the active shell
	 * @param movedProfile
	 *            the moved profile
	 * @param newProfile
	 *            the new profile to apply
	 */
	public MoveProfileDialog(Shell shell, Profile movedProfile, Profile newProfile) {
		super(shell, DIALOG_TITLE, ProfileMigrationPreferenceConstants.PROFILE_MOVE, newProfile);
		this.movedProfile = movedProfile;
	}

	@Override
	protected String getDecription() {
		return NLS.bind(Messages.MoveProfileDialog_description, movedProfile.getName(), newProfile.getName());
	}

}
