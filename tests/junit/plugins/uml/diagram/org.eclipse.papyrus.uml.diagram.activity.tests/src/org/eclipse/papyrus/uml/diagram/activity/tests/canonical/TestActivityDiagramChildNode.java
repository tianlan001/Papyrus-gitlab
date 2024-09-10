/*****************************************************************************
 * Copyright (c) 2009, 2023 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 582007
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.classification.InteractiveTest;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.activity.tests.IActivityDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestClassDiagramChildNode.
 */
public class TestActivityDiagramChildNode extends AbstractTestActivityChildNode {

	@Override
	protected String getProjectName() {
		return IActivityDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected String getFileName() {
		return IActivityDiagramTestsConstants.FILE_NAME;
	}

	@Override
	protected boolean isSemanticTest() {
		return true;
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageCentralBufferNode() {
		testToManageNode(UMLElementTypes.CentralBufferNode_Shape, UMLPackage.eINSTANCE.getCentralBufferNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageOpaqueAction() {
		testToManageNode(UMLElementTypes.OpaqueAction_Shape, UMLPackage.eINSTANCE.getOpaqueAction(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage initialNode.
	 */
	@Test
	public void testToManageInitialNode() {
		testToManageNode(UMLElementTypes.InitialNode_Shape, UMLPackage.eINSTANCE.getInitialNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage finalNode.
	 */
	@Test
	public void testToManageActivityFinalNode() {
		testToManageNode(UMLElementTypes.ActivityFinalNode_Shape, UMLPackage.eINSTANCE.getActivityFinalNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage flow final.
	 */
	@Test
	public void testToManageFlowFinalNode() {
		testToManageNode(UMLElementTypes.FlowFinalNode_Shape, UMLPackage.eINSTANCE.getFlowFinalNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageDecisionNode() {
		testToManageNode(UMLElementTypes.DecisionNode_Shape, UMLPackage.eINSTANCE.getDecisionNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage MergeNode
	 */
	@Test
	public void testToManageMergeNode() {
		testToManageNode(UMLElementTypes.MergeNode_Shape, UMLPackage.eINSTANCE.getMergeNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage.JoinNode
	 */
	@Test
	public void testToManageJoinNode() {
		testToManageNode(UMLElementTypes.JoinNode_Shape, UMLPackage.eINSTANCE.getJoinNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage ForkNode_Shape
	 */
	@Test
	public void testToManageForkNode() {
		testToManageNode(UMLElementTypes.ForkNode_Shape, UMLPackage.eINSTANCE.getForkNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageDataStoreNode() {
		testToManageNode(UMLElementTypes.DataStoreNode_Shape, UMLPackage.eINSTANCE.getDataStoreNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageSendObjectAction() {
		// Change for Bug 438560: 2 pins were created by the Live validation actions. These validation actions are not live anymore.
		// FIXME: Implement a specific Palette post-action to create the pins automatically (Independently of the validation rules)
		testToManageNode(UMLElementTypes.SendObjectAction_Shape, UMLPackage.eINSTANCE.getSendObjectAction(), UMLElementTypes.StructuredActivityNode_Shape, true, 0);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageAcceptEventAction() {
		testToManageNode(UMLElementTypes.AcceptEventAction_Shape, UMLPackage.eINSTANCE.getAcceptEventAction(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageValueSpecificationAction() {
		testToManageNode(UMLElementTypes.ValueSpecificationAction_Shape, UMLPackage.eINSTANCE.getValueSpecificationAction(), UMLElementTypes.StructuredActivityNode_Shape, true, 1);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageActivityPartition() {
		testToManageNode(UMLElementTypes.ActivityPartition_Shape, UMLPackage.eINSTANCE.getActivityPartition(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	/**
	 * Test to manageInteruptibleActivityRegion.
	 */
	@FailingTest("IARegion hasn't label and AbstractTestNode#testNameLabel is failing")
	@Test
	public void testToManageInteruptibleActivityRegion() {
		testToManageNode(UMLElementTypes.InterruptibleActivityRegion_Shape, UMLPackage.eINSTANCE.getInterruptibleActivityRegion(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	/**
	 * Test to manageStructuredActivity.
	 */
	@Test
	public void testToManageStructuredActivity() {
		testToManageNode(UMLElementTypes.StructuredActivityNode_Shape, UMLPackage.eINSTANCE.getStructuredActivityNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage ReadSelfAction.
	 */
	@Test
	public void testToManageReadSelfAction() {
		// Pin of ReadSelfAction should be create and update automatically
		// Set Automated Model Completion preference to NONE
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.READ_SELF_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.NONE);

		testToManageNode(UMLElementTypes.ReadSelfAction_Shape, UMLPackage.eINSTANCE.getReadSelfAction(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageConditionalNode() {
		testToManageNode(UMLElementTypes.ConditionalNode_Shape, UMLPackage.eINSTANCE.getConditionalNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageExpansionRegion() {
		testToManageNode(UMLElementTypes.ExpansionRegion_Shape, UMLPackage.eINSTANCE.getExpansionRegion(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageloopNode() {
		testToManageNode(UMLElementTypes.LoopNode_Shape, UMLPackage.eINSTANCE.getLoopNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageSequenceNode() {
		testToManageNode(UMLElementTypes.SequenceNode_Shape, UMLPackage.eINSTANCE.getSequenceNode(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage Activity
	 */
	@Test
	@InteractiveTest
	public void testToManageActivity() {
		testToManageNode(UMLElementTypes.Activity_Shape_CN, UMLPackage.eINSTANCE.getActivity(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	/**
	 * Test to manage readStructuralFeatureAction
	 */
	@Test
	public void testToManageReadStructuralFeatureAction() {
		// Change for Bug 438560: 2 pins were created by the Live validation actions. These validation actions are not live anymore.
		// FIXME: Implement a specific Palette post-action to create the pins automatically (Independently of the validation rules)
		testToManageNode(UMLElementTypes.ReadStructuralFeatureAction_Shape, UMLPackage.eINSTANCE.getReadStructuralFeatureAction(), UMLElementTypes.StructuredActivityNode_Shape, true, 1);
	}

	/**
	 * Test to manage DestroyObjectAction
	 */
	@Test
	public void testToManageDestroyObjectAction() {
		// Change for Bug 438560: 1 pin was created by the Live validation actions. These validation actions are not live anymore.
		// FIXME: Implement a specific Palette post-action to create the pins automatically (Independently of the validation rules)
		testToManageNode(UMLElementTypes.DestroyObjectAction_Shape, UMLPackage.eINSTANCE.getDestroyObjectAction(), UMLElementTypes.StructuredActivityNode_Shape, true, 0);
	}

	/**
	 * Test to manage ReadVariableAction.
	 */
	@Test
	public void testToManageReadVariableAction() {
		// Change for Bug 438560: 1 pin was created by the Live validation actions. These validation actions are not live anymore.
		// FIXME: Implement a specific Palette post-action to create the pins automatically (Independently of the validation rules)
		testToManageNode(UMLElementTypes.ReadVariableAction_Shape, UMLPackage.eINSTANCE.getReadVariableAction(), UMLElementTypes.StructuredActivityNode_Shape, true, 0);
	}

	/**
	 * Test to manageDecision Node.
	 */
	@Test
	public void testToManageBroadCastSignalAction() {
		testToManageNode(UMLElementTypes.BroadcastSignalAction_Shape, UMLPackage.eINSTANCE.getBroadcastSignalAction(), UMLElementTypes.StructuredActivityNode_Shape, true);
	}

	/**
	 * Test to manage CreateObjectAction.
	 */
	@Test
	public void testToManageCreateObjectAction() {
		// Change for Bug 438560: 1 pin was created by the Live validation actions. These validation actions are not live anymore.
		// FIXME: Implement a specific Palette post-action to create the pins automatically (Independently of the validation rules)
		testToManageNode(UMLElementTypes.CreateObjectAction_Shape, UMLPackage.eINSTANCE.getCreateObjectAction(), UMLElementTypes.StructuredActivityNode_Shape, true, 1);
	}

	/**
	 * Test to manage AddVariableValueAction
	 */
	@Test
	public void testToManageAddVariableValueAction() {
		// Change for Bug 438560: 2 pins were created by the Live validation actions. These validation actions are not live anymore.
		// FIXME: Implement a specific Palette post-action to create the pins automatically (Independently of the validation rules)
		testToManageNode(UMLElementTypes.AddVariableValueAction_Shape, UMLPackage.eINSTANCE.getAddVariableValueAction(), UMLElementTypes.StructuredActivityNode_Shape, true, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		// no container, it should be created on the main activity, not the diagram edit part
		return null;
	}

}
