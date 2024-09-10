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
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 525367
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.tests.tests;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.common.modelresource.PapyrusNattableModel;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExportCommandHandler;
import org.eclipse.papyrus.infra.nattable.export.file.PapyrusFileExporter;
import org.eclipse.papyrus.infra.nattable.export.file.command.PapyrusFileExportCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.menu.MenuUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.style.configattribute.PapyrusExportConfigAttributes;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.nattable.matrix.tests.Activator;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;
import org.junit.Rule;

/**
 * 
 * Abstract class used for the table JUnit tests
 */
public abstract class AbstractTableTest extends AbstractPapyrusTest {

	/**
	 * The editor fixture
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * the nattable editor
	 */
	protected NatTableEditor editor;

	/**
	 * the nattable widget
	 */
	protected NatTable natTable;

	/**
	 * the tree table manager
	 */
	protected TreeNattableModelManager manager;

	/**
	 * the initialization of the classes before executing the JUnit testsS
	 */
	public void initTest() {
		this.manager = (TreeNattableModelManager) fixture.getActiveTableManager();
		this.natTable = (NatTable) this.manager.getAdapter(NatTable.class);
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
		Assert.assertTrue(null != this.manager);
		Assert.assertTrue(null != this.natTable);
		registerNattableWidgetInEclipseContext(manager, new LabelStack(GridRegion.BODY));
	}

	/**
	 * This method register the nattable widget in the eclipse context, to be able to get is in the the setEnable(Objet) of the handlers
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler.getCurrentNattableModelManager()
	 * @param manager
	 *            the nattable manager
	 */
	protected void registerNattableWidgetInEclipseContext(INattableModelManager manager, LabelStack regionLabels) {
		MenuUtils.registerNatTableWidgetInEclipseContext(manager, regionLabels);
	}


	/**
	 * Create a model identifying the editor. This model will be saved with the sash.
	 * 
	 * @param view
	 *            The view context of the table.
	 * @throws Exception
	 *             The exception.
	 */
	protected void createAndOpenEditorTable(final EObject tableContext, final TableConfiguration tableConfiguration, final String tableName) throws Exception {
		final ServicesRegistry serviceRegistry = ServiceUtilsForEObject.getInstance().getServiceRegistry(tableContext);
		final ModelSet modelSet = ServiceUtils.getInstance().getModelSet(serviceRegistry);
		Assert.assertNotNull(tableConfiguration);

		final TransactionalEditingDomain domain = ServiceUtils.getInstance().getTransactionalEditingDomain(serviceRegistry);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				Table editorModel;
				try {
					final String name = (tableName != null && !tableName.isEmpty()) ? tableName : tableConfiguration.getName();
					editorModel = TableHelper.createTable(tableConfiguration, tableContext, name, "");
					// Save the model in the associated resource
					final PapyrusNattableModel model = (PapyrusNattableModel) modelSet.getModelChecked(PapyrusNattableModel.MODEL_ID);
					model.addPapyrusTable(editorModel);

					// Get the manager allowing to add/open new editor.
					final IPageManager pageMngr = ServiceUtils.getInstance().getService(IPageManager.class, serviceRegistry);
					pageMngr.openPage(editorModel);
				} catch (Exception e) {
					Activator.log.error(e);
				}
			}
		});
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
		this.fixture.flushDisplayEvents();

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
	 * Return the source path of the text file used as expected result of the table contents
	 */
	protected abstract String getSourcePath();

	/**
	 * @param elementList The list of elements axis
	 *
	 * @return A string contains all axis names of the input elements list, name separated by a comma.
	 */
	protected String getAllAxisNameString(final List<Object> elementList) {
		String result = "";
		int index = 0;
		int noColumn = elementList.size();

		for (Object columnObject : elementList) {
			String objName = "";
			if (columnObject instanceof NamedElement) {
				objName = ((NamedElement) columnObject).getName();
			} else if (columnObject instanceof IAxis && ((IAxis) columnObject).getElement() instanceof NamedElement) {
				objName = ((NamedElement) ((IAxis) columnObject).getElement()).getName();
			}
			result += objName;

			// Add the comma after a name, except the last one
			if (index < noColumn - 1) {
				result += ", ";
			}
			index++;
		}

		return result;
	}
}

