/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.stereotype.edition.provider;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.service.ProviderServiceUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.AbstractCommentEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeMultilinePropertyEditPart;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpolicies.AppliedStereotypeCommentEditPolicy;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpolicies.AppliedStereotypeCompartmentEditPolicy;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;

/**
 * this is an editpolicy provider in charge to install a policy to create a AssociationClass
 *
 * @author Patrick Tessier
 */
public class CustomEditPolicyProvider extends AbstractProvider implements IEditPolicyProvider {

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void createEditPolicies(EditPart editPart) {
		if (editPart instanceof IPapyrusEditPart) {
			if (!(editPart instanceof AppliedStereotypeMultilinePropertyEditPart)) {

				if (editPart instanceof IPrimaryEditPart) {
					if (UMLUtil.resolveUMLElement(editPart) != null) {
						editPart.installEditPolicy(AppliedStereotypeCommentEditPolicy.APPLIED_STEREOTYPE_COMMENT, new AppliedStereotypeCommentEditPolicy());
					}

				}
				if (editPart instanceof NamedElementEditPart || editPart instanceof AbstractCommentEditPart) {
					editPart.installEditPolicy(AppliedStereotypeCompartmentEditPolicy.STEREOTYPE_COMPARTMENT_POLICY, new AppliedStereotypeCompartmentEditPolicy());
				}
				if (editPart instanceof NodeEditPart) {
					NodeEditPart nodeEP = (NodeEditPart) editPart;
					if (nodeEP.getPrimaryShape() instanceof RoundedCompartmentFigure) {
						editPart.installEditPolicy(AppliedStereotypeCompartmentEditPolicy.STEREOTYPE_COMPARTMENT_POLICY, new AppliedStereotypeCompartmentEditPolicy());
					}
				}
			}
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public boolean provides(IOperation operation) {
		CreateEditPoliciesOperation epOperation = (CreateEditPoliciesOperation) operation;
		if (!ProviderServiceUtil.isEnabled(this, epOperation.getEditPart())) {
			return false;
		}

		if (!(epOperation.getEditPart() instanceof GraphicalEditPart) && !(epOperation.getEditPart() instanceof ConnectionEditPart)) {
			return false;
		}

		EditPart gep = epOperation.getEditPart();

		try {
			if (ServiceUtilsForEditPart.getInstance().getServiceRegistry(gep) == null) {
				// Not a Papyrus element
				return false;
			}
		} catch (ServiceException e) {
			// Not a Papyrus element
			return false;
		}

		if (!(gep instanceof AppliedStereotypeMultilinePropertyEditPart)) {

			if (gep instanceof IPrimaryEditPart) {
				if (UMLUtil.resolveUMLElement(gep) != null) {
					return true;
				}

			}
			if (gep instanceof NamedElementEditPart) {
				return true;
			}
		}

		return false;
	}


}
