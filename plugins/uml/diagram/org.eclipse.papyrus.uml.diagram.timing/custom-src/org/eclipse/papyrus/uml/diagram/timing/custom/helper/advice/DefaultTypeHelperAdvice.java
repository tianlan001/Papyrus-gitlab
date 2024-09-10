/*******************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.helper.advice;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.timing.custom.utils.ViewUtils;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantVerticalLineEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimingDiagramEditPart;

/**
 * This HelperAdvice disapproves destroy requests for VerticalLines.
 *
 * @since 1.3
 */
public class DefaultTypeHelperAdvice extends AbstractEditHelperAdvice {

	private static String VERTICAL_LINE_ID = FullStateInvariantVerticalLineEditPart.VISUAL_ID;

	@Override
	public boolean approveRequest(final IEditCommandRequest request) {
		if (request instanceof DestroyElementRequest) {
			final DestroyElementRequest destroyElementRequest = (DestroyElementRequest) request;
			final EObject elementToDestroy = destroyElementRequest.getElementToDestroy();
			if (elementToDestroy instanceof View) {
				final View view = (View) elementToDestroy;
				if (VERTICAL_LINE_ID.equals(view.getType()) && TimingDiagramEditPart.MODEL_ID.equals(ViewUtils.getContainingDiagramType(view))) {
					return false;
				}
			}
		}
		return super.approveRequest(request);
	}

}
