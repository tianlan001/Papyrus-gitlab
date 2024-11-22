/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests;

import org.eclipse.uml2.uml.Element;

/**
 * This class provides the graphical drop tools ids for Sirius Sequence Diagram odesign.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class GraphicalDropToolsIds {

	private static final String GRAPHICAL_PREFIX = "Graphical"; //$NON-NLS-1$

	private static final String DROP_SUFFIX = "Drop"; //$NON-NLS-1$

	private GraphicalDropToolsIds() {
		// to prevent instantiation
	}

	/**
	 * Get the graphical drop tool id for the given element type name.
	 * 
	 * @param elementTypeName
	 *            the type name of the element to drop.
	 * @return the graphical drop tool id.
	 */
	public static String getGraphicalDropToolsId(String elementTypeName) {
		return GRAPHICAL_PREFIX + elementTypeName + DROP_SUFFIX;
	}

	/**
	 * Get the graphical drop tool id for the given element type.
	 * 
	 * @param elementType
	 *            the type of the element to drop.
	 * @return the graphical drop tool id.
	 */
	public static String getGraphicalDropToolsId(Class<? extends Element> elementType) {
		return getGraphicalDropToolsId(elementType.getSimpleName());
	}

}
