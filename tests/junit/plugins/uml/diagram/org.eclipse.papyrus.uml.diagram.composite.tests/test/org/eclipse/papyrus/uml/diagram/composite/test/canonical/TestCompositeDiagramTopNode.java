/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST.
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
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 527259
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.test.canonical;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.composite.CreateCompositeDiagramCommand;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.composite.test.ICompositeDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestTopNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestCompositeDiagramLink use to test link.
 */
public class TestCompositeDiagramTopNode extends TestTopNode {
	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	/**
	 * @see org.eclipse.papyrus.diagram.tests.canonical.AbstractPapyrusTestCase#getDiagramCommandCreation()
	 *
	 * @return
	 */

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateCompositeDiagramCommand();
	}

	/**
	 * @see org.eclipse.papyrus.diagram.tests.canonical.AbstractPapyrusTestCase#getProjectName()
	 *
	 * @return
	 */

	@Override
	protected String getProjectName() {
		return ICompositeDiagramTestsConstants.PROJECT_NAME;
	}

	/**
	 * @see org.eclipse.papyrus.diagram.tests.canonical.AbstractPapyrusTestCase#getFileName()
	 *
	 * @return
	 */

	@Override
	protected String getFileName() {
		return ICompositeDiagramTestsConstants.FILE_NAME;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Class_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	/**
	 * Test to manage Activity.
	 */
	@Test
	public void testToActivity() {
		testToManageNode(UMLElementTypes.Activity_Shape, UMLPackage.eINSTANCE.getActivity(), UMLElementTypes.Activity_Shape, false);
	}

	/**
	 * Test to manage Interaction.
	 */
	@Test
	public void testToInteraction() {
		testToManageNode(UMLElementTypes.Interaction_Shape, UMLPackage.eINSTANCE.getInteraction(), UMLElementTypes.Interaction_Shape, false);
	}

	/**
	 * Test to manage ProtocolStateMachine.
	 */
	@Test
	public void testToProtocolStateMachine() {
		testToManageNode(UMLElementTypes.ProtocolStateMachine_Shape, UMLPackage.eINSTANCE.getProtocolStateMachine(), UMLElementTypes.ProtocolStateMachine_Shape, false);
	}

	/**
	 * Test to manage StateMachine.
	 */
	@Test
	public void testToStateMachine() {
		testToManageNode(UMLElementTypes.StateMachine_Shape, UMLPackage.eINSTANCE.getStateMachine(), UMLElementTypes.StateMachine_Shape, false);
	}

	/**
	 * Test to manage FunctionBehavior.
	 */
	@Test
	public void testToFunctionBehavior() {
		testToManageNode(UMLElementTypes.FunctionBehavior_Shape, UMLPackage.eINSTANCE.getFunctionBehavior(), UMLElementTypes.FunctionBehavior_Shape, false);
	}

	/**
	 * Test to manage OpaqueBehavior.
	 */
	@Test
	public void testToOpaqueBehavior() {
		testToManageNode(UMLElementTypes.OpaqueBehavior_Shape, UMLPackage.eINSTANCE.getOpaqueBehavior(), UMLElementTypes.OpaqueBehavior_Shape, false);
	}

	/**
	 * Test to manage Component.
	 */
	@Test
	public void testToComponent() {
		testToManageNode(UMLElementTypes.Component_Shape, UMLPackage.eINSTANCE.getComponent(), UMLElementTypes.Component_Shape, false);
	}

	/**
	 * Test to manage Device.
	 */
	@Test
	public void testToDevice() {
		testToManageNode(UMLElementTypes.Device_Shape, UMLPackage.eINSTANCE.getDevice(), UMLElementTypes.Device_Shape, false);
	}

	/**
	 * Test to manage ExecutionEnvironment.
	 */
	@Test
	public void testToExecutionEnvironment() {
		testToManageNode(UMLElementTypes.ExecutionEnvironment_Shape, UMLPackage.eINSTANCE.getExecutionEnvironment(), UMLElementTypes.ExecutionEnvironment_Shape, false);
	}

	/**
	 * Test to manage Node.
	 */
	@Test
	public void testToNode() {
		testToManageNode(UMLElementTypes.Node_Shape, UMLPackage.eINSTANCE.getNode(), UMLElementTypes.Node_Shape, false);
	}

	/**
	 * Test to manage Class.
	 */
	@Test
	public void testToClass() {
		testToManageNode(UMLElementTypes.Class_Shape, UMLPackage.eINSTANCE.getClass_(), UMLElementTypes.Class_Shape, false);
	}

	/**
	 * Test to manage Collaboration.
	 */
	@Test
	public void testToCollaboration() {
		testToManageNode(UMLElementTypes.Collaboration_Shape, UMLPackage.eINSTANCE.getCollaboration(), UMLElementTypes.Collaboration_Shape, false);
	}

	/**
	 * Test to manage Interface.
	 */
	@Test
	public void testToInterface() {
		testToManageNode(UMLElementTypes.Interface_Shape, UMLPackage.eINSTANCE.getInterface(), UMLElementTypes.Interface_Shape, false);
	}

	/**
	 * Test to manage PrimitiveType.
	 */
	@Test
	public void testToPrimitiveType() {
		testToManageNode(UMLElementTypes.PrimitiveType_Shape, UMLPackage.eINSTANCE.getPrimitiveType(), UMLElementTypes.PrimitiveType_Shape, false);
	}

	/**
	 * Test to manage Enumeration.
	 */
	@Test
	public void testToEnumeration() {
		testToManageNode(UMLElementTypes.Enumeration_Shape, UMLPackage.eINSTANCE.getEnumeration(), UMLElementTypes.Enumeration_Shape, false);
	}

	/**
	 * Test to manage DataType.
	 */
	@Test
	public void testToDataType() {
		testToManageNode(UMLElementTypes.DataType_Shape, UMLPackage.eINSTANCE.getDataType(), UMLElementTypes.DataType_Shape, false);
	}

	/**
	 * Test to manage Actor.
	 */
	@Test
	public void testToActor() {
		testToManageNode(UMLElementTypes.Actor_Shape, UMLPackage.eINSTANCE.getActor(), UMLElementTypes.Actor_Shape, false);
	}

	/**
	 * Test to manage DeploymentSpecification.
	 */
	@Test
	public void testToDeploymentSpecification() {
		testToManageNode(UMLElementTypes.DeploymentSpecification_Shape, UMLPackage.eINSTANCE.getDeploymentSpecification(), UMLElementTypes.DeploymentSpecification_Shape, false);
	}

	/**
	 * Test to manage Artifact.
	 */
	@Test
	public void testToArtifact() {
		testToManageNode(UMLElementTypes.Artifact_Shape, UMLPackage.eINSTANCE.getArtifact(), UMLElementTypes.Artifact_Shape, false);
	}

	/**
	 * Test to manage InformationItem.
	 */
	@Test
	public void testToInformationItem() {
		testToManageNode(UMLElementTypes.InformationItem_Shape, UMLPackage.eINSTANCE.getInformationItem(), UMLElementTypes.InformationItem_Shape, false);
	}

	/**
	 * Test to manage Signal.
	 */
	@Test
	public void testToSignal() {
		testToManageNode(UMLElementTypes.Signal_Shape, UMLPackage.eINSTANCE.getSignal(), UMLElementTypes.Signal_Shape, false);
	}

	/**
	 * Test to manage UseCase.
	 */
	@Test
	public void testToUseCase() {
		testToManageNode(UMLElementTypes.UseCase_Shape, UMLPackage.eINSTANCE.getUseCase(), UMLElementTypes.UseCase_Shape, false);
	}

	/**
	 * Test to manage SignalEvent.
	 */
	@Test
	public void testToSignalEvent() {
		testToManageNode(UMLElementTypes.SignalEvent_Shape, UMLPackage.eINSTANCE.getSignalEvent(), UMLElementTypes.SignalEvent_Shape, false);
	}

	/**
	 * Test to manage CallEvent.
	 */
	@Test
	public void testToCallEvent() {
		testToManageNode(UMLElementTypes.CallEvent_Shape, UMLPackage.eINSTANCE.getCallEvent(), UMLElementTypes.CallEvent_Shape, false);
	}

	/**
	 * Test to manage AnyReceiveEvent.
	 */
	@Test
	public void testToAnyReceiveEvent() {
		testToManageNode(UMLElementTypes.AnyReceiveEvent_Shape, UMLPackage.eINSTANCE.getAnyReceiveEvent(), UMLElementTypes.AnyReceiveEvent_Shape, false);
	}

	/**
	 * Test to manage ChangeEvent.
	 */
	@Test
	public void testToChangeEvent() {
		testToManageNode(UMLElementTypes.ChangeEvent_Shape, UMLPackage.eINSTANCE.getChangeEvent(), UMLElementTypes.ChangeEvent_Shape, false);
	}

	/**
	 * Test to manage TimeEvent.
	 */
	@Test
	public void testToTimeEvent() {
		testToManageNode(UMLElementTypes.TimeEvent_Shape, UMLPackage.eINSTANCE.getTimeEvent(), UMLElementTypes.TimeEvent_Shape, false);
	}

	/**
	 * Test to manage DurationObservation.
	 */
	@Test
	public void testToDurationObservation() {
		testToManageNode(UMLElementTypes.DurationObservation_Shape, UMLPackage.eINSTANCE.getDurationObservation(), UMLElementTypes.DurationObservation_Shape, false);
	}

	/**
	 * Test to manage TimeObservation.
	 */
	@Test
	public void testToTimeObservation() {
		testToManageNode(UMLElementTypes.TimeObservation_Shape, UMLPackage.eINSTANCE.getTimeObservation(), UMLElementTypes.TimeObservation_Shape, false);
	}

	/**
	 * Test to manage LiteralBoolean.
	 */
	@Test
	public void testToLiteralBoolean() {
		testToManageNode(UMLElementTypes.LiteralBoolean_Shape, UMLPackage.eINSTANCE.getLiteralBoolean(), UMLElementTypes.LiteralBoolean_Shape, false);
	}

	/**
	 * Test to manage LiteralInteger.
	 */
	@Test
	public void testToLiteralInteger() {
		testToManageNode(UMLElementTypes.LiteralInteger_Shape, UMLPackage.eINSTANCE.getLiteralInteger(), UMLElementTypes.LiteralInteger_Shape, false);
	}

	/**
	 * Test to manage LiteralNull.
	 */
	@Test
	public void testToLiteralNull() {
		testToManageNode(UMLElementTypes.LiteralNull_Shape, UMLPackage.eINSTANCE.getLiteralNull(), UMLElementTypes.LiteralNull_Shape, false);
	}

	/**
	 * Test to manage LiteralString.
	 */
	@Test
	public void testToLiteralString() {
		testToManageNode(UMLElementTypes.LiteralString_Shape, UMLPackage.eINSTANCE.getLiteralString(), UMLElementTypes.LiteralString_Shape, false);
	}

	/**
	 * Test to manage LiteralUnlimitedNatural.
	 */
	@Test
	public void testToLiteralUnlimitedNatural() {
		testToManageNode(UMLElementTypes.LiteralUnlimitedNatural_Shape, UMLPackage.eINSTANCE.getLiteralUnlimitedNatural(), UMLElementTypes.LiteralUnlimitedNatural_Shape, false);
	}

	/**
	 * Test to manage StringExpression.
	 */
	@Test
	public void testToStringExpression() {
		testToManageNode(UMLElementTypes.StringExpression_PackagedElementShape, UMLPackage.eINSTANCE.getStringExpression(), UMLElementTypes.StringExpression_PackagedElementShape, false);
	}

	/**
	 * Test to manage OpaqueExpression.
	 */
	@Test
	public void testToOpaqueExpression() {
		testToManageNode(UMLElementTypes.OpaqueExpression_Shape, UMLPackage.eINSTANCE.getOpaqueExpression(), UMLElementTypes.OpaqueExpression_Shape, false);
	}

	/**
	 * Test to manage TimeExpression.
	 */
	@Test
	public void testToTimeExpression() {
		testToManageNode(UMLElementTypes.TimeExpression_Shape, UMLPackage.eINSTANCE.getTimeExpression(), UMLElementTypes.TimeExpression_Shape, false);
	}

	/**
	 * Test to manage Expression.
	 */
	@Test
	public void testToExpression() {
		testToManageNode(UMLElementTypes.Expression_Shape, UMLPackage.eINSTANCE.getExpression(), UMLElementTypes.Expression_Shape, false);
	}

	/**
	 * Test to manage Duration.
	 */
	@Test
	public void testToDuration() {
		testToManageNode(UMLElementTypes.Duration_Shape, UMLPackage.eINSTANCE.getDuration(), UMLElementTypes.Duration_Shape, false);
	}

	/**
	 * Test to manage TimeInterval.
	 */
	@Test
	public void testToTimeInterval() {
		testToManageNode(UMLElementTypes.TimeInterval_Shape, UMLPackage.eINSTANCE.getTimeInterval(), UMLElementTypes.TimeInterval_Shape, false);
	}

	/**
	 * Test to manage DurationInterval.
	 */
	@Test
	public void testToDurationInterval() {
		testToManageNode(UMLElementTypes.DurationInterval_Shape, UMLPackage.eINSTANCE.getDurationInterval(), UMLElementTypes.DurationInterval_Shape, false);
	}

	/**
	 * Test to manage Interval.
	 */
	@Test
	public void testToInterval() {
		testToManageNode(UMLElementTypes.Interval_Shape, UMLPackage.eINSTANCE.getInterval(), UMLElementTypes.Interval_Shape, false);
	}

	/**
	 * Test to manage InstanceValue.
	 */
	@Test
	public void testToInstanceValue() {
		testToManageNode(UMLElementTypes.InstanceValue_Shape, UMLPackage.eINSTANCE.getInstanceValue(), UMLElementTypes.InstanceValue_Shape, false);
	}

	/**
	 * Test to manage Comment.
	 */
	@Test
	public void testToComment() {
		testToManageNode(UMLElementTypes.Comment_Shape, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Comment_Shape, false);
	}

	/**
	 * Test to manage DurationConstraint.
	 */
	@Test
	public void testToDurationConstraint() {
		testToManageNode_ForIntervalConstraint(UMLElementTypes.DurationConstraint_Shape, UMLPackage.eINSTANCE.getDurationConstraint(), UMLElementTypes.DurationConstraint_Shape, false, null, 0);
	}

	/**
	 * Test to manage TimeConstraint.
	 */
	@Test
	public void testToTimeConstraint() {
		testToManageNode(UMLElementTypes.TimeConstraint_Shape, UMLPackage.eINSTANCE.getTimeConstraint(), UMLElementTypes.TimeConstraint_Shape, false);
	}

	/**
	 * Test to manage IntervalConstraint.
	 */
	@Test
	public void testToIntervalConstraint() {
		testToManageNode(UMLElementTypes.IntervalConstraint_Shape, UMLPackage.eINSTANCE.getIntervalConstraint(), UMLElementTypes.IntervalConstraint_Shape, false);
	}

	/**
	 * Test to manage InteractionConstraint.
	 */
	@Test
	public void testToInteractionConstraint() {
		testToManageNode(UMLElementTypes.InteractionConstraint_Shape, UMLPackage.eINSTANCE.getInteractionConstraint(), UMLElementTypes.InteractionConstraint_Shape, false);
	}

	/**
	 * Test to manage Constraint.
	 */
	@Test
	public void testToConstraint() {
		testToManageNode(UMLElementTypes.Constraint_Shape, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Constraint_Shape, false);
	}

	/**
	 * Test to manage Interval Constraint node.
	 * Two Intervals (Min, Max) was created with IntervalConstraint.
	 *
	 * @param type
	 *            the type
	 * @param containerType
	 *            the container type
	 */
	public void testToManageNode_ForIntervalConstraint(IElementType type, EClass eClass, IElementType containerType, boolean containerMove, String initialName, int numberSemanticChildreen) {
		// create a node (Creation of IntervalContraint, MinInterval, MaxInterval)
		testToCreateANode(type, 0, 0, 1, 3, false, initialName, numberSemanticChildreen);
		// creates a second one (Creation of IntervalContraint, MinInterval, MaxInterval)
		testToCreateANode(type, 1, 3, 1, 3, false, initialName, numberSemanticChildreen);
		// destroy the first element (it does not destroy MinInterval and MaxInterval)
		testDestroy(type, 2, 6, 1, 1);
		// destroy the second one (it does not destroy MinInterval and MaxInterval)
		testDestroy(type, 1, 5, 1, 1);
		// the node has been destroyed, the UML element also. restore one element
		undoOnUIThread();
		// the node and the UML element are present ((it does not destroy MinInterval and MaxInterval))
		testViewDeletion(type, 1, 5, 1);
		// The node has been deleted, the uml element is still present
		testDrop(type, eClass, 0, 5, 1);

		// The element can be dropped several time in the diagrams
		testDrop(type, eClass, 1, 5, 1);

		// undo the second drop
		undoOnUIThread();
		// the node and element are present
		if (containerMove) {
			testChangeContainer(type, containerType);
		}
	}
}
