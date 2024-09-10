/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.papyrus.uml.service.types.command.AssociationReorientCommand;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DeploymentTarget;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

public class CommunicationPathEditHelper extends AssociationBaseEditHelper {

	@Override
	protected boolean canCreate(EObject source, EObject target) {
		if (isInvalidEnd(source)) {
			return false;
		}
		if (target == null) {
			return true;
		}
		if (isInvalidEnd(target)) {
			return false;
		}
		return true;
	}

	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		return new AssociationReorientCommand(req) {

			@Override
			protected boolean canReorientSource() {
				return !isInvalidEnd(getNewSource());
			}

			@Override
			protected boolean canReorientTarget() {
				return !isInvalidEnd(getNewTarget());
			}
		};
	}

	@Override
	protected ICommand getCreateRelationshipCommand(CreateRelationshipRequest req) {
		EObject parent = req.getSource();
		while (parent != null && false == parent instanceof Package) {
			parent = parent.eContainer();
		}
		req.setContainer(parent);
		req.setContainmentFeature(UMLPackage.eINSTANCE.getPackage_PackagedElement());
		return super.getCreateRelationshipCommand(req);
	}

	private static boolean isInvalidEnd(Object end) {
		return false == end instanceof DeploymentTarget || false == end instanceof Classifier;
	}
}
