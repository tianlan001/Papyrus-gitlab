/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 573987
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.editor.actions;

import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * An action to toggle the UIEditor Preview
 *
 * @author Camille Letavernier
 */
public class TogglePreviewAction extends AbstractToggleHandler {

	public TogglePreviewAction() {
		super("org.eclipse.papyrus.customization.properties.TogglePreview"); //$NON-NLS-1$
	}

	@Override
	protected void doToggle(IWorkbenchPage page, boolean on) {

		try {
			if (on) {
				page.showView(Activator.PREVIEW_ID);
			} else {
				IViewReference viewReference = page.findViewReference(Activator.PREVIEW_ID);
				if (viewReference != null) {
					page.hideView(viewReference);
				}
			}
		} catch (PartInitException ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	protected boolean updateState(IWorkbenchPage page) {
		return page.findViewReference(Activator.PREVIEW_ID) != null;
	}

	@Override
	protected boolean initializeFromState(IWorkbenchPage page, boolean on) {
		return page.findViewReference(Activator.PREVIEW_ID) != null;
	}

}
