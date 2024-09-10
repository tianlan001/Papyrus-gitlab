/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	 Florian Noyrit  (CEA) florian.noyrit@cea.fr - Initial API and Implementation
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - reconciler to add floating label
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.migration;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler;

/**
 * Composite Structure Diagram Reconciler from 1.1.0 to 1.2.0
 */
public class CompositeReconciler_1_2_0 extends DiagramReconciler {

	@Override
	public ICommand getReconcileCommand(Diagram diagram) {
		CompositeCommand cc = new CompositeCommand("Migrate diagram from 1.1.0 to 1.2.0");
		cc.add(new ChangeVisualIDsCommand(diagram));
		return cc;
	}

	protected class ChangeVisualIDsCommand extends AbstractCommand {

		protected final Diagram diagram;

		public ChangeVisualIDsCommand(Diagram diagram) {
			super("Change the diagram's visual ids from 1.1.0 to 1.2.0");
			this.diagram = diagram;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			TreeIterator<EObject> allContentIterator = diagram.eAllContents();

			while (allContentIterator.hasNext()) {
				EObject eObject = allContentIterator.next();
				if (eObject instanceof View) {
					View view = (View) eObject;
					view.setType(getNewVisualID(view.getType()));
				}
			}

			return CommandResult.newOKCommandResult();
		}

		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public boolean canRedo() {
			return false;
		}

		@Override
		protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			throw new ExecutionException("Should not be called, canRedo false");
		}

