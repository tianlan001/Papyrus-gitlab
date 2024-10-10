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
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker;

import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementChecker;
import org.eclipse.sirius.viewpoint.DRepresentationElement;

/**
 * This checker allows to group an {@link ISemanticRepresentationElementChecker} and an {@link IGraphicalRepresentationElementChecker}
 */
public abstract class SemanticAndGraphicChecker {

	/**
	 * the semantic checker
	 */
	private final ISemanticRepresentationElementChecker semanticChecker;

	/**
	 * the graphical checker
	 */
	private final IGraphicalRepresentationElementChecker graphicalChecker;

	/**
	 * Constructor.
	 *
	 */
	public SemanticAndGraphicChecker(final ISemanticRepresentationElementChecker semanticChecker, final IGraphicalRepresentationElementChecker graphicalChecker) {
		this.semanticChecker = semanticChecker;
		this.graphicalChecker = graphicalChecker;
	}

	/**
	 * 
	 * @param createdElementRepresentation
	 */
	public void validateRepresentationElement(final DRepresentationElement createdElementRepresentation) {
		this.semanticChecker.validateRepresentationElement(createdElementRepresentation);
		this.graphicalChecker.validateRepresentationElement(createdElementRepresentation);
	}

	/**
	 * validate the semantic aspect after the Undo of the action
	 */
	public void validateAfterUndo() {
		this.semanticChecker.validateAfterUndo();
		this.graphicalChecker.validateAfterUndo();
	}

	/**
	 * validate the semantic aspect after the Redo of the action
	 */
	public void validateAfterRedo() {
		this.semanticChecker.validateAfterRedo();
		this.graphicalChecker.validateAfterRedo();
	}
}
