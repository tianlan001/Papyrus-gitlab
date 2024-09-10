/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import org.eclipse.nebula.widgets.nattable.selection.command.SelectCellCommand;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.uml.nattable.generic.tests.tests.InvertAxisGenericTableTest;
import org.eclipse.ui.IEditorPart;
import org.junit.Assert;
import org.junit.Before;

/**
 * This class allows to test the invert axis in the Generic NatTable
 */
public class InvertAxisTwiceGenericTableTest extends InvertAxisGenericTableTest {

	/**
	 * Initialize the model.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Before
	public void initModel() throws Exception {
		super.initModel(); // $NON-NLS-1$

		// The properties view need to be opened for this JUnit test
		editor.getSite().getPage().showView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$

		flushDisplayEvents();

		// Select the table to display the correct properties view
		final IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		final INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		manager.getBodyLayerStack().getSelectionLayer().doCommand(new SelectCellCommand(manager.getBodyLayerStack().getSelectionLayer(), 0, 0, false, false));

		flushDisplayEvents();
	}
}
