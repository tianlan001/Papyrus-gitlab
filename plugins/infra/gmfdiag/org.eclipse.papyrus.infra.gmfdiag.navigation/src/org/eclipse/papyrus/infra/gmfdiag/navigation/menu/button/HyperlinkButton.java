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

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Image;

public class HyperlinkButton {
	private String _text = new String();
	private String _tooltip = new String();
	private Image _icon = null;
	private IAction _action = null;

	public HyperlinkButton(String text, String tooltip, Image image, IAction action) {
		_text = text;
		_tooltip = tooltip;
		_icon = image;
		_action = action;
	}

	public final Image getIcon() {
		return _icon;
	}

	public final IAction getAction() {
		return _action;
	}

	public final String getToolTip() {
		return _tooltip;
	}

	public final String getText() {
		return _text;
	}

} // end NavigationMenuDescriptor