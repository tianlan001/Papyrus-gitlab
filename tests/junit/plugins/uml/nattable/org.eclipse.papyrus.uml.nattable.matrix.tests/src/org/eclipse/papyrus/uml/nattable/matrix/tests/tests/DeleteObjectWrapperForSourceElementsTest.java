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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 525245
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.tests.tests;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the deletion of object wrappers when source elements are removed in Model Explorer as described in the bug 525245.
 */
@SuppressWarnings("nls")
@PluginResource("resources/deleteObjectWrapperForSourceElementsTest/DeleteObjectWrapperForSourceElements.di")
public class DeleteObjectWrapperForSourceElementsTest extends AbstractTableTest {

	/** The Package1 package. */
	private Package package1 = null;

	/** The Package2 as column source. */
	private Package package2ColumnSource = null;

	/** The Package3 as column source. */
	private Package package3ColumnSource = null;

	/** The Package4 as row source. */
	private Package package4RowSource = null;

	/** The initial number column sources. */
	private int initNumberColumnSources;

	/** The initial number row sources. */
	private int initNumberRowSources;

	/** The transactional editing domain. */
	private TransactionalEditingDomain editingDomain = null;

	/** The papyrus table. */
	private Table table = null;

	/** The current column axis provider. */
	private IMasterAxisProvider currentColumnAxisProvider = null;

	/** The current row axis provider. */
	private IMasterAxisProvider currentRowAxisProvider = null;

	/**
	 * Default constructor.
	 */
	public DeleteObjectWrapperForSourceElementsTest() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initTest() {
		super.initTest();

		table = this.manager.getTable();
		Assert.assertNotNull(table);

		Assert.assertTrue("The manager must be a matrix nattable model manager", this.manager instanceof IMatrixTableWidgetManager);
		editingDomain = fixture.getEditingDomain();
		Model rootModel = (Model) fixture.getModel();

		// Package1 contains others packages
		package1 = (Package) rootModel.getPackagedElement("Package1");
		Assert.assertNotNull(package1);

		// Package2 and Package3 (each has 2 elements) are used as column source axis
		package2ColumnSource = package1.getNestedPackage("Package2");
		Assert.assertNotNull(package2ColumnSource);
		package3ColumnSource = package1.getNestedPackage("Package3");
		Assert.assertNotNull(package3ColumnSource);

		// The number column sources must be 2
		currentColumnAxisProvider = (IMasterAxisProvider) table.getCurrentColumnAxisProvider();
		initNumberColumnSources = currentColumnAxisProvider.getSources().size();
		Assert.assertEquals(2, initNumberColumnSources);

		// Package4 with 2 elements is used as row source axis
		package4RowSource = package1.getNestedPackage("Package4");
		Assert.assertNotNull(package4RowSource);

		// The number of row sources must be 2
		currentRowAxisProvider = (IMasterAxisProvider) table.getCurrentRowAxisProvider();
		initNumberRowSources = currentRowAxisProvider.getSources().size();
		Assert.assertEquals(2, initNumberRowSources);
	}

	/**
	 * This tests the delete of one element in a row source package.
	 * The row object wrapper must be deleted too.
	 */
	@Test
	@ActiveTable("RelationshipGenericMatrix")
	public void testDeleteARowSourceElement() {
		initTest();

		final Interface interface1 = (Interface) package4RowSource.getPackagedElement("Interface1");
		Assert.assertNotNull(interface1);

		// Delete the Interface1 element inside the Package4
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(interface1);
		final ICommand cmd = provider.getEditCommand(new DestroyElementRequest(interface1, false));
		Assert.assertTrue(cmd.canExecute());
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));

		// The number row sources must be decreased by 1 because the corresponding row object wrapper is also deleted
		Assert.assertEquals(1, currentRowAxisProvider.getSources().size());
		checkUndoRedo(0, -1);
	}

	/**
	 * This tests the delete of one element as a column source package.
	 * The column object wrapper must be deleted too.
	 */
	@Test
	@ActiveTable("RelationshipGenericMatrix")
	public void testDeleteAColumnSourcePackage() {
		initTest();

		// Delete the Package3 which is selected as a column source object
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(package3ColumnSource);
		final ICommand cmd = provider.getEditCommand(new DestroyElementRequest(package3ColumnSource, false));
		Assert.assertTrue(cmd.canExecute());
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));

		// The number column sources must be decreased by 1 because the corresponding column object wrapper is also deleted
		Assert.assertEquals(1, currentColumnAxisProvider.getSources().size());
		checkUndoRedo(-1, 0);
	}

	/**
	 * Check the undo/redo operation.
	 *
	 * @param numberColumnSourcesAffected The number of source columns is affected by the command, 0 for unchanged, >0 for increment, <0 for decrement
	 * @param numberRowSourcesAffected The number of source rows is affected by the command, 0 for unchanged, >0 for increment, <0 for decrement
	 */
	private void checkUndoRedo(final int numberColumnSourcesAffected, final int numberRowSourcesAffected) {
		// Check the undo
		editingDomain.getCommandStack().undo();
		Assert.assertEquals(initNumberColumnSources, currentColumnAxisProvider.getSources().size());
		Assert.assertEquals(initNumberRowSources, currentRowAxisProvider.getSources().size());

		// Check the redo
		editingDomain.getCommandStack().redo();
		Assert.assertEquals(initNumberColumnSources + numberColumnSourcesAffected, currentColumnAxisProvider.getSources().size());
		Assert.assertEquals(initNumberRowSources + numberRowSourcesAffected, currentRowAxisProvider.getSources().size());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSourcePath() {
		return "resources/deleteObjectWrapperForSourceElementsTest/";
	}
}
