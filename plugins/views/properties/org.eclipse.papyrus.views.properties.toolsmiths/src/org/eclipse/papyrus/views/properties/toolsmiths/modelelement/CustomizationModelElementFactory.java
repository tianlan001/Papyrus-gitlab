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
package org.eclipse.papyrus.views.properties.toolsmiths.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.views.properties.toolsmiths.modelelement.CustomizationModelElement;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractEMFModelElementFactory;
import org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElementFactory;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;

/**
 * A Factory for build {@link CustomizationModelElement}s
 *
 * @author Camille Letavernier
 */
public class CustomizationModelElementFactory extends AbstractModelElementFactory<CustomizationModelElement> {

	private static final EMFModelElementFactory emfFactory = new EMFModelElementFactory();

	@Override
	protected CustomizationModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		return new CustomizationModelElement((EMFModelElement) emfFactory.createFromSource(sourceElement, context));
	}

	@Override
	protected void updateModelElement(CustomizationModelElement modelElement, Object newSourceElement) {
		EObject eObject = EMFHelper.getEObject(newSourceElement);
		if (eObject == null) {
			throw new IllegalArgumentException("Cannot resolve EObject selection: " + newSourceElement);
		}

		AbstractEMFModelElementFactory.updateEMFModelElement(modelElement.delegate, eObject);
	}
}
