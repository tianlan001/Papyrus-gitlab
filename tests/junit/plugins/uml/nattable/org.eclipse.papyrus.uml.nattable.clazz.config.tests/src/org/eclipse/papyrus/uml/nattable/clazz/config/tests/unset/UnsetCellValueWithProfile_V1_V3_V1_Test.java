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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectCellCommand;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.ui.util.SelectionHelper;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vincent Lorenzo
 *
 */
@PluginResource("resources/unset/UnsetCellValueWithProfile_V1_V3_V1_Test.di") // NON-NLS-1
public class UnsetCellValueWithProfile_V1_V3_V1_Test extends AbstractUnsetCellValueTest {

	private Stereotype stereotype;

	private static final String CLASS_1 = "Class1";// NON-NLS-1 //$NON-NLS-1$

	private static final String CLASS_1_ATTRIBUTE_1 = "Attribute1";// NON-NLS-1 //$NON-NLS-1$

	private static final String CLASS_1_ATTRIBUTE_2 = "Attribute2";// NON-NLS-1 //$NON-NLS-1$

	private static final int ROW_CLASS_1_INDEX = 1;

	private static final int ROW_CLASS_1_ATTRIBUTE_1_INDEX = 3;
	
	private static final int ROW_CLASS_1_ATTRIBUTE_2_INDEX = 4;


	private static final int COLUMN_VISIBILITY_INDEX = 0;

	private static final int COLUMN_OWNED_ATTRIBUTES_INDEX = 1;

	private static final int COLUMN_PRIORITY_INDEX = 2;

	private static final int COLUMN_SINGLE_INT_VALUE_INDEX = 3;

	private static final int COLUMN_SINGLE_STRING_PROP_INDEX = 4;

	private static final String PRIORITY = "p";// NON-NLS-1 //$NON-NLS-1$

	private static final String SINGLE_INT_VALUE = "singleIntValue";// NON-NLS-1 //$NON-NLS-1$

	private static final String SINGLE_STRING_PROP = "singleStringProp";// NON-NLS-1 //$NON-NLS-1$

	private static final int DEFAULT_SINGLE_INT_VALUE = 42;

	private static final String DEFAULT_SINGLE_STRING_PROP = "singlePropDefaultValue";// NON-NLS-1 //$NON-NLS-1$

	private static final String MEDIUM = "medium"; //$NON-NLS-1$
	
	private static final String LOW = "low"; //$NON-NLS-1$

	private Model root;
	
	private Class class1;
	
	private Property prop1Class1;
	
	private Property prop2Class1;

	// TODO : check the tested model
	@Test
	@ActiveTable("ClassTreeTable")
	public void testResetClassVisibility() {
		startTest();
		fixture.flushDisplayEvents();
		SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		int rowPos = layer.getRowPositionByIndex(ROW_CLASS_1_INDEX);
		int colPos = layer.getColumnPositionByIndex(COLUMN_VISIBILITY_INDEX);

		boolean done = natTable.doCommand(new SelectCellCommand(layer, colPos, rowPos, false, false));
		Assert.assertTrue("The selection command doesn't work", done); //$NON-NLS-1$
		// layer.setSelectedCell(columnPosition, natTable.getRowIndexByPosition(rowPosition));
		fixture.flushDisplayEvents();
		ISelection selection = SelectionHelper.getCurrentSelection();
		Assert.assertTrue(selection instanceof IStructuredSelection);
		Assert.assertTrue("The selection must no be empty", !selection.isEmpty()); //$NON-NLS-1$
		checkUnsetCellValueHandlerStatusAndExecuteCommandIfRequired(true);

		Assert.assertEquals(VisibilityKind.PUBLIC_LITERAL, class1.getVisibility());
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());
		Assert.assertNotNull(domain);
		domain.getCommandStack().undo();
		Assert.assertEquals(VisibilityKind.PROTECTED_LITERAL, class1.getVisibility());

