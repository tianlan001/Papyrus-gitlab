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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityParameterNodeStreamLabelEditPart;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Show and hide exception label edit policy according to {@link Parameter#isStream()}
 *
 * @since 2.0
 */
public class ShowHideStreamLabelEditPolicy extends AbstractShowHideParameterPropertyEditPolicy {

	public static final String KEY = "ShowHideStreamLabelEditPolicy";

	@Override
	protected void notifyParameter(Notification notification) {
		switch (notification.getFeatureID(Parameter.class)) {
		case UMLPackage.PARAMETER__IS_STREAM:
			refresh();
		}
	}

	@Override
	public void refresh() {
		Parameter parameter = findHostSemantic().getParameter();
		View streamLabel = getLabelView(ActivityParameterNodeStreamLabelEditPart.VISUAL_ID);

		if (streamLabel != null) {

			if (parameter == null || !parameter.isStream()) {
				hideLabelView(streamLabel);
			} else {
				showLabelView(streamLabel);
			}
			getHost().refresh();
		}
	}
}
