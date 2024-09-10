/*****************************************************************************
 * Copyright (c) 2011, 2014 CEA LIST and others.
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

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;

/**
 * A ModelElementFactory for AnnotationModelElements
 *
 * @author Camille Letavernier
 */
public class AnnotationModelElementFactory extends AbstractModelElementFactory<AnnotationModelElement> {

	@Override
	protected AnnotationModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		EObject source = EMFHelper.getEObject(sourceElement);
		if (source == null) {
			Activator.log.warn("Unable to resolve the selected element to an EObject"); //$NON-NLS-1$
			return null;
		}

		if (!(source instanceof EModelElement)) {
			Activator.log.warn("The selected element must be an EModelElement"); //$NON-NLS-1$
		}

		EditingDomain domain = EMFHelper.resolveEditingDomain(source);
		return new AnnotationModelElement((EModelElement) source, domain, context.getName());
	}

	@Override
	protected void updateModelElement(AnnotationModelElement modelElement, Object newSourceElement) {
		EObject eObject = EMFHelper.getEObject(newSourceElement);
		if (!(eObject instanceof EModelElement)) {
			throw new IllegalArgumentException("Cannot resolve EModelElement selection: " + newSourceElement);
		}
		modelElement.source = (EModelElement) eObject;
		modelElement.domain = EMFHelper.resolveEditingDomain(eObject);
	}
}
