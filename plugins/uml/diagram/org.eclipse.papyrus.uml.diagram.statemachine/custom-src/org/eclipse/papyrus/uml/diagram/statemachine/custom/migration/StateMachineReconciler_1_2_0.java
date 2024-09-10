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
package org.eclipse.papyrus.uml.diagram.statemachine.custom.migration;

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
 * State Machine Diagram Reconciler from 1.1.0 to 1.2.0
 *
 * @since 3.1
 */
public class StateMachineReconciler_1_2_0 extends DiagramReconciler {

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
			return "Package_StateMachineDiagram";
		case "3000":
			return "Region_Shape";
		case "5000":
			return "FinalState_Shape";
		case "5001":
			return "FinalState_FloatingNameLabel";
		case "5002":
			return "FinalState_StereotypeLabel";
		case "6000":
			return "State_Shape";
		case "6001":
			return "State_NameLabel";
		case "19003":
			return "State_FloatingNameLabel";
		case "8000":
			return "Pseudostate_InitialShape";
		case "8001":
			return "Pseudostate_InitialFloatingNameLabel";
		case "8002":
			return "Pseudostate_InitialStereotypeLabel";
		case "9000":
			return "Pseudostate_JoinShape";
		case "9001":
			return "Pseudostate_JoinFloatingNameLabel";
		case "9002":
			return "Pseudostate_JoinStereotypeLabel";
		case "10000":
			return "Pseudostate_ForkShape";
		case "10001":
			return "Pseudostate_ForkFloatingNameLabel";
		case "10002":
			return "Pseudostate_ForkStereotypeLabel";
		case "11000":
			return "Pseudostate_ChoiceShape";
		case "11001":
			return "Pseudostate_ChoiceFloatingNameLabel";
		case "11002":
			return "Pseudostate_ChoiceStereotypeLabel";
		case "12000":
			return "Pseudostate_JunctionShape";
		case "12001":
			return "Pseudostate_JunctionFloatingNameLabel";
		case "12002":
			return "Pseudostate_JunctionStereotypeLabel";
		case "13000":
			return "Pseudostate_ShallowHistoryShape";
		case "13001":
			return "Pseudostate_ShallowHistoryFloatingNameLabel";
		case "13002":
			return "Pseudostate_ShallowHistoryStereotypeLabel";
		case "14000":
			return "Pseudostate_DeepHistoryShape";
		case "14001":
			return "Pseudostate_DeepHistoryFloatingNameLabel";
		case "14002":
			return "Pseudostate_DeepHistoryStereotypeLabel";
		case "15000":
			return "Pseudostate_TerminateShape";
		case "15001":
			return "Pseudostate_TerminateFloatingNameLabel";
		case "15002":
			return "Pseudostate_TerminateStereotypeLabel";
		case "16000":
			return "Pseudostate_EntryPointShape";
		case "16001":
			return "Pseudostate_EntryPointFloatingNameLabel";
		case "16002":
			return "Pseudostate_EntryPointStereotypeLabel";
		case "17000":
			return "Pseudostate_ExitPointShape";
		case "17001":
			return "Pseudostate_ExitPointFloatingNameLabel";
		case "17002":
			return "Pseudostate_ExitPointStereotypeLabel";
		case "18000":
			return "ConnectionPointReference_Shape";
		case "18001":
			return "ConnectionPointReference_NameLabel";
		case "18002":
			return "ConnectionPointReference_StereotypeLabel";
		case "666":
			return "Comment_Shape";
		case "6666":
			return "Comment_BodyLabel";
		case "668":
			return "Constraint_Shape";
		case "6668":
			return "Constraint_NameLabel";
		case "6669":
			return "Constraint_BodyLabel";
		case "680":
			return "Transition_InternalTransitionLabel";
		case "690":
			return "Behavior_EntryBehaviorLabel";
		case "691":
			return "Behavior_DoActivityBehaviorLabel";
		case "692":
			return "Behavior_ExitBehaviorLabel";
		case "2000":
			return "StateMachine_Shape";
		case "2001":
			return "StateMachine_NameLabel";
		case "7000":
			return "Transition_Edge";
		case "7001":
			return "Transition_NameLabel";
		case "7002":
			return "Transition_GuardLabel";
		case "7003":
			return "Transition_StereotypeLabel";
		case "19000":
			return "Generalization_Edge";
		case "19002":
			return "Generalization_StereotypeLabel";
		case "667":
			return "Comment_AnnotatedElementEdge";
		case "670":
			return "Constraint_ConstrainedElementEdge";
		case "8500":
			return "Constraint_ContextEdge";
		case "8501":
			return "Constraint_KeywordLabel";
		case "3002":
			return "Region_SubvertexCompartment";
		case "2002":
			return "StateMachine_RegionCompartment";
		case "6002":
			return "State_RegionCompartment";
		default:
			return defaultGetNewVisualID(oldVisualID);
		}
	}

	private static String defaultGetNewVisualID(String oldVisualID) {
		return oldVisualID;
	}
}
