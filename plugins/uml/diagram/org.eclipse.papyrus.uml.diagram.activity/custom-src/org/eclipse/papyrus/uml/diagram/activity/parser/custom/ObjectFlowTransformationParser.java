/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.parser.custom;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A specific parser for displaying the transformation behavior associated to an
 * object flow.
 */
public class ObjectFlowTransformationParser extends AssociatedBehaviorParser {

	/** The format for displaying the object flow transformation */
	private static final String TRANSFORMATION_FORMAT = "<<transformation>>".concat(System.getProperty("line.separator")).concat("%s");

	/**
	 * Get the format for displaying the object flow transformation
	 *
	 * @see org.eclipse.papyrus.uml.diagram.activity.parser.custom.AssociatedBehaviorParser#getFormatString()
	 * @return format string
	 */
	@Override
	protected String getFormatString() {
		return TRANSFORMATION_FORMAT;
	}

	/**
	 * Get the feature referencing the object flow transformation
	 *
	 * @see org.eclipse.papyrus.uml.diagram.activity.parser.custom.AssociatedBehaviorParser#getReferenceFeature()
	 * @return feature
	 */
	@Override
	protected EStructuralFeature getReferenceFeature() {
		return UMLPackage.eINSTANCE.getObjectFlow_Transformation();
	}
}
