/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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

package org.eclipse.papyrus.uml.nattable.generic.tests.tests;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.papyrus.commands.OpenDiagramCommand;
import org.eclipse.papyrus.editor.integration.tests.tests.AbstractEditorIntegrationTest;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.menu.MenuUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.utils.EditorUtils;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.ProjectUtils;
import org.eclipse.papyrus.uml.nattable.generic.tests.Activator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.junit.AfterClass;
import org.junit.Assert;
import org.osgi.framework.Bundle;

/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractGenericTableTest extends AbstractEditorIntegrationTest {

	/**
	 * Inits this.editor
	 * Fails or throws an exception if an error occurs
	 *
	 * @param bundle
	 *            the source bundle where the model is store
	 * @param projectName
	 *            the project that will created at runtime to execute test
	 * @param modelName
	 *            the model that will be copied and test executed on.
	 * @param otherModelsToLoad
	 *            model to load
	 */
	protected void initModelWithAdditionalModels(String projectName, String modelName, Bundle bundle, Collection<String> otherModelsToLoad) throws Exception {
		project = ProjectUtils.createProject(projectName);
		for (String current : otherModelsToLoad) {
			final IFile project1 = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), current); //$NON-NLS-1$
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					try {
						editor = EditorUtils.openPapyrusEditor(project1);
					} catch (Exception ex) {
						Activator.log.error(ex);
						Assert.fail(ex.getMessage());
					}
				}
			});
		}
		this.diModelFile = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), modelName);
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					editor = EditorUtils.openPapyrusEditor(diModelFile);
				} catch (Exception ex) {
					Activator.log.error(ex);
					Assert.fail(ex.getMessage());
				}
			}
		});

		Assert.assertNotNull(editor);
	}

	/**
	 * this method opens the generic table (must be the first editor available in the page manager)
	 *
	 * @throws Exception
	 */

	public void loadGenericTable() throws Exception {
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertNull(tableEditor);
		Resource notationResource = NotationUtils.getNotationModel(editor.getServicesRegistry().getService(ModelSet.class)).getResource();
		Table table = (Table) notationResource.getContents().get(0);
		TransactionalEditingDomain editingDomain = editor.getServicesRegistry().getService(TransactionalEditingDomain.class);
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(new OpenDiagramCommand(editingDomain, table)));
		// to refresh the table content
		// while(!Display.getDefault().isDisposed() && Display.getDefault().readAndDispatch());
		tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertNotNull(manager);
		Assert.assertEquals(org.eclipse.papyrus.uml.nattable.generic.config.Activator.TABLE_TYPE, manager.getTable().getTableConfiguration().getType());
		MenuUtils.registerNatTableWidgetInEclipseContext(manager, new LabelStack(GridRegion.BODY));
	}

	protected Table getTable() throws ServiceException {
		Resource notationResource = NotationUtils.getNotationModel(editor.getServicesRegistry().getService(ModelSet.class)).getResource();
		Table table = (Table) notationResource.getContents().get(0);
		return table;
	}

	@Override
	protected String getSourcePath() {
		return "/resources/"; //$NON-NLS-1$
	}

	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}


	protected INattableModelManager getTableManager() {
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		return manager;
	}

	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}
}
