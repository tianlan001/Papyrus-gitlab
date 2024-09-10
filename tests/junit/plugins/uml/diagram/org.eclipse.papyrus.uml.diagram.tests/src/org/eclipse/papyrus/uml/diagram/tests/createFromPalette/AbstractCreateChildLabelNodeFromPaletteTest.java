/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 464647
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.tests.createFromPalette;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;

import com.google.common.collect.Iterables;

/**
 * @author Juan Cadavid
 *
 */
public abstract class AbstractCreateChildLabelNodeFromPaletteTest extends AbstractPapyrusTestCase {
	private GraphicalEditPart parentNode;

	/**
	 * Test case for creation of a label node within a top node of the diagram.
	 */
	public void testCreateChildLabelNodeFromPalette(IElementType topNodeType, IElementType childNodeType, String compartmentType, boolean mustPass) {
		create(topNodeType);
		createChildNode(childNodeType, compartmentType);
	}

	/**
	 * Test case for creation of a label node within a node that is nested within a top node of the diagram.
	 */
	public void testCreateChildLabelNodeFromPalette(IElementType topNodeType, String topNodeCompartmentType, IElementType nestedNodeType, IElementType childNodeType, String compartmentType, boolean mustPass) {
		create(nestedNodeType, topNodeType, topNodeCompartmentType);
		createChildNode(childNodeType, compartmentType);
	}

	/**
	 * @param childNodeType
	 * @param compartmentType
	 * @param b
	 */
	private void createChildNode(IElementType childNodeType, String compartmentType) {
		ListCompartmentEditPart compartment = null;

		// Find the edit-part for the compartment
		for (Object editPart : getParentEditPart().getChildren()) {
			if (editPart instanceof ListCompartmentEditPart && (((View) ((ListCompartmentEditPart) (editPart)).getModel()).getType().equals(compartmentType))) { //$NON-NLS-1$
				compartment = (ListCompartmentEditPart) editPart;
			}
		}

		assertTrue("Container compartment not found", compartment != null); //$NON-NLS-1$
		// CREATION
		// assertTrue(CREATION + INITIALIZATION_TEST, compartment.getChildren().size() == 0);
		// assertTrue(CREATION + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == 0);
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(childNodeType, getDiagramEditPart().getDiagramPreferencesHint());
		Command command = compartment.getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertTrue(CREATION + TEST_THE_EXECUTION, compartment.getChildren().size() == 1);
		Assert.assertTrue("Node must be org.eclipse.gmf.runtime.notation.Shape", ((EditPart) compartment.getChildren().get(0)).getModel() instanceof Shape); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertTrue(CREATION + TEST_THE_UNDO, compartment.getChildren().size() == 0);
		assertTrue(CREATION + TEST_THE_UNDO, ((Element) ((View) getParentEditPart().getModel()).getElement()).getOwnedElements().size() == 0);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertTrue("CREATION: " + TEST_THE_REDO, compartment.getChildren().size() == 1); //$NON-NLS-1$

		Assert.assertNotEquals("Diagram updater must detect that children has been created", 0, getDiagramUpdater().getSemanticChildren(getRootView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link has been created", 0, getDiagramUpdater().getContainedLinks(getRootView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link are incoming", 0, getDiagramUpdater().getIncomingLinks(((GraphicalEditPart) compartment.getChildren().get(0)).getNotationView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link are outgoing", 0, getDiagramUpdater().getOutgoingLinks(((GraphicalEditPart) compartment.getChildren().get(0)).getNotationView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no children has ben created in the new element", 0, getDiagramUpdater().getSemanticChildren(((GraphicalEditPart) compartment.getChildren().get(0)).getNotationView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link has been created in the new element", 0, getDiagramUpdater().getContainedLinks(((GraphicalEditPart) compartment.getChildren().get(0)).getNotationView()).size()); //$NON-NLS-1$
	}

	/**
	 * Gets the top edit part.
	 *
	 * @return the top edit part
	 */
	public GraphicalEditPart getParentEditPart() {
		return parentNode;
	}

	public abstract DiagramUpdater getDiagramUpdater();


	/**
	 * Create a top node.
	 *
	 * @param type
	 *            the type
	 */
	public void create(IElementType type) {
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, getDiagramEditPart().getDiagramPreferencesHint());
		Command command = getDiagramEditPart().getCommand(requestcreation);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		parentNode = (GraphicalEditPart) getDiagramEditPart().getChildren().get(0);
	}

	/**
	 * Create a node nested within a top node.
	 */
	public void create(IElementType nestedNodeType, IElementType topNodeType, String compartmentType) {
		create(topNodeType);
		GraphicalEditPart topNode = parentNode;
		parentNode = null;

		// Find the edit-part for the compartment
		String compartmentVisualID = compartmentType;
		CompartmentEditPart compartment = null;
		for (CompartmentEditPart editPart : Iterables.filter(topNode.getChildren(), CompartmentEditPart.class)) {
			if (compartmentVisualID.equals(((View) editPart.getModel()).getType())) { // $NON-NLS-1$
				compartment = editPart;
			}
		}

		CreateViewRequest request = CreateViewRequestFactory.getCreateShapeRequest(nestedNodeType, getDiagramEditPart().getDiagramPreferencesHint());
		Command command = compartment.getCommand(request);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);

		// Scan the compartment to find the new child
		for (GraphicalEditPart child : Iterables.filter(compartment.getChildren(), GraphicalEditPart.class)) {
			if (nestedNodeType.getEClass().isInstance(child.resolveSemanticElement())) {
				parentNode = child;
				break;
			}
		}

		assertThat("Parent edit-part nested in top edit-part not found", parentNode, notNullValue());
	}
}
