/*****************************************************************************
 * Copyright (c) 2012, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 469188
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.IFilteredLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;


/**
 * A LabelProvider contribution to handle Workspace elements
 *
 * @author Camille Letavernier
 */
public class WorkbenchFilteredLabelProvider extends LabelProvider implements IFilteredLabelProvider {

	private final ILabelProvider workbenchLabelProvider;

	public WorkbenchFilteredLabelProvider() {
		workbenchLabelProvider = WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();
	}

	@Override
	public String getText(Object element) {
		return workbenchLabelProvider.getText(unwrapSelection(element));
	}

	@Override
	public Image getImage(Object element) {
		return workbenchLabelProvider.getImage(unwrapSelection(element));
	}

	@Override
	public boolean accept(Object element) {
		return unwrapSelection(element) instanceof IResource;
	}

	/**
	 * Unwraps a single selection to get the element inside it.
	 * 
	 * @param selection
	 * @return
	 */
	Object unwrapSelection(Object possibleSelection) {
		Object result = possibleSelection;

		if (possibleSelection instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) possibleSelection;
			if (selection.size() == 1) {
				result = selection.getFirstElement();
			}
		}

		return result;
	}
}
