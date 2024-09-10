/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.fonts.internal.addon;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.ui.fonts.Activator;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * An E4 Addon to load additional fonts in SWT. This is required to make sure we
 * have at least one common Font available on all platforms, that we can use as
 * the default for Papyrus.
 * </p>
 */
public class FontAddon {

	@PostConstruct
	public void loadFonts() {
		if (Display.getCurrent() == null) {
			Display.getDefault().asyncExec(this::loadFonts);
			return;
		}

		loadFonts(new String[] { //
				"roboto/Roboto-Regular.ttf", //$NON-NLS-1$
				"roboto/Roboto-Italic.ttf", //$NON-NLS-1$
				"roboto/Roboto-Bold.ttf", //$NON-NLS-1$
				"roboto/Roboto-BoldItalic.ttf", //$NON-NLS-1$
		});
	}

	private void loadFonts(String[] fonts) {
		try {
			URL fontFolder = new URL("platform:/plugin/" + Activator.PLUGIN_ID + "/fonts/"); //$NON-NLS-1$ //$NON-NLS-2$
			for (String font : fonts) {
				URL fontURL = new URL(fontFolder, font);
				URL fontFileURL = FileLocator.toFileURL(fontURL);

				if (fontFileURL == null) {
					Activator.getDefault().getLog()
							.log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, "Unable to find font " + fontURL)); //$NON-NLS-1$
					continue;
				}

				IPath path = new Path(fontFileURL.getFile());
				String osPath = path.toOSString();
				if (!Display.getCurrent().loadFont(osPath)) {
					Activator.getDefault().getLog().log(new Status(IStatus.WARNING, Activator.PLUGIN_ID,
							"Failed to load font " + fontURL + " (Resolved URL: " + osPath + ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					continue;
				}
			}
		} catch (IOException ex) {
			Activator.getDefault().getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to load default fonts", ex)); //$NON-NLS-1$
			return;
		}
	}

}
