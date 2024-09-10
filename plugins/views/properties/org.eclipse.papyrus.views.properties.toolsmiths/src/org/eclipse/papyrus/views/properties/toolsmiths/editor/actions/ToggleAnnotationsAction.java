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
 *  Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.editor.actions;

import org.eclipse.papyrus.views.properties.toolsmiths.editor.UIEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

/**
 * An action to toggle the display of annotations in the <em>Properties Context Editor</em>.
 */
public class ToggleAnnotationsAction extends AbstractToggleHandler {

	public static boolean showAnnotations = false;

	public ToggleAnnotationsAction() {
		super("org.eclipse.papyrus.customization.properties.ToggleAnnotations"); //$NON-NLS-1$
	}

	@Override
	protected void doToggle(IWorkbenchPage page, boolean on) {
		showAnnotations = on;

		IEditorPart editor = page.getActiveEditor();
		if (editor instanceof UIEditor) {
			UIEditor uiEditor = (UIEditor) editor;
			uiEditor.getViewer().refresh();
		}
	}

	@Override
	protected boolean updateState(IWorkbenchPage page) {
		return showAnnotations;
	}

	@Override
	protected boolean initializeFromState(IWorkbenchPage page, boolean on) {
		showAnnotations = on;
		return showAnnotations;
	}

}
