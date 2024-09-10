/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.figures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AutomaticCompartmentLayoutManager;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;

/**
 * Represents a classifier.
 */
public class StateMachineFigure extends RoundedCompartmentFigure {

	/** The StateMachine Compartment */
	private static final String STATEMACHINE_COMPARTMENT = "stateMachineCompartment";

	/** The List of Compartment */
	private static final List<String> COMPARTMENT = new ArrayList<>() {

		private static final long serialVersionUID = 1L;
		{
			add(STATEMACHINE_COMPARTMENT);
		}
	};

	/**
	 * Default Constructor
	 */
	public StateMachineFigure() {
		this(null);
	}

	/**
	 * Create a new StateMachine figure with the given tag
	 *
	 * @param tagLabel
	 *            a String that will be displayed at the top of the figure
	 */
	public StateMachineFigure(String tagLabel) {
		super(COMPARTMENT, tagLabel);
		((AutomaticCompartmentLayoutManager) getLayoutManager()).setAddExtraHeight(false);
	}

	/**
	 * Get the statemachine compartment figure
	 *
	 * @return
	 */
	public RectangleFigure getStateMachineCompartmentFigure() {
		return getCompartment(STATEMACHINE_COMPARTMENT);
	}

}
