/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.toolsmiths.display;

import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.properties.ui.runtime.ViewConstraintEngine;


public class DisplayManager {

	private DisplayManager() {
		// TODO : Use a local constraint engine, and remove the context declaration in plugin.xml
		constraintEngine = PropertiesRuntime.getConfigurationManager().getConstraintEngine();

		// TODO : Add support for local constraint engines in the creation factories
		// constraintEngine = new DefaultViewConstraintEngine();
		// ResourceSet resourceSet = new ResourceSetImpl();
		// URI uri = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/Model/Customization/Customization.ctx", true); //$NON-NLS-1$
		// try {
		// EObject eObject = EMFHelper.loadEMFModel(resourceSet, uri);
		//
		// if(eObject instanceof Context) {
		// constraintEngine.addContext((Context)eObject);
		// } else {
		// Activator.log.warn("Cannot load the plug-in creation wizard UI");
		// }
		// } catch (Exception ex) {
		// Activator.log.error("Cannot load the plug-in creation wizard UI", ex);
		// }
	}

	public static DisplayManager instance = new DisplayManager();

	public ViewConstraintEngine constraintEngine;
}
