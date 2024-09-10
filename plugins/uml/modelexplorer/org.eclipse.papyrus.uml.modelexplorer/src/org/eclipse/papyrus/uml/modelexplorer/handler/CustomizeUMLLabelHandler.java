/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 500219 - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.handler;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.uml.modelexplorer.Activator;
import org.eclipse.papyrus.uml.modelexplorer.dialogs.CustomizeUMLLabelDialog;
import org.eclipse.papyrus.uml.modelexplorer.preferences.CustomizableLabelPreferences;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerPageBookView;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.navigator.CommonNavigator;


/**
 * Than handler to display dialog to customize UML Label.
 * @since 2.0
 */
public class CustomizeUMLLabelHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		// Create the dialog
		CustomizeUMLLabelDialog dialog = new CustomizeUMLLabelDialog(Display.getDefault().getActiveShell());

		// open it
		int code = dialog.open();
		if (Window.OK == code) {
			Object[] result = dialog.getResult();
			if (0 < result.length && result[0] instanceof Map) {
				Map map = (Map<String, String>) result[0];
				IPreferenceStore store = Activator.getDefault().getPreferenceStore();
				store.setValue(CustomizableLabelPreferences.CUSTOMIZED_TYPES, map.get(CustomizableLabelPreferences.CUSTOMIZED_TYPES).toString());
				store.setValue(CustomizableLabelPreferences.CUSTOMIZED_STYLES, map.get(CustomizableLabelPreferences.CUSTOMIZED_STYLES).toString());
			}

			// Refresh model explorer view
			IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
			if (activePart instanceof ModelExplorerPageBookView) {
				IViewPart page = ((ModelExplorerPageBookView) activePart).getActiveView();
				if (page instanceof CommonNavigator) {
					((CommonNavigator) page).getCommonViewer().refresh();
				}
			}

		}
		return null;
	}


}
