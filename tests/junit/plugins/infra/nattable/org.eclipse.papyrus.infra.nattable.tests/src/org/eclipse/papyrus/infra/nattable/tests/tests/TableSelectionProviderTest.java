/*****************************************************************************
 * Copyright (c) 2014, 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 517617, 532452 (just rewrite the test to use a fixture)
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.tests.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.print.command.TurnViewportOffCommand;
import org.eclipse.nebula.widgets.nattable.print.command.TurnViewportOnCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectCellCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author Vincent Lorenzo
 *
 *         This class tests the selection provider of the table
 *
 */
@PluginResource("resources/model.di")
public class TableSelectionProviderTest extends AbstractPapyrusTest {
	protected Model root;

	private NamedElement class1;

	private Package _package;

	private NamedElement nestedClass1;

	private NamedElement nestedClass2;

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	@Before
	public void init() {
		this.root = (Model) fixture.getModel();
		this.class1 = this.root.getMember("Class1");
		this._package = (Package) this.root.getMember("Package");
		this.nestedClass1 = this._package.getMember("NestedClass1");
		this.nestedClass2 = this._package.getMember("NestedClass2");
	}

	/**
	 * test the selection of a single row
	 */
	@Test
	@ActiveTable("GenericTable0")
	public void testRowSelection() {
		final INattableModelManager tableManager = this.fixture.getActiveTableManager();
		final NatTable nattable = tableManager.getAdapter(NatTable.class);
		Assert.assertTrue("The selection (even if empty) must be a TableSelectionProvider", getCurrentSelection() instanceof TableStructuredSelection); //$NON-NLS-1$
		Assert.assertEquals("The empty selection must have table context as selection", tableManager.getTable().getContext(), ((TableStructuredSelection) getCurrentSelection()).getFirstElement()); //$NON-NLS-1$
		nattable.doCommand(new TurnViewportOffCommand());
		nattable.doCommand(new SelectRowsCommand(nattable, 2, 3, false, false));// select the row representing class1
		nattable.doCommand(new TurnViewportOnCommand());
		final List<Object> wantedSelection = new ArrayList<>();
		wantedSelection.add(this.class1);
		verifyCurrentSelection(wantedSelection);
	}

	/**
	 * Test the selection of several rows
	 */
	@Test
	@ActiveTable("GenericTable0")
	public void test_RowSelectionWithCTRL() {
		final INattableModelManager tableManager = this.fixture.getActiveTableManager();
		final NatTable nattable = tableManager.getAdapter(NatTable.class);
		Assert.assertTrue("The selection (even if empty) must be a TableSelectionProvider", getCurrentSelection() instanceof TableStructuredSelection); //$NON-NLS-1$
		Assert.assertEquals("The empty selection must have table context as selection", tableManager.getTable().getContext(), ((TableStructuredSelection) getCurrentSelection()).getFirstElement()); //$NON-NLS-1$
		nattable.doCommand(new TurnViewportOffCommand());
		nattable.doCommand(new SelectRowsCommand(nattable, 2, 3, false, false));// select the row representing class1
		nattable.doCommand(new SelectRowsCommand(nattable, 2, 5, false, true));// select the row representing package
		nattable.doCommand(new TurnViewportOnCommand());
		final List<Object> wantedSelection = new ArrayList<>();
		wantedSelection.add(this._package);
		wantedSelection.add(this.class1);
		verifyCurrentSelection(wantedSelection);
	}

	/**
	 * test the selection of one row, followed by the selection of a cell, with CTRL
	 */
	@Test
	@ActiveTable("GenericTable0")
	public void test_RowThenCellSelection() {
		final INattableModelManager tableManager = this.fixture.getActiveTableManager();
		final NatTable nattable = tableManager.getAdapter(NatTable.class);
		Assert.assertTrue("The selection (even if empty) must be a TableSelectionProvider", getCurrentSelection() instanceof TableStructuredSelection); //$NON-NLS-1$
		Assert.assertEquals("The empty selection must have table context as selection", tableManager.getTable().getContext(), ((TableStructuredSelection) getCurrentSelection()).getFirstElement()); //$NON-NLS-1$
		nattable.doCommand(new TurnViewportOffCommand());
		nattable.doCommand(new SelectRowsCommand(nattable, 2, 3, false, false));// select the row representing class1
		nattable.doCommand(new SelectCellCommand(nattable, 5, 5, false, true));// select the cell package/members
		nattable.doCommand(new TurnViewportOnCommand());
		final List<Object> wantedSelection = new ArrayList<>();
		wantedSelection.add(this.class1);
		wantedSelection.add(this.nestedClass1);
		wantedSelection.add(this.nestedClass2);
		verifyCurrentSelection(wantedSelection);
	}

	/**
	 * test the selection of one cell, followed by the selection of one row, with CTRL
	 */
	@Test
	@ActiveTable("GenericTable0")
	public void test_CellThenRowSelection() {
		final INattableModelManager tableManager = this.fixture.getActiveTableManager();
		final NatTable nattable = tableManager.getAdapter(NatTable.class);
		Assert.assertTrue("The selection (even if empty) must be a TableSelectionProvider", getCurrentSelection() instanceof TableStructuredSelection); //$NON-NLS-1$
		Assert.assertEquals("The empty selection must have table context as selection", tableManager.getTable().getContext(), ((TableStructuredSelection) getCurrentSelection()).getFirstElement()); //$NON-NLS-1$
		nattable.doCommand(new TurnViewportOffCommand());
		nattable.doCommand(new SelectCellCommand(nattable, 5, 5, false, false));// select the cell package/members
		nattable.doCommand(new SelectRowsCommand(nattable, 2, 3, false, true));// select the row representing class1
		nattable.doCommand(new TurnViewportOnCommand());
		final List<Object> wantedSelection = new ArrayList<>();
		wantedSelection.add(this.class1);
		wantedSelection.add(this.nestedClass1);
		wantedSelection.add(this.nestedClass2);
		verifyCurrentSelection(wantedSelection);
	}

	/**
	 *
	 * @param wantedSelection
	 *            compare the wanted selection and the current selection
	 */
	protected void verifyCurrentSelection(final List<Object> wantedSelection) {
		ISelection selection = getCurrentSelection();
		if (selection instanceof IStructuredSelection) {
			List<?> currentSelection = ((IStructuredSelection) selection).toList();
			Assert.assertEquals(wantedSelection.size(), currentSelection.size());
			for (Object current : currentSelection) {
				Assert.assertTrue(wantedSelection.contains(current));
			}
		}
	}

	/**
	 *
	 * @return
	 *         the current eclipse selection
	 */
	protected ISelection getCurrentSelection() {
		return getSelectionService().getSelection();
	}

	/**
	 *
	 * @return
	 *         the selection service
	 */
	protected ISelectionService getSelectionService() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
	}

}
