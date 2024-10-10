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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests;

import java.util.List;

/**
 * This class provides mapping types for Sirius Class Diagram odesign
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class MappingTypes {

	private MappingTypes() {
		// to prevent instantiation
	}

	/*----------------------------------------------The Node mapping IDs----------------------------------------------*/

	public static final String ACTIVITY_NODE_TYPE = "UCD_Activity"; //$NON-NLS-1$

	public static final String ACTIVITY_NODE_USECASES_COMPARTMENTS_TYPE = "UCD_ActivityUseCasesCompartment"; //$NON-NLS-1$

	public static final String ACTOR_NODE_TYPE = "UCD_Actor"; //$NON-NLS-1$

	public static final String CLASS_NODE_TYPE = "UCD_Class"; //$NON-NLS-1$

	public static final String CLASS_NODE_USECASES_COMPARTMENTS_TYPE = "UCD_ClassUseCasesCompartment"; //$NON-NLS-1$

	public static final String COMMENT_NODE_TYPE = "UCD_Comment"; //$NON-NLS-1$

	public static final String COMPONENT_NODE_TYPE = "UCD_Component"; //$NON-NLS-1$

	public static final String CONSTRAINT_NODE_TYPE = "UCD_Constraint"; //$NON-NLS-1$

	public static final String COMPONENT_NODE_USECASES_COMPARTMENTS_TYPE = "UCD_ComponentUseCasesCompartment"; //$NON-NLS-1$

	public static final String DIAGRAM_TYPE = "UseCaseDiagram"; //$NON-NLS-1$

	public static final String INTERACTION_NODE_TYPE = "UCD_Interaction"; //$NON-NLS-1$

	public static final String INTERACTION_NODE_USECASES_COMPARTMENTS_TYPE = "UCD_InteractionUseCasesCompartment"; //$NON-NLS-1$

	public static final String PACKAGE_NODE_PACKAGEDELEMENTS_COMPARTMENTS_TYPE = "UCD_PackagePackagedElementsCompartment"; //$NON-NLS-1$

	public static final String PACKAGE_NODE_TYPE = "UCD_Package"; //$NON-NLS-1$

	public static final String STATE_MACHINE_NODE_TYPE = "UCD_StateMachine"; //$NON-NLS-1$

	public static final String STATE_MACHINE_NODE_USECASES_COMPARTMENTS_TYPE = "UCD_StateMachineUseCasesCompartment"; //$NON-NLS-1$

	public static final String USE_CASE_NODE_TYPE = "UCD_UseCase"; //$NON-NLS-1$

	/*----------------------------------------------The Edge mapping IDs----------------------------------------------*/

	public static final String ABSTRACTION_EDGE_TYPE = "UCD_Abstraction"; //$NON-NLS-1$

	public static final String ASSOCIATION_EDGE_TYPE = "UCD_Association"; //$NON-NLS-1$

	public static final String DEPENDENCY_EDGE_TYPE = "UCD_Dependency"; //$NON-NLS-1$

	public static final String EXTEND_EDGE_TYPE = "UCD_Extend"; //$NON-NLS-1$

	public static final String GENERALIZATION_EDGE_TYPE = "UCD_Generalization"; //$NON-NLS-1$

	public static final String INCLUDE_EDGE_TYPE = "UCD_Include"; //$NON-NLS-1$

	public static final String LINK_EDGE_TYPE = "UCD_Link"; //$NON-NLS-1$

	public static final String PACKAGE_IMPORT_EDGE_TYPE = "UCD_PackageImport"; //$NON-NLS-1$

	public static final String PACKAGE_MERGE_EDGE_TYPE = "UCD_PackageMerge"; //$NON-NLS-1$

	public static final String REALIZATION_EDGE_TYPE = "UCD_Realization"; //$NON-NLS-1$

	public static final String USAGE_EDGE_TYPE = "UCD_Usage"; //$NON-NLS-1$

	public static boolean isNode(String mappingType) {
		return List.of(ACTOR_NODE_TYPE, COMMENT_NODE_TYPE, CONSTRAINT_NODE_TYPE, USE_CASE_NODE_TYPE).contains(mappingType);
	}
}
