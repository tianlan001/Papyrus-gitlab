/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 426732
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.UMLPackage;

public final class GateUtils {

	private GateUtils() {
		// utility class
	}

	/**
	 * Get all elements to delete when deleting a Gate.
	 *
	 * @param gate
	 *            the Gate being deleted
	 * @return the elements (EObjects and Views) that should be deleted together with the Gate
	 */
	public static Collection<EObject> getElementsToDelete(final Gate gate) {
		final Set<EObject> elementsToDestroy = new HashSet<>();
		final Collection<Setting> settings = EMFHelper.getUsages(gate);
		for (final Setting setting : settings) {
			// delete messages originating from or going to that gate
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getMessage_SendEvent() || setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getMessage_ReceiveEvent()) {
				elementsToDestroy.add(setting.getEObject());
			}
		}
		return elementsToDestroy;
	}
}
