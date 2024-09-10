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
package org.eclipse.papyrus.uml.diagram.deployment.custom.migration;

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
 * Deployment Diagram Reconciler from 1.1.0 to 1.2.0
 *
 * @since 3.0
 */
public class DeploymentReconciler_1_2_0 extends DiagramReconciler {

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
			return "Package_DeploymentDiagram";
		case "49":
			return "Model_Shape_CN";
		case "50":
			return "Model_NameLabel_CN";
		case "36":
			return "Package_Shape_CN";
		case "37":
			return "Package_NameLabel_CN";
		case "16":
			return "Device_Shape_CCN";
		case "20":
			return "Device_NameLabel_CCN";
		case "44":
			return "Device_Shape_CN";
		case "45":
			return "Device_NameLabel_CN";
		case "21":
			return "ExecutionEnvironment_Shape_CCN";
		case "22":
			return "ExecutionEnvironment_NameLabel_CCN";
		case "46":
			return "ExecutionEnvironment_Shape_CN";
		case "47":
			return "ExecutionEnvironment_NameLabel_CN";
		case "23":
			return "Node_Shape_CCN";
		case "24":
			return "Node_NameLabel_CCN";
		case "42":
			return "Node_Shape_CN";
		case "43":
			return "Node_NameLabel_CN";
		case "25":
			return "Artifact_Shape_CCN";
		case "27":
			return "Artifact_NameLabel_CCN";
		case "59":
			return "Artifact_FloatingNameLabel_CCN";
		case "28":
			return "Artifact_Shape_ACN";
		case "29":
			return "Artifact_NameLabel_ACN";
		case "60":
			return "Artifact_FloatingNameLabel_ACN";
		case "40":
			return "Artifact_Shape_CN";
		case "41":
			return "Artifact_NameLabel_CN";
		case "54":
			return "Comment_Shape_CN";
		case "55":
			return "Comment_BodyLabel_CN";
		case "56":
			return "Constraint_Shape_CN";
		case "57":
			return "Constraint_NameLabel_CN";
		case "58":
			return "Constraint_BodyLabel_CN";
		case "2014":
			return "DeploymentSpecification_Shape_CCN";
		case "64":
			return "DeploymentSpecification_NameLabel_CCN";
		case "65":
			return "DeploymentSpecification_FloatingNameLabel_CCN";
		case "2015":
			return "DeploymentSpecification_Shape_CN";
		case "66":
			return "DeploymentSpecification_NameLabel_CN";
		case "67":
			return "DeploymentSpecification_FloatingNameLabel_CN";
		case "2016":
			return "DeploymentSpecification_Shape_ACN";
		case "68":
			return "DeploymentSpecification_NameLabel_ACN";
		case "69":
			return "DeploymentSpecification_FloatingNameLabel_ACN";
		case "2011":
			return "Dependency_Shape";
		case "7":
			return "Dependency_MultiNameLabel";
		case "2010":
			return "Model_Shape";
		case "48":
			return "Model_NameLabel";
		case "2009":
			return "Package_Shape";
		case "35":
			return "Package_NameLabel";
		case "2005":
			return "Constraint_Shape";
		case "2":
			return "Constraint_NameLabel";
		case "3":
			return "Constraint_BodyLabel";
		case "2001":
			return "Comment_Shape";
		case "1":
			return "Comment_BodyLabel";
		case "2002":
			return "ExecutionEnvironment_Shape";
		case "5":
			return "ExecutionEnvironment_NameLabel";
		case "2003":
			return "Device_Shape";
		case "6":
			return "Device_NameLabel";
		case "2006":
			return "Artifact_Shape";
		case "8":
			return "Artifact_NameLabel";
		case "61":
			return "Artifact_FloatingNameLabel";
		case "2008":
			return "Node_Shape";
		case "9":
			return "Node_NameLabel";
		case "2012":
			return "NamedElement_DefaultShape";
		case "53":
			return "NamedElement_NameLabel";
		case "2013":
			return "DeploymentSpecification_Shape";
		case "62":
			return "DeploymentSpecification_NameLabel";
		case "63":
			return "DeploymentSpecification_FloatingNameLabel";
		case "4005":
			return "Link_DescriptorEdge";
		case "4008":
			return "Comment_AnnotatedElementEdge";
		case "4009":
			return "Constraint_ConstrainedElementEdge";
		case "4001":
			return "Deployment_Edge";
		case "13":
			return "Deployment_NameLabel";
		case "14":
			return "Deployment_StereotypeLabel";
		case "4002":
			return "Manifestation_Edge";
		case "10":
			return "Manifestation_NameLabel";
		case "11":
			return "Manifestation_StereotypeLabel";
		case "4003":
			return "Generalization_Edge";
		case "4":
			return "Generalization_StereotypeLabel";
		case "4004":
			return "Dependency_Edge";
		case "12":
			return "Dependency_NameLabel";
		case "15":
			return "Dependency_StereotypeLabel";
		case "4010":
			return "Dependency_BranchEdge";
		case "4011":
			return "CommunicationPath_Edge";
		case "6001":
			return "CommunicationPath_NameLabel";
		case "6002":
			return "CommunicationPath_StereotypeLabel";
		case "51":
			return "Model_PackagedElementCompartment";
		case "38":
			return "Package_PackagedElementCompartment";
		case "17":
			return "Device_NestedNodeCompartment";
		case "18":
			return "ExecutionEnvironment_NestedNodeCompartment";
		case "19":
			return "Node_NestedNodeCompartment";
		case "26":
			return "Artifact_NestedArtifactCompartment";
		case "52":
			return "Model_PackagedElementCompartment_CN";
		case "39":
			return "Package_PackagedElementCompartment_CN";
		case "30":
			return "Device_NestedNodeCompartment_CN";
		case "31":
			return "ExecutionEnvironment_NestedNodeCompartment_CN";
		case "32":
			return "Node_NestedNodeCompartment_CN";
		case "33":
			return "Artifact_NestedArtifactCompartment_CCN";
		case "34":
			return "Artifact_NestedArtifactCompartment_ACN";
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
