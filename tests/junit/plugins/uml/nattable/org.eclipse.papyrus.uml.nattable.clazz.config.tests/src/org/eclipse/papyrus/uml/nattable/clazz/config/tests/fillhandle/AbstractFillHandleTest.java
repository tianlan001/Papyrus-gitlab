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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataToClipboardCommand;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExportCommandHandler;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExporter;
import org.eclipse.papyrus.infra.nattable.export.file.command.PapyrusFileExportCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.style.configattribute.PapyrusExportConfigAttributes;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractTableTest;
import org.eclipse.ui.IEditorPart;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

/**
 * This allows to manage the fill handle (copy or increment/decrement) tests.
 */
public abstract class AbstractFillHandleTest extends AbstractTableTest {

	/**
	 * The path of the model to test.
	 */
	public static final String PASTE_FOLDER_PATH = "/resources/fillhandle/"; //$NON-NLS-1$

	/**
	 * The table name.
	 */
	protected static final String TABLE_NAME = "ClassTreeTable"; //$NON-NLS-1$

	/**
	 * The suffix of the file containing the initial.
	 */
	public static final String INITIAL_POST_FILE_NAME = "_Initial"; //$NON-NLS-1$

	/**
	 * The suffix of the file containing the result content after a copy.
	 */
	public static final String RESULT_COPY_POST_FILE_NAME = "_ResultCopy"; //$NON-NLS-1$

	/**
	 * The suffix of the file containing the result content after an increment in UP direction.
	 */
	public static final String RESULT_INCREMENT_UP_POST_FILE_NAME = "_ResultIncrementUp"; //$NON-NLS-1$

	/**
	 * The suffix of the file containing the result content after an increment in DOWN direction.
	 */
	public static final String RESULT_INCREMENT_DOWN_POST_FILE_NAME = "_ResultIncrementDown"; //$NON-NLS-1$

	/**
	 * The suffix of the file containing the result content after a decrement in UP direction.
	 */
	public static final String RESULT_DECREMENT_UP_POST_FILE_NAME = "_ResultDecrementUp"; //$NON-NLS-1$

	/**
	 * The suffix of the file containing the result content after a decrement in DOW direction.
	 */
	public static final String RESULT_DECREMENT_DOWN_POST_FILE_NAME = "_ResultDecrementDown"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public AbstractFillHandleTest() {
		super();
	}

