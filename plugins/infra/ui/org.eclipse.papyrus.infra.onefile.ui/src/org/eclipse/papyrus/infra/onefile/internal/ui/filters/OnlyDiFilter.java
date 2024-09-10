/*****************************************************************************
 * Copyright (c) 2011 Atos Origin Integration.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (Atos Origin Integration) tristan.faure@atosorigin.com - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.onefile.internal.ui.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.onefile.utils.OneFileUtils;

/**
 * Filter hiding di files and associated. the filter enables the content
 * provider
 *
 * @author tfaure
 *
 */
public class OnlyDiFilter extends ViewerFilter {

	public static final String FILTER_ID = "org.eclipse.papyrus.infra.onefile.onlyDiFilter";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 *
	 * @Override
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return OneFileUtils.isVisible(element);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#isFilterProperty(java.lang.Object,
	 * java.lang.String)
	 *
	 * @Override
	 */
	@Override
	public boolean isFilterProperty(Object element, String property) {
		return true;
	}

}
