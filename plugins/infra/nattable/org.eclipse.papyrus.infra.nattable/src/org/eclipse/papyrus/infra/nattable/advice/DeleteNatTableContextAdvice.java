/*****************************************************************************
 * Copyright (c) 2013, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 573788
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.advice;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * Destroy the NatTable
 *
 *
 * @author Vincent Lorenzo
 *
 */
public class DeleteNatTableContextAdvice extends AbstractEditHelperAdvice {

	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		final EObject objectToDestroy = request.getElementToDestroy();
		if (objectToDestroy == null) {
			return null;
		}

		final ECrossReferenceAdapter crossReferencerAdapter = ECrossReferenceAdapter.getCrossReferenceAdapter(objectToDestroy);
		if (crossReferencerAdapter != null) {
			final Collection<Setting> settings = crossReferencerAdapter.getNonNavigableInverseReferences(objectToDestroy);
			CompositeCommand cmd = new CompositeCommand(Messages.DeleteNatTableContextAdvice_DestroyNattableCommand);
			for (Setting currentSetting : settings) {
				final EObject currentEObject = currentSetting.getEObject();
				final EStructuralFeature currentfeature = currentSetting.getEStructuralFeature();
				if (currentEObject instanceof Table && currentfeature == NattablePackage.eINSTANCE.getTable_Context()) {
					Optional.ofNullable(request.getDestroyDependentCommand(currentEObject))
							.ifPresent(cmd::add);
				}
			}
			if (!cmd.isEmpty()) {
				return cmd.reduce();
			}
		}
		return null;
	}

}
