/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.RectangleFigure;

/**
 * Represents an InstanceSpecification.
 */
public class InstanceSpecificationFigure extends RoundedCompartmentFigure {

	/** Slot Compartment */
	private static final String SLOT_COMPARTMENT = "slotCompartment";

	/** List of compartment */
	private static final List<String> COMPARTMENT = new ArrayList<>() {

		private static final long serialVersionUID = -5999996890220382272L;

		{
			add(SLOT_COMPARTMENT);
		}
	};

	/**
	 * Default Constructor
	 */
	public InstanceSpecificationFigure() {
		super(COMPARTMENT);
	}

	/**
	 * Get the slot's compartment figure
	 *
	 * @return
	 */
	public RectangleFigure getSlotCompartmentFigure() {
		return getCompartment(SLOT_COMPARTMENT);
	}

}
