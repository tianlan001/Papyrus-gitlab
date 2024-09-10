/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.helper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IndirectMaskLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.Messages;
import org.eclipse.papyrus.uml.diagram.common.helper.StereotypedElementLabelHelper;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.TypeUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Pin;
import org.eclipse.uml2.uml.TypedElement;

/**
 * Helper for labels displaying {@link Pin}
 */
public class PinLabelHelper extends StereotypedElementLabelHelper {

	private static PinLabelHelper labelHelper;

	private final Map<String, String> masks = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	public static PinLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new PinLabelHelper();
		}
		return labelHelper;
	}


	private PinLabelHelper() {
		masks.put(ICustomAppearance.DISP_TYPE, Messages.ICustomAppearance_PIN_DISP_TYPE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String elementLabel(GraphicalEditPart editPart) {
		IMaskManagedLabelEditPolicy policy = (IMaskManagedLabelEditPolicy) editPart.getEditPolicy(IMaskManagedLabelEditPolicy.MASK_MANAGED_LABEL_EDIT_POLICY);
		if (policy == null) {
			policy = (IMaskManagedLabelEditPolicy) editPart.getEditPolicy(IndirectMaskLabelEditPolicy.INDRIRECT_MASK_MANAGED_LABEL);
		}
		Collection<String> displayValue = Collections.emptySet();
		if (policy != null) {
			displayValue = policy.getCurrentDisplayValue();
		}
		NamedElement namedElement = getUMLElement(editPart);
		return namedElement != null ? getCustomLabel(namedElement, displayValue) : "";
	}

	/**
	 * {@inheritDoc}
	 */
	private String getCustomLabel(NamedElement namedElement, Collection<String> maskValues) {
		StringBuffer buffer = new StringBuffer();
		if (maskValues.contains(ICustomAppearance.DISP_NAME)) {
			buffer.append(" ");
			String name = UMLLabelInternationalization.getInstance().getLabel(namedElement);
			buffer.append(null == name ? "" : name);
		}
		if (namedElement instanceof TypedElement) {
			if (maskValues.contains(ICustomAppearance.DISP_TYPE)) {
				if (((TypedElement) namedElement).getType() != null) {
					buffer.append(": " + UMLLabelInternationalization.getInstance().getLabel(((TypedElement) namedElement).getType()));
				} else {
					buffer.append(": " + TypeUtil.UNDEFINED_TYPE_NAME);
				}
			}
		}
		return buffer.toString().trim();
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, String> getMasks() {
		return masks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pin getUMLElement(GraphicalEditPart editPart) {
		Element element = super.getUMLElement(editPart);
		return element instanceof Pin ? (Pin) element : null;
	}
}
