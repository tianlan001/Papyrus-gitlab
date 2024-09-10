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

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

/**
 * This is the ProblemCellLabelProvider type. Enjoy.
 */
public class ProblemCellLabelProvider
		extends CellLabelProvider {

	private final ProblemLabelProvider labelProvider;

	public ProblemCellLabelProvider(ProblemLabelProvider labelProvider) {
		super();

		this.labelProvider = labelProvider;
	}

	@Override
	public void update(ViewerCell cell) {
		cell.setText(labelProvider.getText(cell.getElement()));
		cell.setImage(labelProvider.getImage(cell.getElement()));
	}

	@Override
	public String getToolTipText(Object element) {
		return labelProvider.getText(element);
	}
}
