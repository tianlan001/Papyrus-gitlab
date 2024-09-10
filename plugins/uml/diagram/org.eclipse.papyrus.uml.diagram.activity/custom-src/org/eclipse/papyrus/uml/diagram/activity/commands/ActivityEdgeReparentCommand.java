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
 *  Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.uml.tools.utils.ActivityEdgeUtil;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.StructuredActivityNode;

/**
 * Customized ActivityEdge reparent command, takes changing of container into account.
 */
public class ActivityEdgeReparentCommand extends AbstractTransactionalCommand {

	private ActivityEdge edge;

	/**
	 * Constructor.
	 *
	 * @param domain
	 * @param edge
	 */
	public ActivityEdgeReparentCommand(TransactionalEditingDomain domain, ActivityEdge edge) {
		super(domain, "ActivityEdge reparent command", null); //$NON-NLS-1$
		this.edge = edge;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param monitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		changeContainer(edge);
		return CommandResult.newOKCommandResult();
	}

	/**
	 * change the container of an edge, if necessary
	 *
	 * @param edge
	 */
	private boolean changeContainer(ActivityEdge edge) {
		Element proposedContainer = ActivityEdgeUtil.deduceContainer(edge.getSource(), edge.getTarget());
		if (proposedContainer == null) {
			return false; // edge is not valid
		}
		if (proposedContainer != edge.getOwner()) {
			if (proposedContainer instanceof Activity) {
				((Activity) proposedContainer).getEdges().add(edge); // will remove edge automatically from original container
			} else if (proposedContainer instanceof StructuredActivityNode) {
				((StructuredActivityNode) proposedContainer).getEdges().add(edge);
			}
		}
		return true;
	}

}
