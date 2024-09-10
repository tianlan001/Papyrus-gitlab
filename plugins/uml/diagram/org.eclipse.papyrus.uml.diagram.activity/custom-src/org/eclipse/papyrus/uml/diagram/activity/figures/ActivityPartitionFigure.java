/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *	 Arthur Daussy - 372745: [ActivityDiagram] Major refactoring group framework
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.figures;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;

/**
 * Figure an activity partition
 */
public class ActivityPartitionFigure extends RoundedCompartmentFigure {

	private static final String ACTIVITY_CONTENT_COMPARTMENT = "ActivityContentCompartment";

	public ActivityPartitionFigure(List<String> compartmentFigure) {
		super(compartmentFigure);
	}

	public ActivityPartitionFigure() {
		super(Collections.singletonList(ACTIVITY_CONTENT_COMPARTMENT));
	}

	public RectangleFigure getActivityPartitionCompartment() {
		return getCompartment(ACTIVITY_CONTENT_COMPARTMENT);
	}

	public WrappingLabel getPartitionLabel() {
		return getNameLabel();
	}
}
