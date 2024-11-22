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

/**
 * This class provides mapping types for Sirius Sequence Diagram odesign.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class MappingTypes {

	private static final String SD_PREFIX = "SD_"; //$NON-NLS-1$

	/*----------------------------------------------The Node mapping IDs----------------------------------------------*/

	public static final String LIFELINE_NODE_TYPE = "SD_Lifeline"; //$NON-NLS-1$

	public static final String LIFELINE_EXECUTION_NODE_TYPE = "SD_Lifeline_Execution"; //$NON-NLS-1$

	public static final String EXECUTION_SPECIFICATION_NODE_TYPE = "SD_ExecutionSpecification"; //$NON-NLS-1$

	/*----------------------------------------------The Edge mapping IDs----------------------------------------------*/

	public static final String MESSAGE_EDGE_TYPE = "SD_Message"; //$NON-NLS-1$

	private MappingTypes() {
		// to prevent instantiation
	}

	/**
	 * Get the mapping type (container, node or edge) for the given element type name;
	 *
	 * @param elementTypeName
	 *            the represented element type name.
	 * @return the mapping id.
	 */
	public static final String getMappingType(String elementTypeName) {
		return SD_PREFIX + elementTypeName;
	}
}
