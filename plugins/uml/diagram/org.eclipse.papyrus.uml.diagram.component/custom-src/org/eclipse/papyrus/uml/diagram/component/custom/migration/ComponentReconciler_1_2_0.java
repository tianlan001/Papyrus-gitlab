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
package org.eclipse.papyrus.uml.diagram.component.custom.migration;

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
 * Component Diagram Reconciler from 1.1.0 to 1.2.0
 *
 * @since 3.0
 */
public class ComponentReconciler_1_2_0 extends DiagramReconciler {

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
		case "1000": //$NON-NLS-1$
			return "Package_ComponentDiagram"; //$NON-NLS-1$
		case "3069": //$NON-NLS-1$
			return "Port_Shape"; //$NON-NLS-1$
		case "5006": //$NON-NLS-1$
			return "Port_NameLabel"; //$NON-NLS-1$
		case "5007": //$NON-NLS-1$
			return "Port_StereotypeLabel"; //$NON-NLS-1$
		case "3077": //$NON-NLS-1$
			return "Model_Shape_CN"; //$NON-NLS-1$
		case "5264": //$NON-NLS-1$
			return "Model_NameLabel_CN"; //$NON-NLS-1$
		case "3076": //$NON-NLS-1$
			return "Package_Shape_CN"; //$NON-NLS-1$
		case "5261": //$NON-NLS-1$
			return "Package_NameLabel_CN"; //$NON-NLS-1$
		case "3078": //$NON-NLS-1$
			return "Interface_ClassifierShape_CN"; //$NON-NLS-1$
		case "5267": //$NON-NLS-1$
			return "Interface_ClassifierNameLabel_CN"; //$NON-NLS-1$
		case "6033": //$NON-NLS-1$
			return "Interface_ClassifierFloatingNameLabel_CN"; //$NON-NLS-1$
		case "3070": //$NON-NLS-1$
			return "Component_PackagedElementShape_CCN"; //$NON-NLS-1$
		case "5256": //$NON-NLS-1$
			return "Component_NameLabel_CCN"; //$NON-NLS-1$
		case "6026": //$NON-NLS-1$
			return "Component_FloatingNameLabel_CCN"; //$NON-NLS-1$
		case "3071": //$NON-NLS-1$
			return "Component_PackagedElementShape_CN"; //$NON-NLS-1$
		case "5257": //$NON-NLS-1$
			return "Component_NameLabel_CN"; //$NON-NLS-1$
		case "6027": //$NON-NLS-1$
			return "Component_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3074": //$NON-NLS-1$
			return "Comment_Shape_CN"; //$NON-NLS-1$
		case "5258": //$NON-NLS-1$
			return "Comment_BodyLabel_CN"; //$NON-NLS-1$
		case "3075": //$NON-NLS-1$
			return "Constraint_Shape_CN"; //$NON-NLS-1$
		case "5259": //$NON-NLS-1$
			return "Constraint_NameLabel_CN"; //$NON-NLS-1$
		case "5260": //$NON-NLS-1$
			return "Constraint_BodyLabel_CN"; //$NON-NLS-1$
		case "1": //$NON-NLS-1$
			return "Property_InterfaceAttributeLabel"; //$NON-NLS-1$
		case "5": //$NON-NLS-1$
			return "Operation_InterfaceOperationLabel"; //$NON-NLS-1$
		case "6": //$NON-NLS-1$
			return "Reception_InterfaceReceptionLabel"; //$NON-NLS-1$
		case "3072": //$NON-NLS-1$
			return "Interface_Shape_CN"; //$NON-NLS-1$
		case "0": //$NON-NLS-1$
			return "Interface_NameLabel_CN"; //$NON-NLS-1$
		case "6028": //$NON-NLS-1$
			return "Interface_FloatingNameLabel_CN"; //$NON-NLS-1$
		case "3079": //$NON-NLS-1$
			return "Property_Shape"; //$NON-NLS-1$
		case "5268": //$NON-NLS-1$
			return "Property_NameLabel"; //$NON-NLS-1$
		case "3203": //$NON-NLS-1$
			return "Dependency_Shape"; //$NON-NLS-1$
		case "5008": //$NON-NLS-1$
			return "Dependency_MultiNameLabel"; //$NON-NLS-1$
		case "6029": //$NON-NLS-1$
			return "Dependency_FloatingNameLabel"; //$NON-NLS-1$
		case "2002": //$NON-NLS-1$
			return "Component_PackagedElementShape"; //$NON-NLS-1$
		case "5004": //$NON-NLS-1$
			return "Component_NameLabel"; //$NON-NLS-1$
		case "6030": //$NON-NLS-1$
			return "Component_FloatingNameLabel"; //$NON-NLS-1$
		case "3202": //$NON-NLS-1$
			return "Model_Shape"; //$NON-NLS-1$
		case "5262": //$NON-NLS-1$
			return "Model_NameLabel"; //$NON-NLS-1$
		case "3200": //$NON-NLS-1$
			return "Package_Shape"; //$NON-NLS-1$
		case "5254": //$NON-NLS-1$
			return "Package_NameLabel"; //$NON-NLS-1$
		case "3205": //$NON-NLS-1$
			return "Interface_ClassifierShape"; //$NON-NLS-1$
		case "5266": //$NON-NLS-1$
			return "Interface_ClassifierNameLabel"; //$NON-NLS-1$
		case "6031": //$NON-NLS-1$
			return "Interface_ClassifierFloatingNameLabel"; //$NON-NLS-1$
		case "3201": //$NON-NLS-1$
			return "Comment_Shape"; //$NON-NLS-1$
		case "5255": //$NON-NLS-1$
			return "Comment_BodyLabel"; //$NON-NLS-1$
		case "3199": //$NON-NLS-1$
			return "Constraint_Shape"; //$NON-NLS-1$
		case "5252": //$NON-NLS-1$
			return "Constraint_NameLabel"; //$NON-NLS-1$
		case "5253": //$NON-NLS-1$
			return "Constraint_BodyLabel"; //$NON-NLS-1$
		case "3204": //$NON-NLS-1$
			return "NamedElement_DefaultShape"; //$NON-NLS-1$
		case "5265": //$NON-NLS-1$
			return "NamedElement_NameLabel"; //$NON-NLS-1$
		case "2003": //$NON-NLS-1$
			return "Interface_Shape"; //$NON-NLS-1$
		case "5005": //$NON-NLS-1$
			return "Interface_NameLabel"; //$NON-NLS-1$
		case "6032": //$NON-NLS-1$
			return "Interface_FloatingNameLabel"; //$NON-NLS-1$
		case "4001": //$NON-NLS-1$
			return "Usage_Edge"; //$NON-NLS-1$
		case "6016": //$NON-NLS-1$
			return "Usage_NameLabel"; //$NON-NLS-1$
		case "6017": //$NON-NLS-1$
			return "Usage_StereotypeLabel"; //$NON-NLS-1$
		case "4006": //$NON-NLS-1$
			return "InterfaceRealization_Edge"; //$NON-NLS-1$
		case "6010": //$NON-NLS-1$
			return "InterfaceRealization_NameLabel"; //$NON-NLS-1$
		case "6011": //$NON-NLS-1$
			return "InterfaceRealization_StereotypeLabel"; //$NON-NLS-1$
		case "4003": //$NON-NLS-1$
			return "Generalization_Edge"; //$NON-NLS-1$
		case "2": //$NON-NLS-1$
			return "Generalization_StereotypeLabel"; //$NON-NLS-1$
		case "4012": //$NON-NLS-1$
			return "Substitution_Edge"; //$NON-NLS-1$
		case "6006": //$NON-NLS-1$
			return "Substitution_NameLabel"; //$NON-NLS-1$
		case "6020": //$NON-NLS-1$
			return "Substitution_StereotypeLabel"; //$NON-NLS-1$
		case "4014": //$NON-NLS-1$
			return "Manifestation_Edge"; //$NON-NLS-1$
		case "6008": //$NON-NLS-1$
			return "Manifestation_NameLabel"; //$NON-NLS-1$
		case "6022": //$NON-NLS-1$
			return "Manifestation_StereotypeLabel"; //$NON-NLS-1$
		case "4007": //$NON-NLS-1$
			return "ComponentRealization_Edge"; //$NON-NLS-1$
		case "3": //$NON-NLS-1$
			return "ComponentRealization_NameLabel"; //$NON-NLS-1$
		case "4": //$NON-NLS-1$
			return "ComponentRealization_StereotypeLabel"; //$NON-NLS-1$
		case "4013": //$NON-NLS-1$
			return "Abstraction_Edge"; //$NON-NLS-1$
		case "6007": //$NON-NLS-1$
			return "Abstraction_NameLabel"; //$NON-NLS-1$
		case "6021": //$NON-NLS-1$
			return "Abstraction_StereotypeLabel"; //$NON-NLS-1$
		case "4016": //$NON-NLS-1$
			return "Link_DescriptorEdge"; //$NON-NLS-1$
		case "4015": //$NON-NLS-1$
			return "Comment_AnnotatedElementEdge"; //$NON-NLS-1$
		case "4009": //$NON-NLS-1$
			return "Constraint_ConstrainedElementEdge"; //$NON-NLS-1$
		case "4010": //$NON-NLS-1$
			return "Dependency_Edge"; //$NON-NLS-1$
		case "6009": //$NON-NLS-1$
			return "Dependency_NameLabel"; //$NON-NLS-1$
		case "6023": //$NON-NLS-1$
			return "Dependency_StereotypeLabel"; //$NON-NLS-1$
		case "4017": //$NON-NLS-1$
			return "Dependency_BranchEdge"; //$NON-NLS-1$
		case "4018": //$NON-NLS-1$
			return "Link_InterfacePortEdge"; //$NON-NLS-1$
		case "4019": //$NON-NLS-1$
			return "Connector_Edge"; //$NON-NLS-1$
		case "6024": //$NON-NLS-1$
			return "Connector_StereotypeLabel"; //$NON-NLS-1$
		case "6025": //$NON-NLS-1$
			return "Connector_NameLabel"; //$NON-NLS-1$
		case "7001": //$NON-NLS-1$
			return "Component_StructureCompartment"; //$NON-NLS-1$
		case "7006": //$NON-NLS-1$
			return "Model_PackagedElementCompartment"; //$NON-NLS-1$
		case "7002": //$NON-NLS-1$
			return "Package_PackagedElementCompartment"; //$NON-NLS-1$
		case "7007": //$NON-NLS-1$
			return "Model_PackagedElementCompartment_CN"; //$NON-NLS-1$
		case "7005": //$NON-NLS-1$
			return "Package_PackagedElementCompartment_CN"; //$NON-NLS-1$
		case "7003": //$NON-NLS-1$
			return "Component_StructureCompartment_CCN"; //$NON-NLS-1$
		case "7004": //$NON-NLS-1$
			return "Component_StructureCompartment_CN"; //$NON-NLS-1$
		case "7008": //$NON-NLS-1$
			return "Interface_AttributeCompartment"; //$NON-NLS-1$
		case "7009": //$NON-NLS-1$
			return "Interface_OperationCompartment"; //$NON-NLS-1$
		case "7010": //$NON-NLS-1$
			return "Interface_AttributeCompartment_CN"; //$NON-NLS-1$
		case "7011": //$NON-NLS-1$
			return "Interface_OperationCompartment_CN"; //$NON-NLS-1$
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