		@Override
		protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			throw new ExecutionException("Should not be called, canUndo false");
		}
	}

	public static String getNewVisualID(String oldVisualID) {
		switch (oldVisualID) {
		case "1000":
			return "Package_CompositeStructureDiagram";
		case "3121":
			return "Port_BehaviorShape";
		case "6053":
			return "Port_BehaviorFloatingNameLabel";
		case "3069":
			return "Port_Shape";
		case "5125":
			return "Port_NameLabel";
		case "6029":
			return "Port_StereotypeLabel";
		case "3088":
			return "Parameter_Shape";
		case "6033":
			return "Parameter_NameLabel";
		case "6034":
			return "Parameter_StereotypeLabel";
		case "3070":
			return "Property_Shape";
		case "5126":
			return "Property_NameLabel";
		case "6054":
			return "Property_FloatingNameLabel";
		case "3115":
			return "ConnectableElement_CollaborationRoleShape";
		case "5198":
			return "ConnectableElement_CollaborationRoleNameLabel";
		case "6055":
			return "ConnectableElement_CollaborationRoleFloatingNameLabel";
		case "3071":
			return "CollaborationUse_Shape";
		case "5127":
			return "CollaborationUse_NameLabel";
		case "6056":
			return "CollaborationUse_FloatingNameLabel";
		case "3072":
			return "Activity_Shape_CN";
		case "5128":
			return "Activity_NameLabel_CN";
		case "6057":
			return "Activity_FloatingNameLabel_CN";
		case "3073":
			return "Interaction_Shape_CN";
		case "5129":
			return "Interaction_NameLabel_CN";
		case "6058":
			return "Interaction_FloatingNameLabel_CN";
		case "3074":
			return "ProtocolStateMachine_Shape_CN";
		case "5130":
			return "ProtocolStateMachine_NameLabel_CN";
		case "6059":
			return "ProtocolStateMachine_FloatingNameLabel_CN";
		case "3075":
			return "StateMachine_Shape_CN";
		case "5131":
			return "StateMachine_NameLabel_CN";
		case "6060":
			return "StateMachine_FloatingNameLabel_CN";
		case "3076":
			return "FunctionBehavior_Shape_CN";
		case "5132":
			return "FunctionBehavior_NameLabel_CN";
		case "6061":
			return "FunctionBehavior_FloatingNameLabel_CN";
		case "3077":
			return "OpaqueBehavior_Shape_CN";
		case "5133":
			return "OpaqueBehavior_NameLabel_CN";
		case "6062":
			return "OpaqueBehavior_FloatingNameLabel_CN";
		case "3081":
			return "Component_Shape_CN";
		case "5137":
			return "Component_NameLabel_CN";
		case "6063":
			return "Component_FloatingNameLabel_CN";
		case "3082":
			return "Device_Shape_CN";
		case "5138":
			return "Device_NameLabel_CN";
		case "6064":
			return "Device_FloatingNameLabel_CN";
		case "3083":
			return "ExecutionEnvironment_Shape_CN";
		case "5139":
			return "ExecutionEnvironment_NameLabel_CN";
		case "6065":
			return "ExecutionEnvironment_FloatingNameLabel_CN";
		case "3084":
			return "Node_Shape_CN";
		case "5140":
			return "Node_NameLabel_CN";
		case "6066":
			return "Node_FloatingNameLabel_CN";
		case "3085":
			return "Class_Shape_CN";
		case "5155":
			return "Class_NameLabel_CN";
		case "6067":
			return "Class_FloatingNameLabel_CN";
		case "3086":
			return "Collaboration_Shape_CN";
		case "5141":
			return "Collaboration_NameLabel_CN";
		case "6068":
			return "Collaboration_FloatingNameLabel_CN";
		case "3087":
			return "Interface_Shape_CN";
		case "5154":
			return "Interface_NameLabel_CN";
		case "6069":
			return "Interface_FloatingNameLabel_CN";
		case "3078":
			return "PrimitiveType_Shape_CN";
		case "5134":
			return "PrimitiveType_NameLabel_CN";
		case "6070":
			return "PrimitiveType_FloatingNameLabel_CN";
		case "3079":
			return "Enumeration_Shape_CN";
		case "5135":
			return "Enumeration_NameLabel_CN";
		case "6071":
			return "Enumeration_FloatingNameLabel_CN";
		case "3080":
			return "DataType_Shape_CN";
		case "5136":
			return "DataType_NameLabel_CN";
		case "6072":
			return "DataType_FloatingNameLabel_CN";
		case "3091":
			return "Actor_Shape_CN";
		case "5144":
			return "Actor_NameLabel_CN";
		case "6073":
			return "Actor_FloatingNameLabel_CN";
		case "3092":
			return "DeploymentSpecification_Shape_CN";
		case "5145":
			return "DeploymentSpecification_NameLabel_CN";
		case "6074":
			return "DeploymentSpecification_FloatingNameLabel_CN";
		case "3093":
			return "Artifact_Shape_CN";
		case "5146":
			return "Artifact_NameLabel_CN";
		case "6075":
			return "Artifact_FloatingNameLabel_CN";
		case "3094":
			return "InformationItem_Shape_CN";
		case "5147":
			return "InformationItem_NameLabel_CN";
		case "6076":
			return "InformationItem_FloatingNameLabel_CN";
		case "3095":
			return "Signal_Shape_CN";
		case "5148":
			return "Signal_NameLabel_CN";
		case "6077":
			return "Signal_FloatingNameLabel_CN";
		case "3096":
			return "UseCase_Shape_CN";
		case "5149":
			return "UseCase_NameLabel_CN";
		case "6078":
			return "UseCase_FloatingNameLabel_CN";
		case "3097":
			return "Comment_Shape_CN";
		case "5150":
			return "Comment_BodyLabel_CN";
		case "3116":
			return "DurationConstraint_Shape_CN";
		case "6040":
			return "DurationConstraint_NameLabel_CN";
		case "6041":
			return "DurationConstraint_BodyLabel_CN";
		case "3117":
			return "TimeConstraint_Shape_CN";
		case "6042":
			return "TimeConstraint_NameLabel_CN";
		case "6043":
			return "TimeConstraint_BodyLabel_CN";
		case "3118":
			return "IntervalConstraint_Shape_CN";
		case "6044":
			return "IntervalConstraint_NameLabel_CN";
		case "6045":
			return "IntervalConstraint_BodyLabel_CN";
		case "3119":
			return "InteractionConstraint_Shape_CN";
		case "6046":
			return "InteractionConstraint_NameLabel_CN";
		case "6047":
			return "InteractionConstraint_BodyLabel_CN";
		case "3120":
			return "Constraint_Shape_CN";
		case "6048":
			return "Constraint_NameLabel_CN";
		case "6049":
			return "Constraint_BodyLabel_CN";
		case "3101":
			return "Property_AttributeLabel";
		case "3102":
			return "Operation_OperationLabel";
		case "3066":
			return "EnumerationLiteral_LiteralLabel";
		case "2060":
			return "Activity_Shape";
		case "5112":
			return "Activity_NameLabel";
		case "6079":
			return "Activity_FloatingNameLabel";
		case "2061":
			return "Interaction_Shape";
		case "5113":
			return "Interaction_NameLabel";
		case "6080":
			return "Interaction_FloatingNameLabel";
		case "2062":
			return "ProtocolStateMachine_Shape";
		case "5114":
			return "ProtocolStateMachine_NameLabel";
		case "6081":
			return "ProtocolStateMachine_FloatingNameLabel";
		case "2063":
			return "StateMachine_Shape";
		case "5115":
			return "StateMachine_NameLabel";
		case "6082":
			return "StateMachine_FloatingNameLabel";
		case "2064":
			return "FunctionBehavior_Shape";
		case "5116":
			return "FunctionBehavior_NameLabel";
		case "6083":
			return "FunctionBehavior_FloatingNameLabel";
		case "2065":
			return "OpaqueBehavior_Shape";
		case "5117":
			return "OpaqueBehavior_NameLabel";
		case "6084":
			return "OpaqueBehavior_FloatingNameLabel";
		case "2069":
			return "Component_Shape";
		case "5121":
			return "Component_NameLabel";
		case "6085":
			return "Component_FloatingNameLabel";
		case "2070":
			return "Device_Shape";
		case "5122":
			return "Device_NameLabel";
		case "6086":
			return "Device_FloatingNameLabel";
		case "2071":
			return "ExecutionEnvironment_Shape";
		case "5123":
			return "ExecutionEnvironment_NameLabel";
		case "6087":
			return "ExecutionEnvironment_FloatingNameLabel";
		case "2072":
			return "Node_Shape";
		case "5124":
			return "Node_NameLabel";
		case "6088":
			return "Node_FloatingNameLabel";
		case "2073":
			return "Class_Shape";
		case "5156":
			return "Class_NameLabel";
		case "6089":
			return "Class_FloatingNameLabel";
		case "2075":
			return "Collaboration_Shape";
		case "5158":
			return "Collaboration_NameLabel";
		case "6090":
			return "Collaboration_FloatingNameLabel";
		case "2076":
			return "Interface_Shape";
		case "5159":
			return "Interface_NameLabel";
		case "6091":
			return "Interface_FloatingNameLabel";
		case "2066":
			return "PrimitiveType_Shape";
		case "5118":
			return "PrimitiveType_NameLabel";
		case "6092":
			return "PrimitiveType_FloatingNameLabel";
		case "2067":
			return "Enumeration_Shape";
		case "5119":
			return "Enumeration_NameLabel";
		case "6093":
			return "Enumeration_FloatingNameLabel";
		case "2068":
			return "DataType_Shape";
		case "5120":
			return "DataType_NameLabel";
		case "6094":
			return "DataType_FloatingNameLabel";
		case "2077":
			return "Actor_Shape";
		case "5160":
			return "Actor_NameLabel";
		case "6095":
			return "Actor_FloatingNameLabel";
		case "2078":
			return "DeploymentSpecification_Shape";
		case "5161":
			return "DeploymentSpecification_NameLabel";
		case "6096":
			return "DeploymentSpecification_FloatingNameLabel";
		case "2079":
			return "Artifact_Shape";
		case "5162":
			return "Artifact_NameLabel";
		case "6097":
			return "Artifact_FloatingNameLabel";
		case "2080":
			return "InformationItem_Shape";
		case "5163":
			return "InformationItem_NameLabel";
		case "6098":
			return "InformationItem_FloatingNameLabel";
		case "2081":
			return "Signal_Shape";
		case "5164":
			return "Signal_NameLabel";
		case "6099":
			return "Signal_FloatingNameLabel";
		case "2082":
			return "UseCase_Shape";
		case "5165":
			return "UseCase_NameLabel";
		case "6100":
			return "UseCase_FloatingNameLabel";
		case "2083":
			return "SignalEvent_Shape";
		case "5166":
			return "SignalEvent_NameLabel";
		case "6101":
			return "SignalEvent_FloatingNameLabel";
		case "2084":
			return "CallEvent_Shape";
		case "5167":
			return "CallEvent_NameLabel";
		case "6102":
			return "CallEvent_FloatingNameLabel";
		case "2085":
			return "AnyReceiveEvent_Shape";
		case "5168":
			return "AnyReceiveEvent_NameLabel";
		case "6103":
			return "AnyReceiveEvent_FloatingNameLabel";
		case "2088":
			return "ChangeEvent_Shape";
		case "5171":
			return "ChangeEvent_NameLabel";
		case "6104":
			return "ChangeEvent_FloatingNameLabel";
		case "2089":
			return "TimeEvent_Shape";
		case "5172":
			return "TimeEvent_NameLabel";
		case "6105":
			return "TimeEvent_FloatingNameLabel";
		case "2093":
			return "DurationObservation_Shape";
		case "5151":
			return "DurationObservation_NameLabel";
		case "5152":
			return "DurationObservation_StereotypeLabel";
		case "2094":
			return "TimeObservation_Shape";
		case "5142":
			return "TimeObservation_NameLabel";
		case "5143":
			return "TimeObservation_StereotypeLabel";
		case "2095":
			return "LiteralBoolean_Shape";
		case "5178":
			return "LiteralBoolean_NameLabel";
		case "6106":
			return "LiteralBoolean_FloatingNameLabel";
		case "2096":
			return "LiteralInteger_Shape";
		case "5179":
			return "LiteralInteger_NameLabel";
		case "6107":
			return "LiteralInteger_FloatingNameLabel";
		case "2097":
			return "LiteralNull_Shape";
		case "5180":
			return "LiteralNull_NameLabel";
		case "6108":
			return "LiteralNull_FloatingNameLabel";
		case "2098":
			return "LiteralString_Shape";
		case "5181":
			return "LiteralString_NameLabel";
		case "6109":
			return "LiteralString_FloatingNameLabel";
		case "2099":
			return "LiteralUnlimitedNatural_Shape";
		case "5182":
			return "LiteralUnlimitedNatural_NameLabel";
		case "6110":
			return "LiteralUnlimitedNatural_FloatingNameLabel";
		case "2100":
			return "StringExpression_PackagedElementShape";
		case "5183":
			return "StringExpression_NameLabel";
		case "6111":
			return "StringExpression_FloatingNameLabel";
		case "2101":
			return "OpaqueExpression_Shape";
		case "5184":
			return "OpaqueExpression_NameLabel";
		case "6112":
			return "OpaqueExpression_FloatingNameLabel";
		case "2102":
			return "TimeExpression_Shape";
		case "5185":
			return "TimeExpression_NameLabel";
		case "6113":
			return "TimeExpression_FloatingNameLabel";
		case "2103":
			return "Expression_Shape";
		case "5186":
			return "Expression_NameLabel";
		case "6114":
			return "Expression_FloatingNameLabel";
		case "2104":
			return "Duration_Shape";
		case "5187":
			return "Duration_NameLabel";
		case "6115":
			return "Duration_FloatingNameLabel";
		case "2105":
			return "TimeInterval_Shape";
		case "5188":
			return "TimeInterval_NameLabel";
		case "6116":
			return "TimeInterval_FloatingNameLabel";
		case "2106":
			return "DurationInterval_Shape";
		case "5189":
			return "DurationInterval_NameLabel";
		case "6117":
			return "DurationInterval_FloatingNameLabel";
		case "2107":
			return "Interval_Shape";
		case "5190":
			return "Interval_NameLabel";
		case "6118":
			return "Interval_FloatingNameLabel";
		case "2108":
			return "InstanceValue_Shape";
		case "5191":
			return "InstanceValue_NameLabel";
		case "6119":
			return "InstanceValue_FloatingNameLabel";
		case "2109":
			return "Comment_Shape";
		case "5192":
			return "Comment_BodyLabel";
		case "2110":
			return "DurationConstraint_Shape";
		case "5193":
			return "DurationConstraint_NameLabel";
		case "6035":
			return "DurationConstraint_BodyLabel";
		case "2111":
			return "TimeConstraint_Shape";
		case "5194":
			return "TimeConstraint_NameLabel";
		case "6036":
			return "TimeConstraint_BodyLabel";
		case "2112":
			return "IntervalConstraint_Shape";
		case "5195":
			return "IntervalConstraint_NameLabel";
		case "6037":
			return "IntervalConstraint_BodyLabel";
		case "2113":
			return "InteractionConstraint_Shape";
		case "5196":
			return "InteractionConstraint_NameLabel";
		case "6038":
			return "InteractionConstraint_BodyLabel";
		case "2114":
			return "Constraint_Shape";
		case "5197":
			return "Constraint_NameLabel";
		case "6039":
			return "Constraint_BodyLabel";
		case "4022":
			return "Port_BehaviorEdge";
		case "4001":
			return "Link_DescriptorEdge";
		case "4002":
			return "Comment_AnnotatedElementEdge";
		case "4003":
			return "Constraint_ConstrainedElementEdge";
		case "4004":
			return "ComponentRealization_Edge";
		case "6001":
			return "ComponentRealization_NameLabel";
		case "6015":
			return "ComponentRealization_StereotypeLabel";
		case "4005":
			return "InterfaceRealization_Edge";
		case "6002":
			return "InterfaceRealization_NameLabel";
		case "6016":
			return "InterfaceRealization_StereotypeLabel";
		case "4011":
			return "Substitution_Edge";
		case "6003":
			return "Substitution_NameLabel";
		case "6017":
			return "Substitution_StereotypeLabel";
		case "4006":
			return "Realization_Edge";
		case "6004":
			return "Realization_NameLabel";
		case "6018":
			return "Realization_StereotypeLabel";
		case "4012":
			return "Manifestation_Edge";
		case "6005":
			return "Manifestation_NameLabel";
		case "6019":
			return "Manifestation_StereotypeLabel";
		case "4007":
			return "Abstraction_Edge";
		case "6006":
			return "Abstraction_NameLabel";
		case "6020":
			return "Abstraction_StereotypeLabel";
		case "4008":
			return "Usage_Edge";
		case "6007":
			return "Usage_NameLabel";
		case "6021":
			return "Usage_StereotypeLabel";
		case "4009":
			return "Deployment_Edge";
		case "6008":
			return "Deployment_NameLabel";
		case "6022":
			return "Deployment_StereotypeLabel";
		case "4017":
			return "Dependency_RoleBindingEdge";
		case "6027":
			return "Dependency_RoleBindingNameLabel";
		case "6028":
			return "Dependency_RoleBindingStereotypeLabel";
		case "4010":
			return "Dependency_Edge";
		case "6009":
			return "Dependency_NameLabel";
		case "6023":
			return "Dependency_StereotypeLabel";
		case "4013":
			return "Connector_Edge";
		case "6025":
			return "Connector_StereotypeLabel";
		case "6050":
			return "Connector_NameLabel";
		case "6051":
			return "Connector_SourceMultiplicityLabel";
		case "6052":
			return "Connector_TargetMultiplicityLabel";
		case "4015":
			return "Generalization_Edge";
		case "6024":
			return "Generalization_StereotypeLabel";
		case "4018":
			return "TimeObservation_EventEdge";
		case "4019":
			return "DurationObservation_EventEdge";
		case "4020":
			return "Representation_Edge";
		case "6030":
			return "Representation_KeywordLabel";
		case "4021":
			return "InformationFlow_Edge";
		case "6031":
			return "InformationFlow_ConveyedLabel";
		case "6032":
			return "InformationFlow_StereotypeLabel";
		case "7033":
			return "DataType_AttributeCompartment";
		case "7034":
			return "DataType_OperationCompartment";
		case "7036":
			return "DataType_AttributeCompartment_CN";
		case "7037":
			return "DataType_OperationCompartment_CN";
		case "7048":
			return "Enumeration_LiteralCompartment";
		case "7049":
			return "Enumeration_LiteralCompartment_CN";
		case "7050":
			return "Activity_StructureCompartment_CN";
		case "7051":
			return "Interaction_StructureCompartment_CN";
		case "7052":
			return "ProtocolStateMachine_StructureCompartment_CN";
		case "7053":
			return "StateMachine_StructureCompartment_CN";
		case "7054":
			return "FunctionBehavior_StructureCompartment_CN";
		case "7055":
			return "OpaqueBehavior_StructureCompartment_CN";
		case "7056":
			return "Component_StructureCompartment_CN";
		case "7057":
			return "Device_StructureCompartment_CN";
		case "7058":
			return "ExecutionEnvironment_StructureCompartment_CN";
		case "7059":
			return "Node_StructureCompartment_CN";
		case "7060":
			return "Class_StructureCompartment_CN";
		case "7061":
			return "Collaboration_StructureCompartment_CN";
		case "7063":
			return "Activity_StructureCompartment";
		case "7064":
			return "Interaction_StructureCompartment";
		case "7065":
			return "ProtocolStateMachine_StructureCompartment";
		case "7066":
			return "StateMachine_StructureCompartment";
		case "7067":
			return "FunctionBehavior_StructureCompartment";
		case "7068":
			return "OpaqueBehavior_StructureCompartment";
		case "7069":
			return "Component_StructureCompartment";
		case "7070":
			return "Device_StructureCompartment";
		case "7071":
			return "ExecutionEnvironment_StructureCompartment";
		case "7072":
			return "Node_StructureCompartment";
		case "7073":
			return "Class_StructureCompartment";
		case "7075":
			return "Collaboration_StructureCompartment";
		case "7077":
			return "Property_StructureCompartment";
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
