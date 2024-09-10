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

import java.util.function.Predicate;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.IMouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.papyrus.infra.editor.welcome.nattable.painter.LabelPainter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

/**
 * A NatTable configuration that installs hyperlink navigation cursor feed-back and
 * actions on the cells of a table that have hyperlinks.
 */
public class HyperlinkNavigationConfiguration<E> extends AbstractUiBindingConfiguration {
	private final Class<? extends E> elementType;
	private final Predicate<? super E> isActiveHyperlink;

	private Cursor defaultCursor;
	private Cursor handCursor;

	private int currentColumn = -1;
	private int currentRow = -1;
	private Rectangle currentCellTextBounds;

	/**
	 * Initializes me.
	 * 
	 * @param owner
	 *            the control that owns the {@link NatTable} that I will configure
	 * @param elementType
	 *            the type of object that is a potential hyperlink
	 * @param isActiveHyperlink
	 *            a predicate matching valid, navigable hyperlink elements
	 */
	public HyperlinkNavigationConfiguration(Control owner, Class<? extends E> elementType, Predicate<? super E> isActiveHyperlink) {
		super();

		this.elementType = elementType;
		this.isActiveHyperlink = isActiveHyperlink;

		this.defaultCursor = owner.getCursor();
		this.handCursor = new Cursor(owner.getDisplay(), SWT.CURSOR_HAND);

		owner.addDisposeListener(event -> handCursor.dispose());
	}

	@Override
	public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
		uiBindingRegistry.registerSingleClickBinding(
				MouseEventMatcher.bodyLeftClick(SWT.NONE),
				new HyperlinkNavigationAction());

		// On moving the mouse into the body area, show a navigation cursor
		uiBindingRegistry.registerFirstMouseMoveBinding(
				new IMouseEventMatcher() {
					@Override
					public boolean matches(NatTable natTable, MouseEvent event, LabelStack regionLabels) {
						int col = natTable.getColumnPositionByX(event.x);
						int row = natTable.getRowPositionByY(event.y);

						return (col >= 0) && (row >= 0);
					}

				}, this::updateCursor);

		// On moving the mouse out of the body area, restore the usual cursor
		uiBindingRegistry.registerMouseMoveBinding(new IMouseEventMatcher() {
			@Override
			public boolean matches(NatTable natTable, MouseEvent event, LabelStack regionLabels) {
				return (natTable != null && regionLabels == null);
			}

		}, this::reset);

		// And same if it is moved out of the table altogether
		uiBindingRegistry.registerMouseExitBinding(new IMouseEventMatcher() {
			@Override
			public boolean matches(NatTable natTable, MouseEvent event, LabelStack regionLabels) {
				return true;
			}

		}, this::reset);
	}

	void updateCursor(NatTable table, MouseEvent event) {
		int col = table.getColumnPositionByX(event.x);
		int row = table.getRowPositionByY(event.y);
		Object data = (col >= 0) && (row >= 0) ? table.getDataValueByPosition(col, row) : null;

		if (!elementType.isInstance(data) || !isActiveHyperlink.test(elementType.cast(data))) {
			// not a havigable hyperlink
			table.setCursor(defaultCursor);
		} else {
			// are we over text?
			if ((currentCellTextBounds == null) || (col != currentColumn) || (row != currentRow)) {
				updateCellTextBounds(table, col, row);
			}

			if (currentCellTextBounds != null && currentCellTextBounds.contains(event.x, event.y)) {
				table.setCursor(handCursor);
			} else {
				table.setCursor(defaultCursor);
			}
		}
	}

	void reset(NatTable table, MouseEvent event) {
		currentColumn = -1;
		currentRow = -1;
		currentCellTextBounds = null;

		table.setCursor(defaultCursor);
	}

	void updateCellTextBounds(NatTable table, int column, int row) {
		currentColumn = column;
		currentRow = row;
		currentCellTextBounds = null;

		ILayerCell cell = table.getCellByPosition(column, row);
		if (cell != null) {
			ICellPainter painter = table.getCellPainter(column, row, cell, table.getConfigRegistry());
			if (painter instanceof LabelPainter) {
				GC gc = new GC(table.getDisplay());
				try {
					currentCellTextBounds = ((LabelPainter) painter).getTextBounds(cell, gc, table.getConfigRegistry());
				} finally {
					gc.dispose();
				}
			} else {
				// Assume the whole cell is text, then
				currentCellTextBounds = cell.getBounds();
			}
		}
	}
}
