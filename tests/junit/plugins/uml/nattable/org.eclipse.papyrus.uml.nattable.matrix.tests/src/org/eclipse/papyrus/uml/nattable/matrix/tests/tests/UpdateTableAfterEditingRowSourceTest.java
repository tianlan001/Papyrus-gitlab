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
 *   CEA LIST - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 522721
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.tests.tests;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
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
 * This class tests the update of an existing table after editing the row sources
 */
@SuppressWarnings("nls")
@PluginResource("resources/updateTableContentsAfterRowsSourceChangeTests/updateTableContentsAfterRowsSourceChange.di")
public class UpdateTableAfterEditingRowSourceTest extends AbstractTableTest {

	private static final int NB_CLASSES_IN_PACKAGE1_ROWS_SOURCES = 4;

	private static final int NB_CLASSES_IN_PACKAGE2_ROWS_SOURCES = 5;

	private static final int NB_COLUMNS = 4;

	private NamedElement package1_Rows_Sources;

	private NamedElement package2_Rows_Sources;

	private static final String PACKAGE1_ROWS_SOURCES_NAME = "Package1_RowsSource";

	private static final String PACKAGE2_ROWS_SOURCES_NAME = "Package2_RowsSource";

	/**
	 * @see org.eclipse.papyrus.uml.nattable.matrix.tests.tests.AbstractTableTest#getSourcePath()
	 *
	 * @return
	 */
	@Override
	protected String getSourcePath() {
		return "resources/updateTableContentsAfterRowsSourceChangeTests/";
	}

	/**
	 * This JUnit tests check the opening of an existing matrix
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testOpeningMatrix() {
		initTest();
		final List<Object> rowElementsList = this.manager.getRowElementsList();
		final List<Object> columnElementsList = this.manager.getColumnElementsList();
		Assert.assertEquals("The number of rows is not the expected one.", NB_CLASSES_IN_PACKAGE1_ROWS_SOURCES + 1 + 1, rowElementsList.size()); //+1 for tree filling + for the root package
		Assert.assertEquals("The number of columns is not the expected one.", NB_COLUMNS, columnElementsList.size());
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.matrix.tests.tests.AbstractTableTest#initTest()
	 *
	 */
	@Override
	public void initTest() {
		super.initTest();
		this.package1_Rows_Sources = this.fixture.getModel().getMember(PACKAGE1_ROWS_SOURCES_NAME);
		this.package2_Rows_Sources = this.fixture.getModel().getMember(PACKAGE2_ROWS_SOURCES_NAME);
		Assert.assertNotNull(this.package1_Rows_Sources);
		Assert.assertNotNull(this.package2_Rows_Sources);

	}


