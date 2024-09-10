/*****************************************************************************
 * Copyright (c) 2013, 2014, 2021 CEA LIST and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 434993
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 571540
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.views.tests.tests;

import java.util.Collection;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.commands.OpenDiagramCommand;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * This test creates a table view, checks its contents and check the synchronization of the table contents when with delete a Diagram
 *
 */
@PluginResource("resources/model2.di")
public class TableCreationAndSynchronizationTest extends AbstractCreationTableTests {

	private static final String TABLE_1_NAME = "TableOfViews0"; //$NON-NLS-1$

	private static final String CREATED_TABLE_NAME = "MyViewTable1"; //$NON-NLS-1$

	private static final String CLASS_DIAGRAM_1_NAME = "ClassDiagram1"; //$NON-NLS-1$

	private static final String CLASS_DIAGRAM_2_NAME = "ClassDiagram2"; //$NON-NLS-1$


	private Model rootModel;

	private Model subModel;

	private Table table1;

	private Diagram diagram1;

	private Diagram diagram2;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		this.rootModel = (Model) fixture.getModel();
		this.subModel = (Model) this.rootModel.getMember("SubModel1"); //$NON-NLS-1$

		Collection<Object> pages = this.fixture.getPageManager().allPages();
		for (final Object current : pages) {
			if (current instanceof Diagram) {
				if (CLASS_DIAGRAM_1_NAME.equals(((Diagram) current).getName())) {
					diagram1 = (Diagram) current;
				} else if (CLASS_DIAGRAM_2_NAME.equals(((Diagram) current).getName())) {
					diagram2 = (Diagram) current;
				}

			} else if (current instanceof Table) {
				if (TABLE_1_NAME.equals(((Table) current).getName())) {
					table1 = (Table) current;
				}
			}
		}

		Assert.assertNotNull(this.subModel);
		Assert.assertNotNull(this.diagram1);
		Assert.assertNotNull(this.diagram2);
		Assert.assertNotNull(this.table1);
		Assert.assertNotNull(this.rootModel);
		Assert.assertEquals(this.subModel, this.table1.getContext());
		Assert.assertEquals(this.subModel, this.diagram2.getElement());
		Assert.assertEquals(this.rootModel, this.diagram1.getElement());
	}



	@Test
	public void createTable2AndDestroyAnOwnedElement() throws ServiceException {

		// 1. get the view prototype
		TableViewPrototype viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.rootModel);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$

		// 1.1 Create first table
		viewPrototype.instantiateOn(this.rootModel, CREATED_TABLE_NAME, true);
		flushEvent();

		// 1.2 get the created editor
		IEditorPart tmp = this.papyrusEditor.getActiveEditor();
		Assert.assertTrue(tmp instanceof NatTableEditor);
		final NatTableEditor firstEditor = (NatTableEditor) tmp;
		final INattableModelManager firstManager = (INattableModelManager) firstEditor.getAdapter(INattableModelManager.class);

		// 1.3 check created editor
		Assert.assertNotNull(firstManager);
		final Table firstTable = firstManager.getTable();
		Assert.assertEquals(AllTests.VIEWS_TABLE_ID, firstTable.getTableConfiguration().getType());
		Assert.assertEquals(CREATED_TABLE_NAME, firstTable.getName());
		Assert.assertEquals(CREATED_TABLE_NAME, firstEditor.getPartName());


		final IAxisManager rowAxisManager = firstManager.getRowAxisManager();
		Collection<Object> managedAxis_Table1 = rowAxisManager.getAllManagedAxis();
		Assert.assertEquals(4, managedAxis_Table1.size());
		Assert.assertTrue(managedAxis_Table1.contains(this.table1));
		Assert.assertTrue(managedAxis_Table1.contains(firstTable));
		Assert.assertTrue(managedAxis_Table1.contains(this.diagram1));
		Assert.assertTrue(managedAxis_Table1.contains(this.diagram2));
		Assert.assertEquals(managedAxis_Table1.size(), rowAxisManager.getTableManager().getRowElementsList().size());
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(firstTable));
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(this.table1));
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(this.diagram1));
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(this.diagram2));


		TransactionalEditingDomain editingDomain = this.papyrusEditor.getServicesRegistry().getService(TransactionalEditingDomain.class);
		DestroyElementRequest request = new DestroyElementRequest(editingDomain, this.diagram2, false);
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(this.diagram2);
		ICommand cmd = provider.getEditCommand(request);
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));


		flushEvent();

		// verify the contents of table2
		managedAxis_Table1 = rowAxisManager.getAllManagedAxis();
		Assert.assertEquals(3, managedAxis_Table1.size());
		Assert.assertTrue(managedAxis_Table1.contains(this.table1));
		Assert.assertTrue(managedAxis_Table1.contains(firstTable));
		Assert.assertTrue(managedAxis_Table1.contains(this.diagram1));
		Assert.assertFalse(managedAxis_Table1.contains(this.diagram2));




		Assert.assertEquals(managedAxis_Table1.size(), rowAxisManager.getTableManager().getRowElementsList().size());
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(firstTable));
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(this.table1));
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(this.diagram1));
		Assert.assertFalse(rowAxisManager.getTableManager().getRowElementsList().contains(this.diagram2));



		// verify the contents of table1 (we open it)
		IPageManager pageManager = this.papyrusEditor.getServicesRegistry().getService(IPageManager.class);
		Assert.assertEquals(3, pageManager.allPages().size());
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(new OpenDiagramCommand(editingDomain, this.table1)));

		flushEvent();

		IEditorPart tableEditor = this.papyrusEditor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertNotNull(manager);
		Assert.assertEquals(TABLE_1_NAME, manager.getTable().getName());

		IAxisManager axisManager = manager.getRowAxisManager();
		final Collection<Object> managedAxis_Table2 = axisManager.getAllManagedAxis();
		Assert.assertEquals(1, managedAxis_Table2.size());
		Assert.assertTrue(managedAxis_Table2.contains(this.table1));

		Assert.assertEquals(managedAxis_Table2.size(), axisManager.getTableManager().getRowElementsList().size());
		Assert.assertTrue(axisManager.getTableManager().getRowElementsList().contains(this.table1));

	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.views.tests.tests.AbstractCreationTableTests#tearDown()
	 *
	 */
	@Override
	public void tearDown() {
		super.tearDown();
		this.rootModel = null;
		this.subModel = null;
		this.table1 = null;
		this.diagram1 = null;
		this.diagram2 = null;
	}

}
