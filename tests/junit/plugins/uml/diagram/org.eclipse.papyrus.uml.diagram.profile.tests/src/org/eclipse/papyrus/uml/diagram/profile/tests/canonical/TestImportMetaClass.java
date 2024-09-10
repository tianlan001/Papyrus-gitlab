package org.eclipse.papyrus.uml.diagram.profile.tests.canonical;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.profile.CreateProfileDiagramCommand;
import org.eclipse.papyrus.uml.diagram.profile.custom.helper.MetaclassHelper;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestTopNode;
import org.eclipse.papyrus.uml.tools.providers.UMLMetaclassContentProvider;
import org.eclipse.uml2.uml.Profile;
import org.junit.Assert;
import org.junit.Test;

public class TestImportMetaClass extends TestTopNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Package_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return (ICreationCommand) new CreateProfileDiagramCommand();
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
	public void testImportMetaClass() {
		// create Stereotype
		GraphicalEditPart stereotypeEditPart = createNodeOnDiagram(UMLElementTypes.Stereotype_Shape, new Point(100, 100), 1);
		// import metaclass
		GraphicalEditPart metaClassEditPart = createMetaclassOnDiagram(new Point(100, 300), 2);
		// create a extension link between stereotype and metaclass
		createALink(UMLElementTypes.Extension_Edge, stereotypeEditPart, metaClassEditPart);
		// test enable for delete metaclass from model
		testEnableForDeleteFromModel(metaClassEditPart);
		// delete metaclass from diagram
		deleteView(metaClassEditPart);
		// re-import metaclass
		metaClassEditPart = createMetaclassOnDiagram(new Point(100, 300), 2);
		assertNotNull("Expected not null MetaclassEditPart.", metaClassEditPart);
	}

	private void deleteView(GraphicalEditPart deletedEditPart) {
		// Delete view from diagram
		Request deleteViewRequest = new GroupRequest(RequestConstants.REQ_DELETE);
		Command command = deletedEditPart.getCommand(deleteViewRequest);
		assertNotNull(VIEW_DELETION + COMMAND_NULL, command);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
	}

	private GraphicalEditPart createMetaclassOnDiagram(Point loc, int childrenDiagramEditPartId) {
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Class_MetaclassShape, getDiagramEditPart().getDiagramPreferencesHint());
		Profile parentProfile = MetaclassHelper.getParentProfile(getDiagramEditPart());
		List<Object> importedElements = Arrays.asList(new UMLMetaclassContentProvider(parentProfile).getElements());
		List<Object> forDialogSetup = new ArrayList<Object>();
		forDialogSetup.add(importedElements.get(0));
		MetaclassHelper.setupSuppressDialogRequest(requestcreation, forDialogSetup);
		assertTrue("Metaclass selection dialog should be supressed.", MetaclassHelper.shouldSuppressDialog(requestcreation));
		return createNodeOnDiagram(requestcreation, loc, childrenDiagramEditPartId);
	}

	private GraphicalEditPart createNodeOnDiagram(IElementType nodeType, Point loc, int childrenDiagramEditPartId) {
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(nodeType, getDiagramEditPart().getDiagramPreferencesHint());
		return createNodeOnDiagram(requestcreation, loc, childrenDiagramEditPartId);
	}

	private GraphicalEditPart createNodeOnDiagram(CreateViewRequest request, Point loc, int childrenDiagramEditPartId) {
		request.setLocation(loc);
		Command command = getDiagramEditPart().getCommand(request);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		return (GraphicalEditPart) getDiagramEditPart().getChildren().get(childrenDiagramEditPartId - 1);
	}

	private void createALink(IElementType linkType, GraphicalEditPart source, GraphicalEditPart target) {
		Command command = target.getCommand(createConnectionViewRequest(linkType, source, target));
		Assert.assertNotNull(CREATION + COMMAND_NULL, command);
		Assert.assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
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
