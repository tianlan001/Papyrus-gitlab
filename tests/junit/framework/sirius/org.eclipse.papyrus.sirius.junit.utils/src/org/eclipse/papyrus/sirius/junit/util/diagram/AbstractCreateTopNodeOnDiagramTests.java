/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.sirius.junit.util.diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.uml2.uml.Element;

/**
 * Abstract Tests for TopNode creation on Diagram
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public abstract class AbstractCreateTopNodeOnDiagramTests extends AbstractCreateNodeTests {


	/**
	 * @see org.eclipse.papyrus.sirius.uml.diagram.clazz.tests.creation.topNodes.AbstractTopNode_CreationTests#getSemanticOwner()
	 *
	 * @return
	 */
	@Override
	protected final Element getSemanticOwner() {
		return this.root;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.sirius.junit.util.diagram.clazz.tests.creation.topNodes.AbstractCreateNodeTests#getTopGraphicalContainer()
	 *
	 * @return
	 */
	@Override
	protected final EObject getTopGraphicalContainer() {
		return getDDiagram();
	}


	/**
	 * 
	 * @param creationToolId
	 *            the ID of the creation tool to use
	 * @param checker
	 *            the checker to use to validate the creation
	 */
	protected void createNode(final String creationToolId, final SemanticAndGraphicalCreationChecker checker) {
		super.createNode(creationToolId, checker, getTopGraphicalContainer());
	}
}
