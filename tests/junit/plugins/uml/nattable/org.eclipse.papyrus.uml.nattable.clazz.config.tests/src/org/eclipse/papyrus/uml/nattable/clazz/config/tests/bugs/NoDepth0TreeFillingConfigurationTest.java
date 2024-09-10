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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.bugs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerView;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to manage the bug 467706 (No Tree filling configuration for depth 0, add object and close then reopen it).
 */
@PluginResource("resources/bugs/bug467706/NoDepth0TreeFillingConfigurationTest.di") //$NON-NLS-1$
public class NoDepth0TreeFillingConfigurationTest extends AbstractPapyrusTest {

	/**
	 * The first class name in the model.
	 */
	private static final String FIRST_CLASS = "Class1"; //$NON-NLS-1$

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "ClassTreeTable"; //$NON-NLS-1$

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
	 * The model explorer part.
	 */
	public IViewPart modelExplorerPart = null;

	/**
	 * The model explorer view.
	 */
	public ModelExplorerView modelExplorerView = null;

	/**
	 * The nattable model manager.
	 */
	public INattableModelManager currentManager = null;

	/**
	 * Constructor.
	 */
	public NoDepth0TreeFillingConfigurationTest() {
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
	}

	/**
	 * This allows to test the move in the same parent.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testReopenedWithoutDepth0TreefillingConfiguration() throws Exception {
		// The table must be empty
		Assert.assertEquals("The table must be empty", 0, currentManager.getRowElementsList().size()); // $NON-NLS-N$

		// Get the editing domain
		TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Get the first class
		final EObject firstClass = model.getPackagedElement(FIRST_CLASS);

		final Collection<Object> objectsToAdd = new ArrayList<Object>(1);
		objectsToAdd.add(firstClass);

		// Add the element to the table
		final Command addCommand = currentManager.getAddRowElementCommand(objectsToAdd);

		// Execute the command
		editingDomain.getCommandStack().execute(addCommand);
		fixture.flushDisplayEvents();

		// Save the project
		IEditorPart part = fixture.getEditor().getActiveEditor();
		part.doSave(new NullProgressMonitor());

		// Close the table
		final Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().closePage(mainTable);
		fixture.flushDisplayEvents();

		// Reopen the table
		init();

		Assert.assertEquals("The table must be not empty", 1, currentManager.getRowElementsList().size()); //$NON-NLS-1$

		final IAxis firstRow = (IAxis) currentManager.getRowElementsList().get(0);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertEquals("The first element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) firstRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the first element is not corresponding to the first class", FIRST_CLASS, ((org.eclipse.uml2.uml.Class) firstRowRepresentedElement).getName()); //$NON-NLS-1$

		// Test the undo
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// Save the project
		part = fixture.getEditor().getActiveEditor();
		part.doSave(new NullProgressMonitor());

		// Close the table
		fixture.getPageManager().closePage(mainTable);
		fixture.flushDisplayEvents();

		// Reopen the table
		init();

		// The table must be empty
		Assert.assertEquals("The table must be empty", 0, currentManager.getRowElementsList().size()); // $NON-NLS-N$

		// Close the table
		fixture.getPageManager().closePage(mainTable);
		fixture.flushDisplayEvents();
	}

}