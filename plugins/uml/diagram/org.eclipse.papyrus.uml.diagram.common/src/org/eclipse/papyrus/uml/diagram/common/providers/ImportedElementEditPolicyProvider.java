/*****************************************************************************
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
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.providers;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.ExternalReferenceEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.ExternalReferenceEditPolicyProvider;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ImportedElementEditPolicy;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;

/**
 * UML-Specific implementation of the ExternalReferenceEditPolicyProvider
 *
 * @author Camille Letavernier
 *
 */
public class ImportedElementEditPolicyProvider extends ExternalReferenceEditPolicyProvider {

	@Override
	public boolean provides(IOperation operation) {
		if (super.provides(operation)) {
			CreateEditPoliciesOperation createOperation = (CreateEditPoliciesOperation) operation;
			return UMLUtil.resolveUMLElement(createOperation.getEditPart()) != null;
		}

		return false;
	}

	@Override
	public void createEditPolicies(EditPart editPart) {
		editPart.installEditPolicy(ExternalReferenceEditPolicy.EDIT_POLICY_ROLE, new ImportedElementEditPolicy());
	}

}
