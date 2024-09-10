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
 *   Dirk Fauth <dirk.fauth@googlemail.com> - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.clientarea;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.ui.action.IDragMode;
import org.eclipse.nebula.widgets.nattable.util.ClientAreaAdapter;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.nebula.widgets.nattable.viewport.command.RecalculateScrollBarsCommand;
import org.eclipse.swt.events.MouseEvent;

/**
 *
 */
public class ClientAreaResizeDragMode implements IDragMode {

	protected ILayer rowHeaderIndexLayer;
    protected ILayer baseLayer;
    protected ClientAreaAdapter clientAreaAdapter;
    protected ViewportLayer[] viewportLayer;

    public ClientAreaResizeDragMode(ILayer rowHeaderIndexLayer, ILayer baseLayer,
            ClientAreaAdapter clientAreaAdapter, ViewportLayer... viewportLayer) {
    	this.rowHeaderIndexLayer = rowHeaderIndexLayer;
        this.baseLayer = baseLayer;
        this.clientAreaAdapter = clientAreaAdapter;
        this.viewportLayer = viewportLayer;
    }

    @Override
    public void mouseDown(NatTable natTable, MouseEvent event) {}

    @Override
    public void mouseMove(NatTable natTable, MouseEvent event) {
    }

    @Override
    public void mouseUp(NatTable natTable, MouseEvent event) {
        int baseWidth = this.baseLayer.getWidth();
        int newWidth = event.x - this.rowHeaderIndexLayer.getWidth();
        if (newWidth < 0) {
            newWidth = 1;
        } else if (newWidth > baseWidth) {
            newWidth = baseWidth;
        }
        this.clientAreaAdapter.setWidth(newWidth);

        for (ViewportLayer vp : this.viewportLayer) {
            vp.invalidateHorizontalStructure();

            vp.doCommand(new RecalculateScrollBarsCommand());
        }

        natTable.redraw();
        natTable.getParent().layout(true, true);
    }

}
