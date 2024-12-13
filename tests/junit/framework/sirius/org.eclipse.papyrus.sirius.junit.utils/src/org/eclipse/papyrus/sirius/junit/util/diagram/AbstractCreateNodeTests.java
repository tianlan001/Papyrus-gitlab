/*****************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *  Obeo - Improvement on creation checks
 *****************************************************************************/
package org.eclipse.papyrus.sirius.junit.util.diagram;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.GraphicalOwnerUtils;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.viewpoint.DMappingBased;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.uml2.uml.Package;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Abstract Test for Node Creation
 */
public abstract class AbstractCreateNodeTests extends AbstractSiriusDiagramTests {

	/**
	 * the root of the model
	 */
	protected Package root;

	/**
	 * This method set up the test environment
	 */
	@Before
	public void setUp() {
		this.root = this.fixture.getModel();
	}

	/**
	 *
	 * @return
	 *         the semantic owner to use for the test
	 */
	protected abstract EObject getSemanticOwner();

	/**
	 *
	 * @return
	 *         the "top" node of the graphical container
	 */
	protected abstract EObject getTopGraphicalContainer();

	/**
	 *
	 * @param mappingID
	 *            the mapping used by the subnode
	 * @return
	 *         the subnode of the top graphical Container with the expected mappingID. basically this method is used to find a compartment in the node
	 */
	protected EObject getSubNodeOfGraphicalContainer(final String mappingID) {
		final EObject container = getTopGraphicalContainer();
		if (container instanceof DMappingBased && ((DMappingBased) container).getMapping().getName().equals(mappingID)) {
			return container;
		}
		if (container instanceof DNodeContainer) {
			for (final DDiagramElement diagramElement : ((DNodeContainer) container).getOwnedDiagramElements()) {
				if (mappingID.equals(diagramElement.getMapping().getName())) {
					return diagramElement;
				}
			}
		}
		if(container instanceof DNode) {
			for(final DDiagramElement diagramElement : ((DNode) container).getOwnedBorderedNodes()) {
				if(mappingID.equals(diagramElement.getMapping().getName())) {
					return diagramElement;
				}
			}
		}
		return null;
	}

	/**
	 * This method creates the node and checks that the diagram is unsynchronized
	 *
	 * @param creationToolId
	 *            the ID of the creation tool to used
	 * @param checker
	 *            the checker used to validate the creation of the node
	 * @param graphicalContainer
	 *            the graphical container to use to create the node
	 */
	protected void createNode(final String creationToolId, final SemanticAndGraphicalCreationChecker checker, final EObject graphicalContainer) {
		checkSiriusDiagramSynchronization(isSynchronization());
		int initialChildrenNumber = GraphicalOwnerUtils.getGraphicalOwnerChildrenSize(graphicalContainer);

		boolean result = applyCreationTool(creationToolId, getDDiagram(), graphicalContainer);
		Assert.assertTrue("The creation of element failed", result); //$NON-NLS-1$
		fixture.flushDisplayEvents();

		Assert.assertEquals("The graphical container must contains only 1 additional element after the creation of the node.", initialChildrenNumber + 1, GraphicalOwnerUtils.getGraphicalOwnerChildrenSize(graphicalContainer)); //$NON-NLS-1$
		// Use the last element in the list if the graphical container already contains elements.
		final List<? extends EObject> graphicalContainerChildren = GraphicalOwnerUtils.getChildren(graphicalContainer);
		final EObject createdElementRepresentation = graphicalContainerChildren.get(graphicalContainerChildren.size() - 1);
		Assert.assertTrue(createdElementRepresentation instanceof DRepresentationElement);

		checker.validateRepresentationElement((DRepresentationElement) createdElementRepresentation);

		// undo
		fixture.getEditingDomain().getCommandStack().undo();
		fixture.flushDisplayEvents();
		checker.validateAfterUndo();


		// redo
		fixture.getEditingDomain().getCommandStack().redo();
		fixture.flushDisplayEvents();
		checker.validateAfterRedo();
	}

	/**
	 * Applies the node creation tool with the provided parameters.
	 *
	 * @param creationToolId
	 *            the ID of the creation tool to use
	 * @param diagram
	 *            the diagram containing the tool to use
	 * @param graphicalContainer
	 *            the graphical container to use to create the node
	 * @return <code>true</code> if the tool could be applied, <code>false</code> otherwise
	 */
	protected boolean applyCreationTool(String creationToolId, DDiagram diagram, EObject graphicalContainer) {
		return fixture.applyContainerCreationTool(creationToolId, diagram, graphicalContainer);
	}

	/**
	 * This method clean the test environment
	 */
	@After
	public void tearDown() {
		this.root = null;
	}
}