	/**
	 * This JUnit tests check the content of an existing matrix.
	 * 
	 * @throws Exception
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testMatrixContents() throws Exception {
		initTest();

		checkTableContent(manager, "_Package1AsSource");
	}

	/**
	 * This JUnitTest checks removing all row sources.
	 * 
	 * @throws Exception
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testMatrixRemovingAllRowSources() throws Exception {
		initTest();

		// Remove the existing row source
		Command command = new RecordingCommand(this.fixture.getEditingDomain()) {
			@Override
			protected void doExecute() {
				final IMatrixTableWidgetManager matrixManager = (IMatrixTableWidgetManager) UpdateTableAfterEditingRowSourceTest.this.manager;
				matrixManager.removeRowSources(Collections.singleton(UpdateTableAfterEditingRowSourceTest.this.package1_Rows_Sources));
			}
		};
		this.fixture.execute(command);

		// Check there are no source wrappers
		final MasterObjectAxisProvider rowAxisProvider = (MasterObjectAxisProvider) this.manager.getTable().getCurrentRowAxisProvider();
		Assert.assertEquals(0, rowAxisProvider.getSources().size());

		// Check there are no ITreeItemAxis stored for the rows
		Assert.assertEquals(0, rowAxisProvider.getAxis().size());
		Assert.assertEquals("The number of rows is not the expected one.", 0, this.manager.getRowElementsList().size());

		// Check undo
		this.fixture.undo();
		// Strange problem: must do a collapse all and then expand all, otherwise the number of rows and or the table content is not correctly captured
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.COLLAPSE_ALL, null);
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		Assert.assertEquals(1, rowAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_Rows_Sources, rowAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals(1, rowAxisProvider.getAxis().size());
		Assert.assertEquals("The number of rows is not the expected one.", NB_CLASSES_IN_PACKAGE1_ROWS_SOURCES + 1 + 1, this.manager.getRowElementsList().size());
		checkTableContent(this.manager, "_Package1AsSource");

		// Check redo
		this.fixture.redo();
		Assert.assertEquals(0, rowAxisProvider.getSources().size());
		Assert.assertEquals(0, rowAxisProvider.getAxis().size());
		Assert.assertEquals("The number of rows is not the expected one.", 0, this.manager.getRowElementsList().size());
	}

	/**
	 * This JUnitTest checks the addition of the Package2 as source row.
	 * 
	 * @throws Exception
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testMatrixAddPackage2AsRowSources() throws Exception {
		initTest();
		
		Command command = new RecordingCommand(this.fixture.getEditingDomain()) {
			@Override
			protected void doExecute() {
				final IMatrixTableWidgetManager matrixManager = (IMatrixTableWidgetManager) UpdateTableAfterEditingRowSourceTest.this.manager;
				matrixManager.addRowSources(Collections.singleton(UpdateTableAfterEditingRowSourceTest.this.package2_Rows_Sources));
			}
		};
		this.fixture.execute(command);

		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);

		// Check source wrapper contains Package1 and Package2
		final MasterObjectAxisProvider rowAxisProvider = (MasterObjectAxisProvider) this.manager.getTable().getCurrentRowAxisProvider();
		Assert.assertEquals(2, rowAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_Rows_Sources, rowAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals(this.package2_Rows_Sources, rowAxisProvider.getSources().get(1).getElement());

		// Check ITreeItemAxis contains Package1 and Package2
		Assert.assertEquals(2, rowAxisProvider.getAxis().size());
		Assert.assertEquals(this.package1_Rows_Sources, rowAxisProvider.getAxis().get(0).getElement());
		Assert.assertEquals(this.package2_Rows_Sources, rowAxisProvider.getAxis().get(1).getElement());

		// Check number of rows
		Assert.assertEquals("The number of rows is not the expected one.", NB_CLASSES_IN_PACKAGE1_ROWS_SOURCES + NB_CLASSES_IN_PACKAGE2_ROWS_SOURCES + 4, this.manager.getRowElementsList().size());
		checkTableContent(manager, "_Package1AndPackage2ASource");

		// Check undo
		this.fixture.undo();
		Assert.assertEquals(1, rowAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_Rows_Sources, rowAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals(1, rowAxisProvider.getAxis().size());
		Assert.assertEquals(this.package1_Rows_Sources, rowAxisProvider.getAxis().get(0).getElement());
		Assert.assertEquals("The number of rows is not the expected one.", NB_CLASSES_IN_PACKAGE1_ROWS_SOURCES + 2, this.manager.getRowElementsList().size());
		checkTableContent(this.manager, "_Package1AsSource");

		// Check redo
		this.fixture.redo();
		// Strange problem: must do a collapse all and then expand all, otherwise the table contents are not correctly updated
		// This is not happened when doing the test in application
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.COLLAPSE_ALL, null);
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		Assert.assertEquals(2, rowAxisProvider.getSources().size());
		Assert.assertEquals(this.package1_Rows_Sources, rowAxisProvider.getSources().get(0).getElement());
		Assert.assertEquals(this.package2_Rows_Sources, rowAxisProvider.getSources().get(1).getElement());
		Assert.assertEquals(2, rowAxisProvider.getAxis().size());
		Assert.assertEquals(this.package1_Rows_Sources, rowAxisProvider.getAxis().get(0).getElement());
		Assert.assertEquals(this.package2_Rows_Sources, rowAxisProvider.getAxis().get(1).getElement());
		Assert.assertEquals("The number of rows is not the expected one.", NB_CLASSES_IN_PACKAGE1_ROWS_SOURCES + NB_CLASSES_IN_PACKAGE2_ROWS_SOURCES + 4, this.manager.getRowElementsList().size());
		checkTableContent(manager, "_Package1AndPackage2ASource");
	}

}
