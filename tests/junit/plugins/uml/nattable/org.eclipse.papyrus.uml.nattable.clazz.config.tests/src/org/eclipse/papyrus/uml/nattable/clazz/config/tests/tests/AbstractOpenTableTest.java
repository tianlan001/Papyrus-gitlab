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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.papyrus.commands.OpenDiagramCommand;
import org.eclipse.papyrus.editor.integration.tests.tests.AbstractEditorIntegrationTest;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.menu.MenuUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.eclipse.ui.IEditorPart;
import org.junit.AfterClass;
import org.junit.Assert;
import org.osgi.framework.Bundle;

/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractOpenTableTest extends AbstractEditorIntegrationTest {

	/**
	 * This test allows to be sure that we doesn't break existing table model
	 *
	 * @throws Exception
	 */

	public void testOpenExistingTable(String projectName, String modelName) throws Exception {
		// initModel(projectName, modelName, getBundle()); //$NON-NLS-1$ //$NON-NLS-2$
		IPageManager pageManager = editor.getServicesRegistry().getService(IPageManager.class);
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
		Assert.assertEquals(org.eclipse.papyrus.uml.nattable.clazz.config.Activator.TABLE_TYPE, manager.getTable().getTableConfiguration().getType());

		registerNattableWidgetInEclipseContext(manager, new LabelStack(GridRegion.BODY));
	}

	/**
	 * This method register the nattable widget in the eclipse context, to be able to get is in the the setEnable(Objet) of the handlers
	 * 
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler.getCurrentNattableModelManager()
	 * @param manager
	 *            the nattable manager
	 */
	protected void registerNattableWidgetInEclipseContext(INattableModelManager manager, LabelStack regionLabels) {
		MenuUtils.registerNatTableWidgetInEclipseContext(manager, regionLabels);
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

	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}

}
