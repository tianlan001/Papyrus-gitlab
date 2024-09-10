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
 *   Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.clazz.test.dnd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.canonical.AbstractPapyrusTestCase;
import org.eclipse.papyrus.uml.diagram.common.editparts.ClassifierEditPart;
import org.eclipse.papyrus.uml.diagram.common.util.AssociationUtil;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.junit.Assert;
import org.junit.Test;

/**
 * The test class to verify that there is no regression in relation to Bug 492893.
 * Tests of the Drag and Drop via Model Explorer view to Class Diagram.
 */
public class DragAndDropAssociationsTest extends AbstractPapyrusTestCase {

	/** Message to inform that the diagram edit part is null. */
	private static final String DIAGRAM_EDITPART_NOT_NULL = "The Diagram Edit Part must not be null."; //$NON-NLS-1$
	/** Message to inform that the command isn't executable. */
	private static final String COMMAND_EXECUTABLE = "The command must be executable."; //$NON-NLS-1$
	/** Message to inform that the command is null. */
	protected static final String COMMAND_NOT_NULL = "The command must not be null."; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public DragAndDropAssociationsTest() {
		super();
	}

	/**
	 * Test drop Association.
	 */
	@Test
	public void testDropAssociationNotInverted() {
		IGraphicalEditPart sourceEditPart = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 0);
		IGraphicalEditPart targetEditPart = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 1);
		Association association = createAssociation(sourceEditPart, targetEditPart);
		checkAssociationSemantic(sourceEditPart, targetEditPart, association);
		doDrop(association, sourceEditPart, targetEditPart);
	}



	/**
	 * Test drop Association after a reorient of the target end.
	 */
	@Test
	public void testDropAssociationAfterReorientTarget() {
		IGraphicalEditPart sourceEditPart = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 0);
		IGraphicalEditPart targetEditPart = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 1);
		Association association = createAssociation(sourceEditPart, targetEditPart);
		checkAssociationSemantic(sourceEditPart, targetEditPart, association);
		IGraphicalEditPart newTargetEditPart = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 2);
		AssociationEditPart firstAssociationEditPart = getFirstAssociationEditPart();

		ReconnectRequest reconnectReq = new ReconnectRequest(RequestConstants.REQ_RECONNECT_TARGET);
		reconnectReq.setConnectionEditPart(firstAssociationEditPart);
		reconnectReq.setTargetEditPart(newTargetEditPart);
		Command command = newTargetEditPart.getCommand(reconnectReq);
		executeOnUIThread(command);

		doDrop(association, sourceEditPart, newTargetEditPart);
	}

	/**
	 * Test drop Association after a reorient of the source end.
	 */
	@Test
	public void testDropAssociationAfterReorientSource() {
		IGraphicalEditPart sourceEditPart = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 0);
		IGraphicalEditPart targetEditPart = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 1);
		Association association = createAssociation(sourceEditPart, targetEditPart);
		checkAssociationSemantic(sourceEditPart, targetEditPart, association);
		IGraphicalEditPart newSourceEditPart = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 2);
		AssociationEditPart firstAssociationEditPart = getFirstAssociationEditPart();

		ReconnectRequest reconnectReq = new ReconnectRequest(RequestConstants.REQ_RECONNECT_SOURCE);
		reconnectReq.setConnectionEditPart(firstAssociationEditPart);
		reconnectReq.setTargetEditPart(newSourceEditPart);
		Command command = newSourceEditPart.getCommand(reconnectReq);
		executeOnUIThread(command);

		doDrop(association, newSourceEditPart, targetEditPart);
	}

	/**
	 * 
	 * @param association
	 * @param sourceEditPart
	 * @param targetEditPart
	 */
	protected void doDrop(final Association association, final IGraphicalEditPart sourceEditPart, final IGraphicalEditPart targetEditPart) {
		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		ArrayList<Element> list = new ArrayList<Element>();
		list.add(association);
		dropObjectsRequest.setObjects(list);
		dropObjectsRequest.setLocation(new Point(20, 20));

		DiagramEditPart diagramEditPart = getDiagramEditPart();
		assertNotNull(DIAGRAM_EDITPART_NOT_NULL, diagramEditPart);

		List<AssociationEditPart> associationEdges = getAssociationEditParts(association);
		assertEquals(1, associationEdges.size());
		checkSourceTarget(associationEdges, (Classifier) sourceEditPart.resolveSemanticElement(), (Classifier) targetEditPart.resolveSemanticElement());

		Command command = diagramEditPart.getCommand(dropObjectsRequest);
		assertNotNull(COMMAND_NOT_NULL, command);
		assertTrue(COMMAND_EXECUTABLE, command.canExecute());
		executeOnUIThread(command);

		associationEdges = getAssociationEditParts(association);
		assertEquals(2, associationEdges.size());
		checkSourceTarget(associationEdges, (Classifier) sourceEditPart.resolveSemanticElement(), (Classifier) targetEditPart.resolveSemanticElement());

		command.undo();

		associationEdges = getAssociationEditParts(association);
		assertEquals(1, associationEdges.size());
		checkSourceTarget(associationEdges, (Classifier) sourceEditPart.resolveSemanticElement(), (Classifier) targetEditPart.resolveSemanticElement());

		command.redo();

		associationEdges = getAssociationEditParts(association);
		assertEquals(2, associationEdges.size());
		checkSourceTarget(associationEdges, (Classifier) sourceEditPart.resolveSemanticElement(), (Classifier) targetEditPart.resolveSemanticElement());
	}

	/**
	 * Check the source and the tagret end.
	 * 
	 * @param associationEditParts
	 * @param expectedSource
	 * @param expectedTarget
	 */
	protected void checkSourceTarget(final List<AssociationEditPart> associationEditParts, final Classifier expectedSource, final Classifier expectedTarget) {
		for (AssociationEditPart editPart : associationEditParts) {
			EditPart sourceEditPart = editPart.getSource();
			assertTrue(sourceEditPart instanceof ClassifierEditPart);
			EObject sourceElement = ((ClassifierEditPart) sourceEditPart).resolveSemanticElement();
			assertEquals(expectedSource, sourceElement);


			EditPart targetEditPart = editPart.getTarget();
			assertTrue(targetEditPart instanceof ClassifierEditPart);
			EObject targetElement = ((ClassifierEditPart) targetEditPart).resolveSemanticElement();
			assertEquals(expectedTarget, targetElement);

			checkMemberEnds((Association) editPart.resolveSemanticElement(), expectedSource, expectedTarget);
		}
	}

	/**
	 * Validate the source and target MemberEnds of an Edge.
	 *
	 * @param edge
	 *            The Edge.
	 * @param sourceType
	 *            The expected source Type.
	 * @param targetType
	 *            The expected target Type.
	 */
	protected void checkMemberEnds(final Association association, final Classifier sourceType, final Classifier targetType) {
		Property sourceEnd = AssociationUtil.getInitialSourceSecondEnd(association);
		Property targetEnd = AssociationUtil.getInitialTargetFirstEnd(association);
		assertEquals(sourceType, sourceEnd.getType());
		assertEquals(targetType, targetEnd.getType());
	}

	/**
	 * Get all the AssociationEditPart of an Association element.
	 * 
	 * @param association
	 *            The Association.
	 * @return The List of AssociationEdiPart.
	 */
	protected List<AssociationEditPart> getAssociationEditParts(final Association association) {
		List<AssociationEditPart> associationEditParts = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object> connections = diagramEditPart.getConnections();
		for (Object connection : connections) {
			if (connection instanceof AssociationEditPart) {
				if (((AssociationEditPart) connection).resolveSemanticElement().equals(association)) {
					associationEditParts.add((AssociationEditPart) connection);
				}
			}
		}
		return associationEditParts;
	}

	/**
	 * Get the only one AssociationEditPart of the DiagramEditPart.
	 * 
	 * @return The AssociationEditPart.
	 */
	protected AssociationEditPart getFirstAssociationEditPart() {
		// tHe diagram contains only one Association
		assertEquals(1, getDiagramEditPart().getConnections().size());
		Object connection = getDiagramEditPart().getConnections().get(0);
		assertNotNull(connection);
		Assert.assertTrue(connection instanceof AssociationEditPart);
		return (AssociationEditPart) connection;
	}

	/**
	 * Find the Association contained on a Diagram.
	 * 
	 * @param source
	 *            The source of the Association.
	 * @return The Association.
	 */
	protected Association findAssociation(final IGraphicalEditPart source) {
		assertEquals(1, getDiagramEditPart().getConnections().size());
		assertEquals(1, ((Classifier) source.resolveSemanticElement()).getAssociations().size());
		Association associationFromClassifier = ((Classifier) source.resolveSemanticElement()).getAssociations().get(0);
		Object connection = getDiagramEditPart().getConnections().get(0);
		assertNotNull(connection);
		Assert.assertTrue(connection instanceof AssociationEditPart);
		EObject associationSemantic = ((AssociationEditPart) connection).resolveSemanticElement();
		assertTrue("Created link is not association.", associationSemantic instanceof Association); //$NON-NLS-1$
		assertTrue("Diagram contains two different associations.", associationSemantic == associationFromClassifier); //$NON-NLS-1$
		return (Association) associationSemantic;
	}

	/**
	 * Check the source and target EditPart of an Association.
	 * 
	 * @param source
	 *            The expected source EditPart.
	 * @param target
	 *            The expected target EditPart.
	 * @param association
	 *            The Association.
	 */
	protected void checkAssociationSemantic(final IGraphicalEditPart source, final IGraphicalEditPart target, final Association association) {
		// check source semantic
		EObject sourceSemantic = source.resolveSemanticElement();
		assertTrue("Source should be StructuredClassifier.", sourceSemantic instanceof StructuredClassifier); //$NON-NLS-1$
		List<Property> sourceProperties = ((StructuredClassifier) sourceSemantic).getOwnedAttributes();
		assertEquals("Source owned attributes", 1, sourceProperties.size()); //$NON-NLS-1$
		Property sourceProperty = sourceProperties.get(0);
		assertEquals("Source property type", target.resolveSemanticElement(), sourceProperty.getType()); //$NON-NLS-1$
		// check target semantic
		EObject targetSemantic = target.resolveSemanticElement();
		assertTrue("Target should be StructuredClassifier.", targetSemantic instanceof StructuredClassifier); //$NON-NLS-1$
		assertEquals("Target owned attributes", 0, ((StructuredClassifier) targetSemantic).getOwnedAttributes().size()); //$NON-NLS-1$
		// check association semantic
		List<Property> associationProperties = association.getOwnedEnds();
		assertEquals("Association owned attributes", 1, associationProperties.size()); //$NON-NLS-1$
		Property associationProperty = associationProperties.get(0);
		assertEquals("Association property owner", association, associationProperty.eContainer()); //$NON-NLS-1$
		assertEquals("Association property type", sourceSemantic, associationProperty.getType()); //$NON-NLS-1$
	}

	/**
	 * Create a child on the diagram.
	 * 
	 * @param childType
	 *            The kind of child.
	 * @param container
	 *            The container of the new child.
	 * @param num
	 *            The index of the new child on the list of children.
	 * @return
	 */
	protected IGraphicalEditPart createChild(final IElementType childType, final IGraphicalEditPart container, final int num) {
		final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(childType, container.getDiagramPreferencesHint());
		requestcreation.setSize(new Dimension(1, 1));
		requestcreation.setLocation(new Point(10, 10));
		Command cmd = container.getCommand(requestcreation);
		executeOnUIThread(cmd);
		assertEquals("Probably, child was not created.", num + 1, getDiagramEditPart().getChildren().size()); //$NON-NLS-1$
		return (IGraphicalEditPart) getDiagramEditPart().getChildren().get(num);
	}

	/**
	 * Create an Association.
	 * 
	 * @param source
	 *            The source EditPart of the Association.
	 * @param target
	 *            The target EditPart of the Association.
	 * @return The new Association.
	 */
	protected Association createAssociation(final IGraphicalEditPart source, final IGraphicalEditPart target) {
		createLink(UMLDIElementTypes.ASSOCIATION_DIRECTED_EDGE, source, target);
		return findAssociation(source);
	}

	/**
	 * Create a new link.
	 * 
	 * @param type
	 *            The type of the link
	 * @param source
	 *            The source EditPart of the link.
	 * @param target
	 *            The target EditPart of the link.
	 */
	protected void createLink(final IElementType type, final EditPart source, final EditPart target) {
		Command endCommand = target.getCommand(createConnectionViewRequest(type, source, target));
		Assert.assertNotNull(endCommand);
		Assert.assertTrue(endCommand.canExecute());
		executeOnUIThread(endCommand);
		Assert.assertEquals(1, getDiagramEditPart().getConnections().size());
	}

	/**
	 * Create the Connection view request.
	 * 
	 * @param type
	 *            The type of the Connection.
	 * @param source
	 *            The source EditPart of the Connection.
	 * @param target
	 *            The Target EditPart of the Connection.
	 * @return The new CreateConnectionViewRequest.
	 */
	protected CreateConnectionViewRequest createConnectionViewRequest(final IElementType type, final EditPart source, final EditPart target) {
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
