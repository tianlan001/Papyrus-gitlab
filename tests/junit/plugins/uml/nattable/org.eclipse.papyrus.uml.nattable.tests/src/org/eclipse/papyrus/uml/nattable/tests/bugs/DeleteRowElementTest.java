/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.uml.nattable.tests.bugs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class allows to test the delete row elements action.
 */
@PluginResource("resources/bugs/bug487496/DeleteRowElement.di") //$NON-NLS-1$
public class DeleteRowElementTest extends AbstractPapyrusTest {

	/**
	 * The class name in the model.
	 */
	private static final String CLASS_NAME = "Class1"; //$NON-NLS-1$

	/**
	 * The attribute name in the model.
	 */
	private static final String ATTRIBUTE_NAME = "Attribute1"; //$NON-NLS-1$

	/**
	 * The operation name in the model.
	 */
	private static final String OPERATION_NAME = "Operation1"; //$NON-NLS-1$

	/**
	 * The parameter name in the model.
	 */
	private static final String PARAMETER_NAME = "Parameter1"; //$NON-NLS-1$


	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "GenericTreeTable0"; //$NON-NLS-1$

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * The current model.
	 */
	public Model model = null;

	/**
	 * The nattable model manager.
	 */
	public INattableModelManager currentManager = null;

	/**
	 * Constructor.
	 */
	public DeleteRowElementTest() {
		super();
	}

