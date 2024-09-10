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
package org.eclipse.papyrus.uml.diagram.usecase.custom.migration;

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
 * Use Case Diagram Reconciler from 1.1.0 to 1.2.0
 */
public class UseCaseReconciler_1_2_0 extends DiagramReconciler {

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
			return "Package_UseCaseDiagram";
		case "3007":
			return "ExtensionPoint_ExtensionPointLabel";
		case "3008":
			return "ExtensionPoint_ClassifierExtensionPointLabel";
		case "3009":
			return "UseCase_Shape_CCN";
		case "5018":
			return "UseCase_NameLabel_CCN";
		case "6045":
			return "UseCase_FloatingNameLabel_CCN";
		case "3016":
			return "Component_Shape_CCN";
		case "5030":
			return "Component_NameLabel_CCN";
		case "3015":
			return "Comment_Shape_CN";
		case "5028":
			return "Comment_BodyLabel_CN";
		case "3017":
			return "Constraint_Shape_CCN";
		case "5029":
			return "Constraint_NameLabel_CCN";
		case "6043":
			return "Constraint_BodyLabel_CCN";
		case "3018":
			return "Actor_Shape_CCN";
		case "5031":
			return "Actor_NameLabel_CCN";
		case "6027":
			return "Actor_StereotypeLabel_CCN";
		case "6041":
			return "Actor_QualifiedNameLabel_CCN";
		case "6050":
			return "Actor_FloatingNameLabel_CCN";
		case "3010":
			return "Constraint_Shape_CN";
		case "5020":
			return "Constraint_NameLabel_CN";
		case "6044":
			return "Constraint_BodyLabel_CN";
		case "3011":
			return "Actor_Shape_CN";
		case "5021":
			return "Actor_NameLabel_CN";
		case "6028":
			return "Actor_StereotypeLabel_CN";
		case "6040":
			return "Actor_QualifiedNameLabel_CN";
		case "6049":
			return "Actor_FloatingNameLabel_CN";
		case "3012":
			return "UseCase_Shape_CN";
		case "5022":
			return "UseCase_NameLabel_CN";
		case "6046":
			return "UseCase_FloatingNameLabel_CN";
		case "3013":
			return "Component_Shape_CN";
		case "5023":
			return "Component_NameLabel_CN";
		case "6051":
			return "Component_FloatingNameLabel";
		case "3014":
			return "Package_Shape_CN";
		case "5024":
			return "Package_NameLabel_CN";
		case "2011":
			return "Actor_Shape";
		case "5014":
			return "Actor_NameLabel";
		case "6029":
			return "Actor_StereotypeLabel";
		case "6037":
			return "Actor_QualifiedNameLabel";
		case "6048":
			return "Actor_FloatingNameLabel";
		case "2012":
			return "Actor_ClassifierShape";
		case "5015":
			return "Actor_ClassifierNameLabel";
		case "2013":
			return "UseCase_Shape";
		case "5016":
			return "UseCase_NameLabel";
		case "6038":
			return "UseCase_FloatingNameLabel";
		case "2014":
			return "UseCase_ClassifierShape";
		case "5017":
			return "UseCase_ClassifierNameLabel";
		case "2015":
			return "Classifier_SubjectShape";
		case "5019":
			return "Classifier_NameLabel";
		case "6047":
			return "Classifier_FloatingNameLabel";
		case "2016":
			return "Package_Shape";
		case "5025":
			return "Package_NameLabel";
		case "2017":
			return "Constraint_Shape";
		case "5026":
			return "Constraint_NameLabel";
		case "6042":
			return "Constraint_BodyLabel";
		case "2018":
			return "Comment_Shape";
		case "5027":
			return "Comment_BodyLabel";
		case "2022":
			return "NamedElement_DefaultShape";
		case "6039":
			return "NamedElement_NameLabel";
		case "2019":
			return "Diagram_ShortcutShape";
		case "5032":
			return "Diagram_NameLabel";
		case "4008":
			return "Include_Edge";
		case "6006":
			return "Include_KeywordLabel";
		case "6030":
			return "Include_StereotypeLabel";
		case "4009":
			return "Extend_Edge";
		case "6007":
			return "Extend_KeywordLabel";
		case "6031":
			return "Extend_StereotypeLabel";
		case "4010":
			return "Generalization_Edge";
		case "6032":
			return "Generalization_StereotypeLabel";
		case "4011":
			return "Association_Edge";
		case "6008":
			return "Association_NameLabel";
		case "6033":
			return "Association_StereotypeLabel";
		case "4012":
			return "Constraint_ConstrainedElementEdge";
		case "4013":
			return "Dependency_Edge";
		case "6010":
			return "Dependency_NameLabel";
		case "6034":
			return "Dependency_StereotypeLabel";
		case "4014":
			return "Comment_AnnotatedElementEdge";
		case "4015":
			return "Abstraction_Edge";
		case "6011":
			return "Abstraction_NameLabel";
		case "6014":
			return "Abstraction_StereotypeLabel";
		case "4016":
			return "Usage_Edge";
		case "6012":
			return "Usage_NameLabel";
		case "6013":
			return "Usage_StereotypeLabel";
		case "4017":
			return "Realization_Edge";
		case "6015":
			return "Realization_NameLabel";
		case "6035":
			return "Realization_StereotypeLabel";
		case "4018":
			return "PackageMerge_Edge";
		case "0":
			return "PackageMerge_StereotypeLabel";
		case "4019":
			return "PackageImport_Edge";
		case "6036":
			return "PackageImport_StereotypeLabel";
		case "7009":
			return "UseCase_ExtensionPointCompartment";
		case "7010":
			return "UseCase_ClassifierExtensionPointCompartment";
		case "7011":
			return "Classifier_UseCaseCompartment";
		case "7012":
			return "UseCase_ExtensionPointCompartment_CCN";
		case "7017":
			return "Component_PackagedElementCompartment_CCN";
		case "7014":
			return "UseCase_ExtensionPointCompartment_CN";
		case "7015":
			return "Component_PackagedElementCompartment_CN";
		case "7016":
			return "Package_PackagedElementCompartment_CN";
		case "7013":
			return "Package_PackagedElementCompartment";
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
