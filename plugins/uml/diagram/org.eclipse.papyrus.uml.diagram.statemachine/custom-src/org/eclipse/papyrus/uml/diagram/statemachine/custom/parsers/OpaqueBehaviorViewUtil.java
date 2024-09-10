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

package org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.preferences.CSSOptionsConstants;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.preferences.PreferenceConstants;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.tools.utils.OpaqueBehaviorUtil;
import org.eclipse.papyrus.uml.tools.utils.OpaqueExpressionUtil;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;

/**
 * Utility class for opaque behaviors that are partially shown in transitions or
 * entry/exit/do actions
 */
public class OpaqueBehaviorViewUtil {

	public static final String DOTS = "..."; //$NON-NLS-1$
	public static final String PARAM_DOTS = "(...)"; //$NON-NLS-1$
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * Cut a body string after a predefined number of lines (taken from preference store).
	 *
	 * @param body
	 *            the body string
	 * @return
	 */
	public static String cutBodyString(View view, String body) {
		IPreferenceStore preferenceStore = UMLDiagramEditorPlugin.getInstance().getPreferenceStore();
		int prefCutLength = preferenceStore.getInt(PreferenceConstants.BODY_CUT_LENGTH);
		int cutLength = prefCutLength;
		if (view != null) {
			cutLength = NotationUtils.getIntValue(view, CSSOptionsConstants.BODY_CUT_LENGTH, prefCutLength);
		}
		if (cutLength == 0) {
			return DOTS;
		} else {
			int start = 0;
			int newStart = 0;
			while (cutLength > 0) {
				// use "\n" instead of System.lineSeparator, since code embedded into a model
				// might not be destined for the development machine, e.g. contain eventually only
				// \n, although the model is opened on a windows machine.
				newStart = body.indexOf("\n", start); //$NON-NLS-1$
				if (newStart > 0) {
					cutLength--;
					start = newStart + 1;
				} else {
					return body;
				}
			}
			if (newStart > 0) {
				// handle case that \n is preceded by a \r
				if (newStart >= 1 && body.charAt(newStart - 1) == '\r') {
					return body.substring(0, start - 1) + DOTS;
				}
				return body.substring(0, newStart) + DOTS;
			}
			return body;
		}
	}

	/**
	 * Return the body of an opaque behavior. Retrieve the "Natural Language" body with priority,
	 * i.e. return this body if it exists, otherwise return the first body.
	 *
	 * @param exp
	 *            an opaque expression
	 * @return the associated body
	 */
	public static String retrieveBody(View view, OpaqueBehavior ob) {
		String body = OpaqueBehaviorUtil.getBody(ob, "Natural Language"); //$NON-NLS-1$
		if (body.equals(EMPTY_STRING) && ob.getBodies().size() > 0) {
			body = ob.getBodies().get(0);
		}
		return cutBodyString(view, body);
	}

	/**
	 * Return the body of an expression. Retrieve the "Natural Language" body with priority,
	 * i.e. return this body if it exists, otherwise return the first body.
	 *
	 * @param exp
	 *            an opaque expression
	 * @return the associated body
	 */
	public static String retrieveBody(View view, OpaqueExpression exp) {
		String body = OpaqueExpressionUtil.getBodyForLanguage(exp, "Natural Language"); //$NON-NLS-1$
		if (body.equals(EMPTY_STRING)) {
			body = OpaqueExpressionUtil.getBodyForLanguage(exp, null);
		}
		return cutBodyString(view, body);
	}

	/**
	 *
	 * @return true, if the presence of parameters should be indicated by (...)
	 */
	public static boolean displayParamDots(View view) {
		IPreferenceStore preferenceStore = UMLDiagramEditorPlugin.getInstance().getPreferenceStore();
		boolean prefValue = preferenceStore.getBoolean(PreferenceConstants.INDICATE_PARAMETERS);
		return NotationUtils.getBooleanValue(view, CSSOptionsConstants.INDICATE_PARAMETERS, prefValue);
	}
}
