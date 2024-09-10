/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;



/**
 * These tests have been developed to check developement of bugs
 * 517617: [Table] Refresh method reset a bad selection in the table and move the scrollbar
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=517617
 *
 * 532452: [Table] Table editor shall not change the current selection when a column is resized.
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=532452
 *
 */
@PluginResource("resources/selection/test_selection_1.di")
public class TableSelectionTest extends AbstractPapyrusTest {

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	private Package root;

	private Class class1;
	private Class class2;
	private Property property2_1;
	private Class class3;

	@Before
	public void init() {
		root = fixture.getModel();
		Assert.assertNotNull(root);

		class1 = (Class) root.getPackagedElement("Class1"); //$NON-NLS-1$
		class2 = (Class) root.getPackagedElement("Class2"); //$NON-NLS-1$
		class3 = (Class) root.getPackagedElement("Class3"); //$NON-NLS-1$
		Assert.assertNotNull(class1);
		Assert.assertNotNull(class2);
		Assert.assertNotNull(class3);

		property2_1 = class2.getOwnedAttribute("Property2_1", null); //$NON-NLS-1$
		Assert.assertNotNull(property2_1);

	}

	/**
	 * this test check the selection in the expanded tree table with categories visible
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void one_row_selection() {
		final INattableModelManager manager = fixture.getActiveTableManager();
		Collection<Object> rowElementToSelect = new ArrayList<>();
		rowElementToSelect.add(class1);
		basicTest(rowElementToSelect, Collections.emptyList(), (NattableModelManager) manager);
	}

	/**
	 *
	 * @param expectedRowSelection
	 *            the expected row selection
	 * @param expectedColumnSelection
	 *            the expected column selection
	 * @param manager
	 *            the table manager
	 */
	protected void checkSelection(final Collection<?> expectedRowSelection, final Collection<?> expectedColumnSelection, final INattableModelManager manager) {
		fixture.flushDisplayEvents();
		TableStructuredSelection selection = manager.getSelectionInTable();
		Assert.assertEquals(expectedRowSelection.size() + expectedColumnSelection.size(), selection.size());
		final Collection<Object> unwantedSelectedElement = new ArrayList<>(selection.toList());
		unwantedSelectedElement.removeAll(expectedRowSelection);
		unwantedSelectedElement.removeAll(expectedColumnSelection);
		Assert.assertEquals(0, unwantedSelectedElement.size());

		TableSelectionWrapper wrapper = (TableSelectionWrapper) selection.getAdapter(TableSelectionWrapper.class);

		final SelectionLayer selectionLayer = manager.getBodyLayerStack().getSelectionLayer();

		Assert.assertEquals(expectedColumnSelection.size(), wrapper.getFullySelectedColumns().size());
		Assert.assertEquals(
				(selectionLayer.getColumnCount() * expectedRowSelection.size())
						+ (selectionLayer.getRowCount() * expectedColumnSelection.size())
						- (expectedRowSelection.size() * expectedColumnSelection.size()),
				wrapper.getSelectedCells().size());
		Assert.assertEquals(expectedRowSelection.size(), wrapper.getFullySelectedRows().size());
		Iterator<Entry<Integer, Object>> iter = wrapper.getFullySelectedRows().entrySet().iterator();

		while (iter.hasNext()) {
			Entry<Integer, Object> current = iter.next();
			int rowIndex = selectionLayer.getRowIndexByPosition(current.getKey());
			Assert.assertTrue("The index of selected element doesn't match the index of the element in the list", rowIndex == manager.getRowElementsList().indexOf(current.getValue())); //$NON-NLS-1$
			Assert.assertTrue("The selected element is not the expected one", expectedRowSelection.contains(AxisUtils.getRepresentedElement(current.getValue()))); //$NON-NLS-1$
		}

		iter = wrapper.getFullySelectedColumns().entrySet().iterator();

		while (iter.hasNext()) {
			Entry<Integer, Object> current = iter.next();
			int columnIndex = selectionLayer.getColumnIndexByPosition(current.getKey());
			Assert.assertTrue("The index of selected element doesn't match the index of the element in the list", columnIndex == manager.getColumnElementsList().indexOf(current.getValue())); //$NON-NLS-1$
			Assert.assertTrue("The selected element is not the expected one", expectedColumnSelection.contains(AxisUtils.getRepresentedElement(current.getValue()))); //$NON-NLS-1$
		}
	}

