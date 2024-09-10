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
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
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
@PluginResource("resources/bugs/bug495312/MoveElementInSynchronizedTable.di") //$NON-NLS-1$
public class MoveElementInSynchronizedTableTest extends AbstractPapyrusTest {

	/**
	 * The activity name in the model.
	 */
	private static final String ACTIVITY_NAME = "Activity1"; //$NON-NLS-1$

	/**
	 * The first accept event action name in the model.
	 */
	private static final String ACCEPT_EVENT_ACTION_1 = "AcceptEventAction1"; //$NON-NLS-1$

	/**
	 * The second accept event action name in the model.
	 */
	private static final String ACCEPT_EVENT_ACTION_2 = "AcceptEventAction2"; //$NON-NLS-1$

	/**
	 * The third accept event action name in the model.
	 */
	private static final String ACCEPT_EVENT_ACTION_3 = "AcceptEventAction3"; //$NON-NLS-1$
	
	/**
	 * The fourth accept event action name in the model.
	 */
	private static final String ACCEPT_EVENT_ACTION_4 = "AcceptEventAction4"; //$NON-NLS-1$

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
	public MoveElementInSynchronizedTableTest() {
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
	 * This allows to test the move of an accept event action of the activity.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testMoveElementInSynchronizedTable() throws Exception {
		checkInitialContent();

		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Destroy the row element
		final IAxisManager axisManager = currentManager.getRowAxisManager();
		Assert.assertNotNull(axisManager);
		
		// Get the activity
		final EObject activity = model.getPackagedElement(ACTIVITY_NAME);
		
		List<EObject> nodes = new ArrayList<EObject>((List<EObject>) activity.eGet(UMLPackage.eINSTANCE.getActivity_OwnedNode()));
		EObject second = nodes.remove(1);
		nodes.add(2, second);
		
		final IEditCommandRequest request = new SetRequest(editingDomain, activity, UMLPackage.eINSTANCE.getActivity_OwnedNode(), nodes);
		final IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(activity);
		
		// Execute the command
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(commandProvider.getEditCommand(request)));
		fixture.flushDisplayEvents();

		checkAfterMove();

		undo(editingDomain);
		checkInitialContent();

		redo(editingDomain);
		checkAfterMove();
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

		checkTableContent(false);
	}

	/**
	 * This allows to test the content of the table (size and row elements) when the class is removed.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkAfterMove() throws Exception {
		final List<Object> rowElements = currentManager.getRowElementsList();
		Assert.assertEquals("The table content size is not the correct one", 7, rowElements.size()); //$NON-NLS-1$
		
		checkTableContent(true);
	}

	/**
	 * This allows to check the table content with/without attribute, operation and parameter.
	 * 
	 * @param hasMove
	 *            Boolean to determinate if the move action was done.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkTableContent(final boolean hasMove) throws Exception {
		int index = 0;

		final List<Object> rowElements = currentManager.getRowElementsList();

		final IAxis firstRow = (IAxis) rowElements.get(index++);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertTrue("The first row must be a tree filling configuration", firstRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$

		final IAxis secondRow = (IAxis) rowElements.get(index++);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals("The first element must be a class", UMLPackage.eINSTANCE.getActivity(), ((EObject) secondRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the first element is not corresponding to the activity", ACTIVITY_NAME, ((org.eclipse.uml2.uml.Activity) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis thirdRow = (IAxis) rowElements.get(index++);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertTrue("The third row must be a tree filling configuration", thirdRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
	
		final IAxis fourthRow = (IAxis) rowElements.get(index++);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals("The second element must be an accept event action", UMLPackage.eINSTANCE.getAcceptEventAction(), ((EObject) fourthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the second element is not corresponding to the first accept event action", ACCEPT_EVENT_ACTION_1, ((org.eclipse.uml2.uml.AcceptEventAction) fourthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fifthRow = (IAxis) rowElements.get(index++);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals("The third element must be an accept event action", UMLPackage.eINSTANCE.getAcceptEventAction(), ((EObject) fifthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the third element is not corresponding to the second accept event action", hasMove ? ACCEPT_EVENT_ACTION_3 : ACCEPT_EVENT_ACTION_2, ((org.eclipse.uml2.uml.AcceptEventAction) fifthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis sixthRow = (IAxis) rowElements.get(index++);
		final Object sixthRowRepresentedElement = sixthRow.getElement();
		Assert.assertEquals("The fourth element must be an accept event action", UMLPackage.eINSTANCE.getAcceptEventAction(), ((EObject) sixthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fourth element is not corresponding to the third accept event action", hasMove ? ACCEPT_EVENT_ACTION_2 : ACCEPT_EVENT_ACTION_3, ((org.eclipse.uml2.uml.AcceptEventAction) sixthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis seventhRow = (IAxis) rowElements.get(index++);
		final Object seventhRowRepresentedElement = seventhRow.getElement();
		Assert.assertEquals("The fifth element must be an accept event action", UMLPackage.eINSTANCE.getAcceptEventAction(), ((EObject) seventhRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fifth element is not corresponding to the fourth accept event action", ACCEPT_EVENT_ACTION_4, ((org.eclipse.uml2.uml.AcceptEventAction) seventhRowRepresentedElement).getName()); //$NON-NLS-1$
	}
}