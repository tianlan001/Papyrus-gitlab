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

import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@PluginResource("resources/model.di")
public class TableCreationTest extends AbstractCreationTableTests {

	private static final String FIRST_TABLE_NAME = "MyViewTable1"; //$NON-NLS-1$

	private static final String TABLE_2_NAME = "MyViewTable2"; //$NON-NLS-1$

	private static final String TABLE_3_NAME = "MyViewTable3"; //$NON-NLS-1$

	private Model rootModel;

	private Model subModel;

	private Model subSubModel;

	private Package subPackage;

	private Package subSubPackage;


	@Override
	@Before
	public void setUp() {
		super.setUp();
		this.rootModel = (Model) fixture.getModel();
		this.subModel = (Model) this.rootModel.getMember("SubModel"); //$NON-NLS-1$
		this.subPackage = (Package) this.rootModel.getMember("SubPackage"); //$NON-NLS-1$
		if (this.subModel != null && this.subPackage != null) {
			this.subSubModel = (Model) this.subModel.getMember("SubSubModel"); //$NON-NLS-1$
			this.subSubPackage = (Package) this.subPackage.getMember("SubSubPackage");//$NON-NLS-1$
		}
		Assert.assertNotNull(this.rootModel);
		Assert.assertNotNull(this.subModel);
		Assert.assertNotNull(this.subPackage);
		Assert.assertNotNull(this.subSubModel);
		Assert.assertNotNull(this.subSubPackage);
	}


