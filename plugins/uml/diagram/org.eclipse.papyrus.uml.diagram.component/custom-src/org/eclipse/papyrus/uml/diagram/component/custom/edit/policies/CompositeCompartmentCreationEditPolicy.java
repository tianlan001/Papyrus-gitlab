/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.edit.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy;
import org.eclipse.uml2.uml.Component;

/**
 * This policy used to reparent in case when semantically child {@link Component} contained in parnet {@link Component}
 * but graphically child {@link Component} contained in another place.
 *
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=479981
 */
public class CompositeCompartmentCreationEditPolicy extends DefaultCreationEditPolicy {

	/**
	 * Reparent only view if child is {@link Component}
	 * if other case
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy#getReparentCommand(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart)
	 */
	@Override
	protected ICommand getReparentCommand(IGraphicalEditPart gep) {
		if (gep.resolveSemanticElement() instanceof Component) {
			return getReparentViewCommand(gep);
		}

		return super.getReparentCommand(gep);
	}

	/**
	 * In case when element is {@link Component} don't check is newContext already contain element
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy#shouldReparent(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean shouldReparent(EObject element, EObject newContext) {
		if (element instanceof Component) {
			return !(element == null || element == newContext || isContainedIn(element, newContext));
		}
		return super.shouldReparent(element, newContext);
	}

	/**
	 * pivate method isContainedIn({@link EObject} element, {@link EObject} newContext) from {@link CreationEditPolicy}
	 */
	private boolean isContainedIn(EObject element, EObject newContext) {
		EObject container = newContext.eContainer();
		while (container != null) {
			if (container.equals(element)) {
				return true;
			}
			container = container.eContainer();
		}
		return false;
	}
}
