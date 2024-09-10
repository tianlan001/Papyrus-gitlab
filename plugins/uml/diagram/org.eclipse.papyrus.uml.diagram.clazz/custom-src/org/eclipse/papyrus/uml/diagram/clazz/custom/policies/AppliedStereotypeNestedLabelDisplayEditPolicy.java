package org.eclipse.papyrus.uml.diagram.clazz.custom.policies;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.helper.NestedStereotypeLabelHelper;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy;

/**
 *
 *
 */
public class AppliedStereotypeNestedLabelDisplayEditPolicy extends AppliedStereotypeLabelDisplayEditPolicy {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy#refreshStereotypeDisplay()
	 *
	 */
	@Override
	protected void refreshStereotypeDisplay() {
		NestedStereotypeLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) getHost());
	}
}