		domain.getCommandStack().redo();
		Assert.assertEquals(VisibilityKind.PUBLIC_LITERAL, class1.getVisibility());
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void testResetStereotypePropertyVisibility() {
		startTest();
		fixture.flushDisplayEvents();
		SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		int rowPos = layer.getRowPositionByIndex(ROW_CLASS_1_INDEX);
		int colPos = layer.getColumnPositionByIndex(COLUMN_PRIORITY_INDEX);

		boolean done = natTable.doCommand(new SelectCellCommand(layer, colPos, rowPos, false, false));
		Assert.assertTrue("The selection command doesn't work", done); //$NON-NLS-1$
		// layer.setSelectedCell(columnPosition, natTable.getRowIndexByPosition(rowPosition));
		fixture.flushDisplayEvents();
		ISelection selection = SelectionHelper.getCurrentSelection();
		Assert.assertTrue(selection instanceof IStructuredSelection);
		Assert.assertTrue("The selection must no be empty", !selection.isEmpty()); //$NON-NLS-1$
		checkUnsetCellValueHandlerStatusAndExecuteCommandIfRequired(true);

		Assert.assertEquals(MEDIUM, ((EnumerationLiteral) class1.getValue(stereotype, PRIORITY)).getName());
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());
		Assert.assertNotNull(domain);
		domain.getCommandStack().undo();
		Assert.assertEquals(LOW, ((EnumerationLiteral) class1.getValue(stereotype, PRIORITY)).getName());

		domain.getCommandStack().redo();
		Assert.assertEquals(MEDIUM, ((EnumerationLiteral) class1.getValue(stereotype, PRIORITY)).getName());
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void testResetSingleIntValue() {
		startTest();
		int previousValue = ((Integer) class1.getValue(stereotype, SINGLE_INT_VALUE)).intValue();
		fixture.flushDisplayEvents();
		SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		int rowPos = layer.getRowPositionByIndex(ROW_CLASS_1_INDEX);
		int colPos = layer.getColumnPositionByIndex(COLUMN_SINGLE_INT_VALUE_INDEX);

		boolean done = natTable.doCommand(new SelectCellCommand(layer, colPos, rowPos, false, false));
		Assert.assertTrue("The selection command doesn't work", done); //$NON-NLS-1$
		// layer.setSelectedCell(columnPosition, natTable.getRowIndexByPosition(rowPosition));
		fixture.flushDisplayEvents();
		ISelection selection = SelectionHelper.getCurrentSelection();
		Assert.assertTrue(selection instanceof IStructuredSelection);
		Assert.assertTrue("The selection must no be empty", !selection.isEmpty()); //$NON-NLS-1$
		checkUnsetCellValueHandlerStatusAndExecuteCommandIfRequired(true);

		Assert.assertEquals(DEFAULT_SINGLE_INT_VALUE, ((Integer) class1.getValue(stereotype, SINGLE_INT_VALUE)).intValue());
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());
		Assert.assertNotNull(domain);
		domain.getCommandStack().undo();
		Assert.assertEquals(previousValue, ((Integer) class1.getValue(stereotype, SINGLE_INT_VALUE)).intValue());

		domain.getCommandStack().redo();
		Assert.assertEquals(DEFAULT_SINGLE_INT_VALUE, ((Integer) class1.getValue(stereotype, SINGLE_INT_VALUE)).intValue());
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void testResetSingleString() {
		startTest();
		String previousValue = ((String) class1.getValue(stereotype, SINGLE_STRING_PROP));
		fixture.flushDisplayEvents();
		SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		int rowPos = layer.getRowPositionByIndex(ROW_CLASS_1_INDEX);
		int colPos = layer.getColumnPositionByIndex(COLUMN_SINGLE_STRING_PROP_INDEX);

		boolean done = natTable.doCommand(new SelectCellCommand(layer, colPos, rowPos, false, false));
		Assert.assertTrue("The selection command doesn't work", done); //$NON-NLS-1$
		// layer.setSelectedCell(columnPosition, natTable.getRowIndexByPosition(rowPosition));
		fixture.flushDisplayEvents();
		ISelection selection = SelectionHelper.getCurrentSelection();
		Assert.assertTrue(selection instanceof IStructuredSelection);
		Assert.assertTrue("The selection must no be empty", !selection.isEmpty()); //$NON-NLS-1$
		checkUnsetCellValueHandlerStatusAndExecuteCommandIfRequired(true);

		Assert.assertEquals(DEFAULT_SINGLE_STRING_PROP, ((String) class1.getValue(stereotype, SINGLE_STRING_PROP)));
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());
		Assert.assertNotNull(domain);
		domain.getCommandStack().undo();
		Assert.assertEquals(previousValue, ((String) class1.getValue(stereotype, SINGLE_STRING_PROP)));

