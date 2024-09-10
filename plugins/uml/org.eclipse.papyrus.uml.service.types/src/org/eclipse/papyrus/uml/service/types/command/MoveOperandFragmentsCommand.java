/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.papyrus.uml.service.types.request.MoveOperandFragmentEditRequest;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;

/**
 * Command to move Operand fragments.
 * @since 3.0
 */
public class MoveOperandFragmentsCommand extends EditElementCommand {

	/** The parent. */
	private InteractionFragment parent;

	/** The list of fragmants to move. */
	private EList<InteractionFragment> fragmentsToMove;

	/** The source operand. */
	private InteractionOperand sourceOperand;

	/** The combined Fragment. */
	private CombinedFragment combinedFragment;

	/**
	 * 
	 * Constructor.
	 *
	 * @param cf
	 *            The {@link CombinedFragment}
	 * @param op
	 *            The {@link InteractionOperand}
	 * @param editRequest
	 *            the {@link MoveOperandFragmentEditRequest}
	 */
	public MoveOperandFragmentsCommand(CombinedFragment cf, InteractionOperand op, MoveOperandFragmentEditRequest editRequest) {
		super(null, editRequest.getElementToEdit(), editRequest);
		InteractionOperand enclosingOp = cf.getEnclosingOperand();
		if (enclosingOp != null) {
			Element owner = enclosingOp.getOwner();
			if (owner instanceof CombinedFragment) {
				this.parent = enclosingOp;
			}
		} else {
			Element owner = cf.getOwner();
			if (owner instanceof Interaction) {
				this.parent = (InteractionFragment) owner;
			}
		}

		this.sourceOperand = op;
		this.combinedFragment = cf;
		fragmentsToMove = op.getFragments();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (sourceOperand != null && combinedFragment != null);
	}

	/**
	 * <p>
	 * Move the fragments to parents and clear the source operand.
	 * <p/>
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (parent != null && !fragmentsToMove.isEmpty()) {

			if (parent instanceof InteractionOperand) {
				((InteractionOperand) parent).getFragments().addAll(fragmentsToMove);

			} else if (parent instanceof Interaction) {
				((Interaction) parent).getFragments().addAll(fragmentsToMove);
			}
		}
		sourceOperand.getFragments().clear();
		return CommandResult.newOKCommandResult();
	} 
}