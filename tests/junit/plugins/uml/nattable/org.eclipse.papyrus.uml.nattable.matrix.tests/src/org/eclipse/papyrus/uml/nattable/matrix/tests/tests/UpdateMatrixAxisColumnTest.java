/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 520602
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.tests.tests;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the update of matrix axis columns when model element are changed as described in the bug 520602.
 */
@SuppressWarnings("nls")
@PluginResource("resources/updateMatrixAxisColumnTests/UpdateMatrixAxisColumnTest.di")
public class UpdateMatrixAxisColumnTest extends AbstractTableTest {

	/** The root model. */
	private Model rootModel = null;

	/** The Package1 as column source. */
	private Package package1ColumnSource = null;

	/** The Package2 as column source. */
	private Package package2ColumnSource = null;

	/** The Package3 as row source. */
	private Package package3RowSource = null;

	/** The initial number column. */
	private int initNumberColumn;

	/** The initial column names string. */
	private String initColumnNames;

	/** The transactional editing domain. */
	private TransactionalEditingDomain editingDomain = null;

	/**
	 * Default constructor.
	 */
	public UpdateMatrixAxisColumnTest() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initTest() {
		super.initTest();

		final IEditorPart tableEditor = fixture.getActiveTableEditor();
		Assert.assertTrue("Table editor must be a nattable editor", tableEditor instanceof NatTableEditor);

		Assert.assertTrue("The manager must be a matrix nattable model manager", this.manager instanceof IMatrixTableWidgetManager);
		editingDomain = fixture.getEditingDomain();
		rootModel = (Model) fixture.getModel();

		// Package1 and Package2 (each has 2 elements) are used as column source axis
		package1ColumnSource = rootModel.getNestedPackage("Package1_ColumnSource");
		Assert.assertNotNull(package1ColumnSource);
		package2ColumnSource = rootModel.getNestedPackage("Package2_ColumnSource");
		Assert.assertNotNull(package2ColumnSource);

		// Check the column header names
		initColumnNames = getAllAxisNameString(manager.getVerticalFilterList());
		Assert.assertEquals("Class1, Class2, Component1, Component2", getAllAxisNameString(manager.getVerticalFilterList()));

		// The initial number of column axis must equal 4
		initNumberColumn = manager.getColumnCount();
		Assert.assertEquals(4, initNumberColumn);

		// Package3 with 2 elements is used as row source axis
		package3RowSource = rootModel.getNestedPackage("Package3_RowSource");
		Assert.assertNotNull(package3RowSource);

		// Check the row header names
		Assert.assertEquals("Interface1, Interface2", getAllAxisNameString(manager.getHorizontalFilterEventList()));
		// The initial number of row axis must be 2
		Assert.assertEquals(2, manager.getRowCount());
	}

