/*****************************************************************************
 * Copyright (c) 2012, 2015, 2019 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 462979
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bugs 494514, 533248
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A customization of the destroy and configure commands for activity nodes to account for
 * the bizarre {@link Element#getOwnedElements() Element::ownedElement} override in {@link Activity} that has {@code node} and {@code group} subsetting {@code ownedElement} instead of {@code ownedNode} and {@code ownedGroup}.
 *
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=463177
 */
public class ActivityNodeHelper extends ElementEditHelper {

	/**
	 * Parameter name for {@link ConfigureRequest} Used to pass the actual partition instance to set {@link ActivityNode#getInPartition()}
	 */
	public static final String IN_PARTITION = "IN_PARTITION"; //$NON-NLS-1$

	public static final String IN_INTERRUPTIBLE_ACTIVITY_REGION = "IN_INTERRUPTIBLE_ACTIVITY_REGION"; //$NON-NLS-1$

	public static final String OUT_FROM_PARTITION = "OUT_FROM_PARTITION";

	public static final String OUT_FROM_INTERRUPTIBLE_REGION = "OUT_FROM_REGION";

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.helper.DefaultEditHelper#getCreateCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest)
	 */
	@Override
	protected ICommand getCreateCommand(final CreateElementRequest req) {
		// Manage the InPartitions and the InInterruptibleRegions features
		fillRequestWithInPartitionsAndInInterruptibleRegions(req);
		return super.getCreateCommand(req);
	}

