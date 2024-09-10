/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.infra.gmfdiag.dnd.policy;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;


public class CustomizableDropEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	@Override
	public boolean provides(IOperation operation) {
		CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;
		if (!ProviderServiceUtil.isEnabled(this, epOperation.getEditPart())) {
			return false;
		}

		try {
			ServicesRegistry registry = ServiceUtilsForEditPart.getInstance().getServiceRegistry(epOperation.getEditPart());
			return registry != null;
		} catch (ServiceException e) {
			return false;
		}
	}

	@Override
	public void createEditPolicies(EditPart editPart) {
		EditPolicy defaultDropEditPolicy = editPart.getEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE);
		EditPolicy defaultCreationEditPolicy = editPart.getEditPolicy(EditPolicyRoles.CREATION_ROLE);

		CustomizableDropEditPolicy dropEditPolicy = new CustomizableDropEditPolicy(defaultDropEditPolicy, defaultCreationEditPolicy);

		editPart.installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, null);
		editPart.installEditPolicy(EditPolicyRoles.CREATION_ROLE, dropEditPolicy);
	}

}