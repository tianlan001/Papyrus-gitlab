/*****************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *  Obeo - Additional contributions.
 *****************************************************************************/
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.junit.Assert;

/**
 * Abstract checker for element represented with a {@link DNodeContainer}
 */
public abstract class AbstractDNodeContainerCreationChecker extends AbstractGraphicalNodeCreationChecker {

	/**
	 *
	 * Constructor.
	 *
	 * @param diagram
	 *            the gmf diagram
	 * @param graphicalParent
	 *            the graphical parent
	 */
	public AbstractDNodeContainerCreationChecker(final Diagram diagram, final EObject graphicalParent) {
		super(diagram, graphicalParent);
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.checker.clazz.tests.checkers.internal.api.AbstractDNodeCreationChecker#checkCreatedElementInstanceOf(org.eclipse.sirius.viewpoint.DRepresentationElement)
	 *
	 * @param createdElementRepresentation
	 */

	@Override
	protected void checkCreatedElementInstanceOf(final DRepresentationElement createdElementRepresentation) {
		Assert.assertTrue(NLS.bind("The created element must be a DNodeContainer instead of a {0}.", createdElementRepresentation.eClass().getName()), createdElementRepresentation instanceof DNodeContainer); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.checker.clazz.tests.checkers.internal.api.AbstractDNodeCreationChecker#checkCreatedElementMapping(org.eclipse.sirius.viewpoint.DRepresentationElement)
	 *
	 * @param createdElementRepresentation
	 */

	@Override
	protected void checkCreatedElementMapping(final DRepresentationElement createdElementRepresentation) {
		// 1. check the mapping type of the created element
		super.checkCreatedElementMapping(createdElementRepresentation);
		final DNodeContainer nodeContainer = (DNodeContainer) createdElementRepresentation;
		final List<String> compartments = this.getNodeCompartmentTypes();
		final List<String> borderNodes = this.getSynchronizedBorderNodes();

		// 2.check the number of children
		Assert.assertEquals("The created DNodeContainer doesn't have the expected number of compartment types", compartments.size(), nodeContainer.getOwnedDiagramElements().size()); //$NON-NLS-1$
		Assert.assertEquals("The created DNodeContainer doesn't have the expected number of synchronized bordered nodes", borderNodes.size(), nodeContainer.getOwnedBorderedNodes().size()); //$NON-NLS-1$

		// 3. check the mapping type of compartments
		for (int i = 0; i < compartments.size(); i++) {
			final String compartmentMappingType = compartments.get(i);
			final DDiagramElement subElement = nodeContainer.getOwnedDiagramElements().get(i);
			Assert.assertEquals("The mapping type of compartment is not the expected one", compartmentMappingType, subElement.getMapping().getName()); //$NON-NLS-1$
		}
		// 4. check the mapping type of border nodes
		for (int i = 0; i < borderNodes.size(); i++) {
			final DDiagramElement subElement = nodeContainer.getOwnedBorderedNodes().get(i);
			Assert.assertTrue("The mapping type of border node is not the expected one", borderNodes.contains(subElement.getMapping().getName())); //$NON-NLS-1$
		}
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.checker.clazz.tests.checkers.internal.api.AbstractDNodeCreationChecker#getNumberOfExpectedCreatedElement()
	 *
	 * @return
	 */

	@Override
	public int getNumberOfExpectedCreatedElement() {
		// 1 node with its compartments and its borderNodes
		// Get BorderNodes number
		Integer nbSynchronizedBorderNodes = Optional.ofNullable(this.getSynchronizedBorderNodes())//
				.map(List::size)//
				.orElse(Integer.valueOf(0));

		// Get Compartments number
		Integer nbNodeCompartmentTypes = Optional.ofNullable(this.getNodeCompartmentTypes())//
				.map(List::size)//
				.orElse(Integer.valueOf(0));

		return super.getNumberOfExpectedCreatedElement() + nbNodeCompartmentTypes.intValue() + nbSynchronizedBorderNodes.intValue();
	}

	/**
	 *
	 * @return
	 *         the list of the compartment of the node, represented by their IDs
	 */
	protected abstract List<String> getNodeCompartmentTypes();

	/**
	 *
	 * @return the list of synchronized border nodes, represented by their IDs
	 */
	protected abstract List<String> getSynchronizedBorderNodes();

}
