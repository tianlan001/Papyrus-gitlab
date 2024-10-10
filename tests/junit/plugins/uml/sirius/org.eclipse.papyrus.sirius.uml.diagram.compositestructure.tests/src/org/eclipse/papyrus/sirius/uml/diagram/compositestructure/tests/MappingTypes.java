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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests;

import java.util.List;

/**
 * This class provides mapping types for Sirius Composite Structure Diagram odesign
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class MappingTypes {

	private MappingTypes() {
		// to prevent instantiation
	}

	/*----------------------------------------------The Node mapping IDs----------------------------------------------*/

	public static final String ACTIVITY_NODE_TYPE = "CSD_Activity"; //$NON-NLS-1$

	public static final String ACTIVITY_NODE_CSD_COMPARTMENTS_TYPE = "CSD_ActivityInternalStructureCompartment"; //$NON-NLS-1$

	public static final String CLASS_NODE_TYPE = "CSD_Class"; //$NON-NLS-1$

	public static final String CLASS_NODE_CSD_COMPARTMENTS_TYPE = "CSD_ClassInternalStructureCompartment"; //$NON-NLS-1$

	public static final String COLLABORATION_NODE_TYPE = "CSD_Collaboration"; //$NON-NLS-1$

	public static final String COLLABORATION_NODE_CSD_COMPARTMENTS_TYPE = "CSD_CollaborationInternalStructureCompartment"; //$NON-NLS-1$

	public static final String COLLABORATION_USE_NODE_TYPE = "CSD_CollaborationUse"; //$NON-NLS-1$

	public static final String COMMENT_NODE_TYPE = "CSD_Comment"; //$NON-NLS-1$

	public static final String CONSTRAINT_NODE_TYPE = "CSD_Constraint"; //$NON-NLS-1$

	public static final String INFORMATION_ITEM_NODE_TYPE = "CSD_InformationItem"; //$NON-NLS-1$

	public static final String INTERACTION_NODE_TYPE = "CSD_Interaction"; //$NON-NLS-1$

	public static final String INTERACTION_NODE_CSD_COMPARTMENTS_TYPE = "CSD_InteractionInternalStructureCompartment"; //$NON-NLS-1$

	public static final String FUNCTION_BEHAVIOR_NODE_TYPE = "CSD_FunctionBehavior"; //$NON-NLS-1$

	public static final String FUNCTION_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE = "CSD_FunctionBehaviorInternalStructureCompartment"; //$NON-NLS-1$

	public static final String OPAQUE_BEHAVIOR_NODE_TYPE = "CSD_OpaqueBehavior"; //$NON-NLS-1$

	public static final String OPAQUE_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE = "CSD_OpaqueBehaviorInternalStructureCompartment"; //$NON-NLS-1$

	public static final String PARAMETER_NODE_TYPE = "CSD_Parameter"; //$NON-NLS-1$

	public static final String PORT_NODE_TYPE = "CSD_Port"; //$NON-NLS-1$

	public static final String PORT_TYPE_PROPERTY_NODE_TYPE = "CSD_PortOnTypedProperty"; //$NON-NLS-1$

	public static final String PROPERTY_NODE_TYPE = "CSD_Property"; //$NON-NLS-1$

	public static final String PROPERTY_NODE_CSD_COMPARTMENTS_TYPE = "CSD_PropertyInternalStructureCompartment"; //$NON-NLS-1$

	public static final String PROTOCOL_STATE_MACHINE_NODE_TYPE = "CSD_ProtocolStateMachine"; //$NON-NLS-1$

	public static final String PROTOCOL_STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE = "CSD_ProtocolStateMachineInternalStructureCompartment"; //$NON-NLS-1$

	public static final String STATE_MACHINE_NODE_TYPE = "CSD_StateMachine"; //$NON-NLS-1$

	public static final String STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE = "CSD_StateMachineInternalStructureCompartment"; //$NON-NLS-1$

	// /*----------------------------------------------The Edge mapping IDs----------------------------------------------*/

	public static final String ABSTRACTION_EDGE_TYPE = "CSD_Abstraction"; //$NON-NLS-1$

	public static final String CONNECTOR_EDGE_TYPE = "CSD_Connector"; //$NON-NLS-1$

	public static final String DEPENDENCY_EDGE_TYPE = "CSD_Dependency"; //$NON-NLS-1$

	public static final String GENERALIZATION_EDGE_TYPE = "CSD_Generalization"; //$NON-NLS-1$

	public static final String INFORMATION_FLOW_EDGE_TYPE = "CSD_InformationFlow"; //$NON-NLS-1$

	public static final String MANIFESTATION_EDGE_TYPE = "CSD_Manifestation"; //$NON-NLS-1$

	public static final String REALIZATION_EDGE_TYPE = "CSD_Realization"; //$NON-NLS-1$

	public static final String SUBSTITUTION_EDGE_TYPE = "CSD_Substitution"; //$NON-NLS-1$

	public static final String USAGE_EDGE_TYPE = "CSD_Usage"; //$NON-NLS-1$

	/**
	 * Returns whether the given Mapping type is a border node or not.
	 * 
	 * @param type
	 *            the mapping type.
	 * @return true if the mapping type is a border node, false otherwise.
	 */
	public static boolean isBorderNode(String type) {
		return MappingTypes.PORT_NODE_TYPE.equals(type) || MappingTypes.PARAMETER_NODE_TYPE.equals(type);
	}

	public static boolean isNode(String mappingType) {
		return List.of(COLLABORATION_USE_NODE_TYPE, COMMENT_NODE_TYPE, CONSTRAINT_NODE_TYPE, INFORMATION_ITEM_NODE_TYPE).contains(mappingType);
	}

}
