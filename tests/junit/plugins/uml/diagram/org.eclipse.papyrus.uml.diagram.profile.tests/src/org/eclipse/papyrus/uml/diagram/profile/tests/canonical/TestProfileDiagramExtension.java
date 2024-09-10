package org.eclipse.papyrus.uml.diagram.profile.tests.canonical;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.common.commands.ShowHideElementsRequest;
import org.eclipse.papyrus.uml.diagram.profile.CreateProfileDiagramCommand;
import org.eclipse.papyrus.uml.diagram.profile.custom.edit.parts.CustomUMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.profile.custom.helper.MetaclassHelper;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.papyrus.uml.tools.providers.UMLMetaclassContentProvider;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Assert;
import org.junit.Test;

public class TestProfileDiagramExtension extends AbstractPapyrusTestCase {

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateProfileDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return "ProfileDiagramTestProject";
	}

	@Override
	protected String getFileName() {
		return "ProfileDiagramTest.profile.di";
	}

	@Test
	public void testDestroyExtensionProperty() {
		// create Stereotype
		GraphicalEditPart stereotypeEditPart = createNodeOnDiagram(UMLElementTypes.Stereotype_Shape, new Point(100, 100), 1);
		EObject stereotypeObject = ((View) stereotypeEditPart.getModel()).getElement();
		Assert.assertTrue("Expected StereotypeImpl.", stereotypeObject instanceof Stereotype);
		Stereotype stereotypeElement = (Stereotype) stereotypeObject;
		Assert.assertTrue("Stereotype owned attr list should be empty at this step.", stereotypeElement.getOwnedAttributes().isEmpty());
		IGraphicalEditPart propertyCompartment = findChildBySemanticHint(stereotypeEditPart, StereotypeAttributeCompartmentEditPart.VISUAL_ID);
		GraphicalEditPart metaClassEditPart = createMetaclassOnDiagram(new Point(100, 300), 2);
		// expected CSSConnectorImpl@6a2d9ede (type: StereotypeCommentLink) after the MetaclassEditPart creating
		int linksAtStart = 1;
		checkLinksCount(linksAtStart);
		// create a extension link between stereotype and metaclass
		createLink(UMLElementTypes.Extension_Edge, stereotypeEditPart, metaClassEditPart);
		checkLinksCount(linksAtStart + 1);
		Assert.assertEquals("Expected just one stereotype owned attr.", 1, stereotypeElement.getOwnedAttributes().size());
		Property extensionProperty = stereotypeElement.getOwnedAttributes().get(0);
		showStereotypePropertyView(stereotypeEditPart, propertyCompartment.getNotationView(), extensionProperty);
		Assert.assertEquals("Expected just one stereotype property attr.", 1, propertyCompartment.getChildren().size());
		IGraphicalEditPart stereotypePropertyEP = (IGraphicalEditPart) propertyCompartment.getChildren().get(0);
		Assert.assertTrue("stereotype property attr should be Property", ((View) stereotypePropertyEP.getModel()).getElement() instanceof Property);
		destroyEditPart(stereotypePropertyEP);
		Assert.assertTrue("We had removed stereotype property so owned attributes list should be empty.", stereotypeElement.getOwnedAttributes().isEmpty());
		checkLinksCount(linksAtStart);
	}

	private void checkLinksCount(int expected) {
		Assert.assertEquals("Diagram updater must detect that no link has been created", expected, CustomUMLDiagramUpdater.INSTANCE.getContainedLinks(getRootView()).size()); //$NON-NLS-1$
		Assert.assertEquals(CREATION + INITIALIZATION_TEST, expected, ((Diagram) getRootView()).getEdges().size());
	}

	private void showStereotypePropertyView(EditPart parentEP, View compartmentView, Property property) {
		ShowHideElementsRequest req = new ShowHideElementsRequest(compartmentView, property);
		Command cmd = parentEP.getCommand(req);
		assertNotNull(CREATION + COMMAND_NULL, cmd);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, cmd != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, cmd.canExecute()); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(cmd);
	}

	private IGraphicalEditPart findChildBySemanticHint(IGraphicalEditPart parent, String vid) {
		IGraphicalEditPart childEP = parent.getChildBySemanticHint(vid);
		assertNotNull("Parent " + parent + ", type " + parent.getNotationView() + " looking for: " + vid, childEP);
		return childEP;
	}

	private void destroyEditPart(EditPart currentEditPart) {
		Request deleteViewRequest = new EditCommandRequestWrapper(new DestroyElementRequest(false));
		Command command = currentEditPart.getCommand(deleteViewRequest);
		assertNotNull(DESTROY_DELETION + COMMAND_NULL, command);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		executeOnUIThread(command);
	}

	private GraphicalEditPart createMetaclassOnDiagram(Point loc, int childrensCount) {
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Class_MetaclassShape, getDiagramEditPart().getDiagramPreferencesHint());
		Profile parentProfile = MetaclassHelper.getParentProfile(getDiagramEditPart());
		List<Object> importedElements = Arrays.asList(new UMLMetaclassContentProvider(parentProfile).getElements());
		List<Object> forDialogSetup = new ArrayList<Object>();
		forDialogSetup.add(importedElements.get(0));
		MetaclassHelper.setupSuppressDialogRequest(requestcreation, forDialogSetup);
		assertTrue("Metaclass selection dialog should be supressed.", MetaclassHelper.shouldSuppressDialog(requestcreation));
		return createNodeOnDiagram(requestcreation, loc, childrensCount);
	}

	private GraphicalEditPart createNodeOnDiagram(IElementType nodeType, Point loc, int childrensCount) {
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(nodeType, getDiagramEditPart().getDiagramPreferencesHint());
		return createNodeOnDiagram(requestcreation, loc, childrensCount);
	}

	private GraphicalEditPart createNodeOnDiagram(CreateViewRequest request, Point loc, int childrensCount) {
		request.setLocation(loc);
		Command command = getDiagramEditPart().getCommand(request);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute()); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		return (GraphicalEditPart) getDiagramEditPart().getChildren().get(childrensCount - 1);
	}

	private void createLink(IElementType linkType, GraphicalEditPart source, GraphicalEditPart target) {
		Command command = target.getCommand(createConnectionViewRequest(linkType, source, target));
		Assert.assertNotNull(CREATION + COMMAND_NULL, command);
		Assert.assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
	}

	private CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target) {
		CreateConnectionViewRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type, ((IGraphicalEditPart) getDiagramEditPart()).getDiagramPreferencesHint());
		connectionRequest.setSourceEditPart(null);
		connectionRequest.setTargetEditPart(source);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		source.getCommand(connectionRequest);
		connectionRequest.setSourceEditPart(source);
		connectionRequest.setTargetEditPart(target);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		return connectionRequest;
	}
}
