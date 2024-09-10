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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class allows to test the sort in the Generic NatTable
 */
public class SortAxisGenericTableTest extends AbstractGenericTableTest {

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
	 * This allow to test the sort columns by name.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void sortColumns() throws Exception {
		loadGenericTable();
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);

		List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals(3, columnElements.size());

		// Check the initial display
		checkinitialColumns(columnElements);

		// Sort ASC columns by name and check the new order
		manager.sortColumnsByName(true);
		columnElements = manager.getColumnElementsList();
		checkSortedColumns(columnElements, true);
		
		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		columnElements = manager.getColumnElementsList();
		checkinitialColumns(columnElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		columnElements = manager.getColumnElementsList();
		checkSortedColumns(columnElements, true);

		// Sort DESC columns by name and check the new order
		manager.sortColumnsByName(false);
		columnElements = manager.getColumnElementsList();
		checkSortedColumns(columnElements, false);
		
		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		columnElements = manager.getColumnElementsList();
		checkSortedColumns(columnElements, true);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		columnElements = manager.getColumnElementsList();
		checkSortedColumns(columnElements, false);
	}
	
	/**
	 * This allows to check the columns when a sort was done.
	 * 
	 * @param columnElements The column elements.
	 * @param alphabeticOrder <code>true</code> if the alphabetic order is used, <code>false</code> otherwise.
	 * @throws Exception The exception.
	 */
	public void checkSortedColumns(final List<?> columnElements, final boolean alphabeticOrder) throws Exception{
		if(alphabeticOrder){
			final IAxis firstColumn = (IAxis) columnElements.get(0);
			final Object firstRepresentedElement = firstColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, firstRepresentedElement);

			final IAxis secondColumn = (IAxis) columnElements.get(1);
			final Object secondRepresentedElement = secondColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, secondRepresentedElement);

