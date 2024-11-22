/*****************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST
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
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.junit.Assert;

/**
 * Abstract checker for element represented with a {@link DNode}
 */
public abstract class AbstractDNodeCreationChecker extends AbstractGraphicalNodeCreationChecker {

	/**
	 * 
	 * Constructor.
	 *
	 * @param diagram
	 *            the gmf diagram
	 * @param graphicalParent
	 *            the graphical parent
	 */
	public AbstractDNodeCreationChecker(final Diagram diagram, final EObject graphicalParent) {
		super(diagram, graphicalParent);
	}

	/**
	 * check the instance of on the created element
	 * 
	 * @param createdElementRepresentation
	 *            the created {@link DRepresentationElement}
	 */
	@Override
	protected void checkCreatedElementInstanceOf(final DRepresentationElement createdElementRepresentation) {
		Assert.assertTrue(NLS.bind("The created element must be a DNode instead of a {0}.", createdElementRepresentation.eClass().getName()), createdElementRepresentation instanceof DNode); //$NON-NLS-1$
	}

	@Override
	public int getNumberOfExpectedCreatedElement() {
		// 1 node with the synchronized border nodes
		Integer nbSynchronizedBorderNodes = Optional.ofNullable(this.getSynchronizedBorderNodes())
				.map(List::size)
				.orElse(Integer.valueOf(0));
		return super.getNumberOfExpectedCreatedElement() + nbSynchronizedBorderNodes.intValue();
	}

	/**
	 * 
	 * @return the list of synchronized border nodes, represented by their IDs
	 */
	protected abstract List<String> getSynchronizedBorderNodes();

}
