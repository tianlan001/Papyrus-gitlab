/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.viewpoints.internal.policy.advice;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * Advice that updates {@linkplain RepresentationKind representations} that are owned
 * by or in the context of some {@link EObject} being edited in a Papyrus model.
 * Currently this implements advice for
 * <ul>
 * <li>{@linkplain #getBeforeDestroyDependentsCommand(DestroyDependentsRequest) destroy-dependents request} on the owner/context object
 * to delete the representation</li>
 * </ul>
 */
public class RepresentationDependencyAdvice extends AbstractEditHelperAdvice {

	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		Collection<EObject> representationsToDelete = new HashSet<>();

		EObject element = request.getElementToDestroy();
		for (EStructuralFeature.Setting setting : EMFHelper.getUsages(element)) {
			EObject owner = setting.getEObject();
			ViewPrototype prototype = ViewPrototype.get(owner);
			if (prototype != null && !prototype.isUnavailable() && element == prototype.getRootOf(owner)) {
				// This is a representation of the object being deleted. Delete it, also.
				// Be careful not to delete a representation if only its logical owner is being
				// deleted, not the 'root' (semantic context) of the representation
				representationsToDelete.add(owner);
			}
		}

		return representationsToDelete.isEmpty() ? null : request.getDestroyDependentsCommand(representationsToDelete);
	}

}
