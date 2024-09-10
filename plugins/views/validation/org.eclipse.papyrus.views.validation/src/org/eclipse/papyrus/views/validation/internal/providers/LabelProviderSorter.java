/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.validation.internal.providers;

import static com.google.common.base.Strings.nullToEmpty;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

/**
 * This is the LabelProviderSorter type. Enjoy.
 */
public class LabelProviderSorter
		extends ViewerComparator {

	private final ILabelProvider labelProvider;

	public LabelProviderSorter(ILabelProvider labelProvider) {
		super();

		this.labelProvider = labelProvider;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		String label1 = nullToEmpty(labelProvider.getText(e1));
		String label2 = nullToEmpty(labelProvider.getText(e2));

		return getComparator().compare(label1, label2);
	}

}
