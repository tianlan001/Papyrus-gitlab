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
package org.eclipse.papyrus.views.modelexplorer.widgets;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.providers.AbstractTreeFilter;

/**
 * A class to retain all elements that are instance of the
 * given metaclass.
 *
 * The parent elements of retained elements are also retained.
 *
 * @author Camille Letavernier
 *
 */
public class MetaclassViewerFilter extends AbstractTreeFilter {

	protected Object metaclass;

	public MetaclassViewerFilter(Object selectedMetaClass) {
		this.metaclass = selectedMetaClass;
	}

	@Override
	public boolean isVisible(Viewer viewer, Object parentElement, Object element) {

		if (metaclass == null || !(metaclass instanceof EClass)) {
			return false;
		}

		EClass eClass = (EClass) metaclass;

		if (element instanceof IAdaptable) {
			EObject eObject = (EObject) ((IAdaptable) element).getAdapter(EObject.class);
			if (eObject != null) {
				element = eObject;
			}
		}

		if (element instanceof EObject) {
			return eClass.isInstance(element);
		}

		return false;
	}

}
