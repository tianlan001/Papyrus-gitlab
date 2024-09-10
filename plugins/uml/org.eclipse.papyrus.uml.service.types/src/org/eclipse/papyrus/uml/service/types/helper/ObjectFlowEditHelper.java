/*****************************************************************************
 * Copyright (c) 2011-2012, 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    CEA LIST - Initial API and implementation
 *    Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 494514
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.service.types.command.ObjectFlowReorientCommand;
import org.eclipse.papyrus.uml.tools.utils.ActivityEdgeUtil;
import org.eclipse.papyrus.uml.tools.utils.ObjectFlowUtil;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Edit helper class for binary {@link ObjectFlow}
 */
public class ObjectFlowEditHelper extends ElementEditHelper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		ICommand resultCommand = new ObjectFlowReorientCommand(req);

		// Get the object flow
		final ObjectFlow objectFlow = (ObjectFlow) req.getRelationship();

		// Get if this is a source re-orient or the target
		final boolean isReorientSource = req.getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE;

		// Get the source and target from objectFlow and request for needed one
		final Object source = isReorientSource ? req.getNewRelationshipEnd() : objectFlow.getSource();
		final Object target = isReorientSource ? objectFlow.getTarget() : req.getNewRelationshipEnd();

		if ((null == source || source instanceof ActivityNode) && (null == target || target instanceof ActivityNode)) {
			// Get the command to update the InPartition feature
			final ICommand updatePartitionsCommand = getInPartitionUpdateCommand(objectFlow, (ActivityNode) source, (ActivityNode) target);
			if (null != updatePartitionsCommand) {
				resultCommand = CompositeCommand.compose(resultCommand, updatePartitionsCommand);
			}
		}

		return resultCommand;
	}

	/**
	 * Test if the relationship creation is allowed.
	 *
	 * @param source
	 *            the relationship source can be null
	 * @param target
	 *            the relationship target can be null
	 * @param sourceView
	 *            the relationship graphical source can be null
	 * @param targetView
	 *            the relationship graphical target can be null
	 * @return true if the creation is allowed
	 */
	protected boolean canCreate(EObject source, EObject target, View sourceView, View targetView) {
		return canCreate(source, target);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getCreateRelationshipCommand(CreateRelationshipRequest req) {
		EObject source = req.getSource();
		EObject target = req.getTarget();
		boolean noSourceOrTarget = (source == null || target == null);
		boolean noSourceAndTarget = (source == null && target == null);
		if (!noSourceAndTarget && !canCreate(source, target)) {
			// Abort creation.
			return UnexecutableCommand.INSTANCE;
		}
		if (noSourceOrTarget && !noSourceAndTarget) {
			// The request isn't complete yet. Return the identity command so
			// that the create relationship gesture is enabled.
			if (source != null && !(source instanceof ActivityNode)) {
				return UnexecutableCommand.INSTANCE;
			}
			return IdentityCommand.INSTANCE;
		}
		// Propose a semantic container for the new control flow.
		Element proposedContainer = deduceContainer(req);
		if (proposedContainer == null) {
			return UnexecutableCommand.INSTANCE;
		}
		req.setContainer(proposedContainer);
		return new CreateRelationshipCommand(req);
	}

	protected Element deduceContainer(CreateRelationshipRequest request) {
		return ActivityEdgeUtil.deduceContainer(request.getSource(), request.getTarget());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getConfigureCommand(final ConfigureRequest req) {
		ICommand configureCommand = new ConfigureElementCommand(req) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				ObjectFlow element = (ObjectFlow) req.getElementToConfigure();
				ActivityNode source = getSourceObject(req);
				ActivityNode target = getTargetObject(req);
				if (source != null) {
					element.setSource(getSourceObject(req));
				}
				if (target != null) {
					element.setTarget(target);
				}

				// Manage InPartitions
				if (null != source && null != target) {

					// Get the source InPartitions
					final List<ActivityPartition> partitions = new ArrayList<>(source.getInPartitions());
					// Keep the InPartitions contained in the source and target to add it into the control flow
					partitions.retainAll(target.getInPartitions());

					element.getInPartitions().addAll(partitions);
				}

				return CommandResult.newOKCommandResult(element);
			}
		};
		return CompositeCommand.compose(configureCommand, super.getConfigureCommand(req));
	}

	/**
	 * This method provides the object to be use as source.
	 *
	 * @return the source value
	 */
	protected ActivityNode getSourceObject(ConfigureRequest req) {
		Object result = req.getParameter(CreateRelationshipRequest.SOURCE);
		return (result instanceof ActivityNode) ? (ActivityNode) result : null;
	}

	/**
	 * This method provides the object to be used as target.
	 *
	 * @return the target value
	 */
	protected ActivityNode getTargetObject(ConfigureRequest req) {
		Object result = req.getParameter(CreateRelationshipRequest.TARGET);
		return (result instanceof ActivityNode) ? (ActivityNode) result : null;
	}

	/**
	 * {@inheritDoc}
	 */
	protected boolean canCreate(EObject source, EObject target) {
		if ((source != null) && !(source instanceof ActivityNode)) {
			return false;
		}
		if ((target != null) && !(target instanceof ActivityNode)) {
			return false;
		}

		Element container = ActivityEdgeUtil.deduceContainer(source, target);
		if (container == null) {
			return false;
		}
		return ObjectFlowUtil.canExistObjectFlow(container, null, (ActivityNode) source, (ActivityNode) target);
	}

	/**
	 * This allows to get the command to update the InPartition feature if needed.
	 *
	 * @param objectFlow
	 *            The object flow to update.
	 * @param source
	 *            The source activity node.
	 * @param target
	 *            The target activity node.
	 * @return The command to update the InPartition feature (can be <code>null</code>).
	 */
	protected ICommand getInPartitionUpdateCommand(final ObjectFlow objectFlow, final ActivityNode source, final ActivityNode target) {
		List<ActivityPartition> partitions = new ArrayList<>();
		if (null != source && null != target) {
			// Get the source InPartitions
			partitions = new ArrayList<>(source.getInPartitions());
			// Keep the in partitions contained in the source and target to add it into the object flow
			partitions.retainAll(target.getInPartitions());

			if (!partitions.isEmpty()) {
				return new SetValueCommand(new SetRequest(objectFlow, UMLPackage.eINSTANCE.getActivityEdge_InPartition(), partitions));
			}
		}

		// If the partitions found are empty and the object flow InPartitions are not, clear this
		if (partitions.isEmpty() && !objectFlow.getInPartitions().isEmpty()) {
			return new SetValueCommand(new SetRequest(objectFlow, UMLPackage.eINSTANCE.getActivityEdge_InPartition(), partitions));
		}

		return null;
	}
}
