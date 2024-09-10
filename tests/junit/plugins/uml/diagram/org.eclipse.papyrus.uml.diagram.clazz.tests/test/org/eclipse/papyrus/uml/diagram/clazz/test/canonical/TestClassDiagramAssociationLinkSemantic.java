/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.junit.Assert;
import org.junit.Test;

public class TestClassDiagramAssociationLinkSemantic extends AbstractPapyrusTestCase {

	@Test
	public void testAssociationLink() {
		IGraphicalEditPart source = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 0);
		IGraphicalEditPart target = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 1);
		Association association = createAssociation(source, target);
		checkAssociationSemantic(source, target, association);
	}

	@Test
	public void testAssociationLinkReorient() {
		IGraphicalEditPart source = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 0);
		IGraphicalEditPart target = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 1);
		Association association = createAssociation(source, target);
		checkAssociationSemantic(source, target, association);
		IGraphicalEditPart newSource = createChild(UMLElementTypes.Class_Shape, getDiagramEditPart(), 2);
		IElementEditService service = ElementEditServiceUtils.getCommandProvider(association);
		ReorientRelationshipRequest req = new ReorientRelationshipRequest(getEditingDomain(), association, newSource.resolveSemanticElement(), source.resolveSemanticElement(), ReorientRequest.REORIENT_SOURCE);
		ICommand cmd = service.getEditCommand(req);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		executeOnUIThread(GMFtoGEFCommandWrapper.wrap(cmd));
		association = findAssociation(newSource);
		checkAssociationSemantic(newSource, target, association);
	}

	private Association findAssociation(IGraphicalEditPart source) {
		assertEquals(1, getDiagramEditPart().getConnections().size());
		assertEquals(1, ((Classifier) source.resolveSemanticElement()).getAssociations().size());
		Association associationFromClassifier = ((Classifier) source.resolveSemanticElement()).getAssociations().get(0);
		Object connection = getDiagramEditPart().getConnections().get(0);
		assertNotNull(connection);
		Assert.assertTrue(connection instanceof AssociationEditPart);
		EObject associationSemantic = ((AssociationEditPart) connection).resolveSemanticElement();
		assertTrue("Created link is not association.", associationSemantic instanceof Association);
		assertTrue("Diagram contains two different associations.", associationSemantic == associationFromClassifier);
		return (Association) associationSemantic;
	}

	private void checkAssociationSemantic(IGraphicalEditPart source, IGraphicalEditPart target, Association association) {
		// check source semantic
		EObject sourceSemantic = source.resolveSemanticElement();
		assertTrue("Source should be StructuredClassifier.", sourceSemantic instanceof StructuredClassifier);
		List<Property> sourceProperties = ((StructuredClassifier) sourceSemantic).getOwnedAttributes();
		assertEquals("Source owned attributes", 1, sourceProperties.size());
		Property sourceProperty = sourceProperties.get(0);
		assertEquals("Source property type", target.resolveSemanticElement(), sourceProperty.getType());
		// check target semantic
		EObject targetSemantic = target.resolveSemanticElement();
		assertTrue("Target should be StructuredClassifier.", targetSemantic instanceof StructuredClassifier);
		assertEquals("Target owned attributes", 0, ((StructuredClassifier) targetSemantic).getOwnedAttributes().size());
		// check association semantic
		List<Property> associationProperties = association.getOwnedEnds();
		assertEquals("Association owned attributes", 1, associationProperties.size());
		Property associationProperty = associationProperties.get(0);
		assertEquals("Association property owner", association, associationProperty.eContainer());
		assertEquals("Association property type", sourceSemantic, associationProperty.getType());
	}

	private IGraphicalEditPart createChild(IElementType childType, IGraphicalEditPart container, int num) {
		final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(childType, container.getDiagramPreferencesHint());
		requestcreation.setSize(new Dimension(1, 1));
		requestcreation.setLocation(new Point(10, 10));
		Command cmd = container.getCommand(requestcreation);
		executeOnUIThread(cmd);
		assertEquals("Probably, child was not created.", num + 1, getDiagramEditPart().getChildren().size());
		return (IGraphicalEditPart) getDiagramEditPart().getChildren().get(num);
	}

	private Association createAssociation(IGraphicalEditPart source, IGraphicalEditPart target) {
		createLink(UMLDIElementTypes.ASSOCIATION_DIRECTED_EDGE, source, target);
		return findAssociation(source);
	}

	private void createLink(IElementType type, EditPart source, EditPart target) {
		Command endCommand = target.getCommand(createConnectionViewRequest(type, source, target));
		Assert.assertNotNull(endCommand);
		Assert.assertTrue(endCommand.canExecute());
		executeOnUIThread(endCommand);
		Assert.assertEquals(1, getDiagramEditPart().getConnections().size());
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
