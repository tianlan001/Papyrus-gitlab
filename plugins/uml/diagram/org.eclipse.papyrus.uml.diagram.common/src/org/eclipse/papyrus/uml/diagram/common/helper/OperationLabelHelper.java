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
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) - Bug 359270
 *  Benoît Maggi (CEA LIST) - Bug 516045 ClassCastException when opening a diagram
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.OperationUtil;
import org.eclipse.uml2.uml.Operation;

/**
 * Helper for labels displaying {@link Operation}
 */
public class OperationLabelHelper extends StereotypedElementLabelHelper {

	/**
	 * singleton instance
	 */
	private static OperationLabelHelper labelHelper;

	/**
	 * Returns the singleton instance of this class
	 *
	 * @return the singleton instance.
	 */
	public static OperationLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new OperationLabelHelper();
		}
		return labelHelper;
	}

	/** Map for masks */
	protected final Map<String, String> masks = new HashMap<>();

	/**
	 * Creates a new Operation label helper.
	 */
	protected OperationLabelHelper() {
		// initialize the map
		masks.put(ICustomAppearance.DISP_VISIBILITY, "Visibility");
		masks.put(ICustomAppearance.DISP_NAME, "Name");
		masks.put(ICustomAppearance.DISP_PARAMETER_NAME, "Parameters Name");
		masks.put(ICustomAppearance.DISP_PARAMETER_DIRECTION, "Parameters Direction");
		masks.put(ICustomAppearance.DISP_PARAMETER_TYPE, "Parameters Type");
		masks.put(ICustomAppearance.DISP_RT_TYPE, "Return Type");
		masks.put(ICustomAppearance.DISP_RT_MULTIPLICITY, "Return Multiplicity");
		masks.put(ICustomAppearance.DISP_PARAMETER_MULTIPLICITY, "Parameters Multiplicity");
		masks.put(ICustomAppearance.DISP_PARAMETER_DEFAULT, "Parameters Default Value");
		masks.put(ICustomAppearance.DISP_PARAMETER_MODIFIERS, "Parameters Modifiers");
		masks.put(ICustomAppearance.DISP_MODIFIERS, "Modifiers");

	}

	/**
	 * Computes the label that should be displayed by the figure managed by this
	 * edit part.
	 *
	 * @param editPart
	 *            the edit part that controls the {@link Operation} to be
	 *            displayed
	 * @return the label corresponding to the specific display of the property
	 *         ("default" display given by preferences or specific display given
	 *         by eAnnotation).
	 */
	@Override
	protected String elementLabel(GraphicalEditPart editPart) {
		String elementLabel = ""; //$NON-NLS-1$
		Operation umlElement = getUMLElement(editPart);
		if (umlElement != null) {
			Collection<String> displayValue = ICustomAppearance.DEFAULT_UML_OPERATION;
			IMaskManagedLabelEditPolicy policy = (IMaskManagedLabelEditPolicy) editPart.getEditPolicy(IMaskManagedLabelEditPolicy.MASK_MANAGED_LABEL_EDIT_POLICY);
			if (policy != null) {
				displayValue = policy.getCurrentDisplayValue();
			}
			elementLabel = OperationUtil.getCustomLabel(umlElement, displayValue);
		}
		return elementLabel;
	}

	/**
	 * Returns the map of masks used to display an {@link Operation}
	 *
	 * @return the {@link Map} of masks used to display a {@link Operation}
	 */
	public Map<String, String> getMasks() {
		return masks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operation getUMLElement(GraphicalEditPart editPart) {
		Operation operation = null;
		Object model = editPart.getModel();
		if (model instanceof View) {
			View view = (View) model;
			EObject element = view.getElement();
			if (element instanceof Operation) {
				operation = (Operation) element;
			} else {
				Activator.log.warn("Operation Label Helper should only be applied on operation. " //$NON-NLS-1$
						+ "Probably something else is present in the operation compartment, check: " + EcoreUtil.getID(view) + //$NON-NLS-1$
						" or " + EcoreUtil.getIdentification(view)); //$NON-NLS-1$
			}
		}
		return operation;
	}

}
