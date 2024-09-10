/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *  Christian W. Damus (CEA) - bug 417409
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * A partial implementation of a ModelElementFactory for creating {@link EMFModelElement}s
 *
 * @author Camille Letavernier
 *
 */
public abstract class AbstractEMFModelElementFactory<T extends EMFModelElement> extends AbstractModelElementFactory<T> {

	@Override
	protected void updateModelElement(T modelElement, Object newSourceElement) {
		EObject eObject = EMFHelper.getEObject(newSourceElement);
		if (eObject == null) {
			throw new IllegalArgumentException("Cannot resolve EObject selection: " + newSourceElement);
		}

		updateEMFModelElement(modelElement, eObject);
	}

	public static void updateEMFModelElement(EMFModelElement modelElement, EObject newEObject) {
		modelElement.source = newEObject;
		modelElement.domain = EMFHelper.resolveEditingDomain(newEObject);
	}
}