	/**
	 * This allows to test the copy.
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	@ActiveTable("ClassTreeTable") //$NON-NLS-1$
	public void testCopy() throws Exception {
		final IEditorPart tableEditor = fixture.getActiveTableEditor();
		Assert.assertTrue("Table editor must be a nattable editor", tableEditor instanceof NatTableEditor); //$NON-NLS-1$
		final INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The manager must be a tree nattable model manager", manager instanceof ITreeNattableModelManager); //$NON-NLS-1$

		final TreeNattableModelManager treeManager = (TreeNattableModelManager) manager;
		final NatTable natTable = (NatTable) treeManager.getAdapter(NatTable.class);

		executeCommand(treeManager, getCopyCommand(natTable), RESULT_COPY_POST_FILE_NAME, false);
	}

	/**
	 * This allows to test the increment series
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	@ActiveTable("ClassTreeTable") //$NON-NLS-1$
	public void testIncrementUp() throws Exception {
		final IEditorPart tableEditor = fixture.getActiveTableEditor();
		Assert.assertTrue("Table editor must be a nattable editor", tableEditor instanceof NatTableEditor); //$NON-NLS-1$
		final INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The manager must be a tree nattable model manager", manager instanceof ITreeNattableModelManager); //$NON-NLS-1$

		final TreeNattableModelManager treeManager = (TreeNattableModelManager) manager;
		final NatTable natTable = (NatTable) treeManager.getAdapter(NatTable.class);

		executeCommand(treeManager, getIncrementUpCommand(natTable), RESULT_INCREMENT_UP_POST_FILE_NAME, true);
	}

	/**
	 * This allows to test the increment series
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	@ActiveTable("ClassTreeTable") //$NON-NLS-1$
	public void testIncrementDown() throws Exception {
		final IEditorPart tableEditor = fixture.getActiveTableEditor();
		Assert.assertTrue("Table editor must be a nattable editor", tableEditor instanceof NatTableEditor); //$NON-NLS-1$
		final INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The manager must be a tree nattable model manager", manager instanceof ITreeNattableModelManager); //$NON-NLS-1$

		final TreeNattableModelManager treeManager = (TreeNattableModelManager) manager;
		final NatTable natTable = (NatTable) treeManager.getAdapter(NatTable.class);

		executeCommand(treeManager, getIncrementDownCommand(natTable), RESULT_INCREMENT_DOWN_POST_FILE_NAME, false);
	}

	/**
	 * This allows to test the decrement series
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	@ActiveTable("ClassTreeTable") //$NON-NLS-1$
	public void testDecrementUp() throws Exception {
		final IEditorPart tableEditor = fixture.getActiveTableEditor();
		Assert.assertTrue("Table editor must be a nattable editor", tableEditor instanceof NatTableEditor); //$NON-NLS-1$
		final INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The manager must be a tree nattable model manager", manager instanceof ITreeNattableModelManager); //$NON-NLS-1$

		final TreeNattableModelManager treeManager = (TreeNattableModelManager) manager;
		final NatTable natTable = (NatTable) treeManager.getAdapter(NatTable.class);

		executeCommand(treeManager, getDecrementUpCommand(natTable), RESULT_DECREMENT_UP_POST_FILE_NAME, true);
	}

	/**
	 * This allows to test the decrement series
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	@ActiveTable("ClassTreeTable") //$NON-NLS-1$
	public void testDecrementDown() throws Exception {
		final IEditorPart tableEditor = fixture.getActiveTableEditor();
		Assert.assertTrue("Table editor must be a nattable editor", tableEditor instanceof NatTableEditor); //$NON-NLS-1$
		final INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The manager must be a tree nattable model manager", manager instanceof ITreeNattableModelManager); //$NON-NLS-1$

		final TreeNattableModelManager treeManager = (TreeNattableModelManager) manager;
		final NatTable natTable = (NatTable) treeManager.getAdapter(NatTable.class);

		executeCommand(treeManager, getDecrementDownCommand(natTable), RESULT_DECREMENT_DOWN_POST_FILE_NAME, false);
	}

	/**
	 * This allows to execute a command and check its result.
	 * 
	 * @param command
	 *            The command to execute.
	 * @param resultFileName
	 *            The post file name of the result.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void executeCommand(final TreeNattableModelManager treeManager, final ILayerCommand command, final String resultPostFileName, final boolean isUpDirection) throws Exception {

		// Check the table context before command
		checkTableContent(treeManager, INITIAL_POST_FILE_NAME);

		if (isUpDirection) {
			selectCellToFillToUp(treeManager);
		} else {
			selectCellToFillToDown(treeManager);
		}

		final NatTable natTable = (NatTable) treeManager.getAdapter(NatTable.class);
		natTable.doCommand(new CopyDataToClipboardCommand(
				"\t", //$NON-NLS-1$
				System.getProperty("line.separator"), //$NON-NLS-1$
				natTable.getConfigRegistry()));

		if (isUpDirection) {
			setFillHandlerRegionToUp(treeManager);
		} else {
			setFillHandlerRegionToDown(treeManager);
		}

		// Execute the copy command
		natTable.doCommand(command);

		// Check the table context before command
		checkTableContent(treeManager, resultPostFileName);
		
		// Check the undo/redo
		checkUndoRedo(treeManager, resultPostFileName);
	}

	/**
	 * This allows to check the undo redo result.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 * @param resultPostFileName
	 *            The post file name of the result.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkUndoRedo(final TreeNattableModelManager treeManager, final String resultPostFileName) throws Exception {

		TransactionalEditingDomain editingDomain = fixture.getEditingDomain();

		// Check the undo
		editingDomain.getCommandStack().undo();
		// Check the table context after undo
		checkTableContent(treeManager, INITIAL_POST_FILE_NAME);

		// Check the redo
		editingDomain.getCommandStack().redo();
		// Check the table context after redo
		checkTableContent(treeManager, resultPostFileName);
	}

	/**
	 * Get the selected cell to fill when the action is managed to UP direction.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 */
	protected abstract void selectCellToFillToUp(final TreeNattableModelManager treeManager);

	/**
	 * Get the selected cell to fill when the action is managed to DOWN direction.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 */
	protected abstract void selectCellToFillToDown(final TreeNattableModelManager treeManager);

	/**
	 * Set the fill handler region when the action is managed to UP direction.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 */
	protected abstract void setFillHandlerRegionToUp(final TreeNattableModelManager treeManager);

	/**
	 * Set the fill handler region when the action is managed to DOWN direction.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 */
	protected abstract void setFillHandlerRegionToDown(final TreeNattableModelManager treeManager);

	/**
	 * Get the copy command.
	 * 
	 * @param natTable
	 *            The nattable.
	 * @return The copy command.
	 */
	protected abstract ILayerCommand getCopyCommand(final NatTable natTable);

