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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;

/**
 * Creation checker for a DNode.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class DNodeCreationChecker extends AbstractDNodeCreationChecker {

	private final String nodeMappingType;
	private List<String> synchronizedBorderNodes;

	/**
	 *
	 * Constructor.
	 *
	 * @param diagram
	 *            the GMF diagram
	 * @param container
	 *            the graphical parent of the element to create
	 * @param nodeMappingType
	 *            the expected mapping type for the created node
	 */
	public DNodeCreationChecker(final Diagram diagram, final EObject container, String nodeMappingType) {
		this(diagram, container, nodeMappingType, List.of());
	}

	/**
	 * Constructor.
	 *
	 * @param
	 * diagram
	 *            the GMF diagram
	 * @param container
	 *            the graphical parent of the element to create
	 * @param nodeMappingType
	 *            the expected mapping type for the created node
	 * @param synchronizedBorderNodes
	 *            the list of synchronized border nodes, represented by their IDs
	 */
	public DNodeCreationChecker(final Diagram diagram, final EObject container, String nodeMappingType, List<String> synchronizedBorderNodes) {
		super(diagram, container);
		this.nodeMappingType = nodeMappingType;
		this.synchronizedBorderNodes = List.copyOf(synchronizedBorderNodes);
	}

	@Override
	protected String getNodeMappingType() {
		return this.nodeMappingType;
	}

	@Override
	public List<String> getSynchronizedBorderNodes() {
		return this.synchronizedBorderNodes;
	}
}