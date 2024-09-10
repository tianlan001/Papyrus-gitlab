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

package org.eclipse.papyrus.uml.diagram.activity.tests.edit.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.uml.diagram.activity.edit.parts.AcceptEventActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityEditPartCN;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityFinalNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityParameterNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.AddStructuralFeatureValueActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.AddVariableValueActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.BroadcastSignalActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallOperationActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CentralBufferNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ClearAssociationActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ClearStructuralFeatureActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConditionalNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CreateLinkActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CreateLinkObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CreateObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DataStoreNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DestroyLinkActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DestroyObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.FlowFinalNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ForkNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.InitialNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.InterruptibleActivityRegionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.JoinNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.LoopNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.MergeNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.OpaqueActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadExtentActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadIsClassifiedObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadLinkActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadSelfActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadStructuralFeatureActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadVariableActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReclassifyObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReduceActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SendObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SendSignalActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SequenceNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StartClassifierBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StartObjectBehavoiurActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StructuredActivityNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.TestIdentityActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.UnmarshallActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ValueSpecificationActionEditPart;
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

		classes.add(ActivityEditPart.class);
		classes.add(ActivityEditPartCN.class);
		classes.add(AcceptEventActionEditPart.class);
		classes.add(ActivityFinalNodeEditPart.class);
		classes.add(ActivityPartitionEditPart.class);
		classes.add(AddStructuralFeatureValueActionEditPart.class);
		classes.add(AddVariableValueActionEditPart.class);
		classes.add(BroadcastSignalActionEditPart.class);
		classes.add(CallBehaviorActionEditPart.class);
		classes.add(CallOperationActionEditPart.class);
		classes.add(CentralBufferNodeEditPart.class);
		classes.add(ClearAssociationActionEditPart.class);
		classes.add(ClearStructuralFeatureActionEditPart.class);
		classes.add(ConditionalNodeEditPart.class);
		classes.add(CreateLinkActionEditPart.class);
		classes.add(CreateLinkObjectActionEditPart.class);
		classes.add(CreateObjectActionEditPart.class);
		classes.add(DataStoreNodeEditPart.class);
		classes.add(DecisionNodeEditPart.class);
		classes.add(DestroyLinkActionEditPart.class);
		classes.add(DestroyObjectActionEditPart.class);
		classes.add(ExpansionRegionEditPart.class);
		classes.add(FlowFinalNodeEditPart.class);
		classes.add(ForkNodeEditPart.class);
		classes.add(InitialNodeEditPart.class);
		classes.add(InterruptibleActivityRegionEditPart.class);
		classes.add(JoinNodeEditPart.class);
		classes.add(LoopNodeEditPart.class);
		classes.add(MergeNodeEditPart.class);
		classes.add(OpaqueActionEditPart.class);
		classes.add(ReadExtentActionEditPart.class);
		classes.add(ReadIsClassifiedObjectActionEditPart.class);
		classes.add(ReadLinkActionEditPart.class);
		classes.add(ReadSelfActionEditPart.class);
		classes.add(ReadStructuralFeatureActionEditPart.class);
		classes.add(ReadVariableActionEditPart.class);
		classes.add(ReclassifyObjectActionEditPart.class);
		classes.add(ReduceActionEditPart.class);
		classes.add(SendObjectActionEditPart.class);
		classes.add(SendSignalActionEditPart.class);
		classes.add(SequenceNodeEditPart.class);
		classes.add(StartClassifierBehaviorActionEditPart.class);
		classes.add(StartObjectBehavoiurActionEditPart.class);
		classes.add(StructuredActivityNodeEditPart.class);
		classes.add(TestIdentityActionEditPart.class);
		classes.add(ValueSpecificationActionEditPart.class);
		classes.add(UnmarshallActionEditPart.class);

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
		classes.add(ActivityParameterNodeEditPart.class);
		return classes;
	}
}
