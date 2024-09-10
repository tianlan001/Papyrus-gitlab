/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.test.dnd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.menu.utils.DeleteActionUtil;
import org.eclipse.papyrus.uml.diagram.component.CreateComponentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.component.test.IComponentDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusSemanticTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestDropComponent extends AbstractPapyrusSemanticTestCase {

	protected IGraphicalEditPart topComponent;

	protected IGraphicalEditPart topCompartment;

	protected IGraphicalEditPart childComponent;

	@Before
	public void init() {
		topComponent = createChild(ComponentEditPart.VISUAL_ID, getDiagramEditPart());
		topCompartment = findChildBySemanticHint(topComponent, ComponentCompositeCompartmentEditPart.VISUAL_ID);
		childComponent = createChild(ComponentEditPartCN.VISUAL_ID, topCompartment);
	}

	@Test
	public void testDropChildComponentFromComponentToDiagram() {
		IGraphicalEditPart diagram = getDiagramEditPart();
		Request req = createDragDropRequest(childComponent);
		Command command = diagram.getCommand(req);
		assertNotNull("Drop component from component to diagram command must not be null", command);

		int diagramChildrenBefore = diagram.getChildren().size();
		executeOnUIThread(command);
		assertEquals(diagramChildrenBefore + 1, diagram.getChildren().size());
	}

	@Test
	public void testHideAndDropChildComponentToComponent() {
		EObject childComponentSemantic = childComponent.resolveSemanticElement();

		Command command = DeleteActionUtil.getDeleteFromDiagramCommand(childComponent);
		assertNotNull("ComponentEditPartCN should have posible to be deleted from diagram", command);
		int childrenBefore = topCompartment.getChildren().size();
		executeOnUIThread(command);
		assertEquals(childrenBefore - 1, topCompartment.getChildren().size());

		Request req = createDropObjectRequest(childComponentSemantic);
		command = topCompartment.getCommand(req);
		assertNotNull("ComponentEditPartCN must be dropable to top component editpart", command);
		execute(command);
		assertEquals(childrenBefore, topCompartment.getChildren().size());
	}

	@Test
	public void testHideChild_DropToDiagram_DropToParentComponent() {
		EObject childComponentSemantic = childComponent.resolveSemanticElement();
		IGraphicalEditPart diagram = getDiagramEditPart();

		Command command = DeleteActionUtil.getDeleteFromDiagramCommand(childComponent);
		assertNotNull("ComponentEditPartCN should have posible to be deleted from diagram", command);
		int childrenBefore = topCompartment.getChildren().size();
		executeOnUIThread(command);
		assertEquals(childrenBefore - 1, topCompartment.getChildren().size());

		Request req = createDropObjectRequest(childComponentSemantic);
		command = diagram.getCommand(req);
		int diagramCgildrenBefore = diagram.getChildren().size();
		assertNotNull("Component must be dropable to diagram", command);
		execute(command);
		assertEquals(diagramCgildrenBefore + 1, diagram.getChildren().size());

		IGraphicalEditPart componentToDrop = null;
		// find component to drop
		for (Object child : diagram.getChildren()) {
			IGraphicalEditPart childEP = (IGraphicalEditPart) child;
			if (childEP.resolveSemanticElement() == childComponentSemantic) {
				componentToDrop = childEP;
				break;
			}
		}
		assertNotNull("Cannot find dropped component to diagram", componentToDrop);
		req = createDragDropRequest(componentToDrop);
		command = topCompartment.getCommand(req);
		assertNotNull("ComponentEditPart must be dropable from diagram to self semantically parent", command);
		execute(command);
		assertEquals(diagramCgildrenBefore, diagram.getChildren().size());
		assertEquals(childrenBefore, topCompartment.getChildren().size());
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateComponentDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IComponentDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IComponentDiagramTestsConstants.FILE_NAME;
	}

	protected Request createDropObjectRequest(EObject objectToDrop) {
		DropObjectsRequest result = new DropObjectsRequest();
		result.setObjects(Arrays.asList(objectToDrop));
		result.setType(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_DROP_OBJECTS);
		result.setLocation(new Point(1, 1));
		return result;
	}

	protected Request createDragDropRequest(IGraphicalEditPart editPart) {
		ChangeBoundsRequest result = new ChangeBoundsRequest();
		result.setEditParts(editPart);
		result.setLocation(new Point(1, 1));
		result.setType(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_DROP);
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusSemanticTestCase#getTypeByID(java.lang.String)
	 */
	@Override
	protected IElementType getTypeByID(final String vid) {
		return UMLElementTypes.getElementType(vid);
	}


}
