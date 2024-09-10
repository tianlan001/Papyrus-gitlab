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
 * This class allows to test the invert axis in the Generic NatTable
 */
public class InvertAxisGenericTableTest extends AbstractGenericTableTest {

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
		
		loadGenericTable();
	};

	/**
	 * This allow to test the invert axis method.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void invertAxis() throws Exception {
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);

		// Check the initial rows ans columns
		Assert.assertEquals(3, manager.getColumnElementsList().size());
		Assert.assertEquals(5, manager.getRowElementsList().size());
		checkInitialTable(manager.getColumnElementsList(), manager.getRowElementsList());

		// Invert Axis and check the rows and columns
		manager.invertAxis();
		Assert.assertEquals(3, manager.getRowElementsList().size());
		Assert.assertEquals(5, manager.getColumnElementsList().size());
		checkInitialTable(manager.getRowElementsList(), manager.getColumnElementsList());
		
		flushDisplayEvents();

		// Invert Axis a second time and check the rows and columns
		manager.invertAxis();
		Assert.assertEquals(3, manager.getColumnElementsList().size());
		Assert.assertEquals(5, manager.getRowElementsList().size());
		checkInitialTable(manager.getColumnElementsList(), manager.getRowElementsList());
		
		flushDisplayEvents();
		
		checkUndoRedo(manager);
	}
	
	/**
	 * This allow to check the undo and the redo action.
	 * 
	 * @param manager The nattable model manager.
	 * @throws Exception The exception.
	 */
	public void checkUndoRedo(final INattableModelManager manager) throws Exception {
		getTransactionalEditingDomain().getCommandStack().undo();
		Assert.assertEquals(3, manager.getRowElementsList().size());
		Assert.assertEquals(5, manager.getColumnElementsList().size());
		checkInitialTable(manager.getRowElementsList(), manager.getColumnElementsList());
		
		flushDisplayEvents();
		
		// check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		Assert.assertEquals(3, manager.getColumnElementsList().size());
		Assert.assertEquals(5, manager.getRowElementsList().size());
		checkInitialTable(manager.getColumnElementsList(), manager.getRowElementsList());
		
		flushDisplayEvents();
	}

	/**
	 * This allow to test the row and the columns elements.
	 * 
	 * @param columnElements
	 *            The column elements.
	 * @param rowElements
	 *            The row elements.
	 * @throws Exception
	 *             The exception.
	 */
	private void checkInitialTable(List<?> columnElements, final List<?> rowElements) throws Exception {
		// Check the columns elements
		IAxis firstColumn = (IAxis) columnElements.get(0);
		Object firstColumnRepresentedElement = firstColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__NAME, firstColumnRepresentedElement);

		IAxis secondColumn = (IAxis) columnElements.get(1);
		Object secondColumnRepresentedElement = secondColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, secondColumnRepresentedElement);

		IAxis thirdColumn = (IAxis) columnElements.get(2);
		Object thirdColumnRepresentedElement = thirdColumn.getElement();
		Assert.assertEquals(UMLPackage.Literals.TYPED_ELEMENT__TYPE, thirdColumnRepresentedElement);

		// Check the row elements
		IAxis firstRow = (IAxis) rowElements.get(0);
		Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) firstRowRepresentedElement).getName());

		IAxis secondRow = (IAxis) rowElements.get(1);
		Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) secondRowRepresentedElement).eClass());
		Assert.assertEquals(FIRST_PROPERTY, ((Property) secondRowRepresentedElement).getName());

		IAxis thirdRow = (IAxis) rowElements.get(2);
		Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(SECOND_PROPERTY, ((Property) thirdRowRepresentedElement).getName());

		IAxis fourthRow = (IAxis) rowElements.get(3);
		Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) fourthRowRepresentedElement).eClass());
		Assert.assertEquals(SECOND_CLASS, ((org.eclipse.uml2.uml.Class) fourthRowRepresentedElement).getName());

		IAxis fifthRow = (IAxis) rowElements.get(4);
		Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getProperty(), ((EObject) fifthRowRepresentedElement).eClass());
		Assert.assertEquals(THIRD_PROPERTY, ((Property) fifthRowRepresentedElement).getName());
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
