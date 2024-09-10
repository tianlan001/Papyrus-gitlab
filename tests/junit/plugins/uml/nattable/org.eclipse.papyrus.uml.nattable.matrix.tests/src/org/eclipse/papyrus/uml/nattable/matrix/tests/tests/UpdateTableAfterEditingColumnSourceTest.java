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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 525367
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.tests.tests;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.MasterObjectAxisProvider;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class tests the update of an existing table after editing the column sources.
 */
@SuppressWarnings("nls")
@PluginResource("resources/updateTableContentsAfterColumnSourcesChangeTests/updateTableContentsAfterColumnSourcesChange.di")
public class UpdateTableAfterEditingColumnSourceTest extends AbstractTableTest {

	private static final int NB_CLASSES_IN_PACKAGE1_COLUMN_SOURCES = 4;

	private static final int NB_CLASSES_IN_PACKAGE2_COLUMN_SOURCES = 3;

	private static final int NB_ROWS = 4;

	private NamedElement package1_ColumnSources = null;

	private NamedElement package2_ColumnSources = null;

	private static final String PACKAGE1_COLUMN_SOURCES_NAME = "Package1_ColumnSources";

	private static final String PACKAGE2_COLUMN_SOURCES_NAME = "Package2_ColumnSources";

	/**
	 * @see org.eclipse.papyrus.uml.nattable.matrix.tests.tests.AbstractTableTest#getSourcePath()
	 *
	 * @return
	 */
	@Override
	protected String getSourcePath() {
		return "resources/updateTableContentsAfterColumnSourcesChangeTests";
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.matrix.tests.tests.AbstractTableTest#initTest()
	 */
	@Override
	public void initTest() {
		super.initTest();
		this.package1_ColumnSources = this.fixture.getModel().getMember(PACKAGE1_COLUMN_SOURCES_NAME);
		this.package2_ColumnSources = this.fixture.getModel().getMember(PACKAGE2_COLUMN_SOURCES_NAME);
		Assert.assertNotNull(this.package1_ColumnSources);
		Assert.assertNotNull(this.package2_ColumnSources);
	}

	/**
	 * This test checks the opening of the existing matrix.
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testOpeningMatrix() throws Exception {
		initTest();
		final List<Object> rowElementsList = this.manager.getRowElementsList();
		final List<Object> columnElementsList = this.manager.getColumnElementsList();

		// There is no columns initially
		Assert.assertEquals("The number of columns is not the expected one.", 0, columnElementsList.size());
		// NB_ROWS +1 for tree filling +1 for the root package
		Assert.assertEquals("The number of rows is not the expected one.", NB_ROWS + 1 + 1, rowElementsList.size());

		// Check the initial matrix content
		checkTableContent(manager, "_NoColumnSource");
	}

	/**
	 * This test checks the addition of Package1 as column source in the existing matrix.
	 *
	 * @throws Exception
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testMatrixAddPackage1AsColumnSources() throws Exception {
		initTest();

		// Add Package1_ColumnSources as column sources
		final RecordingCommand rc = getAddColumnSourcesCommand(this.package1_ColumnSources);
		fixture.getEditingDomain().getCommandStack().execute(rc);

		// Check source wrapper contains Package1
		final MasterObjectAxisProvider columnAxisProvider = (MasterObjectAxisProvider) this.manager.getTable().getCurrentColumnAxisProvider();
		Assert.assertEquals(1, columnAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_ColumnSources, columnAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES_IN_PACKAGE1_COLUMN_SOURCES, this.manager.getColumnElementsList().size());

		// Check the table content
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		checkTableContent(this.manager, "_Package1AsSource");

		// Check undo
		fixture.getEditingDomain().getCommandStack().undo();
		Assert.assertEquals(0, columnAxisProvider.getSources().size());
		Assert.assertEquals("The number of columns is not the expected one.", 0, this.manager.getColumnElementsList().size());
		checkTableContent(this.manager, "_NoColumnSource");

		// Check redo
		fixture.getEditingDomain().getCommandStack().redo();
		Assert.assertEquals(1, columnAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_ColumnSources, columnAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES_IN_PACKAGE1_COLUMN_SOURCES, this.manager.getColumnElementsList().size());
		checkTableContent(this.manager, "_Package1AsSource");
	}

	/**
	 * This test checks the addition of Package1 and Package2 as column source in the existing matrix.
	 *
	 * @throws Exception
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testMatrixAddPackage1Package2AsColumnSources() throws Exception {
		initTest();

		addPackage1Package2AsColumnSources();

		final MasterObjectAxisProvider columnAxisProvider = (MasterObjectAxisProvider) this.manager.getTable().getCurrentColumnAxisProvider();

		// Check undo
		fixture.getEditingDomain().getCommandStack().undo();
		Assert.assertEquals(0, columnAxisProvider.getSources().size());
		checkTableContent(this.manager, "_NoColumnSource");

		// Check redo
		fixture.getEditingDomain().getCommandStack().redo();
		Assert.assertEquals(2, columnAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_ColumnSources, columnAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals(this.package2_ColumnSources, columnAxisProvider.getSources().get(1).getElement());
		checkTableContent(this.manager, "_Package1AndPackage2AsSource");
	}

	/**
	 * This test checks the removing of package column sources of an existing matrix.
	 *
	 * @throws Exception
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testMatrixRemovingPackagesAsColumnSources() throws Exception {
		initTest();

		// 1. Add Package1 and Package2 as column sources
		addPackage1Package2AsColumnSources();

		// 2. Remove Package1
		final RecordingCommand rc = getRemoveColumnSourcesCommand(this.package1_ColumnSources);
		fixture.getEditingDomain().getCommandStack().execute(rc);

		final MasterObjectAxisProvider columnAxisProvider = (MasterObjectAxisProvider) this.manager.getTable().getCurrentColumnAxisProvider();

		// Check source wrapper contains Package2
		Assert.assertEquals(1, columnAxisProvider.getSources().size());
		Assert.assertEquals(this.package2_ColumnSources, columnAxisProvider.getSources().get(0).getElement());
		// Check the table content
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		checkTableContent(this.manager, "_Package2AsSource");

		// Check undo
		fixture.getEditingDomain().getCommandStack().undo();
		// Check source wrapper contains Package1 and Package2
		Assert.assertEquals(2, columnAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_ColumnSources, columnAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals(this.package2_ColumnSources, columnAxisProvider.getSources().get(1).getElement());

		// Bug 525540: table columns are not refreshed after an undo, the order of columns is not preserved.
		// So the check the table content is commented out here.
		// TODO It should be uncommented once the bug is corrected.
		// this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		// checkTableContent(this.manager, "_Package1AndPackage2AsSource");

		// Instead, the column names will be verified
		Assert.assertEquals("Class21, Class22, Class23, Class11, Class12, Class13, Class14", getAllAxisNameString(manager.getVerticalFilterList()));

		// Check redo
		fixture.getEditingDomain().getCommandStack().redo();
		// Check source wrapper contains Package2
		Assert.assertEquals(1, columnAxisProvider.getSources().size());
		Assert.assertEquals(this.package2_ColumnSources, columnAxisProvider.getSources().get(0).getElement());
		// Check the table content
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		checkTableContent(this.manager, "_Package2AsSource");

		//3. Remove Package2
		final RecordingCommand rc1 = getRemoveColumnSourcesCommand(this.package2_ColumnSources);
		fixture.getEditingDomain().getCommandStack().execute(rc1);

		// Check source wrapper is empty
		Assert.assertEquals(0, columnAxisProvider.getSources().size());
		// Check the table content
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		checkTableContent(this.manager, "_NoColumnSource");

		// Check undo
		fixture.getEditingDomain().getCommandStack().undo();
		// Check source wrapper contains Package2
		Assert.assertEquals(1, columnAxisProvider.getSources().size());
		Assert.assertEquals(this.package2_ColumnSources, columnAxisProvider.getSources().get(0).getElement());
		// Check the table content
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		checkTableContent(this.manager, "_Package2AsSource");

		// Check redo
		fixture.getEditingDomain().getCommandStack().redo();
		// Check source wrapper is empty
		Assert.assertEquals(0, columnAxisProvider.getSources().size());
		// Check the table content
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		checkTableContent(this.manager, "_NoColumnSource");
	}

	/**
	 * Method to add Package1 and Package2 as column sources into the current table manager.
	 * @throws Exception
	 */
	public void addPackage1Package2AsColumnSources() throws Exception {
		// Add Package1 and Package2 as column sources
		final RecordingCommand rc = getAddColumnSourcesCommand(this.package1_ColumnSources, this.package2_ColumnSources);
		fixture.getEditingDomain().getCommandStack().execute(rc);

		// Check source wrapper contains Package1 and Package2
		final MasterObjectAxisProvider columnAxisProvider = (MasterObjectAxisProvider) this.manager.getTable().getCurrentColumnAxisProvider();
		Assert.assertEquals(2, columnAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_ColumnSources, columnAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals(this.package2_ColumnSources, columnAxisProvider.getSources().get(1).getElement());
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES_IN_PACKAGE1_COLUMN_SOURCES + NB_CLASSES_IN_PACKAGE2_COLUMN_SOURCES, this.manager.getColumnElementsList().size());

		// Check the table content
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		checkTableContent(this.manager, "_Package1AndPackage2AsSource");
	}

	/**
	 * Get recording command to add column sources.
	 *
	 * @param packagesToAdd The array of packages to be added
	 * @return The add recording command
	 */
	private RecordingCommand getAddColumnSourcesCommand(final NamedElement... packagesToAdd) {
		return new RecordingCommand(this.fixture.getEditingDomain()) {
			@Override
			protected void doExecute() {
				final IMatrixTableWidgetManager matrixManager = (IMatrixTableWidgetManager) UpdateTableAfterEditingColumnSourceTest.this.manager;
				matrixManager.addColumnSources(Arrays.asList(packagesToAdd));
			}
		};
	}

	/**
	 * Get recording command to remove column sources.
	 *
	 * @param packagesToRemove The array of packages to be removed
	 * @return The remove recording command
	 */
	private RecordingCommand getRemoveColumnSourcesCommand(final NamedElement... packagesToRemove) {
		return new RecordingCommand(this.fixture.getEditingDomain()) {
			@Override
			protected void doExecute() {
				final IMatrixTableWidgetManager matrixManager = (IMatrixTableWidgetManager) UpdateTableAfterEditingColumnSourceTest.this.manager;
				matrixManager.removeColumnSources(Arrays.asList(packagesToRemove));
			}
		};
	}
}
