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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests;

import java.util.List;

/**
 * This class provides mapping types for Sirius Profile Diagram odesign
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class MappingTypes {

	private MappingTypes() {
		// to prevent instantiation
	}

	/*----------------------------------------------The Node mapping IDs----------------------------------------------*/

	public static final String CLASS_NODE_TYPE = "PRD_Class"; //$NON-NLS-1$

	public static final String CLASS_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE = "PRD_ClassAttributesCompartment"; //$NON-NLS-1$

	public static final String CLASS_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE = "PRD_ClassOperationsCompartment"; //$NON-NLS-1$

	public static final String COMMENT_NODE_TYPE = "PRD_Comment"; //$NON-NLS-1$

	public static final String CONSTRAINT_NODE_TYPE = "PRD_Constraint"; //$NON-NLS-1$

	public static final String DATATYPE_NODE_TYPE = "PRD_DataType"; //$NON-NLS-1$

	public static final String DATATYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE = "PRD_DataTypeAttributesCompartment"; //$NON-NLS-1$

	public static final String DATATYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE = "PRD_DataTypeOperationsCompartment"; //$NON-NLS-1$

	public static final String ENUMERATION_NODE_TYPE = "PRD_Enumeration"; //$NON-NLS-1$

	public static final String ENUMERATION_NODE_PRD_ENUMERATIONLITERALSCOMPARTMENT_TYPE = "PRD_EnumerationLiteralsCompartment"; //$NON-NLS-1$

	public static final String ENUMERATIONLITERAL_NODE_TYPE = "PRD_EnumerationLiteralLabelNode"; //$NON-NLS-1$

	public static final String METACLASS_NODE_TYPE = "PRD_Metaclass"; //$NON-NLS-1$

	public static final String OPERATION_NODE_TYPE = "PRD_OperationLabelNode"; //$NON-NLS-1$

	public static final String PACKAGE_NODE_TYPE = "PRD_Package"; //$NON-NLS-1$

	public static final String PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE = "PRD_PackagePackagedElementsCompartment"; //$NON-NLS-1$

	public static final String PROFILE_NODE_TYPE = "PRD_Profile"; //$NON-NLS-1$

	public static final String PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE = "PRD_ProfilePackagedElementsCompartment"; //$NON-NLS-1$

	public static final String PRIMITIVETYPE_NODE_TYPE = "PRD_PrimitiveType"; //$NON-NLS-1$

	public static final String PROPERTY_NODE_TYPE = "PRD_PropertyLabelNode"; //$NON-NLS-1$

	public static final String STEREOTYPE_NODE_TYPE = "PRD_Stereotype"; //$NON-NLS-1$

	public static final String STEREOTYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE = "PRD_StereotypeAttributesCompartment"; //$NON-NLS-1$

	public static final String STEREOTYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE = "PRD_StereotypeOperationsCompartment"; //$NON-NLS-1$

	// /*----------------------------------------------The Edge mapping IDs----------------------------------------------*/

	public static final String ASSOCIATION_EDGE_TYPE = "PRD_Association"; //$NON-NLS-1$

	public static final String EXTENSION_EDGE_TYPE = "PRD_Extension"; //$NON-NLS-1$

	public static final String GENERALIZATION_EDGE_TYPE = "PRD_Generalization"; //$NON-NLS-1$

	public static final String LINK_EDGE_TYPE = "PRD_Link"; //$NON-NLS-1$

	public static boolean isNode(String mappingType) {
		return List.of(COMMENT_NODE_TYPE, PRIMITIVETYPE_NODE_TYPE, CONSTRAINT_NODE_TYPE, COMMENT_NODE_TYPE, METACLASS_NODE_TYPE).contains(mappingType);
	}
}