		domain.getCommandStack().redo();
		Assert.assertEquals(DEFAULT_SINGLE_STRING_PROP, ((String) class1.getValue(stereotype, SINGLE_STRING_PROP)));
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void testResetOwnedAttributes() {
		startTest();
		fixture.flushDisplayEvents();
		SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		int rowPos = layer.getRowPositionByIndex(ROW_CLASS_1_INDEX);
		int colPos = layer.getColumnPositionByIndex(COLUMN_OWNED_ATTRIBUTES_INDEX);

		boolean done = natTable.doCommand(new SelectCellCommand(layer, colPos, rowPos, false, false));
		Assert.assertTrue("The selection command doesn't work", done); //$NON-NLS-1$
		// layer.setSelectedCell(columnPosition, natTable.getRowIndexByPosition(rowPosition));
		fixture.flushDisplayEvents();
		ISelection selection = SelectionHelper.getCurrentSelection();
		Assert.assertTrue(selection instanceof IStructuredSelection);
		Assert.assertTrue("The selection must no be empty", !selection.isEmpty()); //$NON-NLS-1$
		checkUnsetCellValueHandlerStatusAndExecuteCommandIfRequired(true);
		fixture.flushDisplayEvents();
		Assert.assertTrue(class1.getOwnedAttributes().isEmpty());
		List<?> rows = manager.getRowElementsList();
		Assert.assertEquals(2, rows.size());
		for (Object current : rows) {
			Object representedElement = AxisUtils.getRepresentedElement(current);
			Assert.assertFalse(representedElement instanceof Property);
			if (representedElement == class1 && current instanceof ITreeItemAxis) {
				Assert.assertEquals(0, ((ITreeItemAxis) current).getChildren().size());
			}
		}

		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());
		Assert.assertNotNull(domain);
		domain.getCommandStack().undo();

		fixture.flushDisplayEvents();
		Assert.assertEquals(2, class1.getOwnedAttributes().size());
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		rows = manager.getRowElementsList();
		Assert.assertEquals(5, rows.size());
		
		domain.getCommandStack().redo();


		fixture.flushDisplayEvents();
		Assert.assertTrue(class1.getOwnedAttributes().isEmpty());
		rows = manager.getRowElementsList();
		Assert.assertEquals(2, rows.size());
		for (Object current : rows) {
			Object representedElement = AxisUtils.getRepresentedElement(current);
			Assert.assertFalse(representedElement instanceof Property);
			if (representedElement == class1 && current instanceof ITreeItemAxis) {
				Assert.assertEquals(0, ((ITreeItemAxis) current).getChildren().size());
			}
		}
	}




	/**
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.unset.AbstractUnsetCellValueTest#startTest()
	 *
	 */
	@Override
	protected void startTest() {
		super.startTest();
		Assert.assertEquals("For this test we must have 5 columns", 5, manager.getColumnCount()); //$NON-NLS-1$
		EObject context = manager.getTable().getContext();
		Assert.assertTrue(context instanceof Model);
		root = (Model) context;
		class1 = (Class) root.getMember(CLASS_1);

		Assert.assertNotNull(class1);

		prop1Class1 = (Property) class1.getMember(CLASS_1_ATTRIBUTE_1);
		prop2Class1 = (Property) class1.getMember(CLASS_1_ATTRIBUTE_2);
		Assert.assertNotNull(prop1Class1);
		Assert.assertNotNull(prop2Class1);


		for (int i = 0; i < manager.getRowElementsList().size(); i++) {
			Object element = AxisUtils.getRepresentedElement(manager.getRowElement(i));
			if (element == class1) {
				Assert.assertEquals(ROW_CLASS_1_INDEX, i);
			}
			if (element == prop1Class1) {
				Assert.assertEquals(ROW_CLASS_1_ATTRIBUTE_1_INDEX, i);
			}

			if (element == prop2Class1) {
				Assert.assertEquals(ROW_CLASS_1_ATTRIBUTE_2_INDEX, i);
			}
		}

		stereotype = class1.getAppliedStereotypes().get(0);
	}


}