	@Test
	public void testCreationHandlerStatusOnRootModel() {
		final TableViewPrototype viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.rootModel);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$
	}

	@Test
	public void testCreationHandlerStatusOnSubModel() {
		final TableViewPrototype viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.subModel);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$
	}

	@Test
	public void testCreationHandlerStatusOnSubSubModel() {
		final TableViewPrototype viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.subSubModel);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$
	}

	@Test
	public void testCreationHandlerStatusOnSubPackage() {
		final TableViewPrototype viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.subPackage);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$
	}

	@Test
	public void testCreationHandlerStatusOnSubSubPackage() {
		final TableViewPrototype viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.subSubPackage);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$
	}

	@Test
	public void testCreationAndSynchronization() {
		// 1. get the view prototype
		TableViewPrototype viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.subSubPackage);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$

		// 1.1 Create first table
		viewPrototype.instantiateOn(this.subSubPackage, FIRST_TABLE_NAME, true);
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
		Assert.assertEquals(FIRST_TABLE_NAME, firstTable.getName());
		Assert.assertEquals(FIRST_TABLE_NAME, firstEditor.getPartName());

		// 1.4 check contents
		final IAxisManager rowAxisManager = firstManager.getRowAxisManager();
		final Collection<Object> managedAxis_subSubPackageTable1 = rowAxisManager.getAllManagedAxis();
		Assert.assertEquals(1, managedAxis_subSubPackageTable1.size());
		Assert.assertTrue(managedAxis_subSubPackageTable1.contains(firstTable));
		Assert.assertEquals(managedAxis_subSubPackageTable1.size(), rowAxisManager.getTableManager().getRowElementsList().size());
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(firstTable));

		/* we create a second table in the same element, to check the correct synchronization of the contents */

		// 2 get the view prototype to create a second table
		viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.subSubPackage);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$

		// 2.1 we create a second table. This one must be added to the content of the first one
		viewPrototype.instantiateOn(this.subSubPackage, TABLE_2_NAME, true);
		flushEvent();

		// 2.2 get the created editor
		tmp = this.papyrusEditor.getActiveEditor();
		Assert.assertTrue(tmp instanceof NatTableEditor);
		final NatTableEditor secondEditor = (NatTableEditor) tmp;
		final INattableModelManager secondManager = (INattableModelManager) secondEditor.getAdapter(INattableModelManager.class);

		// 2.3 check created editor
		Assert.assertNotNull(secondManager);
		final Table secondTable = secondManager.getTable();
		Assert.assertEquals(AllTests.VIEWS_TABLE_ID, secondTable.getTableConfiguration().getType());
		Assert.assertEquals(TABLE_2_NAME, secondTable.getName());
		Assert.assertEquals(TABLE_2_NAME, secondEditor.getPartName());

		// 2.4 check contents
		final IAxisManager rowAxisManager2 = secondManager.getRowAxisManager();
		Collection<Object> managedAxis2 = rowAxisManager2.getAllManagedAxis();
		Assert.assertEquals(2, managedAxis2.size());
		Assert.assertTrue(managedAxis2.contains(firstTable));
		Assert.assertTrue(managedAxis2.contains(secondTable));

		Assert.assertEquals(managedAxis2.size(), rowAxisManager2.getTableManager().getRowElementsList().size());
		Assert.assertTrue(rowAxisManager2.getTableManager().getRowElementsList().contains(firstTable));
		Assert.assertTrue(rowAxisManager2.getTableManager().getRowElementsList().contains(secondTable));


		// 2.5 we verify the contents of the first table
		final Collection<Object> managedAxis_subSubPackageTable1_1 = rowAxisManager.getAllManagedAxis();
		Assert.assertEquals(2, managedAxis_subSubPackageTable1_1.size());
		Assert.assertTrue(managedAxis_subSubPackageTable1_1.contains(firstTable));
		Assert.assertTrue(managedAxis_subSubPackageTable1_1.contains(secondTable));
		Assert.assertEquals(managedAxis_subSubPackageTable1_1.size(), rowAxisManager.getTableManager().getRowElementsList().size());
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(firstTable));
		Assert.assertTrue(rowAxisManager.getTableManager().getRowElementsList().contains(secondTable));

		/* 3. we create a third table in another container. This table must contains only itself and the others table muse not contains it */
		viewPrototype = getViewPrototype(TABLE_VIEW_REPRESENTATION_KIND_ID, this.subModel);
		Assert.assertNotNull("The view prototype to create the table is not available", viewPrototype); //$NON-NLS-1$

		// 3.1 create the thirst table
		viewPrototype.instantiateOn(this.subModel, TABLE_3_NAME, true);
		flushEvent();

		// 3.2 get the created editor
		tmp = this.papyrusEditor.getActiveEditor();
		Assert.assertTrue(tmp instanceof NatTableEditor);
		final NatTableEditor thirdEditor = (NatTableEditor) tmp;
		final INattableModelManager thirdManager = (INattableModelManager) thirdEditor.getAdapter(INattableModelManager.class);

		// 3.3 check created editor
		Assert.assertNotNull(thirdManager);
		final Table thirdTable = thirdManager.getTable();
		Assert.assertEquals(AllTests.VIEWS_TABLE_ID, thirdTable.getTableConfiguration().getType());
		Assert.assertEquals(TABLE_3_NAME, thirdTable.getName());
		Assert.assertEquals(TABLE_3_NAME, thirdEditor.getPartName());

		// 3.4 check contents
		final IAxisManager rowAxisManager3 = thirdManager.getRowAxisManager();
		final Collection<Object> managedAxis_SubPackageTable3 = rowAxisManager3.getAllManagedAxis();
		Assert.assertEquals(1, managedAxis_SubPackageTable3.size());
		Assert.assertTrue(managedAxis_SubPackageTable3.contains(thirdTable));
		Assert.assertEquals(managedAxis_SubPackageTable3.size(), rowAxisManager3.getTableManager().getRowElementsList().size());
		Assert.assertTrue(rowAxisManager3.getTableManager().getRowElementsList().contains(thirdTable));

		// 3.5 we verify that the other tables don't reference it
		// verify in table 1
		IAxisManager rowAxisManagerTable1 = firstManager.getRowAxisManager();
		Collection<Object> managedAxisTable1 = rowAxisManagerTable1.getAllManagedAxis();
		Assert.assertEquals(2, managedAxisTable1.size());
		Assert.assertFalse(managedAxisTable1.contains(thirdTable));
		Assert.assertEquals(managedAxisTable1.size(), rowAxisManagerTable1.getTableManager().getRowElementsList().size());
		Assert.assertFalse(rowAxisManagerTable1.getTableManager().getRowElementsList().contains(thirdTable));

		// verify in table 2
		IAxisManager rowAxisManagerTable2 = secondManager.getRowAxisManager();
		Collection<Object> managedAxisTable2 = rowAxisManagerTable2.getAllManagedAxis();
		Assert.assertEquals(2, managedAxisTable2.size());
		Assert.assertFalse(managedAxisTable2.contains(thirdTable));
		Assert.assertEquals(managedAxisTable2.size(), rowAxisManagerTable2.getTableManager().getRowElementsList().size());
		Assert.assertFalse(rowAxisManagerTable2.getTableManager().getRowElementsList().contains(thirdTable));
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
		this.subSubModel = null;
		this.subPackage = null;
		this.subSubPackage = null;
	}

}
