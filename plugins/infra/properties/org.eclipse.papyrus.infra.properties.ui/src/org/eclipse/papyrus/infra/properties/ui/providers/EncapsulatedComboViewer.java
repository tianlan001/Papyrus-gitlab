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

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ViewerFilter;

@Deprecated
public class EncapsulatedComboViewer extends ComboViewer {

	public EncapsulatedComboViewer(ComboViewer viewer) {
		super(viewer.getCCombo());
		if (viewer.getContentProvider() != null) {
			super.setContentProvider(viewer.getContentProvider());
		}
		if (viewer.getInput() != null) {
			super.setInput(viewer.getInput());
		}
		if (viewer.getLabelProvider() != null) {
			super.setLabelProvider(viewer.getLabelProvider());
		}
		if (viewer.getFilters() != null) {
			super.setFilters(viewer.getFilters());
		}
	}

	@Override
	public void setFilters(ViewerFilter[] filters) {
		for (ViewerFilter filter : filters) {
			addFilter(filter);
		}
	}

	@Override
	public void addFilter(ViewerFilter filter) {
		super.addFilter(new EncapsulatedViewerFilter(filter));
	}
}
