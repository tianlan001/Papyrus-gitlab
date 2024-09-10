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

package org.eclipse.papyrus.infra.services.navigation.service;

import org.eclipse.swt.graphics.Image;

/**
 * This class represents a wrapper that contains info on a button in the navigation menu.
 * It is only a model that contains enough information so the navigation menu can implement
 * the actual button (frontend).
 *
 */

public abstract class NavigationMenuButton {
	protected String label;
	protected Image icon;
	protected String tooltip;

	public NavigationMenuButton(String label, Image icon, String tooltip) {
		this.label = label;
		this.icon = icon;
		this.tooltip =  tooltip;
	}

	public String getLabel() {
		return label;
	}

	public Image getIcon() {
		return icon;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
} // end NavMenuButton