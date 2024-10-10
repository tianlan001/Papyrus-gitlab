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
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.sirius.uml.diagram.activity.services.ActivityDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.uml.domain.services.labels.UMLCharacters;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.AddVariableValueAction;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.ExpansionNode;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link ActivityDiagramServices} tests.
 * 
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class ActivityDiagramServicesTest extends AbstractServicesTest {

	private static final String MY_ACTIVITY = "myActivity"; //$NON-NLS-1$

	private static final String DECISION_INPUT = "decisionInput"; //$NON-NLS-1$

	private ActivityDiagramServices activityDiagramServices;

	@Before
	public void setUp() {
		this.activityDiagramServices = new ActivityDiagramServices();
	}

	@Test
	public void testGetActivityEdgeCandidates() {
		Activity rootActivity = create(Activity.class);
		ObjectFlow objectFlow = createIn(ObjectFlow.class, rootActivity);
		ControlFlow controlFlow = createIn(ControlFlow.class, rootActivity);
		Activity subActivity = createIn(Activity.class, rootActivity);
		ObjectFlow objectFlow2 = createIn(ObjectFlow.class, subActivity);
		ControlFlow controlFlow2 = createIn(ControlFlow.class, subActivity);
		StructuredActivityNode structuredActivityNode = createIn(StructuredActivityNode.class, subActivity);
		ObjectFlow objectFlow3 = createIn(ObjectFlow.class, structuredActivityNode);
		ControlFlow controlFlow3 = createIn(ControlFlow.class, structuredActivityNode);
		Collection<ActivityEdge> objectFlowCandidates = this.activityDiagramServices.getActivityEdgesCandidates(rootActivity);
		assertEquals(6, objectFlowCandidates.size());
		assertTrue(objectFlowCandidates.containsAll(List.of(objectFlow, objectFlow2, objectFlow3, controlFlow, controlFlow2, controlFlow3)));

		// Check with a root different from an Activity:
		objectFlowCandidates = this.activityDiagramServices.getActivityEdgesCandidates(structuredActivityNode);
		assertTrue(objectFlowCandidates.isEmpty());
	}

	@Test
	public void testGetActivityNodeCandidatesOnActivity() {
		Activity activity = create(Activity.class);
		ActivityPartition activityPartition = create(ActivityPartition.class);
		InitialNode initialNode = create(InitialNode.class);
		ActivityFinalNode finalNode = create(ActivityFinalNode.class);
		MergeNode mergeNode = create(MergeNode.class);
		activityPartition.getNodes().add(mergeNode);
		activity.getNodes().addAll(List.of(initialNode, finalNode, mergeNode));

		Collection<ActivityNode> activityNodeCandidates = this.activityDiagramServices.getActivityNodeCandidates(activity);
		assertEquals(2, activityNodeCandidates.size());
		assertTrue(activityNodeCandidates.containsAll(List.of(initialNode, finalNode)));
	}

	@Test
	public void testGetActivityNodeCandidatesOnActivityPartition() {
		Activity activity = create(Activity.class);
		ActivityPartition activityPartition = create(ActivityPartition.class);
		InitialNode initialNode = create(InitialNode.class);
		ActivityFinalNode finalNode = create(ActivityFinalNode.class);
		MergeNode mergeNode = create(MergeNode.class);
		activity.getNodes().addAll(List.of(initialNode, finalNode, mergeNode));
		activityPartition.getNodes().addAll(List.of(finalNode, mergeNode));

		Collection<ActivityNode> activityNodeCandidates = this.activityDiagramServices.getActivityNodeCandidates(activityPartition);
		assertEquals(2, activityNodeCandidates.size());
		assertTrue(activityNodeCandidates.containsAll(List.of(mergeNode, finalNode)));
	}

	@Test
	public void testGetActivityNodeCandidatesOnInterruptibleActivityRegion() {
		InterruptibleActivityRegion interruptibleActivityRegion = create(InterruptibleActivityRegion.class);
		InitialNode initialNode = create(InitialNode.class);
		ActivityFinalNode finalNode = create(ActivityFinalNode.class);
		MergeNode mergeNode = create(MergeNode.class);
		interruptibleActivityRegion.getNodes().addAll(List.of(initialNode, finalNode, mergeNode));

		Collection<ActivityNode> activityNodeCandidates = this.activityDiagramServices.getActivityNodeCandidates(interruptibleActivityRegion);
		assertEquals(3, activityNodeCandidates.size());
		assertTrue(activityNodeCandidates.containsAll(List.of(initialNode, finalNode, mergeNode)));
	}

	@Test
	public void testGetActivityNodeCandidatesOnStructuredActivity() {
		ExpansionRegion expansionRegion = create(ExpansionRegion.class);
		InitialNode initialNode = create(InitialNode.class);
		ActivityFinalNode finalNode = create(ActivityFinalNode.class);
		MergeNode mergeNode = create(MergeNode.class);
		expansionRegion.getNodes().addAll(List.of(initialNode, finalNode, mergeNode));

		Collection<ActivityNode> activityNodeCandidates = this.activityDiagramServices.getActivityNodeCandidates(expansionRegion);
		assertEquals(3, activityNodeCandidates.size());
		assertTrue(activityNodeCandidates.containsAll(List.of(initialNode, finalNode, mergeNode)));
	}

	@Test
	public void testGetActivityNodeCandidatesOnNull() {
		Collection<ActivityNode> activityNodeCandidates = this.activityDiagramServices.getActivityNodeCandidates(null);
		assertEquals(0, activityNodeCandidates.size());
	}

	@Test
	public void testGetActivityPartitionCandidatesOnActivity() {
		Activity activity = create(Activity.class);
		ActivityPartition activityPartition = create(ActivityPartition.class);
		ActivityPartition activityPartition2 = create(ActivityPartition.class);
		activity.getOwnedGroups().addAll(List.of(activityPartition, activityPartition2));
		activity.getPartitions().addAll(List.of(activityPartition, activityPartition2));

		Collection<ActivityPartition> activityPartitions = this.activityDiagramServices.getActivityPartitionCandidates(activity);
		assertEquals(2, activityPartitions.size());
		assertTrue(activityPartitions.containsAll(List.of(activityPartition, activityPartition2)));
	}

	@Test
	public void testGetActivityPartitionCandidatesOnActivityPartition() {
		ActivityPartition activityPartition = create(ActivityPartition.class);
		ActivityPartition subActivityPartition = create(ActivityPartition.class);
		ActivityPartition subActivityPartition2 = create(ActivityPartition.class);
		activityPartition.getSubpartitions().addAll(List.of(subActivityPartition, subActivityPartition2));
		Collection<ActivityPartition> activityPartitions = this.activityDiagramServices.getActivityPartitionCandidates(activityPartition);
		assertEquals(2, activityPartitions.size());
		assertTrue(activityPartitions.containsAll(List.of(subActivityPartition, subActivityPartition2)));
	}

	@Test
	public void testGetActivityPartitionCandidatesOnNull() {
		Collection<ActivityPartition> activityPartitions = this.activityDiagramServices.getActivityPartitionCandidates(null);
		assertEquals(0, activityPartitions.size());
	}

	@Test
	public void testGetInterruptibleActivityRegionCandidates() {
		Activity activity = create(Activity.class);
		InterruptibleActivityRegion interruptibleActivityRegion = create(InterruptibleActivityRegion.class);
		InterruptibleActivityRegion interruptibleActivityRegion2 = create(InterruptibleActivityRegion.class);
		ActivityPartition activityPartition = create(ActivityPartition.class);
		activity.getOwnedGroups().addAll(List.of(interruptibleActivityRegion, interruptibleActivityRegion2, activityPartition));

		Collection<InterruptibleActivityRegion> interruptibleActivityRegions = this.activityDiagramServices.getInterruptibleActivityRegionCandidates(activity);
		assertEquals(2, interruptibleActivityRegions.size());
		assertTrue(interruptibleActivityRegions.containsAll(List.of(interruptibleActivityRegion, interruptibleActivityRegion2)));
	}

	@Test
	public void testGetInterruptibleActivityRegionCandidatesOnNull() {
		Collection<InterruptibleActivityRegion> interruptibleActivityRegions = this.activityDiagramServices.getInterruptibleActivityRegionCandidates(null);
		assertEquals(0, interruptibleActivityRegions.size());
	}

	@Test
	public void testgetDecisionInputNoteLabelWithoutDecisionInput() {
		DecisionNode decisionNode = create(DecisionNode.class);
		assertEquals(UMLCharacters.EMPTY, this.activityDiagramServices.getDecisionInputNoteLabel(decisionNode));
	}

	@Test
	public void testgetDecisionInputNoteLabelWithDecisionInput() {
		DecisionNode decisionNode = create(DecisionNode.class);
		Activity activity = create(Activity.class);
		activity.setName(MY_ACTIVITY);
		decisionNode.setDecisionInput(activity);
		assertEquals(UMLCharacters.ST_LEFT + DECISION_INPUT + UMLCharacters.ST_RIGHT + UMLCharacters.SPACE + MY_ACTIVITY, this.activityDiagramServices.getDecisionInputNoteLabel(decisionNode));
	}

	@Test
	public void testGetExpansionNodesCandidates() {
		ExpansionRegion expansionRegion = create(ExpansionRegion.class);
		ExpansionNode expansionNode = create(ExpansionNode.class);
		ExpansionNode expansionNode2 = create(ExpansionNode.class);
		ExpansionNode expansionNode3 = create(ExpansionNode.class);
		Collection<ExpansionNode> expansionNodesCandidates = this.activityDiagramServices.getExpansionNodesCandidates(expansionRegion);
		assertTrue(expansionNodesCandidates.isEmpty());

		expansionRegion.getNodes().addAll(List.of(expansionNode, expansionNode2, expansionNode3));
		expansionRegion.getInputElements().add(expansionNode);
		expansionNode.setRegionAsInput(expansionRegion);
		expansionRegion.getOutputElements().addAll(List.of(expansionNode2, expansionNode3));
		expansionNode2.setRegionAsOutput(expansionRegion);
		expansionNode3.setRegionAsOutput(expansionRegion);
		expansionNodesCandidates = this.activityDiagramServices.getExpansionNodesCandidates(expansionRegion);
		assertEquals(3, expansionNodesCandidates.size());
	}

	@Test
	public void testCanCreateAD() {
		AcceptEventAction acceptEventAction = create(AcceptEventAction.class);
		assertTrue(this.activityDiagramServices.canCreateIntoParent(acceptEventAction, UMLPackage.eINSTANCE.getOutputPin()));
		AddVariableValueAction addVariableValueAction = create(AddVariableValueAction.class);
		assertFalse(this.activityDiagramServices.canCreateIntoParent(addVariableValueAction, UMLPackage.eINSTANCE.getOutputPin()));
	}

	@Test
	public void testCanCreateADWithFeatureName() {
		Activity activity = create(Activity.class);
		EClass activityParameterNodeClass = UMLPackage.eINSTANCE.getActivityParameterNode();
		assertTrue(this.activityDiagramServices.canCreateAD(activity, activityParameterNodeClass, "ownedNode")); //$NON-NLS-1$
		assertFalse(this.activityDiagramServices.canCreateAD(activity, activityParameterNodeClass, "nodes")); //$NON-NLS-1$
	}

	@Test
	public void testCanCreateActivityPartition() {
		Activity activity = create(Activity.class);
		assertTrue(this.activityDiagramServices.canCreateActivityPartition(activity));

		ActivityPartition activityPartition = create(ActivityPartition.class);
		assertTrue(this.activityDiagramServices.canCreateActivityPartition(activityPartition));
	}

	@Test
	public void testCanCreateActivityNode() {
		EClass decisionNodeClass = UMLPackage.eINSTANCE.getDecisionNode();
		Activity activity = create(Activity.class);
		assertTrue(this.activityDiagramServices.canCreateActivityNode(activity, decisionNodeClass));

		ActivityPartition activityPartition = create(ActivityPartition.class);
		activity.getOwnedGroups().add(activityPartition);
		assertTrue(this.activityDiagramServices.canCreateActivityNode(activityPartition, decisionNodeClass));

		InterruptibleActivityRegion interruptibleActivityRegion = create(InterruptibleActivityRegion.class);
		activity.getOwnedGroups().add(interruptibleActivityRegion);
		assertTrue(this.activityDiagramServices.canCreateActivityNode(interruptibleActivityRegion, decisionNodeClass));

		StructuredActivityNode structuredActivityNode = create(StructuredActivityNode.class);
		assertTrue(this.activityDiagramServices.canCreateActivityNode(structuredActivityNode, decisionNodeClass));

		Class clazz = create(Class.class);
		assertFalse(this.activityDiagramServices.canCreateActivityNode(clazz, decisionNodeClass));
	}

	@Test
	public void testCanCreateStructuredActivityNode() {
		EClass structuredActivityNodeClass = UMLPackage.eINSTANCE.getStructuredActivityNode();
		Activity activity = create(Activity.class);
		assertTrue(this.activityDiagramServices.canCreateStructuredActivityNode(activity, structuredActivityNodeClass));

		ActivityPartition activityPartition = create(ActivityPartition.class);
		activity.getOwnedGroups().add(activityPartition);
		assertTrue(this.activityDiagramServices.canCreateStructuredActivityNode(activityPartition, structuredActivityNodeClass));

		InterruptibleActivityRegion interruptibleActivityRegion = create(InterruptibleActivityRegion.class);
		activity.getOwnedGroups().add(interruptibleActivityRegion);
		assertTrue(this.activityDiagramServices.canCreateStructuredActivityNode(interruptibleActivityRegion, structuredActivityNodeClass));

		StructuredActivityNode structuredActivityNode = create(StructuredActivityNode.class);
		assertTrue(this.activityDiagramServices.canCreateStructuredActivityNode(structuredActivityNode, structuredActivityNodeClass));

		Class clazz = create(Class.class);
		assertFalse(this.activityDiagramServices.canCreateStructuredActivityNode(clazz, structuredActivityNodeClass));
	}

	@Test
	public void testCanCreateInterruptibleActivityRegion() {
		Activity activity = create(Activity.class);
		assertTrue(this.activityDiagramServices.canCreateInterruptibleActivityRegion(activity));

		ActivityPartition activityPartition = create(ActivityPartition.class);
		assertFalse(this.activityDiagramServices.canCreateInterruptibleActivityRegion(activityPartition));
	}

	@Test
	public void testCanCreateExpansionNode() {
		ExpansionRegion expansionRegion = create(ExpansionRegion.class);
		assertTrue(this.activityDiagramServices.canCreateExpansionNode(expansionRegion));

		Activity activity = create(Activity.class);
		assertFalse(this.activityDiagramServices.canCreateExpansionNode(activity));
	}
}
