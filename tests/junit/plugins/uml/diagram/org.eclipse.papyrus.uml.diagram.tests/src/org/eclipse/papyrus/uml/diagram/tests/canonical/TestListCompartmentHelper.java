package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AttributeOwner;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;

/**
 * ListCompartmentN test helper
 *
 */
public abstract class TestListCompartmentHelper extends AbstractPapyrusTest {

	private boolean myOperationFailed = false;

	private final DiagramEditPart myDiagramEditPart;

	/** The diagram editor. */
	private final UmlGmfDiagramEditor myDiagramEditor;

	public TestListCompartmentHelper(DiagramEditPart diagramEditPart, UmlGmfDiagramEditor diagramEditor) {
		myDiagramEditPart = diagramEditPart;
		myDiagramEditor = diagramEditor;
	}

	public void checkDuplicate(String parent, String parentContainer, String child) {
		checkDuplicate(parent, parentContainer, child, null);
	}

	public void checkDuplicate(String parent, String parentContainer, String child, String nestedChild) {
		IGraphicalEditPart parentEP = createChild(parent, myDiagramEditPart, 0);
		IGraphicalEditPart targetEP = findChildBySemanticHint(parentEP, parentContainer);
		IGraphicalEditPart dndEP = createChild(nestedChild != null ? nestedChild : child, targetEP, 0);
		{
			Command ddCommand = createDropCommand(dndEP, myDiagramEditPart);
			Assert.assertTrue(ddCommand.canExecute());
			executeOnUIThread(ddCommand);
		}
		IGraphicalEditPart canvasDnDEP = findChildBySemanticHint(myDiagramEditPart, nestedChild != null ? child : getDefaultNamedElementVisualId());
		{
			Command ddCommand = createDropCommand(canvasDnDEP, targetEP);
			Assert.assertFalse(canvasDnDEP.getClass().getName() + " can't be dropped to the " + targetEP.getClass().getName() + " due the duplication behavior rule.", ddCommand.canExecute());
		}
	}

	public void checkUnexecutableChildCreate(String targetVisualId, String targetCompartmentVisualId, String childVisualId) {
		IGraphicalEditPart targetEP = createChild(targetVisualId, myDiagramEditPart, 0);
		IGraphicalEditPart targetCompartmentEP = findChildBySemanticHint(targetEP, targetCompartmentVisualId);
		Command command = getCreateChildCommand(childVisualId, targetCompartmentEP);
		Assert.assertFalse("The " + childVisualId + "-visualId can't be created in the " + targetCompartmentEP.getClass().getName(), command.canExecute());
	}

	public void checkChildCreate(String targetVisualId, String targetCompartmentVisualId, String childVisualId) {
		IGraphicalEditPart targetEP = createChild(targetVisualId, myDiagramEditPart, 0);
		IGraphicalEditPart targetCompartmentEP = findChildBySemanticHint(targetEP, targetCompartmentVisualId);
		Command command = getCreateChildCommand(childVisualId, targetCompartmentEP);
		Assert.assertTrue("The " + childVisualId + "-visualId should be created in the " + targetCompartmentEP.getClass().getName(), command.canExecute());
	}

	public void checkUnexecutableDrop2Canvas(String targetVisualId, String targetCompartmentVisualId, String childVisualId) {
		IGraphicalEditPart targetEP = createChild(targetVisualId, myDiagramEditPart, 0);
		IGraphicalEditPart targetCompartmentEP = findChildBySemanticHint(targetEP, targetCompartmentVisualId);
		IGraphicalEditPart childEP = createChild(childVisualId, targetCompartmentEP, 0);
		EObject semantic = childEP.resolveSemanticElement();
		Assert.assertTrue(semantic != null && (semantic instanceof Property || semantic instanceof Operation));
		Command command = createDropCommand(childEP, myDiagramEditPart);
		Assert.assertFalse("The " + childEP.getClass().getName() + " can't be droped to the Canvas.", command.canExecute());
	}

