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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.handler.PasteInTableHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.PasteInsertUtil;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.uml.nattable.generic.tests.Activator;
import org.eclipse.ui.IEditorPart;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class allows to manage the paste with overwrite tests.
 */
public abstract class AbstractPasteOverwriteTest extends AbstractPasteInsertTest {

	/**
	 * The path of the model to test.
	 */
	public static final String PASTE_FOLDER_PATH = "/resources/paste_overwrite/"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public AbstractPasteOverwriteTest() {
		super();
	}

	/**
	 * This allows to test the paste.
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	public void testPaste() throws Exception {
		// Open the table and get the manager
		loadGenericTable();
		final IEditorPart editorPart = editor.getActiveEditor();
		Assert.assertTrue("Table editor must be a nattable editor", editorPart instanceof NatTableEditor); //$NON-NLS-1$
		final INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The manager must be a nattable model manager", currentManager instanceof NattableModelManager); //$NON-NLS-1$
		final NattableModelManager manager = (NattableModelManager) currentManager;

		// Check the table context before command
		checkTableContent(manager, INITIAL_POST_FILE_NAME);

		// Manage the selection
		manageSelection(manager);
		flushDisplayEvents();

		// fill the clipboard
		final String fileName = getSuffixStateFileName(manager, TOCOPY_POST_FILE_NAME);
		final String str = FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID, getSourcePath(), fileName);
		fillClipboard("Fill the clipboard to enable the handler"); //$NON-NLS-1$

		// Execute the command with the non-UI parameters
		final Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put(PasteInTableHandler.OPEN_DIALOG_ON_FAIL_BOOLEAN_PARAMETER, Boolean.FALSE);
		parameters.put(PasteInTableHandler.OPEN__PROGRESS_MONITOR_DIALOG, Boolean.FALSE);
		// This parameters allows to set the text to paste instead of copy/paste it programmatically (this may be overwrite by other copy)
		parameters.put(PasteInTableHandler.TEXT_TO_PASTE, str);
		manageParameters(parameters);
		flushDisplayEvents();
		final Object res = PasteInsertUtil.paste(manager, manager.getSelectionInTable(), parameters);
		Assert.assertTrue("The result must be a status", res instanceof IStatus); //$NON-NLS-1$
		final IStatus status = (IStatus) res;

		// Check the returned status
		checkReturned_Status(status);

		if (status.isOK()) {
			// Check the table content after command
			checkTableContent(manager, RESULT_POST_FILE_NAME);

			// Undo/Redo
			testUndo_Redo(manager);
		}
	}
	
	/**
	 * This allows to add parameters if necessary
	 * 
	 * @param parameters
	 *            The parameters for the command.
	 */
	public void manageParameters(final Map<Object, Object> parameters) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractOpenTableTest#getSourcePath()
	 */
	@Override
	protected String getSourcePath() {
		return PASTE_FOLDER_PATH;
	}
}
