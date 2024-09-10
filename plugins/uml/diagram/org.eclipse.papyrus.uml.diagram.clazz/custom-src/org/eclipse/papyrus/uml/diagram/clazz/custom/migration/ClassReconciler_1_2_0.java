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
package org.eclipse.papyrus.uml.diagram.clazz.custom.migration;

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
 * Class Diagram Reconciler from 1.1.0 to 1.2.0
 */
public class ClassReconciler_1_2_0 extends DiagramReconciler {

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
			throw new ExecutionException("Should not be called, canRedo false"); //$NON-NLS-1$
		}

		@Override
		protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			throw new ExecutionException("Should not be called, canUndo false"); //$NON-NLS-1$
		}
	}

	public static String getNewVisualID(String oldVisualID) {
		switch (oldVisualID) {
		case "1000": //$NON-NLS-1$
			return "Package_ClassDiagram"; //$NON-NLS-1$
		case "3012": //$NON-NLS-1$
			return "Property_ClassAttributeLabel"; //$NON-NLS-1$
		case "3002": //$NON-NLS-1$
			return "Property_ComponentAttributeLabel"; //$NON-NLS-1$
		case "3005": //$NON-NLS-1$
			return "Property_SignalAttributeLabel"; //$NON-NLS-1$
		case "3006": //$NON-NLS-1$
			return "Property_InterfaceAttributeLabel"; //$NON-NLS-1$
		case "3041": //$NON-NLS-1$
			return "Property_PrimitiveTypeAttributeLabel"; //$NON-NLS-1$
		case "3018": //$NON-NLS-1$
			return "Property_DataTypeAttributeLabel"; //$NON-NLS-1$
		case "3014": //$NON-NLS-1$
			return "Class_ClassNestedClassifierLabel"; //$NON-NLS-1$
		case "3004": //$NON-NLS-1$
			return "Class_ComponentNestedClassifierLabel"; //$NON-NLS-1$
		case "3008": //$NON-NLS-1$
			return "Class_InterfaceNestedClassifierLabel"; //$NON-NLS-1$
		case "3013": //$NON-NLS-1$
			return "Operation_ClassOperationLabel"; //$NON-NLS-1$
		case "3003": //$NON-NLS-1$
			return "Operation_ComponentOperationLabel"; //$NON-NLS-1$
		case "3007": //$NON-NLS-1$
			return "Operation_InterfaceOperationLabel"; //$NON-NLS-1$
		case "3042": //$NON-NLS-1$
			return "Operation_PrimitiveTypeOperationLabel"; //$NON-NLS-1$
		case "3019": //$NON-NLS-1$
			return "Operation_DataTypeOperationLabel"; //$NON-NLS-1$
		case "3034": //$NON-NLS-1$
			return "ConnectableElementTemplateParameter_TemplateParameterLabel"; //$NON-NLS-1$
		case "3035": //$NON-NLS-1$
			return "OperationTemplateParameter_TemplateParameterLabel"; //$NON-NLS-1$
		case "3031": //$NON-NLS-1$
			return "ClassifierTemplateParameter_TemplateParameterLabel"; //$NON-NLS-1$
		case "3016": //$NON-NLS-1$
			return "TemplateParameter_TemplateParameterLabel"; //$NON-NLS-1$
		case "3017": //$NON-NLS-1$
			return "EnumerationLiteral_LiteralLabel"; //$NON-NLS-1$
		case "3011": //$NON-NLS-1$
			return "Reception_ReceptionLabel"; //$NON-NLS-1$
		case "3039": //$NON-NLS-1$
			return "Reception_InterfaceReceptionLabel"; //$NON-NLS-1$
		case "3030": //$NON-NLS-1$
			return "Slot_SlotLabel"; //$NON-NLS-1$
		case "3015": //$NON-NLS-1$
			return "RedefinableTemplateSignature_Shape"; //$NON-NLS-1$
		case "3033": //$NON-NLS-1$
			return "TemplateSignature_Shape"; //$NON-NLS-1$
		case "3020": //$NON-NLS-1$
			return "InstanceSpecification_Shape_CN"; //$NON-NLS-1$
		case "5040": //$NON-NLS-1$
			return "InstanceSpecification_NameLabel_CN"; //$NON-NLS-1$
		case "8509": //$NON-NLS-1$
			return "InstanceSpecification_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3021": //$NON-NLS-1$
			return "Component_Shape_CN"; //$NON-NLS-1$
		case "5043": //$NON-NLS-1$
			return "Component_NameLabel_CN"; //$NON-NLS-1$
		case "8513": //$NON-NLS-1$
			return "Component_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3022": //$NON-NLS-1$
			return "Signal_Shape_CN"; //$NON-NLS-1$
		case "5046": //$NON-NLS-1$
			return "Signal_NameLabel_CN"; //$NON-NLS-1$
		case "8514": //$NON-NLS-1$
			return "Signal_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3023": //$NON-NLS-1$
			return "Interface_Shape_CN"; //$NON-NLS-1$
		case "5049": //$NON-NLS-1$
			return "Interface_NameLabel_CN"; //$NON-NLS-1$
		case "8515": //$NON-NLS-1$
			return "Interface_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3024": //$NON-NLS-1$
			return "Model_Shape_CN"; //$NON-NLS-1$
		case "5052": //$NON-NLS-1$
			return "Model_NameLabel_CN"; //$NON-NLS-1$
		case "3025": //$NON-NLS-1$
			return "Enumeration_Shape_CN"; //$NON-NLS-1$
		case "5055": //$NON-NLS-1$
			return "Enumeration_NameLabel_CN"; //$NON-NLS-1$
		case "8516": //$NON-NLS-1$
			return "Enumeration_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3009": //$NON-NLS-1$
			return "Package_Shape_CN"; //$NON-NLS-1$
		case "5017": //$NON-NLS-1$
			return "Package_NameLabel_CN"; //$NON-NLS-1$
		case "3040": //$NON-NLS-1$
			return "InformationItem_Shape_CN"; //$NON-NLS-1$
		case "5162": //$NON-NLS-1$
			return "InformationItem_NameLabel_CN"; //$NON-NLS-1$
		case "8517": //$NON-NLS-1$
			return "InformationItem_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3010": //$NON-NLS-1$
			return "Class_Shape_CN"; //$NON-NLS-1$
		case "5014": //$NON-NLS-1$
			return "Class_NameLabel_CN"; //$NON-NLS-1$
		case "8518": //$NON-NLS-1$
			return "Class_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3026": //$NON-NLS-1$
			return "PrimitiveType_Shape_CN"; //$NON-NLS-1$
		case "5058": //$NON-NLS-1$
			return "PrimitiveType_NameLabel_CN"; //$NON-NLS-1$
		case "8519": //$NON-NLS-1$
			return "PrimitiveType_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3027": //$NON-NLS-1$
			return "DataType_Shape_CN"; //$NON-NLS-1$
		case "5061": //$NON-NLS-1$
			return "DataType_NameLabel_CN"; //$NON-NLS-1$
		case "8520": //$NON-NLS-1$
			return "DataType_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3028": //$NON-NLS-1$
			return "Comment_Shape_CN"; //$NON-NLS-1$
		case "5063": //$NON-NLS-1$
			return "Comment_BodyLabel_CN"; //$NON-NLS-1$
		case "3029": //$NON-NLS-1$
			return "Constraint_PackagedElementShape_CN"; //$NON-NLS-1$
		case "5064": //$NON-NLS-1$
			return "Constraint_NameLabel_CN"; //$NON-NLS-1$
		case "5160": //$NON-NLS-1$
			return "Constraint_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3036": //$NON-NLS-1$
			return "Interface_ClassNestedClassifierLabel"; //$NON-NLS-1$
		case "3037": //$NON-NLS-1$
			return "Interface_ComponentNestedClassifierLabel"; //$NON-NLS-1$
		case "3038": //$NON-NLS-1$
			return "Interface_InterfaceNestedClassifierLabel"; //$NON-NLS-1$
		case "3052": //$NON-NLS-1$
			return "Enumeration_ClassNestedClassifierLabel"; //$NON-NLS-1$
		case "3053": //$NON-NLS-1$
			return "Enumeration_ComponentNestedClassifierLabel"; //$NON-NLS-1$
		case "3054": //$NON-NLS-1$
			return "Enumeration_InterfaceNestedClassifierLabel"; //$NON-NLS-1$
		case "3047": //$NON-NLS-1$
			return "PrimitiveType_ClassNestedClassifierLabel"; //$NON-NLS-1$
		case "3046": //$NON-NLS-1$
			return "PrimitiveType_ComponentNestedClassifierLabel"; //$NON-NLS-1$
		case "3048": //$NON-NLS-1$
			return "PrimitiveType_InterfaceNestedClassifierLabel"; //$NON-NLS-1$
		case "3044": //$NON-NLS-1$
			return "DataType_ClassNestedClassifierLabel"; //$NON-NLS-1$
		case "3045": //$NON-NLS-1$
			return "DataType_ComponentNestedClassifierLabel"; //$NON-NLS-1$
		case "3043": //$NON-NLS-1$
			return "DataType_InterfaceNestedClassifierLabel"; //$NON-NLS-1$
		case "3050": //$NON-NLS-1$
			return "Signal_ClassNestedClassifierLabel"; //$NON-NLS-1$
		case "3051": //$NON-NLS-1$
			return "Signal_ComponentNestedClassifierLabel"; //$NON-NLS-1$
		case "3049": //$NON-NLS-1$
			return "Signal_InterfaceNestedClassifierLabel"; //$NON-NLS-1$
		case "3055": //$NON-NLS-1$
			return "Component_ClassNestedClassifierLabel"; //$NON-NLS-1$
		case "3056": //$NON-NLS-1$
			return "Component_InterfaceNestedClassifierLabel"; //$NON-NLS-1$
		case "3057": //$NON-NLS-1$
			return "Component_ComponentNestedClassifierLabel"; //$NON-NLS-1$
		case "2014": //$NON-NLS-1$
			return "Dependency_Shape"; //$NON-NLS-1$
		case "1": //$NON-NLS-1$
			return "Dependency_MultiNameLabel"; //$NON-NLS-1$
		case "8522": //$NON-NLS-1$
			return "Dependency_FloatingNameLabel"; //$NON-NLS-1$
		case "2013": //$NON-NLS-1$
			return "AssociationClass_Shape"; //$NON-NLS-1$
		case "5066": //$NON-NLS-1$
			return "AssociationClass_NameLabel"; //$NON-NLS-1$
		case "8504": //$NON-NLS-1$
			return "AssociationClass_FloatingNameLabel"; //$NON-NLS-1$
		case "2015": //$NON-NLS-1$
			return "Association_Shape"; //$NON-NLS-1$
		case "8521": //$NON-NLS-1$
			return "Association_FloatingNameLabel"; //$NON-NLS-1$
		case "2001": //$NON-NLS-1$
			return "InstanceSpecification_Shape"; //$NON-NLS-1$
		case "5002": //$NON-NLS-1$
			return "InstanceSpecification_NameLabel"; //$NON-NLS-1$
		case "8505": //$NON-NLS-1$
			return "InstanceSpecification_FloatingNameLabel"; //$NON-NLS-1$
		case "2002": //$NON-NLS-1$
			return "Component_Shape"; //$NON-NLS-1$
		case "5005": //$NON-NLS-1$
			return "Component_NameLabel"; //$NON-NLS-1$
		case "8503": //$NON-NLS-1$
			return "Component_FloatingNameLabel"; //$NON-NLS-1$
		case "2003": //$NON-NLS-1$
			return "Signal_Shape"; //$NON-NLS-1$
		case "5008": //$NON-NLS-1$
			return "Signal_NameLabel"; //$NON-NLS-1$
		case "8506": //$NON-NLS-1$
			return "Signal_FloatingNameLabel"; //$NON-NLS-1$
		case "2004": //$NON-NLS-1$
			return "Interface_Shape"; //$NON-NLS-1$
		case "5011": //$NON-NLS-1$
			return "Interface_NameLabel"; //$NON-NLS-1$
		case "8507": //$NON-NLS-1$
			return "Interface_FloatingNameLabel"; //$NON-NLS-1$
		case "2005": //$NON-NLS-1$
			return "Model_Shape"; //$NON-NLS-1$
		case "5020": //$NON-NLS-1$
			return "Model_NameLabel"; //$NON-NLS-1$
		case "2006": //$NON-NLS-1$
			return "Enumeration_Shape"; //$NON-NLS-1$
		case "5023": //$NON-NLS-1$
			return "Enumeration_NameLabel"; //$NON-NLS-1$
		case "8508": //$NON-NLS-1$
			return "Enumeration_FloatingNameLabel"; //$NON-NLS-1$
		case "2007": //$NON-NLS-1$
			return "Package_Shape"; //$NON-NLS-1$
		case "5026": //$NON-NLS-1$
			return "Package_NameLabel"; //$NON-NLS-1$
		case "2099": //$NON-NLS-1$
			return "InformationItem_Shape"; //$NON-NLS-1$
		case "5161": //$NON-NLS-1$
			return "InformationItem_NameLabel"; //$NON-NLS-1$
		case "8512": //$NON-NLS-1$
			return "InformationItem_FloatingNameLabel"; //$NON-NLS-1$
		case "2008": //$NON-NLS-1$
			return "Class_Shape"; //$NON-NLS-1$
		case "5029": //$NON-NLS-1$
			return "Class_NameLabel"; //$NON-NLS-1$
		case "8510": //$NON-NLS-1$
			return "Class_FloatingNameLabel"; //$NON-NLS-1$
		case "2009": //$NON-NLS-1$
			return "PrimitiveType_Shape"; //$NON-NLS-1$
		case "5032": //$NON-NLS-1$
			return "PrimitiveType_NameLabel"; //$NON-NLS-1$
		case "8511": //$NON-NLS-1$
			return "PrimitiveType_FloatingNameLabel"; //$NON-NLS-1$
		case "2010": //$NON-NLS-1$
			return "DataType_Shape"; //$NON-NLS-1$
		case "5035": //$NON-NLS-1$
			return "DataType_NameLabel"; //$NON-NLS-1$
		case "8502": //$NON-NLS-1$
			return "DataType_FloatingNameLabel"; //$NON-NLS-1$
		case "2011": //$NON-NLS-1$
			return "Constraint_PackagedElementShape"; //$NON-NLS-1$
		case "5037": //$NON-NLS-1$
			return "Constraint_NameLabel"; //$NON-NLS-1$
		case "5159": //$NON-NLS-1$
			return "Constraint_BodyLabel"; //$NON-NLS-1$
		case "2012": //$NON-NLS-1$
			return "Comment_Shape"; //$NON-NLS-1$
		case "5038": //$NON-NLS-1$
			return "Comment_BodyLabel"; //$NON-NLS-1$
		case "2016": //$NON-NLS-1$
			return "Diagram_ShortcutShape"; //$NON-NLS-1$
		case "0": //$NON-NLS-1$
			return "Diagram_NameLabel"; //$NON-NLS-1$
		case "2095": //$NON-NLS-1$
			return "DurationObservation_Shape"; //$NON-NLS-1$
		case "5155": //$NON-NLS-1$
			return "DurationObservation_FloatingNameLabel"; //$NON-NLS-1$
		case "5156": //$NON-NLS-1$
			return "DurationObservation_StereotypeLabel"; //$NON-NLS-1$
		case "2096": //$NON-NLS-1$
			return "TimeObservation_Shape"; //$NON-NLS-1$
		case "5153": //$NON-NLS-1$
			return "TimeObservation_FloatingNameLabel"; //$NON-NLS-1$
		case "5154": //$NON-NLS-1$
			return "TimeObservation_StereotypeLabel"; //$NON-NLS-1$
		case "2097": //$NON-NLS-1$
			return "NamedElement_DefaultShape"; //$NON-NLS-1$
		case "5157": //$NON-NLS-1$
			return "NamedElement_NameLabel"; //$NON-NLS-1$
		case "4016": //$NON-NLS-1$
			return "AssociationClass_TetherEdge"; //$NON-NLS-1$
		case "4017": //$NON-NLS-1$
			return "AssociationClass_Edge"; //$NON-NLS-1$
		case "6031": //$NON-NLS-1$
			return "AssociationClass_SourceRoleLabel"; //$NON-NLS-1$
		case "6032": //$NON-NLS-1$
			return "AssociationClass_TargetRoleLabel"; //$NON-NLS-1$
		case "4001": //$NON-NLS-1$
			return "Association_Edge"; //$NON-NLS-1$
		case "6001": //$NON-NLS-1$
			return "Association_StereotypeLabel"; //$NON-NLS-1$
		case "6002": //$NON-NLS-1$
			return "Association_NameLabel"; //$NON-NLS-1$
		case "6003": //$NON-NLS-1$
			return "Association_TargetRoleLabel"; //$NON-NLS-1$
		case "6005": //$NON-NLS-1$
			return "Association_SourceRoleLabel"; //$NON-NLS-1$
		case "6033": //$NON-NLS-1$
			return "Association_SourceMultiplicityLabel"; //$NON-NLS-1$
		case "6034": //$NON-NLS-1$
			return "Association_TargetMultiplicityLabel"; //$NON-NLS-1$
		case "4019": //$NON-NLS-1$
			return "Association_BranchEdge"; //$NON-NLS-1$
		case "6024": //$NON-NLS-1$
			return "Association_BranchRoleLabel"; //$NON-NLS-1$
		case "6035": //$NON-NLS-1$
			return "Association_BranchMultiplicityLabel"; //$NON-NLS-1$
		case "4002": //$NON-NLS-1$
			return "Generalization_Edge"; //$NON-NLS-1$
		case "6007": //$NON-NLS-1$
			return "Generalization_StereotypeLabel"; //$NON-NLS-1$
		case "4003": //$NON-NLS-1$
			return "InterfaceRealization_Edge"; //$NON-NLS-1$
		case "6008": //$NON-NLS-1$
			return "InterfaceRealization_StereotypeLabel"; //$NON-NLS-1$
		case "6009": //$NON-NLS-1$
			return "InterfaceRealization_NameLabel"; //$NON-NLS-1$
		case "4004": //$NON-NLS-1$
			return "Substitution_Edge"; //$NON-NLS-1$
		case "6010": //$NON-NLS-1$
			return "Substitution_StereotypeLabel"; //$NON-NLS-1$
		case "6011": //$NON-NLS-1$
			return "Substitution_NameLabel"; //$NON-NLS-1$
		case "4005": //$NON-NLS-1$
			return "Realization_Edge"; //$NON-NLS-1$
		case "6012": //$NON-NLS-1$
			return "Realization_StereotypeLabel"; //$NON-NLS-1$
		case "6013": //$NON-NLS-1$
			return "Realization_NameLabel"; //$NON-NLS-1$
		case "4006": //$NON-NLS-1$
			return "Abstraction_Edge"; //$NON-NLS-1$
		case "6014": //$NON-NLS-1$
			return "Abstraction_NameLabel"; //$NON-NLS-1$
		case "6015": //$NON-NLS-1$
			return "Abstraction_StereotypeLabel"; //$NON-NLS-1$
		case "4007": //$NON-NLS-1$
			return "Usage_Edge"; //$NON-NLS-1$
		case "6016": //$NON-NLS-1$
			return "Usage_NameLabel"; //$NON-NLS-1$
		case "6017": //$NON-NLS-1$
			return "Usage_StereotypeLabel"; //$NON-NLS-1$
		case "4008": //$NON-NLS-1$
			return "Dependency_Edge"; //$NON-NLS-1$
		case "6026": //$NON-NLS-1$
			return "Dependency_NameLabel"; //$NON-NLS-1$
		case "6027": //$NON-NLS-1$
			return "Dependency_StereotypeLabel"; //$NON-NLS-1$
		case "4018": //$NON-NLS-1$
			return "Dependency_BranchEdge"; //$NON-NLS-1$
		case "4009": //$NON-NLS-1$
			return "ElementImport_Edge"; //$NON-NLS-1$
		case "6020": //$NON-NLS-1$
			return "ElementImport_AliasLabel"; //$NON-NLS-1$
		case "6021": //$NON-NLS-1$
			return "ElementImport_StereotypeLabel"; //$NON-NLS-1$
		case "4010": //$NON-NLS-1$
			return "PackageImport_Edge"; //$NON-NLS-1$
		case "6022": //$NON-NLS-1$
			return "PackageImport_StereotypeLabel"; //$NON-NLS-1$
		case "4011": //$NON-NLS-1$
			return "PackageMerge_Edge"; //$NON-NLS-1$
		case "6030": //$NON-NLS-1$
			return "PackageMerge_StereotypeLabel"; //$NON-NLS-1$
		case "4012": //$NON-NLS-1$
			return "ProfileApplication_Edge"; //$NON-NLS-1$
		case "4013": //$NON-NLS-1$
			return "Comment_AnnotatedElementEdge"; //$NON-NLS-1$
		case "4014": //$NON-NLS-1$
			return "Constraint_ConstrainedElementEdge"; //$NON-NLS-1$
		case "4015": //$NON-NLS-1$
			return "TemplateBinding_Edge"; //$NON-NLS-1$
		case "6023": //$NON-NLS-1$
			return "TemplateBinding_SubstitutionLabel"; //$NON-NLS-1$
		case "6036": //$NON-NLS-1$
			return "TemplateBinding_StereotypeLabel"; //$NON-NLS-1$
		case "4020": //$NON-NLS-1$
			return "GeneralizationSet_Edge"; //$NON-NLS-1$
		case "5067": //$NON-NLS-1$
			return "GeneralizationSet_ConstraintLabel"; //$NON-NLS-1$
		case "6037": //$NON-NLS-1$
			return "GeneralizationSet_StereotypeLabel"; //$NON-NLS-1$
		case "4021": //$NON-NLS-1$
			return "InstanceSpecification_Edge"; //$NON-NLS-1$
		case "6039": //$NON-NLS-1$
			return "InstanceSpecification_SourceRoleLabel"; //$NON-NLS-1$
		case "6038": //$NON-NLS-1$
			return "InstanceSpecification_TargetRoleLabel"; //$NON-NLS-1$
		case "4023": //$NON-NLS-1$
			return "Element_ContainmentEdge"; //$NON-NLS-1$
		case "4024": //$NON-NLS-1$
			return "TimeObservation_EventEdge"; //$NON-NLS-1$
		case "4025": //$NON-NLS-1$
			return "DurationObservation_EventEdge"; //$NON-NLS-1$
		case "4026": //$NON-NLS-1$
			return "InformationFlow_Edge"; //$NON-NLS-1$
		case "6040": //$NON-NLS-1$
			return "InformationFlow_ConveyedLabel"; //$NON-NLS-1$
		case "6041": //$NON-NLS-1$
			return "InformationFlow_StereotypeLabel"; //$NON-NLS-1$
		case "8500": //$NON-NLS-1$
			return "Constraint_ContextEdge"; //$NON-NLS-1$
		case "8501": //$NON-NLS-1$
			return "Constraint_KeywordLabel"; //$NON-NLS-1$
		case "7011": //$NON-NLS-1$
			return "Class_AttributeCompartment_CN"; //$NON-NLS-1$
		case "7012": //$NON-NLS-1$
			return "Class_OperationCompartment_CN"; //$NON-NLS-1$
		case "7013": //$NON-NLS-1$
			return "Class_NestedClassifierCompartment_CN"; //$NON-NLS-1$
		case "7023": //$NON-NLS-1$
			return "Component_AttributeCompartment_CN"; //$NON-NLS-1$
		case "7024": //$NON-NLS-1$
			return "Component_OperationCompartment_CN"; //$NON-NLS-1$
		case "7025": //$NON-NLS-1$
			return "Component_NestedClassifierCompartment_CN"; //$NON-NLS-1$
		case "7026": //$NON-NLS-1$
			return "Signal_AttributeCompartment_CN"; //$NON-NLS-1$
		case "7027": //$NON-NLS-1$
			return "Interface_AttributeCompartment_CN"; //$NON-NLS-1$
		case "7028": //$NON-NLS-1$
			return "Interface_OperationCompartment_CN"; //$NON-NLS-1$
		case "7029": //$NON-NLS-1$
			return "Interface_NestedClassifierCompartment_CN"; //$NON-NLS-1$
		case "7041": //$NON-NLS-1$
			return "PrimitiveType_AttributeCompartment_CN"; //$NON-NLS-1$
		case "7042": //$NON-NLS-1$
			return "PrimitiveType_OperationCompartment_CN"; //$NON-NLS-1$
		case "7032": //$NON-NLS-1$
			return "DataType_AttributeCompartment_CN"; //$NON-NLS-1$
		case "7033": //$NON-NLS-1$
			return "DataType_OperationCompartment_CN"; //$NON-NLS-1$
		case "7030": //$NON-NLS-1$
			return "Model_PackagedElementCompartment_CN"; //$NON-NLS-1$
		case "7010": //$NON-NLS-1$
			return "Package_PackagedElementCompartment_CN"; //$NON-NLS-1$
		case "7031": //$NON-NLS-1$
			return "Enumeration_LiteralCompartment_CN"; //$NON-NLS-1$
		case "7035": //$NON-NLS-1$
			return "InstanceSpecification_SlotCompartment_CN"; //$NON-NLS-1$
		case "7017": //$NON-NLS-1$
			return "Class_AttributeCompartment"; //$NON-NLS-1$
		case "7018": //$NON-NLS-1$
			return "Class_OperationCompartment"; //$NON-NLS-1$
		case "7019": //$NON-NLS-1$
			return "Class_NestedClassifierCompartment"; //$NON-NLS-1$
		case "7002": //$NON-NLS-1$
			return "Component_AttributeCompartment"; //$NON-NLS-1$
		case "7003": //$NON-NLS-1$
			return "Component_OperationCompartment"; //$NON-NLS-1$
		case "7004": //$NON-NLS-1$
			return "Component_NestedClassifierCompartment"; //$NON-NLS-1$
		case "7006": //$NON-NLS-1$
			return "Interface_AttributeCompartment"; //$NON-NLS-1$
		case "7007": //$NON-NLS-1$
			return "Interface_OperationCompartment"; //$NON-NLS-1$
		case "7008": //$NON-NLS-1$
			return "Interface_NestedClassifierCompartment"; //$NON-NLS-1$
		case "7039": //$NON-NLS-1$
			return "PrimitiveType_AttributeCompartment"; //$NON-NLS-1$
		case "7040": //$NON-NLS-1$
			return "PrimitiveType_OperationCompartment"; //$NON-NLS-1$
		case "7020": //$NON-NLS-1$
			return "DataType_AttributeCompartment"; //$NON-NLS-1$
		case "7021": //$NON-NLS-1$
			return "DataType_OperationCompartment"; //$NON-NLS-1$
		case "7034": //$NON-NLS-1$
			return "AssociationClass_AttributeCompartment"; //$NON-NLS-1$
		case "7001": //$NON-NLS-1$
			return "InstanceSpecification_SlotCompartment"; //$NON-NLS-1$
		case "7005": //$NON-NLS-1$
			return "Signal_AttributeCompartment"; //$NON-NLS-1$
		case "7009": //$NON-NLS-1$
			return "Model_PackagedElementCompartment"; //$NON-NLS-1$
		case "7016": //$NON-NLS-1$
			return "Package_PackagedElementCompartment"; //$NON-NLS-1$
		case "7015": //$NON-NLS-1$
			return "Enumeration_LiteralCompartment"; //$NON-NLS-1$
		case "7036": //$NON-NLS-1$
			return "AssociationClass_OperationCompartment"; //$NON-NLS-1$
		case "7037": //$NON-NLS-1$
			return "AssociationClass_NestedClassifierCompartment"; //$NON-NLS-1$
		case "7014": //$NON-NLS-1$
			return "RedefinableTemplateSignature_TemplateParameterCompartment"; //$NON-NLS-1$
		case "7038": //$NON-NLS-1$
			return "TemplateSignature_TemplateParameterCompartment"; //$NON-NLS-1$
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
