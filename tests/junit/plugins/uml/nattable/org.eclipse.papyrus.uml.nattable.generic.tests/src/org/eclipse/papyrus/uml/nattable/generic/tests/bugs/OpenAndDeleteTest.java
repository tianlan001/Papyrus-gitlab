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

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.core.resource.AbstractBaseModel;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ShowView;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allow to test the opening and the deletion of a table
 */
@PluginResource("resources/bugs/bug470811/OpenAndDeleteTest.di")
@ShowView(value = "org.eclipse.papyrus.views.modelexplorer.modelexplorer")
public class OpenAndDeleteTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "GenericTable0"; //$NON-NLS-1$


	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * Constructor.
	 */
	public OpenAndDeleteTest() {
		super();
	}

	/**
	 * This allows to test the deletion of the table.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testTableDeletion() throws Exception {
		final Model model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();

		// Get the editing domain
		TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Get the provider for the table
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(mainTable);
		Assert.assertNotNull("The provider cannot be null", provider); //$NON-NLS-1$

		// Retrieve delete command from the Element Edit service
		DestroyElementRequest request = new DestroyElementRequest(mainTable, false);
		Assert.assertNotNull("The request cannot be null", request); //$NON-NLS-1$

		ICommand deleteCommand = provider.getEditCommand(request);
		Assert.assertNotNull("The delete command cannot be null", deleteCommand); //$NON-NLS-1$

		// Add current EObject destroy command to the global command
		ICommand gmfCommand = null;
		gmfCommand = CompositeCommand.compose(gmfCommand, deleteCommand);
		Assert.assertNotNull("The GMF command cannot be null", gmfCommand); //$NON-NLS-1$

		// Execute the command
		editingDomain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(gmfCommand.reduce()));
		fixture.flushDisplayEvents();

		// Check the deletion
		checkNoNotation();

		// Undo the command
		editingDomain.getCommandStack().undo();
		mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		Assert.assertNotNull("The table was not re-created", mainTable); //$NON-NLS-1$
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();

		// Try to execute a resize command to check the re-creation
		IPageManager pageManager = fixture.getPageManager();
		List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		NatTableEditor editor = (NatTableEditor) part;
		INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		((NattableModelManager) currentManager).resizeHeader();

		// Redo the command
		editingDomain.getCommandStack().redo();
		checkNoNotation();
	}

	/**
	 * Check that no notation exist in the notation file (after deleted the table).
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	private void checkNoNotation() throws Exception {
		IModel notationModel = fixture.getModelSet().getModel(NotationModel.MODEL_ID);
		AbstractBaseModel notationBaseModel = null;
		if (notationModel instanceof AbstractBaseModel) {
			notationBaseModel = (AbstractBaseModel) notationModel;
		} else {
			Assert.fail("notation model is not an abstract base model"); //$NON-NLS-1$
		}
		Assert.assertEquals("The table was not deleted", 0, notationBaseModel.getResource().getContents().size()); //$NON-NLS-1$
	}

}
