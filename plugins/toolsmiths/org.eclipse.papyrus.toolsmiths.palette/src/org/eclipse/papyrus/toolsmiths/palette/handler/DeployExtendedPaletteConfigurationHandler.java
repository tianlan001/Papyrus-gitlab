/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.palette.handler;

import java.util.Set;

import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.common.core.service.ProviderPriority;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPalettePreferences;
import org.eclipse.ui.XMLMemento;


/**
 * Handler to deploy new configuration of palette based on a XML file
 */
public class DeployExtendedPaletteConfigurationHandler extends AbstractDeployPaletteConfigurationHandler implements IHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IStatus deployPalette(String fileName, String paletteName, String path, ProviderPriority priority, String editorID, Set<String> requiredProfiles) {
		// just add the palette
		PapyrusPalettePreferences.addWorkspaceExtendedPalette(paletteName, paletteName, path, priority, editorID, requiredProfiles);
		return new Status(IStatus.OK, Activator.ID, "The palette configuration has been successfully deployed and activated");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IStatus activatePalette(String fileName, String paletteName, String path, ProviderPriority priority, String editorID, Set<String> requiredProfiles) {
		PapyrusPalettePreferences.addWorkspaceExtendedPalette(paletteName, paletteName, path, priority, editorID, requiredProfiles);
		PapyrusPalettePreferences.changePaletteVisibility(fileName, editorID, true);
		return new Status(IStatus.OK, Activator.ID, "The palette configuration was already deployed, it has just been activated");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected XMLMemento getMemento() {
		return PapyrusPalettePreferences.getExistingWorkspaceExtendedPalettes();
	}
}