	public void checkDropPropertyFromModelExplorer2Canvas(String targetVisualId, String targetCompartmentVisualId, String childVisualId) throws Exception {
		IGraphicalEditPart targetEP = createChild(targetVisualId, myDiagramEditPart, 0);
		IGraphicalEditPart targetCompartmentEP = findChildBySemanticHint(targetEP, targetCompartmentVisualId);
		IGraphicalEditPart childEP = createChild(childVisualId, targetCompartmentEP, 0);
		EObject semantic = childEP.resolveSemanticElement();
		Assert.assertTrue(semantic != null && (semantic instanceof Property || semantic instanceof Operation));
		List<EObject> forDrop = new ArrayList<EObject>();
		forDrop.add(semantic);
		Command ddCommand = createDropCommandFromModelExplorer2Canvas(forDrop);
		Assert.assertTrue(ddCommand.canExecute());
	}

	/**
	 * Requirement 20170700 An association end property could be dropped only in a container which is its semantic container
	 * @param targetVisualId
	 * @param targetCompartmentVisualId
	 * @param associationType
	 * @throws Exception
	 */
	public void checkDropAssociationEndPropertyFromModelExplorer(String targetVisualId, String targetCompartmentVisualId, IElementType associationType) throws Exception {
		IGraphicalEditPart sourceEP = createChild(targetVisualId, myDiagramEditPart, 0);
		IGraphicalEditPart targetEP = createChild(targetVisualId, myDiagramEditPart, 1);
		IGraphicalEditPart sourceCompartmentEP = findChildBySemanticHint(sourceEP, targetCompartmentVisualId);
		IGraphicalEditPart targetCompartmentEP = findChildBySemanticHint(targetEP, targetCompartmentVisualId);
		IGraphicalEditPart associationEP = createAssociationLink(associationType, sourceEP, targetEP);
		assertNotNull(associationEP);
		EObject sourceSemantic = sourceEP.resolveSemanticElement();
		assertTrue("Source should be StructuredClassifier.", sourceSemantic instanceof AttributeOwner);
		List<Property> sourceProperties = ((AttributeOwner) sourceSemantic).getOwnedAttributes();
		assertEquals("Source owned attributes", 1, sourceProperties.size());
		Property sourceProperty = sourceProperties.get(0);
		List<EObject> forDrop = new ArrayList<EObject>();
		forDrop.add(sourceProperty);
		Command ddCommand = createDropCommandFromModelExplorer(forDrop, sourceCompartmentEP);
		Assert.assertTrue(ddCommand != null || true == ddCommand.canExecute());
		
		ddCommand = createDropCommandFromModelExplorer(forDrop, targetCompartmentEP);
		Assert.assertTrue(ddCommand == null || false == ddCommand.canExecute());
	}

	private IGraphicalEditPart createAssociationLink(IElementType type, EditPart source, EditPart target) {
		Command endCommand = target.getCommand(createConnectionViewRequest(type, source, target));
		Assert.assertNotNull(endCommand);
		Assert.assertTrue(endCommand.canExecute());
		executeOnUIThread(endCommand);
		Assert.assertEquals(1, myDiagramEditPart.getConnections().size());
		IGraphicalEditPart association = (IGraphicalEditPart) myDiagramEditPart.getConnections().get(0);
		Assert.assertTrue(association.resolveSemanticElement() instanceof Association);
		return association;
	}

