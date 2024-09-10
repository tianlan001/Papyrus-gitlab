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

package org.eclipse.papyrus.infra.editor.welcome.internal.preferences;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.papyrus.infra.editor.welcome.internal.Activator;
import org.eclipse.papyrus.infra.properties.ui.preferences.Preferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The specialized preference page for the <em>Welcome Page</em> contents.
 */
public class WelcomeContentPreferencePage extends Preferences {

	public WelcomeContentPreferencePage() {
		super();
	}

	@Override
	protected void createHeaderContents(Composite parent) {
		Composite header = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).margins(0, 0).applyTo(header);

		new Label(header, SWT.NONE).setText("Default Welcome Page layout:");
		Button resetButton = new Button(header, SWT.PUSH);
		resetButton.setText("Reset");
		resetButton.setToolTipText("Revert the default Welcome Page layout to factory defaults");
		resetButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resetWelcomePageLayout();
			}
		});
	}

	private void resetWelcomePageLayout() {
		if (MessageDialog.openConfirm(getShell(), "Reset Default Welcome Page Layout", "Are you sure you want to reset the default Welcome Page layout to factory defaults?\nThis will affect all Welcome Pages that have not customized the layout.")) {
			try {
				Activator.getDefault().getWelcomeModelManager().deleteDefaultWelcomeResource();
			} catch (IOException e) {
				Activator.log.error("Failed to delete the workspace's default welcome layout.", e); //$NON-NLS-1$
			}
		}
	}
}
