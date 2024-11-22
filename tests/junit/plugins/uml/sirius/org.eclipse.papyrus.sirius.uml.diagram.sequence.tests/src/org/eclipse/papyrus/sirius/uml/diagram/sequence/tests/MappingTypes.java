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

import java.util.List;

import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;

/**
 * This class provides mapping types for Sirius Sequence Diagram odesign.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public final class MappingTypes {

	private static final String SD_PREFIX = "SD_"; //$NON-NLS-1$

	private MappingTypes() {
		// to prevent instantiation
	}

	/**
	 * Get the mapping type (container, node or edge) for the given element type name.
	 *
	 * @param elementTypeName
	 *            the represented element type name.
	 * @return the mapping id.
	 */
	public static String getMappingType(String elementTypeName) {
		return SD_PREFIX + elementTypeName;
	}

	/**
	 * Get the mapping type (container, node or edge) for the given element type.
	 * 
	 * @param elementType
	 *            the represented element type
	 * @return the mapping id.
	 */
	public static String getMappingType(Class<? extends Element> elementType) {
		Class<? extends Element> associatedType = elementType;
		if (Message.class.isAssignableFrom(elementType)) {
			associatedType = Message.class;
		}
		if (ExecutionSpecification.class.isAssignableFrom(elementType)) {
			associatedType = ExecutionSpecification.class;
		}
		return getMappingType(associatedType.getSimpleName());
	}

	/**
	 * Returns whether the given Mapping type is a border node or not.
	 *
	 * @param mappingType
	 *            the mapping type.
	 * @return true if the mapping type is a border node, false otherwise.
	 */
	public static boolean isBorderNode(String mappingType) {
		return List.of()
				.contains(mappingType);
	}

	/**
	 * Returns whether the given Mapping type is a node or not.
	 *
	 * @param mappingType
	 *            the mapping type.
	 * @return true if the mapping type is a node, false otherwise.
	 */
	public static boolean isNode(String mappingType) {
		return List.of(getMappingType(Comment.class),
				getMappingType(Constraint.class))
				.contains(mappingType);
	}
}
