/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class allows to test the sort in the Hierarchical NatTable
 */
public class EditAxisClassTreeTableTest extends AbstractOpenTableTest {

	/**
	 * The file name of the papyrus project used.
	 */
	public static String fileName = "contents_sort_edit_model"; //$NON-NLS-1$

	/**
	 * The first class name in the model.
	 */
	private static final String FIRST_CLASS = "Class1"; //$NON-NLS-1$

	/**
	 * The second class name in the model.
	 */
	private static final String SECOND_CLASS = "Class2"; //$NON-NLS-1$

	/**
	 * The third class name in the model.
	 */
	private static final String THIRD_CLASS = "Class3"; //$NON-NLS-1$

	/**
	 * The first attribute name in the model.
	 */
	private static final String FIRST_PROPERTY = "Attribute11"; //$NON-NLS-1$

	/**
	 * The second attribute name in the model.
	 */
	private static final String SECOND_PROPERTY = "Attribute12"; //$NON-NLS-1$

	/**
	 * The third attribute name in the model.
	 */
	private static final String THIRD_PROPERTY = "Attribute21"; //$NON-NLS-1$

	/**
	 * The created attribute name in the model.
	 */
	private static final String FOURTH_PROPERTY = "Attribute31"; //$NON-NLS-1$

	/**
	 * Initialize the model.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Before
	public void initModel() throws Exception {
		initModel("ClassTreeTable", fileName, getBundle()); //$NON-NLS-1$
	};

	/**
	 * This allow to test the add row in a table.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void editRows() throws Exception {
		testOpenExistingTable(fileName, "ClassTreeTable"); //$NON-NLS-1$
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		ITreeNattableModelManager manager = (ITreeNattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);

		// Test the initial display
		// Check the row elements
		List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals(8, rowElements.size());
		checkInitialRows(rowElements);

		// Create a class to add in rows
		org.eclipse.uml2.uml.Package rootModel = getRootUMLModel();
		Class thirdClass = UMLFactory.eINSTANCE.createClass();
		thirdClass.setName(THIRD_CLASS);
		Property fourthProperty = UMLFactory.eINSTANCE.createProperty();
		fourthProperty.setName(FOURTH_PROPERTY);
		Assert.assertNotNull(thirdClass);
		final CompoundCommand compoundCommand = new CompoundCommand("Add class with property"); //$NON-NLS-1$
		compoundCommand.append(AddCommand.create(getTransactionalEditingDomain(), rootModel, UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, thirdClass));
		compoundCommand.append(AddCommand.create(getTransactionalEditingDomain(), thirdClass, UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE, fourthProperty));
		getTransactionalEditingDomain().getCommandStack().execute(compoundCommand);

		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);

		// The created class and property display
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(11, rowElements.size());
		checkAddedRow(rowElements);

		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(8, rowElements.size());
		checkInitialRows(rowElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(11, rowElements.size());
		checkAddedRow(rowElements);

		// Search the second class and remove it
		Class secondClass = null;
		for (final Element element : rootModel.getOwnedElements()) {
			if (element instanceof Class && ((Class) element).getName().equals(SECOND_CLASS)) {
				secondClass = (Class) element;
			}
		}
		Assert.assertNotNull(secondClass);
		final CompoundCommand removeClassCompoundCommand = new CompoundCommand();
		removeClassCompoundCommand.append(RemoveCommand.create(getTransactionalEditingDomain(), secondClass, UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE, secondClass.getOwnedAttributes().get(0)));
		removeClassCompoundCommand.append(RemoveCommand.create(getTransactionalEditingDomain(), rootModel, UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, secondClass));
		getTransactionalEditingDomain().getCommandStack().execute(removeClassCompoundCommand);

		// Check the removed class
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(8, rowElements.size());
		checkRemovedRow(rowElements);

		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(11, rowElements.size());
		checkAddedRow(rowElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(8, rowElements.size());
		checkRemovedRow(rowElements);
	}

	/**
	 * This allows to check the initial display rows.
	 * 
	 * @param rowElements
	 *            The row elements.
	 * @throws Exception
	 *             The exception.
	 */
	public void checkInitialRows(final List<?> rowElements) throws Exception {
		final IAxis firstRow = (IAxis) rowElements.get(1);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) firstRowRepresentedElement).getName());

		final IAxis thirdRow = (IAxis) rowElements.get(3);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_PROPERTY, ((Property) thirdRowRepresentedElement).getName());

		final IAxis fourthRow = (IAxis) rowElements.get(4);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) fourthRowRepresentedElement).eClass());
		Assert.assertEquals(SECOND_PROPERTY, ((Property) fourthRowRepresentedElement).getName());

		final IAxis fifthRow = (IAxis) rowElements.get(5);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) fifthRowRepresentedElement).eClass());
		Assert.assertEquals(SECOND_CLASS, ((org.eclipse.uml2.uml.Class) fifthRowRepresentedElement).getName());

		final IAxis seventhRow = (IAxis) rowElements.get(7);
		final Object seventhRowRepresentedElement = seventhRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) seventhRowRepresentedElement).eClass());
		Assert.assertEquals(THIRD_PROPERTY, ((Property) seventhRowRepresentedElement).getName());
	}

	/**
	 * This allows to check the columns after the added row.
	 * 
	 * @param rowElements
	 *            The row elements.
	 * @throws Exception
	 *             The exception.
	 */
	public void checkAddedRow(final List<?> rowElements) throws Exception {
		checkInitialRows(rowElements);

		final IAxis heightRow = (IAxis) rowElements.get(8);
		final Object heightRowRepresentedElement = heightRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) heightRowRepresentedElement).eClass());
		Assert.assertEquals(THIRD_CLASS, ((org.eclipse.uml2.uml.Class) heightRowRepresentedElement).getName());

		final IAxis tenthRow = (IAxis) rowElements.get(10);
		final Object tenthRowRepresentedElement = tenthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) tenthRowRepresentedElement).eClass());
		Assert.assertEquals(FOURTH_PROPERTY, ((Property) tenthRowRepresentedElement).getName());
	}

	/**
	 * This allows to check the columns after the removed row.
	 * 
	 * @param rowElements
	 *            The row elements.
	 * @throws Exception
	 *             The exception.
	 */
	public void checkRemovedRow(final List<?> rowElements) throws Exception {
		final IAxis firstRow = (IAxis) rowElements.get(1);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) firstRowRepresentedElement).getName());

		final IAxis thirdRow = (IAxis) rowElements.get(3);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_PROPERTY, ((Property) thirdRowRepresentedElement).getName());

		final IAxis fourthRow = (IAxis) rowElements.get(4);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) fourthRowRepresentedElement).eClass());
		Assert.assertEquals(SECOND_PROPERTY, ((Property) fourthRowRepresentedElement).getName());

		final IAxis fifthRow = (IAxis) rowElements.get(5);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) fifthRowRepresentedElement).eClass());
		Assert.assertEquals(THIRD_CLASS, ((org.eclipse.uml2.uml.Class) fifthRowRepresentedElement).getName());

		final IAxis seventhRow = (IAxis) rowElements.get(7);
		final Object seventhRowRepresentedElement = seventhRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) seventhRowRepresentedElement).eClass());
		Assert.assertEquals(FOURTH_PROPERTY, ((Property) seventhRowRepresentedElement).getName());
	}

	/**
	 * This allow to test the add column in a table.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void editColumns() throws Exception {
		testOpenExistingTable(fileName, "ClassTreeTable"); //$NON-NLS-1$
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);

		// Test the initial display
		// Check the row elements
		List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals(4, columnElements.size());
		checkInitialColumns(columnElements);

		// Add columns
		final Collection<Object> columnsToAdd = new ArrayList<Object>();
		columnsToAdd.add(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE);
		columnsToAdd.add(UMLPackage.Literals.TYPED_ELEMENT__TYPE);
		manager.addColumns(columnsToAdd);

		// Check the created columns display
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(6, columnElements.size());
		checkAddedColumns(columnElements);

		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(4, columnElements.size());
		checkInitialColumns(columnElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(6, columnElements.size());
		checkAddedColumns(columnElements);

		// Remove columns
		final Collection<Object> columnsToRemove = new ArrayList<Object>();
		columnsToRemove.add(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY);
		columnsToRemove.add(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE);
		columnsToRemove.add(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE);
		manager.removeColumns(columnsToRemove);

		// Check the columns after remove
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(3, columnElements.size());
		checkRemovedColumns(columnElements);

		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(6, columnElements.size());
		checkAddedColumns(columnElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(3, columnElements.size());
		checkRemovedColumns(columnElements);
	}

	/**
	 * This allows to check the initial display columns.
	 * 
	 * @param columnElements
	 *            The row elements.
	 * @throws Exception
	 *             The exception.
	 */
	public void checkInitialColumns(final List<?> columnElements) throws Exception {
		final IAxis firstColumn = (IAxis) columnElements.get(0);
		final Object firstColumnRepresentedElement = firstColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, firstColumnRepresentedElement);

		final IAxis secondColumn = (IAxis) columnElements.get(1);
		final Object secondColumnRepresentedElement = secondColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY, secondColumnRepresentedElement);

		final IAxis thirdColumn = (IAxis) columnElements.get(2);
		final Object thirdColumnRepresentedElement = thirdColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, thirdColumnRepresentedElement);

		final IAxis fourthColumn = (IAxis) columnElements.get(3);
		final Object fourthColumnRepresentedElement = fourthColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, fourthColumnRepresentedElement);
	}

	/**
	 * This allows to check the columns after the added columns.
	 * 
	 * @param columnElements
	 *            The column elements.
	 * @throws Exception
	 *             The exception.
	 */
	public void checkAddedColumns(final List<?> columnElements) throws Exception {
		checkInitialColumns(columnElements);

		final IAxis fifthColumn = (IAxis) columnElements.get(4);
		final Object fifthColumnRepresentedElement = fifthColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, fifthColumnRepresentedElement);

		final IAxis sixthColumn = (IAxis) columnElements.get(5);
		final Object sixthColumnRepresentedElement = sixthColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.TYPED_ELEMENT__TYPE, sixthColumnRepresentedElement);
	}

	/**
	 * This allows to check the columns after the removed row.
	 * 
	 * @param columnElements
	 *            The column elements.
	 * @throws Exception
	 *             The exception.
	 */
	public void checkRemovedColumns(final List<?> columnElements) throws Exception {
		final IAxis firstColumn = (IAxis) columnElements.get(0);
		final Object firstColumnRepresentedElement = firstColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, firstColumnRepresentedElement);

		final IAxis secondColumn = (IAxis) columnElements.get(1);
		final Object secondColumnRepresentedElement = secondColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, secondColumnRepresentedElement);

		final IAxis thirdColumn = (IAxis) columnElements.get(2);
		final Object thirdColumnRepresentedElement = thirdColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.TYPED_ELEMENT__TYPE, thirdColumnRepresentedElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSourcePath() {
		return "/resources/contents_and_expand_tests_resources/"; //$NON-NLS-1$
	}

	/**
	 * This allow to close the opened editors.
	 */
	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}
}
