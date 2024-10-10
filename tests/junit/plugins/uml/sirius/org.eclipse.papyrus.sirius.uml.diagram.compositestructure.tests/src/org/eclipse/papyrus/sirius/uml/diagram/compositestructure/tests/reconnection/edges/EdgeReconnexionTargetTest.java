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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.reconnection.edges;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectTargetEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.ReconnectionToolsIds;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the target reconnection of edges on Composite Structure diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/reconnection/Edge_ReconnectionTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class EdgeReconnexionTargetTest extends AbstractReconnectTargetEdgeTests {

	/**
	 * Name of the semantic edge target called Activity2.
	 */
	private static final String ACTIVITY2 = "Activity2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Activity1.
	 */
	private static final String ACTIVITY1 = "Activity1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Class3.
	 */
	private static final String CLASS3 = "Class3"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Class2.
	 */
	private static final String CLASS2 = "Class2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Class1.
	 */
	private static final String CLASS1 = "Class1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Collaboration2.
	 */
	private static final String COLLABORATION2 = "Collaboration2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called collaboration1.
	 */
	private static final String COLLABORATION1 = "Collaboration1"; //$NON-NLS-1$

	/**
	 * Name of the connector edge.
	 */
	private static final String CONNECTOR1 = "Connector1";//$NON-NLS-1$

	/**
	 * Name of the semantic edge target called FunctionBehavior2.
	 */
	private static final String FUNCTIONBEHAVIOR2 = "FunctionBehavior2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called FunctionBehavior1.
	 */
	private static final String FUNCTIONBEHAVIOR1 = "FunctionBehavior1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Interaction2.
	 */
	private static final String INTERACTION2 = "Interaction2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Interaction1.
	 */
	private static final String INTERACTION1 = "Interaction1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called OpaqueBehavior2.
	 */
	private static final String OPAQUEBEHAVIOR2 = "OpaqueBehavior2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called OpaqueBehavior1.
	 */
	private static final String OPAQUEBEHAVIOR1 = "OpaqueBehavior1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Port2.
	 */
	private static final String PORT2 = "Port2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Port1.
	 */
	private static final String PORT1 = "Port1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Property2.
	 */
	private static final String PROPERTY2 = "Property2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Property1.
	 */
	private static final String PROPERTY1 = "Property1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called ProtocolStateMachine2.
	 */
	private static final String PROTOCOLSTATEMACHINE2 = "ProtocolStateMachine2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called ProtocolStateMachine1.
	 */
	private static final String PROTOCOLSTATEMACHINE1 = "ProtocolStateMachine1"; //$NON-NLS-1$


	/**
	 * Name of the semantic edge target called StateMachine2.
	 */
	private static final String STATEMACHINE2 = "StateMachine2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called StateMachine1.
	 */
	private static final String STATEMACHINE1 = "StateMachine1"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called ActivityNewEnd.
	 */
	private static final String ACTIVITY_NEW_TARGET = "ActivityNewEnd"; //$NON-NLS-1$
	/**
	 * Name of the new semantic edge target called ClassNewEnd.
	 */
	private static final String CLASS_NEW_TARGET = "ClassNewEnd"; //$NON-NLS-1$
	/**
	 * Name of the new semantic edge target called CollaborationNewEnd.
	 */
	private static final String COLLABORATION_NEW_TARGET = "CollaborationNewEnd"; //$NON-NLS-1$
	/**
	 * Name of the new semantic edge target called CollaborationUseNewEnd.
	 */
	private static final String COLLABORATIONUSE_NEW_TARGET = "CollaborationUseNewEnd"; //$NON-NLS-1$
	/**
	 * Name of the new semantic edge target called ConstraintNewEnd.
	 */
	private static final String CONSTRAINT_NEW_TARGET = "ConstraintNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called FunctionBehaviorNewEnd.
	 */
	private static final String FUNCTIONBEHAVIOR_NEW_TARGET = "FunctionBehaviorNewEnd"; //$NON-NLS-1$
	/**
	 * Name of the new semantic edge target called InformationItemNewEnd.
	 */
	private static final String INFORMATIONITEM_NEW_TARGET = "InformationItemNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called InteractionNewEnd.
	 */
	private static final String INTERACTION_NEW_TARGET = "InteractionNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called OpaqueBehaviorNewEnd.
	 */
	private static final String OPAQUEBEHAVIOR_NEW_TARGET = "OpaqueBehaviorNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called ParameterNewEnd.
	 */
	private static final String PARAMETER_NEW_TARGET = "ParameterNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called PortNewEnd.
	 */
	private static final String PORT_NEW_TARGET = "PortNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called PropertyNewEnd.
	 */
	private static final String PROPERTY_NEW_TARGET = "PropertyNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called ProtocolStateMachineNewEnd.
	 */
	private static final String PROTOCOLSTATEMACHINE_NEW_TARGET = "ProtocolStateMachineNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called StateMachineNewEnd.
	 */
	private static final String STATEMACHINE_NEW_TARGET = "StateMachineNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the diagram.
	 */
	private static final String DIAGRAM_NAME = "EdgeCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String newTargetName;

	private final String mappingNewTargetTypeName;

	/**
	 * Default constructor.
	 *
	 */
	public EdgeReconnexionTargetTest(String newTargetName, String mappingNewTargetTypeName) {
		this.newTargetName = newTargetName;
		this.mappingNewTargetTypeName = mappingNewTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetAbstractionTest() {
		initializeGraphicalAndSemanticContext(FUNCTIONBEHAVIOR1, FUNCTIONBEHAVIOR2, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.ABSTRACTION_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__ABSTRACTION__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetConnectorTest() {
		if (PORT_NEW_TARGET.equals(newTargetName) || PROPERTY_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(PORT1, PORT2, MappingTypes.PORT_TYPE_PROPERTY_NODE_TYPE, MappingTypes.PORT_TYPE_PROPERTY_NODE_TYPE);
			// @formatter:off
			DEdge edgeToReconnect = getDDiagram().getEdges().stream()
					.filter(edge -> CONNECTOR1.equals(edge.getName()))
					.findFirst().orElse(null);
			// @formatter:on
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__CONNECTOR__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetDependencyTest() {
		initializeGraphicalAndSemanticContext(ACTIVITY1, ACTIVITY2, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.DEPENDENCY_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__DEPENDENCY__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetGeneralizationTest() {
		if (!COLLABORATIONUSE_NEW_TARGET.equals(newTargetName) && !CONSTRAINT_NEW_TARGET.equals(newTargetName) && !PARAMETER_NEW_TARGET.equals(newTargetName) && !PORT_NEW_TARGET.equals(newTargetName) && !PROPERTY_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(CLASS1, CLASS2, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.GENERALIZATION_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__GENERALIZATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetInformationFlowTest() {
		initializeGraphicalAndSemanticContext(INTERACTION1, INTERACTION2, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.INFORMATION_FLOW_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__INFORMATION_FLOW__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetManifestationTest() {
		if (!COLLABORATIONUSE_NEW_TARGET.equals(newTargetName) && !PARAMETER_NEW_TARGET.equals(newTargetName) && !PORT_NEW_TARGET.equals(newTargetName) && !PROPERTY_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(COLLABORATION1, COLLABORATION2, MappingTypes.COLLABORATION_NODE_TYPE, MappingTypes.COLLABORATION_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.MANIFESTATION_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__MANIFESTATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetRealizationTest() {
		initializeGraphicalAndSemanticContext(PROTOCOLSTATEMACHINE1, PROTOCOLSTATEMACHINE2, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.REALIZATION_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__REALIZATION__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetSubstitutionTest() {
		if (!COLLABORATIONUSE_NEW_TARGET.equals(newTargetName) && !CONSTRAINT_NEW_TARGET.equals(newTargetName) && !PARAMETER_NEW_TARGET.equals(newTargetName) && !PORT_NEW_TARGET.equals(newTargetName) && !PROPERTY_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(STATEMACHINE1, STATEMACHINE2, MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.SUBSTITUTION_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__SUBSTITUTION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetUsageTest() {
		initializeGraphicalAndSemanticContext(OPAQUEBEHAVIOR1, OPAQUEBEHAVIOR2, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.USAGE_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__USAGE__TOOL, edgeToReconnect);
	}

	private void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		// initialize source and target context for test
		if (PORT1.equals(sourceName)) {
			// connector case
			initializeGraphicalAndSemanticContextForConnector(sourceName, targetName, mappingSourceTypeName, mappingTargetTypeName);
		} else {
			this.setSemanticSource(this.root.getMember(sourceName));
			this.setSemanticTarget(this.root.getMember(targetName));
			this.setEdgeSource(getNodeFromDiagram(sourceName, mappingSourceTypeName));
			this.setEdgeTarget(getNodeFromDiagram(targetName, mappingTargetTypeName));
		}
		// initialize newTarget context for test
		if (PORT_NEW_TARGET.equals(newTargetName)) {
			Class classNewTarget = (Class) this.root.getMember(CLASS_NEW_TARGET);
			this.setSemanticNewTarget(classNewTarget.getOwnedPorts().get(0));
			this.setEdgeNewTarget(getNewPortTarget());
		} else if (PROPERTY_NEW_TARGET.equals(newTargetName)) {
			Class class3 = (Class) this.root.getMember(CLASS3);
			// @formatter:off
			Property property = class3.getAllAttributes().stream()
					.filter(att -> newTargetName.equals(att.getName()))
					.findFirst().orElse(null);
			// @formatter:on
			this.setSemanticNewTarget(property);
			this.setEdgeNewTarget(getNewPropertyTarget());
		} else if (COLLABORATIONUSE_NEW_TARGET.equals(newTargetName)) {
			Collaboration collaboration = (Collaboration) this.root.getMember(COLLABORATION_NEW_TARGET);
			this.setSemanticNewTarget(collaboration.getCollaborationUse(newTargetName));
			this.setEdgeNewTarget(getNewCollaborationUseTarget());
		} else if (PARAMETER_NEW_TARGET.equals(newTargetName)) {
			OpaqueBehavior opaqueBehavior = (OpaqueBehavior) this.root.getMember(OPAQUEBEHAVIOR_NEW_TARGET);
			this.setSemanticNewTarget(opaqueBehavior.getOwnedParameters().get(0));
			this.setEdgeNewTarget(getNewParameterTarget());
		} else {
			this.setSemanticNewTarget(this.root.getMember(newTargetName));
			this.setEdgeNewTarget(getNodeFromDiagram(newTargetName, mappingNewTargetTypeName));
		}
	}

	private void initializeGraphicalAndSemanticContextForConnector(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		// set source semantic and graphic
		Class class1 = (Class) this.root.getMember(CLASS1);
		this.setSemanticSource(class1.getOwnedPorts().get(0));
		DNodeContainer classNode = (DNodeContainer) getNodeFromDiagram(CLASS3, MappingTypes.CLASS_NODE_TYPE);
		final DDiagramElement compartment = classNode.getOwnedDiagramElements().get(0);
		AbstractDNode propertyNode = getNodeFromContainer(PROPERTY1, MappingTypes.PROPERTY_NODE_TYPE, compartment);
		// @formatter:off
		DDiagramElement portNode1 = ((DNodeContainer) propertyNode).getElements().stream()
				.filter(ddiagElt -> mappingSourceTypeName.equals(ddiagElt.getMapping().getName()))
				.findFirst().orElse(null);

		this.setEdgeSource(portNode1);
		// set target semantic and graphic
		Class class2 = (Class) this.root.getMember(CLASS2);
		this.setSemanticTarget(class2.getOwnedPorts().get(0));
		propertyNode = getNodeFromContainer(PROPERTY2, MappingTypes.PROPERTY_NODE_TYPE, compartment);
		// @formatter:off
		DDiagramElement portNode2 = ((DNodeContainer) propertyNode).getElements().stream()
				.filter(ddiagElt -> mappingTargetTypeName.equals(ddiagElt.getMapping().getName()))
				.findFirst().orElse(null);
		// @formatter:on
		this.setEdgeTarget(portNode2);
	}

	@Parameters(name = "{index} reconnect target edge on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY_NEW_TARGET, MappingTypes.ACTIVITY_NODE_TYPE },
				{ CLASS_NEW_TARGET, MappingTypes.CLASS_NODE_TYPE },
				{ COLLABORATION_NEW_TARGET, MappingTypes.COLLABORATION_NODE_TYPE },
				{ COLLABORATIONUSE_NEW_TARGET, MappingTypes.COLLABORATION_USE_NODE_TYPE },
				{ CONSTRAINT_NEW_TARGET, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ FUNCTIONBEHAVIOR_NEW_TARGET, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE },
				{ INFORMATIONITEM_NEW_TARGET, MappingTypes.INFORMATION_ITEM_NODE_TYPE },
				{ INTERACTION_NEW_TARGET, MappingTypes.INTERACTION_NODE_TYPE },
				{ OPAQUEBEHAVIOR_NEW_TARGET, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE },
				{ PARAMETER_NEW_TARGET, MappingTypes.PARAMETER_NODE_TYPE },
				{ PORT_NEW_TARGET, MappingTypes.PORT_TYPE_PROPERTY_NODE_TYPE },
				{ PROPERTY_NEW_TARGET, MappingTypes.PROPERTY_NODE_TYPE },
				{ PROTOCOLSTATEMACHINE_NEW_TARGET, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE },
				{ STATEMACHINE_NEW_TARGET, MappingTypes.STATE_MACHINE_NODE_TYPE },
		});
	}

	/**
	 * Get Parameter node used for new Target from the property node in class node.
	 * 
	 * @return the Parameter node.
	 */
	private DDiagramElement getNewParameterTarget() {
		DNodeContainer opaqueBehaviorNode = (DNodeContainer) getNodeFromDiagram(OPAQUEBEHAVIOR_NEW_TARGET, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE);
		return getNodeFromContainer(newTargetName, mappingNewTargetTypeName, opaqueBehaviorNode);
	}

	/**
	 * Get CollaborationUse node used for new Target from the property node in class node.
	 * 
	 * @return the CollaborationUse node.
	 */
	private DDiagramElement getNewCollaborationUseTarget() {
		DNodeContainer collaborationNode = (DNodeContainer) getNodeFromDiagram(COLLABORATION_NEW_TARGET, MappingTypes.COLLABORATION_NODE_TYPE);
		final DDiagramElement compartment = collaborationNode.getOwnedDiagramElements().get(0);
		return getNodeFromContainer(newTargetName, mappingNewTargetTypeName, compartment);
	}

	/**
	 * Get Property node used for new Target from the property node in class node.
	 * 
	 * @return the Property node.
	 */
	private DDiagramElement getNewPropertyTarget() {
		DNodeContainer classNode = (DNodeContainer) getNodeFromDiagram(CLASS3, MappingTypes.CLASS_NODE_TYPE);
		final DDiagramElement compartment = classNode.getOwnedDiagramElements().get(0);
		return getNodeFromContainer(newTargetName, mappingNewTargetTypeName, compartment);
	}

	/**
	 * Get Port border node used for new Target from the property node in class node.
	 * 
	 * @return the Port border node.
	 */
	private DDiagramElement getNewPortTarget() {
		DNodeContainer classNode = (DNodeContainer) getNodeFromDiagram(CLASS3, MappingTypes.CLASS_NODE_TYPE);
		final DDiagramElement compartment = classNode.getOwnedDiagramElements().get(0);
		AbstractDNode propertyNode = getNodeFromContainer(PROPERTY_NEW_TARGET, MappingTypes.PROPERTY_NODE_TYPE, compartment);
		// @formatter:off
		DDiagramElement portNode = ((DNodeContainer) propertyNode).getElements().stream()
				.filter(ddiagElt -> mappingNewTargetTypeName.equals(ddiagElt.getMapping().getName()))
				.findFirst().orElse(null);
		// @formatter:on
		return portNode;
	}

}
