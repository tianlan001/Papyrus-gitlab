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

package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.bugs;

import java.util.List;

import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allow to define the tests for the requirement reodering.
 */
@PluginResource("resources/bugs/bug439501/RequirementsReordering.di")
public class RequirementReorderingTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "RequirementTable0"; //$NON-NLS-1$

	/**
	 * The first requirement name.
	 */
	private static final String REQUIREMENT_1 = "Requirement1"; //$NON-NLS-1$

	/**
	 * The second requirement name.
	 */
	private static final String REQUIREMENT_2 = "Requirement2"; //$NON-NLS-1$

	/**
	 * The third requirement name.
	 */
	private static final String REQUIREMENT_3 = "Requirement3"; //$NON-NLS-1$

	/**
	 * The fourth requirement name.
	 */
	private static final String REQUIREMENT_4 = "Requirement4"; //$NON-NLS-1$


	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * The model reference.
	 */
	protected Model model;

	/**
	 * Constructor.
	 */
	public RequirementReorderingTest() {
		super();
	}

	/**
	 * This allow to initialize the tests.
	 */
	@Before
	public void init() {
		model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();
	}

	/**
	 * The test for the requirements reordering.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void testRequirementsReordering() throws Exception {
		// Open the table and get the manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		final INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(currentManager instanceof INattableModelManager);

		// check initial data
		checkInitialData(currentManager);

		final MoveCommand moveCommand = new MoveCommand(editor.getEditingDomain(), model, UMLPackage.eINSTANCE.getPackage_PackagedElement(), 1, 3);
		editor.getEditingDomain().getCommandStack().execute(moveCommand);
		fixture.flushDisplayEvents();

		// Check modified data
		checkModifiedData(currentManager);

		editor.getEditingDomain().getCommandStack().undo();
		fixture.flushDisplayEvents();

		// Check initial data
		checkInitialData(currentManager);

		editor.getEditingDomain().getCommandStack().redo();
		fixture.flushDisplayEvents();

		// Check modified data
		checkModifiedData(currentManager);
	}

	/**
	 * This allows to check the initial table data.
	 * 
	 * @param currentManager
	 *            The nattable model manager.
	 */
	protected void checkInitialData(final INattableModelManager currentManager) {
		final List<Object> rowElements = currentManager.getRowElementsList();

		final Object firstRowRepresentedElement = rowElements.get(0);
		Assert.assertEquals("The first row element is not the expected one", REQUIREMENT_1, ((NamedElement) firstRowRepresentedElement).getName()); //$NON-NLS-1$

		final Object secondRowRepresentedElement = rowElements.get(1);
		Assert.assertEquals("The second row element is not the expected one", REQUIREMENT_2, ((NamedElement) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		final Object thirdRowRepresentedElement = rowElements.get(2);
		Assert.assertEquals("The third row element is not the expected one", REQUIREMENT_3, ((NamedElement) thirdRowRepresentedElement).getName()); //$NON-NLS-1$

		final Object fourthRowRepresentedElement = rowElements.get(3);
		Assert.assertEquals("The fourth row element is not the expected one", REQUIREMENT_4, ((NamedElement) fourthRowRepresentedElement).getName()); //$NON-NLS-1$
	}

	/**
	 * This allows to check the modified table data.
	 * 
	 * @param currentManager
	 *            The nattable model manager.
	 */
	protected void checkModifiedData(final INattableModelManager currentManager) {
		final List<Object> rowElements = currentManager.getRowElementsList();

		final Object firstRowRepresentedElement = rowElements.get(0);
		Assert.assertEquals("The first row element is not the expected one", REQUIREMENT_1, ((NamedElement) firstRowRepresentedElement).getName()); //$NON-NLS-1$

		final Object secondRowRepresentedElement = rowElements.get(1);
		Assert.assertEquals("The second row element is not the expected one", REQUIREMENT_3, ((NamedElement) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		final Object thirdRowRepresentedElement = rowElements.get(2);
		Assert.assertEquals("The third row element is not the expected one", REQUIREMENT_4, ((NamedElement) thirdRowRepresentedElement).getName()); //$NON-NLS-1$

		final Object fourthRowRepresentedElement = rowElements.get(3);
		Assert.assertEquals("The fourth row element is not the expected one", REQUIREMENT_2, ((NamedElement) fourthRowRepresentedElement).getName()); //$NON-NLS-1$
	}
}
