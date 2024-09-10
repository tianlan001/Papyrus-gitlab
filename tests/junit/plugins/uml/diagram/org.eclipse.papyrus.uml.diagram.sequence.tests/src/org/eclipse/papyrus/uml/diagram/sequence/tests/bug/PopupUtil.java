/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
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
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus (CEA) - fixing issues in sequence diagram test execution
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;


public class PopupUtil {

	private final AbstractHouseKeeperRule houseKeeper;

	private boolean menuPopup = false;

	private int clickMenuIndex = 0;

	private volatile boolean menuListenerEngaged;

	public PopupUtil(AbstractHouseKeeperRule houseKeeper) {
		this.houseKeeper = houseKeeper;
	}

	public boolean isMenuPopup() {
		return menuPopup;
	}

	public void addMenuListener(int clickIndex) {
		if(!menuListenerEngaged) {
			menuPopup = false;
			clickMenuIndex = clickIndex;
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					Display.getDefault().addFilter(SWT.Show, menuListener);
					Display.getDefault().addFilter(SWT.Hide, menuListener);
				}
			});

			menuListenerEngaged = true;
			
			houseKeeper.cleanUpLater(this, "removeMenuListener"); //$NON-NLS-1$
		}
	}

	public void removeMenuListener() {
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				Display.getDefault().removeFilter(SWT.Show, menuListener);
				Display.getDefault().removeFilter(SWT.Hide, menuListener);
				menuListenerEngaged = false;
				menuPopup = false;
			}
		});
	}

	public void click(final Menu bar) {
		click(bar, 0);
	}

	public void click(final Menu bar, int index) {
		MenuItem[] items = bar.getItems();
		if(items != null && index < items.length) {
			notifyEvent(items[index], SWT.Selection);
		}

		bar.setVisible(false);
		bar.notifyListeners(SWT.Hide, new Event());
		waitForComplete();
	}

	protected void waitForComplete() {
//		boolean run = true;
//		while(run) {
//			try {
//				run = Display.getDefault().readAndDispatch();
//			} catch (Exception e) {
//				run = true;
//			}
//		}
	}

	public void notifyEvent(final Widget menuItem, final int eventType) {
		final Event event = new Event();
		event.time = (int)System.currentTimeMillis();
		event.widget = menuItem;
		event.display = menuItem.getDisplay();
		event.type = eventType;

		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				menuItem.notifyListeners(eventType, event);
			}
		});

		waitForComplete();
	}

	/**
	 * A private class to listen for the show/hide events.
	 */
	private class ShowHideListener implements Listener {

		/**
		 * Handles the event by checking if it is the proper event. If it is a show, then the current context menu is
		 * set. Otherwise it will be set to <code>null</code> if it is a hide event.
		 * 
		 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
		 * @param event
		 *        the event to check.
		 */
		public void handleEvent(Event event) {
			if(!(event.widget instanceof Menu)) {
				return;
			}
			Menu menu = (Menu)event.widget;
			if(hasStyle(menu, SWT.POP_UP)) {
				if(event.type == SWT.Show) {
					currentContextMenu = menu;
					click(menu, clickMenuIndex);
					menuPopup = true;
				} else if((event.type == SWT.Hide) && (menu == currentContextMenu)) {
					currentContextMenu = null;
					menuPopup = false;
				}
			}
		}
	}

	/**
	 * Checks if the widget has the given style.
	 * 
	 * @param w
	 *        the widget.
	 * @param style
	 *        the style.
	 * @return <code>true</code> if the widget has the specified style bit set. Otherwise <code>false</code>.
	 */
	public static boolean hasStyle(final Widget w, final int style) {
		if((w == null) || w.isDisposed()) {
			return false;
		}
		if(style == SWT.NONE) {
			return true;
		}

		final List<Boolean> list = new ArrayList<Boolean>();
		w.getDisplay().syncExec(new Runnable() {


			public void run() {
				list.add((w.getStyle() & style) != 0);
			}
		});
		return list.get(0);
	}

	private Menu currentContextMenu;

	private Listener menuListener = new ShowHideListener();

	private Listener dialogCloseHandler = new Listener() {

		public void handleEvent(Event event) {
			if(event.widget instanceof Shell) {
				Shell shell = (Shell)event.widget;
				Button defaultButton = shell.isDisposed() ? null : shell.getDefaultButton();
				waitForComplete();

				if((defaultButton != null) && !defaultButton.isDisposed()) {
					notifyEvent(defaultButton, SWT.Selection);
				}
			}
		}
	};

	private volatile boolean dialogCloseHandlerEngaged = false;

	public void addDialogCloseHandler() {
		if(!dialogCloseHandlerEngaged) {
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					Display.getDefault().addFilter(SWT.Show, dialogCloseHandler);
					dialogCloseHandlerEngaged = true;
				}
			});
			
			houseKeeper.cleanUpLater(this, "removeDialogCloseHandler"); //$NON-NLS-1$
		}
	}

	public void removeDialogCloseHandler() {
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				Display.getDefault().removeFilter(SWT.Show, dialogCloseHandler);
				dialogCloseHandlerEngaged = false;
			}
		});
	}

	/**
	 * Runs the specified {@code runnable}, ensuring that during its execution any dialogs that are opened are not automatically closed
	 * but are managed from within the {@code runnable}.
	 * 
	 * @param runnable
	 *        a runnable that deliberately opens and manages dialogs
	 */
	public void runWithDialogs(Runnable runnable) {
		if(!dialogCloseHandlerEngaged) {
			// Just run it
			runnable.run();
		} else {
			// First, disengage the dialog close handler
			removeDialogCloseHandler();

			try {
				runnable.run();
			} finally {
				// Re-engage the handler
				addDialogCloseHandler();
			}
		}
	}
}
