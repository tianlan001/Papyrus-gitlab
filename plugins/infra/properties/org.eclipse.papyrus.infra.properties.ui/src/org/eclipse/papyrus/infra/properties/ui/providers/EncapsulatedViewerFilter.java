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
package org.eclipse.papyrus.infra.properties.ui.providers;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.widgets.providers.UnchangedObject;
import org.eclipse.papyrus.infra.widgets.providers.UnsetObject;


public class EncapsulatedViewerFilter extends ViewerFilter {

	private ViewerFilter viewerFilter;

	public EncapsulatedViewerFilter(ViewerFilter encapsulated) {
		this.viewerFilter = encapsulated;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element == UnsetObject.instance || element == UnchangedObject.instance) {
			return true;
		}
		return viewerFilter.select(viewer, parentElement, element);
	}

}
