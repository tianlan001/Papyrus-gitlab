/*****************************************************************************
 * Copyright (c) 2017 Ericsson Communications.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ericsson Communications - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.helper;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionNameEditPart;
import org.eclipse.papyrus.uml.diagram.common.helper.StereotypedElementLabelHelper;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;

/**
 * @since 3.0
 */
public class ActivityPartitionLabelHelper extends StereotypedElementLabelHelper {

	public static final String SHOW_REPRESENT_NAME = "representsName"; //$NON-NLS-1$

	public static final String SHOW_REPRESENT_SELECTOR = "representsSelector"; //$NON-NLS-1$

	public static final String SHOW_REPRESENT_TYPE = "representsType"; //$NON-NLS-1$

	public static final String SHOW_UNDEFINED_TYPE = "undefinedType"; //$NON-NLS-1$

	public static final String SHOW_ACTIVITY_PARTITION_NAME = "activitypartitionName"; //$NON-NLS-1$

	public static final Collection<String> DEFAULT_LABEL_DISPLAY = Arrays.asList(SHOW_REPRESENT_NAME, SHOW_REPRESENT_SELECTOR, SHOW_REPRESENT_TYPE);

	private static final String UNNAMED = "<Unnamed>"; //$NON-NLS-1$


	/**
	 * singleton instance
	 */
	private static ActivityPartitionLabelHelper labelHelper;

	/**
	 * Returns the singleton instance of this class
	 *
	 * @return the singleton instance.
	 */
	public static ActivityPartitionLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new ActivityPartitionLabelHelper();
		}
		return labelHelper;
	}

	/** Map for masks */
	protected final Map<String, String> masks = new HashMap<>();

	/**
	 * Creates a new Operation label helper.
	 */
	protected ActivityPartitionLabelHelper() {
		// initialize the map
		masks.put(SHOW_REPRESENT_NAME, "Show represent name");
		masks.put(SHOW_REPRESENT_SELECTOR, "Show represent selector");
		masks.put(SHOW_REPRESENT_TYPE, "Show represent type");
		masks.put(SHOW_UNDEFINED_TYPE, "Always show undefined type");
		masks.put(SHOW_ACTIVITY_PARTITION_NAME, "Always show activity partition name");
	}

	/**
	 * Returns the map of masks used to display
	 *
	 * @return the map of masks used to display
	 */
	public Map<String, String> getMasks() {
		return masks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActivityPartition getUMLElement(GraphicalEditPart editPart) {
		return (ActivityPartition) UMLUtil.resolveUMLElement(editPart);
	}

	@Override
	protected String elementLabel(GraphicalEditPart editPart) {
		GraphicalEditPart graphicalEditPart = editPart;
		if (editPart instanceof ActivityPartitionNameEditPart) {
			graphicalEditPart = (GraphicalEditPart) editPart.getParent();
		}
		Collection<String> displayValue = DEFAULT_LABEL_DISPLAY;
		IMaskManagedLabelEditPolicy policy = (IMaskManagedLabelEditPolicy) editPart.getEditPolicy(IMaskManagedLabelEditPolicy.MASK_MANAGED_LABEL_EDIT_POLICY);
		if (policy != null) {
			displayValue = policy.getCurrentDisplayValue();
		}
		return getCustomLabel(getUMLElement(graphicalEditPart), displayValue);
	}

	private String getCustomLabel(ActivityPartition activitypartition, Collection<String> displayValue) {
		StringBuilder sb = new StringBuilder();
		appendName(activitypartition, displayValue, sb);
		return sb.toString();
	}

	protected void appendName(ActivityPartition activitypartition, Collection<String> displayValue, StringBuilder sb) {
		Element represents = activitypartition.getRepresents();
		String activitypartitionName = UMLLabelInternationalization.getInstance().getLabel(activitypartition);

		if (represents instanceof NamedElement) {
			activitypartitionName = ((NamedElement) represents).getName();
		}
		appendString(sb, activitypartitionName, UNNAMED);
		return;
	}

	private void appendString(StringBuilder sb, String str, String defaultValue) {
		if (str != null) {
			sb.append(str);
		} else {
			sb.append(defaultValue);
		}
	}

}