	private CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target) {
		CreateConnectionViewRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type, myDiagramEditPart.getDiagramPreferencesHint());
		connectionRequest.setSourceEditPart(null);
		connectionRequest.setTargetEditPart(source);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		source.getCommand(connectionRequest);
		connectionRequest.setSourceEditPart(source);
		connectionRequest.setTargetEditPart(target);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		return connectionRequest;
	}

	private Command createDropCommandFromModelExplorer2Canvas(List<EObject> elements) {
		return myDiagramEditPart.getCommand(createDropRequestFromModelExplorer(elements));
	}

	private Command createDropCommandFromModelExplorer(List<EObject> elements, IGraphicalEditPart target) {
		return target.getCommand(createDropRequestFromModelExplorer(elements));
	}

	private DropObjectsRequest createDropRequestFromModelExplorer(List<EObject> elements) {
		DropObjectsRequest req = new DropObjectsRequest();
		req.setObjects(elements);
		req.setAllowedDetail(DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK);
		req.setLocation(new Point(15, 15));
		req.setRequiredDetail(DND.DROP_COPY);
		req.setType(RequestConstants.REQ_DROP_OBJECTS);
		return req;
	}

	private Command getCreateChildCommand(String childVID, IGraphicalEditPart container) {
		final IElementType childType = getElementType(childVID);
		final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(childType, container.getDiagramPreferencesHint());
		requestcreation.setSize(new Dimension(1, 1));
		requestcreation.setLocation(new Point(10, 10));
		return container.getCommand(requestcreation);
	}

	public void checkUnexecutableDrop(String targetVisualId, String targetCompartmentVisualId, String dropableVisualId) {
		IGraphicalEditPart targetEP = createChild(targetVisualId, myDiagramEditPart, 0);
		IGraphicalEditPart dropableEP = createChild(dropableVisualId, myDiagramEditPart, 1);
		IGraphicalEditPart targetCompartmentEP = findChildBySemanticHint(targetEP, targetCompartmentVisualId);
		Command command = createDropCommand(dropableEP, targetCompartmentEP);
		Assert.assertFalse("The " + dropableEP.getClass().getName() + " can't be droped to the " + targetCompartmentEP.getClass().getName(), command.canExecute());
	}

	protected abstract String getDefaultNamedElementVisualId();

	protected abstract IElementType getElementType(String childVID);

	protected abstract String getVisualID(View view);

	private void executeOnUIThread(final Command command) {
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				execute(command);
			}
		});
	}

	private void assertLastOperationSuccessful() {
		Assert.assertFalse("The operation failed. Look at the log, or put a breakpoint on ExecutionException or DefaultOperationHistory#notifyNotOK to find the cause.", this.myOperationFailed); //$NON-NLS-1$
	}

	/**
	 * Reset the "operation failed" state. Call this before executing each
	 * operation for which you want to test whether if failed with {@link assertLastOperationSuccessful()}.
	 */
	private void resetLastOperationFailedState() {
		myOperationFailed = false;
	}

	/** Execute the given command in the diagram editor. */
	private void execute(final Command command) {
		resetLastOperationFailedState();
		getCommandStack().execute(new GEFtoEMFCommandWrapper(command));
		assertLastOperationSuccessful();
	}

	/** The command stack to use to execute commands on the diagram. */
	private CommandStack getCommandStack() {
		// not "diagramEditor.getDiagramEditDomain().getDiagramCommandStack()"
		// because it messes up undo contexts
		return myDiagramEditor.getEditingDomain().getCommandStack();
	}

	private Command createDropCommand(IGraphicalEditPart child, IGraphicalEditPart newParent) {
		CompoundCommand c = new CompoundCommand();
		ChangeBoundsRequest changeBoundsRequest = new ChangeBoundsRequest(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_DRAG);
		changeBoundsRequest.setEditParts(child);
		changeBoundsRequest.setLocation(new Point(15, 15));
		c.add(child.getCommand(changeBoundsRequest));
		myDiagramEditPart.getEditingDomain().getCommandStack().execute(new GEFtoEMFCommandWrapper(c));
		changeBoundsRequest.setType(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_DROP);
		c.add(newParent.getCommand(changeBoundsRequest));
		return c;
	}

	private IGraphicalEditPart createChild(String childVID, IGraphicalEditPart container, int number) {
		Command cmd = getCreateChildCommand(childVID, container);
		executeOnUIThread(cmd);
		return findChild(container, childVID, number);
	}


	private IGraphicalEditPart findChild(IGraphicalEditPart parent, String vid, int number) {
		List<?> children = parent.getChildren();
		if (number > children.size() - 1) {
			Assert.fail("Parent " + parent + ", type " + parent.getNotationView() + " hasn't less children then " + number);
		}
		IGraphicalEditPart childEP = (IGraphicalEditPart) children.get(number);
		assertNotNull("Parent " + parent + ", type " + parent.getNotationView() + " looking for: " + vid, childEP);
		Assert.assertEquals("Parent " + parent + ", type " + parent.getNotationView() + " looking for: " + vid, getVisualID(childEP.getNotationView()), vid);
		return childEP;
	}

	private IGraphicalEditPart findChildBySemanticHint(IGraphicalEditPart parent, String vid) {
		IGraphicalEditPart childEP = parent.getChildBySemanticHint(vid);
		assertNotNull("Parent " + parent + ", type " + parent.getNotationView() + " looking for: " + vid, childEP);
		return childEP;
	}
}
