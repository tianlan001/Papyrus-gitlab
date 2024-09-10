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
package org.eclipse.papyrus.uml.diagram.profile.custom.migration;

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
 * Profile Diagram Reconciler from 1.1.0 to 1.2.0
 */
public class ProfileReconciler_1_2_0 extends DiagramReconciler {

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
			return "Profile_ProfileDiagram";
		case "3026":
			return "PrimitiveType_Shape_CN";
		case "5058":
			return "PrimitiveType_NameLabel_CN";
		case "3019":
			return "Operation_DataTypeOperationLabel";
		case "1037":
			return "EnumerationLiteral_LiteralLabel";
		case "3018":
			return "Property_DataTypeAttributeLabel";
		case "3002":
			return "Property_ClassAttributeLabel";
		case "3020":
			return "Operation_ClassOperationLabel";
		case "1023":
			return "Stereotype_Shape_CN";
		case "1046":
			return "Stereotype_NameLabel_CN";
		case "3010":
			return "Class_Shape_CN";
		case "5014":
			return "Class_NameLabel_CN";
		case "3028":
			return "Class_MetaclassShape_CN";
		case "5062":
			return "Class_MetaclassNameLabel_CN";
		case "1007":
			return "Comment_Shape_CN";
		case "1008":
			return "Comment_BodyLabel_CN";
		case "1027":
			return "Model_Shape_CN";
		case "1056":
			return "Model_NameLabel_CN";
		case "1024":
			return "Profile_Shape_CN";
		case "1050":
			return "Profile_NameLabel_CN";
		case "1012":
			return "Package_Shape_CN";
		case "1010":
			return "Package_NameLabel_CN";
		case "1028":
			return "Constraint_PackagedElementShape_CN";
		case "1059":
			return "Constraint_NameLabel_CN";
		case "5064":
			return "Constraint_BodyLabel_CN";
		case "3025":
			return "Enumeration_Shape_CN";
		case "5055":
			return "Enumeration_NameLabel_CN";
		case "3027":
			return "DataType_Shape_CN";
		case "5061":
			return "DataType_NameLabel_CN";
		case "2014":
			return "Dependency_Shape";
		case "1":
			return "Dependency_MultiNameLabel";
		case "2015":
			return "Association_Shape";
		case "1026":
			return "Stereotype_Shape";
		case "1034":
			return "Stereotype_NameLabel";
		case "2008":
			return "Class_Shape";
		case "5029":
			return "Class_NameLabel";
		case "1031":
			return "Class_MetaclassShape";
		case "1084":
			return "Class_MetaclassNameLabel";
		case "1002":
			return "Comment_Shape";
		case "3":
			return "Comment_BodyLabel";
		case "1014":
			return "Constraint_PackagedElementShape";
		case "1015":
			return "Constraint_NameLabel";
		case "5063":
			return "Constraint_BodyLabel";
		case "2005":
			return "Model_Shape";
		case "5020":
			return "Model_NameLabel";
		case "1030":
			return "Profile_Shape";
		case "1047":
			return "Profile_NameLabel";
		case "2007":
			return "Package_Shape";
		case "5026":
			return "Package_NameLabel";
		case "2006":
			return "Enumeration_Shape";
		case "5023":
			return "Enumeration_NameLabel";
		case "2009":
			return "PrimitiveType_Shape";
		case "5032":
			return "PrimitiveType_NameLabel";
		case "2010":
			return "DataType_Shape";
		case "5035":
			return "DataType_NameLabel";
		case "2016":
			return "Diagram_ShortcutShape";
		case "2":
			return "Diagram_NameLabel";
		case "1013":
			return "Extension_Edge";
		case "4001":
			return "Association_Edge";
		case "6001":
			return "Association_StereotypeLabel";
		case "6002":
			return "Association_NameLabel";
		case "6003":
			return "Association_TargetRoleLabel";
		case "6005":
			return "Association_SourceRoleLabel";
		case "6033":
			return "Association_SourceMultiplicityLabel";
		case "6034":
			return "Association_TargetMultiplicityLabel";
		case "1045":
			return "ProfileApplication_Edge";
		case "4019":
			return "Association_BranchEdge";
		case "6024":
			return "Association_BranchRoleLabel";
		case "6035":
			return "Association_BranchMultiplicityLabel";
		case "4002":
			return "Generalization_Edge";
		case "6007":
			return "Generalization_StereotypeLabel";
		case "4008":
			return "Dependency_Edge";
		case "6026":
			return "Dependency_NameLabel";
		case "6027":
			return "Dependency_StereotypeLabel";
		case "4018":
			return "Dependency_BranchEdge";
		case "1064":
			return "ElementImport_Edge";
		case "6020":
			return "ElementImport_AliasLabel";
		case "6021":
			return "ElementImport_StereotypeLabel";
		case "1065":
			return "PackageImport_Edge";
		case "6022":
			return "PackageImport_StereotypeLabel";
		case "1022":
			return "Comment_AnnotatedElementEdge";
		case "4014":
			return "Constraint_ConstrainedElementEdge";
		case "8500":
			return "Constraint_ContextEdge";
		case "8501":
			return "Constraint_KeywordLabel";
		case "1005":
			return "Package_PackagedElementCompartment";
		case "11":
			return "Package_PackagedElementCompartment_CN";
		case "1042":
			return "Profile_PackagedElementCompartment";
		case "1051":
			return "Profile_PackagedElementCompartment_CN";
		case "1071":
			return "Stereotype_AttributeCompartment";
		case "1052":
			return "Stereotype_AttributeCompartment_CN";
		case "1019":
			return "Stereotype_OperationCompartment";
		case "1053":
			return "Stereotype_OperationCompartment_CN";
		case "1057":
			return "Model_PackagedElementCompartment";
		case "1058":
			return "Model_PackagedElementCompartment_CN";
		case "1062":
			return "Enumeration_LiteralCompartment_CN";
		case "1063":
			return "Enumeration_LiteralCompartment";
		case "1067":
			return "DataType_AttributeCompartment";
		case "1069":
			return "DataType_AttributeCompartment_CN";
		case "1068":
			return "DataType_OperationCompartment";
		case "1070":
			return "DataType_OperationCompartment_CN";
		case "7017":
			return "Class_AttributeCompartment";
		case "7011":
			return "Class_AttributeCompartment_CN";
		case "7018":
			return "Class_OperationCompartment";
		case "7012":
			return "Class_OperationCompartment_CN";
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
