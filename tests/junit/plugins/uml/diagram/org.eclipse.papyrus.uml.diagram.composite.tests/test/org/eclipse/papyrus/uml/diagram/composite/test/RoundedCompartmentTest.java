/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ActivityCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ActivityCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.AnyReceiveEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.BehaviorPortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CallEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ChangeEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CollaborationCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CollaborationCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CollaborationRoleEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CollaborationUseEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ComponentCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ComponentCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DataTypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DataTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DeploymentSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DeploymentSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DeviceCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DeviceCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DurationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DurationIntervalEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DurationObservationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.EnumerationEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ExecutionEnvironmentCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ExecutionEnvironmentCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ExpressionEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.FunctionBehaviorCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.FunctionBehaviorCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InformationItemEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InformationItemEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InstanceValueEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InteractionCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InteractionCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InterfaceEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.IntervalEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralBooleanEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralIntegerEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralNullEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralStringEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralUnlimitedNaturalEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.NodeCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.NodeCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.OpaqueBehaviorCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.OpaqueBehaviorCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.OpaqueExpressionEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ParameterEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PrimitiveTypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PrimitiveTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ProtocolStateMachineCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ProtocolStateMachineCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.SignalEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.SignalEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.SignalEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.StateMachineCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.StateMachineCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.StringExpressionEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeExpressionEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeIntervalEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeObservationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.UseCaseEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.UseCaseEditPartCN;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractGenericShapeTest;

/**
 * The Class RoundedCompartmentTest use to test if edit part which need it, extends RoundCompartmentEditPart and RoundBorderNameElementEditPart.
 */
public class RoundedCompartmentTest extends AbstractGenericShapeTest {

	/**
	 * Gets the rounded compartment edit parts.
	 *
	 * @return the rounded compartment edit parts
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractGenericShapeTest#getRoundedCompartmentEditParts()
	 */
	public List<Class<?>> getRoundedCompartmentEditParts() {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		classes.add(BehaviorPortEditPart.class);
		classes.add(ActivityCompositeEditPart.class);
		classes.add(ActivityCompositeEditPartCN.class);
		classes.add(AnyReceiveEventEditPart.class);
		classes.add(CallEventEditPart.class);
		classes.add(ChangeEventEditPart.class);
		classes.add(ClassCompositeEditPart.class);
		classes.add(ClassCompositeEditPartCN.class);
		classes.add(CollaborationCompositeEditPart.class);
		classes.add(CollaborationCompositeEditPartCN.class);
		classes.add(CollaborationRoleEditPartCN.class);
		classes.add(CollaborationUseEditPartCN.class);
		classes.add(ComponentCompositeEditPart.class);
		classes.add(ComponentCompositeEditPartCN.class);
		classes.add(DataTypeEditPart.class);
		classes.add(DataTypeEditPartCN.class);
		classes.add(DeploymentSpecificationEditPart.class);
		classes.add(DeploymentSpecificationEditPartCN.class);
		classes.add(DeviceCompositeEditPart.class);
		classes.add(DeviceCompositeEditPartCN.class);
		classes.add(DurationEditPart.class);
		classes.add(DurationIntervalEditPart.class);
		classes.add(DurationObservationEditPart.class);
		classes.add(EnumerationEditPart.class);
		classes.add(EnumerationEditPartCN.class);
		classes.add(ExecutionEnvironmentCompositeEditPart.class);
		classes.add(ExecutionEnvironmentCompositeEditPartCN.class);
		classes.add(ExpressionEditPart.class);
		classes.add(FunctionBehaviorCompositeEditPart.class);
		classes.add(FunctionBehaviorCompositeEditPartCN.class);
		classes.add(InformationItemEditPart.class);
		classes.add(InformationItemEditPartCN.class);
		classes.add(InstanceValueEditPart.class);
		classes.add(InteractionCompositeEditPart.class);
		classes.add(InteractionCompositeEditPartCN.class);
		classes.add(InterfaceEditPart.class);
		classes.add(InterfaceEditPartCN.class);
		classes.add(IntervalEditPart.class);
		classes.add(LiteralBooleanEditPart.class);
		classes.add(LiteralIntegerEditPart.class);
		classes.add(LiteralNullEditPart.class);
		classes.add(LiteralStringEditPart.class);
		classes.add(LiteralUnlimitedNaturalEditPart.class);
		classes.add(NodeCompositeEditPart.class);
		classes.add(NodeCompositeEditPartCN.class);
		classes.add(OpaqueBehaviorCompositeEditPart.class);
		classes.add(OpaqueBehaviorCompositeEditPartCN.class);
		classes.add(OpaqueExpressionEditPart.class);
		classes.add(PrimitiveTypeEditPart.class);
		classes.add(PrimitiveTypeEditPartCN.class);
		classes.add(PropertyPartEditPartCN.class);
		classes.add(ProtocolStateMachineCompositeEditPart.class);
		classes.add(ProtocolStateMachineCompositeEditPartCN.class);
		classes.add(SignalEditPart.class);
		classes.add(SignalEditPartCN.class);
		classes.add(SignalEventEditPart.class);
		classes.add(StateMachineCompositeEditPart.class);
		classes.add(StateMachineCompositeEditPartCN.class);
		classes.add(StringExpressionEditPart.class);
		classes.add(TimeEventEditPart.class);
		classes.add(TimeExpressionEditPart.class);
		classes.add(TimeIntervalEditPart.class);
		classes.add(TimeObservationEditPart.class);
		classes.add(UseCaseEditPart.class);
		classes.add(UseCaseEditPartCN.class);
		return classes;
	}

	/**
	 * Gets the rounded border named element edit parts.
	 *
	 * @return the rounded border named element edit parts
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractGenericShapeTest#getRoundedBorderNamedElementEditParts()
	 */
	public List<Class<?>> getRoundedBorderNamedElementEditParts() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(ParameterEditPart.class);
		classes.add(PortEditPart.class);
		return classes;
	}
}