	/**
	 * Initialize.
	 */
	@Before
	public void init() {
		// Get the model
		model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		final Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();

		// Get the table and its manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The table manage must be a tree table manager", currentManager instanceof TreeNattableModelManager); //$NON-NLS-1$

		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to test the deletion of the class row element.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testClassDestroyRowElement() throws Exception {
		checkInitialContent();

		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Destroy the row element
		final IAxisManager axisManager = currentManager.getRowAxisManager();
		Assert.assertNotNull(axisManager);

		// The list of indexes to delete
		final List<Integer> indexes = new ArrayList<Integer>();

		// Test that this is not possible to destroy row element on tree filling
		indexes.add(0);
		axisManager.destroyAxisElement(indexes);

		Assert.assertNull("The command stack must be null because the destroy of tree filling musn't be possible", editingDomain.getCommandStack().getMostRecentCommand()); //$NON-NLS-1$

		// Destroy class
		indexes.clear();
		indexes.add(1);
		axisManager.destroyAxisElement(indexes);
		fixture.flushDisplayEvents();
		checkWithoutClass();

		undo(editingDomain);
		checkInitialContent();

		redo(editingDomain);
		checkWithoutClass();
	}

	/**
	 * This allows to test the deletion of the attribute row element.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testAttributeDestroyRowElement() throws Exception {
		checkInitialContent();

		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Destroy the row element
		final IAxisManager axisManager = currentManager.getRowAxisManager();
		Assert.assertNotNull(axisManager);

		// The list of indexes to delete
		final List<Integer> indexes = new ArrayList<Integer>();

		// Test that this is not possible to destroy row element on tree filling
		indexes.add(2);
		axisManager.destroyAxisElement(indexes);

		Assert.assertNull("The command stack must be null because the destroy of tree filling musn't be possible", editingDomain.getCommandStack().getMostRecentCommand()); //$NON-NLS-1$

		// Destroy attribute
		indexes.clear();
		indexes.add(3);
		axisManager.destroyAxisElement(indexes);
		fixture.flushDisplayEvents();
		checkWithoutAttribute();

		undo(editingDomain);
		checkInitialContent();

		redo(editingDomain);
		checkWithoutAttribute();
	}

	/**
	 * This allows to test the deletion of the operation row element.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testOperationDestroyRowElement() throws Exception {
		checkInitialContent();

		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Destroy the row element
		final IAxisManager axisManager = currentManager.getRowAxisManager();
		Assert.assertNotNull(axisManager);

		// The list of indexes to delete
		final List<Integer> indexes = new ArrayList<Integer>();

		// Destroy operation
		indexes.add(4);
		axisManager.destroyAxisElement(indexes);
		fixture.flushDisplayEvents();
		checkWithoutOperation();

		undo(editingDomain);
		checkInitialContent();

		redo(editingDomain);
		checkWithoutOperation();
	}

	/**
	 * This allows to test the parameter of the class row element.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testParameterDestroyRowElement() throws Exception {
		checkInitialContent();

		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Destroy the row element
		final IAxisManager axisManager = currentManager.getRowAxisManager();
		Assert.assertNotNull(axisManager);

		// The list of indexes to delete
		final List<Integer> indexes = new ArrayList<Integer>();

		// Test that this is not possible to destroy row element on tree filling
		indexes.add(5);
		axisManager.destroyAxisElement(indexes);

		Assert.assertNull("The command stack must be null because the destroy of tree filling musn't be possible", editingDomain.getCommandStack().getMostRecentCommand()); //$NON-NLS-1$

		// Destroy parameter
		indexes.clear();
		indexes.add(6);
		axisManager.destroyAxisElement(indexes);
		fixture.flushDisplayEvents();
		checkWithoutParameter();

		undo(editingDomain);
		checkInitialContent();

		redo(editingDomain);
		checkWithoutParameter();
	}

	/**
	 * This allows to undo the previous command.
	 * 
	 * @param editingDomain
	 *            The current editing domain.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void undo(final TransactionalEditingDomain editingDomain) throws Exception {
		editingDomain.getCommandStack().undo();
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to redo the previous command.
	 * 
	 * @param editingDomain
	 *            The current editing domain.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void redo(final TransactionalEditingDomain editingDomain) throws Exception {
		editingDomain.getCommandStack().redo();
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to test the initial content of the table (size and row elements).
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkInitialContent() throws Exception {
		final List<Object> rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The table content size is not the correct one", 7, rowElements.size()); //$NON-NLS-1$

		checkTableContent(true, true, true);
	}

	/**
	 * This allows to test the content of the table (size and row elements) when the class is removed.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkWithoutClass() throws Exception {
		final List<Object> rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The table content size is not the correct one", 0, rowElements.size()); //$NON-NLS-1$
	}

	/**
	 * This allows to test the content of the table (size and row elements) when the attribute is removed.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkWithoutAttribute() throws Exception {
		final List<Object> rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The table content size is not the correct one", 6, rowElements.size()); //$NON-NLS-1$

		checkTableContent(false, true, true);
	}

	/**
	 * This allows to test the content of the table (size and row elements) when the operation is removed.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkWithoutOperation() throws Exception {
		final List<Object> rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The table content size is not the correct one", 4, rowElements.size()); //$NON-NLS-1$

		checkTableContent(true, false, false);
	}

	/**
	 * This allows to test the content of the table (size and row elements) when the parameter is removed.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkWithoutParameter() throws Exception {
		final List<Object> rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The table content size is not the correct one", 5, rowElements.size()); //$NON-NLS-1$

		checkTableContent(true, true, false);
	}

	/**
	 * This allows to check the table content with/without attribute, operation and parameter.
	 * 
	 * @param hasAttribute
	 *            Boolean to determinate if the attribute must be available.
	 * @param hasOperation
	 *            Boolean to determinate if the operation must be available.
	 * @param hasParameter
	 *            Boolean to determinate if the parameter must be available.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkTableContent(final boolean hasAttribute, final boolean hasOperation, final boolean hasParameter) throws Exception {
		int index = 0;

		final List<Object> rowElements = currentManager.getRowElementsList();

		final IAxis firstRow = (IAxis) rowElements.get(index++);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertTrue("The first row must be a tree filling configuration", firstRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$

		final IAxis secondRow = (IAxis) rowElements.get(index++);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals("The first element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) secondRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the first element is not corresponding to the class", CLASS_NAME, ((org.eclipse.uml2.uml.Class) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		if (hasAttribute || hasOperation) {
			final IAxis thirdRow = (IAxis) rowElements.get(index++);
			final Object thirdRowRepresentedElement = thirdRow.getElement();
			Assert.assertTrue("The third row must be a tree filling configuration", thirdRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
		}
		
		if (hasAttribute) {
			final IAxis fourthRow = (IAxis) rowElements.get(index++);
			final Object fourthRowRepresentedElement = fourthRow.getElement();
			Assert.assertEquals("The second element must be a class", UMLPackage.eINSTANCE.getProperty(), ((EObject) fourthRowRepresentedElement).eClass()); //$NON-NLS-1$
			Assert.assertEquals("The name of the second element is not corresponding to the attribute", ATTRIBUTE_NAME, ((org.eclipse.uml2.uml.Property) fourthRowRepresentedElement).getName()); //$NON-NLS-1$
		}

		if (hasOperation) {
			final IAxis fifthRow = (IAxis) rowElements.get(index++);
			final Object fifthRowRepresentedElement = fifthRow.getElement();
			Assert.assertEquals("The third element must be a package", UMLPackage.eINSTANCE.getOperation(), ((EObject) fifthRowRepresentedElement).eClass()); //$NON-NLS-1$
			Assert.assertEquals("The name of the third element is not corresponding to the operation", OPERATION_NAME, ((org.eclipse.uml2.uml.Operation) fifthRowRepresentedElement).getName()); //$NON-NLS-1$

			if (hasParameter) {
				final IAxis sixthRow = (IAxis) rowElements.get(index++);
				final Object sixthRowRepresentedElement = sixthRow.getElement();
				Assert.assertTrue("The seventh row must be a tree filling configuration", sixthRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$

				final IAxis seventhRow = (IAxis) rowElements.get(index++);
				final Object seventhRowRepresentedElement = seventhRow.getElement();
				Assert.assertEquals("The fourth element must be a package", UMLPackage.eINSTANCE.getParameter(), ((EObject) seventhRowRepresentedElement).eClass()); //$NON-NLS-1$
				Assert.assertEquals("The name of the fourth element is not corresponding to the parameter", PARAMETER_NAME, ((org.eclipse.uml2.uml.Parameter) seventhRowRepresentedElement).getName()); //$NON-NLS-1$
			}
		}
	}
}