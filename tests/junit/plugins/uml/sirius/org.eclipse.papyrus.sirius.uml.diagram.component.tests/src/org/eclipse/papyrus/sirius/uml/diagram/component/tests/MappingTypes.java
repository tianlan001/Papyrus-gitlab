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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests;

import java.util.List;

/**
 * This class provides mapping types for Sirius Component Diagram odesign.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class MappingTypes {

	private MappingTypes() {
		// to prevent instantiation
	}

	/*----------------------------------------------The Node mapping IDs----------------------------------------------*/

	public static final String COMMENT_NODE_TYPE = "CPD_Comment"; //$NON-NLS-1$

	public static final String COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE = "CPD_ComponentAttributesCompartment"; //$NON-NLS-1$

	public static final String COMPONENT_NODE_TYPE = "CPD_Component"; //$NON-NLS-1$

	public static final String CONSTRAINT_NODE_TYPE = "CPD_Constraint"; //$NON-NLS-1$

	public static final String INTERFACE_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE = "CPD_InterfaceAttributesCompartment"; //$NON-NLS-1$

	public static final String INTERFACE_NODE_CPD_OPERATIONS_COMPARTMENT_TYPE = "CPD_InterfaceOperationsCompartment"; //$NON-NLS-1$

	public static final String INTERFACE_NODE_CPD_RECEPTION_COMPARTMENT_TYPE = "CPD_InterfaceReceptionsCompartment"; //$NON-NLS-1$

	public static final String INTERFACE_NODE_TYPE = "CPD_Interface"; //$NON-NLS-1$

	public static final String MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE = "CPD_ModelPackagedElementsCompartment"; //$NON-NLS-1$

	public static final String MODEL_NODE_TYPE = "CPD_Model"; //$NON-NLS-1$

	public static final String OPERATION_NODE_TYPE = "CPD_Operation"; //$NON-NLS-1$

	public static final String PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE = "CPD_PackagePackagedElementsCompartment"; //$NON-NLS-1$

	public static final String PACKAGE_NODE_TYPE = "CPD_Package"; //$NON-NLS-1$

	public static final String PORT_NODE_TYPE = "CPD_Port"; //$NON-NLS-1$

	public static final String PORT_ON_TYPED_PROPERTY_NODE_TYPE = "CPD_PortOnTypedProperty"; //$NON-NLS-1$

	public static final String PROPERTY_IN_INTERFACE_NODE_TYPE = "CPD_PropertyInInterface"; //$NON-NLS-1$

	public static final String PROPERTY_IN_TYPED_PROPERTY_NODE_CPD_INTERNAL_STRUCTURE_COMPARTMENT = "CPD_PropertyInTypedPropertyInternalStructureCompartment"; //$NON-NLS-1$

	public static final String PROPERTY_IN_TYPED_PROPERTY_NODE_TYPE = "CPD_PropertyInTypedProperty"; //$NON-NLS-1$

	public static final String PROPERTY_NODE_CPD_PROPERTY_INTERNAL_STRUCTURE_COMPARTMENT = "CPD_PropertyInternalStructureCompartment"; //$NON-NLS-1$

	public static final String PROPERTY_NODE_TYPE = "CPD_Property"; //$NON-NLS-1$

	public static final String RECEPTION_NODE_TYPE = "CPD_Reception"; //$NON-NLS-1$

	/*----------------------------------------------The Edge mapping IDs----------------------------------------------*/

	public static final String ABSTRACTION_EDGE_TYPE = "CPD_Abstraction"; //$NON-NLS-1$

	public static final String COMPONENT_REALIZATION_EDGE_TYPE = "CPD_ComponentRealization"; //$NON-NLS-1$

	public static final String CONNECTOR_EDGE_TYPE = "CPD_Connector"; //$NON-NLS-1$

	public static final String DEPENDENCY_EDGE_TYPE = "CPD_Dependency"; //$NON-NLS-1$

	public static final String GENERALIZATION_EDGE_TYPE = "CPD_Generalization"; //$NON-NLS-1$

	public static final String INTERFACE_REALIZATION_EDGE_TYPE = "CPD_InterfaceRealization"; //$NON-NLS-1$

	public static final String LINK_EDGE_TYPE = "CPD_Link"; //$NON-NLS-1$

	public static final String MANIFESTATION_EDGE_TYPE = "CPD_Manifestation"; //$NON-NLS-1$

	public static final String SUBSTITUTION_EDGE_TYPE = "CPD_Substitution"; //$NON-NLS-1$

	public static final String USAGE_EDGE_TYPE = "CPD_Usage"; //$NON-NLS-1$

	/**
	 * Returns whether the given Mapping type is a border node or not.
	 *
	 * @param mappingType
	 *            the mapping type.
	 * @return true if the mapping type is a border node, false otherwise.
	 */
	public static boolean isBorderNode(String mappingType) {
		return List.of(MappingTypes.PORT_ON_TYPED_PROPERTY_NODE_TYPE, MappingTypes.PORT_NODE_TYPE).contains(mappingType);
	}

	/**
	 * Returns whether the given Mapping type is a node or not.
	 *
	 * @param mappingType
	 *            the mapping type.
	 * @return true if the mapping type is a node, false otherwise.
	 */
	public static boolean isNode(String mappingType) {
		return List.of(COMMENT_NODE_TYPE, CONSTRAINT_NODE_TYPE, PROPERTY_NODE_TYPE).contains(mappingType);
	}
}
