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
import org.eclipse.sirius.diagram.DNodeListElement;

/**
 * Creation checker for a {@link DNodeListElement}.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class DNodeListElementCreationChecker extends AbstractDNodeListElementCreationChecker {

	private final String nodeMappingType;

	/**
	 * 
	 * Constructor.
	 *
	 * @param diagram
	 *            the GMF diagram
	 * @param graphicalParent
	 *            the graphical parent of the element to create
	 */
	public DNodeListElementCreationChecker(final Diagram diagram, final EObject container, String nodeMappingType) {
		super(diagram, container);
		this.nodeMappingType = nodeMappingType;
	}

	@Override
	protected String getNodeMappingType() {
		return this.nodeMappingType;
	}
}