/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.navigation.menu.button;

import java.util.List;

import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuButton;
import org.eclipse.swt.graphics.Image;

/**
 * This class is a navigation menu button that contains also a list of
 * elements. These elements should be shown in some sub-menu once an event
 * is fired on the button. The implementation depends on the actual
 * navigation menu.
 *
 */

public class NavigationSubMenuButton extends NavigationMenuButton {
	private List subMenuElements; // View or NavigableElement

	public NavigationSubMenuButton(String label, Image icon, String tooltip, List subMenuElements) {
		super(label, icon, tooltip);
		this.subMenuElements = subMenuElements;
	}

	public List getSubMenuElements() {
		return subMenuElements;
	}

	public void setSubMenuElements(List subMenuElements) {
		this.subMenuElements = subMenuElements;
	}
} // end DynamicSubMenuButton