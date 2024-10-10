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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests;

import java.util.List;

/**
 * This class provides mapping types for Sirius Deployment Diagram odesign.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class MappingTypes {


	private static final String ARTIFACT = "Artifact"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String COMPARTMENT = "Compartment"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String CONTENT = "Content"; //$NON-NLS-1$

	private static final String DD_PREFIX = "DD_"; //$NON-NLS-1$

	private static final String DEPLOYMENT_SPECIFICATION = "DeploymentSpecification"; //$NON-NLS-1$

	private static final String DEVICE = "Device"; //$NON-NLS-1$

	private static final String MODEL = "Model"; //$NON-NLS-1$

	private static final String NESTED_ARTIFACTS = "NestedArtifacts"; //$NON-NLS-1$

	private static final String NESTED_NODES = "NestedNodes"; //$NON-NLS-1$

	private static final String PACKAGE = "Package"; //$NON-NLS-1$

	private static final String PACKAGED_ELEMENTS = "PackagedElements"; //$NON-NLS-1$


	/**
	 * Get VSM Mapping id for internal compartments.
	 *
	 * @param elementTypeName
	 *            the represented element type name.
	 * @return the mapping id.
	 */
	public static final String getCompartmentMappingType(String elementTypeName) {
		return switch (elementTypeName) {
		case ARTIFACT -> DD_PREFIX + elementTypeName + NESTED_ARTIFACTS + COMPARTMENT;
		case DEVICE -> DD_PREFIX + elementTypeName + NESTED_NODES + COMPARTMENT;
		case MODEL, PACKAGE -> DD_PREFIX + elementTypeName + PACKAGED_ELEMENTS + COMPARTMENT;
		default -> DD_PREFIX + elementTypeName + CONTENT + COMPARTMENT;
		};
	}

	/**
	 * Get the mapping type (container, node or edge) for the given element type name;
	 *
	 * @param elementTypeName
	 *            the represented element type name.
	 * @return the mapping id.
	 */
	public static final String getMappingType(String elementTypeName) {
		return DD_PREFIX + elementTypeName;
	}

	/**
	 * Returns whether the given type is a node or not.
	 * <p>
	 * This method accepts type name (e.g. {@code "Comment"}) as well as mapping type name (e.g. {@code "DD_Comment"}).
	 * </p>
	 *
	 * @param type
	 *            the element type.
	 * @return {@code true} if the mapping type is a node, {@code false} otherwise.
	 */
	public static boolean isNode(String type) {
		String typeName = type;
		if (type.startsWith(DD_PREFIX)) {
			// Handle both type name and mapping type name.
			typeName = typeName.substring(DD_PREFIX.length());
		}
		return List.of(COMMENT, CONSTRAINT, DEPLOYMENT_SPECIFICATION).contains(typeName);
	}


	private MappingTypes() {
		// to prevent instantiation
	}
}
