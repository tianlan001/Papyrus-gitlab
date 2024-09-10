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

package org.eclipse.papyrus.infra.gmfdiag.navigation.menu.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;

/**
 * @author Shuai Li (CEA LIST) <shuai.li@cea.fr>
 *
 */
public class SubSelectionMenuLabelProvider extends SelectionMenuLabelProvider {
	public SubSelectionMenuLabelProvider() {
		super();
	}

	@Override
	public String getText(Object element) {
		if (element instanceof NavigableElement) {
			return "in Model Explorer";
		} else if (element instanceof EObject) {
			String name = getName((EObject) element);
			return "in " + name;
		}
		return super.getText(element);
	}

	@Override
	public String getToolTipText(Object element) {
		if (element instanceof NavigableElement) {
			return "Show in Model Explorer";
		} else if (element instanceof EObject) {
			String name = getName((EObject) element);
			return "Show in " + name;
		}
		return super.getToolTipText(element);
	}
}
