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
package org.eclipse.papyrus.uml.diagram.timing.custom.edit.policies;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.CompactLifelineCreateCommandCN;
import org.eclipse.papyrus.uml.diagram.timing.custom.edit.commands.FullLifelineCreateCommandCN;
import org.eclipse.papyrus.uml.diagram.timing.custom.utils.LifelineUtils;
import org.eclipse.papyrus.uml.diagram.timing.custom.utils.LifelineUtils.LifelineType;
import org.eclipse.papyrus.uml.diagram.timing.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Lifeline;

public class CustomInteractionCompartmentItemSemanticEditPolicy extends InteractionCompartmentItemSemanticEditPolicyTN {

	@Override
	protected Command getCreateCommand(final CreateElementRequest req) {
		final IElementType elementType = req.getElementType();
		if (UMLElementTypes.Lifeline_FullShape == elementType) {
			return getGEFWrapper(new FullLifelineCreateCommandCN(req, DiagramUtils.getDiagramFrom(getHost())) {

				@Override
				protected void doConfigure(final Lifeline newElement, final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
					super.doConfigure(newElement, monitor, info);
					LifelineUtils.setLifelineType(newElement, LifelineType.full);
				}
			});
		}
		if (UMLElementTypes.Lifeline_CompactShape == elementType) {
			return getGEFWrapper(new CompactLifelineCreateCommandCN(req, DiagramUtils.getDiagramFrom(getHost())) {

				@Override
				protected void doConfigure(final Lifeline newElement, final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
					super.doConfigure(newElement, monitor, info);
					LifelineUtils.setLifelineType(newElement, LifelineType.compact);
				}
			});
		}
		return super.getCreateCommand(req);
	}
}
