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

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExportCommandHandler;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExporter;
import org.eclipse.papyrus.infra.nattable.export.file.command.PapyrusFileExportCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.style.configattribute.PapyrusExportConfigAttributes;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.uml.nattable.generic.tests.Activator;
import org.eclipse.papyrus.uml.nattable.generic.tests.tests.AbstractGenericTableTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;

/**
 * This class allows to manage the paste with overwrite tests.
 */
public abstract class AbstractPasteInsertTest extends AbstractGenericTableTest {

	/**
	 * The suffix of the file containing the initial content.
	 */
	public static final String INITIAL_POST_FILE_NAME = "_Initial"; //$NON-NLS-1$

	/**
	 * The suffix of the file containing the content to copy.
	 */
	public static final String TOCOPY_POST_FILE_NAME = "_ToCopy"; //$NON-NLS-1$

	/**
	 * The suffix of the file containing the result content.
	 */
	public static final String RESULT_POST_FILE_NAME = "_Result"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public AbstractPasteInsertTest() {
		super();
	}

	/**
	 * Initialize the model.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Before
	public void initModel() throws Exception {
		initModel("GenericTable", getClass().getSimpleName(), getBundle()); //$NON-NLS-1$
	};

	/**
	 * This allows to set the selection in table for the paste. The initial selection is the first cell of the table.
	 * 
	 * @param manager
	 *            The tree nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	public abstract void manageSelection(final NattableModelManager manager) throws Exception;

	/**
	 * This allows to test the undo redo commands.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void testUndo_Redo(final NattableModelManager treeManager) throws Exception {
		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		// Check the table context after undo
		checkTableContent(treeManager, INITIAL_POST_FILE_NAME);

		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		// Check the table context after redo
		checkTableContent(treeManager, RESULT_POST_FILE_NAME);
	}

	/**
	 * This allows to check the returned status.
	 * 
	 * @param status
	 *            The status.
	 */
	protected void checkReturned_Status(final IStatus status) {
		Assert.assertTrue("The status must be OK", status.isOK()); //$NON-NLS-1$
	}

	/**
	 * This allows to check the table content comparing the table content with file content.
	 * 
	 * @param manager
	 *            The nattable model manager.
	 * @param suffixFileName
	 *            The suffix of the file to check.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkTableContent(final NattableModelManager manager, final String suffixFileName) throws Exception {
		final NatTable natTable = (NatTable) manager.getAdapter(NatTable.class);
		flushDisplayEvents();

		// Unregister and register the papyrus file export to manage it without the shell
		final GridLayer gridLayer = manager.getGridLayer();
		gridLayer.unregisterCommandHandler(PapyrusFileExportCommand.class);
		gridLayer.registerCommandHandler(new PapyrusFileExportCommandHandler(gridLayer.getBodyLayer(), false));

		// Modify the config attribute of the file export to use the file name without the shell
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final String wsFolder = workspace.getRoot().getLocation().toFile().getPath().toString();
		final String contentFile = wsFolder + "\\content.txt"; //$NON-NLS-1$
		natTable.getConfigRegistry().unregisterConfigAttribute(PapyrusExportConfigAttributes.SIMPLE_FILE_EXPORTER);
		natTable.getConfigRegistry().registerConfigAttribute(PapyrusExportConfigAttributes.SIMPLE_FILE_EXPORTER, new PapyrusFileExporter(contentFile));
		manager.exportToFile();

		final StringBuilder content = new StringBuilder();
		final List<String> allLines = Files.readAllLines(Paths.get(contentFile));
		for (int index = 0; index < allLines.size(); index++) {
			content.append(allLines.get(index));
			if (index < allLines.size() - 1) {
				content.append(FileUtils.getSystemPropertyLineSeparator());
			}
		}

		final String str = getWantedString(getSuffixStateFileName(manager, suffixFileName));
		// we check than the contents of the clipboard (so the displayed table) is the same than the wanted result
		Assert.assertEquals("The clipboard must be equals to string which one it is filled", str, content.toString()); //$NON-NLS-1$
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
	 * @param manager
	 *            The nattable model manager.
	 * @param suffixFileName
	 *            The suffix of the file to get.
	 * @return The file name corresponding
	 */
	protected String getSuffixStateFileName(final NattableModelManager manager, final String suffixFileName) {
		URI uri = manager.getTable().eResource().getURI();
		uri = uri.trimFileExtension();
		final StringBuffer buffer = new StringBuffer(uri.lastSegment());
		buffer.append(suffixFileName);
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.TEXT_EXTENSION);
		return buffer.toString();
	}

	/**
	 * This allows to fill the clipboard with the string in parameter.
	 * 
	 * @param newClipBoardContents
	 *            The string needed to fill the clipboard.
	 */
	protected void fillClipboard(final String newClipBoardContents) {

		// its seems that the clipboard must be filled with the same way than we read it!
		java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		StringSelection s = new StringSelection(newClipBoardContents);
		clipboard.setContents(s, s);
	}

	/**
	 * This allow to close the opened editors.
	 */
	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}
}