	/**
	 * Get the increment command with UP direction.
	 * 
	 * @param natTable
	 *            The nattable.
	 * @return The increment command with UP direction.
	 */
	protected abstract ILayerCommand getIncrementUpCommand(final NatTable natTable);

	/**
	 * Get the increment command with DOWN direction.
	 * 
	 * @param natTable
	 *            The nattable.
	 * @return The increment command with DOWN direction.
	 */
	protected abstract ILayerCommand getIncrementDownCommand(final NatTable natTable);

	/**
	 * Get the decrement command with UP direction.
	 * 
	 * @param natTable
	 *            The nattable.
	 * @return The decrement command with UP direction.
	 */
	protected abstract ILayerCommand getDecrementUpCommand(final NatTable natTable);

	/**
	 * Get the decrement command with DOWN direction.
	 * 
	 * @param natTable
	 *            The nattable.
	 * @return The decrement command with DOWN direction.
	 */
	protected abstract ILayerCommand getDecrementDownCommand(final NatTable natTable);

	/**
	 * This allows to check the table content comparing the table content with file content.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 * @param suffixFileName
	 *            The suffix of the file to check.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkTableContent(final TreeNattableModelManager treeManager, final String suffixFileName) throws Exception {
		fixture.flushDisplayEvents();
		treeManager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		final NatTable natTable = (NatTable) treeManager.getAdapter(NatTable.class);

		// Unregister and register the papyrus file export to manage it without the shell
		final GridLayer gridLayer = treeManager.getGridLayer();
		gridLayer.unregisterCommandHandler(PapyrusFileExportCommand.class);
		gridLayer.registerCommandHandler(new PapyrusFileExportCommandHandler(gridLayer.getBodyLayer(), false));

		// Modify the config attribute of the file export to use the file name without the shell
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final String wsFolder = workspace.getRoot().getLocation().toFile().getPath().toString();
		final String contentFile = wsFolder + "\\content.txt"; //$NON-NLS-1$
		natTable.getConfigRegistry().unregisterConfigAttribute(PapyrusExportConfigAttributes.SIMPLE_FILE_EXPORTER);
		natTable.getConfigRegistry().registerConfigAttribute(PapyrusExportConfigAttributes.SIMPLE_FILE_EXPORTER, new PapyrusFileExporter(contentFile));
		treeManager.exportToFile();

		final StringBuilder content = new StringBuilder();
		final List<String> allLines = Files.readAllLines(Paths.get(contentFile));
		for (int index = 0; index < allLines.size(); index++) {
			content.append(allLines.get(index));
			if (index < allLines.size() - 1) {
				content.append(FileUtils.getSystemPropertyLineSeparator());
			}
		}

		final String str = getWantedString(getSuffixStateFileName(treeManager, suffixFileName));
		// we check than the contents of the clipboard (so the displayed table) is the same than the wanted result
		Assert.assertEquals("The clipboard must be equals to string which one it is filled", str, content.toString()); //$NON-NLS-1$
	}

	/**
	 * Get the content file.
	 * 
	 * @param fileName
	 *            The file name.
	 * @return The content of the file.
	 */
	protected String getStringFromFile(final String fileName) {
		final StringBuilder builder = new StringBuilder();
		final URL url;
		try {
			url = new URL("file:/" + fileName); //$NON-NLS-1$
			InputStream inputStream = url.openConnection().getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String inputLine = in.readLine();

			while (inputLine != null) {
				builder.append(inputLine);
				inputLine = in.readLine();
				if (inputLine != null) {
					builder.append(FileUtils.getSystemPropertyLineSeparator());
				}
			}

			in.close();

		} catch (final IOException e) {
			Activator.log.error(e);
		}

		return builder.toString();
	}

	/**
	 * Get the string content from a file.
	 * 
	 * @param fileName
	 *            a file name
	 * @return
	 * 		the text stored in the file associated to this test
	 */
	protected String getWantedString(final String fileName) {
		return FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID, getSourcePath(), fileName, FileUtils.getSystemPropertyLineSeparator());// $NON-NLS-1$
	}

	/**
	 * Get the file name corresponding to the model with the suffix in parameter.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 * @param suffixFileName
	 *            The suffix of the file to get.
	 * @return The file name corresponding
	 */
	protected String getSuffixStateFileName(final TreeNattableModelManager treeManager, final String suffixFileName) {
		URI uri = URI.createURI(getClass().getSimpleName());
		uri = uri.trimFileExtension();
		final StringBuffer buffer = new StringBuffer(uri.lastSegment());
		buffer.append(suffixFileName);
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.TEXT_EXTENSION);
		return buffer.toString();
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

	/**
	 * This allows to close the editors.
	 */
	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}

}
