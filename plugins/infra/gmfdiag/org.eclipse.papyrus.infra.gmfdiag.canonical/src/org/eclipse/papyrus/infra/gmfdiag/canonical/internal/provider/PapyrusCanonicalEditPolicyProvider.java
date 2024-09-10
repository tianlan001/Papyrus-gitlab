/*****************************************************************************
 * Copyright (c) 2014, 2015, 2023 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 433206
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - bug 577845
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.canonical.internal.provider;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.canonical.editpolicy.PapyrusCanonicalEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;

/**
 * this is an editpolicy provider in charge to install a canonical edit policy on papyrus editpart
 */
public class PapyrusCanonicalEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	@Override
	public void createEditPolicies(EditPart editPart) {
		if (supportsCanonical(editPart)) {
			if (editPart instanceof DiagramEditPart || editPart instanceof CompartmentEditPart || editPart instanceof IBorderedShapeEditPart) {
				editPart.installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CANONICAL_ROLE, new PapyrusCanonicalEditPolicy());
			}
		}
	}

	protected boolean supportsCanonical(EditPart editPart) {
		boolean result = false;

		if (!(editPart instanceof ITextAwareEditPart)) {
			result = (editPart instanceof DiagramEditPart)
					|| (editPart instanceof CompartmentEditPart)
					|| (editPart instanceof IBorderedShapeEditPart);
		}

		return result;
	}

	@Override
	public boolean provides(IOperation operation) {
		CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;

		EditPart gep = epOperation.getEditPart();
		if (!ProviderServiceUtil.isPapyrusPart(gep)) {
			return false;
		}
		// test if the edipart is a papyrus editpart
		try {
			if (ServiceUtilsForEditPart.getInstance().getServiceRegistry(gep) == null) {
				return false;
			}
		} catch (org.osgi.framework.ServiceException ex) {
			return false;
		} catch (ServiceException e) {
			return false;
		}

		// we are sure that this is a papyrus editpart
		return supportsCanonical(gep);
	}


}
