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

import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;

/**
 * This checker allows to group an {@link ISemanticRepresentationElementCreationChecker} and an {@link IGraphicalRepresentationElementCreationChecker}
 */
public class SemanticAndGraphicalCreationChecker extends SemanticAndGraphicChecker {

	/**
	 * Constructor.
	 *
	 * @param semanticChecker
	 * @param graphicalChecker
	 */
	public SemanticAndGraphicalCreationChecker(final ISemanticRepresentationElementCreationChecker semanticChecker, final IGraphicalRepresentationElementCreationChecker graphicalChecker) {
		super(semanticChecker, graphicalChecker);
	}

}
