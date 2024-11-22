/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.junit.Assert;

/**
 * Abstract GraphicalEdgeChecker for creation tests.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public abstract class AbstractGraphicalEdgeCreationChecker implements IGraphicalRepresentationElementCreationChecker {

	/**
	 * By default, when executing a creation tool, only one graphical element is created on the diagram.
	 */
	private static final int DEFAULT_CREATED_ELEMENTS_NUMBER = 1;

	/**
	 * The GMF Diagram.
	 */
	protected final Diagram diagram;

	/**
	 * The Sirius Diagram representation.
	 */
	protected final DDiagram semanticDiagram;

	/**
	 * The initial number of elements owned by the Sirius Diagram.
	 */
	protected final int nbSiriusDiagramOwnedChildren;

	/**
	 * The initial total number of elements in the Sirius Diagram.
	 */
	protected final int nbSiriusDiagramTotalElement;

	/**
	 * The graphical parent in which we will create the edge.
	 */
	protected final EObject graphicalParent;

	/**
	 * The initial number of children inside the graphical parent.
	 */
	protected final int nbGraphicalContainerChildren;

	/**
	 * The initial number of edge on the Sirius Diagram.
	 */
	protected final int nbGraphicalEdge;

	/**
	 * The expected number of additional edges.
	 */
	protected int expectedAdditionalEdges;

	/**
	 * The expected number of additional children in the container.
	 */
	protected int expectedAdditionalChildren;

	/**
	 * The expected number of additional created elements in the diagram.
	 */
	protected int expectedCreatedElements;

	/**
	 * 
	 * Constructor.
	 *
	 * @param diagram
	 *            the gmf diagram
	 * @param graphicalParent
	 *            the graphical parent
	 */
	public AbstractGraphicalEdgeCreationChecker(final Diagram diagram, final EObject graphicalParent) {
		this.diagram = diagram;
		this.semanticDiagram = (DDiagram) this.diagram.getElement();

		this.nbSiriusDiagramOwnedChildren = this.semanticDiagram.getOwnedDiagramElements().size();
		this.nbSiriusDiagramTotalElement = this.semanticDiagram.getDiagramElements().size();

		this.graphicalParent = graphicalParent;
		this.nbGraphicalContainerChildren = getGraphicalOwnerChildrenSize();
		this.nbGraphicalEdge = this.diagram.getEdges().size();

		this.expectedAdditionalEdges = DEFAULT_CREATED_ELEMENTS_NUMBER;
		this.expectedAdditionalChildren = DEFAULT_CREATED_ELEMENTS_NUMBER;
		this.expectedCreatedElements = DEFAULT_CREATED_ELEMENTS_NUMBER;

		Assert.assertNotEquals(-1, this.nbGraphicalContainerChildren);
		Assert.assertNotEquals(-1, this.nbGraphicalEdge);
	}

	/**
	 * 
	 * @return
	 *         the number of owned children in the graphical parent
	 */
	protected int getGraphicalOwnerChildrenSize() {
		return GraphicalOwnerUtils.getGraphicalOwnerChildrenSize(this.graphicalParent);
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementChecker#validateRepresentationElement(org.eclipse.sirius.viewpoint.DRepresentationElement)
	 *
	 * @param createdElementRepresentation
	 */

	@Override
	public void validateRepresentationElement(DRepresentationElement createdElementRepresentation) {
		// 1. check the type of
		checkCreatedElementInstanceOf(createdElementRepresentation);

		// 2. check the mapping type of the edge
		checkCreatedElementMapping(createdElementRepresentation);

		// 3. check the number of created element
		checkNumberOfCreatedElement(createdElementRepresentation);
	}


	/**
	 * check the type of the created element
	 * 
	 * @param createdElementRepresentation
	 *            the created {@link DRepresentationElement}
	 */
	protected void checkCreatedElementInstanceOf(final DRepresentationElement createdElementRepresentation) {
		Assert.assertTrue(NLS.bind("The created element must be a DEdge instead of a {0}.", createdElementRepresentation.eClass().getName()), createdElementRepresentation instanceof DEdge); //$NON-NLS-1$
	}

	/**
	 * Check the mapping of the created element
	 * 
	 * @param createdElementRepresentation
	 *            the created {@link DRepresentationElement}
	 */
	protected void checkCreatedElementMapping(final DRepresentationElement createdElementRepresentation) {
		Assert.assertEquals("The mapping is not the expected one.", getEdgeMappingType(), createdElementRepresentation.getMapping().getName()); //$NON-NLS-1$
	}

	/**
	 * Check the number of created element. In this implementation we always consider that 1 edge has been created.
	 * 
	 * @param createdElementRepresentation
	 *            the created {@link DRepresentationElement}
	 */
	protected void checkNumberOfCreatedElement(final DRepresentationElement createdElementRepresentation) {
		// check the parent of the created element is the expected one.
		Assert.assertEquals("The parent of the created graphical element is not the expected one.", this.graphicalParent, createdElementRepresentation.eContainer()); //$NON-NLS-1$

		// check we create only one edge
		Assert.assertEquals("The parent representation must contain only one additional element.", this.nbGraphicalContainerChildren + getExpectedAdditionalChildren(), getGraphicalOwnerChildrenSize()); //$NON-NLS-1$
		Assert.assertEquals("The diagram must contain one additional edge after creating an Edge", this.nbGraphicalEdge + getExpectedAdditionalEdges(), diagram.getEdges().size()); //$NON-NLS-1$

		// check the total number of created element in the diagram
		List<?> semanticChildren = this.semanticDiagram.getDiagramElements();
		Assert.assertEquals(this.nbSiriusDiagramTotalElement + getNumberOfExpectedCreatedElement(), semanticChildren.size());
	}

	/**
	 * Get the expected number of additional children in the container.
	 * 
	 * @return the expected number of additional children in the container.
	 */
	public int getExpectedAdditionalChildren() {
		return this.expectedAdditionalChildren;
	}

	/**
	 * Set the expected number of additional children in the container.
	 * 
	 * @param expectedChildren
	 *            the expected number of additional children in the container.
	 */
	public void setExpectedAdditionalChildren(int expectedChildren) {
		this.expectedAdditionalChildren = expectedChildren;
	}

	/**
	 * Get the expected number of additional edges.
	 * 
	 * @return the expected number of additional edges.
	 */
	public int getExpectedAdditionalEdges() {
		return this.expectedAdditionalEdges;
	}

	/**
	 * Set the expected number of additional edges.
	 * 
	 * @param expectedEdges
	 *            the expected number of additional edges.
	 */
	public void setExpectedAdditionalEdges(int expectedEdges) {
		this.expectedAdditionalEdges = expectedEdges;
	}

	/**
	 * Get the expected number of additional created elements in the diagram.
	 * 
	 * @return the expected number of additional created elements in the diagram.
	 */
	public int getNumberOfExpectedCreatedElement() {
		return this.expectedCreatedElements;
	}

	/**
	 * Set the expected number of additional created elements in the diagram.
	 * 
	 * @param expectedElements
	 *            the expected number of additional created elements in the diagram.
	 */
	public void setExpectedCreatedElements(int expectedElements) {
		this.expectedCreatedElements = expectedElements;
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementChecker#validateAfterUndo()
	 *
	 */

	@Override
	public void validateAfterUndo() {
		Assert.assertEquals("The diagram must contains the same number of elements as initially", this.nbSiriusDiagramTotalElement, this.semanticDiagram.getDiagramElements().size()); //$NON-NLS-1$
		Assert.assertEquals("The graphical owner must contains the same number of elements as initially", this.nbGraphicalContainerChildren, getGraphicalOwnerChildrenSize()); //$NON-NLS-1$
		Assert.assertEquals("The diagram must contain no more edges after undoing the creation of the Edge", this.nbGraphicalEdge, this.diagram.getEdges().size()); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementChecker#validateAfterRedo()
	 *
	 */

	@Override
	public void validateAfterRedo() {
		Assert.assertEquals(this.nbSiriusDiagramTotalElement + getNumberOfExpectedCreatedElement(), this.semanticDiagram.getDiagramElements().size());
		// check we create only one edge
		Assert.assertEquals("The parent edge must contain only one additional element.", this.nbGraphicalContainerChildren + getExpectedAdditionalChildren(), getGraphicalOwnerChildrenSize()); //$NON-NLS-1$
		Assert.assertEquals("The diagram must contain one additional edge after redoing the creation of the Edge", this.nbGraphicalEdge + getExpectedAdditionalEdges(), diagram.getEdges().size()); //$NON-NLS-1$
	}

	/**
	 * 
	 * @return
	 *         the expected mapping type for the created edge
	 */
	protected abstract String getEdgeMappingType();
}