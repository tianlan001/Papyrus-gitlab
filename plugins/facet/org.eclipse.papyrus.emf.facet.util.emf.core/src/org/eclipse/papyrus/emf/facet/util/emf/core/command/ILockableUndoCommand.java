/**
 *  Copyright (c) 2011 CEA LIST.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *      Gregoire Dupe (Mia-Software) - Bug 345730 - Deleting an element in the model breaks the table
 */
package org.eclipse.papyrus.emf.facet.util.emf.core.command;

/**
 * This interface is used to force an EMF command to not be "undo-able"
 *
 * @since 0.1.1
 */
public interface ILockableUndoCommand {

	/**
	 * @param enableUndo
	 *            false to lock the command in an not "undo-able" mode
	 */
	public void enableCanUndo(boolean enableUndo);
}
