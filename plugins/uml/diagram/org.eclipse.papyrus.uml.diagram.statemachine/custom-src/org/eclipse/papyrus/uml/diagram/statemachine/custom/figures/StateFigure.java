/**
 * Copyright (c) 2014-2017 CEA LIST.
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
 * 	Pauline DEVILLE (CEA LIST): Bug 509015 - [StateMachineDiagram] lacks support for UML 2.5 notation of redefinable elements with isLeaf=true
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.figures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;

/**
 * Represents a classifier.
 */
public class StateFigure extends RoundedCompartmentFigure {

	protected String submachineStateName = "";

	protected boolean isSubmachineState = false;

	/**
	 * The isLeaf feature of the state
	 */
	protected boolean isLeafState = false;

	/** The final label. */
	protected PapyrusWrappingLabel finalLabel;

	private static final String FINAL_LABEL_VALUE = "final";

	/** The State Compartment */
	private static final String STATE_COMPARTMENT = "stateCompartment";

	/** The List of Compartment */
	private static final List<String> COMPARTMENT = new ArrayList<>() {

		private static final long serialVersionUID = 1L;
		{
			add(STATE_COMPARTMENT);
		}
	};

	/**
	 * Default Constructor
	 */
	public StateFigure() {
		this(null);
	}

	/**
	 * Create a new StateMachine figure with the given tag
	 *
	 * @param tagLabel
	 *            a String that will be displayed at the top of the figure
	 */
	public StateFigure(String tagLabel) {
		super(COMPARTMENT, tagLabel);
		createFinalLabel();
	}

	public void setSubmachineStateName(String text) {
		submachineStateName = text;
	}

	public void setIsSubmachineState(boolean b) {
		isSubmachineState = b;
	}

	/**
	 * @param isLeafState
	 *            the isLeafState to set
	 */
	public void setIsLeafState(boolean isLeafState) {
		this.isLeafState = isLeafState;
	}

	/**
	 * Get the state compartment figure
	 *
	 * @return
	 */
	public RectangleFigure getStateCompartmentFigure() {
		return getCompartment(STATE_COMPARTMENT);
	}

	/**
	 * Create the final label in the figure.
	 */
	protected void createFinalLabel() {
		finalLabel = new PapyrusWrappingLabel();
		String textToDisplay = new StringBuffer(CHEVRON).insert(1, FINAL_LABEL_VALUE).toString();
		finalLabel.setText(textToDisplay);
		finalLabel.setOpaque(false);
		finalLabel.setAlignment(namePosition);
		finalLabel.setForegroundColor(getNameLabel().getForegroundColor());
		finalLabel.setFont(getNameLabel().getFont());
		if (isLeafState) {
			getTagLabelContainer().add(finalLabel, getTagLabelConstraint(), getFinalLabelPosition());
		}
	}

	public void restoreFinalLabel() {
		if (isLeafState && finalLabel != null) {
			finalLabel.setOpaque(false);
			finalLabel.setAlignment(namePosition);
			getFinalLabelContainer().add(finalLabel, getFinalLabelConstraint(), getFinalLabelPosition());
		} else {
			removeFinalLabel();
		}
	}

	/**
	 * Create a label that contains the stereotype of the element.
	 */
	protected void removeFinalLabel() {
		if (finalLabel != null) {
			if (getFinalLabelContainer().getChildren().contains(finalLabel)) {
				getFinalLabelContainer().remove(finalLabel);
			}
		}
	}

	/**
	 * @return the position of the label
	 */
	protected int getFinalLabelPosition() {
		int position = 0;
		if (getFinalLabelContainer().equals(getNameLabelContainer())) {
			// located after name label
			position = getNameLabelPosition();
			if (this.nameLabel != null) {
				position++;
			}
		} else if (getFinalLabelContainer().equals(getQualifiedNameLabelContainer())) {
			// located after qualifed name label
			position = getQualifiedNameLabelPosition();
			if (this.qualifiedLabel != null) {
				position++;
			}
		} else if (getFinalLabelContainer().equals(getStereotypePropertiesInBraceLabelContainer())) {
			// located after stereotype properties in brace label
			position = getStereotypePropertiesLabelPosition();
			if (this.stereotypePropertiesInBraceContent != null) {
				position++;
			}
		} else if (getFinalLabelContainer().equals(getStereotypeLabelContainer())) {
			// located after stereotype label
			position = getStereotypeLabelPosition();
			if (this.stereotypesLabel != null) {
				position++;
			}
		} else if (getFinalLabelContainer().equals(getIconLabelContainer())) {
			// located after icon label
			position = getIconLabelPosition();
			if ((this.iconLabel != null) && (this.iconLabel.getIcon() != null)) {
				position++;
			}
		}
		return position;
	}

	/**
	 * Get the constraint for adding the final label.
	 *
	 * @return figure containing the final label
	 */
	protected Object getFinalLabelConstraint() {
		return getDefaultLabelsConstraint();
	}

	/**
	 * Get the container for the final label.
	 *
	 * @return figure containing the final label
	 */
	protected IFigure getFinalLabelContainer() {
		return getDefaultLabelsContainer();
	}

	/**
	 * Helper method to calculate the height of name
	 */
	@Override
	protected int getNameHeight() {
		int nameHeight = super.getNameHeight();
		if (finalLabel != null && getFinalLabelContainer().equals(getNameLabelContainer())) {
			nameHeight += finalLabel.getPreferredSize().height;
		}
		return nameHeight;
	}

}
