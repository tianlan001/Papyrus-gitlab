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
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests;

import java.util.List;

/**
 * This class provides mapping types for Sirius Package Diagram odesign.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class MappingTypes {

	/*----------------------------------------------The Node mapping IDs----------------------------------------------*/

	public static final String COMMENT_NODE_TYPE = "PAD_Comment"; //$NON-NLS-1$

	public static final String CONSTRAINT_NODE_TYPE = "PAD_Constraint"; //$NON-NLS-1$

	public static final String MODEL_NODE_TYPE = "PAD_Model"; //$NON-NLS-1$

	public static final String PACKAGE_NODE_TYPE = "PAD_Package"; //$NON-NLS-1$

	/*----------------------------------------------The Compartments mapping IDs----------------------------------------------*/

	public static final String MODEL_NODE_PAD_PACKAGED_ELEMENTS_COMPARTMENTS_TYPE = "PAD_ModelPackagedElementsCompartment"; //$NON-NLS-1$

	public static final String PACKAGE_NODE_PAD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE = "PAD_PackagePackagedElementsCompartments"; //$NON-NLS-1$

	/*----------------------------------------------The Edge mapping IDs----------------------------------------------*/

	public static final String ABSTRACTION_EDGE_TYPE = "PAD_Abstraction"; //$NON-NLS-1$

	public static final String DEPENDENCY_EDGE_TYPE = "PAD_Dependency"; //$NON-NLS-1$

	public static final String LINK_EDGE_TYPE = "PAD_Link"; //$NON-NLS-1$

	public static final String PACKAGE_IMPORT_EDGE_TYPE = "PAD_PackageImport"; //$NON-NLS-1$

	private MappingTypes() {
		// to prevent instantiation
	}

	/**
	 * Returns whether the given Mapping type is a node or not.
	 *
	 * @param mappingType
	 *            the mapping type.
	 * @return true if the mapping type is a node, false otherwise.
	 */
	public static boolean isNode(String mappingType) {
		return List.of(COMMENT_NODE_TYPE,
				CONSTRAINT_NODE_TYPE)
				.contains(mappingType);
	}
}
