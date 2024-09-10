/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.Activator;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;

public abstract class AbstractTestSequenceChildNode extends TestChildNode {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isSemanticTest() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateSequenceDiagramCommand();
	}

	/**
	 * Test change container.
	 *
	 * @param type
	 *        the type
	 * @param containerType
	 *        the container type
	 */
	@Override
	public void testChangeContainer(IElementType type, IElementType containerType) {
		//CHANGE CONTAINER
		assertEquals(CHANGE_CONTAINER + INITIALIZATION_TEST, 1, getContainerEditPart().getChildren().size());
		assertEquals(CHANGE_CONTAINER + INITIALIZATION_TEST, 1, getRootSemanticModel().getOwnedElements().size());

		final Request requestcreation = CreateViewRequestFactory.getCreateShapeRequest(containerType, getContainerEditPart().getDiagramPreferencesHint());

		command = null;
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				command = getContainerEditPart().getCommand(requestcreation);
			}
		});
		assertNotNull(CONTAINER_CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		executeOnUIThread(command);
		assertEquals(CONTAINER_CREATION + TEST_THE_EXECUTION, 2, getRootView().getChildren().size());
		GraphicalEditPart containerEditPart = (GraphicalEditPart)getContainerEditPart().getChildren().get(1);
		ChangeBoundsRequest changeBoundsRequest = new ChangeBoundsRequest(RequestConstants.REQ_ADD);
		changeBoundsRequest.setEditParts((EditPart)getContainerEditPart().getChildren().get(0));
		changeBoundsRequest.setLocation(new Point(30, 30));
		ShapeCompartmentEditPart compartment = null;
		int index = 0;
		while(compartment == null && index < containerEditPart.getChildren().size()) {
			if((containerEditPart.getChildren().get(index)) instanceof ShapeCompartmentEditPart) {
				compartment = (ShapeCompartmentEditPart)(containerEditPart.getChildren().get(index));
			}
			index++;
		}
		assertTrue("Container not found", compartment != null);

		command = compartment.getCommand(changeBoundsRequest);
		assertNotNull(CHANGE_CONTAINER, command);
		assertTrue(CHANGE_CONTAINER + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CHANGE_CONTAINER + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());

		// execute change container
		executeOnUIThread(command);
		assertEquals(CHANGE_CONTAINER + TEST_THE_EXECUTION, 1, getRootView().getChildren().size());
		assertEquals(CHANGE_CONTAINER + TEST_THE_EXECUTION, 1, getRootSemanticModel().getOwnedElements().size());
		assertEquals(CHANGE_CONTAINER + TEST_THE_EXECUTION, 1, compartment.getChildren().size());

		// undo change container
		undoOnUIThread();
		assertEquals(CHANGE_CONTAINER + TEST_THE_UNDO, 2, getRootView().getChildren().size());
		assertEquals(CHANGE_CONTAINER + TEST_THE_UNDO, 2, getRootSemanticModel().getOwnedElements().size());
		assertEquals(CHANGE_CONTAINER + TEST_THE_EXECUTION, 0, compartment.getChildren().size());

		// redo change container
		redoOnUIThread();
		assertEquals(CHANGE_CONTAINER + TEST_THE_REDO, 1, getRootView().getChildren().size());
		// assertEquals(CHANGE_CONTAINER + TEST_THE_REDO, 1, getRootSemanticModel().getOwnedElements().size());
		assertEquals(CHANGE_CONTAINER + TEST_THE_EXECUTION, 1, compartment.getChildren().size());
	}
	/**
	 * @see org.eclipse.papyrus.diagram.clazz.test.canonical.AbstractPapyrusTestCase#setUp()
	 *
	 * @throws Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		// Set the preference to "true" for the dialog to display existing link instead of create a new one.
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		final String alwaysCreateLinkPreferenceName = PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.RESTORE_LINK_ELEMENT);
		store.setValue(alwaysCreateLinkPreferenceName, true);
		store.setDefault(alwaysCreateLinkPreferenceName, true);

		suiteState.setupTest();
		CreateViewRequest requestcreation = createViewRequestShapeContainer();
		if(requestcreation != null) {
			requestcreation.setSize(new Dimension(500, 500));
			Command command = getDiagramEditPart().getCommand(requestcreation);
			assertNotNull(CONTAINER_CREATION + COMMAND_NULL, command);
			assertNotSame(CONTAINER_CREATION + TEST_IF_THE_COMMAND_IS_CREATED, UnexecutableCommand.INSTANCE, command);
			assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
			executeOnUIThread(command);
		}
		//due to the grid there is 2 editparts created
		assertEquals(CREATION + INITIALIZATION_TEST, 2, getDiagramEditPart().getChildren().size());
		GraphicalEditPart containerEditPart = (GraphicalEditPart)getDiagramEditPart().getChildren().get(0);
		rootCompartment = null;
		int index = 0;
		while(rootCompartment == null && index < containerEditPart.getChildren().size()) {
			if((containerEditPart.getChildren().get(index)) instanceof ShapeCompartmentEditPart) {
				rootCompartment = (ShapeCompartmentEditPart)(containerEditPart.getChildren().get(index));
			}
			index++;
		}
		assertNotNull("There should be at least one container for the node tests", rootCompartment); //$NON-NLS-1$
	}
}
