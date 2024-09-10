/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.navigation.provider;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.gmfdiag.navigation.editpolicy.NavigationEditPolicy;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;


public class NavigationEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	public boolean provides(IOperation operation) {
		if (operation instanceof CreateEditPoliciesOperation) {

			CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;
			EditPart editPart = epOperation.getEditPart();
			if (!ProviderServiceUtil.isEnabled(this, editPart)) {
				return false;
			}
			try {
				NavigationService navigationService = ServiceUtilsForEditPart.getInstance().getService(NavigationService.class, editPart);
				return navigationService != null;
			} catch (Exception ex) {
				// Ignore: The NavigationService is not available; do nothing
			}
		}
		return false;
	}

	public void createEditPolicies(EditPart editPart) {
		editPart.installEditPolicy(NavigationEditPolicy.EDIT_POLICY_ID, new NavigationEditPolicy());
	}
}
