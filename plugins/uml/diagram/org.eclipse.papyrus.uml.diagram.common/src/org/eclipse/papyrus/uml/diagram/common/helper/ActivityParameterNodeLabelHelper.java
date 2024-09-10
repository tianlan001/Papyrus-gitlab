/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.State;


public class ActivityParameterNodeLabelHelper extends StereotypedElementLabelHelper {

	/**
	 * String for separate masked label part
	 */
	private static final String SEPARATOR = ": ";

	private static final String STATE_SEPARATOR = ", ";

	private static final String START_STATE_SEQUENCE = "[";

	private static final String END_STATE_SEQUENCE = "]";

	private static ActivityParameterNodeLabelHelper labelHelper;

	/**
	 * Returns the singleton instance of this class
	 *
	 * @return the singleton instance.
	 */
	public static ActivityParameterNodeLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new ActivityParameterNodeLabelHelper();
		}
		return labelHelper;
	}

	/** Map for masks */
	protected final Map<String, String> masks = new HashMap<>();

	/**
	 * Creates a new {@link ActivityParameterNode} label helper.
	 */
	protected ActivityParameterNodeLabelHelper() {
		// initialize the map
		masks.put(ICustomAppearance.DISP_NAME, "Name");
		masks.put(ICustomAppearance.DISP_PARAMETER_NAME, "Parameter Name");
		masks.put(ICustomAppearance.DISP_TYPE, "Type");
		masks.put(ICustomAppearance.DISP_STATE, "States");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String elementLabel(GraphicalEditPart editPart) {
		Collection<String> displayValue = ICustomAppearance.DEFAULT_UML_ACTIVITYPARAMETERNODE;

		IMaskManagedLabelEditPolicy policy = (IMaskManagedLabelEditPolicy) editPart.getParent().getEditPolicy(IMaskManagedLabelEditPolicy.MASK_MANAGED_LABEL_EDIT_POLICY);
		if (policy != null) {
			displayValue = policy.getCurrentDisplayValue();
		}
		return parse(displayValue, getUMLElement(editPart));
	}

	public Map<String, String> getMasks() {
		return masks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActivityParameterNode getUMLElement(GraphicalEditPart editPart) {
		return (ActivityParameterNode) ((View) editPart.getModel()).getElement();
	}

	protected String parse(Collection<String> values, ActivityParameterNode node) {
		StringBuilder result = new StringBuilder();
		if (node == null) {
			return "";
		}
		// building label value take from
		// org.eclipse.papyrus.uml.diagram.activity.parser.custom.ActivityParameterNodeParser#getPrintString
		if (values.contains(ICustomAppearance.DISP_NAME)) {
			appendName(result, node);
		}
		if (values.contains(ICustomAppearance.DISP_PARAMETER_NAME) || result.length() == 0) {
			appendName(result, node.getParameter());
		}
		if (values.contains(ICustomAppearance.DISP_TYPE)) {
			appendName(result, node.getType());
		}
		if (values.contains(ICustomAppearance.DISP_STATE)) {
			StringBuffer stateLabel = new StringBuffer();
			for (State state : node.getInStates()) {
				String stateName = UMLLabelInternationalization.getInstance().getLabel(state);
				if (stateName != null && stateName.length() > 0) {
					if (stateLabel.length() > 0) {
						stateLabel.append(STATE_SEPARATOR);
					}
					stateLabel.append(stateName);
				}
			}
			if (stateLabel.length() > 0) {
				if (result.length() > 0) {
					result.append(System.getProperty("line.separator"));
				}
				result.append(START_STATE_SEQUENCE).append(stateLabel).append(END_STATE_SEQUENCE);
			}
		}
		return result.toString();
	}

	private void appendName(StringBuilder builder, NamedElement element) {
		if (element == null) {
			return;
		}
		final String name = UMLLabelInternationalization.getInstance().getLabel(element);
		if (name != null && name.length() > 0) {
			if (builder.length() > 0) {
				builder.append(SEPARATOR);
			}
			builder.append(name);
		}
	}
}
