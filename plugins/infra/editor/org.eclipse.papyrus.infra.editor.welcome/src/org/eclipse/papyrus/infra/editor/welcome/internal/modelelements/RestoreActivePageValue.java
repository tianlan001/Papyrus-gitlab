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

package org.eclipse.papyrus.infra.editor.welcome.internal.modelelements;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.ui.internal.commands.SashLayoutCommandFactory;

/**
 * Encapsulation of the "restore active page" property of the Papyrus Editor.
 */
public class RestoreActivePageValue extends AbstractPageLayoutToggleValue {

	public RestoreActivePageValue(WelcomeModelElement owner) {
		super(owner);
	}

	public RestoreActivePageValue(Realm realm, WelcomeModelElement owner) {
		super(realm, owner);
	}

	@Override
	protected Boolean doGetValue() {
		boolean result = false;

		SashWindowsMngr sash = getSashWindowsMngr();
		SashModel model = (sash == null) ? null : sash.getSashModel();
		if (model != null) {
			result = model.isRestoreActivePage();
		}

		return result;
	}

	@Override
	protected Command getToggleCommand(SashLayoutCommandFactory factory) {
		return factory.createToggleRestoreActivePageCommand();
	}
}
