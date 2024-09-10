/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.configuration;

import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.resize.action.RowResizeCursorAction;
import org.eclipse.nebula.widgets.nattable.resize.event.RowResizeEventMatcher;
import org.eclipse.nebula.widgets.nattable.resize.mode.RowResizeDragMode;
import org.eclipse.nebula.widgets.nattable.ui.action.ClearCursorAction;
import org.eclipse.nebula.widgets.nattable.ui.action.NoOpMouseAction;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.swt.SWT;

/**
 * Adapted code from DefaultRowResizeBindings. Allow to resize the rows on all regions of the table (corner, body, row header, column header).
 *
 * @author Vincent Lorenzo
 *
 */
public class PapyrusRowResizeBindingsConfiguration extends AbstractUiBindingConfiguration {

	@Override
	public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
		// Mouse move - Show resize cursor
		uiBindingRegistry.registerFirstMouseMoveBinding(new RowResizeEventMatcher(SWT.NONE, GridRegion.COLUMN_HEADER, 0), new RowResizeCursorAction());
		uiBindingRegistry.registerFirstMouseMoveBinding(new RowResizeEventMatcher(SWT.NONE, GridRegion.ROW_HEADER, 0), new RowResizeCursorAction());
		uiBindingRegistry.registerFirstMouseMoveBinding(new RowResizeEventMatcher(SWT.NONE, GridRegion.CORNER, 0), new RowResizeCursorAction());
		uiBindingRegistry.registerFirstMouseMoveBinding(new RowResizeEventMatcher(SWT.NONE, GridRegion.BODY, 0), new RowResizeCursorAction());

		uiBindingRegistry.registerMouseMoveBinding(new MouseEventMatcher(), new ClearCursorAction());

		// Row resize
		uiBindingRegistry.registerFirstMouseDragMode(new RowResizeEventMatcher(SWT.NONE, GridRegion.COLUMN_HEADER, 1), new RowResizeDragMode());
		uiBindingRegistry.registerFirstMouseDragMode(new RowResizeEventMatcher(SWT.NONE, GridRegion.ROW_HEADER, 1), new RowResizeDragMode());
		uiBindingRegistry.registerFirstMouseDragMode(new RowResizeEventMatcher(SWT.NONE, GridRegion.CORNER, 1), new RowResizeDragMode());
		uiBindingRegistry.registerFirstMouseDragMode(new RowResizeEventMatcher(SWT.NONE, GridRegion.BODY, 1), new RowResizeDragMode());

		// uiBindingRegistry.registerDoubleClickBinding(new RowResizeEventMatcher(SWT.NONE, GridRegion.ROW_HEADER, 1), new AutoResizeRowAction());
		uiBindingRegistry.registerSingleClickBinding(new RowResizeEventMatcher(SWT.NONE, GridRegion.ROW_HEADER, 1), new NoOpMouseAction());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(NattableConfigAttributes.REINITIALISE_ROW_HEIGHT, new Boolean(false));
	}

}
