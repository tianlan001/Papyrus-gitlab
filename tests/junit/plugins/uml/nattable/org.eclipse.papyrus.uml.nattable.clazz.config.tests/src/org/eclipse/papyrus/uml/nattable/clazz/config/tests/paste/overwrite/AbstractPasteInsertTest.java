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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.overwrite;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.commands.OpenDiagramCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExportCommandHandler;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExporter;
import org.eclipse.papyrus.infra.nattable.export.file.command.PapyrusFileExportCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.style.configattribute.PapyrusExportConfigAttributes;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.papyrus.junit.utils.EditorUtils;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractOpenTableTest;
import org.eclipse.ui.IEditorPart;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * This class allows to manage the paste with overwrite tests.
 */
public abstract class AbstractPasteInsertTest extends AbstractOpenTableTest {

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
	 * The operation base name.
	 */
	public static final String OPERATION_BASE_NAME = "Operation"; //$NON-NLS-1$

	/**
	 * The parameter base name.
	 */
	public static final String PARAMETER_BASE_NAME = "Param"; //$NON-NLS-1$

	/**
	 * The property base name.
	 */
	public static final String PROPERTY_BASE_NAME = "Property"; //$NON-NLS-1$

	/**
	 * The class base name.
	 */
	public static final String CLASS_BASE_NAME = "Class"; //$NON-NLS-1$

	/**
	 * The nested class base name.
	 */
	public static final String NESTED_CLASS_BASE_NAME = "NestedClass"; //$NON-NLS-1$

	/**
	 * The class element identifier.
	 */
	public static final String CLASS_ELEMENT_ID = "org.eclipse.papyrus.uml.Class"; //$NON-NLS-1$

	/**
	 * The operation element identifier.
	 */
	public static final String OPERATION_ELEMENT_ID = "org.eclipse.papyrus.uml.Operation"; //$NON-NLS-1$

	/**
	 * The parameter element identifier.
	 */
	public static final String PARAMETER_ELEMENT_ID = "org.eclipse.papyrus.uml.Parameter"; //$NON-NLS-1$

