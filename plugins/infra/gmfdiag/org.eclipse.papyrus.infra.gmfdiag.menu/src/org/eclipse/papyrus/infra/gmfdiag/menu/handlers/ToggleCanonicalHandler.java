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

package org.eclipse.papyrus.infra.gmfdiag.menu.handlers;

import static org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil.isCanonical;

import java.util.Collection;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SetCanonicalCommand;

/**
 * Handler for the toggle-canonical command.
 */
public class ToggleCanonicalHandler extends AbstractGraphicalCommandHandler {

	public ToggleCanonicalHandler() {
		super();
	}

	@Override
	protected Command getCommand() {
		Command result;
		final Collection<? extends IGraphicalEditPart> selection = getSelectedElements();

		if (selection.isEmpty()) {
			result = UnexecutableCommand.INSTANCE;
		} else {
			ICommand command = new CompositeTransactionalCommand(getEditingDomain(), "Toggle Synchronize with Model");

			for (final IGraphicalEditPart editPart : getSelectedElements()) {
				if (editPart.getNotationView() != null) {
					command = command.compose(new SetCanonicalCommand(getEditingDomain(), editPart.getNotationView(), !isCanonical(editPart)));
				}
			}

			result = new ICommandProxy(command.reduce());
		}

		return result;
	}
}
