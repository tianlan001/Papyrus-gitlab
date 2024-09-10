/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.component.custom.edit.command;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.View;


/**
 * The Class CreateViewCommand.
 *
 * @since 3.0
 */
public class CreateViewCommand extends CreateCommand {

	/**
	 * Instantiates a new creates the view command.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param viewDescriptor
	 *            the view descriptor
	 * @param containerView
	 *            the container view
	 */
	public CreateViewCommand(TransactionalEditingDomain editingDomain, ViewDescriptor viewDescriptor, View containerView) {
		super(editingDomain, viewDescriptor, containerView);
	}

	/**
	 * Can execute.
	 *
	 * @return true, if successful
	 * @see org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand#canExecute()
	 */

	@Override
	public boolean canExecute() {
		return true;
	}


}
