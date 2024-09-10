/*******************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.SwitchLifelineCommand;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class SwitchLifelineAction implements IObjectActionDelegate {

	public static final String ID = "org.eclipse.papyrus.uml.diagram.timing.custom.SwitchLifelineActionID"; //$NON-NLS-1$

	@Override
	public void run(final IAction action) {
		final SwitchLifelineCommand handler = new SwitchLifelineCommand();
		try {
			handler.execute(null);
		} catch (final ExecutionException e) {
			Activator.log.error("Error switching lifeline", e); //$NON-NLS-1$
		}

	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		// nothing

	}

	@Override
	public void setActivePart(final IAction action, final IWorkbenchPart targetPart) {
		// nothing
	}

}
