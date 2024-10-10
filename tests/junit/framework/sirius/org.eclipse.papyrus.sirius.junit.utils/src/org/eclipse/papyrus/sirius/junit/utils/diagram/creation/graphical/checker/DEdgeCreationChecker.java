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
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;

/**
 * Creation checker for the graphical edge.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class DEdgeCreationChecker extends AbstractGraphicalEdgeCreationChecker {

	private final String edgeMappingType;

	/**
	 * 
	 * Constructor.
	 *
	 * @param diagram
	 *            the GMF diagram
	 * @param container
	 *            the graphical parent of the element to create
	 * @param edgeMappingType
	 *            the expected edge mapping name.
	 */
	public DEdgeCreationChecker(final Diagram diagram, final EObject container, String edgeMappingType) {
		super(diagram, container);
		this.edgeMappingType = edgeMappingType;
	}

	@Override
	protected String getEdgeMappingType() {
		return this.edgeMappingType;
	}
}