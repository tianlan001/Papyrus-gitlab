/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.groups.tabbedproperties.appearance;

import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractNotationPropertiesSection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * Section to use in the properties view. This section enables to choose which of the possible parents graphically contains the selected node.
 *
 * @author vhemery
 */
public class ChooseGraphicalParentSection extends AbstractNotationPropertiesSection {

	/** The switch image. */
	public static Image switchImage = null;

	/** Load the switch icon once */
	static {
		// //load images
		// try {
		// switchImage = new Image(Display.getDefault(), Activator.getDefault().getBundle().getResource(ICON_PATH).openStream());
		// } catch (IOException e) {
		// Activator.getDefault().getLog().log(new Status(Status.WARNING, Activator.PLUGIN_ID, e.getMessage(), e));
		// }
	}

	/**
	 * Create controls to enable direction switch
	 */
	@Override
	public void initializeControls(Composite parent) {
		super.initializeControls(parent);
	}
}
