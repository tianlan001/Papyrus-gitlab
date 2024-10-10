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
 *  	Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *  	Obeo - Generalization for reused
 *****************************************************************************/
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker;

import org.eclipse.sirius.viewpoint.DRepresentationElement;


/**
 * Common interface for Checker in charge to validate the graphical aspect of an action on a Sirius representation element.
 */
public interface IGraphicalRepresentationElementChecker {

	/**
	 * @param createdElementRepresentation
	 */
	void validateRepresentationElement(final DRepresentationElement createdElementRepresentation);

	/**
	 * validate the graphical aspect after the Undo of the action
	 */
	void validateAfterUndo();

	/**
	 * validate the graphical aspect after the Redo of the action
	 */
	void validateAfterRedo();

}
