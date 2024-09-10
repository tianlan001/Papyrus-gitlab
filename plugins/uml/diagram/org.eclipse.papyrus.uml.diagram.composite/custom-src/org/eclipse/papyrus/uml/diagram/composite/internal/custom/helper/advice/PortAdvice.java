/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.internal.custom.helper.advice;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Advice for the visualization of ports when their semantics change.
 */
public class PortAdvice extends AbstractEditHelperAdvice {

	/**
	 * When a port changes from non-conjugated to conjugated, ensure that the
	 * default CSS styling will show this state.
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		ICommand result = null;
		Port port = (Port) request.getElementToEdit();

		if ((request.getFeature() == UMLPackage.Literals.PORT__IS_CONJUGATED)
				&& !port.isConjugated() && Boolean.TRUE.equals(request.getValue())) {

			// All shapes visualizing it should have their fill colour unset to
			// allow the CSS to present it properly
			result = EMFHelper.getUsages(port).stream()
					.filter(s -> s.getEStructuralFeature() == NotationPackage.Literals.VIEW__ELEMENT)
					.map(EStructuralFeature.Setting::getEObject)
					.filter(Shape.class::isInstance).map(Shape.class::cast)
					.filter(s -> PortEditPart.VISUAL_ID.equals(s.getType()))
					.filter(s -> s.eIsSet(NotationPackage.Literals.FILL_STYLE__FILL_COLOR))
					.map(s -> createUnsetFillColorCommand(s, request.getClientContext()))
					.reduce(ICommand::compose)
					// Maybe there are no views of this port that have the fill color set
					.orElse(null);
		}

		return result;
	}

	protected ICommand createUnsetFillColorCommand(FillStyle fillStyle, IClientContext context) {
		// Papyrus doesn't have proper edit helpers for the notation, so
		// we can't get an advisable command for this
		EditingDomain domain = EMFHelper.resolveEditingDomain(fillStyle);
		Command result = SetCommand.create(domain,
				fillStyle,
				NotationPackage.Literals.FILL_STYLE__FILL_COLOR,
				SetCommand.UNSET_VALUE);
		return ICommandWrapper.wrap(result, ICommand.class);
	}
}
