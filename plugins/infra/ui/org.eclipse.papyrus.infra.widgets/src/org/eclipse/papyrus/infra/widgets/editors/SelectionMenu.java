/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Shuai Li (CEA LIST) shuai.li@cea.fr - SelectionMenu#refresh method
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * A basic menu which proposes a list of choices.
 *
 * Implementation is based on a JFace TableViewer
 *
 * Typical usage: JDT-like Ctrl + Click (Navigation)
 *
 * @author Camille Letavernier
 *
 */
public class SelectionMenu {

	private ILabelProvider labelProvider;

	private IStructuredContentProvider contentProvider;

	private Shell parentShell;

	private Point location;

	private Object input;

	private Shell shell;

	private TableViewer tableViewer;

	private ISelectionChangedListener selectionChangedListener;

	private List<ISelectionChangedListener> selectionChangedListeners;

	private KeyListener keyListener;

	private List<KeyListener> keyListeners;

	private MouseTrackListener mouseTrackListener;

	private List<MouseTrackListener> mouseTrackListeners;

	public SelectionMenu(Shell parentShell) {
		this(parentShell, parentShell.getDisplay().getCursorLocation());
	}

	/**
	 * @since 2.0
	 */
	public SelectionMenu(Shell parentShell, Object source) {
		if (parentShell != null) {
			if (source instanceof TableViewer) {
				TableViewer tableViewer = (TableViewer) source;

				// Get the cell's y position (we can't use the getCell(Point location) method)
				int selectionIndex = tableViewer.getTable().getSelectionIndex();
				int cellHeight = tableViewer.getTable().getItem(selectionIndex).getBounds().height;
				int y = tableViewer.getTable().getShell().getLocation().y;
				y += selectionIndex * cellHeight;

				// Get the cell's x position and append by the table's width
				// int width= tableViewer.getTable().getSize().x;
				int width = tableViewer.getTable().getShell().getBounds().width;
				int x = tableViewer.getTable().getShell().getLocation().x + width;

				Point location = new Point(x, y);
				init(parentShell, location, -1, 0);
				return;
			}

			init(parentShell, parentShell.getDisplay().getCursorLocation(), 1, 1);
		}
	}

	/**
	 * @since 2.0
	 */
	public SelectionMenu(Shell parentShell, Object source, Point cursorPosition) {
		if (parentShell != null) {
			if (source instanceof Table && cursorPosition != null) {
				Table table = (Table) source;

				TableItem item = table.getItem(cursorPosition);

				if (item != null) {
					int selectionIndex = 0;
					for (Object tableItem : table.getItems()) {
						if (tableItem.equals(item)) {
							break;
						}
						selectionIndex++;
					}

					int cellHeight = item.getBounds().height;
					int y = table.getShell().getLocation().y;
					y += selectionIndex * cellHeight;

					// Get the cell's x position and append by the table's width
					// int width= tableViewer.getTable().getSize().x;
					int width = table.getShell().getBounds().width;
					int x = table.getShell().getLocation().x + width;

					Point location = new Point(x, y);
					init(parentShell, location, -1, 0);
					return;
				}
			}

			init(parentShell, parentShell.getDisplay().getCursorLocation(), 1, 1);
		}
	}

	public SelectionMenu(Shell parentShell, Point location) {
		init(parentShell, location, 1, 1);
	}

	/**
	 * @since 2.0
	 */
	protected void init(Shell parentShell, Point location, int xOffset, int yOffset) {
		// Move the shell so that it doesn't open under the mouse
		// The hovered element can still be selected
		location.x += xOffset;
		location.y += yOffset;

		this.parentShell = parentShell;
		this.location = location;
		labelProvider = new LabelProvider();

		selectionChangedListeners = new LinkedList<ISelectionChangedListener>();
		selectionChangedListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				for (ISelectionChangedListener listener : selectionChangedListeners) {
					listener.selectionChanged(event);
				}
			}
		};

		keyListeners = new LinkedList<KeyListener>();
		keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent event) {
				for (KeyListener listener : keyListeners) {
					listener.keyPressed(event);
				}
			}

			@Override
			public void keyReleased(KeyEvent event) {
				for (KeyListener listener : keyListeners) {
					listener.keyReleased(event);
				}
			}
		};

		mouseTrackListeners = new LinkedList<MouseTrackListener>();
		mouseTrackListener = new MouseTrackListener() {
			@Override
			public void mouseEnter(MouseEvent event) {
				for (MouseTrackListener mouseTrackListener : mouseTrackListeners) {
					mouseTrackListener.mouseEnter(event);
				}
			}

			@Override
			public void mouseExit(MouseEvent event) {
				for (MouseTrackListener mouseTrackListener : mouseTrackListeners) {
					mouseTrackListener.mouseExit(event);
				}
			}

			@Override
			public void mouseHover(MouseEvent event) {
				for (MouseTrackListener mouseTrackListener : mouseTrackListeners) {
					mouseTrackListener.mouseHover(event);
				}
			}
		};
	}

	public void open() {
		// Shell background and background
		shell = new Shell(parentShell, SWT.NONE);
		shell.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		/*
		 * GridLayout gridLayout = new GridLayout(1, false);
		 * gridLayout.marginWidth = 5;
		 * gridLayout.marginHeight = 5;
		 * shell.setLayout(gridLayout);
		 */
		shell.setLayout(new GridLayout(1, false));

		// TableViewer for menu items
		tableViewer = new TableViewer(shell, SWT.NO_SCROLL);
		tableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableViewer.setContentProvider(contentProvider);
		tableViewer.setLabelProvider(labelProvider);
		ColumnViewerToolTipSupport.enableFor(tableViewer);
		tableViewer.setInput(input);

		// Listeners
		tableViewer.addSelectionChangedListener(selectionChangedListener);
		tableViewer.getTable().addKeyListener(keyListener);
		tableViewer.getTable().addMouseTrackListener(mouseTrackListener);

		// Open
		shell.setLocation(location);
		shell.pack();
		shell.open();


	}

	/**
	 * @since 2.0
	 */
	public void refresh() {
		tableViewer.refresh();
		shell.pack();
	}

	public void dispose() {
		if (tableViewer != null) {
			tableViewer.removeSelectionChangedListener(selectionChangedListener);

			if (tableViewer.getTable() != null) {
				tableViewer.getTable().removeKeyListener(keyListener);
			}
		}

		if (shell != null) {
			shell.dispose();
			shell = null;
		}
	}

	public void setContentProvider(IStructuredContentProvider provider) {
		this.contentProvider = provider;
	}

	public void setInput(Object input) {
		this.input = input;
	}

	public void setLabelProvider(ILabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * @since 1.2
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		this.selectionChangedListeners.add(listener);
	}

	/**
	 * @since 2.0
	 */
	public void addKeyListener(KeyListener listener) {
		this.keyListeners.add(listener);
	}

	/**
	 * @since 2.0
	 */
	public void addMouseTrackListener(MouseTrackListener listener) {
		this.mouseTrackListeners.add(listener);
	}

	/**
	 * @since 2.0
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * @since 2.0
	 */
	public void setShell(Shell shell) {
		this.shell = shell;
	}

	/**
	 * @since 2.0
	 */
	public Shell getParentShell() {
		return parentShell;
	}

	/**
	 * @since 2.0
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}
}
