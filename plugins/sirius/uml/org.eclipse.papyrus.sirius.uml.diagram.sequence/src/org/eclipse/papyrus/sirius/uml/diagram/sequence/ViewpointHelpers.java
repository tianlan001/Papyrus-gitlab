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
package org.eclipse.papyrus.sirius.uml.diagram.sequence;

import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DMappingBased;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.description.RepresentationElementMapping;
import org.eclipse.uml2.uml.Element;

/**
 * Class providing names used in Sequence Diagram Viewpoint.
 *
 * @author <a href="mailto:nicolas.peransin@obeo.fr>Nicolas PERANSIN</a>
 */
public final class ViewpointHelpers {

	/** Id used for link mappings. */
	private static final String DEFAULT_PREFIX = "SD_"; //$NON-NLS-1$

	/** Id used for link mappings. */
	public static final String LINK_MAPPING_ID = DEFAULT_PREFIX + "Link"; //$NON-NLS-1$

	/** Id used for mapping of observable. */
	public static final String OBSERVABLE_END_ID = DEFAULT_PREFIX + "EmptyObservation"; //$NON-NLS-1$

	/** Id used for diagram name. */
	public static final String DIAGRAM_NAME = "SequenceDiagram"; //$NON-NLS-1$

	/**
	 * Evaluates if a diagram a sequence diagram.
	 * <p>
	 * Evaludation is based on the name of description.
	 * </p>
	 *
	 * @param representation
	 *            to evaluate
	 * @return true if a sequence diagram
	 */
	public static boolean isSequenceDiagram(DDiagram representation) {
		return ViewpointHelpers.DIAGRAM_NAME.equals(representation.getDescription().getName());
	}

	/**
	 * Gets name used for simple mapping.
	 *
	 * @param umlType
	 *            type of mapped element
	 * @return mapping name in viewpoint
	 */
	public static String getMappingName(Class<? extends Element> umlType) {
		return DEFAULT_PREFIX + umlType.getSimpleName();
	}

	/**
	 * Evaluates if a view matches a kind of mapping.
	 *
	 * @param view
	 *            representation element to evaluate
	 * @param mappingType
	 *            type of mapping
	 * @param mappingName
	 *            name of mapping
	 * @return true if the view matches
	 */
	public static boolean isMapping(DSemanticDecorator view,
			Class<? extends RepresentationElementMapping> mappingType, String mappingName) {
		return view instanceof DMappingBased mapping
				&& mappingType.isInstance(mapping.getMapping())
				&& mapping.getMapping().getName().equals(mappingName);
	}

	/**
	 * Evaluates if a view matches a kind of mapping.
	 * <p>
	 * The mapping is based on default name for specified name.
	 * </p>
	 *
	 * @param view
	 *            representation element to evaluate
	 * @param mappingType
	 *            type of mapping
	 * @param mappingName
	 *            name of mapping
	 * @return true if the view matches
	 */
	public static boolean isMapping(DSemanticDecorator view,
			Class<? extends RepresentationElementMapping> mappingType, Class<? extends Element> umlType) {
		return isMapping(view, mappingType, getMappingName(umlType));
	}

	/**
	 * Evaluates if a view matches a kind of mapping.
	 * <p>
	 * The mapping is based on default name for specified name.
	 * </p>
	 *
	 * @param view
	 *            representation element to evaluate
	 * @param umlType
	 *            type of mapped element
	 * @return true if the view matches
	 */
	public static boolean isMapping(DSemanticDecorator view, Class<? extends Element> umlType) {
		return isMapping(view, RepresentationElementMapping.class, getMappingName(umlType));
	}

	private ViewpointHelpers() {
		// Prevent instantiation.
	}

}
