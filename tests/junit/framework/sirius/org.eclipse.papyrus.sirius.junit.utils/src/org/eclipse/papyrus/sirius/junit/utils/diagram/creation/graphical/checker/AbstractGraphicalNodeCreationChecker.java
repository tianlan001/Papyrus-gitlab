/*****************************************************************************
 * Copyright (c) 2022, 2024. CEA LIST
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
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.junit.Assert;

/**
 * Abstract GraphicalNodeChecker for creation tests.
 */
public abstract class AbstractGraphicalNodeCreationChecker implements IGraphicalRepresentationElementCreationChecker {

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
	public AbstractGraphicalNodeCreationChecker(final Diagram diagram, final EObject graphicalParent) {
		this.diagram = diagram;
		this.semanticDiagram = (DDiagram) this.diagram.getElement();

		this.nbSiriusDiagramOwnedChildren = this.semanticDiagram.getOwnedDiagramElements().size();
		this.nbSiriusDiagramTotalElement = this.semanticDiagram.getDiagramElements().size();

		this.graphicalParent = graphicalParent;
		this.nbGraphicalContainerChildren = getGraphicalOwnerChildrenSize();

		this.expectedAdditionalChildren = DEFAULT_CREATED_ELEMENTS_NUMBER;
		this.expectedCreatedElements = DEFAULT_CREATED_ELEMENTS_NUMBER;

		Assert.assertNotEquals(-1, this.nbGraphicalContainerChildren);
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
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementChecker.tests.checkers.internal.api.IGraphicalNodeChecker#validateRepresentationElement(org.eclipse.sirius.viewpoint.DRepresentationElement)
	 *
	 * @param createdElementRepresentation
	 */

	@Override
	public void validateRepresentationElement(DRepresentationElement createdElementRepresentation) {
		// 1. check the type of
		checkCreatedElementInstanceOf(createdElementRepresentation);

		// 2. check the mapping type of the node
		checkCreatedElementMapping(createdElementRepresentation);

		// 3. check the number of created element
		checkNumberOfCreatedElement(createdElementRepresentation);
	}


	/**
	 * check the instance of on the created element
	 * 
	 * @param createdElementRepresentation
	 *            the created {@link DRepresentationElement}
	 */
	protected abstract void checkCreatedElementInstanceOf(final DRepresentationElement createdElementRepresentation);

	/**
	 * Check the mapping of the created element
	 * 
	 * @param createdElementRepresentation
	 *            the created {@link DRepresentationElement}
	 */
	protected void checkCreatedElementMapping(final DRepresentationElement createdElementRepresentation) {
		Assert.assertEquals("The mapping is not the expected one.", getNodeMappingType(), createdElementRepresentation.getMapping().getName()); //$NON-NLS-1$
	}

	/**
	 * Check the number of created element. In this implementation we always consider that 1 node has been created, but this node could contains sub-elements
	 * 
	 * @param createdElementRepresentation
	 *            the created {@link DRepresentationElement}
	 */
	protected void checkNumberOfCreatedElement(final DRepresentationElement createdElementRepresentation) {
		// check the parent of the created element is the expected one.
		Assert.assertEquals("The parent of the created graphical element is not the expected one.", this.graphicalParent, createdElementRepresentation.eContainer()); //$NON-NLS-1$

		// check we create only one node
		Assert.assertEquals("The parent node must contain only one additional element.", this.nbGraphicalContainerChildren + getExpectedAdditionalChildren(), getGraphicalOwnerChildrenSize()); //$NON-NLS-1$

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
	 * 
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementChecker.tests.checkers.internal.api.IGraphicalNodeChecker#validateAfterUndo()
	 *
	 */
	public void validateAfterUndo() {
		Assert.assertEquals("The diagram must contains the same number of elements as initially", this.nbSiriusDiagramTotalElement, this.semanticDiagram.getDiagramElements().size()); //$NON-NLS-1$
		Assert.assertEquals("The graphical owner must contains the same number of elements as initially", this.nbGraphicalContainerChildren, getGraphicalOwnerChildrenSize()); //$NON-NLS-1$
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementChecker.tests.checkers.internal.api.IGraphicalNodeChecker#validateAfterRedo()
	 *
	 */
	public void validateAfterRedo() {
		Assert.assertEquals(this.nbSiriusDiagramTotalElement + getNumberOfExpectedCreatedElement(), this.semanticDiagram.getDiagramElements().size());
		// check we create only one node
		Assert.assertEquals("The parent node must contain only one additional element.", this.nbGraphicalContainerChildren + getExpectedAdditionalChildren(), getGraphicalOwnerChildrenSize()); //$NON-NLS-1$

	}

	/**
	 * 
	 * @return
	 *         the expected mapping type for the created node
	 */
	protected abstract String getNodeMappingType();
}