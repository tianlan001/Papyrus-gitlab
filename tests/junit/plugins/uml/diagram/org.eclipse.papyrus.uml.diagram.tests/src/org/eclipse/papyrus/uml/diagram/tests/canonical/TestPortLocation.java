package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;

public abstract class TestPortLocation extends AbstractPapyrusTestCase {

	protected abstract IElementType getPortType();

	protected void testPortLocation(IElementType container) {
		testPortLocation(container, null, null);
	}

	protected void testPortLocation(IElementType container, String containerCompartmentVisualId, IElementType child) {
		IGraphicalEditPart parentEP = createChild(getDiagramEditPart(), container);
		EObject containerSemantic = parentEP.resolveSemanticElement();
		if (containerCompartmentVisualId != null) {
			parentEP = findChildBySemanticHint(parentEP, containerCompartmentVisualId);
		}
		if (child != null) {
			parentEP = createChild(parentEP, child);
			EObject parentSemantic = parentEP.resolveSemanticElement();
			if (parentSemantic instanceof Property) {
				SetValueCommand setPropertyTypeCommand = new SetValueCommand(new SetRequest(parentSemantic, UMLPackage.eINSTANCE.getTypedElement_Type(), containerSemantic));
				executeOnUIThread(new GMFtoGEFCommandWrapper(setPropertyTypeCommand));
			}
		}
		Point parentLocation = new Point(100, 100);
		parentEP.getFigure().setBounds(new Rectangle(parentLocation, new Dimension(200, 200)));
		Point portLocation = new Point(100, 280);
		IGraphicalEditPart portEP = createChild(parentEP, getPortType(), portLocation);
		Port portSemantic = (Port) portEP.resolveSemanticElement();
		assertNotNull(portSemantic);
		checkPortPosition(portEP);
		testViewDeletion(portEP, child == null ? 1 : 0);
		testDrop(parentEP, portSemantic, portLocation, child == null ? 1 : 0);
		portEP = parentEP.getChildBySemanticHint(((IHintedType) getPortType()).getSemanticHint());
		checkPortPosition(portEP);
	}

	private void checkPortPosition(IGraphicalEditPart port) {
		assertNotNull(port);
		PortPositionLocator portPositionLocator = getConstraint(port);
		assertNotNull(portPositionLocator);
		assertNotNull(portPositionLocator.getConstraint());
		Assert.assertEquals(new Point(-10, 180), portPositionLocator.getConstraint().getLocation());
	}

	private IGraphicalEditPart findChildBySemanticHint(IGraphicalEditPart parent, String vid) {
		IGraphicalEditPart childEP = parent.getChildBySemanticHint(vid);
		assertNotNull("Parent " + parent + ", type " + parent.getNotationView() + " looking for: " + vid, childEP);
		return childEP;
	}

	private PortPositionLocator getConstraint(IGraphicalEditPart portEP) {
		if (false == portEP.getModel() instanceof View) {
			return null;
		}
		Object constraint = ((AbstractBorderedShapeEditPart) portEP.getParent()).getBorderedFigure().getBorderItemContainer().getLayoutManager().getConstraint(portEP.getFigure());
		if (false == constraint instanceof PortPositionLocator) {
			return null;
		}
		return (PortPositionLocator) constraint;
	}

	private IGraphicalEditPart createChild(IGraphicalEditPart container, IElementType childType) {
		return createChild(container, childType, new Point(0, 0));
	}

	private IGraphicalEditPart createChild(IGraphicalEditPart container, IElementType childType, Point location) {
		final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(childType, container.getDiagramPreferencesHint());
		@SuppressWarnings("unchecked")
		Map<Object, Object> params = requestcreation.getExtendedData();
		params.put(AspectUnspecifiedTypeCreationTool.INITIAL_MOUSE_LOCATION_FOR_CREATION, location);
		requestcreation.setSize(new Dimension(10, 10));
		requestcreation.setLocation(location);
		Command command = container.getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		executeOnUIThread(command);
		EditPart createdEditPart = (EditPart) container.getChildren().get((container.getChildren().size() - 1));
		Assert.assertNotNull("The editpart must be created", createdEditPart); //$NON-NLS-1$
		Assert.assertTrue(createdEditPart instanceof IGraphicalEditPart);
		return (IGraphicalEditPart) createdEditPart;
	}

	@SuppressWarnings("unchecked")
	private void testViewDeletion(EditPart forHide, int expectedChildViews) {
		assertEquals(VIEW_DELETION + INITIALIZATION_TEST, 1, getRootView().getChildren().size());
		List<View> childViewsBefore = ((View) getRootView().getChildren().get(0)).getChildren();
		int childViewsSize = childViewsBefore.size();
		Request deleteViewRequest = new GroupRequest(RequestConstants.REQ_DELETE);
		Command command = forHide.getCommand(deleteViewRequest);
		assertNotNull(VIEW_DELETION + COMMAND_NULL, command);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		// execute hide the view
		executeOnUIThread(command);
		List<View> childViewsAfter = ((View) getRootView().getChildren().get(0)).getChildren();
		assertEquals(VIEW_DELETION + " if the port has been hided.", expectedChildViews, childViewsSize - childViewsAfter.size());
		// undo hide the view
		undoOnUIThread();
		childViewsAfter = ((View) getRootView().getChildren().get(0)).getChildren();
		assertEquals(VIEW_DELETION + " UNDO if the port has been restored.", childViewsSize, childViewsAfter.size());
		// redo hide the view
		redoOnUIThread();
		childViewsAfter = ((View) getRootView().getChildren().get(0)).getChildren();
		assertEquals(VIEW_DELETION + "REDO if the port has been hided.", expectedChildViews, childViewsSize - childViewsAfter.size());
	}

	@SuppressWarnings("unchecked")
	private void testDrop(IGraphicalEditPart container, Port port, Point location, int expectedChildViews) {
		List<View> childViewsBefore = ((View) getRootView().getChildren().get(0)).getChildren();
		int childViewsBeforeSize = childViewsBefore.size();
		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		ArrayList<Element> list = new ArrayList<Element>();
		list.add(port);
		dropObjectsRequest.setObjects(list);
		dropObjectsRequest.setLocation(location);
		Command command = container.getCommand(dropObjectsRequest);
		assertNotNull(DROP + COMMAND_NULL, command);
		assertTrue(DROP + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DROP + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		// execute the drop
		executeOnUIThread(command);
		List<View> childViewsAfter = ((View) getRootView().getChildren().get(0)).getChildren();
		assertEquals(DROP + TEST_THE_EXECUTION, expectedChildViews, childViewsAfter.size() - childViewsBeforeSize);
		// undo the drop
		undoOnUIThread();
		childViewsAfter = ((View) getRootView().getChildren().get(0)).getChildren();
		Assert.assertEquals(DROP + TEST_THE_UNDO, childViewsAfter.size(), childViewsBeforeSize);
		// redo the drop
		redoOnUIThread();
		childViewsAfter = ((View) getRootView().getChildren().get(0)).getChildren();
		Assert.assertEquals(DROP + TEST_THE_REDO, expectedChildViews, childViewsAfter.size() - childViewsBeforeSize);
	}
}