	/**
	 * This tests the addition of one element in a column source package.
	 * A new column must be inserted in the column axis of the table.
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testAddElementInsideAColumnSourcePackage() {
		initTest();

		// Add a new class inside the Package2
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(package1ColumnSource);
		ICommand cmd = provider.getEditCommand(new CreateElementRequest(package1ColumnSource, UMLElementTypes.CLASS));
		Assert.assertTrue(cmd.canExecute());
		editingDomain.getCommandStack().execute((new GMFtoEMFCommandWrapper(cmd)));

		String expectedColumnNames = "Class1, Class2, Class3, Component1, Component2";

		// Check the column header names
		Assert.assertEquals(expectedColumnNames, getAllAxisNameString(manager.getVerticalFilterList()));

		// The number of column axis must be increased by 1
		Assert.assertEquals(5, manager.getColumnCount());

		checkUndoRedo(1, expectedColumnNames);
	}

	/**
	 * This tests the addition of one element outside the column source objects.
	 * The table column axis must not be changed.
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testAddElementOutsideColumnSourcePackages(){
		initTest();

		// Add a new class inside the Package1
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(rootModel);
		ICommand cmd = provider.getEditCommand(new CreateElementRequest(rootModel, UMLElementTypes.CLASS));
		Assert.assertTrue(cmd.canExecute());
		editingDomain.getCommandStack().execute((new GMFtoEMFCommandWrapper(cmd)));

		// The column header names must be the same
		Assert.assertEquals(initColumnNames, getAllAxisNameString(manager.getVerticalFilterList()));

		// The number of column axis must not be changed
		Assert.assertEquals(4, manager.getColumnCount());

		checkUndoRedo(0, initColumnNames);
	}

	/**
	 * This tests the delete of one element in a column source package.
	 * The corresponding column axis must be removed from the table.
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testDeleteElementInsideAColumnSourcePackage() {
		initTest();

		Class class1 = (Class) package1ColumnSource.getPackagedElement("Class1");
		Assert.assertNotNull(class1);

		// Delete the Class1 element inside the Package2
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(class1);
		ICommand cmd = provider.getEditCommand(new DestroyElementRequest(class1, false));
		Assert.assertTrue(cmd.canExecute());
		editingDomain.getCommandStack().execute((new GMFtoEMFCommandWrapper(cmd)));

		// The column corresponding to Class1 element is deleted
		String expectedColumnNames = "Class2, Component1, Component2";
		Assert.assertEquals(expectedColumnNames, getAllAxisNameString(manager.getVerticalFilterList()));

		// The number of column axis must be decreased by 1
		Assert.assertEquals(3, manager.getColumnCount());

		checkUndoRedo(-1, expectedColumnNames);
	}

	/**
	 * This tests the delete of one element outside the column source objects.
	 * The table column axis must not be changed.
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testDeleteElementOutsideColumnSourcePackages() {
		initTest();

		Enumeration enum1 = (Enumeration) rootModel.getPackagedElement("Enumeration1");
		Assert.assertNotNull(enum1);

		// Delete the Enumeration1 element inside the Root model
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(enum1);
		ICommand cmd = provider.getEditCommand(new DestroyElementRequest(enum1, false));
		Assert.assertTrue(cmd.canExecute());
		editingDomain.getCommandStack().execute((new GMFtoEMFCommandWrapper(cmd)));

		// The column header names must be the same
		Assert.assertEquals(initColumnNames, getAllAxisNameString(manager.getVerticalFilterList()));

		// The number of column axis must not be changed
		Assert.assertEquals(initNumberColumn, manager.getColumnCount());

		checkUndoRedo(0, initColumnNames);
	}

	/**
	 * This tests the delete of one column source object package.
	 * All column axis corresponding to the deleted one must also be removed from the table.
	 */
	@Test
	@ActiveTable("Relationship Generic Matrix")
	public void testDeleteAColumnSourcePackage() {
		initTest();

		// Delete the Package2 column source
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(package2ColumnSource);
		ICommand cmd = provider.getEditCommand(new DestroyElementRequest(package2ColumnSource, false));
		Assert.assertTrue(cmd.canExecute());
		editingDomain.getCommandStack().execute((new GMFtoEMFCommandWrapper(cmd)));

		// There are only 2 columns corresponding to elements in the Package1
		String expectedColumnNames = "Class1, Class2";
		Assert.assertEquals(expectedColumnNames, getAllAxisNameString(manager.getVerticalFilterList()));

		// The number of column axis must be decreased by 2
		Assert.assertEquals(2, manager.getColumnCount());

		checkUndoRedo(-2, expectedColumnNames);
	}

	/**
	 * Check the undo/redo operation.
	 *
	 * @param numberColumnAffected The number of column is affected by the command, 0 for unchanged, >0 for number columns are added, <0 for number columns are deleted
	 * @param expectedColumnNamesString The expected column names string
	 */
	private void checkUndoRedo(final int numberColumnAffected, final String expectedColumnNamesString) {
		// Check the undo
		editingDomain.getCommandStack().undo();
		Assert.assertEquals(initColumnNames, getAllAxisNameString(manager.getVerticalFilterList()));
		Assert.assertEquals(initNumberColumn, manager.getColumnCount());

		// Check the redo
		editingDomain.getCommandStack().redo();
		Assert.assertEquals(expectedColumnNamesString, getAllAxisNameString(manager.getVerticalFilterList()));
		Assert.assertEquals(initNumberColumn + numberColumnAffected, manager.getColumnCount());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSourcePath() {
		return "resources/updateMatrixAxisColumnTests/"; //$NON-NLS-1$
	}
}