	/**
	 * The property element identifier.
	 */
	public static final String PROPERTY_ELEMENT_ID = "org.eclipse.papyrus.uml.Property"; //$NON-NLS-1$


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
		initModel("classTreeTable", getClass().getSimpleName(), getBundle()); //$NON-NLS-1$
	};

	/**
	 * Check the model for the test consistency.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void checkModelForTestConsistency() throws Exception {
		String className = getClass().getSimpleName();
		className = removeClassName(className);
		String[] result = className.split("_"); //$NON-NLS-1$
		Assert.assertTrue("The class name is not correcly build", result.length == 5); //$NON-NLS-1$
		for (int depth = 0; depth < 3; depth++) {
			String current = result[depth];
			// filled by DnD
			if (current.equals("Empty")) { //$NON-NLS-1$
				// no configuration
				Assert.assertTrue("We must not have filling configuration for depth==0", FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(getTable(), 0).size() == 0); //$NON-NLS-1$
				// we can't hide a depth for which we don't have category

				Assert.assertTrue("The depth 0 can't be hidden", StyleUtils.isHiddenDepth(getTable(), 0) == false);//$NON-NLS-1$
			} else {
				Assert.assertEquals("The class name is not correcly build", 2, current.length()); //$NON-NLS-1$
				char visibility = current.charAt(0);
				char nbCategoriesForTheDepth = current.charAt(1);
				switch (visibility) {
				case 'H':
					Assert.assertTrue(NLS.bind("The depth {0} must be hidden", depth), true == StyleUtils.isHiddenDepth(getTable(), depth));//$NON-NLS-1$
					break;
				case 'V':
					Assert.assertTrue(NLS.bind("The depth {0} must be visible", depth), false == StyleUtils.isHiddenDepth(getTable(), depth));//$NON-NLS-1$
					break;
				default:
					throw new Exception("Not supported case"); //$NON-NLS-1$
				}
				// we check that we have the wanted number of filling categories
				List<TreeFillingConfiguration> confs = FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(getTable(), depth);
				int nbConfig = confs.size();
				int wantedConfig = Integer.parseInt(String.valueOf(nbCategoriesForTheDepth));
				Assert.assertEquals("The number of configuration is not the expected one", wantedConfig, nbConfig); //$NON-NLS-1$

				if (depth == 0) {
					Assert.assertTrue("The configuration number is not correct", confs.size() == 1); //$NON-NLS-1$
					for (TreeFillingConfiguration tmp : confs) {
						PasteEObjectConfiguration pasteConf = tmp.getPasteConfiguration();
						Assert.assertNotNull("We don't have paste configuration for a TreeFillingConfiguration, depth=" + tmp.getDepth(), pasteConf); //$NON-NLS-1$
						if (depth == 0) {
							Assert.assertTrue("The configuration number is not correct for the tree filling", confs.size() == 1); //$NON-NLS-1$
							Assert.assertTrue("The pasted element id is not correctly defined", CLASS_ELEMENT_ID.equals(pasteConf.getPastedElementId())); //$NON-NLS-1$
						}
					}
				} else if (depth == 1) {
					boolean nestedClass = false;
					boolean operation = false;
					boolean property = false;

					for (TreeFillingConfiguration tmp : confs) {
						PasteEObjectConfiguration pasteConf = tmp.getPasteConfiguration();
						Assert.assertNotNull("We don't have paste configuration for a TreeFillingConfiguration, depth=" + tmp.getDepth(), pasteConf); //$NON-NLS-1$
						if (depth == 1) {
							if (PROPERTY_ELEMENT_ID.equals(pasteConf.getPastedElementId())) {
								property = true;
							} else if (OPERATION_ELEMENT_ID.equals(pasteConf.getPastedElementId())) {
								operation = true;
							} else if (CLASS_ELEMENT_ID.equals(pasteConf.getPastedElementId())) {
								nestedClass = true;
							}
						}
					}
					if (confs.size() == 3) {
						Assert.assertTrue("Table configuration must contains property", property); //$NON-NLS-1$
						Assert.assertTrue("Table configuration must contains operation", operation); //$NON-NLS-1$
						Assert.assertTrue("Table configuration must contains nestedClass", nestedClass); //$NON-NLS-1$
					} else if (confs.size() == 1) {
						Assert.assertTrue("Table configuration must not contains property", !property); //$NON-NLS-1$
						Assert.assertTrue("Table configuration must contains opration", operation); //$NON-NLS-1$
						Assert.assertTrue("Table configuration must not contains nestedClass", !nestedClass); //$NON-NLS-1$
					}
				} else if (depth == 2) {
					for (TreeFillingConfiguration tmp : confs) {
						PasteEObjectConfiguration pasteConf = tmp.getPasteConfiguration();
						Assert.assertNotNull("We don't have paste configuration for a TreeFillingConfiguration, depth=" + tmp.getDepth(), pasteConf); //$NON-NLS-1$

						Assert.assertTrue("The configuration number is not correct for the tree filling", confs.size() == 1); //$NON-NLS-1$
						Assert.assertTrue("The pasted element id is not correctly defined", PARAMETER_ELEMENT_ID.equals(pasteConf.getPastedElementId())); //$NON-NLS-1$
					}
				}
			}
		}
	}

	/**
	 * This allows to set the selection in table for the paste. The initial selection is the first cell of the table.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	public abstract void manageSelection(final NattableModelManager treeManager) throws Exception;

	/**
	 * This allows to test the close of the table and the re-open.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void testClose_Open() throws Exception {
		final Command cmd = EclipseCommandUtils.getCommandService().getCommand("org.eclipse.ui.file.save"); //$NON-NLS-1$
		cmd.executeWithChecks(new ExecutionEvent());

		this.editor.getEditorSite().getPage().closeEditor(editor, false);

		editor = EditorUtils.openPapyrusEditor(diModelFile);

		final IPageManager pageManager = editor.getServicesRegistry().getService(IPageManager.class);
		Assert.assertEquals("Only the table page must be open", 1, pageManager.allPages().size()); //$NON-NLS-1$
		IEditorPart tableEditor = editor.getActiveEditor();

		// the editor has been saved, so the table is already opened when we re open the model
		final Resource notationResource = NotationUtils.getNotationModel(editor.getServicesRegistry().getService(ModelSet.class)).getResource();
		final Table table = (Table) notationResource.getContents().get(0);
		final TransactionalEditingDomain editingDomain = editor.getServicesRegistry().getService(TransactionalEditingDomain.class);
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(new OpenDiagramCommand(editingDomain, table)));

		tableEditor = editor.getActiveEditor();
		Assert.assertTrue("Table editor must be a nattable editor", tableEditor instanceof NatTableEditor); //$NON-NLS-1$
		final INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The manager must be a tree nattable model manager", manager instanceof ITreeNattableModelManager); //$NON-NLS-1$
	}

	/**
	 * This allows to test the undo redo commands.
	 * 
	 * @param treeManager
	 *            The tree nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void testUndo_Redo(final TreeNattableModelManager treeManager) throws Exception {
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
	 * @param treeManager
	 *            The tree nattable model manager.
	 * @param suffixFileName
	 *            The suffix of the file to check.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkTableContent(final TreeNattableModelManager treeManager, final String suffixFileName) throws Exception {
		treeManager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		final NatTable natTable = (NatTable) treeManager.getAdapter(NatTable.class);
		flushDisplayEvents();

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
	 * This allows to replace the useless class name part.
	 * 
	 * @param className
	 *            The initial class name.
	 * @return The name of the class without the useless name part.
	 * @throws Exception
	 *             The caught exception
	 */
	public abstract String removeClassName(final String className) throws Exception;

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
		URI uri = treeManager.getTable().eResource().getURI();
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractOpenTableTest#getBundle()
	 */
	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

	/**
	 * This allows to close the editors.
	 */
	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}
}
