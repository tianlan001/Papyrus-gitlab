/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.unset;

import java.util.Collections;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractTableTest;
import org.junit.Assert;

/**
 * @author Vincent Lorenzo
 *         Abstract class used to test the unset cell value The unset feature reset the selected cell to its default.
 *         This feature is binded on org.eclipse.ui.edit.delete command
 */
public abstract class AbstractUnsetCellValueTest extends AbstractTableTest {


	/**
	 * 
	 * @param wantedStatus
	 *            the expected status for the handler
	 */
	protected void checkUnsetCellValueHandlerStatusAndExecuteCommandIfRequired(boolean wantedStatus) {
		Command cmd = EclipseCommandUtils.getCommandService().getCommand(EclipseCommandUtils.DELETE_COMMAND);
		Assert.assertNotNull(cmd);
		IHandler handler = cmd.getHandler();
		Assert.assertNotNull(handler);
		/*
		 * the setEnabled of UnsetCellValueHandler has been modified to }
		 */
		Assert.assertEquals(wantedStatus, handler.isEnabled());
		if (wantedStatus) {
			ExecutionEvent event = new ExecutionEvent(cmd, Collections.EMPTY_MAP, null, null);
			try {
				cmd.executeWithChecks(event);
			} catch (Exception e) {
				Assert.assertNull("An exception occured during the command execution", e); ////$NON-NLS-1$
			}

		}
	}
}
