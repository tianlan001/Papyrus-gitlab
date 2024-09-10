/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.uml2.uml.Element;
import org.junit.Before;


public class AbstractTestSequenceAffixedChildNode extends AbstractPapyrusTestCase {


	private GraphicalEditPart containerEditPart;


	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		
		return null;
	}

	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		projectCreation();

		assertTrue(CREATION + INITIALIZATION_TEST, getDiagramEditPart().getChildren().size() == 2);
		containerEditPart = (GraphicalEditPart)getDiagramEditPart().getChildren().get(0);
	}


	/**
	 * Test to create a node.
	 *
	 * @param type
	 *        the type
	 */
	public void testToCreateANode(IElementType type, int subElementNumberGN) {

		//CREATION
		assertTrue(CREATION + INITIALIZATION_TEST, containerEditPart.getChildren().size() == subElementNumberGN + 1);
		assertTrue(CREATION + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == 0);

		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, containerEditPart.getDiagramPreferencesHint());
		Command command = containerEditPart.getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertNotSame(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command, UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		containerEditPart.refresh();
		assertEquals(CREATION + TEST_THE_EXECUTION, containerEditPart.getChildren().size(), subElementNumberGN + 2);
		assertEquals(CREATION + TEST_THE_EXECUTION, getRootSemanticModel().getOwnedElements().size(), 1);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(CREATION + TEST_THE_UNDO, containerEditPart.getChildren().size(), subElementNumberGN+1);
		assertEquals(CREATION + TEST_THE_UNDO, getRootSemanticModel().getOwnedElements().size(), 0);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals("CREATION: " + TEST_THE_REDO, containerEditPart.getChildren().size(), subElementNumberGN + 2);

	}

	/**
	 * Test view deletion.
	 *
	 * @param type
	 *        the type
	 */
	public void testViewDeletion(IElementType type, int subElementNumber) {
		//DELETION OF THE VIEW
		assertEquals(VIEW_DELETION + INITIALIZATION_TEST,subElementNumber+2, containerEditPart.getChildren().size());
		assertEquals(VIEW_DELETION + INITIALIZATION_TEST,1, getRootSemanticModel().getOwnedElements().size());

		Request deleteViewRequest = new GroupRequest(RequestConstants.REQ_DELETE);
		Command command = ((GraphicalEditPart)containerEditPart.getChildren().get(subElementNumber+1)).getCommand(deleteViewRequest);
		assertNotNull(VIEW_DELETION + COMMAND_NULL, command);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(VIEW_DELETION + TEST_THE_EXECUTION, subElementNumber+1,containerEditPart.getChildren().size());
		assertEquals(VIEW_DELETION + TEST_THE_EXECUTION,1, getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(VIEW_DELETION + TEST_THE_UNDO, subElementNumber+2,containerEditPart.getChildren().size());
		assertEquals(VIEW_DELETION + TEST_THE_UNDO, 1,getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(VIEW_DELETION + TEST_THE_REDO, subElementNumber+1,containerEditPart.getChildren().size());
		assertEquals(VIEW_DELETION + TEST_THE_REDO, 1,getRootSemanticModel().getOwnedElements().size());

	}


	/**
	 * Test destroy.
	 *
	 * @param type
	 *        the type
	 */
	public void testDestroy(IElementType type, int subElementNumber) {
		//DESTROY SEMANTIC+ VIEW
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST,  subElementNumber + 2,containerEditPart.getChildren().size());
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST,1, getRootSemanticModel().getOwnedElements().size());

		Request deleteViewRequest = new EditCommandRequestWrapper(new DestroyElementRequest(false));
		Command command = ((GraphicalEditPart)containerEditPart.getChildren().get(subElementNumber+1 )).getCommand(deleteViewRequest);
		assertNotNull(DESTROY_DELETION + COMMAND_NULL, command);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(DESTROY_DELETION + TEST_THE_EXECUTION, subElementNumber + 1,containerEditPart.getChildren().size());
		assertEquals(DESTROY_DELETION + TEST_THE_EXECUTION, 0,getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(DESTROY_DELETION + TEST_THE_UNDO, subElementNumber + 2,containerEditPart.getChildren().size());
		assertEquals(DESTROY_DELETION + TEST_THE_UNDO,1, getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(DESTROY_DELETION + TEST_THE_REDO, subElementNumber + 1,containerEditPart.getChildren().size());
		assertEquals(DESTROY_DELETION + TEST_THE_REDO,0, getRootSemanticModel().getOwnedElements().size());
	}


	/**
	 * Test drop.
	 *
	 * @param type
	 *        the type
	 */
	public void testDrop(IElementType type, int subElementNumber) {
		//DROP
		assertEquals(DROP + INITIALIZATION_TEST, subElementNumber+1,containerEditPart.getChildren().size() );
		assertEquals(DROP + INITIALIZATION_TEST, 1,getRootSemanticModel().getOwnedElements().size());


		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		ArrayList<Element> list = new ArrayList<Element>();
		list.add(getRootSemanticModel().getOwnedElements().get(0));
		dropObjectsRequest.setObjects(list);
		dropObjectsRequest.setLocation(new Point(20, 20));
		Command command = containerEditPart.getCommand(dropObjectsRequest);
		assertNotNull(DROP + COMMAND_NULL, command);
		assertTrue(DROP + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DROP + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(DROP + TEST_THE_EXECUTION,subElementNumber+2, containerEditPart.getChildren().size());
		assertEquals(DROP + TEST_THE_EXECUTION,1, getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(DROP + TEST_THE_UNDO, subElementNumber+1,containerEditPart.getChildren().size());
		assertEquals(DROP + TEST_THE_UNDO,1, getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(DROP + TEST_THE_REDO,subElementNumber+2, containerEditPart.getChildren().size());
		assertEquals(DROP + TEST_THE_REDO, 1,getRootSemanticModel().getOwnedElements().size());
	}


	/**
	 * Test to manage child node.
	 *
	 * @param type
	 *        the type
	 * @param containerType
	 *        the container type
	 */
	public void testToManageChildNode(IElementType type, int subElementNumberGN) {
		testToCreateANode(type, subElementNumberGN);
		// the node is still present
		testDestroy(type, subElementNumberGN);
		// the node has been destroyed, the UML element also
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		// the node and the UML element are present
		testViewDeletion(type, subElementNumberGN);
		// The node has been deleted, the uml element is still present
		testDrop(type, subElementNumberGN);

	}
}
