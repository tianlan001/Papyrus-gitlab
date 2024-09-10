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

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.sort.command.SortColumnCommand;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExportCommandHandler;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExporter;
import org.eclipse.papyrus.infra.nattable.export.file.command.PapyrusFileExportCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.menu.MenuUtils;
import org.eclipse.papyrus.infra.nattable.style.configattribute.PapyrusExportConfigAttributes;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.nattable.generic.tests.Activator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to test the sort column when axis does not have common ancestor.
 */
@PluginResource("resources/bugs/bug501332/TableColumnSort.di") //$NON-NLS-1$
public class SortTableColumnTest extends AbstractPapyrusTest {

	/**
	 * The source path.
	 */
	protected static final String SOURCE_PATH = "resources/bugs/bug501332/"; //$NON-NLS-1$

	/**
	 * The table name.
	 */
	protected static final String TABLE_NAME = "Generic Tree Table0"; //$NON-NLS-1$

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();


	/**
	 * The nattable model manager.
	 */
	protected TreeNattableModelManager manager = null;

	/**
	 * the nattable widget
	 */
	protected NatTable natTable;

	/**
	 * Constructor.
	 */
	public SortTableColumnTest() {
		super();
	}

	/**
	 * Initialize.
	 */
	@Before
	public void init() {
		// these tests works only when the sorted columns are visible (without scrollbar)!
		if (null != Display.getDefault()) {
			Shell shell = Display.getDefault().getActiveShell();
			if (null != shell) {
				shell.setMaximized(true);
			}
		}
	}

	/**
	 * This method initialize some field for the test + expand all the table + check the initial state of the table
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	protected void startTest() throws Exception {
		this.manager = (TreeNattableModelManager) fixture.getActiveTableManager();
		this.natTable = (NatTable) this.manager.getAdapter(NatTable.class);
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
		MenuUtils.registerNatTableWidgetInEclipseContext(manager, new LabelStack(GridRegion.BODY));
	}

	/**
	 * This allows to test the sort column when no common parent is available.
	 * 
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	@ActiveTable("Generic Tree Table0") //$NON-NLS-1$
	public void testSortColumn() throws Exception {
		startTest();

		// Check the initial content
		checkTableContent(manager, "_Initial"); //$NON-NLS-1$

		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, 2, false)));
		fixture.flushDisplayEvents();
		// Check the ASC sort result
		checkTableContent(manager, "_Result_ASC"); //$NON-NLS-1$

		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, 2, true)));
		fixture.flushDisplayEvents();
		// Check the DESC sort result
		checkTableContent(manager, "_Result_DESC"); //$NON-NLS-1$

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
		fixture.flushDisplayEvents();

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
	 * Return the source path.
	 * 
	 * @return
	 * 		the source path to use to load files
	 * 
	 *         must be overridden by sub classes
	 */
	protected String getSourcePath() {
		return SOURCE_PATH;
	}
}
