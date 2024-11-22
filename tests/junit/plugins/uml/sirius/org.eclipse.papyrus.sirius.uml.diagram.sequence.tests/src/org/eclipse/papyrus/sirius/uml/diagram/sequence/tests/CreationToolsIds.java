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
 * This class provides creation tools ids for Sirius Sequence Diagram odesign.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public final class CreationToolsIds {

	private static final String CREATE_PREFIX = "Create"; //$NON-NLS-1$

	private static final String TOOL_SUFFIX = "Tool"; //$NON-NLS-1$

	private CreationToolsIds() {
		// to prevent instantiation
	}

	/**
	 * Get the creation tool id for the given element type name.
	 * 
	 * @param elementTypeName
	 *            the type name of the element to create.
	 * @return the creation tool id.
	 */
	public static String getCreationToolId(String elementTypeName) {
		return CREATE_PREFIX + elementTypeName + TOOL_SUFFIX;
	}

	/**
	 * Get the creation tool id for the given element type.
	 * 
	 * @param elementType
	 *            the type of the element to create.
	 * @return the creation tool id.
	 */
	public static String getCreationToolId(Class<? extends Element> elementType) {
		return getCreationToolId(elementType.getSimpleName());
	}
}
