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

import java.util.List;

import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class allows to test the sort in the Hierarchical NatTable
 */
public class SortAxisClassTreeTableTest extends AbstractOpenTableTest {

	/**
	 * The file name of the papyrus project used.
	 */
	public static String fileName = "contents_sort_edit_model"; //$NON-NLS-1$

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
	 * This allow to test the sort columns by name.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void sortColumns() throws Exception {
		testOpenExistingTable(fileName, "ClassTreeTable");
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);

		List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals(4, columnElements.size());

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
			final Object firstColumnRepresentedElement = firstColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, firstColumnRepresentedElement);

			final IAxis secondColumn = (IAxis) columnElements.get(1);
			final Object secondColumnRepresentedElement = secondColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, secondColumnRepresentedElement);

			final IAxis thirdColumn = (IAxis) columnElements.get(2);
			final Object thirdColumnRepresentedElement = thirdColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, thirdColumnRepresentedElement);

			final IAxis fourthColumn = (IAxis) columnElements.get(3);
			final Object fourthColumnRepresentedElement = fourthColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY, fourthColumnRepresentedElement);
		}else{
			final IAxis firstColumn = (IAxis) columnElements.get(0);
			final Object firstColumnRepresentedElement = firstColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY, firstColumnRepresentedElement);

			final IAxis secondColumn = (IAxis) columnElements.get(1);
			final Object secondColumnRepresentedElement = secondColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, secondColumnRepresentedElement);

			final IAxis thirdColumn = (IAxis) columnElements.get(2);
			final Object thirdColumnRepresentedElement = thirdColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, thirdColumnRepresentedElement);

			final IAxis fourthColumn = (IAxis) columnElements.get(3);
			final Object fourthColumnRepresentedElement = fourthColumn.getElement();
			Assert.assertEquals(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, fourthColumnRepresentedElement);
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
