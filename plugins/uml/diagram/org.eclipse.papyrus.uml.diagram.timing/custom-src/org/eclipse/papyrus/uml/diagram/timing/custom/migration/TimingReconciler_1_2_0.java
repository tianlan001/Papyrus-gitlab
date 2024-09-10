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
package org.eclipse.papyrus.uml.diagram.timing.custom.migration;

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
 * Timing Diagram Reconciler from 1.1.0 to 1.2.0
 *
 * @since 1.3
 */
public class TimingReconciler_1_2_0 extends DiagramReconciler {

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
		case "1":
			return "Package_TimingDiagram";
		case "19":
			return "Lifeline_FullShape";
		case "21":
			return "Lifeline_FullNameLabel";
		case "20":
			return "Lifeline_CompactShape";
		case "22":
			return "Lifeline_CompactNameLabel";
		case "11":
			return "StateInvariant_FullShape";
		case "62":
			return "StateInvariant_FullStereotypeLabel";
		case "28":
			return "StateInvariant_CompactShape";
		case "31":
			return "StateInvariant_CompactNameLabel";
		case "64":
			return "StateInvariant_CompactStereotypeLabel";
		case "12":
			return "OccurrenceSpecification_Shape";
		case "10":
			return "OccurrenceSpecification_NameLabel";
		case "58":
			return "OccurrenceSpecification_StereotypeLabel";
		case "13":
			return "MessageOccurrenceSpecification_Shape";
		case "14":
			return "MessageOccurrenceSpecification_NameLabel";
		case "59":
			return "MessageOccurrenceSpecification_StereotypeLabel";
		case "9":
			return "Node_StateDefinitionShape";
		case "38":
			return "Node_StateDefinitionNameLabel";
		case "39":
			return "Node_StateInvariantTransitionShape";
		case "15":
			return "TimeConstraint_Shape";
		case "30":
			return "TimeConstraint_BodyLabel";
		case "65":
			return "TimeConstraint_StereotypeLabel";
		case "16":
			return "TimeObservation_Shape";
		case "34":
			return "TimeObservation_NameLabel";
		case "66":
			return "TimeObservation_StereotypeLabel";
		case "18":
			return "DurationConstraint_Shape";
		case "33":
			return "DurationConstraint_BodyLabel";
		case "17":
			return "DurationObservation_Shape";
		case "35":
			return "DurationObservation_NameLabel";
		case "67":
			return "GeneralOrdering_Shape";
		case "68":
			return "GeneralOrdering_NameLabel";
		case "24":
			return "Node_FreeTimeRulerShape";
		case "25":
			return "Node_LinearTimeRulerShape";
		case "26":
			return "Node_TickShape";
		case "36":
			return "Node_TickNameLabel";
		case "27":
			return "DestructionOccurrenceSpecification_Shape";
		case "32":
			return "DestructionOccurrenceSpecification_NameLabel";
		case "63":
			return "DestructionOccurrenceSpecification_StereotypeLabel";
		case "40":
			return "Lifeline_Shape";
		case "69":
			return "Gate_Shape";
		case "70":
			return "Gate_NameLabel";
		case "2":
			return "Interaction_Shape";
		case "37":
			return "Interaction_NameLabel";
		case "3":
			return "Message_SynchEdge";
		case "56":
			return "Message_SynchNameLabel";
		case "57":
			return "Message_SynchStereotypeLabel";
		case "4":
			return "Message_AsynchEdge";
		case "60":
			return "Message_AsynchNameLabel";
		case "61":
			return "Message_AsynchStereotypeLabel";
		case "41":
			return "Message_ReplyEdge";
		case "42":
			return "Message_ReplyNameLabel";
		case "43":
			return "Message_ReplyStereotypeLabel";
		case "44":
			return "Message_CreateEdge";
		case "45":
			return "Message_CreateNameLabel";
		case "46":
			return "Message_CreateStereotypeLabel";
		case "47":
			return "Message_DeleteEdge";
		case "48":
			return "Message_DeleteNameLabel";
		case "49":
			return "Message_DeleteStereotypeLabel";
		case "50":
			return "Message_LostEdge";
		case "51":
			return "Message_LostNameLabel";
		case "52":
			return "Message_LostStereotypeLabel";
		case "53":
			return "Message_FoundEdge";
		case "54":
			return "Message_FoundNameLabel";
		case "55":
			return "Message_FoundStereotypeLabel";
		case "5":
			return "Interaction_SubfragmentCompartment";
		case "7":
			return "Lifeline_FullStateDefinitionCompartment";
		case "8":
			return "Lifeline_FullSubfragmentCompartment";
		case "23":
			return "Lifeline_CompactSubfragmentCompartment";
		case "29":
			return "Interaction_TimeRulerCompartment";
		case "82":
			return "Lifeline_FullTimeRulerCompartment";
		case "83":
			return "Lifeline_CompactTimeRulerCompartment";
		case "80":
			return "Node_FreeTimeRulerCompartment";
		case "81":
			return "Node_LinearTimeRulerCompartment";
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