			final IAxis thirdColumn = (IAxis) columnElements.get(2);
			final Object thirdRepresentedElement = thirdColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.TYPED_ELEMENT__TYPE, thirdRepresentedElement);
		}else{
			final IAxis firstColumn = (IAxis) columnElements.get(0);
			final Object firstRepresentedElement = firstColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.TYPED_ELEMENT__TYPE, firstRepresentedElement);

			final IAxis secondColumn = (IAxis) columnElements.get(1);
			final Object secondRepresentedElement = secondColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, secondRepresentedElement);

			final IAxis thirdColumn = (IAxis) columnElements.get(2);
			final Object thirdRepresentedElement = thirdColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, thirdRepresentedElement);
		}
	}
	
	/**
	 * This allows to check the initial display columns.
	 * 
	 * @param columnElements The column elements.
	 * @throws Exception The exception.
	 */
	public void checkinitialColumns(final List<?> columnElements) throws Exception{
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
	 * This allow to test the sort rows by name.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void sortRows() throws Exception {
		loadGenericTable();
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);

		// Check the initial display
		List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals(5, rowElements.size());
		checkinitialRows(rowElements);

		// Sort ASC rows by name and check the rows
		manager.sortRowsByName(true);
		rowElements = manager.getRowElementsList();
		checkSortedRows(rowElements, true);
		
		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		rowElements = manager.getRowElementsList();
		checkinitialRows(rowElements);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		rowElements = manager.getRowElementsList();
		checkSortedRows(rowElements, true);

		// Sort ASC rows by name and check the rows
		manager.sortRowsByName(false);
		rowElements = manager.getRowElementsList();
		checkSortedRows(rowElements, false);
		
		// Check the undo
		getTransactionalEditingDomain().getCommandStack().undo();
		rowElements = manager.getRowElementsList();
		checkSortedRows(rowElements, true);
		// Check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		rowElements = manager.getRowElementsList();
		checkSortedRows(rowElements, false);
	}
	
	/**
	 * This allows to check the rows when a sort was done.
	 * 
	 * @param rowElements The row elements.
	 * @param alphabeticOrder <code>true</code> if the alphabetic order is used, <code>false</code> otherwise.
	 * @throws Exception The exception.
	 */
	public void checkSortedRows(final List<?> rowElements, final boolean alphabeticOrder) throws Exception{
		if(alphabeticOrder){
			final IAxis firstRow = (IAxis) rowElements.get(0);
			final Object firstRepresentedElement = firstRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) firstRepresentedElement).eClass());
			Assert.assertEquals(FIRST_PROPERTY, ((Property) firstRepresentedElement).getName());

			final IAxis secondRow = (IAxis) rowElements.get(1);
			final Object secondRepresentedElement = secondRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) secondRepresentedElement).eClass());
			Assert.assertEquals(SECOND_PROPERTY, ((Property) secondRepresentedElement).getName());

			final IAxis thirdRow = (IAxis) rowElements.get(2);
			final Object thirdRepresentedElement = thirdRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) thirdRepresentedElement).eClass());
			Assert.assertEquals(THIRD_PROPERTY, ((Property) thirdRepresentedElement).getName());

			final IAxis fourthRow = (IAxis) rowElements.get(3);
			final Object fourthRepresentedElement = fourthRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) fourthRepresentedElement).eClass());
			Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) fourthRepresentedElement).getName());

			final IAxis fifthRow = (IAxis) rowElements.get(4);
			final Object fifthRepresentedElement = fifthRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) fifthRepresentedElement).eClass());
			Assert.assertEquals(SECOND_CLASS, ((org.eclipse.uml2.uml.Class) fifthRepresentedElement).getName());
		}else{
			final IAxis firstRow = (IAxis) rowElements.get(0);
			final Object firstRepresentedElement = firstRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRepresentedElement).eClass());
			Assert.assertEquals(SECOND_CLASS, ((org.eclipse.uml2.uml.Class) firstRepresentedElement).getName());

			final IAxis secondRow = (IAxis) rowElements.get(1);
			final Object secondRepresentedElement = secondRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) secondRepresentedElement).eClass());
			Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) secondRepresentedElement).getName());

			final IAxis thirdRow = (IAxis) rowElements.get(2);
			final Object thirdRepresentedElement = thirdRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) thirdRepresentedElement).eClass());
			Assert.assertEquals(THIRD_PROPERTY, ((Property) thirdRepresentedElement).getName());

			final IAxis fourthRow = (IAxis) rowElements.get(3);
			final Object fourthRepresentedElement = fourthRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) fourthRepresentedElement).eClass());
			Assert.assertEquals(SECOND_PROPERTY, ((Property) fourthRepresentedElement).getName());

			final IAxis fifthRow = (IAxis) rowElements.get(4);
			final Object fifthRepresentedElement = fifthRow.getElement();
			Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) fifthRepresentedElement).eClass());
			Assert.assertEquals(FIRST_PROPERTY, ((Property) fifthRepresentedElement).getName());
		}
	}
	
	/**
	 * This allows to check the initial display rows.
	 * 
	 * @param rowElements The row elements.
	 * @throws Exception The exception.
	 */
	public void checkinitialRows(final List<?> rowElements) throws Exception{
		final IAxis firstRow = (IAxis) rowElements.get(0);
		final Object firstRepresentedElement = firstRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRepresentedElement).eClass());
		Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) firstRepresentedElement).getName());
		
		final IAxis secondRow = (IAxis) rowElements.get(1);
		final Object secondRepresentedElement = secondRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) secondRepresentedElement).eClass());
		Assert.assertEquals(FIRST_PROPERTY, ((Property) secondRepresentedElement).getName());

		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) thirdRepresentedElement).eClass());
		Assert.assertEquals(SECOND_PROPERTY, ((Property) thirdRepresentedElement).getName());

		final IAxis fourthRow = (IAxis) rowElements.get(3);
		final Object fourthRepresentedElement = fourthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) fourthRepresentedElement).eClass());
		Assert.assertEquals(SECOND_CLASS, ((org.eclipse.uml2.uml.Class) fourthRepresentedElement).getName());

		final IAxis fifthRow = (IAxis) rowElements.get(4);
		final Object fifthRepresentedElement = fifthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) fifthRepresentedElement).eClass());
		Assert.assertEquals(THIRD_PROPERTY, ((Property) fifthRepresentedElement).getName());
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
