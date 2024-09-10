/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.menu;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.IMenuService;

/**
 * This class provides usefull method to register a natTable widget (the current one) in the eclipse context, in order to get it in the handlers.
 * These methods are used by this plugins and by Junit tests
 *
 */
public class MenuUtils {

	/**
	 * Constructor.
	 *
	 */
	private MenuUtils() {
		// to prevent instanciation
	}

	/**
	 * This method allows to register a nattable widget in the eclipse context, in order to get it easily in org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler.setEnabled(Object)
	 * 
	 * @param manager
	 *            the table manager
	 * @param labelStack
	 *            the labelstack owning the region where concerned by the event
	 */
	public static final void registerNatTableWidgetInEclipseContext(final INattableModelManager manager, final LabelStack labelStack) {
		if (null != manager) {
			registerNatTableWidgetInEclipseContext(manager.getAdapter(NatTable.class), labelStack);
		}
	}

	/**
	 * This method allows to register a nattable widget in the eclipse context, in order to get it easily in org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler.setEnabled(Object)
	 * 
	 * @param natTable
	 *            the {@link NatTable} widget
	 * @param labelStack
	 *            the labelstack owning the region where concerned by the event
	 */
	public static final void registerNatTableWidgetInEclipseContext(final NatTable natTable, final LabelStack labelStack) {
		Event e = new Event();
		e.widget = natTable;
		NatEventData natEventData = new NatEventData(natTable, labelStack, 0, 0, new MouseEvent(e));
		registerNatTableWidgetInEclipseContext(natEventData);
	}

	/**
	 * This method allows to register a nattable widget in the eclipse context, in order to get it easily in org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler.setEnabled(Object)
	 * 
	 * @param natTable
	 *            the {@link NatTable} widget
	 */
	public static final void registerNatTableWidgetInEclipseContext(NatEventData natEventData) {
		final IMenuService menuService = PlatformUI.getWorkbench().getService(IMenuService.class);
		final IEvaluationContext state = menuService.getCurrentState();

		// we register the nattable event data here, in order to get i
		state.addVariable(MenuConstants.NAT_EVENT_DATA_KEY, natEventData);
	}
}
