/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
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
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Bug 517731
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ShowView;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to test the adding object to connected table (features are created as columns table).
 */
@PluginResource("resources/bugs/bug469289/AddElementsOnConnectedTableTest.di")
@ShowView(value = "org.eclipse.papyrus.views.modelexplorer.modelexplorer")
public class AddElementsOnConnectedTableTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "GenericTable0"; //$NON-NLS-1$

	/**
	 * The number of class features and operations.
	 */
	private static final int NB_CLASS_FEATURES_AND_OPERATIONS = 103;

	/**
	 * The number of features and operations provided by Interface, in addition of UML Class.
	 */
	private static final int NB_INTERFACE_FEATURES_AND_OPERATIONS = 6;

	/**
	 * The number of empty axis (equals to 0).
	 */
	private static final int NB_EMPTY_AXIS = 0;

	/**
	 * The command id for the class creation.
	 */
	private static final String CREATE_CLASS_COMMAND = "org.eclipse.papyrus.uml.service.types.ClassCreateCommand"; //$NON-NLS-1$

	/**
	 * The command id for the interface creation.
	 */
	private static final String CREATE_INTERFACE_COMMAND = "org.eclipse.papyrus.uml.service.types.InterfaceCreateCommand"; //$NON-NLS-1$


	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();


	/**
	 * Constructor.
	 */
	public AddElementsOnConnectedTableTest() {
		super();
	}

	/**
	 * This allows to test add of elements in table .
	 *
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testAddElements() throws Exception {
		final Model model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();
		IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue("The part must be a nattable editor", part instanceof NatTableEditor); //$NON-NLS-1$
		NatTableEditor editor = (NatTableEditor) part;
		INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);

		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Check that the table is empty
		List<?> rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must be empty", NB_EMPTY_AXIS, rowElements.size()); //$NON-NLS-1$
		List<?> columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must be empty", NB_EMPTY_AXIS, columnElements.size()); //$NON-NLS-1$

		// Get the command service
		final ICommandService commandService = EclipseCommandUtils.getCommandService();
		Assert.assertNotNull(commandService);


		// Create a class
		createClassElement(commandService);

		// Check the rows and columns size
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must contain the created class", 1, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must contain the class structural features", NB_CLASS_FEATURES_AND_OPERATIONS, columnElements.size()); //$NON-NLS-1$

		// Undo the creation of the class
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// check that the table is empty with the class deletion
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must be empty", NB_EMPTY_AXIS, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must be empty", NB_EMPTY_AXIS, columnElements.size()); //$NON-NLS-1$

		// Redo the creation of the class
		editingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();

		// Check the rows and columns size
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must contain the created class", 1, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must contain the class structural features", NB_CLASS_FEATURES_AND_OPERATIONS, columnElements.size()); //$NON-NLS-1$


		// Create an other class
		createClassElement(commandService);

		// Check the rows and columns size
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must contain the created class and the existing class", 2, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must contain the class structural features (without doubloon)", NB_CLASS_FEATURES_AND_OPERATIONS, columnElements.size()); //$NON-NLS-1$

		// Undo the creation of the class
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// check that the table is empty with the class deletion
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must contain the existing class", 1, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must contain the class structural features", NB_CLASS_FEATURES_AND_OPERATIONS, columnElements.size()); //$NON-NLS-1$

		// Redo the creation of the class
		editingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();

		// Check the rows and columns size
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must contain the created class and the existing class", 2, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must contain the class structural features (without doubloon)", NB_CLASS_FEATURES_AND_OPERATIONS, columnElements.size()); //$NON-NLS-1$


		// Create an interface
		createInterfaceElement(commandService);

		// Check the rows and columns size
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must contain the created interface and the existing classes", 3, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must contain the class and interface structural features (without doubloon)", NB_CLASS_FEATURES_AND_OPERATIONS + NB_INTERFACE_FEATURES_AND_OPERATIONS, columnElements.size()); //$NON-NLS-1$

		// Undo the creation of the class
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// check that the table is empty with the class deletion
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must contain the created class and the existing class", 2, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must contain the class structural features (without doubloon)", NB_CLASS_FEATURES_AND_OPERATIONS, columnElements.size()); //$NON-NLS-1$

		// Redo the creation of the class
		editingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();

		// Check the rows and columns size
		rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The rows must contain the created interface and the existing classes", 3, rowElements.size()); //$NON-NLS-1$
		columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The columns must contain the class and interface structural features (without doubloon)", NB_CLASS_FEATURES_AND_OPERATIONS + NB_INTERFACE_FEATURES_AND_OPERATIONS, columnElements.size()); //$NON-NLS-1$
	}

	/**
	 * This allow to create a class element in the table from the contextual menu.
	 *
	 * @param commandService
	 *            The command service.
	 * @throws Exception
	 *             The exception.
	 */
	private void createClassElement(final ICommandService commandService) throws Exception {
		final EObject createdObject = getcreatedElement(commandService, CREATE_CLASS_COMMAND);
		Assert.assertTrue("The created object must be a class", createdObject instanceof org.eclipse.uml2.uml.Class); //$NON-NLS-1$
	}

	/**
	 * This allow to create an interface element in the table from the contextual menu.
	 *
	 * @param commandService
	 *            The command service.
	 * @throws Exception
	 *             The exception.
	 */
	private void createInterfaceElement(final ICommandService commandService) throws Exception {
		final EObject createdObject = getcreatedElement(commandService, CREATE_INTERFACE_COMMAND);
		Assert.assertTrue("The created object must be an interface", createdObject instanceof org.eclipse.uml2.uml.Interface); //$NON-NLS-1$
	}

	/**
	 * Get the command from the command id an return its created element.
	 *
	 * @param commandService
	 *            The command service.
	 * @param commandId
	 *            The command is.
	 * @return The created element.
	 * @throws Exception
	 *             The exception.
	 */
	protected EObject getcreatedElement(final ICommandService commandService, final String commandId) throws Exception {
		// Get the command and the handler
		final Command cmd = commandService.getCommand(commandId);
		IHandler handler = cmd.getHandler();
		Assert.assertTrue("The command handler must be enabled for the creation", handler.isEnabled()); //$NON-NLS-1$

		// Execute the command with the correct parameter and check its result
		final ExecutionEvent event = new ExecutionEvent(cmd, Collections.EMPTY_MAP, null, null);
		Object res = cmd.executeWithChecks(event);
		fixture.flushDisplayEvents();
		Assert.assertTrue("Result of creation command must be a list", res instanceof List); //$NON-NLS-1$
		final List<?> resList = (List<?>) res;
		Assert.assertEquals("Only one list must be created", 1, resList.size()); //$NON-NLS-1$
		Assert.assertTrue("The first object of the list must be the list of created elements", resList.get(0) instanceof List); //$NON-NLS-1$
		final List<?> createdClasses = (List<?>) resList.get(0);
		Assert.assertEquals("Only one element must be created", 1, createdClasses.size()); //$NON-NLS-1$
		Assert.assertTrue("The created object must be an Element", createdClasses.get(0) instanceof org.eclipse.uml2.uml.Element); //$NON-NLS-1$
		return (EObject) createdClasses.get(0);
	}
}
