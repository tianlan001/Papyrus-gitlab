package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;

public abstract class TestContextLink extends TestLink {

	protected int rootSemanticOwnedElementsAfterDestroy = 4;

	protected void manageContextLink(IElementType sourceType, IElementType targetType, IElementType linkType, IElementType containerType) {
		testToManageLink(sourceType, targetType, linkType, containerType, false, null);
		checkUnexecutableCreateLinkCommand(linkType, source, target);
		checkUnexecutableCreateLinkCommand(linkType, source, targetPlayer);
		testDestroy(linkType);
		checkExecutableCreateLinkCommand(linkType, source, target);
		checkExecutableCreateLinkCommand(linkType, source, targetPlayer);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		testViewDeletion(linkType);
		checkExecutableCreateLinkCommand(linkType, source, target);
		checkExecutableCreateLinkCommand(linkType, source, targetPlayer);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		testToManageDropConstraint();
	}

	@Override
	public void testToCreateALink(IElementType linkType, String initialName) {
		testCreateLink(linkType, initialName);
	}

	@Override
	public void testToManageLink(IElementType sourceType, IElementType targetType, IElementType linkType, IElementType containerType, boolean allowedOntheSame, String initialName) {
		installEnvironment(sourceType, targetType);
		testToCreateALink(linkType, initialName);
		testDestroy(linkType);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		testViewDeletion(linkType);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
	}

	protected void testToManageDropConstraint() {
		testConstraintViewDeletion();
		testDropConstraint();
	}

	// FIXME : this override must be removed and fixed!
	@Override
	public void testViewDeletion(IElementType type) {
		testDestroy(type);
	}

	@Override
	public void testDestroy(IElementType type) {
		// DESTROY SEMANTIC+ VIEW
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST, createdEdgesCount, calculateDiagramEdgesCount());
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST, createdEdgesCount, source.getSourceConnections().size());
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST, createdEdgesCount, getDiagramEditPart().getConnections().size());
		ConnectionEditPart linkEditPart = (ConnectionEditPart) getDiagramEditPart().getConnections().get(0);

		// Request deleteViewRequest = new GroupRequest(RequestConstants.REQ_DELETE);
		Command command = linkEditPart.getCommand((new EditCommandRequestWrapper(new DestroyElementRequest(false))));
		assertNotNull(DESTROY_DELETION + COMMAND_NULL, command);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);

		assertEquals(DESTROY_DELETION + TEST_THE_EXECUTION, 0, calculateDiagramEdgesCount());
		assertEquals(DESTROY_DELETION + TEST_THE_EXECUTION, 0, source.getSourceConnections().size());
		assertEquals(DESTROY_DELETION + TEST_THE_EXECUTION, rootSemanticOwnedElementsAfterDestroy, getRootSemanticModel().getOwnedElements().size());
		assertTrue(DESTROY_DELETION + TEST_THE_UNDO, diagramEditor.getDiagramEditDomain().getDiagramCommandStack().canUndo());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(DESTROY_DELETION + TEST_THE_UNDO, createdEdgesCount, calculateDiagramEdgesCount());
		assertEquals(DESTROY_DELETION + TEST_THE_UNDO, createdEdgesCount, source.getSourceConnections().size());
		assertEquals(DESTROY_DELETION + TEST_THE_UNDO, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST, 0, calculateDiagramEdgesCount());
		assertEquals(DESTROY_DELETION + TEST_THE_REDO, 0, source.getSourceConnections().size());
		assertEquals(DESTROY_DELETION + TEST_THE_REDO, rootSemanticOwnedElementsAfterDestroy, getRootSemanticModel().getOwnedElements().size());
	}

	private void testConstraintViewDeletion() {
		assertEquals(VIEW_DELETION + INITIALIZATION_TEST, 4, getRootEditPart().getChildren().size());
		assertEquals(VIEW_DELETION + INITIALIZATION_TEST, createdEdgesCount, calculateDiagramEdgesCount());
		assertEquals(VIEW_DELETION + INITIALIZATION_TEST, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		Request deleteViewRequest = new GroupRequest(RequestConstants.REQ_DELETE);
		Command command = source.getCommand(deleteViewRequest);
		assertNotNull(VIEW_DELETION + COMMAND_NULL, command);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(VIEW_DELETION + TEST_THE_EXECUTION, 3, getRootEditPart().getChildren().size());
		assertEquals(VIEW_DELETION + TEST_THE_EXECUTION, 0, calculateDiagramEdgesCount());
		assertEquals(VIEW_DELETION + TEST_THE_EXECUTION, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(VIEW_DELETION + TEST_THE_UNDO, 4, getRootEditPart().getChildren().size());
		assertEquals(VIEW_DELETION + TEST_THE_UNDO, createdEdgesCount, calculateDiagramEdgesCount());
		assertEquals(VIEW_DELETION + TEST_THE_UNDO, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(VIEW_DELETION + TEST_THE_REDO, 3, getRootEditPart().getChildren().size());
		assertEquals(VIEW_DELETION + TEST_THE_REDO, 0, calculateDiagramEdgesCount());
		assertEquals(VIEW_DELETION + TEST_THE_REDO, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
	}

	private void testDropConstraint() {
		assertEquals(DROP + INITIALIZATION_TEST, 3, getRootEditPart().getChildren().size());
		assertEquals(DROP + INITIALIZATION_TEST, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		assertEquals(DROP + INITIALIZATION_TEST, 0, calculateDiagramEdgesCount());
		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		ArrayList<Element> list = new ArrayList<>();
		list.add(((Element) target.resolveSemanticElement()).getOwnedElements().get(0));
		dropObjectsRequest.setObjects(list);
		dropObjectsRequest.setLocation(new Point(20, 20));
		Command command = getRootEditPart().getCommand(dropObjectsRequest);
		assertNotNull(DROP + COMMAND_NULL, command);
		assertTrue(DROP + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DROP + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(DROP + TEST_THE_EXECUTION, 4, getRootEditPart().getChildren().size());
		assertEquals(DROP + TEST_THE_EXECUTION, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		assertEquals(DROP + TEST_THE_EXECUTION, createdEdgesCount, calculateDiagramEdgesCount());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(DROP + TEST_THE_UNDO, 3, getRootEditPart().getChildren().size());
		assertEquals(DROP + TEST_THE_UNDO, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		assertEquals(DROP + TEST_THE_UNDO, 0, calculateDiagramEdgesCount());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(DROP + TEST_THE_REDO, 4, getRootEditPart().getChildren().size());
		assertEquals(DROP + TEST_THE_REDO, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		assertEquals(DROP + TEST_THE_REDO, createdEdgesCount, calculateDiagramEdgesCount());
	}

	@Override
	public void installEnvironment(IElementType sourceType, IElementType targetType) {
		rootSemanticOwnedElements = 3;
		super.installEnvironment(sourceType, targetType);
		assertTrue(CREATION + INITIALIZATION_TEST, source.resolveSemanticElement() instanceof Constraint);
	}

	private void checkUnexecutableCreateLinkCommand(IElementType linkType, GraphicalEditPart source, GraphicalEditPart target) {
		Command command = target.getCommand(createConnectionViewRequest(linkType, source, target));
		assertNull("Creation of the second context link from the constraint should be forbidden.", command);
	}

	private void checkExecutableCreateLinkCommand(IElementType linkType, GraphicalEditPart source, GraphicalEditPart target) {
		Command command = target.getCommand(createConnectionViewRequest(linkType, source, target));
		assertTrue(command != null && command.canExecute());
	}
}