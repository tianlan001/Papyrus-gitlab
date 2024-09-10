/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.unset;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectCellCommand;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.ui.util.SelectionHelper;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vincent Lorenzo
 *
 */
@PluginResource("resources/unset/UnsetCellValue_H1_H3_H1_Test.di") // NON-NLS-1)
public class UnsetCellValue_H1_H3_H1_Test extends AbstractUnsetCellValueTest {


	private static final String CLASS_1 = "Class1"; //$NON-NLS-1$

	private static final String CLASS_2 = "Class2"; //$NON-NLS-1$

	private static final String CLASS_1_ATTRIBUTE_1 = "Class1Attribute1"; //$NON-NLS-1$

	private static final String CLASS_2_ATTRIBUTE_1 = "Class2Attribute1"; //$NON-NLS-1$

	private int indexOfClass1 = -1;
	private int indexOfClass2 = -1;
	private int indexOfprop1Class1 = -1;
	private int indexOfprop1Class2 = -1;

	private int nameColumnIndex = 0;

	private static final int EXPECTED_CLASS1_INDEX = 1;
	private static final int EXPECTED_CLASS2_INDEX = 4;
	private static final int EXPECTED_CLASS_1_ATTRIBUTE_1_INDEX = 3;
	private static final int EXPECTED_CLASS_2_ATTRIBUTE_1_INDEX = 6;


	private Model root;
	private Class class1;
	private Class class2;
	private Property prop1Class1;
	private Property prop1Class2;

	// TODO : check the tested model
	@Test
	@ActiveTable("ClassTreeTable")
	public void testReset() {
		startTest();

		// check for element nameS
		checkNameFor(class1, CLASS_1, nameColumnIndex, indexOfClass1);
		checkNameFor(class2, CLASS_2, nameColumnIndex, indexOfClass2);
		checkNameFor(prop1Class1, CLASS_1_ATTRIBUTE_1, nameColumnIndex, indexOfprop1Class1);
		checkNameFor(prop1Class2, CLASS_2_ATTRIBUTE_1, nameColumnIndex, indexOfprop1Class2);

		// check we null selection
		SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		layer.clear();
		fixture.flushDisplayEvents();
		ISelection selection = SelectionHelper.getCurrentSelection();
		Assert.assertTrue("The selection (even if empty) must be a TableSelectionProvider", selection instanceof TableStructuredSelection); //$NON-NLS-1$
		Assert.assertEquals("The empty selection must have table context as selection", manager.getTable().getContext(), ((TableStructuredSelection)selection).getFirstElement()); //$NON-NLS-1$
		checkUnsetCellValueHandlerStatusAndExecuteCommandIfRequired(false);
	}

	/**
	 * 
	 * @param namedElement
	 *            a named element
	 * @param expectedName
	 *            the expected name
	 * @param columnPosition
	 *            the column index
	 * @param rowPosition
	 *            the row index
	 */
	protected void checkNameFor(NamedElement namedElement, String expectedName, int columnPosition, int rowPosition) {
		fixture.flushDisplayEvents();
		SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		int rowPos = layer.getRowPositionByIndex(rowPosition);
		int colPos = layer.getColumnPositionByIndex(columnPosition);

		boolean done = natTable.doCommand(new SelectCellCommand(layer, colPos, rowPos, false, false));
		Assert.assertTrue("The selection command doesn't work", done); //$NON-NLS-1$
		// layer.setSelectedCell(columnPosition, natTable.getRowIndexByPosition(rowPosition));
		fixture.flushDisplayEvents();
		ISelection selection = SelectionHelper.getCurrentSelection();
		Assert.assertTrue(selection instanceof IStructuredSelection);
		Assert.assertTrue("The selection must no be empty", !selection.isEmpty()); //$NON-NLS-1$
		checkUnsetCellValueHandlerStatusAndExecuteCommandIfRequired(true);

		Assert.assertEquals(null, namedElement.getName());
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());
		Assert.assertNotNull(domain);
		domain.getCommandStack().undo();
		Assert.assertEquals(expectedName, namedElement.getName());

		domain.getCommandStack().redo();
		Assert.assertEquals(null, namedElement.getName());
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.unset.AbstractUnsetCellValueTest#startTest()
	 *
	 */
	@Override
	protected void startTest() {
		super.startTest();
		Assert.assertEquals("For this test we must have only one column", 1, manager.getColumnCount()); //$NON-NLS-1$
		EObject context = manager.getTable().getContext();
		Assert.assertTrue(context instanceof Model);
		root = (Model) context;
		class1 = (Class) root.getMember(CLASS_1);
		class2 = (Class) root.getMember(CLASS_2);
		Assert.assertNotNull(class1);
		Assert.assertNotNull(class2);
		prop1Class1 = (Property) class1.getMember(CLASS_1_ATTRIBUTE_1);
		prop1Class2 = (Property) class2.getMember(CLASS_2_ATTRIBUTE_1);
		Assert.assertNotNull(prop1Class1);
		Assert.assertNotNull(prop1Class2);


		for (int i = 0; i < manager.getRowElementsList().size(); i++) {
			Object element = AxisUtils.getRepresentedElement(manager.getRowElement(i));
			if (element == class1) {
				indexOfClass1 = i;
			}
			if (element == class2) {
				indexOfClass2 = i;
			}
			if (element == prop1Class1) {
				indexOfprop1Class1 = i;
			}

			if (element == prop1Class2) {
				indexOfprop1Class2 = i;
			}
		}

		Assert.assertEquals(EXPECTED_CLASS1_INDEX, indexOfClass1);
		Assert.assertEquals(EXPECTED_CLASS_1_ATTRIBUTE_1_INDEX, indexOfprop1Class1);

		Assert.assertEquals(EXPECTED_CLASS2_INDEX, indexOfClass2);
		Assert.assertEquals(EXPECTED_CLASS_2_ATTRIBUTE_1_INDEX, indexOfprop1Class2);

	}


}
