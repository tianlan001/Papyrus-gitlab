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
package org.eclipse.papyrus.uml.diagram.communication.custom.migration;

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
 *
 * @since 3.0
 */
public class CommunicationReconciler_1_2_0 extends DiagramReconciler {

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
			return "Package_CommunicationDiagram";
		case "8001":
			return "Lifeline_Shape";
		case "5002":
			return "Lifeline_NameLabel";
		case "6014":
			return "Lifeline_FloatingNameLabel";
		case "8004":
			return "Constraint_Shape";
		case "5064":
			return "Constraint_NameLabel";
		case "5160":
			return "Constraint_BodyLabel";
		case "8005":
			return "Comment_Shape";
		case "5150":
			return "Comment_BodyLabel";
		case "8006":
			return "TimeObservation_Shape";
		case "5153":
			return "TimeObservation_NameLabel";
		case "5154":
			return "TimeObservation_StereotypeLabel";
		case "8007":
			return "DurationObservation_Shape";
		case "5155":
			return "DurationObservation_NameLabel";
		case "5156":
			return "DurationObservation_StereotypeLabel";
		case "8002":
			return "Interaction_Shape";
		case "5001":
			return "Interaction_NameLabel";
		case "6013":
			return "Interaction_FloatingNameLabel";
		case "8016":
			return "Diagram_ShortcutShape";
		case "0":
			return "Diagram_NameLabel";
		case "8009":
			return "Path_Edge";
		case "6001":
			return "Path_MessageLabel";
		case "6012":
			return "Path_StereotypeLabel";
		case "8010":
			return "Comment_AnnotatedElementEdge";
		case "8011":
			return "Constraint_ConstrainedElementEdge";
		case "8012":
			return "DurationObservation_EventEdge";
		case "8013":
			return "TimeObservation_EventEdge";
		case "7001":
			return "Interaction_SubfragmentCompartment";
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
