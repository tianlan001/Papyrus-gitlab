/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Ansgar Radermacher (CEA LIST) - Bug 516459: Navigation mechanism with Alt+hover does not work on Linux
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.navigation.menu.listener;

import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.DefaultNavigationMenu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

public class NavigationMenuKeyListener implements KeyListener {
	/**
	 *
	 */
	protected DefaultNavigationMenu navigationMenu;

	/**
	 * Constructor.
	 *
	 * @param navigationMenu
	 */
	public NavigationMenuKeyListener(DefaultNavigationMenu navigationMenu) {
		this.navigationMenu = navigationMenu;
	}

	public void keyPressed(KeyEvent e) {
		// Nothing
		// TODO get the hovered over EObject
	}

	public void keyReleased(KeyEvent e) {
		if (e.keyCode == SWT.CONTROL || e.keyCode == SWT.SHIFT) {
			navigationMenu.exitItem();
		}
	}
} // end NavigationMenuKeyListener