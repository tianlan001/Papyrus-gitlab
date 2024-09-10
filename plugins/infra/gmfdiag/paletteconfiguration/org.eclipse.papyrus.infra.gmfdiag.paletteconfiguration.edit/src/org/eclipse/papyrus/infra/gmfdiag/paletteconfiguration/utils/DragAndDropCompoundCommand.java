/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;

/**
 * Compound command which implement {@link DragAndDropFeedback} to have feedback during a drag and drop.This permits to append Commands to a drag and drop command.
 * The first drag and drop command will define methods from {@link DragAndDropFeedback}.
 */
public class DragAndDropCompoundCommand extends CompoundCommand implements DragAndDropFeedback {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validate(final Object owner, final float location, final int operations, final int operation, final Collection<?> collection) {
		boolean validate = false;
		DragAndDropCommand dragAndDropCommand = getDragAndDropCommand();
		if (null != dragAndDropCommand) {
			validate = dragAndDropCommand.validate(owner, location, operations, operation, collection);
		}
		return validate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getFeedback() {
		int feedback = 0;
		DragAndDropCommand dragAndDropCommand = getDragAndDropCommand();
		if (null != dragAndDropCommand) {
			feedback = dragAndDropCommand.getFeedback();
		}
		return feedback;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOperation() {
		int operation = 0;
		DragAndDropCommand dragAndDropCommand = getDragAndDropCommand();
		if (null != dragAndDropCommand) {
			operation = dragAndDropCommand.getOperation();
		}
		return operation;
	}

	private DragAndDropCommand getDragAndDropCommand() {
		for (Command command : commandList) {
			if (command instanceof DragAndDropCommand) {
				return (DragAndDropCommand) command;
			}
		}
		return null;
	}

}
