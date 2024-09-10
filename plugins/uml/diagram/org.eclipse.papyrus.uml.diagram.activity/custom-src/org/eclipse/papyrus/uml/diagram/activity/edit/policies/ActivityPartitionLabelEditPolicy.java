/*****************************************************************************
 * Copyright (c) 2017 Ericsson Communications.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ericsson Communications - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionNameEditPart;
import org.eclipse.papyrus.uml.diagram.activity.helper.ActivityPartitionLabelHelper;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Element;

/**
 * @since 3.0
 */
public class ActivityPartitionLabelEditPolicy extends AbstractMaskManagedEditPolicy {

	@Override
	public void addAdditionalListeners() {
		super.addAdditionalListeners();

		ActivityPartition activityPartition = getUMLElement();
		// check host semantic element is not null
		if (activityPartition == null) {
			return;
		}
		// adds a listener to the element itself, and to linked elements, like Type
		getDiagramEventBroker().addNotificationListener(activityPartition, this);
		Element el = activityPartition.getRepresents();
		if (el != null) {
			getDiagramEventBroker().addNotificationListener(el, this);
		}
	}

	@Override
	protected void removeAdditionalListeners() {
		super.removeAdditionalListeners();
		ActivityPartition activityPartition = getUMLElement();
		// check host semantic element is not null
		if (activityPartition == null) {
			return;
		}
		getDiagramEventBroker().removeNotificationListener(activityPartition, this);
		Element el = activityPartition.getRepresents();
		if (el != null) {
			getDiagramEventBroker().removeNotificationListener(el, this);
		}
	}


	@Override
	public void refreshDisplay() {
		// calls the helper for this edit Part
		ActivityPartitionEditPart lp = (ActivityPartitionEditPart) getHost();
		List children = lp.getChildren();
		for (Object p : children) {
			if (p instanceof ActivityPartitionNameEditPart) {
				ActivityPartitionLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) p);
			}
		}
	}


	@Override
	public Collection<String> getDefaultDisplayValue() {
		return ActivityPartitionLabelHelper.DEFAULT_LABEL_DISPLAY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getMasks() {
		return ActivityPartitionLabelHelper.getInstance().getMasks();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActivityPartition getUMLElement() {
		return (ActivityPartition) hostSemanticElement;

	}

}
