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
package org.eclipse.papyrus.views.properties.toolsmiths.perspective;

import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * A Factory for instantiating the Customization perspective
 *
 * @author Camille Letavernier
 */
public class CustomizationPerspectiveFactory implements IPerspectiveFactory {

	private final static String PROPERTIES = "org.eclipse.ui.views.PropertySheet"; //$NON-NLS-1$

	private final static String PROJECT_EXPLORER = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.addView(PROJECT_EXPLORER, IPageLayout.LEFT, 0.20f, layout.getEditorArea());
		layout.addView(PROPERTIES, IPageLayout.BOTTOM, 0.7f, layout.getEditorArea());
		layout.addView(Activator.PREVIEW_ID, IPageLayout.BOTTOM, 0.6f, layout.getEditorArea());
	}

}