	/**
	 * this test check the selection in the tree table with categories visible
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void two_rows_selection() {
		final INattableModelManager manager = fixture.getActiveTableManager();

		Collection<Object> wantedRowSelection = new ArrayList<>();
		wantedRowSelection.add(class1);
		wantedRowSelection.add(class2);

		basicTest(wantedRowSelection, Collections.emptyList(), (NattableModelManager) manager);
	}

	/**
	 * This test checks a 2-rows selection after an element creation
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void two_rows_selection__create_new_element() {
		final INattableModelManager manager = fixture.getActiveTableManager();

		Collection<Object> wantedRowSelection = new ArrayList<>();
		wantedRowSelection.add(class1);
		wantedRowSelection.add(class2);

		basicTest(wantedRowSelection, Collections.emptyList(), (NattableModelManager) manager);

		CreateElementRequest request = new CreateElementRequest(fixture.getEditingDomain(), root, org.eclipse.papyrus.uml.service.types.element.UMLElementTypes.CLASS);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(root);
		org.eclipse.emf.common.command.Command create = GMFtoEMFCommandWrapper.wrap(edit.getEditCommand(request));

		int previousSize = root.getMembers().size();
		fixture.getEditingDomain().getCommandStack().execute(create);
		Assert.assertEquals(previousSize + 1, root.getMembers().size());
		fixture.flushDisplayEvents();

		checkSelection(wantedRowSelection, Collections.emptyList(), manager);
	}

	/**
	 * This method expands all rows, then select the wanted element and check the result
	 *
	 * @param rowElementToSelect
	 *            the rows to select
	 * @param columnElementToSelect
	 *            the column to select
	 * @param manager
	 *            the table manager
	 */
	protected void basicTest(final Collection<Object> rowElementToSelect, final Collection<Object> columnElementToSelect, final NattableModelManager manager) {
		((TreeNattableModelManager) manager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
		TableStructuredSelection selection = manager.getSelectionInTable();
		Assert.assertEquals("When nothing is selected, the selection must be the table context", root, selection.getFirstElement()); //$NON-NLS-1$

		Collection<Object> elementToReveal = new ArrayList<>(rowElementToSelect);
		elementToReveal.addAll(columnElementToSelect);
		manager.revealElement(elementToReveal);
		fixture.flushDisplayEvents();
		checkSelection(rowElementToSelect, columnElementToSelect, manager);
	}

	/**
	 * This tests checks a 2-rows selections after hidden categories
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void two_rows_selection__hide_all_categories() {
		final INattableModelManager manager = fixture.getActiveTableManager();

		Collection<Object> wantedRowSelection = new ArrayList<>();
		wantedRowSelection.add(class1);
		wantedRowSelection.add(class2);

		basicTest(wantedRowSelection, Collections.emptyList(), (NattableModelManager) manager);

		List<Integer> depthToHide = new ArrayList<>();
		depthToHide.add(Integer.valueOf(0));
		depthToHide.add(Integer.valueOf(1));
		depthToHide.add(Integer.valueOf(2));

		((TreeNattableModelManager) manager).hideShowRowCategories(depthToHide, Collections.emptyList());
		fixture.flushDisplayEvents();
		checkSelection(wantedRowSelection, Collections.emptyList(), manager);
	}

	/**
	 * This test checks a 2-rows selection after an element creation
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void two_rows_selection__hide_all_categories__create_new_element() {
		final INattableModelManager manager = fixture.getActiveTableManager();

		Collection<Object> wantedRowSelection = new ArrayList<>();
		wantedRowSelection.add(class1);
		wantedRowSelection.add(class2);

		basicTest(wantedRowSelection, Collections.emptyList(), (NattableModelManager) manager);
		List<Integer> depthToHide = new ArrayList<>();
		depthToHide.add(Integer.valueOf(0));
		depthToHide.add(Integer.valueOf(1));
		depthToHide.add(Integer.valueOf(2));
		((TreeNattableModelManager) manager).hideShowRowCategories(depthToHide, Collections.emptyList());
		fixture.flushDisplayEvents();

		CreateElementRequest request = new CreateElementRequest(fixture.getEditingDomain(), root, org.eclipse.papyrus.uml.service.types.element.UMLElementTypes.CLASS);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(root);
		org.eclipse.emf.common.command.Command create = GMFtoEMFCommandWrapper.wrap(edit.getEditCommand(request));

		int previousSize = root.getMembers().size();
		fixture.getEditingDomain().getCommandStack().execute(create);
		Assert.assertEquals(previousSize + 1, root.getMembers().size());
		fixture.flushDisplayEvents();

		checkSelection(wantedRowSelection, Collections.emptyList(), manager);
	}

	/**
	 * This test checks a 2-rows selection after a column creation
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void two_rows_selection__create_new_column() {
		final INattableModelManager manager = fixture.getActiveTableManager();

		Collection<Object> wantedRowSelection = new ArrayList<>();
		wantedRowSelection.add(class1);
		wantedRowSelection.add(class2);

		basicTest(wantedRowSelection, Collections.emptyList(), (NattableModelManager) manager);

		int previousNumberOfColumn = manager.getColumnCount();
		manager.addColumns(Collections.singletonList(UMLPackage.eINSTANCE.getNamedElement_QualifiedName()));

		fixture.flushDisplayEvents();
		Assert.assertEquals(previousNumberOfColumn + 1, manager.getColumnCount());
		checkSelection(wantedRowSelection, Collections.emptyList(), manager);
	}

	/**
	 * This test checks a 2-rows and 1-column selection after a column creation
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void two_rows_one_column_selection_hide_all_categories__create_new_element_1() {
		final INattableModelManager manager = fixture.getActiveTableManager();
		List<Integer> depthToHide = new ArrayList<>();
		depthToHide.add(Integer.valueOf(0));
		depthToHide.add(Integer.valueOf(1));
		depthToHide.add(Integer.valueOf(2));
		((TreeNattableModelManager) manager).hideShowRowCategories(depthToHide, Collections.emptyList());
		fixture.flushDisplayEvents();

		Collection<Object> wantedRowSelection = new ArrayList<>();
		wantedRowSelection.add(class2);
		wantedRowSelection.add(class3);

		Collection<Object> wantedColumnSelection = new ArrayList<>();
		wantedColumnSelection.add(UMLPackage.eINSTANCE.getNamedElement_Visibility());

		basicTest(wantedRowSelection, wantedColumnSelection, (NattableModelManager) manager);

		CreateElementRequest request = new CreateElementRequest(fixture.getEditingDomain(), root, org.eclipse.papyrus.uml.service.types.element.UMLElementTypes.CLASS);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(root);
		org.eclipse.emf.common.command.Command create = GMFtoEMFCommandWrapper.wrap(edit.getEditCommand(request));

		int previousSize = root.getMembers().size();
		fixture.getEditingDomain().getCommandStack().execute(create);
		Assert.assertEquals(previousSize + 1, root.getMembers().size());
		fixture.flushDisplayEvents();

		checkSelection(wantedRowSelection, wantedColumnSelection, manager);
	}

	/**
	 * This test checks a 2-rows and 1-column selection after a column creation
	 *
	 * This test is exactly the same than {@link #two_rows_one_column_selection__create_new_element_1()},
	 * but here we select the first column (instead of the second one).
	 * Hypothesis for this fail :
	 * <ul>
	 * <li>bug in NatTable when the first cell (col:0 and row:0) is selected</li>
	 * <li>bug due to hide categories : maybe in Papyrus, because we hide intermediate categories</li>
	 * </ul>
	 */
	@FailingTest
	@Test
	@ActiveTable("ClassTreeTable")
	public void two_rows_one_column_selection_hide_all_categories__create_new_element_2() {
		final INattableModelManager manager = fixture.getActiveTableManager();
		List<Integer> depthToHide = new ArrayList<>();
		depthToHide.add(Integer.valueOf(0));
		depthToHide.add(Integer.valueOf(1));
		depthToHide.add(Integer.valueOf(2));
		((TreeNattableModelManager) manager).hideShowRowCategories(depthToHide, Collections.emptyList());
		fixture.flushDisplayEvents();

		Collection<Object> wantedRowSelection = new ArrayList<>();
		wantedRowSelection.add(class2);
		wantedRowSelection.add(class3);

		Collection<Object> wantedColumnSelection = new ArrayList<>();
		wantedColumnSelection.add(UMLPackage.eINSTANCE.getNamedElement_Name());

		basicTest(wantedRowSelection, wantedColumnSelection, (NattableModelManager) manager);

		CreateElementRequest request = new CreateElementRequest(fixture.getEditingDomain(), root, org.eclipse.papyrus.uml.service.types.element.UMLElementTypes.CLASS);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(root);
		org.eclipse.emf.common.command.Command create = GMFtoEMFCommandWrapper.wrap(edit.getEditCommand(request));

		int previousSize = root.getMembers().size();
		fixture.getEditingDomain().getCommandStack().execute(create);
		Assert.assertEquals(previousSize + 1, root.getMembers().size());
		fixture.flushDisplayEvents();

		checkSelection(wantedRowSelection, wantedColumnSelection, manager);
	}

	/**
	 * This test checks a 2-rows and 1-column selection after a column creation
	 *
	 * This test is exactly the same than {@link #two_rows_one_column_selection__create_new_element_1()},
	 * but here we select the first column (instead of the second one).
	 * Hypothesis for this fail :
	 * <ul>
	 * <li>bug in NatTable when the first cell (col:0 and row:0) is selected</li>
	 * </ul>
	 */
	@FailingTest
	@Test
	@ActiveTable("ClassTreeTable")
	public void two_rows_one_column_selection__create_new_element() {
		final INattableModelManager manager = fixture.getActiveTableManager();

		Collection<Object> wantedRowSelection = new ArrayList<>();
		wantedRowSelection.add(class2);
		wantedRowSelection.add(class3);

		Collection<Object> wantedColumnSelection = new ArrayList<>();
		wantedColumnSelection.add(UMLPackage.eINSTANCE.getNamedElement_Name());

		basicTest(wantedRowSelection, wantedColumnSelection, (NattableModelManager) manager);

		CreateElementRequest request = new CreateElementRequest(fixture.getEditingDomain(), root, org.eclipse.papyrus.uml.service.types.element.UMLElementTypes.CLASS);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(root);
		org.eclipse.emf.common.command.Command create = GMFtoEMFCommandWrapper.wrap(edit.getEditCommand(request));

		int previousSize = root.getMembers().size();
		fixture.getEditingDomain().getCommandStack().execute(create);
		Assert.assertEquals(previousSize + 1, root.getMembers().size());
		fixture.flushDisplayEvents();

		checkSelection(wantedRowSelection, wantedColumnSelection, manager);
	}
}