	@Override
	protected ICommand getBasicDestroyElementCommand(DestroyElementRequest req) {
		ICommand result = req.getBasicDestroyCommand();

		if (result == null) {
			result = new DestroyActivityOwnedElementCommand(req);
		} else {
			// ensure that re-use of this request will not accidentally
			// propagate this command, which would destroy the wrong object
			req.setBasicDestroyCommand(null);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.uml.service.types.helper.ElementEditHelper#getConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected ICommand getConfigureCommand(final ConfigureRequest req) {
		if (req.getParameter(IN_PARTITION) != null) {
			final Object parameterValue = req.getParameter(IN_PARTITION);

			// Manage some commands if this is a collection
			if (parameterValue instanceof Collection) {
				final CompositeCommand compositeCommand = new CompositeCommand("Set ActivityPartitions Nodes"); //$NON-NLS-1$
				for (final Object entryValue : (Collection<?>) parameterValue) {
					if (entryValue instanceof EObject) {
						compositeCommand.add(new SetValueCommand(new SetRequest((EObject) entryValue, UMLPackage.eINSTANCE.getActivityPartition_Node(), req.getElementToConfigure())));
					}
				}
				return compositeCommand;

				// Else, just return the correct set command
			} else if (parameterValue instanceof EObject) {
				return new SetValueCommand(new SetRequest((EObject) parameterValue, UMLPackage.eINSTANCE.getActivityPartition_Node(), req.getElementToConfigure()));
			}
		}
		if (req.getParameter(IN_INTERRUPTIBLE_ACTIVITY_REGION) != null) {

			final Object parameterValue = req.getParameter(IN_INTERRUPTIBLE_ACTIVITY_REGION);

			// Manage some commands if this is a collection
			if (parameterValue instanceof Collection) {
				final CompositeCommand compositeCommand = new CompositeCommand("Set InterruptibleActivityRegions Nodes"); //$NON-NLS-1$
				for (final Object entryValue : (Collection<?>) parameterValue) {
					if (entryValue instanceof EObject) {
						compositeCommand.add(new SetValueCommand(new SetRequest((EObject) entryValue, UMLPackage.eINSTANCE.getInterruptibleActivityRegion_Node(), req.getElementToConfigure())));
					}
				}
				return compositeCommand;

				// Else, just return the correct set command
			} else if (parameterValue instanceof EObject) {
				return new SetValueCommand(new SetRequest((EObject) parameterValue, UMLPackage.eINSTANCE.getInterruptibleActivityRegion_Node(), req.getElementToConfigure()));
			}
		}
		return super.getConfigureCommand(req);
	}

	/**
	 * Basic destruction command for owned elements of activities.
	 *
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=463177
	 */
	static class DestroyActivityOwnedElementCommand extends DestroyElementCommand {

		DestroyActivityOwnedElementCommand(DestroyElementRequest request) {
			super(request);
		}

		@Override
		protected void tearDownIncomingReferences(EObject destructee) {
			Activity activity = TypeUtils.as(destructee.eContainer(), Activity.class);
			if (activity != null) {
				// Forcibly remove it from invalid subsets of Activity::ownedElement
				activity.getNodes().remove(destructee);
				activity.getGroups().remove(destructee);
			}

			super.tearDownIncomingReferences(destructee);
		}
	}

	/**
	 * This allows to fill the create element request with in partitions if needed.
	 *
	 * @param createRequest
	 *            The create element request.
	 */
	public static void fillRequestWithInPartitionsAndInInterruptibleRegions(final CreateElementRequest createRequest) {
		final EObject container = createRequest.getContainer();
		if (container instanceof ActivityNode) {
			// Manage the InPartition if needed
			final Collection<ActivityPartition> inPartitions = ((ActivityNode) container).getInPartitions();
			if (!inPartitions.isEmpty()) {
				createRequest.setParameter(ActivityNodeHelper.IN_PARTITION, inPartitions);
			}

			// Manage the InInterruptibleRegions if needed
			final Collection<InterruptibleActivityRegion> inInterruptibleRegions = ((ActivityNode) container).getInInterruptibleRegions();
			if (!inInterruptibleRegions.isEmpty()) {
				createRequest.setParameter(ActivityNodeHelper.IN_INTERRUPTIBLE_ACTIVITY_REGION, inInterruptibleRegions);
			}
		}
	}

	public static ICommand getMoveOutFromPartitionCommand(MoveRequest req) {
		if (req.getParameter(OUT_FROM_PARTITION) != null) {
			CompositeCommand cc = new CompositeCommand("Move Out From Parition");//$NON-NLS-1$
			ActivityPartition outFromPartition = (ActivityPartition) req.getParameter(OUT_FROM_PARTITION);
			for (Object elementToMove : req.getElementsToMove().keySet()) {
				if (false == elementToMove instanceof ActivityNode) {
					continue;
				}
				ActivityNode node = (ActivityNode) elementToMove;
				List<ActivityPartition> inPartitions = new LinkedList<>(node.getInPartitions());
				boolean change = false;
				if (inPartitions.contains(outFromPartition)) {
					inPartitions.remove(outFromPartition);
					change = true;
				}
				// We know we have to remove the out inPartition, but we maybe have to add another inPartition
				if (req.getParameter(IN_PARTITION) != null) {
					ActivityPartition inPartition = (ActivityPartition) req.getParameter(IN_PARTITION);
					if (!inPartitions.contains(inPartition)) {
						inPartitions.add(inPartition);
						change = true;
					}
				}
				if (change) {
					cc.add(new SetValueCommand(new SetRequest(node, UMLPackage.eINSTANCE.getActivityNode_InPartition(), inPartitions)));
				}

				// We know we have to remove the out inPartition, but maybe the inInterruptibleRegion must be filled
				if (req.getParameter(IN_INTERRUPTIBLE_ACTIVITY_REGION) != null) {
					InterruptibleActivityRegion inRegion = (InterruptibleActivityRegion) req.getParameter(IN_INTERRUPTIBLE_ACTIVITY_REGION);
					List<InterruptibleActivityRegion> inRegions = new LinkedList<>(node.getInInterruptibleRegions());
					if (!inRegions.contains(inRegion)) {
						inRegions.add(inRegion);
						cc.add(new SetValueCommand(new SetRequest(node, UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion(), inRegions)));
					}
				}
			}
			return cc.isEmpty() ? null : cc.reduce();
		}
		return null;
	}

	public static ICommand getMoveOutFromInterruptibleActivityRegionCommand(MoveRequest req) {
		if (req.getParameter(OUT_FROM_INTERRUPTIBLE_REGION) != null) {
			CompositeCommand cc = new CompositeCommand("Move Out From InterruptibleActivityRegion");//$NON-NLS-1$
			InterruptibleActivityRegion outFromRegion = (InterruptibleActivityRegion) req.getParameter(OUT_FROM_INTERRUPTIBLE_REGION);
			for (Object elementToMove : req.getElementsToMove().keySet()) {
				if (false == elementToMove instanceof ActivityNode) {
					continue;
				}
				ActivityNode node = (ActivityNode) elementToMove;
				List<InterruptibleActivityRegion> inRegions = new LinkedList<>(node.getInInterruptibleRegions());
				boolean change = false;
				if (inRegions.contains(outFromRegion)) {
					inRegions.remove(outFromRegion);
					change = true;
				}
				// We know we have to remove the out inInterruptibleRegion, but we maybe have to add another inInterruptibleRegion
				if (req.getParameter(IN_INTERRUPTIBLE_ACTIVITY_REGION) != null) {
					InterruptibleActivityRegion inRegion = (InterruptibleActivityRegion) req.getParameter(IN_INTERRUPTIBLE_ACTIVITY_REGION);
					if (!inRegions.contains(inRegion)) {
						inRegions.add(inRegion);
						change = true;
					}
				}
				if (change) {
					cc.add(new SetValueCommand(new SetRequest(node, UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion(), inRegions)));
				}

				// We know we have to remove the out inInterruptibleRegion, but maybe the inPartition must be filled
				if (req.getParameter(IN_PARTITION) != null) {
					ActivityPartition inPartition = (ActivityPartition) req.getParameter(IN_PARTITION);
					List<ActivityPartition> inPartitions = new LinkedList<>(node.getInPartitions());
					if (!inPartitions.contains(inPartition)) {
						inPartitions.add(inPartition);
						cc.add(new SetValueCommand(new SetRequest(node, UMLPackage.eINSTANCE.getActivityNode_InPartition(), inPartitions)));
					}
				}
			}
			return cc.isEmpty() ? null : cc.reduce();
		}
		return null;
	}
}
