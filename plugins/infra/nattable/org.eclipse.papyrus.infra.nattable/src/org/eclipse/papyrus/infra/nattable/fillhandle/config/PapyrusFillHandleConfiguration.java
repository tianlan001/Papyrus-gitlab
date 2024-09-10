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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.fillhandle.config;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.copy.command.InternalCopyDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.fillhandle.FillHandleLayerPainter;
import org.eclipse.nebula.widgets.nattable.fillhandle.action.FillHandleCursorAction;
import org.eclipse.nebula.widgets.nattable.fillhandle.config.FillHandleConfiguration;
import org.eclipse.nebula.widgets.nattable.fillhandle.event.FillHandleEventMatcher;
import org.eclipse.nebula.widgets.nattable.fillhandle.event.FillHandleMarkupListener;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.action.ClearCursorAction;
import org.eclipse.nebula.widgets.nattable.ui.action.NoOpMouseAction;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.fillhandle.action.PapyrusFillHandleDragMode;
import org.eclipse.papyrus.infra.nattable.fillhandle.command.PapyrusFillHandlePasteCommandHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * The papyrus configuration for the fill handler.
 */
public class PapyrusFillHandleConfiguration extends FillHandleConfiguration {
	
	/**
	 * The current nattable model manager.
	 */
	protected INattableModelManager tableManager;

	/**
	 * Constructor.
	 *
	 * @param selectionLayer The selection layer.
	 * @param tableManager The current nattable model manager.
	 */
	public PapyrusFillHandleConfiguration(final SelectionLayer selectionLayer, final INattableModelManager tableManager) {
		super(selectionLayer);
		this.tableManager = tableManager;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.config.FillHandleConfiguration#configureTypedLayer(org.eclipse.nebula.widgets.nattable.NatTable)
	 */
	@Override
    public void configureTypedLayer(final NatTable natTable) {
        // initialization works here because configureLayer() is executed before
        // configureUiBindings()
        this.clipboard = natTable.getInternalCellClipboard();

        this.painter = new FillHandleLayerPainter(true, false);
        this.selectionLayer.setLayerPainter(this.painter);

        this.selectionLayer.addLayerListener(new FillHandleMarkupListener(this.selectionLayer));

        final InternalCopyDataCommandHandler handler = new InternalCopyDataCommandHandler(this.selectionLayer, this.clipboard);
        handler.setCopyFormattedText(true);
		this.selectionLayer.registerCommandHandler(
                handler);
        this.selectionLayer.registerCommandHandler(
                new PapyrusFillHandlePasteCommandHandler(this.selectionLayer, this.clipboard, this.tableManager));
    }
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.config.FillHandleConfiguration#configureUiBindings(org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry)
	 */
	@Override
    public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
        FillHandleEventMatcher matcher = new FillHandleEventMatcher(this.painter);

        // Mouse move
        // Show fill handle cursor
        uiBindingRegistry.registerFirstMouseMoveBinding(
                matcher,
                new FillHandleCursorAction());
        uiBindingRegistry.registerMouseMoveBinding(
                new MouseEventMatcher(),
                new ClearCursorAction());

        // Mouse drag
        // trigger the handle drag operations
        uiBindingRegistry.registerFirstMouseDragMode(
                matcher,
                new PapyrusFillHandleDragMode(this.selectionLayer, this.clipboard));

        // Mouse click
        // ensure no selection is triggered on mouse down on the handle
        uiBindingRegistry.registerFirstMouseDownBinding(
                matcher,
                new NoOpMouseAction());
    }

}
