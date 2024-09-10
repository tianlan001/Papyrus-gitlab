/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Transition;

/**
 * Special parser for internal transition
 *
 * @author arthur daussy
 *
 */
public class InternalTransitionParser extends TransitionPropertiesParser {

	@Override
	protected String getValueString(IAdaptable element, int flags) {
		Object obj = element.getAdapter(EObject.class);
		if (obj instanceof View) {
			View view = (View) obj;
			obj = view.getElement();
		}
		if (obj instanceof Transition) {
			Transition tran = (Transition) obj;
			boolean noTrigger = tran.getTriggers() == null || tran.getTriggers().size() == 0;
			boolean noGuard = tran.getGuard() == null;
			boolean noEffect = tran.getEffect() == null;
			if (noEffect && noGuard && noTrigger) {
				return UMLLabelInternationalization.getInstance().getLabel(tran);
			}
		}
		return super.getValueString(element, flags);
	}


}
