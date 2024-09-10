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

package org.eclipse.papyrus.uml.nattable.generic.tests.importfile;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.handler.ImportTableHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite.AbstractPasteInsertTest;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.commands.ICommandService;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class allows to manage the import CSV file tests.
 */
public abstract class AbstractImportTest extends AbstractPasteInsertTest {

	/**
	 * The path of the model to test.
	 */
	public static final String INSERT_FOLDER_PATH = "/resources/import/"; //$NON-NLS-1$

	/**
	 * The variable name to determinate if the final dialog must be opened for the import.
	 */
	public static final String OPEN_DIALOG_BOOLEAN_PARAMETER = "openDialog"; //$NON-NLS-1$

	/**
	 * The variable name to determinate the selected file path.
	 */
	public static final String SELECTED_FILE_PATH_STRING_PARAMETER = "selectedFilePath"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public AbstractImportTest() {
		super();
	}

	/**
	 * This allows to test the paste.
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	public void testImport() throws Exception {
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
		flushDisplayEvents();
		manageSelection(manager);
		flushDisplayEvents();

		// fill the clipboard
		final ICommandService commandService = EclipseCommandUtils.getCommandService();
		Assert.assertNotNull("The command service must not be null", commandService); //$NON-NLS-1$

		// Get the paste command
		final Command cmd = commandService.getCommand(getCommandId()); // $NON-NLS-1$
		final IHandler handler = cmd.getHandler();
		Assert.assertTrue("The handler must be enabled", handler.isEnabled()); //$NON-NLS-1$

		// Execute the command with the non-UI parameters
		final Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put(ImportTableHandler.OPEN_DIALOG_BOOLEAN_PARAMETER, Boolean.FALSE);
		manageParameters(parameters, manager);
		final ExecutionEvent event = new ExecutionEvent(cmd, parameters, null, null);
		flushDisplayEvents();
		cmd.executeWithChecks(event);

		// Check the table content after command
		checkTableContent(manager, RESULT_POST_FILE_NAME);

		// Undo/Redo
		testUndo_Redo(manager);
	}

	/**
	 * This allows to add parameters if necessary
	 * 
	 * @param parameters
	 *            The parameters for the command.
	 */
	public void manageParameters(final Map<Object, Object> parameters, final INattableModelManager manager) {
		// Create the path for the csv to load (must be with the following form : 'resources/import/TableName.csv')
		URI uri = manager.getTable().eResource().getURI();
		uri = uri.trimFileExtension();
		final StringBuffer buffer = new StringBuffer(uri.lastSegment());
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.CSV_EXTENSIOn);

		String csvFileAsString = getWantedString(buffer.toString());

		parameters.put(ImportTableHandler.SELECTED_FILE_PATH_STRING_PARAMETER, csvFileAsString);
	}

	/**
	 * This allows to get the command id.
	 * 
	 * @return The command id.
	 */
	public abstract String getCommandId();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractOpenTableTest#getSourcePath()
	 */
	@Override
	protected String getSourcePath() {
		return INSERT_FOLDER_PATH;
	}
}
