/*****************************************************************************
 * Copyright (c) 2018 Maged Elaasar, CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Maged Elaasar - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.architecture;

/**
 * The type of UML diagrams
 * 
 */
public interface UMLDiagramTypes {

	/**
	 * The type of the activity diagram
	 */
	public static final String ACTIVITY_DIAGRAM = "PapyrusUMLActivityDiagram";

	/**
	 * The type of the class diagram
	 */
	public static final String CLASS_DIAGRAM = "PapyrusUMLClassDiagram";
	
	/**
	 * The type of the communication diagram
	 */
	public static final String COMMUNICATION_DIAGRAM = "PapyrusUMLCommunicationDiagram";
	
	/**
	 * The type of the component diagram
	 */
	public static final String COMPONENT_DIAGRAM = "PapyrusUMLComponentDiagram";
	
	/**
	 * The type of the composite structure diagram
	 */
	public static final String COMPOSITE_STRUCTURE_DIAGRAM = "CompositeStructure";
	
	/**
	 * The type of the deployment diagram
	 */
	public static final String DEPLOYMENT_DIAGRAM = "PapyrusUMLDeploymentDiagram";
	
	/**
	 * The type of the interaction overview diagram
	 */
	public static final String INTERACTION_OVERVIEW_DIAGRAM = "PapyrusUMLInteractionOverviewDiagram";
	
	/**
	 * The type of the profile diagram
	 */
	public static final String PROFILE_DIAGRAM = "PapyrusUMLProfileDiagram";
	
	/**
	 * The type of the sequence diagram
	 */
	public static final String SEQUENCE_DIAGRAM = "PapyrusUMLSequenceDiagram";
	
	/**
	 * The type of the state machine diagram
	 */
	public static final String STATE_MACHINE_DIAGRAM = "PapyrusUMLStateMachineDiagram";
	
	/**
	 * The type of the timing diagram
	 */
	public static final String TIMING_DIAGRAM = "PapyrusUMLTimingDiagram";
	
	/**
	 * The type of the use case diagram
	 */
	public static final String USE_CASE_DIAGRAM = "UseCase";

}
