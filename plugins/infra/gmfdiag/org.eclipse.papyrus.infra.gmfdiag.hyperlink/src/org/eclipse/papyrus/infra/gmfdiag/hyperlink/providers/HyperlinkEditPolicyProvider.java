/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 451230
 *  Shuai Li
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.hyperlink.providers;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies.HyperlinkNavigationMenuEditPolicy;

/**
 * An EditPolicyProvider for Navigation and Hyperlinks
 *
 * @author Camille Letavernier
 *
 */
public class HyperlinkEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	/**
	 * {@inheritDoc}
	 *
	 * This provider can handle GraphicalEditParts
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof CreateEditPoliciesOperation) {
			CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;
			EditPart editPart = epOperation.getEditPart();
			if (!ProviderServiceUtil.isEnabled(this, editPart)) {
				return false;
			}
			try {
				ServicesRegistry registry = ServiceUtilsForEditPart.getInstance().getServiceRegistry(editPart);
				if (registry == null) {
					// We're not in the Papyrus context
					return false;
				}
			} catch (ServiceException ex) {
				// We're not in the Papyrus context
				// Ignore the exception and do not provide the EditPolicy
				return false;
			}
			return editPart instanceof IGraphicalEditPart;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Installs the Hyperlink and Navigation edit policies
	 */
	@Override
	public void createEditPolicies(EditPart editPart) {
		editPart.installEditPolicy(org.eclipse.papyrus.infra.gmfdiag.navigation.editpolicy.NavigationEditPolicy.EDIT_POLICY_ID, new HyperlinkNavigationMenuEditPolicy());
		editPart.installEditPolicy(org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies.NavigationEditPolicy.NAVIGATION_POLICY, new org.eclipse.papyrus.infra.gmfdiag.hyperlink.editpolicies.NavigationEditPolicy());
	}
}
