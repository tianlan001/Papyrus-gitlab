/**
 * Copyright (c) 2015, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 472932
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 525463
 */
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IndirectMaskLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editparts.IFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.UMLTextSelectionEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DurationObservationStereotypeLabelEditPart;

/**
 * The custom edit policy for the duration observation stereotype label.
 */
public class CustomDurationObservationStereotypeLabelEditPart extends DurationObservationStereotypeLabelEditPart implements IFloatingLabelEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 *            The view
	 */
	public CustomDurationObservationStereotypeLabelEditPart(final View view) {
		super(view);
	}

	/**
	 * Redefine the refresh label to get the STEREOTYPE_LABEL_POLICY if exist and don't recalculate the text.
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortAppliedStereotypeEditPart#refreshLabel()
	 */
	@Override
	protected void refreshLabel() {
		EditPolicy maskLabelPolicy = getEditPolicy(IMaskManagedLabelEditPolicy.MASK_MANAGED_LABEL_EDIT_POLICY);
		if (maskLabelPolicy == null) {
			maskLabelPolicy = getEditPolicy(IndirectMaskLabelEditPolicy.INDRIRECT_MASK_MANAGED_LABEL);
		}
		// Bug 472932 : Stereotype is always displayed
		if (maskLabelPolicy == null) {
			maskLabelPolicy = getEditPolicy(AppliedStereotypeLabelDisplayEditPolicy.STEREOTYPE_LABEL_POLICY);
		}
		if (maskLabelPolicy == null) {
			View view = (View) getModel();
			if (view.isVisible()) {
				setLabelTextHelper(getFigure(), getLabelText());
				setLabelIconHelper(getFigure(), getLabelIcon());
			} else {
				setLabelTextHelper(getFigure(), ""); //$NON-NLS-1$
				setLabelIconHelper(getFigure(), null);
			}
		}
		Object pdEditPolicy = getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
		if (pdEditPolicy instanceof UMLTextSelectionEditPolicy) {
			((UMLTextSelectionEditPolicy) pdEditPolicy).refreshFeedback();
		}
		Object sfEditPolicy = getEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE);
		if (sfEditPolicy instanceof UMLTextSelectionEditPolicy) {
			((UMLTextSelectionEditPolicy) sfEditPolicy).refreshFeedback();
		}
	}
}
