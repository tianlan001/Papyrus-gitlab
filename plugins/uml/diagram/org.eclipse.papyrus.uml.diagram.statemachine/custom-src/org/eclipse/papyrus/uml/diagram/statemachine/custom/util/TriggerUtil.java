/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.statemachine.custom.util;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers.OpaqueBehaviorViewUtil;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.ChangeEvent;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.TimeEvent;
import org.eclipse.uml2.uml.TimeExpression;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * @since 3.1
 */
public class TriggerUtil {

	/**
	 * Get the text concerning Trigger
	 *
	 * @param view
	 * @param trigger
	 * @return
	 */
	static public String getTextForTrigger(View view, Trigger trigger) {
		StringBuilder result = new StringBuilder();
		if (trigger != null) {
			Event e = trigger.getEvent();
			if (e instanceof CallEvent) {
				Operation op = ((CallEvent) e).getOperation();
				if (op != null) {
					result.append(UMLLabelInternationalization.getInstance().getLabel(op));
					if ((op.getOwnedParameters().size() > 0) && OpaqueBehaviorViewUtil.displayParamDots(view)) {
						result.append(OpaqueBehaviorViewUtil.PARAM_DOTS);
					}
				} else {
					result.append(UMLLabelInternationalization.getInstance().getLabel((e)));
				}
			} else if (e instanceof SignalEvent) {
				Signal signal = ((SignalEvent) e).getSignal();
				if (signal != null) {
					result.append(UMLLabelInternationalization.getInstance().getLabel(signal));
					if ((signal.getAttributes().size() > 0) && OpaqueBehaviorViewUtil.displayParamDots(view)) {
						result.append(OpaqueBehaviorViewUtil.PARAM_DOTS);
					}
				} else {
					result.append(UMLLabelInternationalization.getInstance().getLabel((e)));
				}
			} else if (e instanceof ChangeEvent) {
				ValueSpecification vs = ((ChangeEvent) e).getChangeExpression();
				String value;
				if (vs instanceof OpaqueExpression) {
					value = OpaqueBehaviorViewUtil.retrieveBody(view, (OpaqueExpression) vs);
				} else {
					value = vs.stringValue();
				}
				result.append(value);
			} else if (e instanceof TimeEvent) {
				TimeEvent timeEvent = (TimeEvent) e;
				// absRelPrefix
				result.append(timeEvent.isRelative() ? "after " : "at "); //$NON-NLS-1$ //$NON-NLS-2$
				// body
				TimeExpression te = timeEvent.getWhen();
				String value;
				if (te != null) {
					ValueSpecification vs = te.getExpr();
					if (vs instanceof OpaqueExpression) {
						value = OpaqueBehaviorViewUtil.retrieveBody(view, (OpaqueExpression) vs);
					} else {
						value = vs.stringValue();
					}
				} else {
					value = "undefined"; //$NON-NLS-1$
				}
				result.append(value);
			} else { // any receive event
				result.append("all"); //$NON-NLS-1$
			}
		}
		return result.toString();
	}

}
