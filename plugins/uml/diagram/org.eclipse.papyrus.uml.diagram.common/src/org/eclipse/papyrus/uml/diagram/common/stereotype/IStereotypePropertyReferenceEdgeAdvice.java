/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.stereotype;

/**
 * Interface for stereotype property reference edge advice. It include used keywords.
 *
 * @author Mickaël ADAM
 *
 * @since 3.1
 */
public interface IStereotypePropertyReferenceEdgeAdvice {

	/** The Constant STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT. */
	public static final String STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT = "StereotypePropertyReferenceEdge";//$NON-NLS-1$

	/** The Constant STEREOTYPE_QUALIFY_NAME_ANNOTATION_KEY. */
	public static final String STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY = "stereotypeQualifiedName";//$NON-NLS-1$

	/** The Constant FEATURE_TO_SET_ANNOTATION_KEY. */
	public static final String FEATURE_TO_SET_ANNOTATION_KEY = "featureToSet";//$NON-NLS-1$

	/** The Constant EDGE_LABEL_ANNOTATION_KEY. */
	public static final String EDGE_LABEL_ANNOTATION_KEY = "edgeLabel";//$NON-NLS-1$

	/**
	 * Get the feature to set.
	 *
	 * @return the feature name to set as a {@link String}.
	 */
	public String getFeatureToSet();

	/**
	 * Get the stereotype qualify name.
	 *
	 * @return the stereotype qualify name of the source to set as a {@link String}.
	 */
	public String getStereotypeQualifiedName();

	/**
	 * Get the edge label.
	 *
	 * @return the edge label to display as a {@link String}.
	 */
	public String getEdgeLabel();

}
