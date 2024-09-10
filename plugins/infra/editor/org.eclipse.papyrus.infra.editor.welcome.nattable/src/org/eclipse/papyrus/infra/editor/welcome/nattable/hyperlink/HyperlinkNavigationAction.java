/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.editor.welcome.nattable.hyperlink;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.selection.action.AbstractMouseSelectionAction;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.editor.welcome.nattable.ServiceConfigAttributes;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.swt.events.MouseEvent;

/**
 * A NatTable action for navigation of a hyperlink cell.
 */
public class HyperlinkNavigationAction extends AbstractMouseSelectionAction {

	public HyperlinkNavigationAction() {
		super();
	}

	<T> T getService(NatTable natTable, Class<T> serviceType) {
		return ServiceConfigAttributes.getService(serviceType, natTable.getConfigRegistry(), DisplayMode.NORMAL);
	}

	@Override
	public void run(NatTable natTable, MouseEvent event) {
		super.run(natTable, event);

		Object data = natTable.getDataValueByPosition(getGridColumnPosition(), getGridRowPosition());
		if (data instanceof IObservableValue<?>) {
			data = ((IObservableValue<?>) data).getValue();
		}

		IPageManager pages = getService(natTable, IPageManager.class);
		if (pages.allPages().contains(data)) {
			if (pages.isOpen(data)) {
				pages.selectPage(data);
			} else {
				pages.openPage(data);
			}
		} else if (data != null) {
			getService(natTable, NavigationService.class).navigate(data);
			getService(natTable, ISelectionProvider.class).setSelection(new StructuredSelection(data));
		}
	}

}
