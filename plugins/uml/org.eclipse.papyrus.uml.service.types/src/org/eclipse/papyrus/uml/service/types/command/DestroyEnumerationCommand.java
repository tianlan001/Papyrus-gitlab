/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Bug 496189
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.command;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.commands.DestroyElementPapyrusCommand;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.UMLPackage.Literals;

/**
 * @since 3.1
 */
public class DestroyEnumerationCommand extends DestroyElementPapyrusCommand {

	public DestroyEnumerationCommand(DestroyElementRequest request) {
		super(request);
	}

	@Override
	protected void tearDownIncomingReferences(EObject destructee) {
		Collection<Setting> usages = EMFHelper.getUsages(destructee);

		for (Setting setting : usages) {
			if (setting.getEStructuralFeature() instanceof EReference) {
				EReference eRef = (EReference) setting.getEStructuralFeature();
				if (eRef == Literals.INSTANCE_SPECIFICATION__CLASSIFIER && setting.getEObject() instanceof EnumerationLiteral) {
					// Bug 496189: this feature is not writable for an EnumerationLiteral
					continue;
				}
				if (eRef.isChangeable() && (eRef.isDerived() == false) && (eRef.isContainment() == false) && (eRef.isContainer() == false)) {
					EcoreUtil.remove(setting.getEObject(), eRef, destructee);
				}
			}
		}
	}

}
