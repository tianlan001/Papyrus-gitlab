/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
package org.eclipse.papyrus.uml.modelexplorer.widgets;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.views.modelexplorer.widgets.MetaclassViewerFilter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;


public class StereotypeViewerFilter extends MetaclassViewerFilter {

	public StereotypeViewerFilter(Object selectedMetaClass) {
		super(selectedMetaClass);
	}

	@Override
	public boolean isVisible(Viewer viewer, Object parentElement, Object element) {

		if (metaclass == null) {
			return false;
		}

		if (metaclass instanceof EClass) {
			return super.isVisible(viewer, parentElement, element);
		}

		if (!(metaclass instanceof Stereotype)) {
			return false;
		}

		Stereotype stereotype = (Stereotype) metaclass;

		if (element instanceof IAdaptable) {
			EObject eObject = (EObject) ((IAdaptable) element).getAdapter(EObject.class);
			if (eObject != null) {
				element = eObject;
			}
		}

		if (element instanceof Element) {
			return !((Element) element).getAppliedSubstereotypes(stereotype).isEmpty();
		}

		return false;
	}

}
