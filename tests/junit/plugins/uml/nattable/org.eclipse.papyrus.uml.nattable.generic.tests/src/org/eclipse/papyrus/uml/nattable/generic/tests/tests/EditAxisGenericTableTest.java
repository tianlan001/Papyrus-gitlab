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

package org.eclipse.papyrus.uml.nattable.generic.tests.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class allows to test the add/remove actions in the Generic NatTable
 */
public class EditAxisGenericTableTest extends AbstractGenericTableTest {

	/**
	 * The file name of the papyrus project used.
	 */
	public static String fileName = "contents_sort_invert_edit_model"; //$NON-NLS-1$

	/**
	 * The first class name in the model.
	 */
	private static final String FIRST_CLASS = "Class1"; //$NON-NLS-1$

	/**
	 * The second class name in the model.
	 */
	private static final String SECOND_CLASS = "Class2"; //$NON-NLS-1$

	/**
	 * The created class name in the model.
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
	 * Initialize the model.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Before
	public void initModel() throws Exception {
		initModel("GenericTable", fileName, getBundle()); //$NON-NLS-1$
	};

	/**
	 * This allow to test the add row in a table.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void editRows() throws Exception {
		loadGenericTable();
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);

		// Test the initial display
		// Check the row elements
		List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals(5, rowElements.size());
		checkInitialRows(rowElements);

		// Get a class to add in rows
		org.eclipse.uml2.uml.Package rootModel = getRootUMLModel();
		Class thirdClass = null;
		for (final Element element : rootModel.getOwnedElements()) {
			if (element instanceof Class && ((Class) element).getName().equals(THIRD_CLASS)) {
				thirdClass = (Class) element;
			}
		}
		Assert.assertNotNull(thirdClass);

		// Add a row
		final Collection<Object> rowsToAdd = new ArrayList<Object>();
		rowsToAdd.add(thirdClass);
		manager.addRows(rowsToAdd);

		// The created class display
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(6, rowElements.size());
		checkAddedRow(rowElements);
		
		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(5, rowElements.size());
		checkInitialRows(rowElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(6, rowElements.size());
		checkAddedRow(rowElements);

		// Remove rows
		final Collection<Object> rowsToRemove = new ArrayList<Object>();
		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		final IAxis fifthRow = (IAxis) rowElements.get(4);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		rowsToRemove.add(thirdRowRepresentedElement);
		rowsToRemove.add(fifthRowRepresentedElement);
		manager.removeRows(rowsToRemove);

		// Check the removed row
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(4, rowElements.size());
		checkRemovedRow(rowElements);
		
		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(6, rowElements.size());
		checkAddedRow(rowElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		rowElements = manager.getRowElementsList();
		Assert.assertEquals(4, rowElements.size());
		checkRemovedRow(rowElements);
	}
	
	/**
	 * This allows to check the initial display rows.
	 * 
	 * @param rowElements The row elements.
	 * @throws Exception The exception.
	 */
	public void checkInitialRows(final List<?> rowElements) throws Exception{
		final IAxis firstRow = (IAxis) rowElements.get(0);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) firstRowRepresentedElement).getName());

		final IAxis secondRow = (IAxis) rowElements.get(1);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) secondRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_PROPERTY, ((Property) secondRowRepresentedElement).getName());

		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(SECOND_PROPERTY, ((Property) thirdRowRepresentedElement).getName());

		final IAxis fourthRow = (IAxis) rowElements.get(3);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) fourthRowRepresentedElement).eClass());
		Assert.assertEquals(SECOND_CLASS, ((org.eclipse.uml2.uml.Class) fourthRowRepresentedElement).getName());

		final IAxis fifthRow = (IAxis) rowElements.get(4);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) fifthRowRepresentedElement).eClass());
		Assert.assertEquals(THIRD_PROPERTY, ((Property) fifthRowRepresentedElement).getName());
	}
	
	/**
	 * This allows to check the columns after the added row.
	 * 
	 * @param rowElements The row elements.
	 * @throws Exception The exception.
	 */
	public void checkAddedRow(final List<?> rowElements) throws Exception{
		checkInitialRows(rowElements);
		
		final IAxis sixthRow = (IAxis) rowElements.get(5);
		final Object sixthRowRepresentedElement = sixthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) sixthRowRepresentedElement).eClass());
		Assert.assertEquals(THIRD_CLASS, ((org.eclipse.uml2.uml.Class) sixthRowRepresentedElement).getName());
	}
	
	/**
	 * This allows to check the columns after the removed row.
	 * 
	 * @param rowElements The row elements.
	 * @throws Exception The exception.
	 */
	public void checkRemovedRow(final List<?> rowElements) throws Exception{
		final IAxis firstRow = (IAxis) rowElements.get(0);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) firstRowRepresentedElement).getName());

		final IAxis secondRow = (IAxis) rowElements.get(1);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) secondRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_PROPERTY, ((Property) secondRowRepresentedElement).getName());
		
		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(SECOND_CLASS, ((org.eclipse.uml2.uml.Class) thirdRowRepresentedElement).getName());

		final IAxis fourthRow = (IAxis) rowElements.get(3);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) fourthRowRepresentedElement).eClass());
		Assert.assertEquals(THIRD_CLASS, ((org.eclipse.uml2.uml.Class) fourthRowRepresentedElement).getName());
	}

	/**
	 * This allow to test the add column in a table.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void editColumns() throws Exception {
		loadGenericTable();
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);

		// Test the initial display
		// Check the row elements
		List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals(3, columnElements.size());
		checkInitialColumns(columnElements);

		// Add columns
		final Collection<Object> columnsToAdd = new ArrayList<Object>();
		columnsToAdd.add(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY);
		columnsToAdd.add(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE);
		columnsToAdd.add(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE);
		manager.addColumns(columnsToAdd);

		// Check the created columns display
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(6, columnElements.size());
		checkAddedColumns(columnElements);
		
		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(3, columnElements.size());
		checkInitialColumns(columnElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		columnElements = manager.getColumnElementsList();
		Assert.assertEquals(6, columnElements.size());
		checkAddedColumns(columnElements);

		// Remove columns
		final Collection<Object> columnsToRemove = new ArrayList<Object>();
		columnsToRemove.add(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE);
		columnsToRemove.add(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY);
		columnsToRemove.add(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE);
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
	 * @param columnElements The row elements.
	 * @throws Exception The exception.
	 */
	public void checkInitialColumns(final List<?> columnElements) throws Exception{
		final IAxis firstColumn = (IAxis) columnElements.get(0);
		final Object firstRepresentedElement = firstColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, firstRepresentedElement);

		final IAxis secondColumn = (IAxis) columnElements.get(1);
		final Object secondRepresentedElement = secondColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, secondRepresentedElement);

		final IAxis thirdColumn = (IAxis) columnElements.get(2);
		final Object thirdRepresentedElement = thirdColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.TYPED_ELEMENT__TYPE, thirdRepresentedElement);
	}
	
	/**
	 * This allows to check the columns after the added columns.
	 * 
	 * @param columnElements The column elements.
	 * @throws Exception The exception.
	 */
	public void checkAddedColumns(final List<?> columnElements) throws Exception{
		checkInitialColumns(columnElements);
		
		final IAxis fourthColumn = (IAxis) columnElements.get(3);
		final Object fourthRepresentedElement = fourthColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY, fourthRepresentedElement);

		final IAxis fifthColumn = (IAxis) columnElements.get(4);
		final Object fifthRepresentedElement = fifthColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, fifthRepresentedElement);

		final IAxis sixthColumn = (IAxis) columnElements.get(5);
		final Object sixthRepresentedElement = sixthColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, sixthRepresentedElement);
	}
	
	/**
	 * This allows to check the columns after the removed row.
	 * 
	 * @param columnElements The column elements.
	 * @throws Exception The exception.
	 */
	public void checkRemovedColumns(final List<?> columnElements) throws Exception{
		final IAxis firstColumn = (IAxis) columnElements.get(0);
		final Object firstRepresentedElement = firstColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, firstRepresentedElement);
		
		final IAxis secondColumn = (IAxis) columnElements.get(1);
		final Object secondRepresentedElement = secondColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.TYPED_ELEMENT__TYPE, secondRepresentedElement);

		final IAxis thirdColumn = (IAxis) columnElements.get(2);
		final Object thirdRepresentedElement = thirdColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, thirdRepresentedElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSourcePath() {
		return "/resources/contents_tests_resources/"; //$NON-NLS-1$
	}

	/**
	 * This allow to close the opened editors.
	 */
	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}
}
