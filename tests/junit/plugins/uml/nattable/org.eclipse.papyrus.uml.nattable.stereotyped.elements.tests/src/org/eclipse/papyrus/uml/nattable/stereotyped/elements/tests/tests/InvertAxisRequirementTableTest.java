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

package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.tests;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.paste.without.service.edit.AbstractOpenTableTest;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class allows to test the invert axis in the Requirements NatTable
 */
public class InvertAxisRequirementTableTest extends AbstractOpenTableTest {

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
	 * The second class name in the model.
	 */
	private static final String THIRD_CLASS = "Class3"; //$NON-NLS-1$
	
	/**
	 * The 'derived' property of requirement stereotype.
	 */
	private static final String DERIVED_PROPERTY = "property_of_stereotype:/SysMLCopy::Requirements::Requirement::derived"; //$NON-NLS-1$
	
	/**
	 * The 'traced to' property of requirement stereotype.
	 */
	private static final String TRACED_TO_PROPERTY = "property_of_stereotype:/SysMLCopy::Requirements::Requirement::tracedTo"; //$NON-NLS-1$
	
	/**
	 * Initialize the model.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Before
	public void initModel() throws Exception {
		initModel("RequirementTable", fileName, getBundle()); //$NON-NLS-1$
	};

	/**
	 * This allow to test the invert axis method.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void invertAxis() throws Exception {
		testOpenExistingTable(fileName, "RequirementTable"); //$NON-NLS-1$
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);

		// Check the initial rows ans columns
		Assert.assertEquals(4, manager.getColumnElementsList().size());
		Assert.assertEquals(3, manager.getRowElementsList().size());
		checkInitialTable(manager.getColumnElementsList(), manager.getRowElementsList());

		// Invert Axis and check the rows and columns
		manager.invertAxis();
		Assert.assertEquals(4, manager.getRowElementsList().size());
		Assert.assertEquals(3, manager.getColumnElementsList().size());
		checkInitialTable(manager.getRowElementsList(), manager.getColumnElementsList());

		// Invert Axis a second time and check the rows and columns
		manager.invertAxis();
		Assert.assertEquals(4, manager.getColumnElementsList().size());
		Assert.assertEquals(3, manager.getRowElementsList().size());
		checkInitialTable(manager.getColumnElementsList(), manager.getRowElementsList());
		
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
		Assert.assertEquals(4, manager.getRowElementsList().size());
		Assert.assertEquals(3, manager.getColumnElementsList().size());
		checkInitialTable(manager.getRowElementsList(), manager.getColumnElementsList());
		
		// check the redo
		getTransactionalEditingDomain().getCommandStack().redo();
		Assert.assertEquals(4, manager.getColumnElementsList().size());
		Assert.assertEquals(3, manager.getRowElementsList().size());
		checkInitialTable(manager.getColumnElementsList(), manager.getRowElementsList());
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
		Assert.assertEquals(UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY, secondColumnRepresentedElement);

		IAxis thirdColumn = (IAxis) columnElements.get(2);
		Object thirdColumnRepresentedElement = thirdColumn.getElement();
		Assert.assertEquals(DERIVED_PROPERTY, thirdColumnRepresentedElement);

		IAxis fourthColumn = (IAxis) columnElements.get(3);
		Object fourthColumnRepresentedElement = fourthColumn.getElement();
		Assert.assertEquals(TRACED_TO_PROPERTY, fourthColumnRepresentedElement);

		// Check the row elements
		EObject firstRow = (EObject) rowElements.get(0);
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRow).eClass());
		Assert.assertEquals(FIRST_CLASS, ((org.eclipse.uml2.uml.Class) firstRow).getName());

		EObject secondRow = (EObject) rowElements.get(1);
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) secondRow).eClass());
		Assert.assertEquals(SECOND_CLASS, ((org.eclipse.uml2.uml.Class) secondRow).getName());

		EObject thirdRow = (EObject) rowElements.get(2);
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) thirdRow).eClass());
		Assert.assertEquals(THIRD_CLASS, ((org.eclipse.uml2.uml.Class) thirdRow).getName());
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
