package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.uml.service.types.internal.ui.advice.InstanceSpecificationLinkEditHelperAdvice;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

public abstract class TestInstanceSpecification extends TestLink {

	private GraphicalEditPart class1;

	private GraphicalEditPart class2;

	private GraphicalEditPart instanceSpec1;

	private GraphicalEditPart instanceSpec2;

	private org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart association12;

	/**
	 * Test to manage InstanceSpecification.
	 */
	protected void testInstanceSpecification(IElementType classType, IElementType associationType, IElementType instanceSpecification, IElementType instanceSpecificationLink) {
		installEnvironment(classType, associationType, instanceSpecification);
		testInstanceSpecificationLinkCreation(instanceSpecificationLink, instanceSpec1, instanceSpec2, null, 2);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		setupEditPartClassifier(instanceSpec1.resolveSemanticElement(), class1.resolveSemanticElement());
		setupEditPartClassifier(instanceSpec2.resolveSemanticElement(), class1.resolveSemanticElement());
		testUnexecutableInstanceSpecificationLinkCreation(instanceSpecificationLink, instanceSpec1, instanceSpec2, (Association) association12.resolveSemanticElement(), 2);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		setupEditPartClassifier(instanceSpec1.resolveSemanticElement(), class1.resolveSemanticElement());
		setupEditPartClassifier(instanceSpec2.resolveSemanticElement(), class2.resolveSemanticElement());
		testInstanceSpecificationLinkCreation(instanceSpecificationLink, instanceSpec1, instanceSpec2, (Association) association12.resolveSemanticElement(), 2);
	}

	private void setupEditPartClassifier(EObject source, EObject classifier) {
		SetRequest setRequest = new SetRequest(source, UMLPackage.eINSTANCE.getInstanceSpecification_Classifier(), Arrays.asList(classifier));
		ICommand command = createSetupEditPartClassifierCommand(source, setRequest);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(GMFtoGEFCommandWrapper.wrap(command));
	}

	protected ICommand createSetupEditPartClassifierCommand(EObject source, SetRequest setRequest) {
		return new SetValueCommand(setRequest);
	}

	private void testInstanceSpecificationLinkCreation(IElementType instanceSpecType, GraphicalEditPart source, GraphicalEditPart target, Association association, int expectedEdges) {
		testToCreateInstanceSpecificationLink(instanceSpecType, source, target, association, true, expectedEdges);
	}

	private void testUnexecutableInstanceSpecificationLinkCreation(IElementType instanceSpecType, GraphicalEditPart source, GraphicalEditPart target, Association association, int expectedEdges) {
		testToCreateInstanceSpecificationLink(instanceSpecType, source, target, association, false, expectedEdges);
	}

	private void testToCreateInstanceSpecificationLink(IElementType instanceSpecType, GraphicalEditPart source, GraphicalEditPart target, Association association, boolean executable, int expectedEdges) {
		if (association != null) {
			Set<Association> associations = InstanceSpecificationLinkEditHelperAdvice.getModelAssociations((InstanceSpecification) source.resolveSemanticElement(), (InstanceSpecification) target.resolveSemanticElement());
			assertEquals(CREATION + "Associations list", executable, associations.contains(association));
		}
		if (!executable) {
			return;
		}
		CreateConnectionViewRequest request = createConnectionViewRequest(instanceSpecType, source, target);
		InstanceSpecificationLinkEditHelperAdvice.setupSuppressDialogRequest(request, association);
		testToCreateLink(request, source, target, expectedEdges);
	}

	private void testToCreateAssociation(IElementType associationType, GraphicalEditPart source, GraphicalEditPart target, int expectedEdges) {
		CreateConnectionViewRequest request = createConnectionViewRequest(associationType, source, target);
		testToCreateLink(request, source, target, expectedEdges);
	}

	private void testToCreateLink(CreateConnectionViewRequest request, GraphicalEditPart source, GraphicalEditPart target, int expectedEdges) {
		Command command = target.getCommand(request);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(CREATION + TEST_THE_EXECUTION, expectedEdges, ((Diagram) getRootView()).getEdges().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(CREATION + TEST_THE_REDO, expectedEdges, ((Diagram) getRootView()).getEdges().size());
	}

	private void installEnvironment(IElementType classType, IElementType associationType, IElementType instanceSpecification) {
		createNode(classType, new Point(100, 100));
		createNode(classType, new Point(400, 100));
		createNode(instanceSpecification, new Point(100, 300));
		createNode(instanceSpecification, new Point(400, 300));
		createNode(instanceSpecification, new Point(100, 600));
		class1 = (GraphicalEditPart) getDiagramEditPart().getChildren().get(0);
		class2 = (GraphicalEditPart) getDiagramEditPart().getChildren().get(1);
		instanceSpec1 = (GraphicalEditPart) getDiagramEditPart().getChildren().get(2);
		instanceSpec2 = (GraphicalEditPart) getDiagramEditPart().getChildren().get(3);
		testToCreateAssociation(associationType, class1, class2, 1);
		association12 = (org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart) class1.getSourceConnections().get(0);
	}

	private void createNode(IElementType sourceType, Point location) {
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(sourceType, getDiagramEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(location);
		Command command = getDiagramEditPart().getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute()); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
	}
}