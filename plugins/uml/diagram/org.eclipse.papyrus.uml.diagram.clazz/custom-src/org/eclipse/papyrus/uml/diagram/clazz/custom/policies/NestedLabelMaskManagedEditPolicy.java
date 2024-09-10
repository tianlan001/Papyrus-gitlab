package org.eclipse.papyrus.uml.diagram.clazz.custom.policies;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.helper.NestedStereotypeLabelHelper;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy;

/**
 *
 *
 */
public class NestedLabelMaskManagedEditPolicy extends AbstractMaskManagedEditPolicy {

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy#getMasks()
	 *
	 * @return
	 */
	@Override
	public Map<String, String> getMasks() {
		return Collections.emptyMap();
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy#refreshDisplay()
	 *
	 */
	@Override
	public void refreshDisplay() {
		NestedStereotypeLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) getHost());
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy#getDefaultDisplayValue()
	 *
	 * @return
	 */
	@Override
	protected Collection<String> getDefaultDisplayValue() {
		return Collections.emptyList();
	}

}
