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
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DNode;

/**
 * Utility class providing utilities methods to manipulate Sirius graphical elements
 */
public class GraphicalOwnerUtils {

	/**
	 *
	 * Constructor.
	 *
	 */
	private GraphicalOwnerUtils() {
		// to prevent instantiation
	}

	/**
	 * Returns the number of children elements of provided diagram element.
	 *
	 * @param graphicalParent
	 *            the graphical parent. It should be an element from Sirius Diagram metamodel
	 * @return the owned elements
	 */
	public static final int getGraphicalOwnerChildrenSize(final EObject graphicalParent) {
		return getChildren(graphicalParent).size();
	}

	/**
	 * Returns the children elements of provided diagram element.
	 *
	 * @param graphicalParent
	 *            the graphical parent. It should be an element from Sirius Diagram metamodel
	 * @return the owned elements
	 */
	public static final List<? extends EObject> getChildren(final EObject graphicalParent) {
		if (graphicalParent instanceof DDiagram diagram) {
			return diagram.getOwnedDiagramElements();
		} else if (graphicalParent instanceof DDiagramElementContainer container) {
			return container.getElements();
		} else if (graphicalParent instanceof DNode node) {
			return node.getOwnedBorderedNodes();
		}
		throw new UnsupportedOperationException(NLS.bind("The type {0} is not yet supported", graphicalParent.eClass().getName())); //$NON-NLS-1$

	}
}
