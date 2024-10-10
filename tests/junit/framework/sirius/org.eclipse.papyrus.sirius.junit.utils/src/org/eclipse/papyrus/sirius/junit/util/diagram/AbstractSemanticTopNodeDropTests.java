/*****************************************************************************
 * Copyright (c) 2022 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.junit.util.diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.rules.SiriusDiagramEditorFixture;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.DragAndDropTarget;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

/**
 * Abstract Class for Semantic Drop Top Node tests
 */
public abstract class AbstractSemanticTopNodeDropTests extends AbstractSiriusDiagramTests {

	/**
	 * this fixture is used to access to Papyrus environment (editor/diagram/commandstack/editingdomain/...)
	 */
	@Rule
	public final SiriusDiagramEditorFixture fixture = new SiriusDiagramEditorFixture();

	/**
	 * The root of the model
	 */
	protected Package root;

	protected Diagram diagram;

	protected DDiagram diagramRepresentation;

	/**
	 * Init the test field
	 */
	@Before
	public void setUp() {
		this.root = this.fixture.getModel();
		Assert.assertNotNull(this.root);
		final DiagramEditPart diagramEditpart = this.fixture.getActiveDiagram();
		this.diagram = diagramEditpart.getDiagramView();

		this.diagramRepresentation = (DDiagram) this.diagram.getElement();
	}

	/**
	 * 
	 * @param dropToolId
	 *            the id of the drop tool to use
	 * @param elementMappingType
	 *            the expected mapping type of the created view
	 * @param elementToBeDropped
	 *            the element to drop
	 */
	protected void dropNode(final String dropToolId, final IGraphicalRepresentationElementCreationChecker checker, final Element elementToBeDropped, final DragAndDropTarget dropTarget) {
		Assert.assertNotNull(dropTarget);

		boolean result = this.fixture.applyContainerDropDescriptionTool(this.diagramRepresentation, dropToolId, dropTarget, elementToBeDropped);
		Assert.assertTrue("The drop of element failed", result); //$NON-NLS-1$
		this.fixture.flushDisplayEvents();

		EObject createdElementRepresentation = null;
		if (dropTarget instanceof DNodeContainer) {
			createdElementRepresentation = ((DNodeContainer) dropTarget).getOwnedDiagramElements().get(0);
		}
		if (dropTarget instanceof DSemanticDiagram) {
			createdElementRepresentation = ((DSemanticDiagram) dropTarget).getOwnedDiagramElements().get(0);
		}

		Assert.assertTrue(createdElementRepresentation instanceof DRepresentationElement);

		checker.validateRepresentationElement((DRepresentationElement) createdElementRepresentation);

		// undo
		this.fixture.getEditingDomain().getCommandStack().undo();
		this.fixture.flushDisplayEvents();
		checker.validateAfterUndo();

		// redo
		this.fixture.getEditingDomain().getCommandStack().redo();
		this.fixture.flushDisplayEvents();

		checker.validateAfterRedo();
	}
	
	/**
	 * This method is used to check if the current diagram has the expected synchronization status
	 * 
	 * @param isSynchronized
	 *            <code>true</code> if the diagram must be synchronized
	 */
	@Override
	protected void checkSiriusDiagramSynchronization(final boolean isSynchronized) {
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		Assert.assertNotNull("We don't found a Sirius active diagram", siriusDiagram); //$NON-NLS-1$
		Assert.assertEquals("The synchronization status of the diagram is not the expected one", Boolean.valueOf(isSynchronized), Boolean.valueOf(siriusDiagram.isSynchronized())); //$NON-NLS-1$
	}
	
	/**
	 * Release resources
	 */
	@After
	public void tearDown() {
		this.root = null;
	}
}
