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

package org.eclipse.papyrus.infra.editor.welcome.internal.commands;

import org.eclipse.papyrus.infra.editor.welcome.IWelcomePageService;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;

/**
 * Handler for the <em>Reset Welcome Page</em> menu command.
 */
public class ResetWelcomePageHandler extends AbstractWelcomePageHandler {

	public ResetWelcomePageHandler() {
		super();
	}

	@Override
	protected void doExecute(IMultiDiagramEditor editor, IWelcomePageService welcomeService) {
		welcomeService.resetWelcomePage();
	}
}
